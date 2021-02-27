package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.materials.Items;
import io.github.mooy1.infinityexpansion.implementation.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.abstracts.AbstractMachine;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
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
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Grows crops in a virtual interface
 *
 * @author Mooy1
 */
public final class VirtualFarm extends AbstractMachine implements RecipeDisplayItem {
    
    public static void setup(InfinityExpansion plugin) {
        new VirtualFarm(Categories.BASIC_MACHINES, BASIC, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS),
                Items.MAGSTEEL, new ItemStack(Material.DIAMOND_HOE), Items.MAGSTEEL,
                Items.MACHINE_CIRCUIT, new ItemStack(Material.GRASS_BLOCK), Items.MACHINE_CIRCUIT
        }, 18, 1).register(plugin);
        new VirtualFarm(Categories.ADVANCED_MACHINES, ADVANCED, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.HARDENED_GLASS, SlimefunItems.HARDENED_GLASS, SlimefunItems.HARDENED_GLASS,
                Items.MAGNONIUM, BASIC, Items.MAGNONIUM,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }, 90, 5).register(plugin);
        new VirtualFarm(Categories.INFINITY_CHEAT, INFINITY, InfinityWorkbench.TYPE, new ItemStack[] {
                new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS),
                new ItemStack(Material.GLASS), null, null, null, null, new ItemStack(Material.GLASS),
                new ItemStack(Material.GLASS), null, null, null, null, new ItemStack(Material.GLASS),
                new ItemStack(Material.GLASS), new ItemStack(Material.GRASS_BLOCK), new ItemStack(Material.GRASS_BLOCK), new ItemStack(Material.GRASS_BLOCK), new ItemStack(Material.GRASS_BLOCK), new ItemStack(Material.GLASS),
                Items.MACHINE_PLATE, SlimefunItems.CROP_GROWTH_ACCELERATOR_2, ADVANCED, ADVANCED, SlimefunItems.CROP_GROWTH_ACCELERATOR_2, Items.MACHINE_PLATE,
                Items.MACHINE_PLATE, Items.INFINITE_CIRCUIT, Items.INFINITE_CORE, Items.INFINITE_CORE, Items.INFINITE_CIRCUIT, Items.MACHINE_PLATE
        }, 900, 25).register(plugin);
    }
    
    public static final SlimefunItemStack BASIC = new SlimefunItemStack(
            "BASIC_VIRTUAL_FARM",
            Material.GRASS_BLOCK,
            "&9Basic &aVirtual Farm",
            "&7Automatically grows, harvests, and replants crops",
            "",
            LorePreset.speed(1),
            LorePreset.energyPerSecond(18)
    );
    public static final SlimefunItemStack ADVANCED = new SlimefunItemStack(
            "ADVANCED_VIRTUAL_FARM",
            Material.CRIMSON_NYLIUM,
            "&cAdvanced &aVirtual Farm",
            "&7Automatically grows, harvests, and replants crops",
            "",
            LorePreset.speed(5),
            LorePreset.energyPerSecond(90)
    );
    public static final SlimefunItemStack INFINITY = new SlimefunItemStack(
            "INFINITY_VIRTUAL_FARM",
            Material.WARPED_NYLIUM,
            "&bInfinity &aVirtual Farm",
            "&7Automatically grows, harvests, and replants crops",
            "",
            LorePreset.speed(25),
            LorePreset.energyPerSecond(900)
    );
    
    private static final int TIME = 600;
    private static final int[] OUTPUT_SLOTS = Util.largeOutput;
    private static final int[] INPUT_SLOTS = {
            MenuPreset.slot1 + 27
    };
    private static final int STATUS_SLOT = MenuPreset.slot1;

    private final int speed;
    private final int energy;
    
    private VirtualFarm(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int energy, int speed) {
        super(category, item, recipeType, recipe);
        this.speed = speed;
        this.energy = energy;

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), OUTPUT_SLOTS);
                inv.dropItems(b.getLocation(), INPUT_SLOTS);
            }

            setProgress(b, 0);
            setBlockData(b, "type", null);

            return true;
        });
    }

    @Override
    protected boolean process(@Nonnull BlockMenu inv, @Nonnull Block b, @Nonnull Config data) {
        int progress = Integer.parseInt(getProgress(b));

        if (progress == 0) { //try to start
            ItemStack input = inv.getItemInSlot(INPUT_SLOTS[0]);

            if (input == null) {

                if (inv.hasViewer()) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, "&9Input a seed"));
                }

            } else {

                String inputType = getInputType(input.getType());

                if (inputType == null) {

                    if (inv.hasViewer()) {
                        inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cInput a seed!"));
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
                        inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aPlanting... (" + this.speed + "/" + TIME + ")"));
                    }

                    return true;
                }
            }

            return false;
        }

        if (progress < TIME) { //progress

            setProgress(b, progress + this.speed);

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                        "&aGrowing... (" + (progress + this.speed) + "/" + TIME + ")"));
            }

            return true;
        }

        //done
        int type = Integer.parseInt(getType(b));

        ItemStack output1 = new ItemStack(OUTPUTS[type], OUTPUT_AMOUNTS[type]);
        ItemStack output2 = new ItemStack(INPUTS[type], 1 + ThreadLocalRandom.current().nextInt(1));

        if (!inv.fits(output1, OUTPUT_SLOTS)) {

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughRoom);
            }
            return false;

        } else {
            inv.pushItem(output1, OUTPUT_SLOTS);
            if (inv.fits(output2, INPUT_SLOTS)) inv.pushItem(output2, INPUT_SLOTS);

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aHarvesting..."));
            }

            setProgress(b, 0);
            setType(b, null);
            return true;

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
        if (getProgress(b) == null) {
            setProgress(b, 0);
        }
    }
    
    @Nullable
    private static String getInputType(Material input) {
        for (int i = 0; i < INPUTS.length; i++) {
            if (input == INPUTS[i]) return String.valueOf(i);
        }

        return null;
    }

    private static void setType(Block b, String type) {
        setBlockData(b, "type", type);
    }

    private static String getType(Block b) {
        return getBlockData(b.getLocation(), "type");
    }

    private static void setProgress(Block b, int progress) {
        setBlockData(b, "progress", String.valueOf(progress));
    }

    private static String getProgress(Block b) {
        return getBlockData(b.getLocation(), "progress");
    }

    private static void setBlockData(Block b, String key, String data) {
        BlockStorage.addBlockInfo(b, key, data);
    }

    private static String getBlockData(Location l, String key) {
        return BlockStorage.getLocationInfo(l, key);
    }
    
    @Override
    public int getCapacity() {
        return this.energy * 2;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < INPUTS.length; i++) {
            items.add(new ItemStack(INPUTS[i]));
            items.add(new ItemStack(OUTPUTS[i], OUTPUT_AMOUNTS[i]));
        }

        return items;
    }

    private static final Material[] INPUTS = {
            Material.WHEAT_SEEDS,
            Material.CARROT,
            Material.POTATO,
            Material.BEETROOT_SEEDS,
            Material.PUMPKIN_SEEDS,
            Material.MELON_SEEDS,
            Material.SUGAR_CANE,
            Material.COCOA_BEANS,
            Material.CACTUS,
            Material.BAMBOO,
            Material.CHORUS_FLOWER,
            Material.NETHER_WART,
    };

    private static final Material[] OUTPUTS = {
            Material.WHEAT,
            Material.CARROT,
            Material.POTATO,
            Material.BEETROOT,
            Material.PUMPKIN,
            Material.MELON_SLICE,
            Material.SUGAR_CANE,
            Material.COCOA_BEANS,
            Material.CACTUS,
            Material.BAMBOO,
            Material.CHORUS_FRUIT,
            Material.NETHER_WART,
    };

    private static final int[] OUTPUT_AMOUNTS = {
            2, 3, 4, 2, 1, 6, 2, 3, 2, 4, 4, 2
    };
    
}