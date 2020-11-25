package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.implementation.template.Machine;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.MathUtils;
import io.github.mooy1.infinityexpansion.utils.PresetUtils;
import io.github.mooy1.infinityexpansion.utils.StackUtils;
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
import java.util.Objects;

/**
 * Machines that convert 1 item to another with energy
 *
 * @author Mooy1
 */
public class ConversionMachine extends Machine implements RecipeDisplayItem, EnergyNetComponent {

    public static final int TIME = 4;
    public static final int FREEZER_SPEED = 2;
    public static final int FREEZER_ENERGY = 120;
    public static final int URANIUM_SPEED = 1;
    public static final int URANIUM_ENERGY = 360;
    public static final int DUST_SPEED = 4;
    public static final int DUST_ENERGY = 300;
    public static final int DECOM_SPEED = 4;
    public static final int DECOM_ENERGY = 180;
    private static final int[] INPUT_SLOTS = {PresetUtils.slot1};
    private static final int[] OUTPUT_SLOTS = {PresetUtils.slot3};
    private static final int STATUS_SLOT = PresetUtils.slot2;
    private final Type type;

    public ConversionMachine(Type type) {
        super(type.getCategory(), type.getItem(), type.getRecipeType(), type.getRecipe());
        this.type = type;

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                Location l = b.getLocation();
                inv.dropItems(l, OUTPUT_SLOTS);
                inv.dropItems(l, INPUT_SLOTS);
            }

            setProgress(b, 0);

