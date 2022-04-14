package io.github.mooy1.infinityexpansion.items.quarries;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.categories.Groups;
import io.github.mooy1.infinityexpansion.items.materials.Materials;
import io.github.mooy1.infinitylib.common.StackUtils;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils;

public final class Oscillator extends SlimefunItem {

    private static final Map<String, Oscillator> OSCILLATORS = new HashMap<>();

    public final double chance;

    @Nullable
    public static Oscillator getOscillator(@Nullable ItemStack item) {
        if (item == null) {
            return null;
        }
        return OSCILLATORS.get(StackUtils.getId(item));
    }

    @Nonnull
    public static SlimefunItemStack create(Material material, double chance) {
        return new SlimefunItemStack(
                "QUARRY_OSCILLATOR_" + material.name(),
                material,
                "&b" + ItemUtils.getItemName(new ItemStack(material)) + " Oscillator",
                "&7Place in a quarry to give it",
                "&7a " + (chance * 100) + "% chance of mining this material"
        );
    }

    public Oscillator(SlimefunItemStack item, double chance) {
        super(Groups.MAIN_MATERIALS, item, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.MACHINE_PLATE, SlimefunItems.BLISTERING_INGOT_3, Materials.MACHINE_PLATE,
                SlimefunItems.BLISTERING_INGOT_3, new ItemStack(item.getType()), SlimefunItems.BLISTERING_INGOT_3,
                Materials.MACHINE_PLATE, SlimefunItems.BLISTERING_INGOT_3, Materials.MACHINE_PLATE
        });
        OSCILLATORS.put(getId(), this);
        this.chance = chance;
    }

}
