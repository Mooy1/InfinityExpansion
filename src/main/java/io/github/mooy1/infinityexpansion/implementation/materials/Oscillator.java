package io.github.mooy1.infinityexpansion.implementation.materials;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class Oscillator extends SlimefunItem {

    private static final Map<String, Material> OSCILLATORS = new HashMap<>();

    @Nullable
    public static Material getOscillator(@Nullable ItemStack item) {
        if (item == null) {
            return null;
        }
        return OSCILLATORS.get(StackUtils.getID(item));
    }

    public Oscillator(Material material) {
        super(Categories.MAIN_MATERIALS, new SlimefunItemStack(
                material.name() + "_OSCILLATOR", 
                material,
                "&b" + StackUtils.getInternalName(new ItemStack(material)) + " Oscillator",
                "&7Place in a quarry to boost the",
                "&7chances of mining this material by 50%"
        ), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                new ItemStack(material, 64),new ItemStack(material, 64),new ItemStack(material, 64),
                new ItemStack(material, 64),new SlimefunItemStack(SlimefunItems.BLISTERING_INGOT_3, 4),new ItemStack(material, 64),
                new ItemStack(material, 64),new ItemStack(material, 64),new ItemStack(material, 64)
        });
        OSCILLATORS.put(getId(), material);
    }

}
