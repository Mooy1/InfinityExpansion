package io.github.mooy1.infinityexpansion.implementation.storage;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.items.LoreUtils;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.slimefun.presets.LorePreset;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import static io.github.mooy1.infinityexpansion.implementation.storage.StorageUnit.DISPLAY_KEY;
import static io.github.mooy1.infinityexpansion.implementation.storage.StorageUnit.DISPLAY_SLOT;
import static io.github.mooy1.infinityexpansion.implementation.storage.StorageUnit.EMPTY_KEY;
import static io.github.mooy1.infinityexpansion.implementation.storage.StorageUnit.INPUT_SLOT;
import static io.github.mooy1.infinityexpansion.implementation.storage.StorageUnit.INTERACT_SLOT;
import static io.github.mooy1.infinityexpansion.implementation.storage.StorageUnit.OUTPUT_SLOT;
import static io.github.mooy1.infinityexpansion.implementation.storage.StorageUnit.STATUS_SLOT;

/**
 * Represents a single storage unit with cached data and main functionality
 *
 * @author Mooy1
 */
final class StorageCache {

    /* Configuration */
    private static final boolean DISPLAY_SIGNS = InfinityExpansion.inst().getConfig().getBoolean("storage-unit-options.display-signs");

    /* Menu items */
    private static final ItemStack EMPTY_ITEM = new CustomItem(Material.BARRIER, meta -> {
        meta.setDisplayName(ChatColor.WHITE + "Empty");
        meta.getPersistentDataContainer().set(EMPTY_KEY, PersistentDataType.BYTE, (byte) 1);
    });

    /* Menu strings */
    private static final String EMPTY_DISPLAY_NAME = ChatColor.WHITE + "Empty";
    private static final String VOID_EXCESS_TRUE = ChatColors.color("&7Void Excess:&e true");
    private static final String VOID_EXCESS_FALSE = ChatColors.color("&7Void Excess:&e false");

    /* BlockStorage keys */
    private static final String OLD_STORED_ITEM = "storeditem"; // old item key in block data
    private static final String STORED_AMOUNT = "stored"; // amount key in block data
    private static final String VOID_EXCESS = "void_excess";
    
    /* Instance constants */
    private final StorageUnit unit;
    private final BlockMenu menu;
    
    /* Instance variables */
    private boolean voidExcess;
    private int amount;
    private Material material;
    private ItemMeta meta;
    private String displayName;

    StorageCache(StorageUnit unit, Block block, BlockMenu menu) {
        
        // menu click handlers
        menu.addMenuClickHandler(STATUS_SLOT, (p, slot, item, action) -> {
            voidExcessHandler();
            return false;
        });
        menu.addMenuClickHandler(INTERACT_SLOT, (p, slot, item, action) -> {
            interactHandler(p, action);
            return false;
        });
        
        // instance vars
        this.unit = unit;
        this.menu = menu;
        this.voidExcess = BlockStorage.getLocationInfo(block.getLocation(), VOID_EXCESS) != null;
        this.amount = Util.getIntData(STORED_AMOUNT, block.getLocation());

        if (this.amount == 0) {
            // empty
            this.displayName = EMPTY_DISPLAY_NAME;
            menu.replaceExistingItem(DISPLAY_SLOT, EMPTY_ITEM);
        } else {
            // something is stored
            ItemStack display = menu.getItemInSlot(DISPLAY_SLOT);
            if (display != null) {
                ItemMeta copy = display.getItemMeta();
                // fix if they somehow store the empty item
                if (copy.getPersistentDataContainer().has(EMPTY_KEY, PersistentDataType.BYTE)) {
                    // attempt to recover the correct item from output
                    ItemStack output = menu.getItemInSlot(OUTPUT_SLOT);
                    if (output != null) {
                        setStored(output);
                        menu.replaceExistingItem(OUTPUT_SLOT, null);
                    } else {
                        setEmpty();
                    }
                } else {
                    // load the item in menu
                    load(display, copy);
                }
            } else {
                // attempt to load old data
                String oldID = BlockStorage.getLocationInfo(block.getLocation(), OLD_STORED_ITEM);
                if (oldID != null) {
                    BlockStorage.addBlockInfo(block.getLocation(), OLD_STORED_ITEM, null);
                    ItemStack item = StackUtils.getItemByIDorType(oldID);
                    if (item != null) {
                        load(item, item.getItemMeta());
                    } else {
                        // shouldn't happen
                        setEmpty();
                    }
                } else {
                    // shouldn't happen
                    setEmpty();
                }
            }
        }
    }
    
