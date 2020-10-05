package me.mooy1.infinityexpansion;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.Capacitor;
import me.mooy1.infinityexpansion.blocks.InfinityForge;
import me.mooy1.infinityexpansion.blocks.StorageUnit;
import me.mooy1.infinityexpansion.gear.InfinityGear;
import me.mooy1.infinityexpansion.gear.MagnoniumGear;
import me.mooy1.infinityexpansion.gear.EnderFlame;
import me.mooy1.infinityexpansion.machines.*;
import me.mooy1.infinityexpansion.materials.*;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public final class ItemSetup {

    private ItemSetup() { }

    public static void setup(@Nonnull InfinityExpansion plugin) {

        //add machines

        for (MachineMaterials.Type type : MachineMaterials.Type.values()) {
            new MachineMaterials(type).register(plugin);
        }

        new AdvancedEnchanter(AdvancedEnchanter.Type.BASIC).register(plugin);
        new AdvancedDisenchanter(AdvancedDisenchanter.Type.BASIC).register(plugin);
        new AdvancedEnchanter(AdvancedEnchanter.Type.INFINITY).register(plugin);
        new AdvancedDisenchanter(AdvancedDisenchanter.Type.INFINITY).register(plugin);

        for (Quarry.Type tier : Quarry.Type.values()) {
            new Quarry(tier).register(plugin);
        }

        new InfinityForge().register(plugin);

        for (InfinityPanel.Type type : InfinityPanel.Type.values()) {
            new InfinityPanel(type).register(plugin);
        }

        for (VoidHarvester.Type type : VoidHarvester.Type.values()) {
            new VoidHarvester(type).register(plugin);
        }

        for (SingularityConstructor.Type type : SingularityConstructor.Type.values()) {
            new SingularityConstructor(type).register(plugin);
        }

        for (StorageUnit.Tier tier : StorageUnit.Tier.values()) {
            new StorageUnit(tier).register(plugin);
        }

        for (AdvancedAnvil.Type type : AdvancedAnvil.Type.values()) {
            new AdvancedAnvil(type).register(plugin);
        }

        //add materials

        for (CompressedCobblestone.Type type : CompressedCobblestone.Type.values()) {
            new CompressedCobblestone(type).register(plugin);
        }

        for (EndgameMaterials.Type type : EndgameMaterials.Type.values()) {
            new EndgameMaterials(type).register(plugin);
        }

        for (Singularities.Type type : Singularities.Type.values()) {
            new Singularities(type).register(plugin);
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

        //add geominer recipe

        new EnderEssence().register(plugin);

        //Slimefun constructors

        new Capacitor(Categories.INFINITY_MACHINES, 1_600_000_000, Items.INFINITY_CAPACITOR,
            RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.INFINITE_INGOT, Items.INFINITE_INGOT, Items.INFINITE_INGOT,
            Items.INFINITE_INGOT, SlimefunItems.ENERGIZED_CAPACITOR, Items.INFINITE_INGOT,
            Items.INFINITE_INGOT, Items.INFINITE_INGOT, Items.INFINITE_INGOT
        }).register(plugin);

    }
}