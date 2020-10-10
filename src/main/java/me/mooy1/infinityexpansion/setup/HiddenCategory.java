package me.mooy1.infinityexpansion.setup;

import me.mrCookieSlime.Slimefun.Objects.Category;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class HiddenCategory extends Category {
    public HiddenCategory(NamespacedKey key, ItemStack item, int tier) {
        super(key, item, tier);
    }

    @Override
    public boolean isHidden(@Nonnull Player p) {
        return true;
    }
}