            return true;
        });
    }

    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : PresetUtils.slotChunk1) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.slotChunk2) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.slotChunk3) {
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
        boolean playerWatching = inv.hasViewer();

        if (charge < energy) { //not enough energy
            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughEnergy);
            }
            return;
        }
        ItemStack input = inv.getItemInSlot(INPUT_SLOTS[0]);

        if (input == null) {
            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.inputAnItem);
            }
            return;
        }

        ItemStack output = getOutput(input);

        if (output == null) {
            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.invalidInput);
            }
            return;
        }

        int progress = Integer.parseInt(getProgress(b));
        if (progress < TIME) {

            inv.consumeItem(INPUT_SLOTS[0], 1);
            setProgress(b, progress + type.getSpeed());
            removeCharge(l, energy);
            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aConverting..."));
            }
            return;
        }

        if (inv.fits(output, OUTPUT_SLOTS)) {
            try {
                inv.consumeItem(INPUT_SLOTS[0], 1);
                inv.pushItem(output, OUTPUT_SLOTS);
                removeCharge(l, energy);
                setProgress(b, type.getSpeed());
                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aConverted!"));
                }

            } catch (NullPointerException ignored) { }

        } else {

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughRoom);
            }
        }
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

        ItemStack[] inputs = type.getInput();
        ItemStack[] outputs = type.getOutput();

        if (inputs.length == outputs.length) { //1 to 1
            for (int i = 0 ; i < inputs.length ; i++) {
                items.add(inputs[i]);
                items.add(outputs[i]);
            }
        } else { //each input gets each output
            for (ItemStack input : inputs) {
                for (ItemStack output : outputs) {
                    items.add(input);
                    items.add(output);
                }
            }
        }

        return items;
    }

    @Nullable
    private ItemStack getOutput(@Nonnull ItemStack input) {
        int i = 0;
        for (ItemStack correctInput : type.getInput()) {
            if (Objects.equals(StackUtils.getIDFromItem(input), StackUtils.getIDFromItem(correctInput))) {
                if (type.isRandom()) {
                    return MathUtils.randomOutput(type.getOutput());
                } else {
                    return type.output[i].clone();
                }

            }
            i++;
        }
        return null;
    }

    private void setProgress(Block b, int progress) {
        BlockStorage.addBlockInfo(b.getLocation(), "progress", String.valueOf(progress));
    }

    private String getProgress(Block b) {
        return BlockStorage.getLocationInfo(b.getLocation(), "progress");
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {
        FREEZER(Categories.ADVANCED_MACHINES, Items.EXTREME_FREEZER, FREEZER_ENERGY, FREEZER_SPEED, FREEZER_INPUT, FREEZER_OUTPUT,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                        SlimefunItems.FREEZER_2, SlimefunItems.FREEZER_2, SlimefunItems.FREEZER_2,
                        new ItemStack(Material.WATER_BUCKET), SlimefunItems.FLUID_PUMP, new ItemStack(Material.WATER_BUCKET),
                        Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT,
                }, false),
        DECOM(Categories.ADVANCED_MACHINES, Items.DECOMPRESSOR, DECOM_ENERGY, DECOM_SPEED, DECOM_INPUT, DECOM_OUTPUT,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                        Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE,
                        new ItemStack(Material.STICKY_PISTON), SlimefunItems.ELECTRIC_PRESS_2, new ItemStack(Material.STICKY_PISTON),
                        Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT,
                }, false),
        DUST(Categories.ADVANCED_MACHINES, Items.DUST_EXTRACTOR, DUST_ENERGY, DUST_SPEED, DUST_INPUT, DUST_OUTPUT,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.ELECTRIC_ORE_GRINDER_2, SlimefunItems.ELECTRIC_GOLD_PAN_3, SlimefunItems.ELECTRIC_DUST_WASHER_3,
                SlimefunItems.ELECTRIC_ORE_GRINDER_2, SlimefunItems.ELECTRIC_GOLD_PAN_3, SlimefunItems.ELECTRIC_DUST_WASHER_3,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT,
        }, true),
        URANIUM(Categories.ADVANCED_MACHINES, Items.URANIUM_EXTRACTOR, URANIUM_ENERGY, URANIUM_SPEED, URANIUM_INPUT, URANIUM_OUTPUT,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                        SlimefunItems.ELECTRIC_ORE_GRINDER_2, SlimefunItems.ELECTRIC_ORE_GRINDER_2, SlimefunItems.ELECTRIC_ORE_GRINDER_2,
                        SlimefunItems.ELECTRIC_GOLD_PAN_3, SlimefunItems.ELECTRIC_DUST_WASHER_3, SlimefunItems.AUTOMATED_CRAFTING_CHAMBER,
                        Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT,
                }, false);


        @Nonnull
        private final Category category;
        private final SlimefunItemStack item;
        private final int energy;
        private final int speed;
        private final ItemStack[] input;
        private final ItemStack[] output;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
        private final boolean random;
    }

    private static final ItemStack[] URANIUM_INPUT = {
            new ItemStack(Material.COBBLESTONE),
            new ItemStack(Material.ANDESITE),
            new ItemStack(Material.STONE),
            new ItemStack(Material.DIORITE),
            new ItemStack(Material.GRANITE)
    };

    private static final ItemStack[] URANIUM_OUTPUT = {
            SlimefunItems.SMALL_URANIUM
    };

    private static final ItemStack[] FREEZER_INPUT = {
            new ItemStack(Material.ICE),
            new ItemStack(Material.MAGMA_BLOCK)
    };

    private static final ItemStack[] FREEZER_OUTPUT = {
            SlimefunItems.REACTOR_COOLANT_CELL,
            SlimefunItems.NETHER_ICE_COOLANT_CELL
    };

    private static final ItemStack[] DUST_INPUT = {
            new ItemStack(Material.COBBLESTONE),
            new ItemStack(Material.ANDESITE),
            new ItemStack(Material.STONE),
            new ItemStack(Material.DIORITE),
            new ItemStack(Material.GRANITE)
    };

    private static final ItemStack[] DUST_OUTPUT = {
            new SlimefunItemStack(SlimefunItems.COPPER_DUST, 2),
            new SlimefunItemStack(SlimefunItems.ZINC_DUST, 2),
            new SlimefunItemStack(SlimefunItems.TIN_DUST, 2),
            new SlimefunItemStack(SlimefunItems.ALUMINUM_DUST, 2),
            new SlimefunItemStack(SlimefunItems.LEAD_DUST, 2),
            new SlimefunItemStack(SlimefunItems.SILVER_DUST, 2),
            new SlimefunItemStack(SlimefunItems.GOLD_DUST, 2),
            new SlimefunItemStack(SlimefunItems.IRON_DUST, 2),
            new SlimefunItemStack(SlimefunItems.MAGNESIUM_DUST, 2),
    };

    private static final ItemStack[] DECOM_INPUT = {
            new ItemStack(Material.EMERALD_BLOCK),
            new ItemStack(Material.DIAMOND_BLOCK),
            new ItemStack(Material.GOLD_BLOCK),
            new ItemStack(Material.IRON_BLOCK),
            new ItemStack(Material.NETHERITE_BLOCK),
            new ItemStack(Material.REDSTONE_BLOCK),
            new ItemStack(Material.QUARTZ_BLOCK),
            new ItemStack(Material.LAPIS_BLOCK),
            new ItemStack(Material.COAL_BLOCK),
            Items.COMPRESSED_COBBLESTONE_5,
            Items.COMPRESSED_COBBLESTONE_4,
            Items.COMPRESSED_COBBLESTONE_3,
            Items.COMPRESSED_COBBLESTONE_2,
            Items.COMPRESSED_COBBLESTONE_1,
    };

    private static final ItemStack[] DECOM_OUTPUT = {
            new ItemStack(Material.EMERALD, 9),
            new ItemStack(Material.DIAMOND, 9),
            new ItemStack(Material.GOLD_INGOT, 9),
            new ItemStack(Material.IRON_INGOT, 9),
            new ItemStack(Material.NETHERITE_INGOT, 9),
            new ItemStack(Material.REDSTONE, 9),
            new ItemStack(Material.QUARTZ, 4),
            new ItemStack(Material.LAPIS_LAZULI, 9),
            new ItemStack(Material.COAL, 9),
            new SlimefunItemStack(Items.COMPRESSED_COBBLESTONE_4, 9),
            new SlimefunItemStack(Items.COMPRESSED_COBBLESTONE_3, 9),
            new SlimefunItemStack(Items.COMPRESSED_COBBLESTONE_2, 9),
            new SlimefunItemStack(Items.COMPRESSED_COBBLESTONE_1, 9),
            new ItemStack(Material.COBBLESTONE, 9)
    };
}
