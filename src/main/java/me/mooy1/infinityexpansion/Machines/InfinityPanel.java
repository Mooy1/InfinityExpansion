package me.mooy1.infinityexpansion.Machines;

import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InfinityPanel extends SlimefunItem implements EnergyNetProvider {

    private final InfinityPanel.Panel panel;

    public InfinityPanel(Panel panel) {
        super(Categories.INFINITY_MACHINES, panel.getItem(), panel.getRecipeType(), panel.getRecipe());
        this.panel = panel;
    }

    public int getGeneratedAmount(@Nonnull Block block) {
        if (block.getLocation().add(0, 1, 0).getBlock().getLightFromSky() != 15) {
            return this.panel.getNightGenerationRate();
        } else {
            return this.panel.getDayGenerationRate();
        }
    }

    @Override
    public int getCapacity() {
        return this.panel.getCapacity();
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.GENERATOR;
    }

    @Override
    public int getGeneratedOutput(@Nonnull Location location, @Nonnull Config config) {
        return getGeneratedAmount(location.getBlock());
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Panel {

        CELESTIAL(Items.CELESTIAL_PANEL, 3_600, 0, 600_000, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGNONIUM_INGOT, Items.MAGNONIUM_INGOT, Items.MAGNONIUM_INGOT,
                Items.MACHINE_PLATE, SlimefunItems.SOLAR_GENERATOR_4, Items.MACHINE_PLATE,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        VOID(Items.VOID_PANEL, 0, 10_800, 1_800_000, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGNONIUM_INGOT, Items.MAGNONIUM_INGOT, Items.MAGNONIUM_INGOT,
                Items.MACHINE_PLATE, Items.CELESTIAL_PANEL, Items.MACHINE_PLATE,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(Items.INFINITY_PANEL, 180_000, 180_000, 30_000_000, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.CELESTIAL_PANEL, Items.VOID_PANEL, Items.CELESTIAL_PANEL,
                Items.INFINITY_INGOT, Items.INFINITE_CAPACITOR, Items.INFINITY_INGOT,
                Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT,
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final int dayGenerationRate;
        private final int nightGenerationRate;
        private final int capacity;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;

    }
}