package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.materials.CompressedItem;
import io.github.mooy1.infinityexpansion.implementation.materials.MachineItem;
import io.github.mooy1.infinityexpansion.implementation.materials.SmelteryItem;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.mooy1.infinitylib.objects.AbstractMachine;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Machines that generate materials at the cost of energy
 *
 * @author Mooy1
 */
public final class MaterialGenerator extends AbstractMachine implements RecipeDisplayItem {

    public static void setup(InfinityExpansion plugin) {
        new MaterialGenerator(Categories.BASIC_MACHINES, BASIC_COBBLE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SmelteryItem.MAGSTEEL, new ItemStack(Material.DIAMOND_PICKAXE), SmelteryItem.MAGSTEEL,
                new ItemStack(Material.WATER_BUCKET), CompressedItem.COBBLE_2, new ItemStack(Material.LAVA_BUCKET),
                SmelteryItem.MAGSTEEL, MachineItem.MACHINE_CIRCUIT, SmelteryItem.MAGSTEEL
        }, 24, 1, Material.COBBLESTONE).register(plugin);
        new MaterialGenerator(Categories.ADVANCED_MACHINES, ADVANCED_COBBLE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                MachineItem.MAGSTEEL_PLATE, SlimefunItems.PROGRAMMABLE_ANDROID_MINER, MachineItem.MAGSTEEL_PLATE,
                new ItemStack(Material.WATER_BUCKET), CompressedItem.COBBLE_4, new ItemStack(Material.LAVA_BUCKET),
                MachineItem.MACHINE_CIRCUIT, BASIC_COBBLE, MachineItem.MACHINE_CIRCUIT
        }, 120, 4, Material.COBBLESTONE).register(plugin);
        new MaterialGenerator(Categories.ADVANCED_MACHINES, BASIC_OBSIDIAN, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.FLUID_PUMP, SlimefunItems.PROGRAMMABLE_ANDROID_MINER, SlimefunItems.FLUID_PUMP,
                new ItemStack(Material.DISPENSER), CompressedItem.VOID_INGOT, new ItemStack(Material.DISPENSER),
                MachineItem.MACHINE_CIRCUIT, ADVANCED_COBBLE, MachineItem.MACHINE_CIRCUIT
        }, 240, 1, Material.OBSIDIAN).register(plugin);
    }
    
    public static final SlimefunItemStack BASIC_COBBLE = new SlimefunItemStack(
            "BASIC_COBBLE_GEN",
            Material.SMOOTH_STONE,
            "&9Basic &8Cobble Generator",
            "",
            LorePreset.speed(1),
            LorePreset.energyPerSecond(24)
    );
    public static final SlimefunItemStack ADVANCED_COBBLE = new SlimefunItemStack(
            "ADVANCED_COBBLE_GEN",
            Material.SMOOTH_STONE,
            "&cAdvanced &8Cobble Generator",
            "",
            LorePreset.speed(4),
            LorePreset.energyPerSecond(120)
    );
    public static final SlimefunItemStack BASIC_OBSIDIAN = new SlimefunItemStack(
            "BASIC_OBSIDIAN_GEN",
            Material.SMOOTH_STONE,
            "&8Obsidian Generator",
            "",
            LorePreset.speed(1),
            LorePreset.energyPerSecond(240)
    );

    private static final int[] OUTPUT_SLOTS = {
            13
    };
    private static final int STATUS_SLOT = 4;

    private final int speed;
    private final Material material;

    private MaterialGenerator(Category category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe, int energy, int speed, Material material) {
        super(category, item, type, recipe, STATUS_SLOT, energy);
        this.speed = speed;
        this.material = material;

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), OUTPUT_SLOTS);
            }

            return true;
        });
    }

    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i = 0 ; i < 13 ; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i = 14 ; i < 18 ; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.loadingItemRed,
                ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        }

        return new int[0];
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {

    }

    @Override
    public boolean process(@Nonnull Block b, @Nonnull BlockMenu inv) {
        ItemStack output = new ItemStack(this.material, this.speed);

        if (!inv.fits(output, OUTPUT_SLOTS)) {

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughRoom);
            }
            return false;

        }

        inv.pushItem(output, OUTPUT_SLOTS);

        if (inv.hasViewer()) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aGenerating..."));
        }

        return true;
    }

    @Override
    public int getCapacity() {
        return this.energy * 2;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();
        items.add(null);
        items.add(new ItemStack(this.material, this.speed));
        return items;
    }

    @Nonnull
    @Override
    public String getRecipeSectionLabel(@Nonnull Player p) {
        return "&7Generates";
    }

}
