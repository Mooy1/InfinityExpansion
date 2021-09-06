package io.github.mooy1.infinityexpansion.items.generators;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.items.materials.Materials;
import io.github.mooy1.infinitylib.common.StackUtils;
import io.github.mooy1.infinitylib.machines.MenuBlock;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;

/**
 * A reactor that generates huge power but costs infinity ingots and void ingots
 *
 * @author Mooy1
 */
@ParametersAreNonnullByDefault
public final class InfinityReactor extends MenuBlock implements EnergyNetProvider, RecipeDisplayItem {

    private static final int INFINITY_INTERVAL = 196000;
    private static final int VOID_INTERVAL = 32000;
    private static final int[] INPUT_SLOTS = { 10, 16 };
    private static final int STATUS_SLOT = 13;

    private final int gen;

    public InfinityReactor(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int gen) {
        super(category, item, recipeType, recipe);
        this.gen = gen;
    }

    @Override
    protected void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        if (BlockStorage.getLocationInfo(b.getLocation(), "progress") == null) {
            BlockStorage.addBlockInfo(b, "progress", "0");
        }
    }

    @Override
    protected void setup(@Nonnull BlockMenuPreset blockMenuPreset) {
        blockMenuPreset.drawBackground(new CustomItemStack(Material.WHITE_STAINED_GLASS_PANE,
                "&fInfinity Ingot Input"), new int[] {
                0, 1, 2,
                9, 11,
                18, 19, 20
        });
        blockMenuPreset.drawBackground(new int[] {
                3, 4, 5,
                12, 13, 14,
                21, 22, 23
        });
        blockMenuPreset.drawBackground(new CustomItemStack(Material.BLACK_STAINED_GLASS_PANE,
                "&8Void Ingot Input"), new int[] {
                6, 7, 8,
                15, 17,
                24, 25, 26
        });
    }

    @Nonnull
    @Override
    public int[] getInputSlots(DirtyChestMenu menu, ItemStack item) {
        String input = StackUtils.getId(item);
        if (Materials.VOID_INGOT.getItemId().equals(input)) {
            return new int[] { INPUT_SLOTS[1] };
        }
        else if (Materials.INFINITE_INGOT.getItemId().equals(input)) {
            return new int[] { INPUT_SLOTS[0] };
        }
        else {
            return new int[0];
        }
    }

    @Override
    protected int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    protected int[] getOutputSlots() {
        return new int[0];
    }

    @Override
    public int getGeneratedOutput(@Nonnull Location l, @Nonnull Config config) {
        BlockMenu inv = BlockStorage.getInventory(l);

        int progress = Integer.parseInt(BlockStorage.getLocationInfo(l, "progress"));
        ItemStack infinityInput = inv.getItemInSlot(INPUT_SLOTS[0]);
        ItemStack voidInput = inv.getItemInSlot(INPUT_SLOTS[1]);

        if (progress == 0) { //need infinity + void

            if (infinityInput == null || !Materials.INFINITE_INGOT.getItemId().equals(StackUtils.getId(infinityInput))) { //wrong input

                if (inv.hasViewer()) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.RED_STAINED_GLASS_PANE, "&cInput more &fInfinity Ingots"));
                }
                return 0;

            }

            if (voidInput == null || !Materials.VOID_INGOT.getItemId().equals(StackUtils.getId(voidInput))) { //wrong input

                if (inv.hasViewer()) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.RED_STAINED_GLASS_PANE, "&cInput more &8Void Ingots"));
                }
                return 0;

            }

            //correct input
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.LIME_STAINED_GLASS_PANE,
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
                inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.LIME_STAINED_GLASS_PANE, "&aFinished Generation"));
            }
            BlockStorage.addBlockInfo(l, "progress", "0");
            return this.gen;

        }

        if (Math.floorMod(progress, VOID_INTERVAL) == 0) { //need void

            if (voidInput == null || !Materials.VOID_INGOT.getItemId().equals(StackUtils.getId(voidInput))) { //wrong input

                if (inv.hasViewer()) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.RED_STAINED_GLASS_PANE, "&cInput more &8Void Ingots"));
                }
                return 0;

            }

            //right input
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.LIME_STAINED_GLASS_PANE,
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
            inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.LIME_STAINED_GLASS_PANE,
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

        ItemStack item = new CustomItemStack(Materials.INFINITE_INGOT, Materials.INFINITE_INGOT.getDisplayName(),
                "", ChatColor.GOLD + "Lasts for 1 day");
        items.add(item);
        items.add(null);

        item = new CustomItemStack(Materials.VOID_INGOT, Materials.VOID_INGOT.getDisplayName(),
                ChatColor.GOLD + "Lasts for 4 hours");
        items.add(item);
        items.add(null);

        return items;
    }

}
