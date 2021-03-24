package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.materials.Items;
import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinitylib.abstracts.AbstractMachine;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.mooy1.infinitylib.recipes.normalstrict.StrictOutput;
import io.github.mooy1.infinitylib.recipes.normalstrict.StrictRandomRecipeMap;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
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
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT,
        }, 90, false, new ItemStack[] {
                SlimefunItems.REACTOR_COOLANT_CELL,
                SlimefunItems.NETHER_ICE_COOLANT_CELL
        }, new ItemStack[] {
                new ItemStack(Material.ICE, 2),
                new ItemStack(Material.MAGMA_BLOCK, 2)
        }).register(plugin);

        new ConversionMachine(DUST_EXTRACTOR, new ItemStack[] {
                SlimefunItems.ELECTRIC_ORE_GRINDER_2, SlimefunItems.ELECTRIC_GOLD_PAN_3, SlimefunItems.ELECTRIC_DUST_WASHER_3,
                SlimefunItems.ELECTRIC_ORE_GRINDER_2, SlimefunItems.ELECTRIC_GOLD_PAN_3, SlimefunItems.ELECTRIC_DUST_WASHER_3,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT,
        }, 240, true, new ItemStack[] {
                new SlimefunItemStack(SlimefunItems.COPPER_DUST, 2),
                new SlimefunItemStack(SlimefunItems.ZINC_DUST, 2),
                new SlimefunItemStack(SlimefunItems.TIN_DUST, 2),
                new SlimefunItemStack(SlimefunItems.ALUMINUM_DUST, 2),
                new SlimefunItemStack(SlimefunItems.LEAD_DUST, 2),
                new SlimefunItemStack(SlimefunItems.SILVER_DUST, 2),
                new SlimefunItemStack(SlimefunItems.GOLD_DUST, 2),
                new SlimefunItemStack(SlimefunItems.IRON_DUST, 2),
                new SlimefunItemStack(SlimefunItems.MAGNESIUM_DUST, 2)
        }, new ItemStack[] {
                new ItemStack(Material.COBBLESTONE, 4),
                new ItemStack(Material.ANDESITE, 4),
                new ItemStack(Material.STONE, 4),
                new ItemStack(Material.DIORITE, 4),
                new ItemStack(Material.GRANITE, 4)
        }).register(plugin);

        new ConversionMachine(URANIUM_EXTRACTOR, new ItemStack[] {
                SlimefunItems.ELECTRIC_ORE_GRINDER_2, SlimefunItems.ELECTRIC_ORE_GRINDER_2, SlimefunItems.ELECTRIC_ORE_GRINDER_2,
                SlimefunItems.ELECTRIC_GOLD_PAN_3, SlimefunItems.ELECTRIC_DUST_WASHER_3, SlimefunItems.ENHANCED_AUTO_CRAFTER,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT,
        }, 240, false, new ItemStack[] {
                SlimefunItems.SMALL_URANIUM
        }, new ItemStack[] {
                new ItemStack(Material.COBBLESTONE, 4),
                new ItemStack(Material.ANDESITE, 4),
                new ItemStack(Material.STONE, 4),
                new ItemStack(Material.DIORITE, 4),
                new ItemStack(Material.GRANITE, 4)
        }).register(plugin);

        new ConversionMachine(DECOMPRESSOR, new ItemStack[] {
                Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE,
                new ItemStack(Material.STICKY_PISTON), SlimefunItems.ELECTRIC_PRESS_2, new ItemStack(Material.STICKY_PISTON),
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT,
        }, 60, false, new ItemStack[] {
                new ItemStack(Material.EMERALD, 9),
                new ItemStack(Material.DIAMOND, 9),
                new ItemStack(Material.GOLD_INGOT, 9),
                new ItemStack(Material.IRON_INGOT, 9),
                new ItemStack(Material.NETHERITE_INGOT, 9),
                new ItemStack(Material.REDSTONE, 9),
                new ItemStack(Material.QUARTZ, 4),
                new ItemStack(Material.LAPIS_LAZULI, 9),
                new ItemStack(Material.COAL, 9),
                new SlimefunItemStack(Items.COBBLE_4, 9),
                new SlimefunItemStack(Items.COBBLE_3, 9),
                new SlimefunItemStack(Items.COBBLE_2, 9),
                new SlimefunItemStack(Items.COBBLE_1, 9),
                new ItemStack(Material.COBBLESTONE, 9)
        }, new ItemStack[] {
                new ItemStack(Material.EMERALD_BLOCK, 1),
                new ItemStack(Material.DIAMOND_BLOCK, 1),
                new ItemStack(Material.GOLD_BLOCK, 1),
                new ItemStack(Material.IRON_BLOCK, 1),
                new ItemStack(Material.NETHERITE_BLOCK, 1),
                new ItemStack(Material.REDSTONE_BLOCK, 1),
                new ItemStack(Material.QUARTZ_BLOCK, 1),
                new ItemStack(Material.LAPIS_BLOCK, 1),
                new ItemStack(Material.COAL_BLOCK, 1),
                Items.COBBLE_5,
                Items.COBBLE_4,
                Items.COBBLE_3,
                Items.COBBLE_2,
                Items.COBBLE_1
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
            LorePreset.energyPerSecond(240)
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

    private final int energy;
    private final List<ItemStack> displayRecipes = new ArrayList<>();
    private final StrictRandomRecipeMap recipes = new StrictRandomRecipeMap();

    public ConversionMachine(SlimefunItemStack item, ItemStack[] recipe, int energy, boolean random, ItemStack[] outputs, ItemStack[] inputs) {
        super(Categories.ADVANCED_MACHINES, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        this.energy = energy;
        
        if (inputs.length == outputs.length) { //1 to 1
            for (int i = 0 ; i < inputs.length ; i++) {
                this.displayRecipes.add(inputs[i]);
                this.displayRecipes.add(outputs[i]);
                this.recipes.put(inputs[i], outputs[i], 1);
            }
        } else { //each input gets each output
            for (ItemStack input : inputs) {
                for (ItemStack output : outputs) {
                    this.displayRecipes.add(input);
                    this.displayRecipes.add(output);
                    this.recipes.put(input, output, 1);
                }
            }
        }

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

    @Nonnull
    @Override
    public int[] getTransportSlots(@Nonnull DirtyChestMenu dirtyChestMenu, @Nonnull ItemTransportFlow flow, ItemStack itemStack) {
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

    }

    @Override
    public int getCapacity() {
        return this.energy;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        return this.displayRecipes;
    }

    @Override
    public boolean process(@Nonnull BlockMenu inv, @Nonnull Block block, @Nonnull Config config) {
        ItemStack input = inv.getItemInSlot(INPUT_SLOTS[0]);

        if (input == null) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.inputAnItem);
            }
            return false;
        }

        StrictOutput output = this.recipes.get(input);

        if (output == null) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.invalidInput);
            }
            return false;
        }

        if (inv.fits(output.getOutput(), OUTPUT_SLOTS)) {
            inv.consumeItem(INPUT_SLOTS[0], output.getInputConsumption());
            inv.pushItem(output.getOutput().clone(), OUTPUT_SLOTS);
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
    public void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        super.setupMenu(blockMenuPreset);
        for (int i : MenuPreset.slotChunk1) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk2) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk3) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
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

}
