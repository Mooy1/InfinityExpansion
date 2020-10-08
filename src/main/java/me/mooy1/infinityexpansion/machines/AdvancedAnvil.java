package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class AdvancedAnvil extends SlimefunItem implements EnergyNetComponent, InventoryBlock {

    private final Type type;

    public static int ADVANCED_ENERGY = 100_000;
    public static int INFINITY_ENERGY = 1_000_000;

    public AdvancedAnvil(Type type) {
        super(Categories.INFINITY_MACHINES, type.getItem(), type.getRecipetype(), type.getRecipe());
        this.type = type;
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        if (this.type == Type.ADVANCED) {
            return ADVANCED_ENERGY;

        } else if (this.type == Type.INFINITY) {
            return INFINITY_ENERGY;

        } else {
            return 0;
        }
    }

    @Override
    public int[] getInputSlots() {
        return new int[0];
    }

    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        ADVANCED(Items.ADVANCED_ANVIL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE,
                Items.MACHINE_PLATE, new ItemStack(Material.ANVIL), Items.MACHINE_PLATE,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(Items.INFINITY_ANVIL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.INFINITE_INGOT, Items.INFINITE_INGOT, Items.INFINITE_INGOT,
                Items.VOID_INGOT, Items.ADVANCED_ANVIL, Items.VOID_INGOT,
                Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final RecipeType recipetype;
        private final ItemStack[] recipe;
    }
}
