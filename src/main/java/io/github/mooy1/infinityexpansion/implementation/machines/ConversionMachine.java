package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.materials.CompressedItem;
import io.github.mooy1.infinityexpansion.implementation.materials.MachineItem;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.mooy1.infinitylib.filter.FilterType;
import io.github.mooy1.infinitylib.filter.ItemFilter;
import io.github.mooy1.infinitylib.math.RandomUtils;
import io.github.mooy1.infinitylib.objects.AbstractMachine;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
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
public final class ConversionMachine extends AbstractMachine implements RecipeDisplayItem {
    
    public static void setup(InfinityExpansion plugin) {
        new ConversionMachine(EXTREME_FREEZER, new ItemStack[] {
                SlimefunItems.FREEZER_2, SlimefunItems.FREEZER_2, SlimefunItems.FREEZER_2,
                new ItemStack(Material.WATER_BUCKET), SlimefunItems.FLUID_PUMP, new ItemStack(Material.WATER_BUCKET),
                MachineItem.MACHINE_CIRCUIT, MachineItem.MACHINE_CORE, MachineItem.MACHINE_CIRCUIT,
        }, 90,  false, new ItemStack[] {
                SlimefunItems.REACTOR_COOLANT_CELL,
                SlimefunItems.NETHER_ICE_COOLANT_CELL
        }, new ItemFilter[]{
                new ItemFilter(Material.ICE, 2, FilterType.MIN_AMOUNT),
                new ItemFilter(Material.MAGMA_BLOCK, 2, FilterType.MIN_AMOUNT)
        }).register(plugin);
        
        new ConversionMachine(DUST_EXTRACTOR, new ItemStack[] {
                SlimefunItems.ELECTRIC_ORE_GRINDER_2, SlimefunItems.ELECTRIC_GOLD_PAN_3, SlimefunItems.ELECTRIC_DUST_WASHER_3,
                SlimefunItems.ELECTRIC_ORE_GRINDER_2, SlimefunItems.ELECTRIC_GOLD_PAN_3, SlimefunItems.ELECTRIC_DUST_WASHER_3,
                MachineItem.MACHINE_CIRCUIT, MachineItem.MACHINE_CORE, MachineItem.MACHINE_CIRCUIT,
        }, 180,  true, new ItemStack[] {
                new SlimefunItemStack(SlimefunItems.COPPER_DUST, 2),
                new SlimefunItemStack(SlimefunItems.ZINC_DUST, 2),
                new SlimefunItemStack(SlimefunItems.TIN_DUST, 2),
                new SlimefunItemStack(SlimefunItems.ALUMINUM_DUST, 2),
                new SlimefunItemStack(SlimefunItems.LEAD_DUST, 2),
                new SlimefunItemStack(SlimefunItems.SILVER_DUST, 2),
                new SlimefunItemStack(SlimefunItems.GOLD_DUST, 2),
                new SlimefunItemStack(SlimefunItems.IRON_DUST, 2),
                new SlimefunItemStack(SlimefunItems.MAGNESIUM_DUST, 2)
        }, new ItemFilter[]{
                new ItemFilter(Material.COBBLESTONE, 4, FilterType.MIN_AMOUNT),
                new ItemFilter(Material.ANDESITE, 4, FilterType.MIN_AMOUNT),
                new ItemFilter(Material.STONE, 4, FilterType.MIN_AMOUNT),
                new ItemFilter(Material.DIORITE, 4, FilterType.MIN_AMOUNT),
                new ItemFilter(Material.GRANITE, 4, FilterType.MIN_AMOUNT)
        }).register(plugin);
        
        new ConversionMachine(URANIUM_EXTRACTOR, new ItemStack[] {
                SlimefunItems.ELECTRIC_ORE_GRINDER_2, SlimefunItems.ELECTRIC_ORE_GRINDER_2, SlimefunItems.ELECTRIC_ORE_GRINDER_2,
                SlimefunItems.ELECTRIC_GOLD_PAN_3, SlimefunItems.ELECTRIC_DUST_WASHER_3, SlimefunItems.AUTOMATED_CRAFTING_CHAMBER,
                MachineItem.MACHINE_CIRCUIT, MachineItem.MACHINE_CORE, MachineItem.MACHINE_CIRCUIT,
        }, 240,  false, new ItemStack[] {
                SlimefunItems.SMALL_URANIUM
        }, new ItemFilter[]{
                new ItemFilter(Material.COBBLESTONE, 4, FilterType.MIN_AMOUNT),
                new ItemFilter(Material.ANDESITE, 4, FilterType.MIN_AMOUNT),
                new ItemFilter(Material.STONE, 4, FilterType.MIN_AMOUNT),
                new ItemFilter(Material.DIORITE, 4, FilterType.MIN_AMOUNT),
                new ItemFilter(Material.GRANITE, 4, FilterType.MIN_AMOUNT)
        }).register(plugin);
        
        new ConversionMachine(DECOMPRESSOR, new ItemStack[] {
                MachineItem.MAGSTEEL_PLATE, MachineItem.MAGSTEEL_PLATE, MachineItem.MAGSTEEL_PLATE,
                new ItemStack(Material.STICKY_PISTON), SlimefunItems.ELECTRIC_PRESS_2, new ItemStack(Material.STICKY_PISTON),
                MachineItem.MACHINE_CIRCUIT, MachineItem.MACHINE_CORE, MachineItem.MACHINE_CIRCUIT,
        }, 60,  false, new ItemStack[] {
                new ItemStack(Material.EMERALD, 9),
                new ItemStack(Material.DIAMOND, 9),
                new ItemStack(Material.GOLD_INGOT, 9),
                new ItemStack(Material.IRON_INGOT, 9),
                new ItemStack(Material.NETHERITE_INGOT, 9),
                new ItemStack(Material.REDSTONE, 9),
                new ItemStack(Material.QUARTZ, 4),
                new ItemStack(Material.LAPIS_LAZULI, 9),
                new ItemStack(Material.COAL, 9),
                new SlimefunItemStack(CompressedItem.COBBLE_4, 9),
                new SlimefunItemStack(CompressedItem.COBBLE_3, 9),
                new SlimefunItemStack(CompressedItem.COBBLE_2, 9),
                new SlimefunItemStack(CompressedItem.COBBLE_1, 9),
                new ItemStack(Material.COBBLESTONE, 9)
        }, new ItemFilter[]{
                new ItemFilter(Material.EMERALD_BLOCK, 1, FilterType.MIN_AMOUNT),
                new ItemFilter(Material.DIAMOND_BLOCK, 1, FilterType.MIN_AMOUNT),
                new ItemFilter(Material.GOLD_BLOCK, 1, FilterType.MIN_AMOUNT),
                new ItemFilter(Material.IRON_BLOCK, 1, FilterType.MIN_AMOUNT),
                new ItemFilter(Material.NETHERITE_BLOCK, 1, FilterType.MIN_AMOUNT),
                new ItemFilter(Material.REDSTONE_BLOCK, 1, FilterType.MIN_AMOUNT),
                new ItemFilter(Material.QUARTZ_BLOCK, 1, FilterType.MIN_AMOUNT),
                new ItemFilter(Material.LAPIS_BLOCK, 1, FilterType.MIN_AMOUNT),
                new ItemFilter(Material.COAL_BLOCK, 1, FilterType.MIN_AMOUNT),
                new ItemFilter(CompressedItem.COBBLE_5, FilterType.MIN_AMOUNT),
                new ItemFilter(CompressedItem.COBBLE_4, FilterType.MIN_AMOUNT),
                new ItemFilter(CompressedItem.COBBLE_3, FilterType.MIN_AMOUNT),
                new ItemFilter(CompressedItem.COBBLE_2, FilterType.MIN_AMOUNT),
                new ItemFilter(CompressedItem.COBBLE_1, FilterType.MIN_AMOUNT)
        }).register(plugin);
    }
    
