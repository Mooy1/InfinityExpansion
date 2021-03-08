package io.github.mooy1.infinityexpansion.implementation.blocks;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.materials.Items;
import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.ConfigUtils;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.abstracts.AbstractTicker;
import io.github.mooy1.infinitylib.items.PersistentItemStack;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.player.MessageUtils;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import lombok.AllArgsConstructor;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Bukkit;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Basically just barrels
 *
 * @author Mooy1
 *
 * Thanks to FluffyBear for stuff to learn from
 */
public class StorageUnit extends AbstractTicker {

    private static final int BASIC_STORAGE = 6400;
    private static final int ADVANCED_STORAGE = 25600;
    private static final int REINFORCED_STORAGE = 102400;
    private static final int VOID_STORAGE = 409600;
    private static final int INFINITY_STORAGE = 1_600_000_000;

    public static final SlimefunItemStack BASIC = new SlimefunItemStack(
            "BASIC_STORAGE",
            Material.OAK_WOOD,
            "&9Basic &8Storage Unit",
            LorePreset.storesItem(StorageUnit.BASIC_STORAGE)
    );
    public static final SlimefunItemStack ADVANCED = new SlimefunItemStack(
            "ADVANCED_STORAGE",
            Material.DARK_OAK_WOOD,
            "&cAdvanced &8Storage Unit",
            LorePreset.storesItem(StorageUnit.ADVANCED_STORAGE)
    );
    public static final SlimefunItemStack REINFORCED = new SlimefunItemStack(
            "REINFORCED_STORAGE",
            Material.ACACIA_WOOD,
            "&fReinforced &8Storage Unit",
            LorePreset.storesItem(StorageUnit.REINFORCED_STORAGE)
    );
    public static final SlimefunItemStack VOID = new SlimefunItemStack(
            "VOID_STORAGE",
            Material.CRIMSON_HYPHAE,
            "&8Void &8Storage Unit",
            LorePreset.storesItem(StorageUnit.VOID_STORAGE)
    );
    public static final SlimefunItemStack INFINITY = new SlimefunItemStack(
            "INFINITY_STORAGE",
            Material.WARPED_HYPHAE,
            "&bInfinity &8Storage Unit",
            "&6Capacity: &eInfinite items"
    );

    public static void setup(InfinityExpansion plugin) {
        new StorageUnit(BASIC, BASIC_STORAGE, new ItemStack[] {
                new ItemStack(Material.OAK_LOG), Items.MAGSTEEL, new ItemStack(Material.OAK_LOG),
                new ItemStack(Material.OAK_LOG), new ItemStack(Material.BARREL), new ItemStack(Material.OAK_LOG),
                new ItemStack(Material.OAK_LOG), Items.MAGSTEEL, new ItemStack(Material.OAK_LOG)
        }).register(plugin);
        new StorageUnit(ADVANCED, ADVANCED_STORAGE, new ItemStack[] {
                Items.MAGSTEEL, Items.MACHINE_CIRCUIT, Items.MAGSTEEL,
                Items.MAGSTEEL, StorageUnit.BASIC, Items.MAGSTEEL,
                Items.MAGSTEEL, Items.MACHINE_CIRCUIT, Items.MAGSTEEL
        }).register(plugin);
        new StorageUnit(REINFORCED, REINFORCED_STORAGE, new ItemStack[] {
                Items.MAGSTEEL_PLATE, Items.MACHINE_CIRCUIT, Items.MAGSTEEL_PLATE,
                Items.MAGSTEEL_PLATE, StorageUnit.ADVANCED, Items.MAGSTEEL_PLATE,
                Items.MAGSTEEL_PLATE, Items.MACHINE_PLATE, Items.MAGSTEEL_PLATE
        }).register(plugin);
        new StorageUnit(VOID, VOID_STORAGE, new ItemStack[] {
                Items.VOID_INGOT, Items.MACHINE_PLATE, Items.VOID_INGOT,
                Items.MAGNONIUM, StorageUnit.REINFORCED, Items.MAGNONIUM,
                Items.VOID_INGOT, Items.MACHINE_CORE, Items.VOID_INGOT
        }).register(plugin);
        new StorageUnit(INFINITY, INFINITY_STORAGE, new ItemStack[] {
                Items.INFINITY, Items.VOID_INGOT, Items.INFINITY,
                Items.INFINITY, StorageUnit.VOID, Items.INFINITY,
                Items.INFINITY, Items.VOID_INGOT, Items.INFINITY
        }) {
            @Override
            protected String displayStoredInfo(int amount) {
                return "";
            }
        }.register(plugin);
    }

