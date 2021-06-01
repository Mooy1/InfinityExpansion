package io.github.mooy1.infinityexpansion.items.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.items.abstracts.AbstractMachine;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.mooy1.infinitylib.recipes.RecipeMap;
import io.github.mooy1.infinitylib.recipes.RecipeOutput;
import io.github.mooy1.infinitylib.recipes.ShapedRecipe;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotHopperable;
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
 * Machines that convert 1 item to another with energy
 *
 * @author Mooy1
 */
public final class ConversionMachine extends AbstractMachine implements RecipeDisplayItem, NotHopperable {

    private static final int[] INPUT_SLOTS = {MenuPreset.INPUT};
    private static final int[] OUTPUT_SLOTS = {MenuPreset.OUTPUT};
    private static final int STATUS_SLOT = MenuPreset.STATUS;

    private final int energy;
    private final List<ItemStack> displayRecipes = new ArrayList<>();
    private final RecipeMap<ItemStack> recipes = new RecipeMap<>(ShapedRecipe::new);

    public ConversionMachine(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int energy, ItemStack[] inputs, ItemStack[] outputs) {
        super(category, item, recipeType, recipe);
        this.energy = energy;
        
        if (inputs.length == outputs.length) {
            for (int i = 0 ; i < inputs.length ; i++) {
                this.displayRecipes.add(inputs[i]);
                this.displayRecipes.add(outputs[i]);
                this.recipes.put(new ItemStack[] {inputs[i]}, outputs[i]);
            }
        } else {
            for (ItemStack input : inputs) {
                this.recipes.put(new ItemStack[] {input}, new RandomizedOutput(outputs));
                for (ItemStack output : outputs) {
                    this.displayRecipes.add(input);
                    this.displayRecipes.add(output);
                }
            }
        }
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu menu, @Nonnull Location l) {
        menu.dropItems(l, OUTPUT_SLOTS);
        menu.dropItems(l, INPUT_SLOTS);
    }
    
    @Nonnull
    @Override
    public int[] getTransportSlots(@Nonnull DirtyChestMenu dirtyChestMenu, @Nonnull ItemTransportFlow flow, ItemStack itemStack) {
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

    @Override
    public int getCapacity() {
        return this.energy;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        return this.displayRecipes;
    }

    @Override
    public boolean process(@Nonnull BlockMenu inv, @Nonnull Block block) {
        ItemStack input = inv.getItemInSlot(INPUT_SLOTS[0]);

        if (input == null) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.INPUT_ITEM);
            }
            return false;
        }

        RecipeOutput<ItemStack> output = this.recipes.get(new ItemStack[] {input});

        if (output == null) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.INVALID_INPUT);
            }
            return false;
        }

        ItemStack out = output.getOutput();
        if (out instanceof RandomizedOutput) {
            out = ((RandomizedOutput) out).getOutput();
        }

        if (inv.fits(out, OUTPUT_SLOTS)) {
            output.consumeInput();
            inv.pushItem(out.clone(), OUTPUT_SLOTS);
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aConverting..."));
            }
            return true;
        } else if (inv.hasViewer()) {
            inv.replaceExistingItem(STATUS_SLOT, MenuPreset.NO_ROOM);
        }
        return false;
    }

    @Override
    public void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        super.setupMenu(blockMenuPreset);
        for (int i : MenuPreset.INPUT_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.INPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.STATUS_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.STATUS_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.OUTPUT_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.OUTPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
    }

    @Override
    protected int getStatusSlot() {
        return STATUS_SLOT;
    }

    @Override
    protected int getEnergyConsumption() {
        return this.energy;
    }
    
    private static final class RandomizedOutput extends ItemStack {

        private final ItemStack[] items;

        public RandomizedOutput(ItemStack[] outputs) {
            super(outputs[0]);
            this.items = outputs;
        }

        public ItemStack getOutput() {
            return this.items[ThreadLocalRandom.current().nextInt(this.items.length)];
        }

    }

}