    public static final SlimefunItemStack EXTREME_FREEZER = new SlimefunItemStack(
            "EXTREME_FREEZER",
            Material.LIGHT_BLUE_CONCRETE,
            "&bExtreme Freezer",
            "&7Converts ice into coolant",
            "",
            LorePreset.energyPerSecond(90)
    );
    public static final SlimefunItemStack DUST_EXTRACTOR = new SlimefunItemStack(
            "DUST_EXTRACTOR",
            Material.FURNACE,
            "&8Dust Extractor",
            "&7Converts cobble into dusts",
            "",
            LorePreset.energyPerSecond(180)
    );

    public static final SlimefunItemStack URANIUM_EXTRACTOR = new SlimefunItemStack(
            "URANIUM_EXTRACTOR",
            Material.LIME_CONCRETE,
            "&aUranium Extractor",
            "&7Converts cobble into uranium",
            "",
            LorePreset.energyPerSecond(240)
    );
    public static final SlimefunItemStack DECOMPRESSOR = new SlimefunItemStack(
            "DECOMPRESSOR",
            Material.TARGET,
            "&7Decompressor",
            "&7Reduces blocks to their base material",
            "",
            LorePreset.energyPerSecond(60)
    );
    
    private static final int[] INPUT_SLOTS = {MenuPreset.slot1};
    private static final int[] OUTPUT_SLOTS = {MenuPreset.slot3};
    private static final int STATUS_SLOT = MenuPreset.slot2;
    
