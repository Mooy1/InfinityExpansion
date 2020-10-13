package me.mooy1.infinityexpansion.setup;

import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class HiddenCategory extends Category {
    public HiddenCategory() {
        super(new NamespacedKey(InfinityExpansion.getInstance(), "HIDDEN_RECIPES"),
                new CustomItem(Material.BARRIER, "&c( ͡° ͜ʖ ͡°)"), 10
        );
    }

    @Override
    public boolean isHidden(@Nonnull Player p) {
        return true;
    }
}