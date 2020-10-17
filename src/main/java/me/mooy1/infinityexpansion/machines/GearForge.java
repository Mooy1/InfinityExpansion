package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.utils.IDUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class GearForge extends SlimefunItem implements InventoryBlock, RecipeDisplayItem {

    public GearForge() {
        super(Categories.ADVANCED_MACHINES, Items.GEAR_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {

        });
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0 ; i < INPUTS.length ; i++) {
            items.add(IDUtils.getItemFromID(INPUTS[i], 1));
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

    @Override
    public int[] getInputSlots() {
        return new int[0];
    }

    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }
}
