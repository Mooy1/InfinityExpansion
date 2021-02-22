package io.github.mooy1.infinityexpansion.implementation.blocks;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.materials.CompressedItem;
import io.github.mooy1.infinityexpansion.implementation.materials.MachineItem;
import io.github.mooy1.infinityexpansion.implementation.materials.SmelteryItem;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.abstracts.AbstractTicker;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.player.MessageUtils;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Basically just barrels
 *
 * @author Mooy1
 *
 * Thanks to FluffyBear for stuff to learn from
 *
 */
public final class StorageUnit extends AbstractTicker {
    
    public static void setup(InfinityExpansion plugin) {
        new StorageUnit(BASIC, BASIC_STORAGE, new ItemStack[] {
                new ItemStack(Material.OAK_LOG), SmelteryItem.MAGSTEEL, new ItemStack(Material.OAK_LOG),
                new ItemStack(Material.OAK_LOG), new ItemStack(Material.BARREL), new ItemStack(Material.OAK_LOG),
                new ItemStack(Material.OAK_LOG), SmelteryItem.MAGSTEEL, new ItemStack(Material.OAK_LOG)
        }).register(plugin);
        new StorageUnit(ADVANCED, ADVANCED_STORAGE,  new ItemStack[] {
                SmelteryItem.MAGSTEEL, MachineItem.MACHINE_CIRCUIT, SmelteryItem.MAGSTEEL,
                SmelteryItem.MAGSTEEL, StorageUnit.BASIC, SmelteryItem.MAGSTEEL,
                SmelteryItem.MAGSTEEL, MachineItem.MACHINE_CIRCUIT, SmelteryItem.MAGSTEEL
        }).register(plugin);
        new StorageUnit(REINFORCED, REINFORCED_STORAGE,  new ItemStack[] {
                MachineItem.MAGSTEEL_PLATE, MachineItem.MACHINE_CIRCUIT, MachineItem.MAGSTEEL_PLATE,
                MachineItem.MAGSTEEL_PLATE, StorageUnit.ADVANCED, MachineItem.MAGSTEEL_PLATE,
                MachineItem.MAGSTEEL_PLATE, MachineItem.MACHINE_PLATE, MachineItem.MAGSTEEL_PLATE
        }).register(plugin);
        new StorageUnit(VOID, VOID_STORAGE,  new ItemStack[] {
                CompressedItem.VOID_INGOT, MachineItem.MACHINE_PLATE, CompressedItem.VOID_INGOT,
                SmelteryItem.MAGNONIUM, StorageUnit.REINFORCED, SmelteryItem.MAGNONIUM,
                CompressedItem.VOID_INGOT, MachineItem.MACHINE_CORE, CompressedItem.VOID_INGOT
        }).register(plugin);
        new StorageUnit(INFINITY, INFINITY_STORAGE,  new ItemStack[] {
                SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY,
                SmelteryItem.INFINITY, StorageUnit.VOID, SmelteryItem.INFINITY,
                SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY
        }).register(plugin);
    }
    
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
    
    private static final boolean DISPLAY_SIGNS = InfinityExpansion.getInstance().getConfig().getBoolean("storage-unit-options.display-signs");
    private static final String STORED_AMOUNT = "stored";
    private static final String STORED_ITEM = "storeditem";
    
    private static final int STATUS_SLOT = MenuPreset.slot2;
    private static final int[] INPUT_SLOTS = {MenuPreset.slot1};
    private static final int INPUT_SLOT = INPUT_SLOTS[0];
    private static final int OUTPUT_SLOT = MenuPreset.slot3;
    
    private final int max;
    
    private StorageUnit(SlimefunItemStack item, int max, ItemStack[] recipe) {
        super(Categories.STORAGE_TRANSPORT, item, StorageForge.TYPE, recipe);
        this.max = max;
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu menu, @Nonnull Location l) {
        
        Config config = BlockStorage.getLocationInfo(l);

        int stored = Util.getIntData(STORED_AMOUNT, config, l);

        if (stored > 0) {
            String id = config.getString(STORED_ITEM);

            ItemStack storedItem = StackUtils.getItemByNullableIDorType(id);

            if (storedItem != null) {
                e.setDropItems(false);

                ItemStack drop = getItem().clone();

                drop.setItemMeta(saveData(drop.getItemMeta(), id, storedItem, tryToStoreOrDrop(menu, stored, storedItem, INPUT_SLOT, OUTPUT_SLOT)));

                MessageUtils.message(e.getPlayer(), ChatColor.GREEN + "Stored items transferred to dropped item");

                e.getBlock().getWorld().dropItemNaturally(l, drop);
            }
        }

        menu.dropItems(l, INPUT_SLOT, OUTPUT_SLOT);
    }

