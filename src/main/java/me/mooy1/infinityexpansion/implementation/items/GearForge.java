package me.mooy1.infinityexpansion.implementation.items;

import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.utils.ItemStackUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class GearForge extends SlimefunItem implements RecipeDisplayItem {

    public GearForge() {
        super(Categories.ADVANCED_MACHINES, Items.GEAR_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.MYTHRIL, Items.MYTHRIL, Items.MYTHRIL,
                Items.MAGSTEEL,new ItemStack(Material.SMITHING_TABLE), Items.MAGSTEEL,
                Items.MAGSTEEL,Items.MAGSTEEL,Items.MAGSTEEL
        });
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0 ; i < INPUTS.length ; i++) {
            items.add(ItemStackUtils.getItemFromID(INPUTS[i], 1));
            //customItemFromID
        }

        return items;
    }

    private static final String[] INPUTS = {
            "IRON_INGOT",
            "GOLD_INGOT",
            "DIAMOND",
            "EMERALD",
            "NETHERITE_INGOT",
            "COPPER_INGOT",
    };
}
