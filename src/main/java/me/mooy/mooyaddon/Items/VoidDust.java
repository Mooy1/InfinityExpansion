package me.mooy.mooyaddon.Items;

import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;
import me.mooy.mooyaddon.MooyAddon;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;
import me.mooy.mooyaddon.MooyItems;

public class VoidDust implements GEOResource {

    private final NamespacedKey key = new NamespacedKey(MooyAddon.getInstance(), "Void Dust");

    @Override
    public int getDefaultSupply(World.Environment environment, Biome biome) {
        if (environment == World.Environment.THE_END) {
            return 20;
        } else {
            return 0;
        }
    }

    @Override
    public NamespacedKey getKey() {
        return key;
    }

    @Override
    public int getMaxDeviation() {
        return 1;
    }

    @Override
    public String getName() {
        return "Void Dust";
    }

    @Override
    public ItemStack getItem() {
        return MooyItems.VOID_DUST.clone();
    }

    @Override
    public boolean isObtainableFromGEOMiner() {
        return true;
    }

    }