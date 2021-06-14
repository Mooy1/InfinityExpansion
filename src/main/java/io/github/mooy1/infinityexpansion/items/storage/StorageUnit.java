package io.github.mooy1.infinityexpansion.items.storage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.persistence.PersistenceUtils;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.mooy1.infinitylib.slimefun.AbstractTickingContainer;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

/**
 * A block that stored large amounts of 1 item
 *
 * @author Mooy1
 *
 * Thanks to FluffyBear for stuff to learn from
 */
public final class StorageUnit extends AbstractTickingContainer {

    /* Namespaced keys */
    private static final NamespacedKey EMPTY_KEY = InfinityExpansion.inst().getKey("empty");
    private static final NamespacedKey DISPLAY_KEY = InfinityExpansion.inst().getKey("display");
    private static final NamespacedKey ITEM_KEY = InfinityExpansion.inst().getKey("item");
    private static final NamespacedKey AMOUNT_KEY = InfinityExpansion.inst().getKey("stored");

    /* Menu items */
    private static final ItemStack INTERACTION_ITEM = new CustomItem(Material.LIME_STAINED_GLASS_PANE,
            "&aQuick Actions",
            "&bLeft Click: &7Withdraw 1 item",
            "&bRight Click: &7Withdraw 1 stack",
            "&bShift Left Click: &7Deposit inventory",
            "&bShift Right Click: &7Withdraw inventory"
    );
    private static final ItemStack EMPTY_ITEM = new CustomItem(Material.BARRIER, meta -> {
        meta.setDisplayName(ChatColor.WHITE + "Empty");
        meta.getPersistentDataContainer().set(EMPTY_KEY, PersistentDataType.BYTE, (byte) 1);
    });
    private static final ItemStack LOADING_ITEM = new CustomItem(Material.CYAN_STAINED_GLASS_PANE,
            "&bStatus",
            "&7Loading..."
    );

    /* Menu strings */
    private static final String EMPTY_DISPLAY_NAME = ChatColor.WHITE + "Empty";
    private static final String VOID_EXCESS_TRUE = ChatColors.color("&7Void Excess:&e true");
    private static final String VOID_EXCESS_FALSE = ChatColors.color("&7Void Excess:&e false");

    /* Menu slots */
    private static final int INPUT_SLOT = MenuPreset.INPUT;
    private static final int STORED_SLOT = MenuPreset.STATUS;
    private static final int STATUS_SLOT = STORED_SLOT - 9;
    private static final int INTERACT_SLOT = STORED_SLOT + 9;
    private static final int OUTPUT_SLOT = MenuPreset.OUTPUT;

    /* BlockStorage keys */
    private static final String STORED_AMOUNT = "stored";
    private static final String VOID_EXCESS = "void_excess";

    /* Instance constants */
    private final int max;

    public StorageUnit(SlimefunItemStack item, int max, ItemStack[] recipe) {
        super(Categories.STORAGE, item, StorageForge.TYPE, recipe);
        this.max = max;
    }

