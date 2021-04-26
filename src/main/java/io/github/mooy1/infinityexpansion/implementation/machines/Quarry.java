package io.github.mooy1.infinityexpansion.implementation.machines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.abstracts.AbstractMachine;
import io.github.mooy1.infinitylib.slimefun.presets.MenuPreset;
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
 * Mines stuff
 *
 * @author Mooy1
 */
public final class Quarry extends AbstractMachine implements RecipeDisplayItem {
    
    private static final int[] OUTPUT_SLOTS = {
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44
    };
    private static final int STATUS_SLOT = 4;
    private static final int INTERVAL = InfinityExpansion.inst().getConfig().getInt("quarry-options.ticks-per-output", 1, 100);
    private static final boolean ALLOW_NETHER_IN_OVERWORLD = InfinityExpansion.inst().getConfig().getBoolean("quarry-options.output-nether-materials-in-overworld");

    private final ItemStack cobble;
    private final int chance;
    private final int energy;
    private final ItemStack[] outputs;
    
    public Quarry(Category category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe, int energy, int speed, int chance, ItemStack[] outputs) {
        super(category, item, type, recipe);
        this.cobble = new ItemStack(Material.COBBLESTONE, speed);
        this.chance = chance;
        this.outputs = outputs;
        this.energy = energy;
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu menu, @Nonnull Location l) {
        menu.dropItems(l, OUTPUT_SLOTS);
    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        super.setupMenu(blockMenuPreset);
        for (int i = 0 ; i < 4 ; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i = 5 ; i < 9 ; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i = 45 ; i < 54 ; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
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

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        }
        return new int[0];
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {

    }
    
    @Override
    public int getCapacity() {
        return this.energy * 2;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();

        items.add(this.cobble);
        items.addAll(Arrays.asList(this.outputs));

        return items;
    }

    @Nonnull
    @Override
    public String getRecipeSectionLabel(@Nonnull Player p) {
        return "&7Mines:";
    }
    
    private static final ItemStack MINING = new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aMining...");
    
    @Override
    protected boolean process(@Nonnull BlockMenu inv, @Nonnull Block b, @Nonnull Config data) {
        if (inv.hasViewer()) {
            inv.replaceExistingItem(STATUS_SLOT, MINING);
        }
        
        if ((InfinityExpansion.inst().getGlobalTick() % INTERVAL) != 0) {
            return true;
        }

        ItemStack outputItem;

        Random random = ThreadLocalRandom.current();
        
        if (random.nextInt(this.chance) == 0) {
            outputItem = this.outputs[random.nextInt(this.outputs.length)];
            Material outputType = outputItem.getType();
            if (!ALLOW_NETHER_IN_OVERWORLD && b.getWorld().getEnvironment() != World.Environment.NETHER &&
                    (outputType == Material.QUARTZ || outputType == Material.NETHERITE_INGOT || outputType == Material.NETHERRACK)
            ) {
                outputItem = this.cobble;
            }
        } else {
            outputItem = this.cobble;
        }

        if (!inv.fits(outputItem, OUTPUT_SLOTS)) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughRoom);
            }
            return false;
        }
        
        inv.pushItem(outputItem.clone(), OUTPUT_SLOTS);
        return true;
    }

}
