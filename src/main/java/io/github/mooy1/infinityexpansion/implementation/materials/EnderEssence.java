package io.github.mooy1.infinityexpansion.implementation.materials;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Ender essence geo-resource item
 *
 * @author Mooy1
 */
public class EnderEssence extends SlimefunItem implements NotPlaceable, GEOResource {
    
    private final NamespacedKey key;
     
    public EnderEssence(InfinityExpansion plugin) {
        super(Categories.MAIN_MATERIALS, Items.ENDER_ESSENCE, RecipeType.GEO_MINER, null);
        this.key = new NamespacedKey(plugin, "ender_essence");
        register();
    }
    
    @Override
    public int getDefaultSupply(@Nonnull World.Environment environment, @Nonnull Biome biome) {
        if (environment == World.Environment.THE_END) {
            return 16;
        }
        if (biome == Biome.THE_VOID) {
            return 16;
        }
        if (environment == World.Environment.NETHER) {
            return 8;
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
        return Objects.requireNonNull(Items.ENDER_ESSENCE.getDisplayName());
    }

    @Nonnull
    @Override
    public ItemStack getItem() {
        return Items.ENDER_ESSENCE.clone();
    }

    @Override
    public boolean isObtainableFromGEOMiner() {
        return true;
    }
}