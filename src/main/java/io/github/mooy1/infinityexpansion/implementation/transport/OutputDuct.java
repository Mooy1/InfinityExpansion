package io.github.mooy1.infinityexpansion.implementation.transport;

import io.github.mooy1.infinityexpansion.implementation.template.Machine;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.LocationUtils;
import io.github.mooy1.infinityexpansion.utils.MessageUtils;
import io.github.mooy1.infinityexpansion.utils.StackUtils;
import io.github.mooy1.infinityexpansion.utils.TransferUtils;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.apache.commons.lang.mutable.MutableInt;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.ParametersAreNullableByDefault;
import java.util.ArrayList;
import java.util.List;

/**
 * Connects to a machine or inventory and pushes in
 * whitelisted items from a list of BlockMenus and Inventories
 *
 * @author Mooy1
 *
 */
public class OutputDuct extends Machine {

    private static final int[] WHITELIST_SLOTS = {
            10, 11, 12
    };
    private static final int[] BORDER = {
            0, 1, 2, 3, 4,
            9, 13,
            18, 19, 20, 21, 22
    };
    private static final int[] STATUS_BORDER = {
            5, 6, 7, 8,
            14, 17,
            23, 24, 25, 26
    };

    private static final ItemStack BLACKLIST_ON = new CustomItem(Material.BLACK_STAINED_GLASS_PANE, "&7Blacklist - &aEnabled", "", "&7Click to swap");
    private static final ItemStack BLACKLIST_OFF = new CustomItem(Material.GRAY_STAINED_GLASS_PANE, "&7Blacklist - &cDisabled", "", "&7Click to swap");

    private static final int BLACKLIST_TOGGLE = 15;
    private static final int STATUS_SLOT = 16;
    public static int DUCT_LENGTH = 12;
    public static int MAX_INVS = 8;
    public static int MAX_SLOTS = 9;

    public OutputDuct() {
        super(Categories.STORAGE_TRANSPORT, Items.OUTPUT_DUCT, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.ITEM_DUCT, new ItemStack(Material.HOPPER), Items.ITEM_DUCT,
                Items.MAGSTEEL, Items.MACHINE_CIRCUIT, Items.MAGSTEEL,
                Items.ITEM_DUCT, new ItemStack(Material.HOPPER), Items.ITEM_DUCT
        }, new CustomItem(Items.OUTPUT_DUCT, 2));

