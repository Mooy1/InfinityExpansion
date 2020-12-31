package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinitylib.filter.FilterType;
import io.github.mooy1.infinitylib.filter.ItemFilter;
import io.github.mooy1.infinitylib.math.RandomUtils;
import io.github.mooy1.infinitylib.objects.AbstractContainer;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
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
 * Machines that convert 1 item to another with energy
 *
 * @author Mooy1
 */
public class ConversionMachine extends AbstractContainer implements RecipeDisplayItem, EnergyNetComponent {
    
    public static final int FREEZER_SPEED = 2;
    public static final int FREEZER_ENERGY = 120;
    public static final int URANIUM_SPEED = 1;
    public static final int URANIUM_ENERGY = 360;
    public static final int DUST_SPEED = 4;
    public static final int DUST_ENERGY = 300;
    public static final int DECOM_SPEED = 4;
    public static final int DECOM_ENERGY = 90;
    private static final int[] INPUT_SLOTS = {MenuPreset.slot1};
    private static final int[] OUTPUT_SLOTS = {MenuPreset.slot3};
    private static final int STATUS_SLOT = MenuPreset.slot2;
    private final Type type;

    public ConversionMachine(Type type) {
        super(type.category, type.item, type.recipeType, type.recipe);
        this.type = type;

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);
            
            if (inv != null) {
                Location l = b.getLocation();
                inv.dropItems(l, OUTPUT_SLOTS);
                inv.dropItems(l, INPUT_SLOTS);
            }

