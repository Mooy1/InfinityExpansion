package io.github.mooy1.infinityexpansion.items.machines;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import lombok.Setter;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinitylib.machines.AbstractMachineBlock;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;

public final class GrowingMachine extends AbstractMachineBlock implements RecipeDisplayItem {

    private static final int[] OUTPUT_SLOTS = {
            13, 14, 15, 16,
            22, 23, 24, 25,
            31, 32, 33, 34,
            40, 41, 42, 43
    };
    private static final int[] INPUT_SLOTS = { 37 };
    private static final int STATUS_SLOT = 10;
    private static final ItemStack GROWING = new CustomItemStack(Material.LIME_STAINED_GLASS_PANE, "&aGrowing...");
    private static final ItemStack INPUT_PLANT = new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE, "&9Input a plant!");

    @Setter
    private EnumMap<Material, ItemStack[]> recipes;
    @Setter
    private int ticksPerOutput;

    public GrowingMachine(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    protected boolean process(@Nonnull Block b, @Nonnull BlockMenu menu) {
        ItemStack input = menu.getItemInSlot(INPUT_SLOTS[0]);
        if (input != null && this.recipes.containsKey(input.getType())) {
            if (menu.hasViewer()) {
                menu.replaceExistingItem(STATUS_SLOT, GROWING);
            }
            if (InfinityExpansion.slimefunTickCount() % this.ticksPerOutput == 0) {
                ItemStack[] output = this.recipes.get(input.getType());
                if (output != null) {
                    for (ItemStack item : output) {
                        menu.pushItem(item.clone(), OUTPUT_SLOTS);
                    }
                }
            }
            return true;
        }
        else {
            if (menu.hasViewer()) {
                menu.replaceExistingItem(STATUS_SLOT, INPUT_PLANT);
            }
            return false;
        }
    }

    @Override
    protected int getStatusSlot() {
        return STATUS_SLOT;
    }

    @Override
    protected void setup(@Nonnull BlockMenuPreset blockMenuPreset) {
        blockMenuPreset.drawBackground(new int[] {
                0, 1, 2, 9, 10, 11, 18, 19, 20
        });
        blockMenuPreset.drawBackground(INPUT_BORDER, new int[] {
                27, 28, 29, 36, 38, 45, 46, 47
        });
        blockMenuPreset.drawBackground(OUTPUT_BORDER, new int[] {
                3, 4, 5, 6, 7, 8,
                12, 17,
                21, 26,
                30, 35,
                39, 44,
                48, 49, 50, 51, 52, 53
        });
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> list = new ArrayList<>();
        for (Map.Entry<Material, ItemStack[]> entry : this.recipes.entrySet()) {
            ItemStack in = new ItemStack(entry.getKey());
            for (ItemStack item : entry.getValue()) {
                list.add(in);
                list.add(item);
            }
        }
        return list;
    }

    @Override
    protected int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    protected int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

}
