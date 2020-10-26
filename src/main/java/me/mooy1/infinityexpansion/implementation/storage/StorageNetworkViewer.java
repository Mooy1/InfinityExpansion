package me.mooy1.infinityexpansion.implementation.storage;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.utils.LocationUtils;
import me.mooy1.infinityexpansion.utils.MessageUtils;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StorageNetworkViewer extends SlimefunItem {

    public static final int LENGTH = 64;
    private static final int STATUS_SLOT = 4;
    private static final String[] CONNECTABLE = {
            "STORAGE_NETWORK_VIEWER",
            "STORAGE_DUCT",
            "BASIC_STORAGE",
            "ADVANCED_STORAGE",
            "REINFORCED_STORAGE",
            "VOID_STORAGE",
            "INFINITE_STORAGE",
    };
    private static final String[] UNITS = {
            "BASIC_STORAGE",
            "ADVANCED_STORAGE",
            "REINFORCED_STORAGE",
            "VOID_STORAGE",
            "INFINITY_STORAGE",
    };

    public StorageNetworkViewer() {
        super(Categories.STORAGE_TRANSPORT, Items.STORAGE_NETWORK_VIEWER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.STORAGE_DUCT, Items.MACHINE_CIRCUIT, Items.STORAGE_DUCT,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT,
                Items.STORAGE_DUCT, Items.MACHINE_CIRCUIT, Items.STORAGE_DUCT,
        });

        new BlockMenuPreset(getId(), Objects.requireNonNull(Items.STORAGE_NETWORK_VIEWER.getDisplayName())) {
            @Override
            public void init() {
                setupInv(this);
            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return (player.hasPermission("slimefun.inventory.bypass")
                        || SlimefunPlugin.getProtectionManager().hasPermission(
                        player, block.getLocation(), ProtectableAction.ACCESS_INVENTORIES));
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
                return new int[0];
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
                return new int[0];
            }
        };
    }

    private void setupInv(BlockMenuPreset blockMenuPreset) {
        blockMenuPreset.setEmptySlotsClickable(false);
        for (int i = 0 ; i < 9 ; i ++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemRed, ChestMenuUtils.getEmptyClickHandler());
        for (int i = 45 ; i < 54 ; i ++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) {
                StorageNetworkViewer.this.tick(b);
            }

            public boolean isSynchronized() {
                return true;
            }
        });
    }

    public void tick(Block b) {
        Location l = b.getLocation();
        @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
        if (inv == null) return;

        if (!inv.toInventory().getViewers().isEmpty()) {
            return;
        }

        Pair<List<Location>, Pair<List<Location>, Integer>> flow = inputFlow(0, l, new ArrayList<>(), new ArrayList<>(), l);
        Pair<List<Location>, Integer> flowB = flow.getSecondValue();
        List<Location> foundLocations = flow.getFirstValue();

        for (Location location : foundLocations) {
            MessageUtils.broadcast("LOCATION" + location);
        }

    }

    /**
     * This method will search for connected storage units
     *
     * @param count length of network so far
     * @param thisLocation location being checked
     * @param foundLocations list of units
     * @param checkedLocations checked locations
     * @param prev previous location
     * @return list of locations
     */
    @Nonnull
    private Pair<List<Location>, Pair<List<Location>, Integer>> inputFlow(int count, @Nonnull Location thisLocation, @Nonnull List<Location> foundLocations, @Nonnull List<Location> checkedLocations, @Nonnull Location prev) {
        checkedLocations.add(thisLocation);

        String thisID = BlockStorage.checkID(thisLocation);

        if (thisID != null) { //try sf

            for (String check : CONNECTABLE) {
                if (thisID.equals(check)) { //connector

                    for (String unit : UNITS) { //add if unit
                        if (thisID.equals(unit)) {
                            foundLocations.add(thisLocation);
                            break;
                        }
                    }

                    count++;

                    for (Location location : LocationUtils.getAdjacentLocations(thisLocation)) {

                        if (location != prev && !checkedLocations.contains(location) && count < LENGTH) {

                            Pair<List<Location>, Pair<List<Location>, Integer>> flow = inputFlow(count, location, foundLocations, checkedLocations, location);
                            Pair<List<Location>, Integer> flowB = flow.getSecondValue();
                            foundLocations = flow.getFirstValue();
                            checkedLocations = flowB.getFirstValue();
                            count = flowB.getSecondValue();

                        }
                    }

                    break;
                }
            }
        }

        return new Pair<>(foundLocations, new Pair<>(checkedLocations, count));
    }
}