package io.github.mooy1.infinityexpansion.items.machines;

import java.util.ArrayList;
import java.util.List;
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
import io.github.mooy1.infinityexpansion.items.abstracts.AbstractMachine;
import io.github.mooy1.infinityexpansion.items.materials.Oscillator;
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
 * Mines stuff
 *
 * @author Mooy1
 */
public final class Quarry extends AbstractMachine implements RecipeDisplayItem {
    
    private static final boolean ALLOW_NETHER_IN_OVERWORLD = InfinityExpansion.inst().getConfig().getBoolean("quarry-options.output-nether-materials-in-overworld");
    private static final int INTERVAL = InfinityExpansion.inst().getConfig().getInt("quarry-options.ticks-per-output", 1, 100);

    private static final ItemStack OSCILLATOR_INFO = new CustomItem(
            Material.CYAN_STAINED_GLASS_PANE, 
            "&bOscillator Slot",
            "&7Place a quarry oscillator to",
            "&7boost certain material's rates!"
    );
    private static final int[] OUTPUT_SLOTS = {
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44
    };
    private static final int OSCILLATOR_SLOT = 49;
    private static final int STATUS_SLOT = 4;
    
    private final int speed;
    private final int chance;
    private final int energy;
    private final Material[] outputs;
    
    public Quarry(Category category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe,
                  int energy, int speed, int chance, Material... outputs) {
        super(category, item, type, recipe);
        
        this.speed = speed;
        this.chance = chance;
        this.outputs = outputs;
        this.energy = energy;
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu menu, @Nonnull Location l) {
        menu.dropItems(l, OUTPUT_SLOTS);
        menu.dropItems(l, OSCILLATOR_SLOT);
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
            if (i == OSCILLATOR_SLOT - 1) {
                blockMenuPreset.addItem(i, OSCILLATOR_INFO, ChestMenuUtils.getEmptyClickHandler());
                blockMenuPreset.addItem(i + 2, OSCILLATOR_INFO, ChestMenuUtils.getEmptyClickHandler());
                i+=3;
            }
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

        items.add(new ItemStack(Material.COBBLESTONE, this.speed));
        for (Material mat : this.outputs) {
            items.add(new ItemStack(mat, this.speed));
        }

        return items;
    }

    @Nonnull
    @Override
    public String getRecipeSectionLabel(@Nonnull Player p) {
        return "&7Mines:";
    }
    
    private static final ItemStack MINING = new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aMining...");
    
    @Override
    protected boolean process(@Nonnull BlockMenu inv, @Nonnull Block b) {
        if (inv.hasViewer()) {
            inv.replaceExistingItem(STATUS_SLOT, MINING);
        }
        
        if ((InfinityExpansion.inst().getGlobalTick() % INTERVAL) != 0) {
            return true;
        }
        
        ItemStack outputItem;

        if (ThreadLocalRandom.current().nextInt(this.chance) == 0) {
            Material oscillator = Oscillator.getOscillator(inv.getItemInSlot(OSCILLATOR_SLOT));
            if (oscillator == null || ThreadLocalRandom.current().nextBoolean()) {
                Material outputType = this.outputs[ThreadLocalRandom.current().nextInt(this.outputs.length)];
                if (!ALLOW_NETHER_IN_OVERWORLD && b.getWorld().getEnvironment() != World.Environment.NETHER &&
                        (outputType == Material.QUARTZ || outputType == Material.NETHERITE_INGOT || outputType == Material.NETHERRACK)
                ) {
                    outputItem = new ItemStack(Material.COBBLESTONE, this.speed);
                } else {
                    outputItem = new ItemStack(outputType, this.speed);
                }
            } else {
                outputItem = new ItemStack(oscillator, this.speed);
            }
        } else {
            outputItem = new ItemStack(Material.COBBLESTONE, this.speed);
        }
        
        if (!inv.fits(outputItem, OUTPUT_SLOTS)) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.NO_ROOM);
            }
            return false;
        }
        
        inv.pushItem(outputItem, OUTPUT_SLOTS);
        return true;
    }

}
