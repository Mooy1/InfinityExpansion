package me.mooy1.infinityexpansion.implementation.storage;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.utils.LocationUtils;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mooy1.infinityexpansion.utils.StackUtils;
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
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Allows you to view the status of up to 18 storage units at once
 *
 * @author Mooy1
 */
public class StorageNetworkViewer extends SlimefunItem {

    public static final int RANGE = 48;
    public static final int MAX = 18;
    private static final int STATUS_SLOT = 4;
    private static final String[] CONNECTABLE = {
            "STORAGE_NETWORK_VIEWER",
            "STORAGE_DUCT",
            "BASIC_STORAGE",
            "ADVANCED_STORAGE",
            "REINFORCED_STORAGE",
            "VOID_STORAGE",
            "INFINITY_STORAGE",
    };
    private static final String[] UNITS = {
            "INFINITY_STORAGE",
            "VOID_STORAGE",
            "REINFORCED_STORAGE",
            "ADVANCED_STORAGE",
            "BASIC_STORAGE",
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
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {

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
        for (int i = 0 ; i < 9 ; i ++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemRed, ChestMenuUtils.getEmptyClickHandler());
        for (int i = 45 ; i < 54 ; i ++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i = 9 ; i < 45 ; i++) {
            blockMenuPreset.addItem(i, PresetUtils.invisibleBackground, ChestMenuUtils.getEmptyClickHandler());
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

        if (!inv.hasViewer()) {
            return;
        }

        Pair<Pair<List<Location>, List<String>>, Pair<List<Location>, Integer>> flow = inputFlow(0, l, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), l);
        Pair<List<Location>, List<String>> flowA = flow.getFirstValue();
        List<Location> foundLocations = flowA.getFirstValue();
        List<String> foundIDs = flowA.getSecondValue();
        int length = flow.getSecondValue().getSecondValue();
        int size = foundIDs.size();

        Material material = Material.ORANGE_STAINED_GLASS_PANE;
        String name = "&6No storage units connected";

        if (size > 0)  {
            material = Material.LIME_STAINED_GLASS_PANE;
            name = "&aConnected to Storage Network";
        }

        inv.replaceExistingItem(STATUS_SLOT, new CustomItem(
                material, name, "&6Units: &e" + size + " / " + MAX, "&6Length: &e" + length + " / " + RANGE
        ));

        for (int i = 9 ; i < 45 ; i++) {
            inv.replaceExistingItem(i, PresetUtils.invisibleBackground);
        }

        int spot = 0;
        for (String id : UNITS) {
            int index = 0;
            for (String found : foundIDs) {
                if (id.equals(found)) {
                    displayStatus(inv, spot, foundLocations.get(index), found);
                    spot++;
                }
                index++;
            }
        }
    }

    /**
     * This method will search for connected storage units
     *
     * @param count length of network so far
     * @param thisLocation location being checked
     * @param foundLocations list of units
     * @param foundIDs list of found unit ids
     * @param checkedLocations checked locations
     * @param prev previous location
     * @return list of locations, ids
     */
    @Nonnull
    private Pair<Pair<List<Location>, List<String>>, Pair<List<Location>, Integer>> inputFlow(int count, @Nonnull Location thisLocation, @Nonnull List<Location> foundLocations, @Nonnull List<String> foundIDs, @Nonnull List<Location> checkedLocations, @Nonnull Location prev) {
        checkedLocations.add(thisLocation);

        if (foundIDs.size() < MAX) {

            String thisID = BlockStorage.checkID(thisLocation);

            if (thisID != null) { //try sf

                for (String check : CONNECTABLE) {
                    if (thisID.equals(check)) { //connector

                        for (String unit : UNITS) { //add if unit
                            if (thisID.equals(unit)) {
                                foundIDs.add(thisID);
                                foundLocations.add(thisLocation);
                                break;
                            }
                        }

                        count++;

                        for (Location location : LocationUtils.getAdjacentLocations(thisLocation)) {

                            if (location != prev && !checkedLocations.contains(location) && count < RANGE) {

                                Pair<Pair<List<Location>, List<String>>, Pair<List<Location>, Integer>> flow = inputFlow(count, location, foundLocations, foundIDs, checkedLocations, location);
                                Pair<List<Location>, List<String>> flowA = flow.getFirstValue();
                                Pair<List<Location>, Integer> flowB = flow.getSecondValue();
                                foundLocations = flowA.getFirstValue();
                                foundIDs = flowA.getSecondValue();
                                checkedLocations = flowB.getFirstValue();
                                count = flowB.getSecondValue();

                            }
                        }

                        break;
                    }
                }
            }
        }

        return new Pair<>(new Pair<>(foundLocations, foundIDs), new Pair<>(checkedLocations, count));
    }

    /**
     * This method has does a series of checks before displaying the storage and it's item in a BlockMenu
     *
     * @param inv BlockMenu to display in
     * @param spot spot to display in
     * @param l location of storage unit
     * @param id id of storage unit
     */
    private static void displayStatus(@Nonnull BlockMenu inv, int spot, @Nonnull Location l, @Nonnull String id) {

        ItemStack unit = StackUtils.getItemFromID(id, 1);
        ItemStack item = new CustomItem(Material.GRAY_STAINED_GLASS_PANE, "&8Nothing stored");

        if (unit == null) {
            unit = new CustomItem(Material.BARRIER, "&cERROR");
        }

        String amount = BlockStorage.getLocationInfo(l, "stored");

        if (amount != null) {

            int stored = Integer.parseInt(amount);

            if (stored > 0) {

                String type = BlockStorage.getLocationInfo(l, "storeditem");

                if (type != null) {

                    ItemStack storedItemStack = StackUtils.getItemFromID(type, 1);

                    if (storedItemStack != null) {

                        item = StorageUnit.makeDisplayItem(getMax(id), storedItemStack, stored, id.equals("INFINITY_STORAGE"));
                    }
                }
            }
        }

        displayItem(inv, unit, item, spot);
    }

    /**
     * This method will display 2 the stored item and machine in an inventory at a specified spot
     *
     * @param inv BlockMenu to display in
     * @param item1 machine
     * @param item2 stored item
     * @param spot range 0 - MAX
     */
    private static void displayItem(BlockMenu inv, ItemStack item1, ItemStack item2, int spot) {
        int rows = (int) (1 + Math.floor((float) spot / 9));
        int slot = rows * 9 + spot;
        inv.replaceExistingItem(slot, item1);
        inv.replaceExistingItem(slot + 9, item2);
    }

    /**
     * This method gets the max storage of a storage unit from its id
     *
     * @param id id
     * @return the max storage
     */
    private static int getMax(@Nonnull String id) {
        switch (id) {
            case "BASIC_STORAGE":
                return StorageUnit.BASIC;
            case "ADVANCED_STORAGE":
                return StorageUnit.ADVANCED;
            case "REINFORCED_STORAGE":
                return StorageUnit.REINFORCED;
            case "VOID_STORAGE":
                return StorageUnit.VOID;
            case "INFINITY_STORAGE":
                return StorageUnit.INFINITY;
        }
        return 0;
    }
}