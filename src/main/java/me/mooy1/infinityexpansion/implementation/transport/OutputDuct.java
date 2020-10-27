package me.mooy1.infinityexpansion.implementation.transport;

import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.utils.LocationUtils;
import me.mooy1.infinityexpansion.utils.StackUtils;
import me.mooy1.infinityexpansion.utils.MessageUtils;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mooy1.infinityexpansion.utils.TransferUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Connects to a machine or inventory and pushes in
 * whitelisted items from a list of BlockMenus and Inventories
 *
 * @author Mooy1
 *
 */
public class OutputDuct extends SlimefunItem {

    private static final int[] WHITELIST_SLOTS = {
            10, 11, 12, 13
    };
    private static final int[] BORDER = {
            0, 1, 2, 3, 4, 5,
            9, 14,
            18, 19, 20, 21, 22, 23
    };
    private static final int[] STATUS_BORDER = PresetUtils.slotChunk3;

    private static final int STATUS = 16;
    public static final int LENGTH = 12;
    public static final int MAX = 8;

    public OutputDuct() {
        super(Categories.STORAGE_TRANSPORT, Items.OUTPUT_DUCT, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.ITEM_DUCT, new ItemStack(Material.HOPPER), Items.ITEM_DUCT,
                Items.MACHINE_PLATE, Items.MACHINE_CIRCUIT, Items.MACHINE_PLATE,
                Items.ITEM_DUCT, new ItemStack(Material.HOPPER), Items.ITEM_DUCT
        }, new CustomItem(Items.OUTPUT_DUCT, 2));

        new BlockMenuPreset(getId(), Objects.requireNonNull(Items.OUTPUT_DUCT.getDisplayName())) {
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

        addItemHandler(new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(BlockPlaceEvent e) {
                Block targetBlock = e.getBlockAgainst();
                Location targetLocation = targetBlock.getLocation();

                BlockMenu machine = TransferUtils.getMenu(targetLocation);
                Inventory inventory = TransferUtils.getInventory(targetBlock);

                if (inventory == null && machine == null) {
                    MessageUtils.message(e.getPlayer(), ChatColor.RED + "Must be placed on a machine or inventory");

                } else {

                    Location here = e.getBlockPlaced().getLocation();
                    int relative = LocationUtils.getDirectionInt(targetLocation, here);

                    BlockStorage.addBlockInfo(here, "connected", String.valueOf(relative));

                    ItemStack item = null;
                    String idCheck = BlockStorage.checkID(targetLocation);

                    if (idCheck != null) {
                        ItemStack test = StackUtils.getItemFromID(idCheck, 1);

                        if (test != null) {
                            item = test;
                        }
                    }

                    if (item == null) {
                        item = new ItemStack(targetBlock.getType());
                    }

                    List<String> lore = new ArrayList<>();

                    if (targetLocation.getWorld() != null) {
                        lore.add("");
                        lore.add(ChatColor.GREEN + "Location: ");
                        lore.add(ChatColor.GREEN + "X " + targetLocation.getX());
                        lore.add(ChatColor.GREEN + "Y " + targetLocation.getY());
                        lore.add(ChatColor.GREEN + "Z " + targetLocation.getZ());
                    }

                    StackUtils.addLore(item, lore);

                    BlockStorage.getInventory(here).replaceExistingItem(STATUS, item);

                }
            }
        });

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            onDrop(inv, b);

