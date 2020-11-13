package io.github.mooy1.infinityexpansion.setup;

import me.mrCookieSlime.Slimefun.Objects.Category;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * Category for infinity item cheating since flex category doesn't work for that
 *
 * @author Mooy1
 */
public class CheatCategory extends Category {
    public CheatCategory(NamespacedKey key, ItemStack item, int tier) {
        super(key, item, tier);
    }

    @Override
    public boolean isHidden(@Nonnull Player p) {
        return true;
    }
}
