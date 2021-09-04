package io.github.mooy1.infinityexpansion.items.materials;

import javax.annotation.Nonnull;

import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;

/**
 * Ender essence geo-resource item
 *
 * @author Mooy1
 */
public final class EnderEssence extends SlimefunItem implements NotPlaceable, GEOResource {

    private final NamespacedKey key;

    public EnderEssence(ItemGroup category, SlimefunItemStack item, NamespacedKey key) {
        super(category, item, RecipeType.GEO_MINER, new ItemStack[9]);
        this.key = key;
        register();
    }

    @Override
    public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
        if (environment == World.Environment.THE_END) {
            return 12;
        }
        if (biome == Biome.THE_VOID) {
            return 8;
        }
        if (environment == World.Environment.NETHER) {
            return 4;
        }
        return 0;
    }

    @Nonnull
    @Override
    public NamespacedKey getKey() {
        return this.key;
    }

    @Override
    public int getMaxDeviation() {
        return 4;
    }

    @Nonnull
    @Override
    public String getName() {
        return getItemName();
    }

    @Override
    public boolean isObtainableFromGEOMiner() {
        return true;
    }

}