    private static final boolean DISPLAY_SIGNS = ConfigUtils.getBoolean("storage-unit-options.display-signs", true);

    private static final String OLD_STORED_ITEM = "storeditem"; // old item key in block data
    private static final String STORED_AMOUNT = "stored"; // amount key in block data

    private static final NamespacedKey OLD_ITEM_KEY = PluginUtils.getKey("stored_item"); // old item key in pdc
    private static final NamespacedKey ITEM_KEY = PluginUtils.getKey("item"); // item key for item pdc
    private static final NamespacedKey AMOUNT_KEY = PluginUtils.getKey("stored"); // amount key for item pdc
    private static final NamespacedKey EMPTY_KEY = PluginUtils.getKey("empty"); // key for empty item
    private static final NamespacedKey DISPLAY_KEY = PluginUtils.getKey("display"); // key for display item

    private static final ItemStack EMPTY_ITEM = new CustomItem(Material.BARRIER, meta -> {
        meta.setDisplayName(ChatColor.WHITE + "Empty");
        meta.getPersistentDataContainer().set(EMPTY_KEY, PersistentDataType.BYTE, (byte) 1);
    });
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

    private static final int INPUT_SLOT = MenuPreset.slot1;
    private static final int DISPLAY_SLOT = MenuPreset.slot2;
    private static final int STATUS_SLOT = DISPLAY_SLOT - 9;
    private static final int INTERACT_SLOT = DISPLAY_SLOT + 9;
    private static final int OUTPUT_SLOT = MenuPreset.slot3;

    private static final Map<Location, CachedItemMeta> cachedMetas = new HashMap<>();

    private final int max;

    private StorageUnit(SlimefunItemStack item, int max, ItemStack[] recipe) {
        super(Categories.STORAGE_TRANSPORT, item, StorageForge.TYPE, recipe);
        this.max = max;
    }

