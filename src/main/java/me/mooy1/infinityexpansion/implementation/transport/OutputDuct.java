package me.mooy1.infinityexpansion.implementation.transport;

import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.utils.StackUtils;
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
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public OutputDuct() {
        super(Categories.STORAGE_TRANSPORT, Items.OUTPUT_DUCT, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{

        });

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

                BlockMenu machine = getOutputMachine(targetLocation);
                Inventory inventory = getInventory(targetBlock);

                if (inventory == null && machine == null) {
                    MessageUtils.message(e.getPlayer(), ChatColor.RED + "Must be placed on a machine or inventory");

                } else {

                    Location here = e.getBlockPlaced().getLocation();
                    int relative = getRelativeLocation(targetLocation, here);

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
            targetLocation = getTargetLocation(thisLocation, Integer.parseInt(relative));

            if (targetLocation == null || targetLocation.getBlock().getType() == Material.AIR) { //break when nothing connected
                breakBlock(thisBlock);
                return;
            }

            targetMachine = getOutputMachine(targetLocation);
            targetInventory = getInventory(targetLocation.getBlock());

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

        Pair<Pair<List<BlockMenu>, List<Inventory>>, Pair<List<Location>, Integer>> flow = inputFlow(0, thisLocation, new ArrayList<>(), new ArrayList<>(), checkedLocations, targetLocation);
        Pair<List<BlockMenu>, List<Inventory>> flowA = flow.getFirstValue();
        List<BlockMenu> menuList = flowA.getFirstValue();
        List<Inventory> invList = flowA.getSecondValue();

        if (menuList.isEmpty() && invList.isEmpty()) return;

        for (int whiteListSlot : WHITELIST_SLOTS) {
            ItemStack whiteListItem = thisMenu.getItemInSlot(whiteListSlot);
            String whiteListID = StackUtils.getIDFromItem(whiteListItem);
            if (whiteListID != null) {

                for (BlockMenu extractionMenu : menuList) {
                    int[] extractionSlots = getSlots(extractionMenu, ItemTransportFlow.WITHDRAW, whiteListItem);

                    if (extractionSlots != null && extractionSlots.length > 0) {
                        for (int slot : extractionSlots) {
                            ItemStack outputItem = extractionMenu.getItemInSlot(slot);
                            String outputID = StackUtils.getIDFromItem(outputItem);

                            if (outputItem != null && outputID != null && outputID.equals(whiteListID)) {

                                if (targetMachine != null) {
                                    int[] destinationSlots = getSlots(targetMachine, ItemTransportFlow.INSERT, outputItem);

                                    if (destinationSlots != null && targetMachine.fits(outputItem, destinationSlots)) {
                                        int amount = outputItem.getAmount();

                                        targetMachine.pushItem(outputItem, destinationSlots);

                                        extractionMenu.consumeItem(slot, amount);
                                    }

                                } else {

                                    ItemStack remainingItems = insertIntoVanillaInventory(outputItem, targetInventory);

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
                    int[] range = getOutputSlotRange(extractionInv);
                    int minSlot = range[0];
                    int maxSlot = range[1];

                    for (int slot = minSlot; slot < maxSlot; slot++) {

                        ItemStack slotItem = contents[slot];

                        if (slotItem != null) {

                            ItemStack outputItem = slotItem.clone();
                            String outputID = StackUtils.getIDFromItem(outputItem);

                            if (outputID != null && outputID.equals(whiteListID)) {

                                if (targetMachine != null) {
                                    int[] destinationSlots = getSlots(targetMachine, ItemTransportFlow.INSERT, outputItem);

                                    if (destinationSlots != null && targetMachine.fits(outputItem, destinationSlots)) {

                                        targetMachine.pushItem(outputItem, destinationSlots);

                                        slotItem.setAmount(slotItem.getAmount() - outputItem.getAmount());
                                    }

                                } else {

                                    ItemStack remainingItems = insertIntoVanillaInventory(outputItem, targetInventory);

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

    @Nonnull
    private Pair<Pair<List<BlockMenu>, List<Inventory>>, Pair<List<Location>, Integer>> inputFlow(int count, @Nonnull Location thisLocation, @Nonnull List<BlockMenu> menuList, @Nonnull List<Inventory> invList, @Nonnull List<Location> checkedLocations, @Nonnull Location prev) {
        Block thisBlock = thisLocation.getBlock();
        checkedLocations.add(thisLocation);

        String thisID = BlockStorage.checkID(thisLocation);

        if (thisID != null) { //try sf

            if (thisID.equals("OUTPUT_DUCT") || thisID.equals("ITEM_DUCT")) { //connector

                count++;

                for (Location location : getAdjacentLocations(thisLocation)) {

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

            Inventory inv = getInventory(thisBlock);

            if (inv != null) {

                invList.add(inv);

            }
        }

        return new Pair<>(new Pair<>(menuList, invList), new Pair<>(checkedLocations, count));
    }

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

    @Nullable
    private static BlockMenu getOutputMachine(@Nonnull Location location) {
        String id = BlockStorage.checkID(location);

        if (id != null) {
            BlockMenuPreset preset = BlockMenuPreset.getPreset(id);

            if (preset != null) {

                return BlockStorage.getInventory(location);

            }
        }
        return null;
    }

    @Nullable
    private static Inventory getInventory(@Nonnull Block b) {
        if (hasInventory(b)) {

            BlockState state = PaperLib.getBlockState(b, false).getState();

            if (state instanceof InventoryHolder) {

                return ((InventoryHolder) state).getInventory();
            }
        }

        return null;
    }

    @Nullable
    private static int[] getSlots(@Nullable BlockMenu menu, @Nonnull ItemTransportFlow itemTransportFlow, @Nullable ItemStack item) {
        if (menu != null) {

            BlockMenuPreset preset = menu.getPreset();

            int[] slots = preset.getSlotsAccessedByItemTransport(menu, itemTransportFlow, item);

            if (slots != null && slots.length > 0) {
                return slots;
            } else {
                slots = preset.getSlotsAccessedByItemTransport(itemTransportFlow);

                if (slots != null && slots.length > 0) {
                    return slots;
                }
            }
        }

        return null;
    }

    @Nonnull
    private static Location[] getAdjacentLocations(@Nonnull Location l) {
        Location[] locations = new Location[6];
        locations[0] = l.clone().add(1, 0, 0);
        locations[1] = l.clone().add(-1, 0, 0);
        locations[2] = l.clone().add(0, 1, 0);
        locations[3] = l.clone().add(0, -1, 0);
        locations[4] = l.clone().add(0, 0, 1);
        locations[5] = l.clone().add(0, 0, -1);

        return locations;
    }

    @Nullable
    private static Location getTargetLocation(@Nonnull Location l, int direction) {
        Location location = null;
        Location clone = l.clone();

        if (direction == 0) {
            location = clone.add(1, 0, 0);
        } else if (direction == 1) {
            location = clone.add(-1, 0, 0);
        } else if (direction == 2) {
            location = clone.add(0, 1, 0);
        } else if (direction == 3) {
            location = clone.add(0, -1, 0);
        } else if (direction == 4) {
            location = clone.add(0, 0, 1);
        } else if (direction == 5) {
            location = clone.add(0, 0, -1);
        }

        return location;
    }

    private static int getRelativeLocation(@Nonnull Location next, @Nonnull Location current) {
        int a = next.getBlockX();
        int b = next.getBlockY();
        int c = next.getBlockZ();
        int x = current.getBlockX();
        int y = current.getBlockY();
        int z = current.getBlockZ();

        if (a - x == 1) {
            return 0;
        } else if (a - x == -1) {
            return 1;
        } else if (b - y == 1) {
            return 2;
        } else if (b - y == -1) {
            return 3;
        } else if (c - z == 1) {
            return 4;
        } else if (c - z == -1) {
            return 5;
        } else {
            return 0;
        }
    }

    /**
     * The following methods are from CargoUtils
     * (Some slightly modified)
     *
     * @author TheBusyBiscuit
     * @author Mooy1
     *
     */

    public static boolean hasInventory(@Nonnull Block block) {

        Material type = block.getType();

        if (SlimefunTag.SHULKER_BOXES.isTagged(type)) {
            return true;
        }

        switch (type) {
            case CHEST:
            case TRAPPED_CHEST:
            case FURNACE:
            case DISPENSER:
            case DROPPER:
            case HOPPER:
            case BREWING_STAND:
            case BARREL:
            case BLAST_FURNACE:
            case SMOKER:
                return true;
            default:
                return false;
        }
    }

    public static int[] getOutputSlotRange(Inventory inv) {
        if (inv instanceof FurnaceInventory) {
            // Slot 2-3
            return new int[]{2, 3};
        } else if (inv instanceof BrewerInventory) {
            // Slot 0-3
            return new int[]{0, 3};
        } else {
            // Slot 0-size
            return new int[]{0, inv.getSize()};
        }
    }

    public static int[] getInputSlotRange(@Nonnull Inventory inv, @Nullable ItemStack item) {
        if (inv instanceof FurnaceInventory) {
            if (item != null && item.getType().isFuel()) {
                if (isSmeltable(item)) {
                    // Any non-smeltable items should not land in the upper slot
                    return new int[]{0, 2};
                } else {
                    return new int[]{1, 2};
                }
            } else {
                return new int[]{0, 1};
            }
        } else if (inv instanceof BrewerInventory) {
            if (isPotion(item)) {
                // Slots for potions
                return new int[]{0, 3};
            } else if (item != null && item.getType() == Material.BLAZE_POWDER) {
                // Blaze Powder slot
                return new int[]{4, 5};
            } else {
                // Input slot
                return new int[]{3, 4};
            }
        } else {
            // Slot 0-size
            return new int[]{0, inv.getSize()};
        }
    }

    public static boolean isSmeltable(@Nullable ItemStack stack) {
        return SlimefunPlugin.getMinecraftRecipeService().isSmeltable(stack);
    }

    public static boolean isPotion(@Nullable ItemStack item) {
        return item != null && (item.getType() == Material.POTION || item.getType() == Material.SPLASH_POTION || item.getType() == Material.LINGERING_POTION);
    }

    @Nullable
    private static ItemStack insertIntoVanillaInventory(@Nonnull ItemStack stack, @Nonnull Inventory inv) {
        ItemStack[] contents = inv.getContents();
        int[] range = getInputSlotRange(inv, stack);
        int minSlot = range[0];
        int maxSlot = range[1];

        ItemStackWrapper wrapper = new ItemStackWrapper(stack);

        for (int slot = minSlot; slot < maxSlot; slot++) {
            // Changes to this ItemStack are synchronized with the Item in the Inventory
            ItemStack itemInSlot = contents[slot];

            if (itemInSlot == null) {
                inv.setItem(slot, stack);
                return null;
            } else {
                int maxStackSize = itemInSlot.getType().getMaxStackSize();

                if (SlimefunUtils.isItemSimilar(itemInSlot, wrapper, true, false) && itemInSlot.getAmount() < maxStackSize) {
                    int amount = itemInSlot.getAmount() + stack.getAmount();

                    if (amount > maxStackSize) {
                        stack.setAmount(amount - maxStackSize);
                    } else {
                        stack = null;
                    }

                    itemInSlot.setAmount(Math.min(amount, maxStackSize));
                    return stack;
                }
            }
        }

        return stack;
    }
}
