package io.github.mooy1.infinityexpansion.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinitylib.items.LoreUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;

public final class Util {

    public static final int[] largeOutput = {
            13, 14, 15, 16,
            22, 23, 24, 25,
            31, 32, 33, 34,
            40, 41, 42, 43
    };

    public static final int[] largeOutputBorder = {
            3, 4, 5, 6, 7, 8,
            12, 17,
            21, 26,
            30, 35,
            39, 44,
            48, 49, 50, 51, 52, 53

    };

    @Nonnull
    public static ItemStack getDisplayItem(@Nonnull ItemStack output) {
        LoreUtils.addLore(output, "", "&a-------------------", "&a\u21E8 Click to craft", "&a-------------------");
        return output;
    }

    @Nonnull
    public static Map<Enchantment, Integer> getEnchants(@Nonnull ConfigurationSection section) {
        Map<Enchantment, Integer> enchants = new HashMap<>();
        for (String path : section.getKeys(false)) {
            Enchantment e = enchantmentByPath(path);
            if (e != null) {
                int level = section.getInt(path);
                if (level > 0 && level <= Short.MAX_VALUE) {
                    enchants.put(e, level);
                } else if (level != 0) {
                    section.set(path, 0);
                    InfinityExpansion.inst().log(Level.WARNING,
                            "Enchantment level " + level
                                    + " is out of bounds for " + e.getKey()
                                    + ", resetting to default!"
                    );
                }
            }
        }
        return enchants;
    }

    @Nullable
    private static Enchantment enchantmentByPath(@Nonnull String path) {
        switch (path) {
            case "sharpness":
                return Enchantment.DAMAGE_ALL;
            case "smite":
                return Enchantment.DAMAGE_UNDEAD;
            case "bane-of-arthropods":
                return Enchantment.DAMAGE_ARTHROPODS;
            case "efficiency":
                return Enchantment.DIG_SPEED;
            case "protection":
                return Enchantment.PROTECTION_ENVIRONMENTAL;
            case "fire-aspect":
                return Enchantment.FIRE_ASPECT;
            case "fortune":
                return Enchantment.LOOT_BONUS_BLOCKS;
            case "looting":
                return Enchantment.LOOT_BONUS_MOBS;
            case "silk-touch":
                return Enchantment.SILK_TOUCH;
            case "thorns":
                return Enchantment.THORNS;
            case "aqua-affinity":
                return Enchantment.WATER_WORKER;
            case "power":
                return Enchantment.ARROW_DAMAGE;
            case "flame":
                return Enchantment.ARROW_FIRE;
            case "infinity":
                return Enchantment.ARROW_INFINITE;
            case "punch":
                return Enchantment.ARROW_KNOCKBACK;
            case "feather-falling":
                return Enchantment.PROTECTION_FALL;
            case "unbreaking":
                return Enchantment.DURABILITY;
            default:
                return null;
        }
    }

    public static boolean isWaterLogged(@Nonnull Block b) {
        if ((InfinityExpansion.inst().getGlobalTick() & 7) == 0) {
            BlockData blockData = b.getBlockData();

            if (blockData instanceof Waterlogged) {
                Waterlogged waterLogged = (Waterlogged) blockData;
                if (waterLogged.isWaterlogged()) {
                    BlockStorage.addBlockInfo(b.getLocation(), "water_logged", "true");
                    return true;
                } else {
                    BlockStorage.addBlockInfo(b.getLocation(), "water_logged", "false");
                    return false;
                }
            } else {
                return false;
            }

        } else {
            return "true".equals(BlockStorage.getLocationInfo(b.getLocation(), "water_logged"));
        }
    }

    public static int getIntData(String key, Location block) {
        String val = BlockStorage.getLocationInfo(block, key);
        if (val == null) {
            BlockStorage.addBlockInfo(block, key, "0");
            return 0;
        }
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException x) {
            BlockStorage.addBlockInfo(block, key, "0");
            return 0;
        }
    }

    public static int getIntData(String key, Config config, Location block) {
        String val = config.getString(key);
        if (val == null) {
            BlockStorage.addBlockInfo(block, key, "0");
            return 0;
        }
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException x) {
            BlockStorage.addBlockInfo(block, key, "0");
            return 0;
        }
    }

}
