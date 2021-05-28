package io.github.mooy1.infinityexpansion.implementation.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.persistence.PersistenceUtils;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.mooy1.infinitylib.slimefun.AbstractContainer;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

/**
 * A block that stored large amounts of 1 item
 *
 * @author Mooy1
 *
 * Thanks to FluffyBear for stuff to learn from
 */
public final class StorageUnit extends AbstractContainer {

    /* Namespaced keys */
    static final NamespacedKey EMPTY_KEY = InfinityExpansion.inst().getKey("empty"); // key for empty item
    static final NamespacedKey DISPLAY_KEY = InfinityExpansion.inst().getKey("display"); // key for display item
    private static final NamespacedKey ITEM_KEY = InfinityExpansion.inst().getKey("item"); // item key for item pdc
    private static final NamespacedKey AMOUNT_KEY = InfinityExpansion.inst().getKey("stored"); // amount key for item pdc

    /* Menu slots */
    static final int INPUT_SLOT = MenuPreset.INPUT;
    static final int DISPLAY_SLOT = MenuPreset.STATUS;
    static final int STATUS_SLOT = DISPLAY_SLOT - 9;
    static final int OUTPUT_SLOT = MenuPreset.OUTPUT;
    static final int INTERACT_SLOT = DISPLAY_SLOT + 9;

    /* Menu items */
    private static final ItemStack INTERACTION_ITEM = new CustomItem(Material.LIME_STAINED_GLASS_PANE,
            "&aQuick Actions",
            "&bLeft Click: &7Withdraw 1 item",
            "&bRight Click: &7Withdraw 1 stack",
            "&bShift Left Click: &7Deposit inventory",
            "&bShift Right Click: &7Withdraw inventory"
    );
    private static final ItemStack LOADING_ITEM = new CustomItem(Material.CYAN_STAINED_GLASS_PANE,
            "&bStatus",
            "&7Loading..."
    );

    /* Instance constants */
    private final Map<Location, StorageCache> caches = new HashMap<>();
    final int max;

    public StorageUnit(SlimefunItemStack item, int max, ItemStack[] recipe) {
        super(Categories.STORAGE, item, StorageForge.TYPE, recipe);
        this.max = max;
    }

    @Override
    protected void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        // TEMP FIX
        if (BlockStorage.getInventory(b) == menu) {
            this.caches.put(b.getLocation(), new StorageCache(this, menu));
        }
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e) {
        Location l = e.getBlock().getLocation();
        StorageCache cache = this.caches.remove(l);
        if (cache != null) {
            cache.destroy(l, e);
        }
        BlockStorage.getInventory(l).dropItems(l, INPUT_SLOT, OUTPUT_SLOT);
    }

    @Override
    protected void onPlace(@Nonnull BlockPlaceEvent e) {
        Pair<ItemStack, Integer> data = loadFromStack(e.getItemInHand());
        if (data != null) {
            InfinityExpansion.inst().runSync(() -> {
                StorageCache cache = this.caches.get(e.getBlockPlaced().getLocation());
                cache.load(data.getFirstValue(), data.getFirstValue().getItemMeta());
                cache.setAmount(data.getSecondValue());
            });
        }
    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        MenuPreset.setupBasicMenu(blockMenuPreset);
        blockMenuPreset.addMenuClickHandler(DISPLAY_SLOT, ChestMenuUtils.getEmptyClickHandler());
        blockMenuPreset.addItem(INTERACT_SLOT, INTERACTION_ITEM);
        blockMenuPreset.addItem(STATUS_SLOT, LOADING_ITEM);
    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu dirtyChestMenu, @Nonnull ItemTransportFlow flow, @Nonnull ItemStack itemStack) {
        StorageCache cache = this.caches.get(((BlockMenu) dirtyChestMenu).getLocation());
        if (cache != null) {
            if (flow == ItemTransportFlow.WITHDRAW) {
                return new int[] {OUTPUT_SLOT};
            } else if (flow == ItemTransportFlow.INSERT && (cache.isEmpty() || cache.matches(itemStack))) {
                cache.input();
                return new int[] {INPUT_SLOT};
            }
        }
        return new int[0];
    }

    @Override
    protected void tick(@Nonnull Block b) {
        StorageCache cache = StorageUnit.this.caches.get(b.getLocation());
        if (cache != null) {
            cache.tick(b);
        }
    }

    public void reloadCache(Block b) {
        this.caches.get(b.getLocation()).reloadData();
    }

    static void transferToStack(@Nonnull ItemStack source, @Nonnull ItemStack target) {
        Pair<ItemStack, Integer> data = loadFromStack(source);
        if (data != null) {
            target.setItemMeta(saveToStack(target.getItemMeta(), data.getFirstValue(),
                    StackUtils.getDisplayName(data.getFirstValue()), data.getSecondValue()));
        }
    }

    static ItemMeta saveToStack(ItemMeta meta, ItemStack displayItem, String displayName, int amount) {
        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            lore.add(ChatColor.GOLD + "Stored: " + displayName + ChatColor.YELLOW + " x " + amount);
            meta.setLore(lore);
        }
        meta.getPersistentDataContainer().set(ITEM_KEY, PersistenceUtils.ITEM_STACK, displayItem);
        meta.getPersistentDataContainer().set(AMOUNT_KEY, PersistentDataType.INTEGER, amount);
        return meta;
    }

    private static Pair<ItemStack, Integer> loadFromStack(ItemStack source) {
        if (source.hasItemMeta()) {
            PersistentDataContainer con = source.getItemMeta().getPersistentDataContainer();
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
