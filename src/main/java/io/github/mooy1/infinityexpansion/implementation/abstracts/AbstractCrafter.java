package io.github.mooy1.infinityexpansion.implementation.abstracts;

import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.slimefun.abstracts.TickingContainer;
import io.github.mooy1.infinitylib.slimefun.presets.MenuPreset;
import io.github.mooy1.infinitylib.slimefun.recipes.inputs.StrictMultiInput;
import io.github.mooy1.infinitylib.slimefun.recipes.outputs.StrictMultiOutput;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;

/**
 * An abstract crafter
 * 
 * @author Mooy1
 *
 */
public abstract class AbstractCrafter extends TickingContainer {
    
    protected static final int[] INPUT_SLOTS = MenuPreset.craftingInput;
    private static final int[] OUTPUT_SLOT = MenuPreset.craftingOutput;
    private static final int[] BACKGROUND = {5, 6, 7, 8, 41, 42, 43, 44};
    private static final int[] STATUS_BORDER = {14, 32};
    private static final int STATUS_SLOT = 23;

    private final Map<StrictMultiInput, StrictMultiOutput> recipes;
    
    public AbstractCrafter(Category category, SlimefunItemStack stack,
                           Map<StrictMultiInput, StrictMultiOutput> recipes, RecipeType type, ItemStack[] recipe) {
        super(category, stack, type, recipe);
        this.recipes = recipes;
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu menu, @Nonnull Location l) {
        menu.dropItems(l, OUTPUT_SLOT);
        menu.dropItems(l, INPUT_SLOTS);
    }

    @Nonnull
    @Override
    protected final int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, ItemStack item) {
        return new int[0];
    }

    @Override
    public final void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
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
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.invalidInput, ChestMenuUtils.getEmptyClickHandler());
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
    public final void tick(@Nonnull BlockMenu inv, @Nonnull Block b, @Nonnull Config config) {
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
    
    private void craft(@Nonnull BlockMenu inv, @Nonnull Player p) {
        if (preCraftFail(inv.getLocation(), inv)) {
            inv.replaceExistingItem(STATUS_SLOT, preCraftItem(inv.getLocation(), inv));
            String msg = preCraftMessage(inv.getLocation(), inv);
            if (msg != null) {
                p.sendMessage(msg);
            }
            return;
        }
        
        Pair<ItemStack, int[]> output = getOutput(inv);
        
        if (output == null) { //invalid

            inv.replaceExistingItem(STATUS_SLOT, MenuPreset.invalidRecipe);
            p.sendMessage( ChatColor.RED + "Invalid Recipe!");

        } else {
            
            if (!inv.fits(output.getFirstValue(), OUTPUT_SLOT)) { //not enough room

                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughRoom);
                p.sendMessage(  ChatColor.GOLD + "Not enough room!");

            } else { //enough room

                for (int i = 0 ; i < INPUT_SLOTS.length ; i++) {
                    int amount = output.getSecondValue()[i];
                    if (amount > 0) {
                        inv.consumeItem(INPUT_SLOTS[i], amount);
                    }
                }
                p.sendMessage(  ChatColor.GREEN + "Crafted: " + ItemUtils.getItemName(output.getFirstValue()));

                postCraft(inv.getLocation(), inv, p);

                inv.pushItem(output.getFirstValue(), OUTPUT_SLOT);

            }
        }
    }
    
    @Nullable
    private Pair<ItemStack, int[]> getOutput(@Nonnull BlockMenu inv) {
        StrictMultiOutput output = this.recipes.get(new StrictMultiInput(inv, INPUT_SLOTS));
        if (output == null) {
            return null;
        }
        ItemStack out = output.getOutput().clone();
        modifyOutput(inv, out);
        return new Pair<>(out, output.getInputConsumption());
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

}
