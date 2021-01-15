package io.github.mooy1.infinityexpansion.implementation.gear;

import io.github.mooy1.infinityexpansion.implementation.materials.EnderEssence;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Objects;

/**
 * fire aspect
 *
 * @author Mooy1
 */
public final class EnderFlame extends UnplaceableBlock {
    
    public static final SlimefunItemStack ITEM = new SlimefunItemStack(
            "ENDER_FLAME",
            Material.ENCHANTED_BOOK,
            "&cEnder Flame"
    );
    
    public EnderFlame() {
        super(Categories.MAIN_MATERIALS, ITEM, RecipeType.MAGIC_WORKBENCH, new ItemStack[]{
                EnderEssence.ITEM, EnderEssence.ITEM, EnderEssence.ITEM,
                EnderEssence.ITEM, new ItemStack(Material.BOOK), EnderEssence.ITEM,
                EnderEssence.ITEM, EnderEssence.ITEM, EnderEssence.ITEM
        });
        EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) ITEM.getItemMeta();
        Objects.requireNonNull(storageMeta).addStoredEnchant(Enchantment.FIRE_ASPECT, 10, true);
        ITEM.setItemMeta(storageMeta);
    }
}
