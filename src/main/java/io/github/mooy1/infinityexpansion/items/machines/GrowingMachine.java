package io.github.mooy1.infinityexpansion.items.machines;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.items.abstracts.AbstractMachine;
import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

public final class GrowingMachine extends AbstractMachine implements RecipeDisplayItem {
    
    private static final int[] OUTPUT_SLOTS = Util.LARGE_OUTPUT;
    private static final int[] INPUT_SLOTS = {MenuPreset.INPUT + 27};
    private static final int STATUS_SLOT = MenuPreset.INPUT;
    private static final ItemStack GROWING = new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aGrowing...");
    private static final ItemStack INPUT_PLANT = new CustomItem(Material.BLUE_STAINED_GLASS_PANE, "&9Input a plant!");

    private static final EnumMap<Material, ItemStack[]> NORMAL_RECIPES = new EnumMap<>(Material.class);
    private static final EnumMap<Material, ItemStack[]> TREE_RECIPES = new EnumMap<>(Material.class);
    
    static {
        NORMAL_RECIPES.put(Material.WHEAT_SEEDS, new ItemStack[] {new ItemStack(Material.WHEAT, 2)});
        NORMAL_RECIPES.put(Material.CARROT, new ItemStack[] {new ItemStack(Material.CARROT, 2)});
        NORMAL_RECIPES.put(Material.POTATO, new ItemStack[] {new ItemStack(Material.POTATO, 2)});
        NORMAL_RECIPES.put(Material.BEETROOT_SEEDS, new ItemStack[] {new ItemStack(Material.BEETROOT, 2)});
        NORMAL_RECIPES.put(Material.PUMPKIN_SEEDS, new ItemStack[] {new ItemStack(Material.PUMPKIN)});
        NORMAL_RECIPES.put(Material.MELON_SEEDS, new ItemStack[] {new ItemStack(Material.MELON)});
        NORMAL_RECIPES.put(Material.SUGAR_CANE, new ItemStack[] {new ItemStack(Material.SUGAR_CANE, 2)});
        NORMAL_RECIPES.put(Material.COCOA_BEANS, new ItemStack[] {new ItemStack(Material.COCOA_BEANS, 2)});
        NORMAL_RECIPES.put(Material.CACTUS, new ItemStack[] {new ItemStack(Material.CACTUS, 2)});
        NORMAL_RECIPES.put(Material.BAMBOO, new ItemStack[] {new ItemStack(Material.BAMBOO, 6)});
        NORMAL_RECIPES.put(Material.CHORUS_FLOWER, new ItemStack[] {new ItemStack(Material.CHORUS_FRUIT, 6)});
        NORMAL_RECIPES.put(Material.NETHER_WART, new ItemStack[] {new ItemStack(Material.NETHER_WART, 2)});
        TREE_RECIPES.put(Material.OAK_SAPLING, new ItemStack[] {
                new ItemStack(Material.OAK_LEAVES, 8), new ItemStack(Material.OAK_LOG, 6), new ItemStack(Material.STICK), new ItemStack(Material.APPLE)
        });
        TREE_RECIPES.put(Material.SPRUCE_SAPLING, new ItemStack[] {
                new ItemStack(Material.SPRUCE_LEAVES, 8), new ItemStack(Material.SPRUCE_LOG, 6), new ItemStack(Material.STICK, 2)
        });
        TREE_RECIPES.put(Material.DARK_OAK_SAPLING, new ItemStack[] {
                new ItemStack(Material.DARK_OAK_LEAVES, 8), new ItemStack(Material.DARK_OAK_LOG, 6), new ItemStack(Material.APPLE)
        });
        TREE_RECIPES.put(Material.BIRCH_SAPLING, new ItemStack[] {
                new ItemStack(Material.BIRCH_LEAVES, 8), new ItemStack(Material.BIRCH_LOG, 6)
        });
        TREE_RECIPES.put(Material.ACACIA_SAPLING, new ItemStack[] {
                new ItemStack(Material.ACACIA_LEAVES, 8), new ItemStack(Material.ACACIA_LOG, 6)
        });
        TREE_RECIPES.put(Material.JUNGLE_SAPLING, new ItemStack[] {
                new ItemStack(Material.JUNGLE_LEAVES, 8), new ItemStack(Material.JUNGLE_LOG, 6), new ItemStack(Material.COCOA_BEANS)
        });
        TREE_RECIPES.put(Material.WARPED_FUNGUS, new ItemStack[] {
                new ItemStack(Material.WARPED_HYPHAE, 8), new ItemStack(Material.WARPED_STEM, 6), new ItemStack(Material.SHROOMLIGHT)
        });
        TREE_RECIPES.put(Material.CRIMSON_FUNGUS, new ItemStack[] {
                new ItemStack(Material.CRIMSON_HYPHAE, 8), new ItemStack(Material.CRIMSON_STEM, 6), new ItemStack(Material.WEEPING_VINES)
        });
    }

    private final EnumMap<Material, ItemStack[]> recipes;
    private final int time;
    private final int energy;

    public GrowingMachine(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                          int energy, int time, boolean tree) {
        super(category, item, recipeType, recipe);
        this.energy = energy;
        this.time = time;
        this.recipes = tree ? TREE_RECIPES : NORMAL_RECIPES;
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu inv, @Nonnull Location l) {
        inv.dropItems(l, OUTPUT_SLOTS);
        inv.dropItems(l, INPUT_SLOTS);
    }

    @Override
    protected boolean process(@Nonnull BlockMenu menu, @Nonnull Block b) {
        ItemStack input = menu.getItemInSlot(INPUT_SLOTS[0]);
        if (input != null) {
            if (menu.hasViewer()) {
                menu.replaceExistingItem(STATUS_SLOT, GROWING);
            }
            if (InfinityExpansion.inst().getGlobalTick() % this.time == 0) {
                ItemStack[] output = this.recipes.get(input.getType());
                if (output != null) {
                    for (ItemStack item : output) {
                        menu.pushItem(item.clone(), OUTPUT_SLOTS);
                    }
                }
            }
            return true;
        } else {
            if (menu.hasViewer()) {
                menu.replaceExistingItem(STATUS_SLOT, INPUT_PLANT);
            }
            return false;
        }
    }

    @Override
    protected int getStatusSlot() {
        return STATUS_SLOT;
    }

    @Override
    protected int getEnergyConsumption() {
        return this.energy;
    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        super.setupMenu(blockMenuPreset);
        for (int i : MenuPreset.INPUT_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.STATUS_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.INPUT_BORDER) {
            blockMenuPreset.addItem(i + 27, MenuPreset.INPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : Util.LARGE_OUTPUT_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.OUTPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.INSERT) {
            return INPUT_SLOTS;
        } else if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        } else {
            return new int[0];
        }
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> list = new ArrayList<>();
        for (Map.Entry<Material, ItemStack[]> entry : this.recipes.entrySet()) {
            ItemStack in = new ItemStack(entry.getKey());
            for (ItemStack item : entry.getValue()) {
                list.add(in);
                list.add(item);
            }
        }
        return list;
    }

}
