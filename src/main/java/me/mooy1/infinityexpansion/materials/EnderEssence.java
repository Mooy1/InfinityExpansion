package me.mooy1.infinityexpansion.materials;

import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

import static me.mooy1.infinityexpansion.materials.Singularities.MiddleItem;

public class EnderEssence extends SlimefunItem implements GEOResource {

    private final NamespacedKey key = new NamespacedKey(InfinityExpansion.getInstance(), "EnderEssence");

    public EnderEssence() {
        super(Categories.INFINITY_MATERIALS, Items.ENDER_ESSENCE, RecipeType.GEO_MINER, MiddleItem(new CustomItem(
                        Material.PAPER,
                        "&fGEO-Miner Material",
                        "&aFound in the End and Void biomes",
                        "&aMake sure to GEO-Scan the chunk first"
                ))
        );
    }

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
        return "Ender Essence";
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