            return true;
        });
    }

    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : MenuPreset.slotChunk1) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk2) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk3) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.loadingItemRed, ChestMenuUtils.getEmptyClickHandler());
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
    public void tick(@Nonnull Block b, @Nonnull BlockMenu inv) {
        int energy = this.type.energy;

        if (getCharge(b.getLocation()) < energy) { //not enough energy
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughEnergy);
            }
            return;
        }
        
        ItemStack input = inv.getItemInSlot(INPUT_SLOTS[0]);

        if (input == null) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.inputAnItem);
            }
            return;
        }

        Pair<ItemStack, Integer> pair = getOutput(input);

        if (pair == null) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.invalidInput);
            }
            return;
        }
        
        if (inv.fits(pair.getFirstValue(), OUTPUT_SLOTS)) {
            inv.consumeItem(INPUT_SLOTS[0], pair.getSecondValue());
            inv.pushItem(pair.getFirstValue(), OUTPUT_SLOTS);
            removeCharge(b.getLocation(), energy);
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aConverting..."));
            }

        } else if (inv.hasViewer()) {
            inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughRoom);
        }
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return this.type.energy;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();

        ItemFilter[] inputs = this.type.input;
        ItemStack[] outputs = this.type.output;

        if (inputs.length == outputs.length) { //1 to 1
            for (int i = 0 ; i < inputs.length ; i++) {
                items.add(inputs[i].getItem());
                items.add(outputs[i]);
            }
        } else { //each input gets each output
            for (ItemFilter input : inputs) {
                for (ItemStack output : outputs) {
                    items.add(input.getItem());
                    items.add(output);
                }
            }
        }

        return items;
    }

    @Nullable
    private Pair<ItemStack, Integer> getOutput(@Nonnull ItemStack input) {
        for (int i = 0 ; i < this.type.input.length ; i ++) {
            ItemFilter filter = this.type.input[i];
            if (filter.fits(new ItemFilter(input, FilterType.MIN_AMOUNT), FilterType.MIN_AMOUNT)) {
                if (this.type.random) {
                    return new Pair<>(RandomUtils.randomOutput(this.type.output), filter.getAmount());
                } else {
                    return new Pair<>(this.type.output[i].clone(), filter.getAmount());
                }

            }
        }
        return null;
    }
    
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {
        FREEZER(Categories.ADVANCED_MACHINES, Items.EXTREME_FREEZER, FREEZER_ENERGY, FREEZER_INPUT, FREEZER_OUTPUT,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                        SlimefunItems.FREEZER_2, SlimefunItems.FREEZER_2, SlimefunItems.FREEZER_2,
                        new ItemStack(Material.WATER_BUCKET), SlimefunItems.FLUID_PUMP, new ItemStack(Material.WATER_BUCKET),
                        Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT,
                }, false),
        DECOM(Categories.ADVANCED_MACHINES, Items.DECOMPRESSOR, DECOM_ENERGY, DECOM_INPUT, DECOM_OUTPUT,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                        Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE,
                        new ItemStack(Material.STICKY_PISTON), SlimefunItems.ELECTRIC_PRESS_2, new ItemStack(Material.STICKY_PISTON),
                        Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT,
                }, false),
        DUST(Categories.ADVANCED_MACHINES, Items.DUST_EXTRACTOR, DUST_ENERGY, DUST_INPUT, DUST_OUTPUT,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.ELECTRIC_ORE_GRINDER_2, SlimefunItems.ELECTRIC_GOLD_PAN_3, SlimefunItems.ELECTRIC_DUST_WASHER_3,
                SlimefunItems.ELECTRIC_ORE_GRINDER_2, SlimefunItems.ELECTRIC_GOLD_PAN_3, SlimefunItems.ELECTRIC_DUST_WASHER_3,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT,
        }, true),
        URANIUM(Categories.ADVANCED_MACHINES, Items.URANIUM_EXTRACTOR, URANIUM_ENERGY, URANIUM_INPUT, URANIUM_OUTPUT,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                        SlimefunItems.ELECTRIC_ORE_GRINDER_2, SlimefunItems.ELECTRIC_ORE_GRINDER_2, SlimefunItems.ELECTRIC_ORE_GRINDER_2,
                        SlimefunItems.ELECTRIC_GOLD_PAN_3, SlimefunItems.ELECTRIC_DUST_WASHER_3, SlimefunItems.AUTOMATED_CRAFTING_CHAMBER,
                        Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT,
                }, false);

        
        private final Category category;
        private final SlimefunItemStack item;
        private final int energy;
        private final ItemFilter[] input;
        private final ItemStack[] output;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
        private final boolean random;
        
    }

    private static final ItemFilter[] URANIUM_INPUT = {
            new ItemFilter(Material.COBBLESTONE, 4, FilterType.MIN_AMOUNT),
            new ItemFilter(Material.ANDESITE, 4, FilterType.MIN_AMOUNT),
            new ItemFilter(Material.STONE, 4, FilterType.MIN_AMOUNT),
            new ItemFilter(Material.DIORITE, 4, FilterType.MIN_AMOUNT),
            new ItemFilter(Material.GRANITE, 4, FilterType.MIN_AMOUNT)
    };

    private static final ItemStack[] URANIUM_OUTPUT = {
            SlimefunItems.SMALL_URANIUM
    };

    private static final ItemFilter[] FREEZER_INPUT = {
            new ItemFilter(Material.ICE, 2, FilterType.MIN_AMOUNT),
            new ItemFilter(Material.MAGMA_BLOCK, 2, FilterType.MIN_AMOUNT)
    };

    private static final ItemStack[] FREEZER_OUTPUT = {
            SlimefunItems.REACTOR_COOLANT_CELL,
            SlimefunItems.NETHER_ICE_COOLANT_CELL
    };

    private static final ItemFilter[] DUST_INPUT = {
            new ItemFilter(Material.COBBLESTONE, 4, FilterType.MIN_AMOUNT),
            new ItemFilter(Material.ANDESITE, 4, FilterType.MIN_AMOUNT),
            new ItemFilter(Material.STONE, 4, FilterType.MIN_AMOUNT),
            new ItemFilter(Material.DIORITE, 4, FilterType.MIN_AMOUNT),
            new ItemFilter(Material.GRANITE, 4, FilterType.MIN_AMOUNT)
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
            new SlimefunItemStack(SlimefunItems.MAGNESIUM_DUST, 2)
    };

    private static final ItemFilter[] DECOM_INPUT = {
            new ItemFilter(Material.EMERALD_BLOCK, 1, FilterType.MIN_AMOUNT),
            new ItemFilter(Material.DIAMOND_BLOCK, 1, FilterType.MIN_AMOUNT),
            new ItemFilter(Material.GOLD_BLOCK, 1, FilterType.MIN_AMOUNT),
            new ItemFilter(Material.IRON_BLOCK, 1, FilterType.MIN_AMOUNT),
            new ItemFilter(Material.NETHERITE_BLOCK, 1, FilterType.MIN_AMOUNT),
            new ItemFilter(Material.REDSTONE_BLOCK, 1, FilterType.MIN_AMOUNT),
            new ItemFilter(Material.QUARTZ_BLOCK, 1, FilterType.MIN_AMOUNT),
            new ItemFilter(Material.LAPIS_BLOCK, 1, FilterType.MIN_AMOUNT),
            new ItemFilter(Material.COAL_BLOCK, 1, FilterType.MIN_AMOUNT),
            new ItemFilter(Items.COMPRESSED_COBBLESTONE_5, FilterType.MIN_AMOUNT),
            new ItemFilter(Items.COMPRESSED_COBBLESTONE_4, FilterType.MIN_AMOUNT),
            new ItemFilter(Items.COMPRESSED_COBBLESTONE_3, FilterType.MIN_AMOUNT),
            new ItemFilter(Items.COMPRESSED_COBBLESTONE_2, FilterType.MIN_AMOUNT),
            new ItemFilter(Items.COMPRESSED_COBBLESTONE_1, FilterType.MIN_AMOUNT)
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
