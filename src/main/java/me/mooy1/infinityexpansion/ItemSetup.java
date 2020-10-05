package me.mooy1.infinityexpansion;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.Capacitor;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.AutoDisenchanter;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.AutoEnchanter;
import me.mooy1.infinityexpansion.basics.MainItems;
import me.mooy1.infinityexpansion.materials.MainMaterials;
import me.mooy1.infinityexpansion.machines.InfinityForge;
import me.mooy1.infinityexpansion.machines.IngotForge;
import me.mooy1.infinityexpansion.basics.StorageUnit;
import me.mooy1.infinityexpansion.gear.InfinityGear;
import me.mooy1.infinityexpansion.gear.MagnoniumGear;
import me.mooy1.infinityexpansion.gear.EnderFlame;
import me.mooy1.infinityexpansion.machines.*;
import me.mooy1.infinityexpansion.materials.*;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public final class ItemSetup {

    private ItemSetup() { }

    public static void setup(@Nonnull InfinityExpansion plugin) {

        //basics

        for (MainItems.Type type : MainItems.Type.values()) {
            new MainItems(type).register(plugin);
        }

        for (StorageUnit.Tier tier : StorageUnit.Tier.values()) {
            new StorageUnit(tier).register(plugin);
        }

        //add machines

        new IngotForge().register(plugin);

        new InfinityForge().register(plugin);

        for (MachineMaterials.Type type : MachineMaterials.Type.values()) {
            new MachineMaterials(type).register(plugin);
        }

        for (Quarry.Type tier : Quarry.Type.values()) {
            new Quarry(tier).register(plugin);
        }

        for (InfinityPanel.Type type : InfinityPanel.Type.values()) {
            new InfinityPanel(type).register(plugin);
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

        //add materials

        for (CompressedCobblestone.Type type : CompressedCobblestone.Type.values()) {
            new CompressedCobblestone(type).register(plugin);
        }

        for (Singularities.Type type : Singularities.Type.values()) {
            new Singularities(type).register(plugin);
        }

        for (MainMaterials.Type type : MainMaterials.Type.values()) {
            new MainMaterials(type).register(plugin);
        }

        new EnderEssence().register();

        //add gear

        for (InfinityGear.Type type : InfinityGear.Type.values()) {
            new InfinityGear(type).register(plugin);
        }

        new EnderFlame().register(plugin);

        for (MagnoniumGear.Type type : MagnoniumGear.Type.values()) {
            new MagnoniumGear(type).register(plugin);
        }

        //Slimefun constructors

        new Capacitor(Categories.INFINITY_MACHINES, 1_600_000_000, Items.INFINITY_CAPACITOR,
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
                return 600;
            }
            @Override
            public int getCapacity() {
                return 600;
            }
            @Override
            public int getSpeed() {
                return 5;
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
                return 600;
            }
            @Override
            public int getCapacity() {
                return 600;
            }
            @Override
            public int getSpeed() {
                return 5;
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
                return 60000;
            }
            @Override
            public int getCapacity() {
                return 60000;
            }
            @Override
            public int getSpeed() {
                return 75;
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
                return 60000;
            }
            @Override
            public int getCapacity() {
                return 60000;
            }
            @Override
            public int getSpeed() {
                return 90;
            }

        }.register(plugin);

    }
}