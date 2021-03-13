package io.github.mooy1.infinityexpansion.implementation.storage;

import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinityexpansion.implementation.abstracts.AbstractCrafter;
import io.github.mooy1.infinityexpansion.implementation.materials.Items;
import io.github.mooy1.infinitylib.players.MessageUtils;
import io.github.mooy1.infinitylib.slimefun.utils.DelayedRecipeType;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * A crafting machine for upgrading storage units and retaining the stored items
 *
 * @author Mooy1
 */
public final class StorageForge extends AbstractCrafter {
    
    public static final SlimefunItemStack ITEM = new SlimefunItemStack(
            "STORAGE_FORGE",
            Material.BEEHIVE,
            "&6Storage Forge",
            "&7Upgrades the tier of Storage Units",
            "&7Retains stored items"
    );
    
    public static final DelayedRecipeType TYPE = new DelayedRecipeType(ITEM);
    
    public StorageForge() {
        super(Categories.STORAGE_TRANSPORT, ITEM, TYPE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.MAGSTEEL, new ItemStack(Material.ANVIL), Items.MAGSTEEL,
                Items.MAGSTEEL, new ItemStack(Material.CRAFTING_TABLE), Items.MAGSTEEL,
                Items.MAGSTEEL, new ItemStack(Material.BARREL), Items.MAGSTEEL,
        });
    }
    
    @Override
    protected void modifyOutput(@Nonnull BlockMenu inv, @Nonnull ItemStack output) {
        StorageUnit.transferToStack(inv.getItemInSlot(INPUT_SLOTS[4]), output);
    }

    @Override
    public void postCraft(@Nonnull Location l, @Nonnull BlockMenu inv, @Nonnull Player p) {
        MessageUtils.message(p, ChatColor.GREEN + "Transferred items to upgraded unit");
    }
    
}
