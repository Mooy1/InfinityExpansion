package io.github.mooy1.infinityexpansion.implementation.storage;

import io.github.mooy1.infinitylib.items.StackUtils;
import lombok.Getter;
import lombok.Setter;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

final class StorageCache {

    private static final Map<Location, StorageCache> CACHES = new HashMap<>();
    
    static StorageCache get(Location l) {
        return CACHES.get(l);
    }
    
    static void remove(Location l) {
        CACHES.remove(l);
    }
    
    @Getter
    private final BlockMenu menu;
    @Setter
    @Getter
    private boolean voidExcess;
    @Getter
    private int storedAmount;
    @Getter
    private Material storedMaterial;
    @Getter
    private String storedDisplayName;
    @Getter
    private ItemMeta storedMeta;

    // load from input
    StorageCache(ItemStack item) {
        if (item.hasItemMeta()) {
            this.meta = item.getItemMeta();
            this.displayName = StackUtils.getDisplayName(item, this.meta);
        } else {
            this.meta = null;
            this.displayName = StackUtils.getInternalName(item);
        }
        this.type = item.getType();

        // add the display key to the display item and set amount 1
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(StorageUnit.DISPLAY_KEY, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        item.setAmount(1);
    }

    // load from stored
    StorageCache(ItemStack item, ItemMeta meta) {
        // remove the display key from this meta
        meta.getPersistentDataContainer().remove(StorageUnit.DISPLAY_KEY);

        // check if it had meta besides the display key
        if (meta.equals(Bukkit.getItemFactory().getItemMeta(item.getType()))) {
            this.meta = null;
            this.displayName = StackUtils.getInternalName(item);
        } else {
            this.meta = meta;
            this.displayName = StackUtils.getDisplayName(item, meta);
        }
        this.type = item.getType();
    }
    
    void setAmount(int amount) {
        if (amount != this.storedAmount) {
            BlockStorage.addBlockInfo(this.menu.getLocation(), StorageUnit.STORED_AMOUNT, String.valueOf(amount));
        }
        this.storedAmount = amount;
    }
    
    boolean isEmpty() {
        return this.storedAmount == 0;
    }
    
    void setEmpty() {
        this.storedAmount = 0;
        BlockStorage.addBlockInfo(this.menu.getLocation(), StorageUnit.STORED_AMOUNT, "0");
        
    }

    boolean matches(ItemStack item) {
        return item.getType() == this.storedMaterial
                && item.hasItemMeta() == (this.storedMeta != null)
                && (this.storedMeta == null || this.storedMeta.equals(item.getItemMeta()));
    }

    ItemStack createItem(int amount) {
        ItemStack item = new ItemStack(this.storedMaterial, amount);
        if (this.storedMeta != null) {
            item.setItemMeta(this.storedMeta);
        }
        return item;
    }
    
}
