package me.mooy1.infinityexpansion.materials;

import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;
import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mooy1.infinityexpansion.Items;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class VoidDustResource implements GEOResource {

    private final NamespacedKey key = new NamespacedKey(InfinityExpansion.getInstance(), "VoidDust");

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
        return "Void Dust";
    }

    @Nonnull
    @Override
    public ItemStack getItem() {
        return Items.VOID_DUST.clone();
    }

    @Override
    public boolean isObtainableFromGEOMiner() {
        return true;
    }
}