    @Override
    public void setupMenu(@Nonnull BlockMenuPreset preset) {
        MenuPreset.setupBasicMenu(preset);
        preset.addMenuClickHandler(DISPLAY_SLOT, ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(INTERACT_SLOT, INTERACTION_ITEM);
        preset.addItem(STATUS_SLOT, LOADING_ITEM);
    }

    @Override
    public void tick(@Nonnull BlockMenu menu, @Nonnull Block block, @Nonnull Config config) {
        int amount = Util.getIntData(STORED_AMOUNT, block.getLocation());
        @Nullable
        CachedItemMeta cachedItemMeta = cachedMetas.get(block.getLocation());

        // input
        ItemStack input = menu.getItemInSlot(INPUT_SLOT);
        if (input != null) {
            if (amount <= 0 || cachedItemMeta == null) {
                // set stored item to input
                amount = input.getAmount();
                cachedMetas.put(block.getLocation(), cachedItemMeta = new CachedItemMeta(input));
                menu.replaceExistingItem(INPUT_SLOT, null);
                menu.replaceExistingItem(DISPLAY_SLOT, input);
            } else {
                // try to add input to storage
                int max = this.max - amount;
                if (max > 0 && cachedItemMeta.matches(input)) {
                    int add = input.getAmount();
                    int dif = add - max;
                    if (dif < 0) {
                        amount += add;
                        input.setAmount(0);
                    } else {
                        amount += max;
                        input.setAmount(dif);
                    }
                }
            }
        }

        // output
        if (amount > 0 && cachedItemMeta != null) {
            int remove = Math.min(cachedItemMeta.type.getMaxStackSize(), amount - 1);
            if (remove == 0) {
                // last item
                if (menu.getItemInSlot(OUTPUT_SLOT) == null) {
                    menu.replaceExistingItem(OUTPUT_SLOT, cachedItemMeta.createItem(1), false);
                    cachedItemMeta = null;
                    cachedMetas.remove(block.getLocation());
                    menu.replaceExistingItem(DISPLAY_SLOT, EMPTY_ITEM);
                    amount = 0;
                }
            } else {
                ItemStack remaining = menu.pushItem(cachedItemMeta.createItem(remove), OUTPUT_SLOT);
                if (remaining != null) {
                    remove -= remaining.getAmount();
                }
                amount -= remove;
            }
        }
        
        // set data, don't use config cuz that bugs it out
        BlockStorage.addBlockInfo(block, STORED_AMOUNT, String.valueOf(amount));

        // update status
        if (menu.hasViewer()) {
            if (cachedItemMeta == null) {
                menu.replaceExistingItem(STATUS_SLOT, new CustomItem(
                        Material.CYAN_STAINED_GLASS_PANE,
                        "&bStatus",
                        "&6Stored: &e0" + displayStoredInfo(0),
                        "&7Stacks: 0"
                ), false);
            } else {
                menu.replaceExistingItem(STATUS_SLOT, new CustomItem(
                        Material.CYAN_STAINED_GLASS_PANE,
                        "&bStatus",
                        "&6Stored: &e" + LorePreset.format(amount) + displayStoredInfo(amount),
                        "&7Stacks: " + amount / cachedItemMeta.type.getMaxStackSize()
                ), false);
            }
        }

        // update signs
        if (DISPLAY_SIGNS && (PluginUtils.getCurrentTick() & 15) == 0) {
            Block check = block.getRelative(0, 1, 0);
            if (SlimefunTag.SIGNS.isTagged(check.getType())
                    || checkWallSign(check = block.getRelative(1, 0, 0), block)
                    || checkWallSign(check = block.getRelative(-1, 0, 0), block)
                    || checkWallSign(check = block.getRelative(0, 0, 1), block)
                    || checkWallSign(check = block.getRelative(0, 0, -1), block)
            ) {
                Sign sign = (Sign) check.getState();
                sign.setLine(0, ChatColor.GRAY + "--------------");
                sign.setLine(1, cachedItemMeta == null ? ChatColor.WHITE + "Empty" : cachedItemMeta.displayName);
                sign.setLine(2, ChatColor.YELLOW.toString() + amount);
                sign.setLine(3, ChatColor.GRAY + "--------------");
                sign.update();
            }
        }
    }

    protected String displayStoredInfo(int amount) {
        return " / " + LorePreset.format(this.max) + " &7(" + (100 * amount) / this.max + "%)";
    }

    private static boolean checkWallSign(Block sign, Block block) {
        return SlimefunTag.WALL_SIGNS.isTagged(sign.getType())
                && sign.getRelative(((WallSign) sign.getBlockData()).getFacing().getOppositeFace()).equals(block);
    }

    @Override
    protected void onPlace(BlockPlaceEvent e, @Nonnull Block b) {
        Pair<ItemStack, Integer> data = loadFromStack(e.getItemInHand().getItemMeta());
        if (data != null) {
            cachedMetas.put(b.getLocation(), new CachedItemMeta(data.getFirstValue(), data.getFirstValue().getItemMeta()));
            BlockStorage.getInventory(b).replaceExistingItem(DISPLAY_SLOT, data.getFirstValue());
            BlockStorage.addBlockInfo(b, STORED_AMOUNT, String.valueOf(data.getSecondValue()));
        } else {
            BlockStorage.addBlockInfo(b, STORED_AMOUNT, "0");
        }
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        Location l = b.getLocation();

        // add interaction handler
        menu.addMenuClickHandler(INTERACT_SLOT, new InteractionHandler(menu, l));
        
        // update old data if present
        ItemStack display = menu.getItemInSlot(DISPLAY_SLOT);
        if (display == null) {
            String oldID = BlockStorage.getLocationInfo(l, OLD_STORED_ITEM);
            if (oldID != null) {
                ItemStack item = StackUtils.getItemByIDorType(oldID);
                if (item != null) {
                    cachedMetas.put(l, new CachedItemMeta(item));
                    menu.replaceExistingItem(DISPLAY_SLOT, item);
                } else {
                    menu.replaceExistingItem(DISPLAY_SLOT, EMPTY_ITEM);
                }
                BlockStorage.addBlockInfo(l, OLD_STORED_ITEM, null);
            } else {
                // when placed
                menu.replaceExistingItem(DISPLAY_SLOT, EMPTY_ITEM);
            }
        } else {
            // cache stored meta
            ItemMeta stored = display.getItemMeta();
            
            if (stored.getPersistentDataContainer().has(DISPLAY_KEY, PersistentDataType.BYTE)) {
                
                // hot fix
                if (stored.getPersistentDataContainer().has(EMPTY_KEY, PersistentDataType.BYTE)) {
                    ItemStack output = menu.getItemInSlot(OUTPUT_SLOT);
                    if (output != null) {
                        cachedMetas.put(l, new CachedItemMeta(output));
                        menu.replaceExistingItem(OUTPUT_SLOT, null);
                        menu.replaceExistingItem(DISPLAY_SLOT, output);
                    } else {
                        menu.replaceExistingItem(DISPLAY_SLOT, EMPTY_ITEM);
                        BlockStorage.addBlockInfo(l, STORED_AMOUNT, "0");
                    }
                    return;
                }
                
                cachedMetas.put(l, new CachedItemMeta(display, stored));
                menu.replaceExistingItem(DISPLAY_SLOT, display);
            } else {
                menu.replaceExistingItem(DISPLAY_SLOT, EMPTY_ITEM);
            }
        }
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu menu, @Nonnull Location l) {
        int amount = Util.getIntData(STORED_AMOUNT, BlockStorage.getLocationInfo(l), l);
        if (amount > 0) {
            CachedItemMeta cachedItemMeta = cachedMetas.remove(l);
            if (cachedItemMeta == null) {
                return;
            }
            
            e.setDropItems(false);

            // add output slot
            ItemStack output = menu.getItemInSlot(OUTPUT_SLOT);
            if (output != null && cachedItemMeta.matches(output)) {
                int add = Math.min(this.max - amount, output.getAmount());
                if (add != 0) {
                    amount += add;
                    output.setAmount(output.getAmount() - add);
                }
            }

            ItemStack drop = getItem().clone();

            drop.setItemMeta(saveToStack(drop.getItemMeta(), menu.getItemInSlot(DISPLAY_SLOT), cachedItemMeta.displayName, amount));

            MessageUtils.message(e.getPlayer(), "&aStored items transferred to dropped item");

            e.getBlock().getWorld().dropItemNaturally(l, drop);
        }

        menu.dropItems(l, INPUT_SLOT, OUTPUT_SLOT);

    }

    private static ItemMeta saveToStack(ItemMeta meta, ItemStack displayItem, String displayName, int amount) {
        List<String> lore = meta.getLore();
        if (lore != null) {
            lore.add(ChatColor.GOLD + "Stored: " + displayName + ChatColor.YELLOW + " x " + amount);
            meta.setLore(lore);
        }
        meta.getPersistentDataContainer().set(ITEM_KEY, PersistentItemStack.instance(), displayItem);
        meta.getPersistentDataContainer().set(AMOUNT_KEY, PersistentDataType.INTEGER, amount);
        return meta;
    }

    @Nullable
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
            ItemStack item = meta.getPersistentDataContainer().get(ITEM_KEY, PersistentItemStack.instance());
            if (item != null) {
                return new Pair<>(item, amount);
            }
        }
        return null;
    }

    static void transferToStack(@Nonnull ItemStack source, @Nonnull ItemStack target) {
        Pair<ItemStack, Integer> data = loadFromStack(source.getItemMeta());
        if (data != null) {
            target.setItemMeta(saveToStack(target.getItemMeta(), data.getFirstValue(),
                    StackUtils.getDisplayName(data.getFirstValue()), data.getSecondValue()));
        }
    }
    
    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.INSERT) {
            CachedItemMeta meta = cachedMetas.get(((BlockMenu) menu).getLocation());
            if (meta == null || meta.matches(item)) {
                return new int[] {INPUT_SLOT};
            }
        } else if (flow == ItemTransportFlow.WITHDRAW) {
            return new int[] {OUTPUT_SLOT};
        }
        return new int[0];
    }

    @AllArgsConstructor
    private final class InteractionHandler implements ChestMenu.MenuClickHandler {

        private final BlockMenu menu;
        private final Location location;

        @Override
        public boolean onClick(Player p, int slot, ItemStack item, ClickAction action) {
            CachedItemMeta meta = cachedMetas.get(this.location);
            if (meta == null) {
                return false;
            }
            int amount = Util.getIntData(STORED_AMOUNT, this.location);
            if (amount == 1) {
                if (action.isShiftClicked() && !action.isRightClicked()) {
                    depositAll(p, amount, meta);
                } else {
                    withdrawLast(p, meta);
                }
            } else {
                if (action.isRightClicked()) {
                    if (action.isShiftClicked()) {
                        withdraw(p, amount, amount - 1, meta);
                    } else {
                        withdraw(p, amount, Math.min(meta.type.getMaxStackSize(), amount - 1), meta);
                    }
                } else {
                    if (action.isShiftClicked()) {
                        depositAll(p, amount, meta);
                    } else {
                        withdraw(p, amount, 1, meta);
                    }
                }
            }
            return false;
        }

        private void withdraw(Player p, int amount, int withdraw, CachedItemMeta cachedItemMeta) {
            ItemStack remaining = p.getInventory().addItem(cachedItemMeta.createItem(withdraw)).get(0);
            if (remaining != null) {
                withdraw -= remaining.getAmount();
            }
            BlockStorage.addBlockInfo(this.location, STORED_AMOUNT, String.valueOf(amount - withdraw));
        }

        private void withdrawLast(Player p, CachedItemMeta cachedItemMeta) {
            if (p.getInventory().addItem(cachedItemMeta.createItem(1)).isEmpty()) {
                BlockStorage.addBlockInfo(this.location, STORED_AMOUNT, String.valueOf(0));
                cachedMetas.remove(this.location);
                this.menu.replaceExistingItem(DISPLAY_SLOT, EMPTY_ITEM, false);
            }
        }

        private void depositAll(Player p, int amount, CachedItemMeta meta) {
            amount = StorageUnit.this.max - amount;
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
                BlockStorage.addBlockInfo(this.location, STORED_AMOUNT, String.valueOf(StorageUnit.this.max - amount));
            }
        }

    }

    private static final class CachedItemMeta {

        private final Material type;
        private final String displayName;
        @Nullable
        private final ItemMeta meta;

        // load from input
        private CachedItemMeta(ItemStack item) {
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
            meta.getPersistentDataContainer().set(DISPLAY_KEY, PersistentDataType.BYTE, (byte) 1);
            item.setItemMeta(meta);
            item.setAmount(1);
        }

        // load from stored
        private CachedItemMeta(ItemStack item, ItemMeta meta) {
            // remove the display key from this meta
            meta.getPersistentDataContainer().remove(DISPLAY_KEY);
            
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

        private boolean matches(ItemStack item) {
            return item.getType() == this.type
                    && item.hasItemMeta() == (this.meta != null)
                    && (this.meta == null || this.meta.equals(item.getItemMeta()));
        }

        private ItemStack createItem(int amount) {
            ItemStack item = new ItemStack(this.type, amount);
            if (this.meta != null) {
                item.setItemMeta(this.meta);
            }
            return item;
        }

    }

}
