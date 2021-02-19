package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.implementation.materials.CompressedItem;
import io.github.mooy1.infinityexpansion.implementation.materials.InfinityItem;
import io.github.mooy1.infinityexpansion.implementation.materials.MachineItem;
import io.github.mooy1.infinityexpansion.implementation.materials.MiscItem;
import io.github.mooy1.infinityexpansion.implementation.materials.SmelteryItem;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.mooy1.infinitylib.PluginUtils;
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
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * harvests void bits from... the void
 *
 * @author Mooy1
 */
public final class VoidHarvester extends AbstractMachine implements RecipeDisplayItem {

    public static void setup(InfinityExpansion plugin) {
        new VoidHarvester(Categories.ADVANCED_MACHINES, BASIC, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SmelteryItem.TITANIUM, SmelteryItem.TITANIUM, SmelteryItem.TITANIUM,
                MachineItem.MACHINE_PLATE, SlimefunItems.GEO_MINER, MachineItem.MACHINE_PLATE,
                MachineItem.MACHINE_CIRCUIT, MachineItem.MACHINE_CORE, MachineItem.MACHINE_CIRCUIT
        }, 120, 1).register(plugin);
        new VoidHarvester(Categories.INFINITY_CHEAT, INFINITE, InfinityWorkbench.TYPE, new ItemStack[] {
                MachineItem.MACHINE_PLATE, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_PLATE,
                SmelteryItem.MAGNONIUM, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, SmelteryItem.MAGNONIUM,
                SmelteryItem.MAGNONIUM, CompressedItem.VOID_INGOT, InfinityItem.CIRCUIT, InfinityItem.CIRCUIT, CompressedItem.VOID_INGOT, SmelteryItem.MAGNONIUM,
                SmelteryItem.MAGNONIUM, CompressedItem.VOID_INGOT, InfinityItem.CORE, InfinityItem.CORE, CompressedItem.VOID_INGOT, SmelteryItem.MAGNONIUM,
                SmelteryItem.MAGNONIUM, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, SmelteryItem.MAGNONIUM,
                MachineItem.MACHINE_PLATE, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_PLATE
        }, 1200, 32).register(plugin);
    }
    
    public static final SlimefunItemStack BASIC = new SlimefunItemStack(
            "VOID_HARVESTER",
            Material.OBSIDIAN,
            "&8Void Harvester",
            "&7Slowly harvests &8Void &7Bits from nothing...",
            "",
            LorePreset.speed(1),
            LorePreset.energyPerSecond(120)
    );
    public static final SlimefunItemStack INFINITE = new SlimefunItemStack(
            "INFINITE_VOID_HARVESTER",
            Material.CRYING_OBSIDIAN,
            "&bInfinite &8Void Harvester",
            "&7Harvests &8Void &7Bits from nothing...",
            "",
            LorePreset.speed(32),
            LorePreset.energyPerSecond(1200)
    );
    
    public static final RecipeType TYPE = new RecipeType(PluginUtils.getKey("void_harvester"), BASIC);
    
    private static final int[] OUTPUT_SLOTS = {
        13
    };
    private static final int STATUS_SLOT = 4;
    private static final int TIME = 1600;

    private final int speed;
    private final int energy;

    private VoidHarvester(Category category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe, int energy, int speed) {
        super(category, item, type, recipe);
        this.speed = speed;
        this.energy = energy;
        
        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), OUTPUT_SLOTS);
            }

            return true;
        });
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

        if (progress >= TIME) { //reached full progress

            ItemStack output = MiscItem.VOID_BIT;

            if (inv.fits(output, OUTPUT_SLOTS)) {

                inv.pushItem(output.clone(), OUTPUT_SLOTS);

                progress = 0;

            } else if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughRoom);
                return false;
            }
        } else {
            progress+= this.speed;
        }

        setProgress(b, progress);
        if (inv.hasViewer()) { //update status
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                    "&aHarvesting - " + progress * 100 / TIME + "%",
                    "&7(" + progress + "/" + TIME + ")"
            ));
        }
        return true;
    }
    
    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        super.setupMenu(blockMenuPreset);
        for (int i = 0; i < 13; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i = 14; i < 18; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        }

        return new int[0];
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        if (getProgress(b) == null) {
            setProgress(b, 0);
        }
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
        final List<ItemStack> items = new ArrayList<>();

        items.add(null);
        items.add(MiscItem.VOID_BIT);

        return items;
    }

    @Nonnull
    @Override
    public String getRecipeSectionLabel(@Nonnull Player p) {
        return "&7Harvests:";
    }

}