        addItemHandler(new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(BlockPlaceEvent e) {
                Block targetBlock = e.getBlockAgainst();
                Location targetLocation = targetBlock.getLocation();

                BlockMenu machine = TransferUtils.getMenu(targetLocation);
                Inventory inventory = TransferUtils.getInventory(targetBlock);

                if (inventory == null && (machine == null || machine.getPreset().getID().equals("OUTPUT_DUCT"))) {
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

                    StackUtils.addLore(item, "", ChatColor.GREEN + "Location: ",
                            ChatColor.GREEN + "X " + targetLocation.getX(),
                            ChatColor.GREEN + "Y " + targetLocation.getY(),
                            ChatColor.GREEN + "Z " + targetLocation.getZ()
                    );

                    BlockStorage.getInventory(here).replaceExistingItem(STATUS_SLOT, item);

                }
            }
        });

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            onDrop(inv, b);

            return true;
        });
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        BlockStorage.addBlockInfo(b.getLocation(), "blacklist", "false");

        menu.addMenuClickHandler(BLACKLIST_TOGGLE, (player, i, itemStack, clickAction) -> {
            if (isBlacklisted(b)) {
                menu.replaceExistingItem(BLACKLIST_TOGGLE, BLACKLIST_OFF);
                BlockStorage.addBlockInfo(b.getLocation(), "blacklist", "false");
            } else {
                menu.replaceExistingItem(BLACKLIST_TOGGLE, BLACKLIST_ON);
                BlockStorage.addBlockInfo(b.getLocation(), "blacklist", "true");
            }
            return false;
        });
    }

    private boolean isBlacklisted(Block b) {
        return BlockStorage.getLocationInfo(b.getLocation(), "blacklist").equals("true");
    }

    private void onDrop(BlockMenu inv, Block b) {
        if (inv != null) {
            inv.dropItems(b.getLocation(), WHITELIST_SLOTS);
        }
    }

    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : BORDER) {
            blockMenuPreset.addItem(i, new CustomItem(Material.WHITE_STAINED_GLASS_PANE, "&fWhitelist"),
                    ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : STATUS_BORDER) {
            blockMenuPreset.addItem(i, new CustomItem(Material.CYAN_STAINED_GLASS_PANE, "&3Connected Block"),
                    ChestMenuUtils.getEmptyClickHandler());
        }

        blockMenuPreset.addItem(BLACKLIST_TOGGLE, BLACKLIST_OFF, ChestMenuUtils.getEmptyClickHandler());

        blockMenuPreset.addMenuClickHandler(STATUS_SLOT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        return new int[0];
    }

    @Override
    public void tick(@Nonnull Block thisBlock, @Nonnull Location l, @Nonnull BlockMenu thisMenu) {
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

        if (!isBlacklisted(thisBlock)) {
            int count = 0;
            for (int slot : WHITELIST_SLOTS) {
                if (thisMenu.getItemInSlot(slot) == null) {
                    count++;
                }
            }
            if (count == WHITELIST_SLOTS.length) {
                if (thisMenu.hasViewer()) {
                    ItemStack item = thisMenu.getItemInSlot(STATUS_SLOT);
                    StackUtils.insertLore(item, 4, "Location:", "", ChatColors.color("&6Whitelist an item or enable blacklist!"));
                    thisMenu.replaceExistingItem(STATUS_SLOT, item);
                }
                return;
            }
        }

        List<Location> checkedLocations = new ArrayList<>();
        checkedLocations.add(targetLocation);
        if (targetInventory != null) { //add double chest to checked
            addDoubleChest(targetInventory, checkedLocations);
        }
        MutableInt length = new MutableInt(0);
        List<BlockMenu> menuList = new ArrayList<>();
        List<Inventory> invList = new ArrayList<>();

        inputFlow(thisLocation, targetLocation, checkedLocations, length, menuList, invList);

        int found = menuList.size() + invList.size();

        if (thisMenu.hasViewer()) {

            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColors.color("&6Inventories: &e" + found + " / " + MAX_INVS));
            lore.add(ChatColors.color("&6Length: &e" + length + " / " + DUCT_LENGTH));

            ItemStack item = thisMenu.getItemInSlot(STATUS_SLOT);

            StackUtils.insertLore(item, 4, "Location:", lore);

            thisMenu.replaceExistingItem(STATUS_SLOT, item);
        }

        if (menuList.isEmpty() && invList.isEmpty()) return;

        if (isBlacklisted(thisBlock)) {

            output(null, menuList, invList, targetMachine, targetInventory);

        } else {

            for (int whiteListSlot : WHITELIST_SLOTS) {
                ItemStack whiteListItem = thisMenu.getItemInSlot(whiteListSlot);
                String whiteListID = StackUtils.getIDFromItem(whiteListItem);

                if (whiteListID != null) {
                    if (output(whiteListID, menuList, invList, targetMachine, targetInventory)) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * This method outputs items to the target inv/menu from a list of inv/menus and will follow a whitelist if not null
     *
     * @param whiteListID whitelisted item id if present
     * @param menuList list of menus to extract from
     * @param invList list of invs to extract from
     * @param targetMachine target machine menu if present
     * @param targetInventory target inventory menu if present
     *                        
     * @return whether it was successful       
     */
    @ParametersAreNullableByDefault
    private boolean output(@Nullable String whiteListID, @Nonnull List<BlockMenu> menuList, @Nonnull List<Inventory> invList, @Nullable BlockMenu targetMachine, @Nullable Inventory targetInventory) {
        //sf extractions
        for (BlockMenu extractionMenu : menuList) {
            int[] extractionSlots;

            if (whiteListID == null) {
                extractionSlots = extractionMenu.getPreset().getSlotsAccessedByItemTransport(ItemTransportFlow.WITHDRAW);
            } else {
                extractionSlots = TransferUtils.getSlots(extractionMenu, ItemTransportFlow.WITHDRAW, StackUtils.getItemFromID(whiteListID, 1));
            }

            int count = 1;
            if (extractionSlots != null && extractionSlots.length > 0) {
                for (int slot : extractionSlots) {
                    ItemStack outputItem = extractionMenu.getItemInSlot(slot);
                    String outputID = StackUtils.getIDFromItem(outputItem);

                    if (outputItem != null && (whiteListID == null || (outputID != null && outputID.equals(whiteListID)))) {

                        if (targetMachine != null) { //sf insertion

                            int[] destinationSlots = TransferUtils.getSlots(targetMachine, ItemTransportFlow.INSERT, outputItem);

                            if (destinationSlots != null && targetMachine.fits(outputItem, destinationSlots)) {
                                int amount = outputItem.getAmount();

                                try {
                                    targetMachine.pushItem(outputItem, destinationSlots);
                                    extractionMenu.consumeItem(slot, amount);
                                } catch (NullPointerException ignored) {
                                }

                            } else {
                                return false;
                            }

                        } else if (targetInventory != null) { //vanilla insertion

                            ItemStack remainingItems = TransferUtils.insertToVanillaInventory(outputItem, targetInventory);

                            if (remainingItems == null) {
                                extractionMenu.consumeItem(slot, outputItem.getAmount());
                            } else if (outputItem.getAmount() - remainingItems.getAmount() > 0) {
                                extractionMenu.consumeItem(slot, outputItem.getAmount() - remainingItems.getAmount());
                            } else {
                                return false;
                            }
                        }

                        break;
                    }
                }
                count++;

                if (count >= MAX_SLOTS) break;
            }
        }

        //vanilla extractions

        for (Inventory extractionInv : invList) {

            ItemStack[] contents = extractionInv.getContents();
            int[] range = TransferUtils.getOutputSlotRange(extractionInv);
            int minSlot = range[0];
            int maxSlot = range[1];

            int count = 0;
            for (int slot = minSlot; slot < maxSlot; slot++) {

                ItemStack slotItem = contents[slot];

                if (slotItem != null) {

                    ItemStack outputItem = slotItem.clone();
                    String outputID = StackUtils.getIDFromItem(outputItem);

                    if (whiteListID == null || (outputID != null && outputID.equals(whiteListID))) {

                        if (targetMachine != null) { //sf insertion

                            int[] destinationSlots = TransferUtils.getSlots(targetMachine, ItemTransportFlow.INSERT, outputItem);

                            if (destinationSlots != null && targetMachine.fits(outputItem, destinationSlots)) {

                                try {
                                    targetMachine.pushItem(outputItem, destinationSlots);
                                    slotItem.setAmount(slotItem.getAmount() - outputItem.getAmount());
                                } catch (NullPointerException ignored) {
                                }

                            } else {
                                return false;
                            }

                        } else if (targetInventory != null) { //vanilla insertion

                            ItemStack remainingItems = TransferUtils.insertToVanillaInventory(outputItem, targetInventory);

                            if (remainingItems == null) {
                                slotItem.setAmount(0);
                            } else if (slotItem.getAmount() - remainingItems.getAmount() > 0) {
                                slotItem.setAmount(remainingItems.getAmount());
                            } else {
                                return false;
                            }
                        }

                        break;
                    }
                }
                count++;

                if (count >= MAX_SLOTS) break;
            }
        }
        
        return true;
    }

    /**
     * This methods will search though a path of blocks
     * starting at the target location for BlockMenus and Inventories,
     * It will stop when the count reaches max
     * or their are no locations to check.
     *
     * @param length current amount of ducts found
     * @param thisLocation the location if the block being checked
     * @param menuList list of found BlockMenus
     * @param invList list of found Inventories
     * @param checkedLocations list of checked locations so it wont check the same spot twice
     * @param prev the previous location, fast way to not check the previous location
     */
    @ParametersAreNonnullByDefault
    private boolean inputFlow(Location thisLocation, Location prev, List<Location> checkedLocations, MutableInt length, List<BlockMenu> menuList, List<Inventory> invList) {
        checkedLocations.add(thisLocation);

        if (length.intValue() >= DUCT_LENGTH || menuList.size() + invList.size() >= MAX_INVS) {
            return true;
        }

        Block thisBlock = thisLocation.getBlock();
        Material thisMaterial = thisBlock.getType();

        if (thisMaterial == Material.AIR) {
            return false;
        }

        if (thisMaterial == Items.OUTPUT_DUCT.getType() || thisMaterial == Items.ITEM_DUCT.getType()) {
            String thisID = BlockStorage.checkID(thisLocation);

            if (thisID.equals("OUTPUT_DUCT") || thisID.equals("ITEM_DUCT")) { //connector

                length.increment();

                for (Location location : LocationUtils.getAdjacentLocations(thisLocation, false)) {

                    if (location != prev && !checkedLocations.contains(location)) {

                        //try input flow on each unless the max length is reached
                        if (inputFlow(location, thisLocation, checkedLocations, length, menuList, invList)) {
                            break;
                        }
                    }
                }

                return false;
            }
        }

        if (BlockStorage.hasInventory(thisBlock)) { //machine

            menuList.add(BlockStorage.getInventory(thisLocation));
            return false;
        }

        Inventory inv = TransferUtils.getInventory(thisBlock);

        if (inv != null) {

            invList.add(inv);
            addDoubleChest(inv, checkedLocations);
        }

        return false;
    }

    /**
     * Method to break this block, drop the corrected items, and clear block data
     *
     * @param b block to break
     */
    private void breakBlock(@Nonnull Block b) {
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

    /**
     * This method adds the second chest of a double chest to a list of locations
     *
     * @param inv double chest inv
     * @param checkedLocations list
     */
    @ParametersAreNonnullByDefault
    private static void addDoubleChest(Inventory inv, List<Location> checkedLocations) {
        Pair<Location, Location> doubleChestCheck = TransferUtils.getOtherChest(inv);
        if (doubleChestCheck != null) {

            checkedLocations.add(doubleChestCheck.getFirstValue());
            checkedLocations.add(doubleChestCheck.getSecondValue());
        }
    }
}
