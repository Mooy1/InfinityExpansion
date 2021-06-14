package io.github.mooy1.infinityexpansion.items.storage;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.mooy1.infinitylib.items.StackUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

/**
 * Represents a single storage unit with cached data
 *
 * @author Mooy1
 */
final class StorageCache {

    StorageCache(StorageUnit storageUnit, BlockMenu menu) {
        this.storageUnit = storageUnit;
        this.menu = menu;

        // load data
        reloadData();

        if (isEmpty()) {
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
                        // no output to recover
                        menu.replaceExistingItem(DISPLAY_SLOT, EMPTY_ITEM);
                        this.displayName = EMPTY_DISPLAY_NAME;
                        this.amount = 0;
                    }
                } else {
                    // load the item in menu
                    load(display, copy);
                }
            }
        }

        // load status slot
        updateStatus();
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

    private void setStored(ItemStack input) {

        // add the display key to the display input and set amount 1
        ItemMeta meta = input.getItemMeta();
        meta.getPersistentDataContainer().set(DISPLAY_KEY, PersistentDataType.BYTE, (byte) 1);
        input.setItemMeta(meta);
        input.setAmount(1);
    }

}
