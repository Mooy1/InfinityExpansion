package me.mooy1.infinityexpansion.utils;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public final class ItemStackUtils {

    private ItemStackUtils() {}

    @Nullable
    public static String getIDFromItem(ItemStack item) {
        if (item == null) {
            return null;
        } else if (SlimefunItem.getByItem(item) != null) {
            return Objects.requireNonNull(SlimefunItem.getByItem(item)).getId();
        } else {
            return item.getType().toString();
        }
    }

    @Nullable
    public static ItemStack getItemFromID(@Nonnull String id, int amount) {
        if (SlimefunItem.getByID(id) != null) {
            return new CustomItem(Objects.requireNonNull(SlimefunItem.getByID(id)).getItem(), amount);
        } else if (Material.getMaterial(id) != null){
            return new ItemStack(Objects.requireNonNull(Material.getMaterial(id)), amount);
        } else {
            return null;
        }
    }
}
