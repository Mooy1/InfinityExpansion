package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.Categories;
import io.github.mooy1.infinityexpansion.implementation.SlimefunExtension;
import io.github.mooy1.infinityexpansion.implementation.abstracts.AbstractMachine;
import io.github.mooy1.infinityexpansion.implementation.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.implementation.gear.InfinityTool;
import io.github.mooy1.infinityexpansion.implementation.materials.Items;
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
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Mines stuff
 *
 * @author Mooy1
 */
public final class Quarry extends AbstractMachine implements RecipeDisplayItem {
    
    public static void setup(InfinityExpansion plugin) {
        new Quarry(Categories.ADVANCED_MACHINES, BASIC, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL_PLATE, SlimefunItems.CARBONADO_EDGED_CAPACITOR, Items.MAGSTEEL_PLATE,
                new ItemStack(Material.IRON_PICKAXE), SlimefunItems.GEO_MINER, new ItemStack(Material.IRON_PICKAXE),
                Items.MACHINE_CIRCUIT,Items.MACHINE_CORE,Items.MACHINE_CIRCUIT
        }, 300, 1, 8, new ItemStack[] {
                new ItemStack(Material.COAL, 4),
                new ItemStack(Material.IRON_ORE, 1),
                new ItemStack(Material.COAL, 4),
                new ItemStack(Material.GOLD_ORE, 1),
                new ItemStack(Material.LAPIS_LAZULI, 4),
                new ItemStack(Material.EMERALD, 1),
                new ItemStack(Material.DIAMOND, 1),
                new ItemStack(Material.REDSTONE, 4),
        }).register(plugin);
        