    @Override
    protected void onPlace(@Nonnull BlockPlaceEvent e, @Nonnull Block b) {
        Pair<String, Integer> data = getData(e.getItemInHand().getItemMeta());
        BlockStorage.addBlockInfo(b, STORED_ITEM, data.getFirstValue());
        BlockStorage.addBlockInfo(b, STORED_AMOUNT, String.valueOf(data.getSecondValue()));
        if (data.getFirstValue() != null) {
            MessageUtils.message(e.getPlayer(), ChatColor.GREEN + "Stored items transferred to block");
        }
    }

    private int tryToStoreOrDrop(@Nonnull BlockMenu inv, int stored, ItemStack storedItem, int... slots) {
        for (int slot : slots) {
            ItemStack item = inv.getItemInSlot(slot);
            if (item == null) {
                continue;
            }
            if (item.getType() == storedItem.getType() && canBeAdded(item, item.getItemMeta().getPersistentDataContainer(), storedItem)) {
                int amount = Math.min(this.max - stored, item.getAmount());
                if (amount > 0) {
                    stored += amount;
                    item.setAmount(item.getAmount() - amount);
                }
            }
        }
        
        return stored;
    }
    
    @Override
    public void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        MenuPreset.setupBasicMenu(blockMenuPreset);
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.invalidInput, ChestMenuUtils.getEmptyClickHandler());
    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.INSERT) {
            return new int[] {INPUT_SLOT};
        } else if (flow == ItemTransportFlow.WITHDRAW) {
            return new int[] {OUTPUT_SLOT};
        } else {
            return new int[0];
        }
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {

    }

    private static boolean canBeAdded(@Nonnull ItemStack stack, @Nonnull PersistentDataContainer container, @Nonnull ItemStack stored) {
        return stack.getEnchantments().size() == 0 && container.equals(stored.getItemMeta().getPersistentDataContainer());
    }
    
    private static final NamespacedKey ITEM_KEY = PluginUtils.getKey("stored_item");
    private static final NamespacedKey AMOUNT_KEY = PluginUtils.getKey("stored");
    
    private static ItemMeta saveData(@Nullable ItemMeta meta, @Nonnull String id, @Nullable ItemStack display, int amount) {
        if (meta != null) {
            List<String> lore = meta.getLore();
            if (lore != null) {
                lore.add(ChatColor.GOLD + "Stored: " + ChatColor.WHITE + ItemUtils.getItemName(display) + ChatColor.YELLOW + " x " + amount);
                meta.setLore(lore);
            }
            meta.getPersistentDataContainer().set(ITEM_KEY, PersistentDataType.STRING, id);
            meta.getPersistentDataContainer().set(AMOUNT_KEY, PersistentDataType.INTEGER, amount);
        }
        return meta;
    }
    
    @Nonnull
    private static Pair<String, Integer> getData(@Nullable ItemMeta meta) {
        if (meta != null) {
            String item = meta.getPersistentDataContainer().get(ITEM_KEY, PersistentDataType.STRING);
            Integer amount = meta.getPersistentDataContainer().get(AMOUNT_KEY, PersistentDataType.INTEGER);
            if (amount != null && item != null) {
                return new Pair<>(item, amount);
            }
        }
        return new Pair<>(null, 0);
    }
    
    static ItemMeta transferData(@Nullable ItemMeta input, @Nullable ItemMeta output) {
        Pair<String, Integer> data = getData(input);
        if (data.getFirstValue() != null) {
            saveData(output, data.getFirstValue(), StackUtils.getItemByID(data.getFirstValue()), data.getSecondValue());
        }
        return output;
    }

    @Override
    public void tick(@Nonnull BlockMenu menu, @Nonnull Block block, @Nonnull Config config) {
        String id = config.getString(STORED_ITEM);
        int amount = Util.getIntData(STORED_AMOUNT, config, block.getLocation());
        ItemStack stored;
        
        // input
        ItemStack input = menu.getItemInSlot(INPUT_SLOT);
        if (input != null) {
            if (id == null || amount <= 0) {

                // try to start storing input
                if (!SlimefunTag.SHULKER_BOXES.isTagged(input.getType())) {

                    PersistentDataContainer container = input.getItemMeta().getPersistentDataContainer();
                    String inputID = StackUtils.getIDorType(container, input);

                    if (!inputID.contains("BACKPACK")) {

                        ItemStack inputDefault = StackUtils.getItemByIDorType(inputID);

                        if (inputDefault != null && canBeAdded(input, container, inputDefault)) {

                            id = inputID;
                            amount = input.getAmount();
                            input.setAmount(0);
                            stored = inputDefault;

                        } else {
                            stored = null;
                        }
                    } else {
                        stored = null;
                    }
                } else {
                    stored = null;
                }
            } else {
                // try to add input to storage
                stored = StackUtils.getItemByIDorType(id);
                if (stored != null && input.getType() == stored.getType() && canBeAdded(input, input.getItemMeta().getPersistentDataContainer(), stored)) {
                    int max = this.max - amount;
                    if (max > 0) {
                        int add = input.getAmount();
                        int dif = add - max;
                        if (dif < 0) {
                            amount += add;
                            input.setAmount(0);
                        } else {
                            amount += max;
                            input.setAmount(dif);
                        }
                    }  // else full
                }  // else cant be added
            }
        } else {
            stored = StackUtils.getItemByNullableIDorType(id);
        }

        if (stored != null) {
            StackUtils.removeEnchants(stored);

            // output
            if (amount > 0) {
                int remove = Math.min(stored.getMaxStackSize(), amount - 1);
                // last item
                if (remove == 0) {
                    if (menu.getItemInSlot(OUTPUT_SLOT) == null) {
                        menu.replaceExistingItem(OUTPUT_SLOT, stored, false);
                        amount = 0;
                        id = null;
                        stored = null;
                    }
                } else {
                    stored.setAmount(remove);
                    ItemStack remaining = menu.pushItem(stored, OUTPUT_SLOT);
                    if (remaining != null) {
                        remove -= remaining.getAmount();
                    }
                    amount -= remove;
                }
            }
        }

        // update status
        if (menu.hasViewer()) {
            if (stored == null) {
                menu.replaceExistingItem(STATUS_SLOT, new CustomItem(
                        new ItemStack(Material.BARRIER),
                        "&cEmpty"
                ));
            } else {
                ItemStack status = new CustomItem(
                        stored,
                        ChatColor.WHITE + ItemUtils.getItemName(stored),
                        "&6Stored: &e" + LorePreset.format(amount) + (this.max == INFINITY_STORAGE ? "" : "/" + LorePreset.format(this.max) + " &7(" + Math.round((float) 100 * amount / this.max)  + "%)"),
                        "&7Stacks: " + LorePreset.format(Math.round((float) amount / stored.getMaxStackSize()))
                );
                status.setAmount(1);
                menu.replaceExistingItem(STATUS_SLOT, status);
            }
        }
        
        // set data, don't use config cuz that bugs it out
        BlockStorage.addBlockInfo(block, STORED_AMOUNT, String.valueOf(amount));
        BlockStorage.addBlockInfo(block, STORED_ITEM, id);
        
        // update signs
        if (DISPLAY_SIGNS && (PluginUtils.getCurrentTick() & 15) == 0) {
            Block top = block.getRelative(0, 1, 0);
            if (SlimefunTag.SIGNS.isTagged(top.getType())) {
                writeToSign(top, stored, amount);
                return;
            }
            List<Block> blocks = new ArrayList<>();
            blocks.add(block.getRelative(1, 0, 0));
            blocks.add(block.getRelative(-1, 0, 0));
            blocks.add(block.getRelative(0, 0, 1));
            blocks.add(block.getRelative(0, 0, -1));
            for (Block side : blocks) {
                if (SlimefunTag.WALL_SIGNS.isTagged(side.getType())) {
                    WallSign wall = (WallSign) side.getBlockData();
                    if (side.getRelative(wall.getFacing().getOppositeFace()).equals(block)) {
                        writeToSign(side, stored, amount);
                        return;
                    }
                }
            }
        }
    }

    private static void writeToSign(Block b, ItemStack item, int amount) {
        Sign sign = (Sign) b.getState();
        sign.setLine(0, ChatColor.GRAY + "--------------");
        sign.setLine(1, ChatColor.WHITE +  (item == null ? "None" : ItemUtils.getItemName(item)));
        sign.setLine(2, ChatColor.YELLOW.toString() + amount);
        sign.setLine(3, ChatColor.GRAY + "--------------");
        sign.update();
    }
    
}