    private final boolean random;
    private final ItemStack[] outputs;
    private final ItemFilter[] inputs;

    public ConversionMachine(SlimefunItemStack item, ItemStack[] recipe,
                             int energy, boolean random, ItemStack[] outputs, ItemFilter[] inputs) {
        super(Categories.ADVANCED_MACHINES, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe, STATUS_SLOT, energy);
        this.random = random;
        this.outputs = outputs;
        this.inputs = inputs;
        
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
    public boolean process(@Nonnull Block b, @Nonnull BlockMenu inv) {
        ItemStack input = inv.getItemInSlot(INPUT_SLOTS[0]);

        if (input == null) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.inputAnItem);
            }
            return false;
        }

        Pair<ItemStack, Integer> pair = getOutput(input);

        if (pair == null) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.invalidInput);
            }
            return false;
        }

        if (inv.fits(pair.getFirstValue(), OUTPUT_SLOTS)) {
            inv.consumeItem(INPUT_SLOTS[0], pair.getSecondValue());
            inv.pushItem(pair.getFirstValue(), OUTPUT_SLOTS);
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
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        
    }
    
    @Override
    public int getCapacity() {
        return this.energy;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();
        
        if (this.inputs.length == this.outputs.length) { //1 to 1
            for (int i = 0 ; i < this.inputs.length ; i++) {
                items.add(this.inputs[i].getItem());
                items.add(this.outputs[i]);
            }
        } else { //each input gets each output
            for (ItemFilter input : this.inputs) {
                for (ItemStack output : this.outputs) {
                    items.add(input.getItem());
                    items.add(output);
                }
            }
        }

        return items;
    }

    @Nullable
    private Pair<ItemStack, Integer> getOutput(@Nonnull ItemStack input) {
        for (int i = 0 ; i < this.inputs.length ; i ++) {
            ItemFilter filter = this.inputs[i];
            if (filter.fits(new ItemFilter(input, FilterType.MIN_AMOUNT), FilterType.MIN_AMOUNT)) {
                if (this.random) {
                    return new Pair<>(RandomUtils.randomOutput(this.outputs), filter.getAmount());
                } else {
                    return new Pair<>(this.outputs[i].clone(), filter.getAmount());
                }

            }
        }
        return null;
    }

}
