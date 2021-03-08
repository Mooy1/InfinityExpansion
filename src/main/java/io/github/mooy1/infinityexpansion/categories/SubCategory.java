package io.github.mooy1.infinityexpansion.categories;

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
public class SubCategory extends Category {
    
    public SubCategory(NamespacedKey key, ItemStack item) {
        super(key, item,3);
    }

    @Override
    public boolean isHidden(@Nonnull Player p) {
        return true;
    }
    
}
