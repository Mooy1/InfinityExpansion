package io.github.mooy1.infinityexpansion.items.abstracts;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.mooy1.infinitylib.recipes.RecipeMap;
import io.github.mooy1.infinitylib.recipes.RecipeOutput;
import io.github.mooy1.infinitylib.slimefun.AbstractTickingContainer;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;

/**
 * An abstract crafter
 *
 * @author Mooy1
 */
public abstract class AbstractCrafter extends AbstractTickingContainer {

    protected static final int[] INPUT_SLOTS = MenuPreset.CRAFTING_INPUT;
    private static final int OUTPUT_SLOT = MenuPreset.CRAFTING_OUTPUT;
    private static final int[] BACKGROUND = {5, 6, 7, 8, 41, 42, 43, 44};
    private static final int[] STATUS_BORDER = {14, 32};
    private static final int STATUS_SLOT = 23;

    private final RecipeMap<ItemStack> recipes;

    public AbstractCrafter(Category category, SlimefunItemStack stack,
                           RecipeMap<ItemStack> recipes, RecipeType type, ItemStack[] recipe) {
        super(category, stack, type, recipe);
        this.recipes = recipes;
    }

    @Override
    protected final void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu menu, @Nonnull Location l) {
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
        for (int slot : MenuPreset.CRAFTING_INPUT_BORDER) {
            blockMenuPreset.addItem(slot, MenuPreset.INPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : MenuPreset.CRAFTING_OUTPUT_BORDER) {
            blockMenuPreset.addItem(slot, MenuPreset.OUTPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : BACKGROUND) {
            blockMenuPreset.addItem(slot, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : STATUS_BORDER) {
            blockMenuPreset.addItem(slot, MenuPreset.STATUS_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.INVALID_INPUT, ChestMenuUtils.getEmptyClickHandler());
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
    protected final void tick(@Nonnull BlockMenu inv, @Nonnull Block b) {
        if (inv.hasViewer()) {
            if (preCraftFail(b.getLocation(), inv)) {
                inv.replaceExistingItem(STATUS_SLOT, preCraftItem(b.getLocation(), inv));
                return;
            }

            RecipeOutput<ItemStack> output = getOutput(inv);

            if (output == null) {

                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.INVALID_RECIPE);

            } else {
                ItemStack out = output.getOutput().clone();
                modifyOutput(inv, out);
                inv.replaceExistingItem(STATUS_SLOT, Util.getDisplayItem(out));
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

        RecipeOutput<ItemStack> output = getOutput(inv);

        if (output == null) { //invalid

            inv.replaceExistingItem(STATUS_SLOT, MenuPreset.INVALID_RECIPE);
            p.sendMessage(ChatColor.RED + "Invalid Recipe!");

        } else {
            ItemStack out = output.getOutput().clone();
            modifyOutput(inv, out);

            if (!inv.fits(out, OUTPUT_SLOT)) { //not enough room

                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.NO_ROOM);
                p.sendMessage(ChatColor.GOLD + "Not enough room!");

            } else { //enough room

                output.consumeInput();
                p.sendMessage(ChatColor.GREEN + "Crafted: " + ItemUtils.getItemName(out));
                postCraft(inv.getLocation(), inv, p);
                inv.pushItem(out, OUTPUT_SLOT);
            }
        }
    }

    @Nullable
    private RecipeOutput<ItemStack> getOutput(@Nonnull BlockMenu inv) {
        ItemStack[] in = new ItemStack[INPUT_SLOTS.length];
        for (int i = 0 ; i < in.length ; i++) {
            in[i] = inv.getItemInSlot(INPUT_SLOTS[i]);
        }
        return this.recipes.get(in);
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
