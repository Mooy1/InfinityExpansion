package io.github.mooy1.infinityexpansion.implementation.machines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.implementation.abstracts.AbstractMachine;
import io.github.mooy1.infinitylib.slimefun.presets.MenuPreset;
import io.github.mooy1.infinitylib.slimefun.recipes.inputs.StrictInput;
import io.github.mooy1.infinitylib.slimefun.recipes.outputs.StrictOutput;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotHopperable;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
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

    private static final int[] INPUT_SLOTS = {MenuPreset.slot1};
    private static final int[] OUTPUT_SLOTS = {MenuPreset.slot3};
    private static final int STATUS_SLOT = MenuPreset.slot2;

    private final int energy;
    private final List<ItemStack> displayRecipes = new ArrayList<>();
    private final Map<StrictInput, StrictOutput> recipes = new HashMap<>();

    public ConversionMachine(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int energy, ItemStack[] inputs, ItemStack[] outputs) {
        super(category, item, recipeType, recipe);
        this.energy = energy;
        
        if (inputs.length == outputs.length) {
            for (int i = 0 ; i < inputs.length ; i++) {
                this.displayRecipes.add(inputs[i]);
                this.displayRecipes.add(outputs[i]);
                this.recipes.put(new StrictInput(inputs[i]), new StrictOutput(outputs[i], inputs[i].getAmount()));
            }
        } else {
            for (ItemStack input : inputs) {
                this.recipes.put(new StrictInput(input), new RandomizedOutput(outputs, input.getAmount()));
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
    public boolean process(@Nonnull BlockMenu inv, @Nonnull Block block, @Nonnull Config config) {
        ItemStack input = inv.getItemInSlot(INPUT_SLOTS[0]);

        if (input == null) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.inputAnItem);
            }
            return false;
        }

        StrictOutput output = this.recipes.get(new StrictInput(input));

        if (output == null) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.invalidInput);
            }
            return false;
        }

        if (inv.fits(output.getOutput(), OUTPUT_SLOTS)) {
            inv.consumeItem(INPUT_SLOTS[0], output.getInputConsumption());
            inv.pushItem(output.getOutput().clone(), OUTPUT_SLOTS);
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aConverting..."));
            }
            return true;
        } else if (inv.hasViewer()) {
            inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughRoom);
        }
        return false;
    }

    @Override
    public void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        super.setupMenu(blockMenuPreset);
        for (int i : MenuPreset.slotChunk1) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk2) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk3) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
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
    
    private static final class RandomizedOutput extends StrictOutput {

        private final ItemStack[] items;

        public RandomizedOutput(ItemStack[] outputs, int amount) {
            super(outputs[0], amount);
            this.items = outputs;
        }

        @Override
        public ItemStack getOutput() {
            return this.items[ThreadLocalRandom.current().nextInt(this.items.length)];
        }

    }

}