        new Quarry(Categories.ADVANCED_MACHINES, ADVANCED, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MACHINE_PLATE, SlimefunItems.ENERGIZED_CAPACITOR, Items.MACHINE_PLATE,
                new ItemStack(Material.DIAMOND_PICKAXE), BASIC, new ItemStack(Material.DIAMOND_PICKAXE),
                Items.MACHINE_CIRCUIT,Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }, 900, 2, 6, new ItemStack[] {
                new ItemStack(Material.COAL, 8),
                new ItemStack(Material.IRON_INGOT, 2),
                new ItemStack(Material.NETHERRACK, 2),
                new ItemStack(Material.NETHERRACK, 2),
                new ItemStack(Material.QUARTZ, 8),
                new ItemStack(Material.GOLD_INGOT, 2),
                new ItemStack(Material.COAL, 4),
                new ItemStack(Material.LAPIS_LAZULI, 8),
                new ItemStack(Material.EMERALD, 2),
                new ItemStack(Material.DIAMOND, 2),
                new ItemStack(Material.REDSTONE, 8),
                new ItemStack(Material.NETHERITE_INGOT, 1)
        }).register(plugin);
        
        new Quarry(Categories.ADVANCED_MACHINES, VOID, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.VOID_INGOT, SlimefunExtension.VOID_CAPACITOR, Items.VOID_INGOT,
                new ItemStack(Material.NETHERITE_PICKAXE), ADVANCED, new ItemStack(Material.NETHERITE_PICKAXE),
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE,Items.MACHINE_CIRCUIT
        }, 3600, 4, 4, new ItemStack[] {
                new ItemStack(Material.COAL, 16),
                new CustomItem(SlimefunItems.SIFTED_ORE, 6),
                new ItemStack(Material.COBBLESTONE, 4),
                new ItemStack(Material.IRON_INGOT, 4),
                new ItemStack(Material.NETHERRACK, 4),
                new ItemStack(Material.NETHERRACK, 4),
                new ItemStack(Material.QUARTZ, 16),
                new ItemStack(Material.COAL, 16),
                new ItemStack(Material.GOLD_INGOT, 4),
                new ItemStack(Material.COAL, 16),
                new CustomItem(SlimefunItems.SIFTED_ORE, 6),
                new ItemStack(Material.LAPIS_LAZULI, 16),
                new ItemStack(Material.EMERALD, 4),
                new ItemStack(Material.DIAMOND, 4),
                new ItemStack(Material.REDSTONE, 16),
                new CustomItem(SlimefunItems.GOLD_24K, 4),
                new ItemStack(Material.NETHERITE_INGOT, 2),
        }).register(plugin);
        
        new Quarry(Categories.INFINITY_CHEAT,INFINITY, InfinityWorkbench.TYPE, new ItemStack[] {
                null, Items.MACHINE_PLATE, Items.MACHINE_PLATE, Items.MACHINE_PLATE, Items.MACHINE_PLATE, null,
                Items.MACHINE_PLATE, InfinityTool.PICKAXE, Items.INFINITE_CIRCUIT, Items.INFINITE_CIRCUIT, InfinityTool.PICKAXE, Items.MACHINE_PLATE,
                Items.MACHINE_PLATE, Items.VOID_INGOT, Items.INFINITE_CORE, Items.INFINITE_CORE, Items.VOID_INGOT, Items.MACHINE_PLATE,
                Items.VOID_INGOT, null, Items.INFINITY, Items.INFINITY, null, Items.VOID_INGOT,
                Items.VOID_INGOT, null, Items.INFINITY, Items.INFINITY, null, Items.VOID_INGOT,
                Items.VOID_INGOT, null, Items.INFINITY, Items.INFINITY, null, Items.VOID_INGOT
        }, 36000, 24, 2, new ItemStack[] {
                new ItemStack(Material.COAL, 64),
                new ItemStack(Material.IRON_INGOT, 24),
                new ItemStack(Material.NETHERRACK, 24),
                new ItemStack(Material.NETHERRACK, 24),
                new ItemStack(Material.QUARTZ, 64),
                new ItemStack(Material.COAL, 64),
                new ItemStack(Material.GOLD_INGOT, 24),
                new CustomItem(SlimefunItems.MAGNESIUM_INGOT, 24),
                new CustomItem(SlimefunItems.COPPER_INGOT, 24),
                new CustomItem(SlimefunItems.ZINC_INGOT, 24),
                new CustomItem(SlimefunItems.TIN_INGOT, 24),
                new CustomItem(SlimefunItems.ALUMINUM_INGOT, 24),
                new CustomItem(SlimefunItems.SILVER_INGOT, 24),
                new CustomItem(SlimefunItems.LEAD_INGOT, 24),
                new ItemStack(Material.LAPIS_LAZULI, 64),
                new ItemStack(Material.EMERALD, 24),
                new ItemStack(Material.COAL, 64),
                new ItemStack(Material.DIAMOND, 24),
                new ItemStack(Material.REDSTONE, 64),
                new CustomItem(SlimefunItems.GOLD_24K, 24),
                new ItemStack(Material.NETHERITE_INGOT, 16),
        }).register(plugin);
    }
    
    public static final SlimefunItemStack BASIC = new SlimefunItemStack(
            "BASIC_QUARRY",
            Material.CHISELED_SANDSTONE,
            "&9Basic Quarry",
            "&7Automatically mines vanilla overworld ores",
            "",
            LorePreset.speed(1),
            LorePreset.energyPerSecond(300)
    );
    public static final SlimefunItemStack ADVANCED = new SlimefunItemStack(
            "ADVANCED_QUARRY",
            Material.CHISELED_RED_SANDSTONE,
            "&cAdvanced Quarry",
            "&7Smelts vanilla ores and can mine nether ores",
            "",
            LorePreset.speed(2),
            LorePreset.energyPerSecond(900)
    );
    public static final SlimefunItemStack VOID = new SlimefunItemStack(
            "VOID_QUARRY",
            Material.CHISELED_NETHER_BRICKS,
            "&8Void Quarry",
            "&7Can mine sifted ores or 24 karat gold occasionally",
            "",
            LorePreset.speed(4),
            LorePreset.energyPerSecond(3600)
    );
    public static final SlimefunItemStack INFINITY = new SlimefunItemStack(
            "INFINITY_QUARRY",
            Material.CHISELED_POLISHED_BLACKSTONE,
            "&bInfinity Quarry",
            "&7Can mine and smelt Slimefun ingots",
            "",
            LorePreset.speed(16),
            LorePreset.energyPerSecond(36000)
    );
    
    private static final int[] OUTPUT_SLOTS = {
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44
    };
    private static final int STATUS_SLOT = 4;

    private static final int INTERVAL = (int) (10 * InfinityExpansion.inst().getDifficulty());
    private static final boolean ALLOW_NETHER_IN_OVERWORLD = InfinityExpansion.inst().getConfig().getBoolean("balance-options.quarry-nether-materials-in-overworld");

    private final ItemStack cobble;
    private final int chance;
    private final int energy;
    private final ItemStack[] outputs;
    
    private Quarry(Category category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe, int energy, int speed, int chance, ItemStack[] outputs) {
        super(category, item, type, recipe);
        this.cobble = new ItemStack(Material.COBBLESTONE, speed);
        this.chance = chance;
        this.outputs = outputs;
        this.energy = energy;
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu menu, @Nonnull Location l) {
        menu.dropItems(l, OUTPUT_SLOTS);
    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        super.setupMenu(blockMenuPreset);
        for (int i = 0 ; i < 4 ; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i = 5 ; i < 9 ; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i = 45 ; i < 54 ; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
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

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        }
        return new int[0];
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {

    }
    
    @Override
    public int getCapacity() {
        return this.energy * 2;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();

        items.add(this.cobble);
        items.addAll(Arrays.asList(this.outputs));

        return items;
    }

    @Nonnull
    @Override
    public String getRecipeSectionLabel(@Nonnull Player p) {
        return "&7Mines:";
    }
    
    private static final ItemStack MINING = new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aMining...");
    
    @Override
    protected boolean process(@Nonnull BlockMenu inv, @Nonnull Block b, @Nonnull Config data) {
        if ((InfinityExpansion.inst().getGlobalTick() % INTERVAL) != 0) {
            return true;
        }

        ItemStack outputItem;

        Random random = ThreadLocalRandom.current();
        
        if (random.nextInt(this.chance) == 0) {
            outputItem = this.outputs[random.nextInt(this.outputs.length)];
            Material outputType = outputItem.getType();
            if (!ALLOW_NETHER_IN_OVERWORLD && b.getWorld().getEnvironment() != World.Environment.NETHER &&
                    (outputType == Material.QUARTZ || outputType == Material.NETHERITE_INGOT || outputType == Material.NETHERRACK)
            ) {
                outputItem = this.cobble;
            }
        } else {
            outputItem = this.cobble;
        }

        if (!inv.fits(outputItem, OUTPUT_SLOTS)) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughRoom);
            }
            return false;
        }

        if (inv.hasViewer()) {
            inv.replaceExistingItem(STATUS_SLOT, MINING);
        }
        inv.pushItem(outputItem.clone(), OUTPUT_SLOTS);
        return true;
    }

}
