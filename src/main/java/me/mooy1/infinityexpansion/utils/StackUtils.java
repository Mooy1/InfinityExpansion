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

    public static void insertLore(@Nonnull ItemStack item, @Nonnull List<String> lores, @Nonnull String find, int offset) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return;

        List<String> lore = new ArrayList<>();

        if (meta.getLore() != null) {
            lore = meta.getLore();
        }

        int position = 0;
        int i = 0;
        for (String line : lore) {
            if(ChatColor.stripColor(line).contains(ChatColor.stripColor(find))) {
                position = i;
            }
            i++;
        }

        position += offset;

        if (position < 0) return;

        lore = lore.subList(0, position);

        lore.addAll(lores);

        meta.setLore(lore);

        item.setItemMeta(meta);
    }
}
