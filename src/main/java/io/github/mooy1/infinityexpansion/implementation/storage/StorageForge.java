package io.github.mooy1.infinityexpansion.implementation.storage;

import io.github.mooy1.infinityexpansion.implementation.template.Crafter;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.MessageUtils;
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
public class StorageForge extends Crafter {
    
    public static final ItemStack[][] RECIPES = new ItemStack[][] {
            {
                    Items.MAGSTEEL, Items.MACHINE_CIRCUIT, Items.MAGSTEEL,
                    Items.MAGSTEEL, Items.BASIC_STORAGE, Items.MAGSTEEL,
                    Items.MAGSTEEL, Items.MACHINE_CIRCUIT, Items.MAGSTEEL
            },
            {
                    Items.MAGSTEEL_PLATE, Items.MACHINE_CIRCUIT, Items.MAGSTEEL_PLATE,
                    Items.MAGSTEEL_PLATE, Items.ADVANCED_STORAGE, Items.MAGSTEEL_PLATE,
                    Items.MAGSTEEL_PLATE, Items.MACHINE_PLATE, Items.MAGSTEEL_PLATE
            },
            {
                    Items.VOID_INGOT, Items.MACHINE_PLATE, Items.VOID_INGOT,
                    Items.MAGNONIUM, Items.REINFORCED_STORAGE, Items.MAGNONIUM,
                    Items.VOID_INGOT, Items.MACHINE_CORE, Items.VOID_INGOT
            },
            {
                    Items.INFINITE_INGOT, Items.VOID_INGOT, Items.INFINITE_INGOT,
                    Items.INFINITE_INGOT, Items.VOID_STORAGE, Items.INFINITE_INGOT,
                    Items.INFINITE_INGOT, Items.VOID_INGOT, Items.INFINITE_INGOT
            }
    };
    
    public StorageForge() {
        super(Categories.STORAGE_TRANSPORT, Items.STORAGE_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.MAGSTEEL, new ItemStack(Material.ANVIL), Items.MAGSTEEL,
                Items.MAGSTEEL, new ItemStack(Material.CRAFTING_TABLE), Items.MAGSTEEL,
                Items.MAGSTEEL, new ItemStack(Material.BARREL), Items.MAGSTEEL,
        });
    }

    @Override
    public SlimefunItemStack[] getOutputs() {
        return new SlimefunItemStack[] {Items.ADVANCED_STORAGE, Items.REINFORCED_STORAGE, Items.VOID_STORAGE, Items.INFINITY_STORAGE};
    }

    @Override
    public ItemStack[][] getRecipes() {
        return RECIPES;
    }

    @Override
    public void postCraft(@Nonnull Location l, @Nonnull BlockMenu inv, @Nonnull Player p) {
        MessageUtils.message(p, ChatColor.GREEN + "Transferred items to upgraded unit");
    }
}
