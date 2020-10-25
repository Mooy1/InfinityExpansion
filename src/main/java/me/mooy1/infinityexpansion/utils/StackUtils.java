package me.mooy1.infinityexpansion.utils;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class StackUtils {

    private StackUtils() {}

    @Nullable
    public static String getIDFromItem(@Nullable ItemStack item) {
        if (item == null) {

            return null;

        } else {

            SlimefunItem sfItem = SlimefunItem.getByItem(item);

            if (sfItem != null) {

                return sfItem.getId();

            }
        }

        return item.getType().toString();
    }

    @Nonnull
    public static String getIDFromNonNullItem(@Nonnull ItemStack item) {
        SlimefunItem sfItem = SlimefunItem.getByItem(item);

        if (sfItem != null) {

            return sfItem.getId();

        }

        return item.getType().toString();
    }

    @Nullable
    public static ItemStack getItemFromID(@Nonnull String id, int amount) {

        SlimefunItem sfItem = SlimefunItem.getByID(id);

        if (sfItem != null) {

            return new CustomItem(sfItem.getItem(), amount);

        } else {

            Material material = Material.getMaterial(id);

            if (material != null){

                return new ItemStack(material, amount);

            }
        }

        return null;
    }

    public static void addLore(@Nonnull ItemStack item, @Nonnull List<String> lores) {
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {

            List<String> lore = new ArrayList<>();

            if (meta.getLore() != null) {
                lore = meta.getLore();
            }

            lore.addAll(lores);

            meta.setLore(lore);

            item.setItemMeta(meta);
        }
    }
}
