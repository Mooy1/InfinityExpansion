package io.github.mooy1.infinityexpansion.implementation.storage;

import io.github.mooy1.infinityexpansion.utils.Util;
import lombok.AllArgsConstructor;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
final class InteractionHandler implements ChestMenu.MenuClickHandler {
    
    private final int max;
    private final BlockMenu menu;
    private final Location location;

    @Override
    public boolean onClick(Player p, int slot, ItemStack item, ClickAction action) {
        StorageCache cache = StorageCache.get(this.location);
        if (cache == null) {
            return false;
        }
        int amount = Util.getIntData(StorageUnit.STORED_AMOUNT, this.location);
        if (amount == 1) {
            if (action.isShiftClicked() && !action.isRightClicked()) {
                depositAll(p, amount, cache);
            } else {
                withdrawLast(p, cache);
            }
        } else {
            if (action.isRightClicked()) {
                if (action.isShiftClicked()) {
                    withdraw(p, amount, amount - 1, cache);
                } else {
                    withdraw(p, amount, Math.min(cache.getStoredMaterial().getMaxStackSize(), amount - 1), cache);
                }
            } else {
                if (action.isShiftClicked()) {
                    depositAll(p, amount, cache);
                } else {
                    withdraw(p, amount, 1, cache);
                }
            }
        }
        return false;
    }

    private void withdraw(Player p, int amount, int withdraw, StorageCache cachedItemMeta) {
        ItemStack remaining = p.getInventory().addItem(cachedItemMeta.createItem(withdraw)).get(0);
        if (remaining != null) {
            withdraw -= remaining.getAmount();
        }
        BlockStorage.addBlockInfo(this.location, StorageUnit.STORED_AMOUNT, String.valueOf(amount - withdraw));
    }

    private void withdrawLast(Player p, StorageCache cachedItemMeta) {
        if (p.getInventory().addItem(cachedItemMeta.createItem(1)).isEmpty()) {
            BlockStorage.addBlockInfo(this.location, StorageUnit.STORED_AMOUNT, String.valueOf(0));
            StorageCache.remove(this.location);
            this.menu.replaceExistingItem(StorageUnit.DISPLAY_SLOT, StorageUnit.EMPTY_ITEM, false);
        }
    }

    private void depositAll(Player p, int amount, StorageCache meta) {
        amount = this.max - amount;
        if (amount != 0) {
            for (ItemStack item : p.getInventory().getStorageContents()) {
                if (item != null && meta.matches(item)) {
                    int add = Math.min(amount, item.getAmount());
                    item.setAmount(item.getAmount() - add);
                    amount -= add;
                    if (amount == 0) {
                        break;
                    }
                }
            }
            BlockStorage.addBlockInfo(this.location, StorageUnit.STORED_AMOUNT, String.valueOf(this.max - amount));
        }
    }
    
}
