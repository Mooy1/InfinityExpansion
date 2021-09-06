package io.github.mooy1.infinityexpansion.items.quarries;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinitylib.machines.AbstractMachineBlock;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;

/**
 * Mines stuff
 *
 * @author Mooy1
 */
@ParametersAreNonnullByDefault
public final class Quarry extends AbstractMachineBlock implements RecipeDisplayItem {

    private static final boolean ALLOW_NETHER_IN_OVERWORLD =
            InfinityExpansion.config().getBoolean("quarry-options.output-nether-materials-in-overworld");
    private static final int INTERVAL =
            InfinityExpansion.config().getInt("quarry-options.ticks-per-output", 1, 100);
    private static final ItemStack MINING = new CustomItemStack(Material.LIME_STAINED_GLASS_PANE, "&aMining...");
    private static final ItemStack OSCILLATOR_INFO = new CustomItemStack(
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
    private final Material[] outputs;

    public Quarry(ItemGroup category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe,
                  int speed, int chance, Material... outputs) {
        super(category, item, type, recipe);

        this.speed = speed;
        this.chance = chance;
        this.outputs = outputs;
    }

    @Override
    protected void setup(@Nonnull BlockMenuPreset blockMenuPreset) {
        blockMenuPreset.drawBackground(new int[] {
                0, 1, 2, 3, 4, 5, 6, 7, 8, 45, 46, 47, 51, 52, 53
        });
        blockMenuPreset.addItem(48, OSCILLATOR_INFO, ChestMenuUtils.getEmptyClickHandler());
        blockMenuPreset.addItem(50, OSCILLATOR_INFO, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    protected int[] getInputSlots(DirtyChestMenu menu, ItemStack item) {
        return new int[0];
    }

    @Override
    protected int[] getInputSlots() {
        return new int[] { OSCILLATOR_SLOT };
    }

    @Override
    protected int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {

    }

    @Override
    protected boolean process(Block b, BlockMenu inv) {
        if (inv.hasViewer()) {
            inv.replaceExistingItem(STATUS_SLOT, MINING);
        }

        if (InfinityExpansion.slimefunTickCount() % INTERVAL != 0) {
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
                }
                else {
                    outputItem = new ItemStack(outputType, this.speed);
                }
            }
            else {
                outputItem = new ItemStack(oscillator, this.speed);
            }
        }
        else {
            outputItem = new ItemStack(Material.COBBLESTONE, this.speed);
        }

        inv.pushItem(outputItem, OUTPUT_SLOTS);
        return true;
    }

    @Override
    protected int getStatusSlot() {
        return STATUS_SLOT;
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

}