    @Override
    protected void tick(@Nonnull BlockMenu menu, @Nonnull Block block) {
        Location l = menu.getLocation();
        int amount = Util.getIntData(STORED_AMOUNT, l);
        ItemStack stored = menu.getItemInSlot(STORED_SLOT);

        // input
        amount = input(menu, l, amount);

        // output
        if (amount != 0) {
            ItemStack output = menu.getItemInSlot(OUTPUT_SLOT);
            if (output == null) {
                if (amount == 1) {
                    menu.replaceExistingItem(STORED_SLOT, null);
                    menu.replaceExistingItem(OUTPUT_SLOT, stored);
                    amount = 0;
                } else {
                    int amt = Math.min(stored.getMaxStackSize(), amount - 1);
                    menu.replaceExistingItem(OUTPUT_SLOT, new CustomItem(stored, amt));
                    amount -= amt;
                }
            } else if (amount > 1) {
                int amt = Math.min(stored.getMaxStackSize() - output.getAmount(), amount - 1);
                if (amt != 0 && matches(output, stored)) {
                    output.setAmount(output.getAmount() + amt);
                    amount -= amt;
                }
            }
        }

        // store amount
        BlockStorage.addBlockInfo(l, STORED_AMOUNT, String.valueOf(amount));

        // status
        if (menu.hasViewer()) {
            updateStatus(menu, amount, );
        }

        // sings
        if ((InfinityExpansion.inst().getGlobalTick() & 15) == 0) {
            Block check = block.getRelative(0, 1, 0);
            if (SlimefunTag.SIGNS.isTagged(check.getType())
                    || checkWallSign(check = block.getRelative(1, 0, 0), block)
                    || checkWallSign(check = block.getRelative(-1, 0, 0), block)
                    || checkWallSign(check = block.getRelative(0, 0, 1), block)
                    || checkWallSign(check = block.getRelative(0, 0, -1), block)
            ) {
                Sign sign = (Sign) check.getState();
                sign.setLine(0, ChatColor.GRAY + "--------------");
                sign.setLine(1, StackUtils.getDisplayName(stored));
                sign.setLine(2, ChatColor.YELLOW.toString() + amount);
                sign.setLine(3, ChatColor.GRAY + "--------------");
                sign.update();
            }
        }
    }

