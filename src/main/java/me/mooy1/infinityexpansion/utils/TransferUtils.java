package me.mooy1.infinityexpansion.utils;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


/**
 * Collection of item transfer utils
 *
 * @author Mooy1
 */
public class TransferUtils {

    private TransferUtils() {}

    /**
     * This method gets the BlockMenu of a location
     *
     * @param location location
     * @return the BlockMenu if it exists
     */
    @Nullable
    public static BlockMenu getMenu(@Nonnull Location location) {
        String id = BlockStorage.checkID(location);

        if (id != null) {
            BlockMenuPreset preset = BlockMenuPreset.getPreset(id);

            if (preset != null) {

                return BlockStorage.getInventory(location);

            }
        }
        return null;
    }

    /**
     * This method gets the vanilla inventory of a block
     *
     * @param b block
     * @return the inventory if it exists
     */
    @Nullable
    public static Inventory getInventory(@Nonnull Block b) {
        if (hasInventory(b)) {

            BlockState state = PaperLib.getBlockState(b, false).getState();

            if (state instanceof InventoryHolder) {

                return ((InventoryHolder) state).getInventory();
            }
        }

        return null;
    }

    /**
     * This method gets the location of the other chest in a double chest
     *
     * @param inventory inventory of chest
     * @param found location thats already known
     * @return the other location
     */
    @Nullable
    public static Location getOtherChest(@Nonnull Inventory inventory, @Nonnull Location found) {
        InventoryHolder holder = inventory.getHolder();

        if (holder instanceof DoubleChest) {

            DoubleChest doubleChest = ((DoubleChest) holder);
            Chest leftChest = (Chest) doubleChest.getLeftSide();
            Chest rightChest = (Chest) doubleChest.getRightSide();

            if (leftChest != null && rightChest != null) {

                if (leftChest.getLocation() == found) {
                    return rightChest.getLocation();
                } else {
                    return leftChest.getLocation();
                }
            }
        }

        return null;
    }

    /**
     * This methods gets the slots of a BlockMenu for a type of flow
     *
     * @param menu menu to check
     * @param itemTransportFlow input or output
     * @param item item being inputted
     * @return slots
     */
    @Nullable
    public static int[] getSlots(@Nullable BlockMenu menu, @Nonnull ItemTransportFlow itemTransportFlow, @Nullable ItemStack item) {
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

    /**
     * The following methods are from CargoUtils
     * (Some slightly modified)
     *
     * @author TheBusyBiscuit
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

    /**
     * This method gets the output slots of a vanilla inventory
     *
     * @param inv inventory
     * @return slot range
     */
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

    /**
     * This method gets the input slot range of a vanilla inventory
     *
     * @param inv inventory
     * @param item item being inserted
     * @return slot range
     */
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

    /**
     * This method inserts an ItemStack into a vanilla inventory
     *
     * @param stack stack to insert
     * @param inv inventory to insert to
     * @return remaining items
     */
    @Nullable
    public static ItemStack insertToVanillaInventory(@Nonnull ItemStack stack, @Nonnull Inventory inv) {
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