    void load(ItemStack stored, ItemMeta copy) {
        this.menu.replaceExistingItem(DISPLAY_SLOT, stored);

        // remove the display key from copy
        copy.getPersistentDataContainer().remove(DISPLAY_KEY);

        // check if the copy has anything besides the display key
        if (copy.equals(Bukkit.getItemFactory().getItemMeta(stored.getType()))) {
            this.meta = null;
            this.displayName = StackUtils.getInternalName(stored);
        } else {
            this.meta = copy;
            this.displayName = StackUtils.getDisplayName(stored, copy);
        }
        this.material = stored.getType();
    }

    void setAmount(int amount) {
        this.amount = amount;
        BlockStorage.addBlockInfo(this.menu.getLocation(), STORED_AMOUNT, String.valueOf(amount));
    }
    
    void destroy(BlockBreakEvent e) {
        if (this.amount != 0) {
            e.setDropItems(false);

            // add output slot
            ItemStack output = this.menu.getItemInSlot(OUTPUT_SLOT);
            if (output != null && matches(output)) {
                int add = Math.min(this.unit.max - this.amount, output.getAmount());
                if (add != 0) {
                    this.amount += add;
                    output.setAmount(output.getAmount() - add);
                }
            }

            ItemStack drop = this.unit.getItem().clone();
            drop.setItemMeta(StorageUnit.saveToStack(drop.getItemMeta(), this.menu.getItemInSlot(DISPLAY_SLOT), this.displayName, this.amount));
            e.getPlayer().sendMessage(ChatColor.GREEN + "Stored items transferred to dropped item");
            e.getBlock().getWorld().dropItemNaturally(this.menu.getLocation(), drop);
        }

        this.menu.dropItems(this.menu.getLocation(), INPUT_SLOT, OUTPUT_SLOT);
    }

    void input() {
        ItemStack input = this.menu.getItemInSlot(INPUT_SLOT);
        if (input == null) {
            return;
        }
        if (this.amount == 0) {
            // set the stored item to input
            setAmount(input.getAmount());
            setStored(input);
            this.menu.replaceExistingItem(INPUT_SLOT, null, false);
        } else if (matches(input)) {
            if (this.voidExcess) {
                // input and void excess
                if (this.amount < this.unit.max) {
                    setAmount(Math.min(this.amount + input.getAmount(), this.unit.max));
                }
                input.setAmount(0);
            } else if (this.amount < this.unit.max) {
                // input as much as possible
                if (input.getAmount() + this.amount >= this.unit.max) {
                    // last item
                    input.setAmount(input.getAmount() - (this.unit.max - this.amount));
                    setAmount(this.unit.max);
                } else {
                    setAmount(this.amount + input.getAmount());
                    input.setAmount(0);
                }
            }
        }
    }

    void output(boolean partial) {
        if (this.amount != 0) {
            ItemStack outputSlot = this.menu.getItemInSlot(OUTPUT_SLOT);
            if (outputSlot == null) {
                if (this.amount == 1) {
                    this.menu.replaceExistingItem(OUTPUT_SLOT, createItem(1), false);
                    setEmpty();
                } else {
                    int amt = Math.min(this.material.getMaxStackSize(), this.amount - 1);
                    this.menu.replaceExistingItem(OUTPUT_SLOT, createItem(amt), false);
                    setAmount(this.amount - amt);
                }
            } else if (partial && this.amount != 1) {
                int amt = Math.min(this.material.getMaxStackSize() - outputSlot.getAmount(), this.amount - 1);
                if (amt != 0 && matches(outputSlot)) {
                    outputSlot.setAmount(outputSlot.getAmount() + amt);
                    setAmount(this.amount - amt);
                }
            }
        }
    }

    void updateStatus(Block block) {
        if (this.menu.hasViewer()) {
            if (this.amount == 0) {
                this.menu.replaceExistingItem(STATUS_SLOT, new CustomItem(
                        Material.CYAN_STAINED_GLASS_PANE,
                        "&bStatus",
                        this.unit.emptyString,
                        this.voidExcess ? VOID_EXCESS_TRUE : VOID_EXCESS_FALSE,
                        "&7(Click to toggle)"
                ), false);
            } else {
                this.menu.replaceExistingItem(STATUS_SLOT, new CustomItem(
                        Material.CYAN_STAINED_GLASS_PANE,
                        "&bStatus",
                        "&6Stored: &e" + LorePreset.format(this.amount) + (this.unit.displayMax ? this.unit.maxString + (100 * this.amount) / this.unit.max + "%)" : ""),
                        this.voidExcess ? VOID_EXCESS_TRUE : VOID_EXCESS_FALSE,
                        "&7(Click to toggle)"
                ), false);
            }
        }
        
        if (DISPLAY_SIGNS && (InfinityExpansion.inst().getGlobalTick() & 15) == 0) {
            Block check = block.getRelative(0, 1, 0);
            if (SlimefunTag.SIGNS.isTagged(check.getType())
                    || checkWallSign(check = block.getRelative(1, 0, 0), block)
                    || checkWallSign(check = block.getRelative(-1, 0, 0), block)
                    || checkWallSign(check = block.getRelative(0, 0, 1), block)
                    || checkWallSign(check = block.getRelative(0, 0, -1), block)
            ) {
                Sign sign = (Sign) check.getState();
                sign.setLine(0, ChatColor.GRAY + "--------------");
                sign.setLine(1, this.displayName);
                sign.setLine(2, ChatColor.YELLOW.toString() + this.amount);
                sign.setLine(3, ChatColor.GRAY + "--------------");
                sign.update();
            }
        }
    }

