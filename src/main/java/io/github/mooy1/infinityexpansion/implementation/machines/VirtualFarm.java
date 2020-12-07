package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.implementation.abstracts.Container;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.InfinityRecipes;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.lists.RecipeTypes;
import io.github.mooy1.infinityexpansion.utils.MathUtils;
import io.github.mooy1.infinityexpansion.utils.PresetUtils;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
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

/**
 * Grows crops in a virtual interface
 *
 * @author Mooy1
 */
public class VirtualFarm extends Container implements EnergyNetComponent, RecipeDisplayItem {

    public static final int ENERGY1 = 18;
    public static final int ENERGY2 = 90;
    public static final int ENERGY3 = 900;
    public static final int SPEED1 = 1;
    public static final int SPEED2 = 5;
    public static final int SPEED3 = 25;
    public static final int TIME = 300;
    private final Type type;

    private static final int[] OUTPUT_SLOTS = PresetUtils.largeOutput;
    private static final int[] INPUT_SLOTS = {
            PresetUtils.slot1 + 27
    };
    private static final int STATUS_SLOT = PresetUtils.slot1;

    public VirtualFarm(Type type) {
        super(type.getCategory(), type.getItem(), type.getRecipeType(), type.getRecipe());
        this.type = type;

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

    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : PresetUtils.slotChunk1) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.slotChunk1) {
            blockMenuPreset.addItem(i + 27, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.largeOutputBorder) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemRed, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        if (getProgress(b) == null) {
            setProgress(b, 0);
        }
    }

    @Override
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        if (flow == ItemTransportFlow.INSERT) {
            return INPUT_SLOTS;
        } else if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        } else {
            return new int[0];
        }
    }

    @Override
    public void tick(@Nonnull Block b, @Nonnull Location l, @Nonnull BlockMenu inv) {
        int energy = type.getEnergy();
        int charge = getCharge(l);
        boolean playerWatching = inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty();

        if (charge < energy) { //not enough energy

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughEnergy);
            }
            
            return;

        }

        int progress = Integer.parseInt(getProgress(b));

        if (progress == 0) { //try to start
            ItemStack input = inv.getItemInSlot(INPUT_SLOTS[0]);

            if (input == null) {

                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, "&9Input a seed"));
                }

            } else {

                String inputType = getInputType(input.getType());

                if (inputType == null) {

                    if (playerWatching) {
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

                    setProgress(b, type.getSpeed());
                    setType(b, inputType);
                    inv.consumeItem(INPUT_SLOTS[0], 1);
                    setCharge(l, charge - energy);

                    if (playerWatching) {
                        inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                                "&aPlanting... (" + type.getSpeed() + "/" + TIME + ")"));
                    }

                }
            }
            
            return;
        }
        
        if (progress < TIME) { //progress

            setProgress(b, progress + type.getSpeed());
            setCharge(l, charge - energy);

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                        "&aGrowing... (" + (progress + type.getSpeed()) + "/" + TIME + ")"));
            }
            
            return;
        }
        
        //done
        int type = Integer.parseInt(getType(b));

        ItemStack output1 = new ItemStack(OUTPUTS[type], OUTPUT_AMOUNTS[type]);
        ItemStack output2 = new ItemStack(INPUTS[type], MathUtils.randomFromRange(1, 2));

        if (!inv.fits(output1, OUTPUT_SLOTS)) {

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughRoom);
            }

        } else {
            inv.pushItem(output1, OUTPUT_SLOTS);
            if (inv.fits(output2, INPUT_SLOTS)) inv.pushItem(output2, INPUT_SLOTS);

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aHarvesting..."));
            }

            setProgress(b, 0);
            setType(b, null);
            setCharge(l, charge - energy);

        }
    }

    /**
     * This method gets the input type
     *
     * @param input input item
     * @return type if any
     */
    @Nullable
    private String getInputType(Material input) {
        for (int i = 0; i < INPUTS.length; i++) {
            if (input == INPUTS[i]) return String.valueOf(i);
        }

        return null;
    }

    private void setType(Block b, String type) {
        setBlockData(b, "type", type);
    }

    private String getType(Block b) {
        return getBlockData(b.getLocation(), "type");
    }

    private void setProgress(Block b, int progress) {
        setBlockData(b, "progress", String.valueOf(progress));
    }

    private String getProgress(Block b) {
        return getBlockData(b.getLocation(), "progress");
    }

    private void setBlockData(Block b, String key, String data) {
        BlockStorage.addBlockInfo(b, key, data);
    }

    private String getBlockData(Location l, String key) {
        return BlockStorage.getLocationInfo(l, key);
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return type.getEnergy() * 2;
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

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {
        BASIC(ENERGY1, SPEED1, Categories.BASIC_MACHINES, Items.BASIC_VIRTUAL_FARM, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS),
                Items.MAGSTEEL, new ItemStack(Material.DIAMOND_HOE), Items.MAGSTEEL,
                Items.MACHINE_CIRCUIT, new ItemStack(Material.GRASS_BLOCK), Items.MACHINE_CIRCUIT
        }),
        ADVANCED(ENERGY2, SPEED2, Categories.ADVANCED_MACHINES, Items.ADVANCED_VIRTUAL_FARM, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.HARDENED_GLASS, SlimefunItems.HARDENED_GLASS, SlimefunItems.HARDENED_GLASS,
                Items.MAGNONIUM, Items.BASIC_VIRTUAL_FARM, Items.MAGNONIUM,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(ENERGY3, SPEED3, Categories.INFINITY_CHEAT, Items.INFINITY_VIRTUAL_FARM, RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(Items.INFINITY_VIRTUAL_FARM));

        private final int energy;
        private final int speed;
        @Nonnull
        private final Category category;
        private final SlimefunItemStack item;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}