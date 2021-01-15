package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.implementation.gear.InfinityTool;
import io.github.mooy1.infinityexpansion.implementation.materials.CompressedItem;
import io.github.mooy1.infinityexpansion.implementation.materials.InfinityItem;
import io.github.mooy1.infinityexpansion.implementation.materials.MachineItem;
import io.github.mooy1.infinityexpansion.implementation.materials.SmelteryItem;
import io.github.mooy1.infinityexpansion.setup.SlimefunConstructors;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.mooy1.infinitylib.math.RandomUtils;
import io.github.mooy1.infinitylib.objects.AbstractMachine;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Mines stuff
 *
 * @author Mooy1
 */
public final class Quarry extends AbstractMachine implements RecipeDisplayItem {
    
    public static void setup(InfinityExpansion plugin) {
        new Quarry(Categories.ADVANCED_MACHINES, BASIC, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                MachineItem.MAGSTEEL_PLATE, SlimefunItems.CARBONADO_EDGED_CAPACITOR, MachineItem.MAGSTEEL_PLATE,
                new ItemStack(Material.IRON_PICKAXE), SlimefunItems.GEO_MINER, new ItemStack(Material.IRON_PICKAXE),
                MachineItem.MACHINE_CIRCUIT,MachineItem.MACHINE_CORE,MachineItem.MACHINE_CIRCUIT
        }, 300, 1, 10, new ItemStack[] {
                makeOutput(Material.COAL, 4),
                makeOutput(Material.IRON_ORE, 1),
                makeOutput(Material.COAL, 4),
                makeOutput(Material.GOLD_ORE, 1),
                makeOutput(Material.LAPIS_LAZULI, 4),
                makeOutput(Material.EMERALD, 1),
                makeOutput(Material.DIAMOND, 1),
                makeOutput(Material.REDSTONE, 4),
        }).register(plugin);
        