    private static boolean checkWallSign(Block sign, Block block) {
        return SlimefunTag.WALL_SIGNS.isTagged(sign.getType())
                && sign.getRelative(((WallSign) sign.getBlockData()).getFacing().getOppositeFace()).equals(block);
    }
    
    private void interactHandler(Player p, ClickAction action) {
        if (this.amount == 1) {
            if (action.isShiftClicked() && !action.isRightClicked()) {
                depositAll(p);
            } else {
                withdrawLast(p);
            }
        } else if (this.amount != 0) {
            if (action.isRightClicked()) {
                if (action.isShiftClicked()) {
                    withdraw(p, this.amount - 1);
                } else {
                    withdraw(p, Math.min(this.material.getMaxStackSize(), this.amount - 1));
                }
            } else {
                if (action.isShiftClicked()) {
                    depositAll(p);
                } else {
                    withdraw(p, 1);
                }
            }
        }
    }
    
    private void voidExcessHandler() {
        if (this.voidExcess) {
            BlockStorage.addBlockInfo(this.menu.getLocation(), VOID_EXCESS, null);
            LoreUtils.replaceLine(this.menu.getItemInSlot(STATUS_SLOT), VOID_EXCESS_TRUE, VOID_EXCESS_FALSE);
            this.voidExcess = false;
        } else {
            BlockStorage.addBlockInfo(this.menu.getLocation(), VOID_EXCESS, "true");
            LoreUtils.replaceLine(this.menu.getItemInSlot(STATUS_SLOT), VOID_EXCESS_FALSE, VOID_EXCESS_TRUE);
            this.voidExcess = true;
        }
    }
    
    private void setStored(ItemStack input) {
        if (input.hasItemMeta()) {
            this.meta = input.getItemMeta();
            this.displayName = StackUtils.getDisplayName(input, this.meta);
        } else {
            this.meta = null;
            this.displayName = StackUtils.getInternalName(input);
        }
        this.material = input.getType();

        // add the display key to the display input and set amount 1
        ItemMeta meta = input.getItemMeta();
        meta.getPersistentDataContainer().set(DISPLAY_KEY, PersistentDataType.BYTE, (byte) 1);
        input.setItemMeta(meta);
        input.setAmount(1);

        this.menu.replaceExistingItem(DISPLAY_SLOT, input);
    }

    private void setEmpty() {
        this.displayName = EMPTY_DISPLAY_NAME;
        this.meta = null;
        this.material = null;
        this.menu.replaceExistingItem(DISPLAY_SLOT, EMPTY_ITEM);
        setAmount(0);
    }
    
    boolean isEmpty() {
        return this.amount == 0;
    }

    boolean matches(ItemStack item) {
        return item.getType() == this.material
                && item.hasItemMeta() == (this.meta != null)
                && (this.meta == null || this.meta.equals(item.getItemMeta()));
    }

    private ItemStack createItem(int amount) {
        ItemStack item = new ItemStack(this.material, amount);
        if (this.meta != null) {
            item.setItemMeta(this.meta);
        }
        return item;
    }

    private void withdraw(Player p, int withdraw) {
        ItemStack remaining = p.getInventory().addItem(createItem(withdraw)).get(0);
        if (remaining != null) {
            if (remaining.getAmount() != withdraw) {
                setAmount(this.amount - withdraw + remaining.getAmount());
            }
        } else {
            setAmount(this.amount - withdraw);
        }
    }

    private void withdrawLast(Player p) {
        if (p.getInventory().addItem(createItem(1)).get(0) == null) {
            setEmpty();
        }
    }

    private void depositAll(Player p) {
        if (this.amount < this.unit.max) {
            int amount = this.amount;
            for (ItemStack item : p.getInventory().getStorageContents()) {
                if (item != null && matches(item)) {
                    if (item.getAmount() + amount >= this.unit.max) {
                        // last item
                        item.setAmount(item.getAmount() - (this.unit.max - amount));
                        amount = this.unit.max;
                        break;
                    } else {
                        amount += item.getAmount();
                        item.setAmount(0);
                    }
                }
            }
            if (amount != this.amount) {
                setAmount(amount);
            }
        }
    }

}
