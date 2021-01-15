package io.github.mooy1.infinityexpansion.implementation.generators;

import io.github.mooy1.infinityexpansion.implementation.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.implementation.materials.CompressedItem;
import io.github.mooy1.infinityexpansion.implementation.materials.InfinityItem;
import io.github.mooy1.infinityexpansion.implementation.materials.MachineItem;
import io.github.mooy1.infinityexpansion.implementation.materials.SmelteryItem;
import io.github.mooy1.infinityexpansion.setup.SlimefunConstructors;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.items.LoreUtils;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.objects.AbstractContainer;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNet;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
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
 * A reactor that generates huge power but costs infinity ingots and void ingots
 *
 * @author Mooy1
 */
public final class InfinityReactor extends AbstractContainer implements EnergyNetProvider, RecipeDisplayItem {
    
    public static final SlimefunItemStack ITEM = new SlimefunItemStack(
            "INFINITY_REACTOR",
            Material.BEACON,
            "&bInfinity Reactor",
            "&7Generates power through the compression",
            "&7of &8Void &7and &bInfinity &7Ingots",
            "",
            LorePreset.energyBuffer(InfinityReactor.STORAGE),
            LorePreset.energyPerSecond(InfinityReactor.ENERGY)
    );
    
    public static final int ENERGY = 180_000;
    public static final int STORAGE = 40_000_000;
    public static final int INFINITY_INTERVAL = (int) (86400 * PluginUtils.TICK_RATIO); 
    public static final int VOID_INTERVAL = (int) (14400 * PluginUtils.TICK_RATIO);
    public static final int[] INPUT_SLOTS = {
            MenuPreset.slot1, MenuPreset.slot3
    };
    public static final int STATUS_SLOT = MenuPreset.slot2;

    public InfinityReactor() {
        super(Categories.INFINITY_CHEAT, ITEM, InfinityWorkbench.TYPE, new ItemStack[]  {
                null, SmelteryItem.INFINITY, SmelteryItem.INFINITY, SmelteryItem.INFINITY, SmelteryItem.INFINITY, null,
                SmelteryItem.INFINITY, SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY, SmelteryItem.INFINITY,
                SmelteryItem.INFINITY, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_PLATE, SmelteryItem.INFINITY,
                SmelteryItem.INFINITY, MachineItem.MACHINE_PLATE, SlimefunConstructors.ADVANCED_NETHER_STAR_REACTOR, SlimefunConstructors.ADVANCED_NETHER_STAR_REACTOR, MachineItem.MACHINE_PLATE, SmelteryItem.INFINITY,
                SmelteryItem.INFINITY, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_PLATE, SmelteryItem.INFINITY,
                SmelteryItem.INFINITY, InfinityItem.CIRCUIT, InfinityItem.CORE, InfinityItem.CORE, InfinityItem.CIRCUIT, SmelteryItem.INFINITY
        });
        
        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                Location l = b.getLocation();
                inv.dropItems(l, INPUT_SLOTS);
            }

