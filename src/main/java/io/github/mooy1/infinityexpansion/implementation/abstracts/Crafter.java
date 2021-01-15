package io.github.mooy1.infinityexpansion.implementation.abstracts;

import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.filter.FilterType;
import io.github.mooy1.infinitylib.filter.MultiFilter;
import io.github.mooy1.infinitylib.misc.DelayedRecipeType;
import io.github.mooy1.infinitylib.objects.AbstractContainer;
import io.github.mooy1.infinitylib.player.MessageUtils;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * An abstract crafter
 * 
 * @author Mooy1
 *
 */
public abstract class Crafter extends AbstractContainer {
    
    private static final int EMPTY = new MultiFilter(FilterType.MIN_AMOUNT, new ItemStack[9]).hashCode();
    protected static final int[] INPUT_SLOTS = MenuPreset.craftingInput;
    private static final int[] OUTPUT_SLOT = MenuPreset.craftingOutput;
    private static final int[] BACKGROUND = {5, 6, 7, 8, 41, 42, 43, 44};
    private static final int[] STATUS_BORDER = {14, 32};
    private static final int STATUS_SLOT = 23;

    private final Map<MultiFilter, Pair<SlimefunItemStack, int[]>> recipes = new HashMap<>();
    
    public Crafter(Category category, SlimefunItemStack stack, DelayedRecipeType recipeType, RecipeType type, ItemStack[] recipe) {
        super(category, stack, type, recipe);

        recipeType.acceptEach((stacks, stack1) -> {
            if (stacks.length == 9 && stack1 instanceof SlimefunItemStack) {
                MultiFilter filter = new MultiFilter(FilterType.IGNORE_AMOUNT, stacks);
                this.recipes.put(filter, new Pair<>((SlimefunItemStack) stack1, filter.getAmounts()));
            }
        });

        registerBlockHandler(getId(), (p, b, slimefunItem, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);
            if (inv != null) {
                inv.dropItems(b.getLocation(), OUTPUT_SLOT);
                inv.dropItems(b.getLocation(), INPUT_SLOTS);
            }
            return true;
        });
    }

    public final void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int slot : MenuPreset.craftingInputBorder) {
            blockMenuPreset.addItem(slot, MenuPreset.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : MenuPreset.craftingOutputBorder) {
            blockMenuPreset.addItem(slot, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : BACKGROUND) {
            blockMenuPreset.addItem(slot, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : STATUS_BORDER) {
            blockMenuPreset.addItem(slot, MenuPreset.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.loadingItemBarrier, ChestMenuUtils.getEmptyClickHandler());
    }
    
    public boolean preCraftFail(@Nonnull Location l, @Nonnull BlockMenu inv) {
        return false;
    }

    @Nullable
    public ItemStack preCraftItem(@Nonnull Location l, @Nonnull BlockMenu inv) {
        return null;
    }
    
    @Nullable
    public String preCraftMessage(@Nonnull Location l, @Nonnull BlockMenu inv) {
        return null;
    }
    
    public abstract void postCraft(@Nonnull Location l, @Nonnull BlockMenu inv, @Nonnull Player p);

    @Override
    public final void tick(@Nonnull Block b, @Nonnull BlockMenu inv) {
        if (inv.hasViewer()) {
            if (preCraftFail(b.getLocation(), inv)) {
                inv.replaceExistingItem(STATUS_SLOT, preCraftItem(b.getLocation(), inv));
                return;
            }

            Pair<ItemStack, int[]> output = getOutput(inv);

            if (output == null) {

                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.invalidRecipe);

            } else {

                inv.replaceExistingItem(STATUS_SLOT, Util.getDisplayItem(output.getFirstValue().clone()));

            }
        }
    }

    /**
     * This method crafts an item and updates the status of the menu
     *
     * @param inv BlockMenu
     * @param p player crafting it
     */
    private void craft(@Nonnull BlockMenu inv, @Nonnull Player p) {
        if (preCraftFail(inv.getLocation(), inv)) {
            inv.replaceExistingItem(STATUS_SLOT, preCraftItem(inv.getLocation(), inv));
            String msg = preCraftMessage(inv.getLocation(), inv);
            if (msg != null) MessageUtils.messageWithCD(p, 1000, msg);
            return;
        }
        
        Pair<ItemStack, int[]> output = getOutput(inv);
        
        if (output == null) { //invalid

            inv.replaceExistingItem(STATUS_SLOT, MenuPreset.invalidRecipe);
            MessageUtils.messageWithCD(p, 1000, ChatColor.RED + "Invalid Recipe!");

        } else {
            
            // check for correct amounts
            for (int slot = 0 ; slot < output.getSecondValue().length ; slot++) {
                ItemStack input = inv.getItemInSlot(INPUT_SLOTS[slot]);
                int required = output.getSecondValue()[slot];
                if (required == 0 ? input != null : input.getAmount() < required) {
                    inv.replaceExistingItem(STATUS_SLOT, MenuPreset.invalidRecipe);
                    MessageUtils.messageWithCD(p, 1000, ChatColor.RED + "Invalid input amounts!");
                    return;
                }
            }
            
            if (!inv.fits(output.getFirstValue(), OUTPUT_SLOT)) { //not enough room

                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughRoom);
                MessageUtils.messageWithCD(p, 1000, ChatColor.GOLD + "Not enough room!");

            } else { //enough room

                for (int i = 0 ; i < INPUT_SLOTS.length ; i++) {
                    int amount = output.getSecondValue()[i];
                    if (amount > 0) {
                        inv.consumeItem(INPUT_SLOTS[i], amount);
                    }
                }
                MessageUtils.messageWithCD(p, 1000, ChatColor.GREEN + "Crafted: " + ItemUtils.getItemName(output.getFirstValue()));

                postCraft(inv.getLocation(), inv, p);

                inv.pushItem(output.getFirstValue(), OUTPUT_SLOT);

            }
        }
    }

    /**
     * This method gets the output from an inventory
     *
     * @param inv inventory to check
     * @return the output if any
     */
    @Nullable
    private Pair<ItemStack, int[]> getOutput(@Nonnull BlockMenu inv) {
        
        MultiFilter input = MultiFilter.fromMenu(FilterType.IGNORE_AMOUNT, inv, INPUT_SLOTS);
        
        if (input.hashCode() == EMPTY) {
            return null;
        }

        Pair<SlimefunItemStack, int[]> pair = this.recipes.get(input);
        
        if (pair != null) {
            ItemStack output = pair.getFirstValue().clone();
            modifyOutput(inv, output);
            return new Pair<>(output, pair.getSecondValue());
        }
        
        return null;
    }
    
    protected void modifyOutput(@Nonnull BlockMenu inv, @Nonnull ItemStack output) {
        
    }

    @Override
    public final void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        menu.addMenuClickHandler(STATUS_SLOT, (p, slot, item, action) -> {
            craft(menu, p);
            return false;
        });
    }

    @Override
    public final int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        return new int[0];
    }
}
