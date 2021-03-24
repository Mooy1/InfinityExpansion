package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.Categories;
import io.github.mooy1.infinityexpansion.implementation.abstracts.AbstractMachine;
import io.github.mooy1.infinityexpansion.implementation.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.implementation.materials.Items;
import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.slimefun.presets.LorePreset;
import io.github.mooy1.infinitylib.slimefun.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import lombok.NonNull;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Grows trees in a virtual interface
 *
 * @author Mooy1
 */
public final class TreeGrower extends AbstractMachine implements RecipeDisplayItem {

    public static void setup(InfinityExpansion plugin) {
        new TreeGrower(Categories.BASIC_MACHINES, BASIC, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS),
                Items.MAGSTEEL, new ItemStack(Material.PODZOL), Items.MAGSTEEL,
                Items.MACHINE_CIRCUIT, VirtualFarm.BASIC, Items.MACHINE_CIRCUIT
        }, 36, 1).register(plugin);
        new TreeGrower(Categories.ADVANCED_MACHINES, ADVANCED, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.HARDENED_GLASS, SlimefunItems.HARDENED_GLASS, SlimefunItems.HARDENED_GLASS,
                Items.MAGNONIUM, BASIC,Items.MAGNONIUM,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }, 180, 5).register(plugin);
        new TreeGrower(Categories.INFINITY_CHEAT, INFINITY, InfinityWorkbench.TYPE, new ItemStack[] {
                new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS),
                new ItemStack(Material.GLASS), SlimefunItems.TREE_GROWTH_ACCELERATOR, null, null, SlimefunItems.TREE_GROWTH_ACCELERATOR, new ItemStack(Material.GLASS),
                new ItemStack(Material.GLASS), ADVANCED, null, null, ADVANCED, new ItemStack(Material.GLASS),
                new ItemStack(Material.GLASS), SlimefunItems.TREE_GROWTH_ACCELERATOR, null, null, SlimefunItems.TREE_GROWTH_ACCELERATOR, new ItemStack(Material.GLASS),
                Items.MACHINE_PLATE, new ItemStack(Material.PODZOL), new ItemStack(Material.PODZOL), new ItemStack(Material.PODZOL), new ItemStack(Material.PODZOL), Items.MACHINE_PLATE,
                Items.MACHINE_PLATE, Items.INFINITE_CIRCUIT, Items.INFINITE_CORE, Items.INFINITE_CORE, Items.INFINITE_CIRCUIT, Items.MACHINE_PLATE
        }, 1800, 25).register(plugin);
    }

    public static final SlimefunItemStack BASIC = new SlimefunItemStack(
            "BASIC_TREE_GROWER",
            Material.STRIPPED_OAK_WOOD,
            "&9Basic &2Tree Grower",
            "&7Automatically grows, harvests, and replants trees",
            "",
            LorePreset.speed(1),
            LorePreset.energyPerSecond(36)
    );
    public static final SlimefunItemStack ADVANCED = new SlimefunItemStack(
            "ADVANCED_TREE_GROWER",
            Material.STRIPPED_ACACIA_WOOD,
            "&cAdvanced &2Tree Grower",
            "&7Automatically grows, harvests, and replants trees",
            "",
            LorePreset.speed(5),
            LorePreset.energyPerSecond(180)
    );
    public static final SlimefunItemStack INFINITY = new SlimefunItemStack(
            "INFINITY_TREE_GROWER",
            Material.STRIPPED_WARPED_HYPHAE,
            "&bInfinity &2Tree Grower",
            "&7Automatically grows, harvests, and replants trees",
            "",
            LorePreset.speed(25),
            LorePreset.energyPerSecond(1800)
    );

    private static final int TIME = 1200;

    private static final int[] OUTPUT_SLOTS = Util.largeOutput;
    private static final int[] INPUT_SLOTS = {
            MenuPreset.slot1 + 27
    };
    private static final int STATUS_SLOT = MenuPreset.slot1;

    private final int speed;
    private final int energy;

    private TreeGrower(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int energy, int speed) {
        super(category, item, recipeType, recipe);
        this.speed = speed;
        this.energy = energy;
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu inv, @Nonnull Location l) {
        inv.dropItems(l, OUTPUT_SLOTS);
        inv.dropItems(l, INPUT_SLOTS);
        
        setProgress(e.getBlock(), 0);
        setType(e.getBlock(), null);
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
    protected boolean process(@Nonnull BlockMenu inv, @Nonnull Block b, @Nonnull Config data) {
        int progress = Integer.parseInt(getProgress(b));

        if (progress == 0) { //try to start
            ItemStack input = inv.getItemInSlot(INPUT_SLOTS[0]);

            if (input == null) {

                if (inv.hasViewer()) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, "&9Input a sapling"));
                }

            } else {

                String inputType = getInputType(input);

                if (inputType == null) {

                    if (inv.hasViewer()) {
                        inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cInput a sapling!"));
                    }

                    for (int slot : OUTPUT_SLOTS) {
                        if (inv.getItemInSlot(slot) == null) {
                            inv.replaceExistingItem(slot, input);
                            inv.consumeItem(INPUT_SLOTS[0], input.getAmount());
                            break;
                        }
                    }

                } else { //start

                    setProgress(b, this.speed);
                    setType(b, inputType);
                    inv.consumeItem(INPUT_SLOTS[0], 1);

                    if (inv.hasViewer()) {
                        inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                                "&aPlanting... (" + this.speed + "/" + TIME + ")"));
                    }

                    return true;
                }
            }
            return false;
        }

        if (progress < TIME) { //progress

            setProgress(b, progress + this.speed);

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aGrowing... (" + (progress + this.speed) + "/" + TIME + ")"));
            }
            return true;
        }

        //done
        String type = getType(b);

        Random rand = ThreadLocalRandom.current();
        ItemStack output1 = new ItemStack(Objects.requireNonNull(Material.getMaterial(type + "_LOG")), 6 + rand.nextInt(6));
        ItemStack output2 = new ItemStack(Objects.requireNonNull(Material.getMaterial(type + "_LEAVES")), 8 + rand.nextInt(8));
        ItemStack output3 = new ItemStack(Objects.requireNonNull(Material.getMaterial(type + "_SAPLING")), 1 + rand.nextInt(1));

        if (!inv.fits(output1, OUTPUT_SLOTS)) {

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughRoom);
            }
            return false;

        } else {
            inv.pushItem(output1, OUTPUT_SLOTS);
            if (inv.fits(output2, OUTPUT_SLOTS)) inv.pushItem(output2, OUTPUT_SLOTS);
            if (inv.fits(output3, INPUT_SLOTS)) inv.pushItem(output3, INPUT_SLOTS);

            if (type.equals("OAK")) {
                ItemStack apple = new ItemStack(Material.APPLE);
                if (inv.fits(apple, OUTPUT_SLOTS)) inv.pushItem(apple, OUTPUT_SLOTS);
            }

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aHarvesting..."));
            }

            setProgress(b, 0);
            setType(b, null);

            return true;

        }
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        if (getProgress(b) == null) {
            setProgress(b, 0);
        }
    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        super.setupMenu(blockMenuPreset);
        for (int i : MenuPreset.slotChunk1) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk1) {
            blockMenuPreset.addItem(i + 27, MenuPreset.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : Util.largeOutputBorder) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
    }

    @Nonnull
    @Override
    public int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, @Nonnull ItemStack item) {
        if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        }

        if (flow == ItemTransportFlow.INSERT && SlimefunTag.SAPLINGS.isTagged(item.getType())) {
            return INPUT_SLOTS;
        }

        return new int[0];
    }

    @Nullable
    private static String getInputType(@NonNull ItemStack input) {
        for (String recipe : INPUTS) {
            if (input.getType() == Material.getMaterial(recipe + "_SAPLING")) return recipe;
        }
        return null;
    }

    private static void setType(Block b, String type) {
        BlockStorage.addBlockInfo(b, "type", type);
    }

    private static String getType(Block b) {
        return BlockStorage.getLocationInfo(b.getLocation(), "type");
    }

    private static void setProgress(Block b, int progress) {
        BlockStorage.addBlockInfo(b, "progress", String.valueOf(progress));
    }

    private static String getProgress(Block b) {
        return BlockStorage.getLocationInfo(b.getLocation(), "progress");
    }

    @Override
    public int getCapacity() {
        return this.energy * 2;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();

        for (String input : INPUTS) {
            items.add(new ItemStack(Objects.requireNonNull(Material.getMaterial(input + "_SAPLING"))));
            items.add(new ItemStack(Objects.requireNonNull(Material.getMaterial(input + "_LOG"))));
        }
        return items;
    }

    private static final String[] INPUTS = {
            "OAK",
            "DARK_OAK",
            "ACACIA",
            "SPRUCE",
            "BIRCH",
            "JUNGLE"
    };

}
