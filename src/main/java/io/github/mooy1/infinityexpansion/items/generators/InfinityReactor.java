package io.github.mooy1.infinityexpansion.items.generators;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.items.Materials;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.mooy1.infinitylib.slimefun.AbstractContainer;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

/**
 * A reactor that generates huge power but costs infinity ingots and void ingots
 *
 * @author Mooy1
 */
public final class InfinityReactor extends AbstractContainer implements EnergyNetProvider, RecipeDisplayItem {

    private static final int INFINITY_INTERVAL = 196000;
    private static final int VOID_INTERVAL = 32000;
    private static final int[] INPUT_SLOTS = {
            MenuPreset.INPUT, MenuPreset.OUTPUT
    };
    private static final int STATUS_SLOT = MenuPreset.STATUS;

    private final int gen;

    public InfinityReactor(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int gen) {
        super(category, item, recipeType, recipe);
        this.gen = gen;
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        if (BlockStorage.getLocationInfo(b.getLocation(), "progress") == null) {
            BlockStorage.addBlockInfo(b, "progress", "0");
        }
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu inv, @Nonnull Location l) {
        inv.dropItems(l, INPUT_SLOTS);
    }

    @Override
    public void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : MenuPreset.STATUS_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.STATUS_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.OUTPUT_BORDER) {
            blockMenuPreset.addItem(i, new CustomItem(
                    Material.BLACK_STAINED_GLASS_PANE, "&8Void Ingot Input"), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.INPUT_BORDER) {
            blockMenuPreset.addItem(i, new CustomItem(
                    Material.WHITE_STAINED_GLASS_PANE, "&fInfinity Ingot Input"), ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.LOADING, ChestMenuUtils.getEmptyClickHandler());
    }

    @Nonnull
    @Override
    public int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, @Nonnull ItemStack item) {
        if (flow == ItemTransportFlow.INSERT) {
            String input = StackUtils.getID(item);
            if (Materials.VOID_INGOT.getItemId().equals(input)) {
                return new int[] {INPUT_SLOTS[1]};
            } else if (Materials.INFINITE_INGOT.getItemId().equals(input)) {
                return new int[] {INPUT_SLOTS[0]};
            }
        }

        return new int[0];
    }

    @Override
    public int getGeneratedOutput(@Nonnull Location l, @Nonnull Config config) {
        BlockMenu inv = BlockStorage.getInventory(l);

        int progress = Integer.parseInt(BlockStorage.getLocationInfo(l, "progress"));
        ItemStack infinityInput = inv.getItemInSlot(INPUT_SLOTS[0]);
        ItemStack voidInput = inv.getItemInSlot(INPUT_SLOTS[1]);

        if (progress == 0) { //need infinity + void

            if (infinityInput == null || !Materials.INFINITE_INGOT.getItemId().equals(StackUtils.getID(infinityInput))) { //wrong input

                if (inv.hasViewer()) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.RED_STAINED_GLASS_PANE, "&cInput more &fInfinity Ingots"));
                }
                return 0;

            }

            if (voidInput == null || !Materials.VOID_INGOT.getItemId().equals(StackUtils.getID(voidInput))) { //wrong input

                if (inv.hasViewer()) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.RED_STAINED_GLASS_PANE, "&cInput more &8Void Ingots"));
                }
                return 0;

            }

            //correct input
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                        "&aStarting Generation",
                        "&aTime until infinity ingot needed: " + INFINITY_INTERVAL,
                        "&aTime until void ingot needed: " + VOID_INTERVAL
                ));
            }
            inv.consumeItem(INPUT_SLOTS[0]);
            inv.consumeItem(INPUT_SLOTS[1]);
            BlockStorage.addBlockInfo(l, "progress", "1");
            return this.gen;

        }

        if (progress >= INFINITY_INTERVAL) { //done

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aFinished Generation"));
            }
            BlockStorage.addBlockInfo(l, "progress", "0");
            return this.gen;

        }

        if (Math.floorMod(progress, VOID_INTERVAL) == 0) { //need void

            if (voidInput == null || !Materials.VOID_INGOT.getItemId().equals(StackUtils.getID(voidInput))) { //wrong input

                if (inv.hasViewer()) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.RED_STAINED_GLASS_PANE, "&cInput more &8Void Ingots"));
                }
                return 0;

            }

            //right input
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                        "&aGenerating...",
                        "&aTime until infinity ingot needed: " + (INFINITY_INTERVAL - progress),
                        "&aTime until void ingot needed: " + (VOID_INTERVAL - Math.floorMod(progress, VOID_INTERVAL))
                ));
            }
            BlockStorage.addBlockInfo(l, "progress", String.valueOf(progress + 1));
            inv.consumeItem(INPUT_SLOTS[1]);
            return this.gen;

        }

        //generate

        if (inv.hasViewer()) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                            "&aGenerating...",
                            "&aTime until infinity ingot needed: " + (INFINITY_INTERVAL - progress),
                            "&aTime until void ingot needed: " + (VOID_INTERVAL - Math.floorMod(progress, VOID_INTERVAL))
                    )
            );
        }
        BlockStorage.addBlockInfo(l, "progress", String.valueOf(progress + 1));
        return this.gen;
    }

    @Override
    public int getCapacity() {
        return this.gen * 1000;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();

        ItemStack item = Materials.INFINITE_INGOT.clone();
        StackUtils.addLore(item, "", ChatColor.GOLD + "Lasts for 1 day");
        items.add(item);
        items.add(null);

        item = Materials.VOID_INGOT.clone();
        StackUtils.addLore(item, "", ChatColor.GOLD + "Lasts for 4 hours");
        items.add(item);
        items.add(null);

        return items;
    }

}
