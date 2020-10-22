package me.mooy1.infinityexpansion.setup;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.Capacitor;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.AutoDisenchanter;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.AutoEnchanter;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.ChargingBench;
import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mooy1.infinityexpansion.implementation.gear.InfinityArmor;
import me.mooy1.infinityexpansion.implementation.items.Strainer;
import me.mooy1.infinityexpansion.implementation.items.StrainerBase;
import me.mooy1.infinityexpansion.implementation.storage.StorageDrive;
import me.mooy1.infinityexpansion.implementation.materials.MainMaterial;
import me.mooy1.infinityexpansion.implementation.items.InfinityWorkbench;
import me.mooy1.infinityexpansion.implementation.storage.StorageNetworkViewer;
import me.mooy1.infinityexpansion.implementation.storage.StorageUnit;
import me.mooy1.infinityexpansion.implementation.gear.InfinityTools;
import me.mooy1.infinityexpansion.implementation.gear.EnderFlame;
import me.mooy1.infinityexpansion.implementation.machines.*;
import me.mooy1.infinityexpansion.implementation.materials.*;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.lists.InfinityRecipes;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.lists.RecipeTypes;
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

    public static int ADVANCED_CHARGER_SPEED = 30;
    public static int ADVANCED_CHARGER_ENERGY = 300;

    public static int INFINITY_CHARGER_SPEED = 6000;
    public static int INFINITY_CHARGER_ENERGY = 60000;

    private ItemSetup() { }

    public static void setup(@Nonnull InfinityExpansion plugin) {

        //categories in order

        Categories.BASIC_MACHINES.register();
        Categories.INFINITY_MAIN.register();
        Categories.ADVANCED_MACHINES.register();
        Categories.INFINITY_STORAGE.register();
        Categories.INFINITY_RECIPES.register();
        Categories.INFINITY_CHEAT.register();
        Categories.INFINITY_MATERIALS.register();

        //basic

        new StrainerBase().register(plugin);
        for (Strainer.Type type : Strainer.Type.values()) {
            new Strainer(type).register(plugin);
        }
        for (VirtualFarm.Type type : VirtualFarm.Type.values()) {
            new VirtualFarm(type).register(plugin);
        }
        for (TreeGrower.Type type : TreeGrower.Type.values()) {
            new TreeGrower(type).register(plugin);
        }
        for (MaterialGenerator.Type type : MaterialGenerator.Type.values()) {
            new MaterialGenerator(type).register(plugin);
        }

        //main

        new SlimefunItem(Categories.INFINITY_MAIN, Items.INFINITY_ADDON_INFO, RecipeType.NULL, null).register(plugin);
        new InfinityWorkbench().register(plugin);

        //storage

        for (StorageUnit.Type type : StorageUnit.Type.values()) {
            new StorageUnit(type).register(plugin);
        }
        for (StorageDrive.Type type : StorageDrive.Type.values()) {
            new StorageDrive(type).register(plugin);
        }
        new StorageNetworkViewer().register(plugin);

        //machine

        for (MainMaterial.Type type : MainMaterial.Type.values()) {
            new MainMaterial(type).register(plugin);
        }
        new AdvancedAnvil().register(plugin);
        for (MachineMaterial.Type type : MachineMaterial.Type.values()) {
            new MachineMaterial(type).register(plugin);
        }
        for (Quarry.Type type : Quarry.Type.values()) {
            new Quarry(type).register(plugin);
        }
        for (EnergyGenerator.Type type : EnergyGenerator.Type.values()) {
            new EnergyGenerator(type).register(plugin);
        }
        for (VoidHarvester.Type type : VoidHarvester.Type.values()) {
            new VoidHarvester(type).register(plugin);
        }
        for (SingularityConstructor.Type type : SingularityConstructor.Type.values()) {
            new SingularityConstructor(type).register(plugin);
        }
        for (ConversionMachine.Type type : ConversionMachine.Type.values()) {
            new ConversionMachine(type).register(plugin);
        }

        new GearTransformer().register(plugin);
        new PoweredBedrock().register(plugin);
        new ItemUpdater().register(plugin);
        new InfinityReactor().register(plugin);
        new ResourceSynthesizer().register(plugin);

        //materials

        for (CompressedCobblestone.Type type : CompressedCobblestone.Type.values()) {
            new CompressedCobblestone(type).register(plugin);
        }
        for (Singularity.Type type : Singularity.Type.values()) {
            new Singularity(type).register(plugin);
        }
        new EnderEssence().register(plugin);
        new EnderEssenceResource().register();

        //gear

        for (InfinityArmor.Type type : InfinityArmor.Type.values()) {
            new InfinityArmor(type).register(plugin);
        }
        for (InfinityTools.Type type : InfinityTools.Type.values()) {
            new InfinityTools(type).register(plugin);
        }
        new EnderFlame().register(plugin);

        //sf constructors

        new Capacitor(Categories.INFINITY_CHEAT, INFINITY_CAPACITOR, Items.INFINITY_CAPACITOR,
            RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(Items.INFINITY_CAPACITOR)).register(plugin);

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

        new AutoEnchanter(Categories.INFINITY_CHEAT, Items.INFINITY_ENCHANTER,RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(Items.INFINITY_ENCHANTER)) {
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

        new AutoDisenchanter(Categories.INFINITY_CHEAT, Items.INFINITY_DISENCHANTER, RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(Items.INFINITY_DISENCHANTER)) {
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

        new ChargingBench(Categories.INFINITY_CHEAT, Items.INFINITY_CHARGER, RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(Items.INFINITY_CHARGER)) {
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