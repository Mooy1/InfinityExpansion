package io.github.mooy1.infinityexpansion.setup;

import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.categories.FlexCategory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideLayout;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.Objects.Category;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * A category that holds sub categories
 * 
 * @author Mooy1
 * 
 */
public class MainCategory extends FlexCategory {
    
    public static final List<Category> categories = new ArrayList<>();

    public MainCategory(NamespacedKey key, ItemStack item, int tier) {
        super(key, item, tier);
    }
    
    @Override
    public boolean isVisible(@Nonnull Player p, @Nonnull PlayerProfile profile, @Nonnull SlimefunGuideLayout layout) {
        return layout == SlimefunGuideLayout.CHEST;
    }

    @Override
    public void open(Player p, PlayerProfile profile, SlimefunGuideLayout layout) {
        ChestMenu menu = new ChestMenu(getUnlocalizedName());
        menu.setEmptySlotsClickable(false);
        
        for (int i = 0 ; i < 9 ; i ++) {
            menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        
        menu.addItem(1, ChestMenuUtils.getBackButton(p, "", ChatColor.GRAY + SlimefunPlugin.getLocalization().getMessage(p, "guide.back.guide")));
        menu.addMenuClickHandler(1, (pl, s, is, action) -> {
            SlimefunGuide.openMainMenu(profile, layout, 1);
            return false;
        });
        
        for (int i = 0 ; i < categories.size() ; i++) {
            Category category = categories.get(i);
            menu.addItem(i + 9, category.getItem(p), (pl, s, is, action) -> {
                SlimefunGuide.openCategory(profile, category, layout, 1);
                return false;
            });
        }
        

        for (int i = 45 ; i < 54 ; i ++) {
            menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
        profile.getGuideHistory().add(this, 1);

        menu.open(p);

    }

}