            return true;
        });
    }

    private static void onDrop(BlockMenu inv, Block b) {
        if (inv != null) {
            inv.dropItems(b.getLocation(), WHITELIST_SLOTS);
        }
    }

    private void setupInv(BlockMenuPreset blockMenuPreset) {

        for (int i : BORDER) {
            blockMenuPreset.addItem(i, new CustomItem(Material.WHITE_STAINED_GLASS_PANE, "&fWhitelist"),
                    ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : STATUS_BORDER) {
            blockMenuPreset.addItem(i, new CustomItem(Material.CYAN_STAINED_GLASS_PANE, "&3Connected Block"),
                    ChestMenuUtils.getEmptyClickHandler());
        }

        blockMenuPreset.addMenuClickHandler(STATUS, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) {
                OutputDuct.this.tick(b);
            }

            public boolean isSynchronized() {
                return true;
            }
        });
    }

    public void tick(Block thisBlock) {
        BlockMenu thisMenu = BlockStorage.getInventory(thisBlock);
        if (thisMenu == null) return;

        Location thisLocation = thisBlock.getLocation();

        String relative = BlockStorage.getLocationInfo(thisLocation, "connected");

        BlockMenu targetMachine;
        Inventory targetInventory;
        Location targetLocation;

        if (relative != null) {
            targetLocation = LocationUtils.getRelativeLocation(thisLocation, Integer.parseInt(relative));

            if (targetLocation == null || targetLocation.getBlock().getType() == Material.AIR) { //break when nothing connected
                breakBlock(thisBlock);
                return;
            }

            targetMachine = TransferUtils.getMenu(targetLocation);
            targetInventory = TransferUtils.getInventory(targetLocation.getBlock());

            if (targetMachine == null && targetInventory == null) { //error
                breakBlock(thisBlock);
                return;
            }

        } else { //error
            breakBlock(thisBlock);
            return;
        }

        List<Location> checkedLocations = new ArrayList<>();
        checkedLocations.add(targetLocation);

        if (targetInventory != null) { //add double chest to checked
            addDoubleChest(targetInventory, checkedLocations);
        }

        Pair<Pair<List<BlockMenu>, List<Inventory>>, Pair<List<Location>, Integer>> flow = inputFlow(0, thisLocation, new ArrayList<>(), new ArrayList<>(), checkedLocations, targetLocation);
        Pair<List<BlockMenu>, List<Inventory>> flowA = flow.getFirstValue();
        List<BlockMenu> menuList = flowA.getFirstValue();
        List<Inventory> invList = flowA.getSecondValue();

        if (thisMenu.hasViewer()) {
            int length = flow.getSecondValue().getSecondValue();
            int size = menuList.size() + invList.size();

            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColors.color("&6Inventories: &e" + size + " / " + MAX));
            lore.add(ChatColors.color("&6Length: &e" + length + " / " + LENGTH));

            StackUtils.insertLore(thisMenu.getItemInSlot(STATUS), lore, "Location:", 4);

        }

        if (menuList.isEmpty() && invList.isEmpty()) return;

        for (int whiteListSlot : WHITELIST_SLOTS) {
            ItemStack whiteListItem = thisMenu.getItemInSlot(whiteListSlot);
            String whiteListID = StackUtils.getIDFromItem(whiteListItem);
            if (whiteListID != null) {

                for (BlockMenu extractionMenu : menuList) {
                    int[] extractionSlots = TransferUtils.getSlots(extractionMenu, ItemTransportFlow.WITHDRAW, whiteListItem);

                    if (extractionSlots != null && extractionSlots.length > 0) {
                        for (int slot : extractionSlots) {
                            ItemStack outputItem = extractionMenu.getItemInSlot(slot);
                            String outputID = StackUtils.getIDFromItem(outputItem);

                            if (outputItem != null && outputID != null && outputID.equals(whiteListID)) {

                                if (targetMachine != null) {
                                    int[] destinationSlots = TransferUtils.getSlots(targetMachine, ItemTransportFlow.INSERT, outputItem);

                                    if (destinationSlots != null && targetMachine.fits(outputItem, destinationSlots)) {
                                        int amount = outputItem.getAmount();

                                        try {
                                            targetMachine.pushItem(outputItem, destinationSlots);
                                            extractionMenu.consumeItem(slot, amount);
                                        } catch (NullPointerException ignored) { }

                                    }

                                } else {

                                    ItemStack remainingItems = TransferUtils.insertToVanillaInventory(outputItem, targetInventory);

                                    int extractedAmount;
                                    if (remainingItems == null) {
                                        extractedAmount = outputItem.getAmount();
                                    } else {
                                        extractedAmount = outputItem.getAmount() - remainingItems.getAmount();
                                    }

                                    extractionMenu.consumeItem(slot, extractedAmount);

                                }

                                break;
                            }
                        }
                    }
                }

                for (Inventory extractionInv : invList) {

                    ItemStack[] contents = extractionInv.getContents();
                    int[] range = TransferUtils.getOutputSlotRange(extractionInv);
                    int minSlot = range[0];
                    int maxSlot = range[1];

                    for (int slot = minSlot; slot < maxSlot; slot++) {

                        ItemStack slotItem = contents[slot];

                        if (slotItem != null) {

                            ItemStack outputItem = slotItem.clone();
                            String outputID = StackUtils.getIDFromItem(outputItem);

                            if (outputID != null && outputID.equals(whiteListID)) {

                                if (targetMachine != null) {
                                    int[] destinationSlots = TransferUtils.getSlots(targetMachine, ItemTransportFlow.INSERT, outputItem);

                                    if (destinationSlots != null && targetMachine.fits(outputItem, destinationSlots)) {

                                        targetMachine.pushItem(outputItem, destinationSlots);

                                        slotItem.setAmount(slotItem.getAmount() - outputItem.getAmount());
                                    }

                                } else {

                                    ItemStack remainingItems = TransferUtils.insertToVanillaInventory(outputItem, targetInventory);

                                    int amount;
                                    if (remainingItems == null) {
                                        amount = 0;
                                    } else {
                                        amount = remainingItems.getAmount();
                                    }

                                    slotItem.setAmount(amount);
                                }

                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * This methods will search though a path of blocks
     * starting at the target location for BlockMenus and Inventories,
     * It will stop when the count reaches max
     * or their are no locations to check.
     *
     * @param count current amount of ducts found
     * @param thisLocation the location if the block being checked
     * @param menuList list of found BlockMenus
     * @param invList list of found Inventories
     * @param checkedLocations list of checked locations so it wont check the same spot twice
     * @param prev the previous location, fast way to not check the previous location
     * @return The list of BlockMenus, List of Inventories, count, checked locations
     */
    @Nonnull
    private Pair<Pair<List<BlockMenu>, List<Inventory>>, Pair<List<Location>, Integer>> inputFlow(int count, @Nonnull Location thisLocation, @Nonnull List<BlockMenu> menuList, @Nonnull List<Inventory> invList, @Nonnull List<Location> checkedLocations, @Nonnull Location prev) {
        checkedLocations.add(thisLocation);

        if (menuList.size() + invList.size() < MAX) {

            Block thisBlock = thisLocation.getBlock();
            String thisID = BlockStorage.checkID(thisLocation);

            if (thisID != null) { //try sf

                if (thisID.equals("OUTPUT_DUCT") || thisID.equals("ITEM_DUCT")) { //connector

                    count++;

                    for (Location location : LocationUtils.getAdjacentLocations(thisLocation)) {

                        if (location != prev && !checkedLocations.contains(location) && count < LENGTH) {

                            Pair<Pair<List<BlockMenu>, List<Inventory>>, Pair<List<Location>, Integer>> flow = inputFlow(count, location, menuList, invList, checkedLocations, location);
                            Pair<List<BlockMenu>, List<Inventory>> flowA = flow.getFirstValue();
                            Pair<List<Location>, Integer> flowB = flow.getSecondValue();
                            menuList = flowA.getFirstValue();
                            invList = flowA.getSecondValue();
                            checkedLocations = flowB.getFirstValue();
                            count = flowB.getSecondValue();

                        }
                    }

                } else if (BlockStorage.hasInventory(thisBlock)) { //machine

                    menuList.add(BlockStorage.getInventory(thisLocation));

                }

            } else { //try vanilla

                Inventory inv = TransferUtils.getInventory(thisBlock);

                if (inv != null) {

                    invList.add(inv);
                    addDoubleChest(inv, checkedLocations);
                }
            }
        }

        return new Pair<>(new Pair<>(menuList, invList), new Pair<>(checkedLocations, count));
    }

    /**
     * Method to break this block, drop the corrected items, and clear block data
     * Modified version of Slimefuns method
     *
     * @param b block to break
     */
    private static void breakBlock(@Nonnull Block b) {
        SlimefunItem sfItem = BlockStorage.check(b);

        List<ItemStack> drops = new ArrayList<>();

        if (sfItem != null) {
            drops.addAll(sfItem.getDrops());
        }

        onDrop(BlockStorage.getInventory(b), b);

        BlockStorage.clearBlockInfo(b);
        b.setType(Material.AIR);

        for (ItemStack drop : drops) {
            if (drop != null && drop.getType() != Material.AIR) {
                b.getWorld().dropItemNaturally(b.getLocation(), drop);
            }
        }
    }

    private static void addDoubleChest(Inventory inv, List<Location> checkedLocations) {
        Pair<Location, Location> doubleChestCheck = TransferUtils.getOtherChest(inv);
        if (doubleChestCheck != null) {
            checkedLocations.add(doubleChestCheck.getFirstValue());
            checkedLocations.add(doubleChestCheck.getSecondValue());
        }
    }
}
