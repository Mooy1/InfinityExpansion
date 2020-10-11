package me.mooy1.infinityexpansion.setup;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.Capacitor;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.AutoDisenchanter;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.AutoEnchanter;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.ChargingBench;
import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.gear.InfinityArmor;
import me.mooy1.infinityexpansion.storage.StorageDrive;
import me.mooy1.infinityexpansion.materials.EndgameMaterials;
import me.mooy1.infinityexpansion.materials.OtherMaterials;
import me.mooy1.infinityexpansion.machines.InfinityForge;
import me.mooy1.infinityexpansion.storage.StorageUnit;
import me.mooy1.infinityexpansion.gear.InfinityTools;
import me.mooy1.infinityexpansion.gear.MagnoniumGear;
import me.mooy1.infinityexpansion.gear.EnderFlame;
import me.mooy1.infinityexpansion.machines.*;
import me.mooy1.infinityexpansion.materials.*;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public final class ItemSetup {
    
    public static int INFINITY_CAPACITOR = 1600000000;

    public static int ADVANCED_EN_SPEED = 5;
    public static int ADVANCED_EN_ENERGY = 600;

    public static int ADVANCED_DIS_SPEED = 5;
    public static int ADVANCED_DIS_ENERGY = 600;

    public static int INFINITY_EN_SPEED = 75;
    public static int INFINITY_EN_ENERGY = 60000;

    public static int INFINITY_DIS_SPEED = 90;
    public static int INFINITY_DIS_ENERGY = 60000;

    public static int ADVANCED_CHARGER_SPEED = 25;
    public static int ADVANCED_CHARGER_ENERGY = 250;

    public static int INFINITY_CHARGER_SPEED = 5000;
    public static int INFINITY_CHARGER_ENERGY = 50000;

    private ItemSetup() { }

    public static void setup(@Nonnull InfinityExpansion plugin) {

        //basics

        new SlimefunItem(Categories.INFINITY_MAIN, Items.ADDON_INFO, RecipeType.NULL, null).register(plugin);

        for (EndgameMaterials.Type type : EndgameMaterials.Type.values()) {
            new EndgameMaterials(type).register(plugin);
        }
        for (StorageUnit.Type type : StorageUnit.Type.values()) {
            new StorageUnit(type).register(plugin);
        }
        for (StorageDrive.Type type : StorageDrive.Type.values()) {
            new StorageDrive(type).register(plugin);
        }

        //add machines

        for (MachineMaterials.Type type : MachineMaterials.Type.values()) {
            new MachineMaterials(type).register(plugin);
        }
        for (Quarry.Type type : Quarry.Type.values()) {
            new Quarry(type).register(plugin);
        }
        for (Generators.Type type : Generators.Type.values()) {
            new Generators(type).register(plugin);
        }
        for (VoidHarvester.Type type : VoidHarvester.Type.values()) {
            new VoidHarvester(type).register(plugin);
        }
        for (SingularityConstructor.Type type : SingularityConstructor.Type.values()) {
            new SingularityConstructor(type).register(plugin);
        }

        new AdvancedAnvil().register(plugin);
        new PoweredBedrock().register(plugin);
        new ItemUpdater().register(plugin);
        new InfinityForge().register(plugin);
        new InfinityReactor().register(plugin);
        new ResourceSynthesizer().register(plugin);

        //add materials

        for (CompressedCobblestone.Type type : CompressedCobblestone.Type.values()) {
            new CompressedCobblestone(type).register(plugin);
        }
        for (Singularities.Type type : Singularities.Type.values()) {
            new Singularities(type).register(plugin);
        }
        for (OtherMaterials.Type type : OtherMaterials.Type.values()) {
            new OtherMaterials(type).register(plugin);
        }
        new EnderEssence().register(plugin);
        new EnderEssenceResource().register();
        for (RecipeItems.Type type : RecipeItems.Type.values()) {
            new RecipeItems(type).register(plugin);
        }

        //add gear

        for (InfinityArmor.Type type : InfinityArmor.Type.values()) {
            new InfinityArmor(type).register(plugin);
        }
        for (InfinityTools.Type type : InfinityTools.Type.values()) {
            new InfinityTools(type).register(plugin);
        }
        new EnderFlame().register(plugin);
        for (MagnoniumGear.Type type : MagnoniumGear.Type.values()) {
            new MagnoniumGear(type).register(plugin);
        }

        //sf constructors

        new Capacitor(Categories.INFINITY_MACHINES, INFINITY_CAPACITOR, Items.INFINITY_CAPACITOR,
            RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.INFINITE_INGOT, Items.INFINITE_INGOT, Items.INFINITE_INGOT,
            Items.INFINITE_INGOT, SlimefunItems.ENERGIZED_CAPACITOR, Items.INFINITE_INGOT,
            Items.INFINITE_INGOT, Items.INFINITE_INGOT, Items.INFINITE_INGOT
        }).register(plugin);

        new AutoEnchanter(Categories.ADVANCED_MACHINES, Items.ADVANCED_ENCHANTER,RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                Items.MAGSTEEL_PLATE, SlimefunItems.AUTO_ENCHANTER, Items.MAGSTEEL_PLATE,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }) {
            @Override
            public ItemStack getProgressBar() {
                return new ItemStack(Material.NETHERITE_CHESTPLATE);
            }
            @Override
            public int getEnergyConsumption() {
                return ADVANCED_EN_ENERGY;
            }
            @Override
            public int getCapacity() {
                return ADVANCED_EN_ENERGY;
            }
            @Override
            public int getSpeed() {
                return ADVANCED_EN_SPEED;
            }

        }.register(plugin);

        new AutoDisenchanter(Categories.ADVANCED_MACHINES, Items.ADVANCED_DISENCHANTER,RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                Items.MAGSTEEL_PLATE, SlimefunItems.AUTO_DISENCHANTER, Items.MAGSTEEL_PLATE,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }) {
            @Override
            public ItemStack getProgressBar() {
                return new ItemStack(Material.ENCHANTED_BOOK);
            }
            @Override
            public int getEnergyConsumption() {
                return ADVANCED_DIS_ENERGY;
            }
            @Override
            public int getCapacity() {
                return ADVANCED_DIS_ENERGY;
            }
            @Override
            public int getSpeed() {
                return ADVANCED_DIS_SPEED;
            }


        }.register(plugin);

        new AutoEnchanter(Categories.INFINITY_MACHINES, Items.INFINITY_ENCHANTER,RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.INFINITE_INGOT, Items.VOID_INGOT, Items.INFINITE_INGOT,
                Items.MAGNONIUM_INGOT, Items.ADVANCED_ENCHANTER, Items.MAGNONIUM_INGOT,
                Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT
        }) {
            @Override
            public ItemStack getProgressBar() {
                return new ItemStack(Material.NETHERITE_CHESTPLATE);
            }
            @Override
            public int getEnergyConsumption() {
                return INFINITY_EN_ENERGY;
            }
            @Override
            public int getCapacity() {
                return INFINITY_EN_ENERGY;
            }
            @Override
            public int getSpeed() {
                return INFINITY_EN_SPEED;
            }

        }.register(plugin);



        new AutoDisenchanter(Categories.INFINITY_MACHINES, Items.INFINITY_DISENCHANTER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.INFINITE_INGOT, Items.VOID_INGOT, Items.INFINITE_INGOT,
                Items.MAGNONIUM_INGOT, Items.ADVANCED_DISENCHANTER, Items.MAGNONIUM_INGOT,
                Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT
        }) {
            @Override
            public ItemStack getProgressBar() {
                return new ItemStack(Material.ENCHANTED_BOOK);
            }
            @Override
            public int getEnergyConsumption() {
                return INFINITY_DIS_ENERGY;
            }
            @Override
            public int getCapacity() {
                return INFINITY_DIS_ENERGY;
            }
            @Override
            public int getSpeed() {
                return INFINITY_DIS_SPEED;
            }

        }.register(plugin);

        new ChargingBench(Categories.ADVANCED_MACHINES, Items.ADVANCED_CHARGER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL_PLATE, Items.MACHINE_CIRCUIT, Items.MAGSTEEL_PLATE,
                Items.MACHINE_CIRCUIT, SlimefunItems.CHARGING_BENCH, Items.MACHINE_CIRCUIT,
                Items.MAGSTEEL_PLATE, Items.MACHINE_CORE, Items.MAGSTEEL_PLATE,
        }) {
            @Override
            public int getEnergyConsumption() {
                return ADVANCED_CHARGER_ENERGY;
            }
            @Override
            public int getCapacity() {
                return ADVANCED_CHARGER_ENERGY;
            }
            @Override
            public int getSpeed() {
                return ADVANCED_CHARGER_SPEED;
            }

        }.register(plugin);

        new ChargingBench(Categories.INFINITY_MACHINES, Items.INFINITY_CHARGER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MACHINE_PLATE, Items.INFINITE_MACHINE_CIRCUIT, Items.MACHINE_PLATE,
                Items.INFINITE_MACHINE_CIRCUIT, Items.ADVANCED_CHARGER, Items.INFINITE_MACHINE_CIRCUIT,
                Items.MACHINE_PLATE, Items.INFINITE_MACHINE_CIRCUIT, Items.MACHINE_PLATE,
        }) {
            @Override
            public int getEnergyConsumption() {
                return INFINITY_CHARGER_ENERGY;
            }
            @Override
            public int getCapacity() {
                return INFINITY_CHARGER_ENERGY;
            }
            @Override
            public int getSpeed() {
                return INFINITY_CHARGER_SPEED;
            }

        }.register(plugin);

    }
}