        new Quarry(Categories.ADVANCED_MACHINES, ADVANCED, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                MachineItem.MACHINE_PLATE, SlimefunItems.ENERGIZED_CAPACITOR, MachineItem.MACHINE_PLATE,
                new ItemStack(Material.DIAMOND_PICKAXE), BASIC, new ItemStack(Material.DIAMOND_PICKAXE),
                MachineItem.MACHINE_CIRCUIT,MachineItem.MACHINE_CORE, MachineItem.MACHINE_CIRCUIT
        }, 900, 2, 8, new ItemStack[] {
                makeOutput(Material.COAL, 8),
                makeOutput(Material.IRON_INGOT, 2),
                makeOutput(Material.NETHERRACK, 2),
                makeOutput(Material.NETHERRACK, 2),
                makeOutput(Material.QUARTZ, 8),
                makeOutput(Material.GOLD_INGOT, 2),
                makeOutput(Material.COAL, 8),
                makeOutput(Material.LAPIS_LAZULI, 8),
                makeOutput(Material.EMERALD, 2),
                makeOutput(Material.DIAMOND, 2),
                makeOutput(Material.REDSTONE, 8),
                makeOutput(Material.NETHERITE_INGOT, 1)
        }).register(plugin);
        
        new Quarry(Categories.ADVANCED_MACHINES, VOID, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                CompressedItem.VOID_INGOT, SlimefunConstructors.VOID_CAPACITOR, CompressedItem.VOID_INGOT,
                new ItemStack(Material.NETHERITE_PICKAXE), ADVANCED, new ItemStack(Material.NETHERITE_PICKAXE),
                MachineItem.MACHINE_CIRCUIT, MachineItem.MACHINE_CORE,MachineItem.MACHINE_CIRCUIT
        }, 3600, 4, 6, new ItemStack[] {
                makeOutput(Material.COAL, 16),
                makeOutput(SlimefunItems.SIFTED_ORE, 6),
                makeOutput(Material.COBBLESTONE, 4),
                makeOutput(Material.IRON_INGOT, 4),
                makeOutput(Material.NETHERRACK, 4),
                makeOutput(Material.NETHERRACK, 4),
                makeOutput(Material.QUARTZ, 16),
                makeOutput(Material.COAL, 16),
                makeOutput(Material.GOLD_INGOT, 4),
                makeOutput(Material.COAL, 16),
                makeOutput(SlimefunItems.SIFTED_ORE, 6),
                makeOutput(Material.LAPIS_LAZULI, 16),
                makeOutput(Material.EMERALD, 4),
                makeOutput(Material.DIAMOND, 4),
                makeOutput(Material.REDSTONE, 16),
                makeOutput(SlimefunItems.GOLD_24K, 4),
                makeOutput(Material.NETHERITE_INGOT, 2),
        }).register(plugin);
        
        new Quarry(Categories.INFINITY_CHEAT,INFINITY, InfinityWorkbench.TYPE, new ItemStack[] {
                null, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_PLATE, null,
                MachineItem.MACHINE_PLATE, InfinityTool.PICKAXE, InfinityItem.CIRCUIT, InfinityItem.CIRCUIT, InfinityTool.PICKAXE, MachineItem.MACHINE_PLATE,
                MachineItem.MACHINE_PLATE, CompressedItem.VOID_INGOT, InfinityItem.CORE, InfinityItem.CORE, CompressedItem.VOID_INGOT, MachineItem.MACHINE_PLATE,
                CompressedItem.VOID_INGOT, null, SmelteryItem.INFINITY, SmelteryItem.INFINITY, null, CompressedItem.VOID_INGOT,
                CompressedItem.VOID_INGOT, null, SmelteryItem.INFINITY, SmelteryItem.INFINITY, null, CompressedItem.VOID_INGOT,
                CompressedItem.VOID_INGOT, null, SmelteryItem.INFINITY, SmelteryItem.INFINITY, null, CompressedItem.VOID_INGOT
        }, 36000, 16, 4, new ItemStack[] {
                makeOutput(Material.COAL, 64),
                makeOutput(Material.IRON_INGOT, 16),
                makeOutput(Material.NETHERRACK, 16),
                makeOutput(Material.NETHERRACK, 16),
                makeOutput(Material.QUARTZ, 64),
                makeOutput(Material.COAL, 64),
                makeOutput(Material.GOLD_INGOT, 16),
                makeOutput(SlimefunItems.MAGNESIUM_INGOT, 16),
                makeOutput(SlimefunItems.COPPER_INGOT, 16),
                makeOutput(SlimefunItems.ZINC_INGOT, 16),
                makeOutput(SlimefunItems.TIN_INGOT, 16),
                makeOutput(SlimefunItems.ALUMINUM_INGOT, 16),
                makeOutput(SlimefunItems.SILVER_INGOT, 16),
                makeOutput(SlimefunItems.LEAD_INGOT, 16),
                makeOutput(Material.LAPIS_LAZULI, 64),
                makeOutput(Material.EMERALD, 16),
                makeOutput(Material.COAL, 64),
                makeOutput(Material.DIAMOND, 16),
                makeOutput(Material.REDSTONE, 64),
                makeOutput(SlimefunItems.GOLD_24K, 16),
                makeOutput(Material.NETHERITE_INGOT, 8),
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

    private final ItemStack cobble;
    private final int chance;
    private final ItemStack[] outputs;
    
    private Quarry(Category category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe, int energy, int speed, int chance, ItemStack[] outputs) {
        super(category, item, type, recipe, STATUS_SLOT, energy);
        this.cobble = new ItemStack(Material.COBBLESTONE, speed);
        this.chance = chance;
        this.outputs = outputs;

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), OUTPUT_SLOTS);
            }

            return true;
        });
    }

    @Override
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        }
        return new int[0];
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {

    }

    @Override
    public boolean process(@Nonnull Block b, @Nonnull BlockMenu inv) {

        if (getCharge(b.getLocation()) < this.energy) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughEnergy);
            }
            return false;
        }

        ItemStack outputItem;

        if (RandomUtils.chanceIn(this.chance)) {
            outputItem = RandomUtils.randomOutput(this.outputs);
            Material outputType = outputItem.getType();
            if (b.getWorld().getEnvironment() != World.Environment.NETHER && (outputType == Material.QUARTZ || outputType == Material.NETHERITE_INGOT || outputType == Material.NETHERRACK)) {
                outputItem = this.cobble.clone();
            }
        } else {
            outputItem = this.cobble.clone();
        }

        if (!inv.fits(outputItem, OUTPUT_SLOTS)) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughRoom);
            }
            return false;
        }

        if (inv.hasViewer()) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aMining..."));
        }
        inv.pushItem(outputItem, OUTPUT_SLOTS);
        return true;
    }

    @Override
    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i = 0 ; i < 4 ; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i = 5 ; i < 9 ; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i = 45 ; i < 54 ; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.loadingItemRed, ChestMenuUtils.getEmptyClickHandler());
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
        for (ItemStack item : this.outputs) {
            items.add(null);
            items.add(item);
        }

        return items;
    }

    @Nonnull
    @Override
    public String getRecipeSectionLabel(@Nonnull Player p) {
        return "&7Mines:";
    }
    
    private static ItemStack makeOutput(Material material, int amount) {
        return new ItemStack(material, (int) (InfinityExpansion.getVanillaScale() * Math.ceil((float) amount / 2)));
    }

    private static ItemStack makeOutput(SlimefunItemStack stack, int amount) {
        return new SlimefunItemStack(stack, amount);
    }

}
