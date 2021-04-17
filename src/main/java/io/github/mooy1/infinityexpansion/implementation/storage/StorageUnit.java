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
import org.bukkit.persistence.PersistentDataType;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinityexpansion.implementation.materials.Items;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.persistence.PersistenceUtils;
import io.github.mooy1.infinitylib.slimefun.abstracts.AbstractContainer;
import io.github.mooy1.infinitylib.slimefun.presets.LorePreset;
import io.github.mooy1.infinitylib.slimefun.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
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

    public static void setup(InfinityExpansion plugin) {
        new StorageUnit(BASIC, BASIC_STORAGE,true, new ItemStack[] {
                new ItemStack(Material.OAK_LOG), Items.MAGSTEEL, new ItemStack(Material.OAK_LOG),
                new ItemStack(Material.OAK_LOG), new ItemStack(Material.BARREL), new ItemStack(Material.OAK_LOG),
                new ItemStack(Material.OAK_LOG), Items.MAGSTEEL, new ItemStack(Material.OAK_LOG)
        }).register(plugin);
        new StorageUnit(ADVANCED, ADVANCED_STORAGE,true, new ItemStack[] {
                Items.MAGSTEEL, Items.MACHINE_CIRCUIT, Items.MAGSTEEL,
                Items.MAGSTEEL, StorageUnit.BASIC, Items.MAGSTEEL,
                Items.MAGSTEEL, Items.MACHINE_CIRCUIT, Items.MAGSTEEL
        }).register(plugin);
        new StorageUnit(REINFORCED, REINFORCED_STORAGE,true, new ItemStack[] {
                Items.MAGSTEEL_PLATE, Items.MACHINE_CIRCUIT, Items.MAGSTEEL_PLATE,
                Items.MAGSTEEL_PLATE, StorageUnit.ADVANCED, Items.MAGSTEEL_PLATE,
                Items.MAGSTEEL_PLATE, Items.MACHINE_PLATE, Items.MAGSTEEL_PLATE
        }).register(plugin);
        new StorageUnit(VOID, VOID_STORAGE, true, new ItemStack[] {
                Items.VOID_INGOT, Items.MACHINE_PLATE, Items.VOID_INGOT,
                Items.MAGNONIUM, StorageUnit.REINFORCED, Items.MAGNONIUM,
                Items.VOID_INGOT, Items.MACHINE_CORE, Items.VOID_INGOT
        }).register(plugin);
        new StorageUnit(INFINITY, INFINITY_STORAGE, false, new ItemStack[] {
                Items.INFINITY, Items.VOID_INGOT, Items.INFINITY,
                Items.INFINITY, StorageUnit.VOID, Items.INFINITY,
                Items.INFINITY, Items.VOID_INGOT, Items.INFINITY
        }).register(plugin);
    }

    /* Storage amounts for each tier */
    private static final int BASIC_STORAGE = 6400;
    private static final int ADVANCED_STORAGE = 25600;
    private static final int REINFORCED_STORAGE = 102400;
    private static final int VOID_STORAGE = 409600;
    static final int INFINITY_STORAGE = 1_600_000_000;

    /* Items */
    public static final SlimefunItemStack BASIC = new SlimefunItemStack(
            "BASIC_STORAGE",
            Material.OAK_WOOD,
            "&9Basic &8Storage Unit",
            "&6Capacity: &e" + LorePreset.format(BASIC_STORAGE) + " &eitems"
    );
    public static final SlimefunItemStack ADVANCED = new SlimefunItemStack(
            "ADVANCED_STORAGE",
            Material.DARK_OAK_WOOD,
            "&cAdvanced &8Storage Unit",
            "&6Capacity: &e" + LorePreset.format(ADVANCED_STORAGE) + " &eitems"
    );
    public static final SlimefunItemStack REINFORCED = new SlimefunItemStack(
            "REINFORCED_STORAGE",
            Material.ACACIA_WOOD,
            "&fReinforced &8Storage Unit",
            "&6Capacity: &e" + LorePreset.format(REINFORCED_STORAGE) + " &eitems"
    );
    public static final SlimefunItemStack VOID = new SlimefunItemStack(
            "VOID_STORAGE",
            Material.CRIMSON_HYPHAE,
            "&8Void &8Storage Unit",
            "&6Capacity: &e" + LorePreset.format(VOID_STORAGE) + " &eitems"
    );
    public static final SlimefunItemStack INFINITY = new SlimefunItemStack(
            "INFINITY_STORAGE",
            Material.WARPED_HYPHAE,
            "&bInfinity &8Storage Unit",
            "&6Capacity: &eInfinite items"
    );

    /* Namespaced keys */
    static final NamespacedKey EMPTY_KEY = InfinityExpansion.inst().getKey("empty"); // key for empty item
    static final NamespacedKey DISPLAY_KEY = InfinityExpansion.inst().getKey("display"); // key for display item
    private static final NamespacedKey OLD_ITEM_KEY = InfinityExpansion.inst().getKey("stored_item"); // old item key in pdc
    private static final NamespacedKey ITEM_KEY = InfinityExpansion.inst().getKey("item"); // item key for item pdc
    private static final NamespacedKey AMOUNT_KEY = InfinityExpansion.inst().getKey("stored"); // amount key for item pdc

    /* Menu slots */
    static final int INPUT_SLOT = MenuPreset.slot1;
    static final int DISPLAY_SLOT = MenuPreset.slot2;
    static final int STATUS_SLOT = DISPLAY_SLOT - 9;
    static final int OUTPUT_SLOT = MenuPreset.slot3;
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
    final boolean displayMax;
    final String maxString;
    final String emptyString;
    final int max;

    private StorageUnit(SlimefunItemStack item, int max, boolean displayMax, ItemStack[] recipe) {
        super(Categories.STORAGE, item, StorageForge.TYPE, recipe);
        this.max = max;
        this.displayMax = displayMax;
        this.maxString = " / " + LorePreset.format(max) + " &7(";
        this.emptyString = "&6Stored: &e0" + (displayMax ? " / " + LorePreset.format(max) + " &7(0%)" : "");

        addItemHandler(new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return true;
            }

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                StorageCache cache = StorageUnit.this.caches.get(b.getLocation());
                if (cache != null) {
                    cache.input();
                    cache.output(true);
                    cache.updateStatus(b);
                }
            }
        });
    }

    @Override
    protected void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        this.caches.put(b.getLocation(), new StorageCache(StorageUnit.this, b, menu));
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu menu, @Nonnull Location l) {
        this.caches.remove(l).destroy(e);
    }

    @Override
    protected void onPlace(@Nonnull BlockPlaceEvent e, @Nonnull Block b) {
        Pair<ItemStack, Integer> data = loadFromStack(e.getItemInHand().getItemMeta());
        if (data != null) {
            InfinityExpansion.inst().runSync(() -> {
                StorageCache cache = this.caches.get(b.getLocation());
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
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu dirtyChestMenu, @Nonnull ItemTransportFlow flow, ItemStack itemStack) {
        StorageCache cache = this.caches.get(((BlockMenu) dirtyChestMenu).getLocation());
        if (cache != null) {
            if (flow == ItemTransportFlow.INSERT) {
                // check if input can be stored
                if (cache.isEmpty() || cache.matches(itemStack)) {
                    ItemStack input = dirtyChestMenu.getItemInSlot(INPUT_SLOT);
                    if (input != null && input.getAmount() + itemStack.getAmount() > itemStack.getMaxStackSize()) {
                        // clear the input spot to make room
                        cache.input();
                    }
                    return new int[] {INPUT_SLOT};
                }
            } else if (flow == ItemTransportFlow.WITHDRAW) {
                cache.output(false);
                return new int[] {OUTPUT_SLOT};
            }
        }
        return new int[0];
    }

    static void transferToStack(@Nonnull ItemStack source, @Nonnull ItemStack target) {
        Pair<ItemStack, Integer> data = loadFromStack(source.getItemMeta());
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

    private static Pair<ItemStack, Integer> loadFromStack(ItemMeta meta) {
        // get amount
        Integer amount = meta.getPersistentDataContainer().get(AMOUNT_KEY, PersistentDataType.INTEGER);
        if (amount != null) {

            // check for old id
            String oldID = meta.getPersistentDataContainer().get(OLD_ITEM_KEY, PersistentDataType.STRING);
            if (oldID != null) {
                ItemStack item = StackUtils.getItemByIDorType(oldID);
                if (item != null) {
                    // add the display key to it
                    ItemMeta update = item.getItemMeta();
                    update.getPersistentDataContainer().set(DISPLAY_KEY, PersistentDataType.BYTE, (byte) 1);
                    item.setItemMeta(update);
                    return new Pair<>(item, amount);
                }
            }

            // get item
            ItemStack item = meta.getPersistentDataContainer().get(ITEM_KEY, PersistenceUtils.ITEM_STACK);
            if (item != null) {
                return new Pair<>(item, amount);
            }
        }
        return null;
    }

}
