package io.github.mooy1.infinityexpansion.implementation.materials;

import io.github.mooy1.infinityexpansion.implementation.Categories;
import io.github.mooy1.infinitylib.core.PluginUtils;
import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * Ender essence geo-resource item
 *
 * @author Mooy1
 */
public final class EnderEssence extends SlimefunItem implements NotPlaceable, GEOResource {
    
    public static final SlimefunItemStack ITEM = new SlimefunItemStack(
            "END_ESSENCE",
            Material.BLAZE_POWDER,
            "&5Ender Essence",
            "&8&oFrom the depths of the end..."
    );
    private static final NamespacedKey key = PluginUtils.getKey("ender_essence");
     
    public EnderEssence() {
        super(Categories.MAIN_MATERIALS, ITEM, RecipeType.GEO_MINER, new ItemStack[9]);
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
        return key;
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

    @Nonnull
    @Override
    public ItemStack getItem() {
        return ITEM;
    }

    @Override
    public boolean isObtainableFromGEOMiner() {
        return true;
    }
}