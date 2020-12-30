package io.github.mooy1.infinityexpansion.implementation.abstracts;

import io.github.mooy1.infinityexpansion.utils.RecipeUtils;
import io.github.mooy1.infinitylib.filter.FilterType;
import io.github.mooy1.infinitylib.filter.MultiFilter;
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
    
    private final Map<MultiFilter, SlimefunItemStack> recipes = new HashMap<>();
    
    private static final int[] INPUT_SLOTS = {
            10, 11, 12,
            19, 20, 21,
            28, 29, 30
    };
    private static final int[] INPUT_BORDER = {
            0, 1, 2, 3, 4,
            9, 13,
            18, 22,
            27, 31,
            36, 37, 38, 39, 40
    };
    private static final int[] OUTPUT_SLOTS = {
            MenuPreset.slot3 + 9
    };
    private static final int[] OUTPUT_BORDER = MenuPreset.slotChunk3;
    private static final int[] BACKGROUND = {
            5, 6, 7, 8,
            41, 42, 43, 44
    };
    private static final int[] STATUS_BORDER = {
            14, 32
    };
    private static final int STATUS_SLOT = 23;
    
    public Crafter(Category category, SlimefunItemStack stack, RecipeType recipeType, ItemStack[] recipe) {
        super(category, stack, recipeType, recipe);

        SlimefunItemStack[] outputs = getOutputs();
        ItemStack[][] items = getRecipes();
        
        for (int i = 0 ; i < items.length ; i++) {
            this.recipes.put(MultiFilter.fromStacks(FilterType.MIN_AMOUNT, items[i]), outputs[i]);
        }

        registerBlockHandler(getId(), (p, b, slimefunItem, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);
            if (inv != null) {
                inv.dropItems(b.getLocation(), OUTPUT_SLOTS);
                inv.dropItems(b.getLocation(), INPUT_SLOTS);
            }
            return true;
        });
    }

    public abstract SlimefunItemStack[] getOutputs();
    public abstract ItemStack[][] getRecipes();

    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int slot : INPUT_BORDER) {
            blockMenuPreset.addItem(slot, MenuPreset.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : OUTPUT_BORDER) {
            blockMenuPreset.addItem(slot + 9, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
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
    public String preCraftMessage(@Nonnull Location l, @Nonnull BlockMenu inv) {return null;}
    
    public abstract void postCraft(@Nonnull Location l, @Nonnull BlockMenu inv, @Nonnull Player p);

    @Override
    public void tick(@Nonnull Block b, @Nonnull BlockMenu inv) {
        if (inv.hasViewer()) {
            if (preCraftFail(b.getLocation(), inv)) {
                inv.replaceExistingItem(STATUS_SLOT, preCraftItem(b.getLocation(), inv));
                return;
            }

            Pair<SlimefunItemStack, int[]> output = getOutput(inv);

            if (output == null) {

                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.invalidRecipe);

            } else {

                inv.replaceExistingItem(STATUS_SLOT, RecipeUtils.getDisplayItem(output.getFirstValue().clone()));

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
        
        Pair<SlimefunItemStack, int[]> output = getOutput(inv);

        if (output == null) { //invalid

            inv.replaceExistingItem(STATUS_SLOT, MenuPreset.invalidRecipe);
            MessageUtils.messageWithCD(p, 1000, ChatColor.RED + "Invalid Recipe!");

        } else if (!inv.fits(output.getFirstValue(), OUTPUT_SLOTS)) { //not enough room

            inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughRoom);
            MessageUtils.messageWithCD(p, 1000, ChatColor.GOLD + "Not enough room!");

        } else { //enough room
            
            for (int i = 0 ; i < INPUT_SLOTS.length ; i++) {
                if (inv.getItemInSlot(INPUT_SLOTS[i]) != null ) {
                    inv.consumeItem(INPUT_SLOTS[i], output.getSecondValue()[i]);
                }
            }
            MessageUtils.messageWithCD(p, 1000, ChatColor.GREEN + "Crafted: " + ItemUtils.getItemName(output.getFirstValue()));
            
            postCraft(inv.getLocation(), inv, p);

            inv.pushItem(output.getFirstValue(), OUTPUT_SLOTS);

        }
    }

    /**
     * This method gets the output from an inventory
     *
     * @param inv inventory to check
     * @return the output if any
     */
    @Nullable
    private Pair<SlimefunItemStack, int[]> getOutput(@Nonnull BlockMenu inv) {
        
        MultiFilter input = MultiFilter.fromMenu(FilterType.MIN_AMOUNT, inv, INPUT_SLOTS);

        SlimefunItemStack output = this.recipes.get(input);
        
        if (output != null) {
            output = new SlimefunItemStack(output, output.getAmount());
            if (output.getItem() instanceof LoreStorage) {
                ((LoreStorage) output.getItem()).transfer(output, inv.getItemInSlot(INPUT_SLOTS[4]));
            }
            return new Pair<>(output, input.getAmounts());
        }
        
        return null;
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        menu.addMenuClickHandler(STATUS_SLOT, (p, slot, item, action) -> {
            craft(menu, p);
            return false;
        });
    }

    @Override
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        return new int[0];
    }
}
