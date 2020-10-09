package me.mooy1.infinityexpansion;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.Capacitor;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.AutoDisenchanter;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.AutoEnchanter;
import me.mooy1.infinityexpansion.basics.MainMaterials;
import me.mooy1.infinityexpansion.materials.OtherMaterials;
import me.mooy1.infinityexpansion.machines.InfinityForge;
import me.mooy1.infinityexpansion.machines.IngotForge;
import me.mooy1.infinityexpansion.basics.StorageUnit;
import me.mooy1.infinityexpansion.gear.InfinityGear;
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

    private ItemSetup() { }

    public static void setup(@Nonnull InfinityExpansion plugin) {

        //basics

        new SlimefunItem(Categories.INFINITY_BASICS, Items.ADDON_INFO, RecipeType.NULL, null).register(plugin);

        for (MainMaterials.Type type : MainMaterials.Type.values()) {
            new MainMaterials(type).register(plugin);
        }

        for (StorageUnit.Tier tier : StorageUnit.Tier.values()) {
            new StorageUnit(tier).register(plugin);
        }

        //add machines

        for (MachineMaterials.Type type : MachineMaterials.Type.values()) {
            new MachineMaterials(type).register(plugin);
        }

        for (AlloySynthesizer.Type type : AlloySynthesizer.Type.values()) {
            new AlloySynthesizer(type).register(plugin);
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

        for (AdvancedAnvil.Type type : AdvancedAnvil.Type.values()) {
            new AdvancedAnvil(type).register(plugin);
        }

        new IngotForge().register(plugin);
        new PoweredBedrock().register(plugin);
        new ItemUpdater().register(plugin);
        new InfinityForge().register(plugin);


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

        for (InfinityGear.Type type : InfinityGear.Type.values()) {
            new InfinityGear(type).register(plugin);
        }

        new EnderFlame().register(plugin);

        for (MagnoniumGear.Type type : MagnoniumGear.Type.values()) {
            new MagnoniumGear(type).register(plugin);
        }

        //Sf constructors

        new Capacitor(Categories.INFINITY_MACHINES, INFINITY_CAPACITOR, Items.INFINITY_CAPACITOR,
            RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.INFINITE_INGOT, Items.INFINITE_INGOT, Items.INFINITE_INGOT,
            Items.INFINITE_INGOT, SlimefunItems.ENERGIZED_CAPACITOR, Items.INFINITE_INGOT,
            Items.INFINITE_INGOT, Items.INFINITE_INGOT, Items.INFINITE_INGOT
        }).register(plugin);

        new AutoEnchanter(Categories.INFINITY_MACHINES, Items.ADVANCED_ENCHANTER,RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
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

        new AutoDisenchanter(Categories.INFINITY_MACHINES, Items.ADVANCED_DISENCHANTER,RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
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



        new AutoDisenchanter(Categories.INFINITY_MACHINES, Items.INFINITY_DISENCHANTER,RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
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

    }
}