    private void updateStatus(BlockMenu menu, Location l, int amount) {
        menu.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.CYAN_STAINED_GLASS_PANE, meta -> {
            meta.setDisplayName(ChatColor.AQUA + "Status");
            List<String> lore = new ArrayList<>();
            if (amount == 0) {
                lore.add(ChatColors.color("&6Stored: &e0 / " + LorePreset.format(this.max) + " &7(0%)"));
            } else {
                lore.add(ChatColors.color("&6Stored: &e" + LorePreset.format(amount)
                        + " / " + LorePreset.format(this.max)
                        + " &7(" + LorePreset.format((double) amount / this.max) + "%)"
                ));
            }
            lore.add(BlockStorage.getLocationInfo(l, VOID_EXCESS) != null ? VOID_EXCESS_TRUE : VOID_EXCESS_FALSE);
            lore.add(ChatColor.GRAY + "(Click to toggle)");
            meta.setLore(lore);
        }));
    }

    private static boolean checkWallSign(Block sign, Block block) {
        return SlimefunTag.WALL_SIGNS.isTagged(sign.getType())
                && sign.getRelative(((WallSign) sign.getBlockData()).getFacing().getOppositeFace()).equals(block);
    }

    @Override
    protected void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        // void excess handler
        menu.addMenuClickHandler(STATUS_SLOT, (p, slot, item, action) -> {
            boolean newVoidExcess = BlockStorage.getLocationInfo(menu.getLocation(), VOID_EXCESS) == null;
            BlockStorage.addBlockInfo(menu.getLocation(), VOID_EXCESS, newVoidExcess ? "true" : null);
            ItemMeta meta = StackUtils.getLiveMeta(item);
            List<String> lore = meta.getLore();
            lore.set(1, newVoidExcess ? VOID_EXCESS_TRUE : VOID_EXCESS_FALSE);
            meta.setLore(lore);
            return false;
        });

        // interact handler
        menu.addMenuClickHandler(INTERACT_SLOT, (p, slot, item, action) -> {
            Location l = menu.getLocation();
            int amount = Util.getIntData(STORED_AMOUNT, l);
            ItemStack stored = menu.getItemInSlot(STORED_SLOT);
            if (amount == 1) {
                if (action.isShiftClicked() && !action.isRightClicked()) {
                    depositAll(p, stored, amount, l);
                } else {
                    withdrawLast(p, menu);
                }
            } else if (amount > 1) {
                if (action.isRightClicked()) {
                    if (action.isShiftClicked()) {
                        withdraw(p, l, stored, amount, amount - 1);
                    } else {
                        withdraw(p, l, stored, amount, Math.min(stored.getMaxStackSize(), amount - 1));
                    }
                } else {
                    if (action.isShiftClicked()) {
                        depositAll(p, stored, amount, l);
                    } else {
                        withdraw(p, l, stored, amount, 1);
                    }
                }
            }
            return false;
        });
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu menu, @Nonnull Location l) {
        int amount = Util.getIntData(STORED_AMOUNT, l);

        if (amount != 0) {

            ItemStack stored = menu.getItemInSlot(STORED_SLOT);
            ItemStack output = menu.getItemInSlot(OUTPUT_SLOT);
            if (output != null && matches(output, stored)) {
                int add = Math.min(this.max - amount, output.getAmount());
                if (add != 0) {
                    amount += add;
                    output.setAmount(output.getAmount() - add);
                }
            }

            ItemStack drop = getItem().clone();
            drop.setItemMeta(saveToStack(drop.getItemMeta(), stored, amount));
            e.getPlayer().sendMessage(ChatColor.GREEN + "Stored items transferred to dropped item");
            e.getBlock().getWorld().dropItemNaturally(l, drop);
        }

        menu.dropItems(l, INPUT_SLOT, OUTPUT_SLOT);
    }

    @Override
    protected void onPlace(@Nonnull BlockPlaceEvent e, @Nonnull Block b) {
        Pair<ItemStack, Integer> data = loadFromStack(e.getItemInHand());
        if (data != null) {
            InfinityExpansion.inst().runSync(() -> {
                Location l = b.getLocation();
                BlockStorage.getInventory(l).replaceExistingItem(STORED_SLOT, data.getFirstValue());
                BlockStorage.addBlockInfo(l, STORED_AMOUNT, String.valueOf(data.getSecondValue()));
            });
        }
    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        blockMenuPreset.drawBackground(MenuPreset.INPUT_ITEM, MenuPreset.INPUT_BORDER);
        blockMenuPreset.drawBackground(MenuPreset.STATUS_ITEM, MenuPreset.STATUS_BORDER);
        blockMenuPreset.drawBackground(MenuPreset.OUTPUT_ITEM, MenuPreset.OUTPUT_BORDER);
        blockMenuPreset.addMenuClickHandler(STORED_SLOT, ChestMenuUtils.getEmptyClickHandler());
        blockMenuPreset.addItem(INTERACT_SLOT, INTERACTION_ITEM);
        blockMenuPreset.addItem(STATUS_SLOT, LOADING_ITEM);
    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu dirtyChestMenu, @Nonnull ItemTransportFlow flow, @Nonnull ItemStack itemStack) {
        if (flow == ItemTransportFlow.WITHDRAW) {
            return new int[] {
                    OUTPUT_SLOT
            };
        }
        if (flow == ItemTransportFlow.INSERT) {
            Location l = ((BlockMenu) dirtyChestMenu).getLocation();
            int amount = Util.getIntData(STORED_AMOUNT, l);
            if (amount == 0 || matches(itemStack, dirtyChestMenu.getItemInSlot(STORED_SLOT))) {
                BlockStorage.addBlockInfo(l, STORED_AMOUNT, String.valueOf(input(dirtyChestMenu, l, amount)));
                return new int[] {
                        INPUT_SLOT
                };
            }
        }
        return new int[0];
    }

    private int input(DirtyChestMenu menu, Location l, int amount) {
        ItemStack input = menu.getItemInSlot(INPUT_SLOT);

        if (input == null) {
            return amount;
        }
        if (amount == 0) {
            // set the stored item to input
            amount = input.getAmount();
            setStored(input);
            menu.replaceExistingItem(INPUT_SLOT, null);
        } else if (matches(input, menu.getItemInSlot(STORED_SLOT))) {
            if (BlockStorage.getLocationInfo(l, VOID_EXCESS) != null) {
                // input and void excess
                if (amount < this.max) {
                    amount = Math.min(amount + input.getAmount(), this.max);
                }
                input.setAmount(0);
            } else if (amount < this.max) {
                // input as much as possible
                if (input.getAmount() + amount >= this.max) {
                    // last item
                    input.setAmount(input.getAmount() - (this.max - amount));
                    amount = this.max;
                } else {
                    amount += input.getAmount();
                    input.setAmount(0);
                }
            }
        }

        return amount;
    }

    private boolean matches(ItemStack item, ItemStack stored) {
        return item.getType() == stored.getType() && item.hasItemMeta() == stored.hasItemMeta()
                && (!item.hasItemMeta() || StackUtils.getLiveMeta(item).equals(StackUtils.getLiveMeta(stored)));
    }

    private void withdraw(Player p, Location l, ItemStack stored, int amount, int withdraw) {
        int maxStackSize = stored.getType().getMaxStackSize();

        if (maxStackSize == 64) {
            ItemStack remaining = p.getInventory().addItem(new CustomItem(stored, withdraw)).get(0);
            if (remaining != null) {
                if (remaining.getAmount() != withdraw) {
                    amount += remaining.getAmount() - withdraw;
                }
            } else {
                amount -= withdraw;
            }
        } else {
            Inventory inv = p.getInventory();
            int toWithdraw = withdraw;
            do {
                int amt = Math.min(maxStackSize, toWithdraw);
                ItemStack remaining = inv.addItem(new CustomItem(stored, amount)).get(0);
                if (remaining != null) {
                    toWithdraw -= amt - remaining.getAmount();
                    break;
                } else {
                    toWithdraw -= amt;
                }
            } while (toWithdraw > 0);
            if (toWithdraw != withdraw) {
                amount += toWithdraw - withdraw;
            }
        }

        BlockStorage.addBlockInfo(l, STORED_AMOUNT, String.valueOf(amount));
    }

    private void withdrawLast(Player p, BlockMenu menu) {
        if (p.getInventory().addItem(menu.getItemInSlot(STORED_SLOT).clone()).get(0) == null) {
            BlockStorage.addBlockInfo(menu.getLocation(), STORED_AMOUNT, "0");
            menu.replaceExistingItem(STATUS_SLOT, EMPTY_ITEM);
        }
    }

    private void depositAll(Player p, ItemStack stored, int amount, Location l) {
        if (amount >= this.max) {
            return;
        }
        for (ItemStack item : p.getInventory().getStorageContents()) {
            if (item != null && matches(item, stored)) {
                if (item.getAmount() + amount >= this.max) {
                    // last item
                    item.setAmount(item.getAmount() - (this.max - amount));
                    amount = this.max;
                    break;
                } else {
                    amount += item.getAmount();
                    item.setAmount(0);
                }
            }
        }
        BlockStorage.addBlockInfo(l, STORED_AMOUNT, String.valueOf(amount));
    }

    static void transferToStack(@Nonnull ItemStack source, @Nonnull ItemStack target) {
        Pair<ItemStack, Integer> data = loadFromStack(source);
        if (data != null) {
            target.setItemMeta(saveToStack(target.getItemMeta(), data.getFirstValue(), data.getSecondValue()));
        }
    }

    @Nonnull
    private static ItemMeta saveToStack(ItemMeta meta, ItemStack stored, int amount) {
        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            lore.add(ChatColor.GOLD + "Stored: " + StackUtils.getDisplayName(stored) + ChatColor.YELLOW + " x " + amount);
            meta.setLore(lore);
        }
        meta.getPersistentDataContainer().set(ITEM_KEY, PersistenceUtils.ITEM_STACK, stored);
        meta.getPersistentDataContainer().set(AMOUNT_KEY, PersistentDataType.INTEGER, amount);
        return meta;
    }

    @Nullable
    private static Pair<ItemStack, Integer> loadFromStack(ItemStack source) {
        if (source.hasItemMeta()) {
            PersistentDataContainer con = StackUtils.getLivePDC(source);
            Integer amount = con.get(AMOUNT_KEY, PersistentDataType.INTEGER);
            if (amount != null) {
                ItemStack item = con.get(ITEM_KEY, PersistenceUtils.ITEM_STACK);
                if (item != null) {
                    return new Pair<>(item, amount);
                }
            }
        }
        return null;
    }

}
