package io.github.mooy1.infinityexpansion.items.machines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lombok.AllArgsConstructor;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.items.Machines;
import io.github.mooy1.infinityexpansion.items.abstracts.AbstractMachine;
import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.ItemGroup;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

/**
 * Constructs singularities form many items
 *
 * @author Mooy1
 */
public final class SingularityConstructor extends AbstractMachine implements RecipeDisplayItem {

    private static final List<Recipe> RECIPE_LIST = new ArrayList<>();
    private static final Map<String, Pair<Integer, Recipe>> RECIPE_MAP = new HashMap<>();
    public static final RecipeType TYPE = new RecipeType(InfinityExpansion.inst().getKey("singularity_constructor"), Machines.SINGULARITY_CONSTRUCTOR, (stacks, itemStack) -> {
        int amt = 0;
        for (ItemStack item : stacks) {
            if (item != null) {
                amt += item.getAmount();
            }
        }
        String id = StackUtils.getIDorType(stacks[0]);
        Recipe recipe = new Recipe((SlimefunItemStack) itemStack, stacks[0], id, amt);
        RECIPE_LIST.add(recipe);
        RECIPE_MAP.put(id, new Pair<>(RECIPE_LIST.size() - 1, recipe));
    });

    private static final String PROGRESS = "progress";
    private static final int STATUS_SLOT = 13;
    private static final int INPUT_SLOT = 10;
    private static final int OUTPUT_SLOT = 16;

    private final int speed;
    private final int energy;

    public SingularityConstructor(ItemGroup category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe, int energy, int speed) {
        super(category, item, type, recipe);
        this.speed = speed;
        this.energy = energy;
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu menu, @Nonnull Location l) {
        int progress = Util.getIntData(PROGRESS, l);
        Integer progressID = getProgressID(l);

        menu.dropItems(l, OUTPUT_SLOT, INPUT_SLOT);

        if (progress > 0 && progressID != null) {

            Recipe triplet = RECIPE_LIST.get(progressID);

            if (triplet != null) {
                ItemStack drop = new CustomItem(triplet.input, 64);

                int stacks = progress / 64;

                if (stacks > 0) {
                    for (int i = 0 ; i < stacks ; i++) {
                        e.getBlock().getWorld().dropItemNaturally(l, drop);
                    }
                }

                int remainder = progress % 64;

                if (remainder > 0) {
                    drop.setAmount(remainder);
                    e.getBlock().getWorld().dropItemNaturally(l, drop);
                }
            }
        }

        setProgressID(l, null);
        setProgress(l, 0);
    }

    @Override
    protected boolean process(@Nonnull BlockMenu menu, @Nonnull Block b) {
        ItemStack input = menu.getItemInSlot(INPUT_SLOT);
        String inputID;
        if (input == null) {
            inputID = null;
        }
        else {
            inputID = StackUtils.getIDorType(input);
        }

        // load data
        Integer progressID = getProgressID(b.getLocation());
        int progress = Util.getIntData(PROGRESS, b.getLocation());

        Recipe triplet;
        boolean takeCharge = false;

        if (progressID == null || progress == 0) {
            // not started
            if (inputID != null) {
                Pair<Integer, Recipe> pair = RECIPE_MAP.get(inputID);
                if (pair != null) {
                    progress = Math.min(this.speed, input.getAmount());
                    input.setAmount(input.getAmount() - progress);
                    progressID = pair.getFirstValue();
                    triplet = pair.getSecondValue();
                    takeCharge = true;
                }
                else {
                    // invalid input
                    triplet = null;
                }
            }
            else {
                // still haven't started
                triplet = null;
            }
        }
        else {
            // started
            triplet = RECIPE_LIST.get(progressID);
            if (inputID != null) {
                int max = Math.min(triplet.amount - progress, Math.min(this.speed, input.getAmount()));
                if (max > 0) {
                    if (triplet.id.equals(inputID)) {
                        progress += max;
                        input.setAmount(input.getAmount() - max);
                        takeCharge = true;
                    } // invalid input
                } // already done
            }
        }

        // show status and output if done
        if (triplet != null) {
            if (progress >= triplet.amount && menu.fits(triplet.output, OUTPUT_SLOT)) {
                menu.pushItem(triplet.output.clone(), OUTPUT_SLOT);
                progress = 0;
                progressID = null;

                if (menu.hasViewer()) {
                    menu.replaceExistingItem(STATUS_SLOT, new CustomItem(
                            Material.LIME_STAINED_GLASS_PANE,
                            "&aConstructing " + triplet.output.getDisplayName() + "...",
                            "&7Complete"
                    ));
                }
            }
            else if (menu.hasViewer()) {
                menu.replaceExistingItem(STATUS_SLOT, new CustomItem(
                        Material.LIME_STAINED_GLASS_PANE,
                        "&aConstructing " + triplet.output.getDisplayName() + "...",
                        "&7" + progress + " / " + triplet.amount
                ));
            }
        }
        else if (menu.hasViewer()) {
            invalidInput(menu);
        }

        // save data
        setProgressID(b.getLocation(), progressID);
        setProgress(b.getLocation(), progress);

        return takeCharge;
    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        super.setupMenu(blockMenuPreset);
        blockMenuPreset.drawBackground(MenuPreset.INPUT_ITEM, MenuPreset.INPUT_BORDER);
        blockMenuPreset.drawBackground(MenuPreset.STATUS_ITEM, MenuPreset.STATUS_BORDER);
        blockMenuPreset.drawBackground(MenuPreset.OUTPUT_ITEM, MenuPreset.OUTPUT_BORDER);
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
        if (flow == ItemTransportFlow.INSERT) {
            return new int[] { INPUT_SLOT };
        }
        else if (flow == ItemTransportFlow.WITHDRAW) {
            return new int[] { OUTPUT_SLOT };
        }
        else {
            return new int[0];
        }
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu blockMenu, @Nonnull Block block) {
        invalidInput(blockMenu);
    }

    private static void invalidInput(BlockMenu menu) {
        menu.replaceExistingItem(STATUS_SLOT, new CustomItem(
                Material.RED_STAINED_GLASS_PANE,
                "&cInput a valid material to start"
        ));
    }

    private static void setProgress(Location l, int progress) {
        BlockStorage.addBlockInfo(l, "progress", String.valueOf(progress));
    }

    private static void setProgressID(Location l, @Nullable Integer progressID) {
        if (progressID == null) {
            BlockStorage.addBlockInfo(l, "progressid", null);
        }
        else {
            BlockStorage.addBlockInfo(l, "progressid", String.valueOf(progressID));
        }
    }

    @Nullable
    private static Integer getProgressID(Location l) {
        String id = BlockStorage.getLocationInfo(l, "progressid");
        if (id == null) {
            return null;
        }
        else {
            try {
                return Integer.parseInt(id);
            } catch (NumberFormatException e) {
                setProgressID(l, null);
                return null;
            }
        }
    }

    @Override
    public int getCapacity() {
        return this.energy * 2;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> items = new ArrayList<>();

        for (Recipe recipe : RECIPE_LIST) {
            items.add(recipe.input);
            items.add(recipe.output);
        }

        return items;
    }

    @AllArgsConstructor
    private static final class Recipe {

        private final SlimefunItemStack output;
        private final ItemStack input;
        private final String id;
        private final int amount;

    }

}
