package io.github.mooy1.infinityexpansion.items.machines;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import lombok.Setter;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinitylib.common.StackUtils;
import io.github.mooy1.infinitylib.machines.AbstractMachineBlock;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;

/**
 * Creates special resources from the combination of singularities
 *
 * @author Mooy1
 */
public final class ResourceSynthesizer extends AbstractMachineBlock implements RecipeDisplayItem {

    private static final int[] OUTPUT_SLOTS = {
            40
    };
    private static final int[] INPUT_SLOTS = {
            10, 16
    };
    private static final int STATUS_SLOT = 13;

    @Setter
    private SlimefunItemStack[] recipes;

    public ResourceSynthesizer(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    protected int getStatusSlot() {
        return STATUS_SLOT;
    }

    @Override
    protected void setup(@Nonnull BlockMenuPreset blockMenuPreset) {
        blockMenuPreset.drawBackground(new int[] {
                3, 4, 5,
                12, 13, 14,
                21, 22, 23,
                27, 29, 33, 35,
                36, 44,
                45, 46, 47, 51, 52, 53
        });
        blockMenuPreset.drawBackground(INPUT_BORDER, new int[] {
                0, 1, 2, 6, 7, 8,
                9, 11, 15, 17,
                18, 19, 20, 24, 25, 26
        });
        blockMenuPreset.drawBackground(OUTPUT_BORDER, new int[] {
                28, 34, 37, 38, 42, 43,
                30, 31, 32,
                39, 41,
                48, 49, 50
        });
    }

    @Override
    protected int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    protected int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> items = new ArrayList<>();

        for (int i = 0 ; i < this.recipes.length ; i += 3) {
            items.add(this.recipes[i]);
            items.add(this.recipes[i + 2]);
            items.add(this.recipes[i + 1]);
            items.add(this.recipes[i + 2]);
        }

        return items;
    }

    @Override
    protected boolean process(@Nonnull Block b, @Nonnull BlockMenu inv) {

        ItemStack input1 = inv.getItemInSlot(INPUT_SLOTS[0]);
        ItemStack input2 = inv.getItemInSlot(INPUT_SLOTS[1]);

        if (input1 == null || input2 == null) { //no input

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, IDLE_ITEM);
            }
            return false;

        }

        String id1 = StackUtils.getId(input1);

        if (id1 == null) {
            return false;
        }

        String id2 = StackUtils.getId(input2);

        if (id2 == null) {
            return false;
        }

        ItemStack recipe = null;

        for (int i = 0 ; i < this.recipes.length ; i += 3) {
            if ((id1.equals(this.recipes[i].getItemId()) && id2.equals(this.recipes[i + 1].getItemId()) || (id2.equals(this.recipes[i].getItemId()) && id1.equals(this.recipes[i + 1].getItemId())))) {
                recipe = this.recipes[i + 2];
            }
        }

        if (recipe == null) { //invalid recipe

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, IDLE_ITEM);
            }
            return false;

        }

        recipe = recipe.clone();

        if (inv.fits(recipe, OUTPUT_SLOTS)) { //no item

            inv.pushItem(recipe, OUTPUT_SLOTS);
            inv.consumeItem(INPUT_SLOTS[0], 1);
            inv.consumeItem(INPUT_SLOTS[1], 1);

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.LIME_STAINED_GLASS_PANE, "&aResource Synthesized!"));
            }
            return true;

        }
        else { //not enough room

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, NO_ROOM_ITEM);
            }
            return false;

        }
    }

}
