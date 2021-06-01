package io.github.mooy1.infinityexpansion.items.machines;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.items.abstracts.AbstractMachine;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

/**
 * Creates special resources from the combination of singularities
 *
 * @author Mooy1
 */
public final class ResourceSynthesizer extends AbstractMachine implements RecipeDisplayItem {
    
    private static final int[] OUTPUT_SLOTS = {
            MenuPreset.STATUS + 27
    };
    private static final int[] BACKGROUND = {
            27, 29, 33, 35,
            36, 44,
            45, 46, 47, 51, 52, 53
    };
    private static final int[] OUTPUT_BORDER = {
            28, 34, 37, 38, 42, 43
    };
    private static final int[] INPUT_SLOTS = {
            MenuPreset.INPUT, MenuPreset.OUTPUT
    };
    private static final int INPUT_SLOT1 = INPUT_SLOTS[0];
    private static final int INPUT_SLOT2 = INPUT_SLOTS[1];
    private static final int STATUS_SLOT = MenuPreset.STATUS;

    private final int energy;
    private final SlimefunItemStack[] recipes;

    public ResourceSynthesizer(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, SlimefunItemStack[] recipes, int energy) {
        super(category, item, recipeType, recipe);
        this.recipes = recipes;
        this.energy = energy;
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu inv, @Nonnull Location l) {
        inv.dropItems(l, OUTPUT_SLOTS);
        inv.dropItems(l, INPUT_SLOTS);
    }

    @Override
    protected int getStatusSlot() {
        return STATUS_SLOT;
    }

    @Override
    protected int getEnergyConsumption() {
        return this.energy;
    }
    
    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        super.setupMenu(blockMenuPreset);
        for (int i : BACKGROUND) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.INPUT_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.INPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.OUTPUT_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.INPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : OUTPUT_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.OUTPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.STATUS_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.STATUS_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.STATUS_BORDER) {
            blockMenuPreset.addItem(i + 27, MenuPreset.OUTPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.INSERT) {
            return INPUT_SLOTS;
        } else if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        } else {
            return new int[0];
        }
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {

    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> items = new ArrayList<>();

        for (int i = 0 ; i < this.recipes.length; i += 3) {
            items.add(this.recipes[i]);
            items.add(this.recipes[i + 2]);
            items.add(this.recipes[i + 1]);
            items.add(this.recipes[i + 2]);
        }

        return items;
    }

    @Override
    protected boolean process(@Nonnull BlockMenu inv, @Nonnull Block b) {

        ItemStack input1 = inv.getItemInSlot(INPUT_SLOT1);
        ItemStack input2 = inv.getItemInSlot(INPUT_SLOT2);

        if (input1 == null || input2 == null) { //no input

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.INVALID_INPUT);
            }
            return false;

        }

        String id1 = StackUtils.getID(input1);

        if (id1 == null) return false;

        String id2 = StackUtils.getID(input2);

        if (id2 == null) return false;

        ItemStack recipe = null;

        for (int i = 0 ; i < this.recipes.length; i += 3) {
            if ((id1.equals(this.recipes[i].getItemId()) && id2.equals(this.recipes[i + 1].getItemId()) || (id2.equals(this.recipes[i].getItemId()) && id1.equals(this.recipes[i + 1].getItemId())))) {
                recipe = this.recipes[i + 2];
            }
        }

        if (recipe == null) { //invalid recipe

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.INVALID_RECIPE);
            }
            return false;

        }

        recipe = recipe.clone();

        if (inv.fits(recipe, OUTPUT_SLOTS)) { //no item

            inv.pushItem(recipe, OUTPUT_SLOTS);
            inv.consumeItem(INPUT_SLOT1, 1);
            inv.consumeItem(INPUT_SLOT2, 1);

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aResource Synthesized!"));
            }
            return true;

        } else { //not enough room

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.NO_ROOM);
            }
            return false;

        }
    }

}
