package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class IngotSynthesizer extends AContainer {

    private final Type type;

    public IngotSynthesizer(Type type) {
        super(Categories.INFINITY_MACHINES, type.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, type.getRecipe());
        this.type = type;
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.MAGMA_BLOCK);
    }

    @Override
    public int getEnergyConsumption() {
        return type.getEnergyConsumption();
    }

    @Override
    public int getSpeed() {
        return type.getSpeed();
    }

    @Override
    public String getMachineIdentifier() {
        return null;
    }

    @Override
    public int getCapacity() {
        return type.getEnergyConsumption()*2;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)

    public enum Type {
        BASIC(Items.INGOT_SYNTHESIZER, 1, 1_000, new ItemStack[] {
            Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
            Items.MACHINE_PLATE, SlimefunItems.CARBONADO_EDGED_FURNACE, Items.MACHINE_PLATE,
            Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(Items.INFINITY_INGOT_SYNTHESIZER, 10, 100_000, new ItemStack[] {
            Items.INFINITE_INGOT, Items.MACHINE_PLATE, Items.INFINITE_INGOT,
            Items.MACHINE_PLATE, Items.INGOT_SYNTHESIZER, Items.MACHINE_PLATE,
            Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final int speed;
        private final int energyConsumption;
        private final ItemStack[] recipe;
    }
}
