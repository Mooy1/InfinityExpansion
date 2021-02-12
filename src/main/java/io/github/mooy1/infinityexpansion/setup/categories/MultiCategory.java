package io.github.mooy1.infinityexpansion.setup.categories;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.categories.FlexCategory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
public final class MultiCategory extends FlexCategory {
    
    public static final MultiCategory CATEGORY = new MultiCategory(PluginUtils.getKey("main"),
            new CustomItem(Material.NETHER_STAR, "&bInfinity &7Expansion"), 3
    );
    
    private final List<Category> categories = new ArrayList<>();
    
    public void register(InfinityExpansion plugin, Category... add) {
        for (Category category : add) {
            this.categories.add(category);
            category.register(plugin);
        }
        register(plugin);
    }
    
    private MultiCategory(NamespacedKey key, ItemStack item, int tier) {
        super(key, item, tier);
    }
    
    @Override
    public boolean isVisible(@Nonnull Player p, @Nonnull PlayerProfile profile, @Nonnull SlimefunGuideMode layout) {
        return true;
    }

    @Override
    public void open(Player p, PlayerProfile profile, SlimefunGuideMode mode) {
        ChestMenu menu = new ChestMenu(getUnlocalizedName());
        menu.setEmptySlotsClickable(false);
        
        for (int i = 0 ; i < 9 ; i ++) {
            menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        
        menu.addItem(1, ChestMenuUtils.getBackButton(p, "", ChatColor.GRAY + SlimefunPlugin.getLocalization().getMessage(p, "guide.back.guide")));
        menu.addMenuClickHandler(1, (pl, s, is, action) -> {
            SlimefunGuide.openMainMenu(profile, mode, 1);
            return false;
        });
        
        for (int i = 0 ; i < this.categories.size() ; i++) {
            Category category = this.categories.get(i);
            menu.addItem(i + 9, category.getItem(p), (pl, s, is, action) -> {
                SlimefunGuide.openCategory(profile, category, mode, 1);
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
