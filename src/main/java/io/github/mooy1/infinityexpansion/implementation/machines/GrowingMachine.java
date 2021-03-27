package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinityexpansion.implementation.abstracts.AbstractMachine;
import io.github.mooy1.infinityexpansion.implementation.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.implementation.materials.Items;
import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.slimefun.presets.LorePreset;
import io.github.mooy1.infinitylib.slimefun.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class GrowingMachine extends AbstractMachine implements RecipeDisplayItem {
    
    public static final SlimefunItemStack BASIC = new SlimefunItemStack(
            "BASIC_VIRTUAL_FARM",
            Material.GRASS_BLOCK,
            "&9Basic &aVirtual Farm",
            "&7Automatically grows, harvests, and replants crops",
            "",
            LorePreset.speed(1),
            LorePreset.energyPerSecond(18)
    );
    public static final SlimefunItemStack ADVANCED = new SlimefunItemStack(
            "ADVANCED_VIRTUAL_FARM",
            Material.CRIMSON_NYLIUM,
            "&cAdvanced &aVirtual Farm",
            "&7Automatically grows, harvests, and replants crops",
            "",
            LorePreset.speed(5),
            LorePreset.energyPerSecond(90)
    );
    public static final SlimefunItemStack INFINITY = new SlimefunItemStack(
            "INFINITY_VIRTUAL_FARM",
            Material.WARPED_NYLIUM,
            "&bInfinity &aVirtual Farm",
            "&7Automatically grows, harvests, and replants crops",
            "",
            LorePreset.speed(25),
            LorePreset.energyPerSecond(900)
    );
    public static final SlimefunItemStack BASIC_TREE = new SlimefunItemStack(
            "BASIC_TREE_GROWER",
            Material.STRIPPED_OAK_WOOD,
            "&9Basic &2Tree Grower",
            "&7Automatically grows, harvests, and replants trees",
            "",
            LorePreset.speed(1),
            LorePreset.energyPerSecond(36)
    );
    public static final SlimefunItemStack ADVANCED_TREE = new SlimefunItemStack(
            "ADVANCED_TREE_GROWER",
            Material.STRIPPED_ACACIA_WOOD,
            "&cAdvanced &2Tree Grower",
            "&7Automatically grows, harvests, and replants trees",
            "",
            LorePreset.speed(5),
            LorePreset.energyPerSecond(180)
    );
    public static final SlimefunItemStack INFINITY_TREE = new SlimefunItemStack(
            "INFINITY_TREE_GROWER",
            Material.STRIPPED_WARPED_HYPHAE,
            "&bInfinity &2Tree Grower",
            "&7Automatically grows, harvests, and replants trees",
            "",
            LorePreset.speed(25),
            LorePreset.energyPerSecond(1800)
    );

    public static void setup(InfinityExpansion plugin) {
        EnumMap<Material, ItemStack[]> farmRecipes = new EnumMap<>(Material.class);
        
        farmRecipes.put(Material.WHEAT_SEEDS, new ItemStack[] {new ItemStack(Material.WHEAT, 2)});
        farmRecipes.put(Material.CARROT, new ItemStack[] {new ItemStack(Material.CARROT, 2)});
        farmRecipes.put(Material.POTATO, new ItemStack[] {new ItemStack(Material.POTATO, 2)});
        farmRecipes.put(Material.BEETROOT_SEEDS, new ItemStack[] {new ItemStack(Material.BEETROOT, 2)});
        farmRecipes.put(Material.PUMPKIN_SEEDS, new ItemStack[] {new ItemStack(Material.PUMPKIN)});
        farmRecipes.put(Material.MELON_SEEDS, new ItemStack[] {new ItemStack(Material.MELON)});
        farmRecipes.put(Material.SUGAR_CANE, new ItemStack[] {new ItemStack(Material.SUGAR_CANE, 2)});
        farmRecipes.put(Material.COCOA_BEANS, new ItemStack[] {new ItemStack(Material.COCOA_BEANS, 2)});
        farmRecipes.put(Material.CACTUS, new ItemStack[] {new ItemStack(Material.CACTUS, 2)});
        farmRecipes.put(Material.BAMBOO, new ItemStack[] {new ItemStack(Material.BAMBOO, 6)});
        farmRecipes.put(Material.CHORUS_FLOWER, new ItemStack[] {new ItemStack(Material.CHORUS_FRUIT, 6)});
        farmRecipes.put(Material.NETHER_WART, new ItemStack[] {new ItemStack(Material.NETHER_WART, 2)});
        
        new GrowingMachine(Categories.BASIC_MACHINES, BASIC, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS),
                Items.MAGSTEEL, new ItemStack(Material.DIAMOND_HOE), Items.MAGSTEEL,
                Items.MACHINE_CIRCUIT, new ItemStack(Material.GRASS_BLOCK), Items.MACHINE_CIRCUIT
        }, 18, 300, farmRecipes).register(plugin);
        new GrowingMachine(Categories.ADVANCED_MACHINES, ADVANCED, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.HARDENED_GLASS, SlimefunItems.HARDENED_GLASS, SlimefunItems.HARDENED_GLASS,
                Items.MAGNONIUM, BASIC, Items.MAGNONIUM,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }, 90, 60, farmRecipes).register(plugin);
        new GrowingMachine(Categories.INFINITY_CHEAT, INFINITY, InfinityWorkbench.TYPE, new ItemStack[] {
                new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS),
                new ItemStack(Material.GLASS), null, null, null, null, new ItemStack(Material.GLASS),
                new ItemStack(Material.GLASS), null, null, null, null, new ItemStack(Material.GLASS),
                new ItemStack(Material.GLASS), new ItemStack(Material.GRASS_BLOCK), new ItemStack(Material.GRASS_BLOCK), new ItemStack(Material.GRASS_BLOCK), new ItemStack(Material.GRASS_BLOCK), new ItemStack(Material.GLASS),
                Items.MACHINE_PLATE, SlimefunItems.CROP_GROWTH_ACCELERATOR_2, ADVANCED, ADVANCED, SlimefunItems.CROP_GROWTH_ACCELERATOR_2, Items.MACHINE_PLATE,
                Items.MACHINE_PLATE, Items.INFINITE_CIRCUIT, Items.INFINITE_CORE, Items.INFINITE_CORE, Items.INFINITE_CIRCUIT, Items.MACHINE_PLATE
        }, 900, 12, farmRecipes).register(plugin);
        
        EnumMap<Material, ItemStack[]> treeRecipes = new EnumMap<>(Material.class);
        
        treeRecipes.put(Material.OAK_SAPLING, new ItemStack[] {
                new ItemStack(Material.OAK_LEAVES, 8), new ItemStack(Material.OAK_LOG, 6), new ItemStack(Material.STICK), new ItemStack(Material.APPLE)
        });
        treeRecipes.put(Material.SPRUCE_SAPLING, new ItemStack[] {
                new ItemStack(Material.SPRUCE_LEAVES, 8), new ItemStack(Material.SPRUCE_LOG, 6), new ItemStack(Material.STICK, 2)
        });
        treeRecipes.put(Material.DARK_OAK_SAPLING, new ItemStack[] {
                new ItemStack(Material.DARK_OAK_LEAVES, 8), new ItemStack(Material.DARK_OAK_LOG, 6), new ItemStack(Material.APPLE)
        });
        treeRecipes.put(Material.BIRCH_SAPLING, new ItemStack[] {
                new ItemStack(Material.BIRCH_LEAVES, 8), new ItemStack(Material.BIRCH_LOG, 6)
        });
        treeRecipes.put(Material.ACACIA_SAPLING, new ItemStack[] {
                new ItemStack(Material.ACACIA_LEAVES, 8), new ItemStack(Material.ACACIA_LOG, 6)
        });
        treeRecipes.put(Material.JUNGLE_SAPLING, new ItemStack[] {
                new ItemStack(Material.JUNGLE_LEAVES, 8), new ItemStack(Material.JUNGLE_LOG, 6), new ItemStack(Material.COCOA_BEANS)
        });
        treeRecipes.put(Material.WARPED_FUNGUS, new ItemStack[] {
                new ItemStack(Material.WARPED_HYPHAE, 8), new ItemStack(Material.WARPED_STEM, 6), new ItemStack(Material.SHROOMLIGHT)
        });
        treeRecipes.put(Material.CRIMSON_FUNGUS, new ItemStack[] {
                new ItemStack(Material.CRIMSON_HYPHAE, 8), new ItemStack(Material.CRIMSON_STEM, 6), new ItemStack(Material.WEEPING_VINES)
        });
        
        new GrowingMachine(Categories.BASIC_MACHINES, BASIC_TREE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS),
                Items.MAGSTEEL, new ItemStack(Material.PODZOL), Items.MAGSTEEL,
                Items.MACHINE_CIRCUIT, BASIC, Items.MACHINE_CIRCUIT
        }, 36, 600, treeRecipes).register(plugin);
        new GrowingMachine(Categories.ADVANCED_MACHINES, ADVANCED_TREE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.HARDENED_GLASS, SlimefunItems.HARDENED_GLASS, SlimefunItems.HARDENED_GLASS,
                Items.MAGNONIUM, BASIC, Items.MAGNONIUM,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }, 180, 120, treeRecipes).register(plugin);
        new GrowingMachine(Categories.INFINITY_CHEAT, INFINITY_TREE, InfinityWorkbench.TYPE, new ItemStack[] {
                new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS),
                new ItemStack(Material.GLASS), SlimefunItems.TREE_GROWTH_ACCELERATOR, null, null, SlimefunItems.TREE_GROWTH_ACCELERATOR, new ItemStack(Material.GLASS),
                new ItemStack(Material.GLASS), ADVANCED, null, null, ADVANCED, new ItemStack(Material.GLASS),
                new ItemStack(Material.GLASS), SlimefunItems.TREE_GROWTH_ACCELERATOR, null, null, SlimefunItems.TREE_GROWTH_ACCELERATOR, new ItemStack(Material.GLASS),
                Items.MACHINE_PLATE, new ItemStack(Material.PODZOL), new ItemStack(Material.PODZOL), new ItemStack(Material.PODZOL), new ItemStack(Material.PODZOL), Items.MACHINE_PLATE,
                Items.MACHINE_PLATE, Items.INFINITE_CIRCUIT, Items.INFINITE_CORE, Items.INFINITE_CORE, Items.INFINITE_CIRCUIT, Items.MACHINE_PLATE
        }, 1800, 24, treeRecipes).register(plugin);
    }

    private static final int[] OUTPUT_SLOTS = Util.largeOutput;
    private static final int[] INPUT_SLOTS = {MenuPreset.slot1 + 27};
    private static final int STATUS_SLOT = MenuPreset.slot1;
    private static final ItemStack GROWING = new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aGrowing...");
    private static final ItemStack INPUT_PLANT = new CustomItem(Material.BLUE_STAINED_GLASS_PANE, "&9Input a plant!");

    private final EnumMap<Material, ItemStack[]> recipes;
    private final int time;
    private final int energy;

    private GrowingMachine(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                          int energy, int time, EnumMap<Material, ItemStack[]> recipes) {
        super(category, item, recipeType, recipe);
        this.energy = energy;
        this.time = time;
        this.recipes = recipes;
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu inv, @Nonnull Location l) {
        inv.dropItems(l, OUTPUT_SLOTS);
        inv.dropItems(l, INPUT_SLOTS);
    }

    @Override
    protected boolean process(@Nonnull BlockMenu menu, @Nonnull Block b, @Nonnull Config data) {
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
        for (int i : MenuPreset.slotChunk1) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk1) {
            blockMenuPreset.addItem(i + 27, MenuPreset.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : Util.largeOutputBorder) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
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
