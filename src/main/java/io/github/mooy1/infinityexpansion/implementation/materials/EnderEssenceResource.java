package io.github.mooy1.infinityexpansion.implementation.materials;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * ender essence geo-resource
 *
 * @author Mooy1
 */
public class EnderEssenceResource implements GEOResource {

    private static final NamespacedKey key = new NamespacedKey(InfinityExpansion.getInstance(), "ender_essence");

    @Override
    public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
        if (environment == World.Environment.THE_END) {
            return 1;
        }
        if (biome == Biome.THE_VOID) {
            return 1;
        } else {
            return 0;
        }
    }

    @Nonnull
    @Override
    public NamespacedKey getKey() {
        return key;
    }

    @Override
    public int getMaxDeviation() {
        return 1;
    }

    @Nonnull
    @Override
    public String getName() {
        return "EndEssence";
    }

    @Nonnull
    @Override
    public ItemStack getItem() {
        return Items.END_ESSENCE.clone();
    }

    @Override
    public boolean isObtainableFromGEOMiner() {
        return true;
    }
}