            return true;
        });
    }

    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : MenuPreset.slotChunk2) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk3) {
            blockMenuPreset.addItem(i, new CustomItem(
                    Material.BLACK_STAINED_GLASS_PANE, "&8Void Ingot Input"), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk1) {
            blockMenuPreset.addItem(i, new CustomItem(
                    Material.WHITE_STAINED_GLASS_PANE, "&fInfinity Ingot Input"), ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.loadingItemRed, ChestMenuUtils.getEmptyClickHandler());
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
        }
        return new int[0];
    }

    @Override
    public int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, @Nonnull ItemStack item) {
        if (flow == ItemTransportFlow.INSERT) {
            String input = StackUtils.getID(item);
            if (Objects.equals(input, "VOID_INGOT")) {
                return new int[] {INPUT_SLOTS[1]};
            } else if (Objects.equals(input, "INFINITE_INGOT")) {
                return new int[] {INPUT_SLOTS[0]};
            }
        }

        return new int[0];
    }

    @Override
    public void tick(@Nonnull Block b, @Nonnull BlockMenu inv) {
        if (inv.hasViewer() && !SlimefunPlugin.getNetworkManager().getNetworkFromLocation(b.getLocation(), EnergyNet.class).isPresent()) {
            inv.replaceExistingItem(STATUS_SLOT, MenuPreset.connectToEnergyNet);
        }
    }

    @Override
    public int getGeneratedOutput(@Nonnull Location l, @Nonnull Config config) {
        @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
        if (inv == null) return 0;
        Block b = l.getBlock();

        boolean playerWatching = inv.hasViewer();

        int progress = Integer.parseInt(getProgress(b));

        if (progress == 0) { //need infinity + void

            if (!Objects.equals(StackUtils.getID(inv.getItemInSlot(INPUT_SLOTS[0])), "INFINITE_INGOT")) { //wrong input

                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.RED_STAINED_GLASS_PANE, "&cInput more &fInfinity Ingots"));
                }
                return 0;

            }
            
            if (!Objects.equals(StackUtils.getID(inv.getItemInSlot(INPUT_SLOTS[1])), "VOID_INGOT")) { //wrong input

                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.RED_STAINED_GLASS_PANE, "&cInput more &8Void Ingots"));
                }
                return 0;

            } 
            
            //correct input
            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                                "&aStarting Generation",
                                "&aTime until infinity ingot needed: " + INFINITY_INTERVAL,
                                "&aTime until void ingot needed: " + VOID_INTERVAL
                        ));
            }
            inv.consumeItem(INPUT_SLOTS[0]);
            inv.consumeItem(INPUT_SLOTS[1]);
            setProgress(b, 1);
            return ENERGY;
            
        }
        
        if (progress >= INFINITY_INTERVAL) { //done

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aFinished Generation"));
            }
            setProgress(b, 0);
            return ENERGY;

        } 
        
        if (Math.floorMod(progress, VOID_INTERVAL) == 0) { //need void

            if (!Objects.equals(StackUtils.getID(inv.getItemInSlot(INPUT_SLOTS[1])), "VOID_INGOT")) { //wrong input

                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.RED_STAINED_GLASS_PANE, "&cInput more &8Void Ingots"));
                }
                return 0;

            }
            
            //right input
            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                                "&aGenerating...",
                                "&aTime until infinity ingot needed: " + (INFINITY_INTERVAL - progress),
                                "&aTime until void ingot needed: " + (VOID_INTERVAL - Math.floorMod(progress, VOID_INTERVAL))
                        ));
            }
            setProgress(b, progress + 1);
            inv.consumeItem(INPUT_SLOTS[1]);
            return ENERGY;

        } 
        
        //generate

        if (playerWatching) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                            "&aGenerating...",
                            "&aTime until infinity ingot needed: " + (INFINITY_INTERVAL - progress),
                            "&aTime until void ingot needed: " + (VOID_INTERVAL - Math.floorMod(progress, VOID_INTERVAL))
                    )
            );
        }
        setProgress(b, progress + 1);
        return ENERGY;
    }

    @Override
    public int getCapacity() {
        return STORAGE;
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.GENERATOR;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();

        ItemStack item = SmelteryItem.INFINITY.clone();
        LoreUtils.addLore(item, "", ChatColor.GOLD + "Lasts for 1 day");
        items.add(item);
        items.add(null);

        item = CompressedItem.VOID_INGOT.clone();
        LoreUtils.addLore(item, "", ChatColor.GOLD + "Lasts for 4 hours");
        items.add(item);
        items.add(null);

        return items;
    }

    private void setProgress(Block b, int progress) {
        setBlockData(b, String.valueOf(progress));
    }

    private String getProgress(Block b) {
        return BlockStorage.getLocationInfo(b.getLocation(), "progress");
    }

    private void setBlockData(Block b, String data) {
        BlockStorage.addBlockInfo(b, "progress", data);
    }
}
