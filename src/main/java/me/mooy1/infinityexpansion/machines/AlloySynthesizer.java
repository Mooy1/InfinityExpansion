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

public class AlloySynthesizer extends AContainer {

    private final Type type;
    public static int BASIC_SPEED = 1;
    public static int INFINITY_SPEED = 1;
    public static int BASIC_ENERGY = 1000;
    public static int INFINITY_ENERGY = 100000;

    public AlloySynthesizer(Type type) {
        super(Categories.INFINITY_MACHINES, type.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, type.getRecipe());
        this.type = type;
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.MAGMA_BLOCK);
    }

    @Override
    public int getEnergyConsumption() {
        if (this.type == Type.BASIC) {
            return BASIC_ENERGY;
        } else if (this.type == Type.INFINITY) {
            return INFINITY_ENERGY;
        } else {
            return 0;
        }
    }

    @Override
    public int getSpeed() {
        if (this.type == Type.BASIC) {
            return BASIC_SPEED;
        } else if (this.type == Type.INFINITY) {
            return INFINITY_SPEED;
        } else {
            return 0;
        }
    }

    @Override
    public String getMachineIdentifier() {
        return null;
    }

    @Override
    public int getCapacity() {
        if (this.type == Type.BASIC) {
            return BASIC_ENERGY;
        } else if (this.type == Type.INFINITY) {
            return INFINITY_ENERGY;
        } else {
            return 0;
        }
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)

    public enum Type {
        BASIC(Items.ALLOY_SYNTHESIZER, new ItemStack[] {
            Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
            Items.MACHINE_PLATE, SlimefunItems.REINFORCED_FURNACE, Items.MACHINE_PLATE,
            Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(Items.INFINITY_ALLOY_SYNTHESIZER, new ItemStack[] {
            Items.INFINITE_INGOT, Items.MACHINE_PLATE, Items.INFINITE_INGOT,
            Items.MACHINE_PLATE, Items.ALLOY_SYNTHESIZER, Items.MACHINE_PLATE,
            Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final ItemStack[] recipe;
    }
}
