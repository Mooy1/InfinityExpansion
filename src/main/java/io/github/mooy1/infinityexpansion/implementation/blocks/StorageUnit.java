package io.github.mooy1.infinityexpansion.implementation.blocks;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.materials.CompressedItem;
import io.github.mooy1.infinityexpansion.implementation.materials.MachineItem;
import io.github.mooy1.infinityexpansion.implementation.materials.SmelteryItem;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.misc.Pair;
import io.github.mooy1.infinitylib.objects.AbstractContainer;
import io.github.mooy1.infinitylib.player.MessageUtils;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Basically just barrels...
 *
 * @author Mooy1
 *
 * Thanks to
 * @author NCBPFluffyBear
 * for idea, a few bits of code, and code to learn from
 *
 */
public final class StorageUnit extends AbstractContainer {
    
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
    
    public static boolean DISPLAY_SIGNS = false;
    
    public static final int BASIC_STORAGE = 6400;
    public static final int ADVANCED_STORAGE = 25600;
    public static final int REINFORCED_STORAGE = 102400;
    public static final int VOID_STORAGE = 409600;
    public static final int INFINITY_STORAGE = 1_600_000_000;
    
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
    
    private static final int STATUS_SLOT = MenuPreset.slot2;
    private static final int[] INPUT_SLOTS = {MenuPreset.slot1};
    private static final int INPUT_SLOT = INPUT_SLOTS[0];
    private static final int OUTPUT_SLOT = MenuPreset.slot3;
    
    private static final Map<Block, List<Block>> SIGNS = new HashMap<>();
    
    private final int max;
    
    private StorageUnit(SlimefunItemStack item, int max, ItemStack[] recipe) {
        super(Categories.STORAGE_TRANSPORT, item, StorageForge.TYPE, recipe);
        this.max = max;

        addItemHandler(new BlockPlaceHandler(false) { 
            @Override
            public void onPlayerPlace(BlockPlaceEvent e) {
                Pair<String, Integer> data = getData(e.getItemInHand().getItemMeta());
                setStored(e.getBlock(), data.getA());
                setAmount(e.getBlock(), data.getB());
                if (data.getA() != null) {
                    MessageUtils.message(e.getPlayer(), ChatColor.GREEN + "Stored items transferred to block");
                }
            }
        });
        
        addItemHandler((BlockBreakHandler) (e, item1, fortune, drops) -> { Block b = e.getBlock();
            BlockMenu inv = BlockStorage.getInventory(b);
            
            if (inv == null) {
                return true;
            }

            int stored = getAmount(b);
            
            if (stored > 0) {
                String id = getStored(b);

                ItemStack storedItem = StackUtils.getItemByNullableIDorType(id);

                if (storedItem != null) {
                    e.setDropItems(false);

                    ItemStack drop = getItem().clone();
                    
                    drop.setItemMeta(saveData(drop.getItemMeta(), id, storedItem, tryToStoreOrDrop(inv, stored, storedItem, INPUT_SLOT, OUTPUT_SLOT)));
                    
                    MessageUtils.message(e.getPlayer(), ChatColor.GREEN + "Stored items transferred to dropped item");
                    
                    b.getWorld().dropItemNaturally(b.getLocation(), drop);
                }
            }
            
            inv.dropItems(b.getLocation(), INPUT_SLOT, OUTPUT_SLOT);
            
            return true;
        });
    }
    
    private int tryToStoreOrDrop(@Nonnull BlockMenu inv, int stored, ItemStack storedItem, int... slots) {
        for (int slot : slots) {
            ItemStack item = inv.getItemInSlot(slot);
            if (item == null) {
                continue;
            }
            if (canBeAdded(item, item.getItemMeta().getPersistentDataContainer(), storedItem)) {
                int amount = Math.min(this.max - stored, item.getAmount());
                if (amount > 0) {
                    stored += amount;
                    item.setAmount(item.getAmount() - amount);
                }
            }
        }
        
        return stored;
    }

    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
        MenuPreset.setupBasicMenu(blockMenuPreset);
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.loadingItemBarrier, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
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
    
    @Override
    public void tick(@Nonnull Block b, @Nonnull BlockMenu menu) {
        String id = getStored(b);
        int amount = getAmount(b);
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
                        
                        if (canBeAdded(input, container, inputDefault)) {
                            
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
                if (canBeAdded(input, input.getItemMeta().getPersistentDataContainer(), stored)) {
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
        
        // update signs
        if (DISPLAY_SIGNS && InfinityExpansion.progressEvery(16)) {
            for (Block sign : SIGNS.computeIfAbsent(b, k -> {
                List<Block> list = new ArrayList<>();
                Location l = b.getLocation();
                list.add(l.clone().add(1, 0, 0).getBlock());
                list.add(l.clone().add(-1, 0, 0).getBlock());
                list.add(l.clone().add(0, 0, 1).getBlock());
                list.add(l.clone().add(0, 0, -1).getBlock());
                return list;
            })) {
                if (SlimefunTag.WALL_SIGNS.isTagged(sign.getType())) {
                    WallSign wall = (WallSign) sign.getBlockData();
                    if (sign.getRelative(wall.getFacing().getOppositeFace()).equals(b)) {
                        Sign lines = (Sign) sign.getState();
                        lines.setLine(0, ChatColor.AQUA + "------------");
                        lines.setLine(1, ChatColor.WHITE + (stored != null ? ItemUtils.getItemName(stored) : "None"));
                        lines.setLine(2, ChatColor.GRAY + "Stored: " + amount);
                        lines.setLine(3, ChatColor.AQUA + "------------");
                        lines.update();
                        break;
                    }
                }
            }
        }
        
        // set data
        setAmount(b, amount);
        setStored(b, id);
    }
    
    private static boolean canBeAdded(ItemStack stack, PersistentDataContainer container, ItemStack stored) {
        if (stack.getEnchantments().size() != 0) {
            return false;
        }
        return container.equals(stored.getItemMeta().getPersistentDataContainer());
    }

    private void setAmount(Block b, int amount) {
       BlockStorage.addBlockInfo(b, "stored", String.valueOf(amount));
    }

    private void setStored(Block b, String storedItem) {
        BlockStorage.addBlockInfo(b, "storeditem", storedItem);
    }

    public int getAmount(@Nonnull Block b) {
        try {
            return Integer.parseInt(BlockStorage.getLocationInfo(b.getLocation(), "stored"));
        } catch (NumberFormatException e) {
            setAmount(b, 0);
            return 0;
        }
    }
    
    @Nullable
    public String getStored(@Nonnull Block b) {
        return BlockStorage.getLocationInfo(b.getLocation(), "storeditem");
    }

    private static final NamespacedKey STORED_ITEM = PluginUtils.getKey("stored_item");
    private static final NamespacedKey STORED = PluginUtils.getKey("stored");
    
    private static ItemMeta saveData(@Nullable ItemMeta meta, @Nonnull String id, @Nullable ItemStack display, int amount) {
        if (meta != null) {
            List<String> lore = meta.getLore();
            if (lore != null) {
                lore.add(ChatColor.GOLD + "Stored: " + ChatColor.WHITE + ItemUtils.getItemName(display) + ChatColor.YELLOW + " x " + amount);
                meta.setLore(lore);
            }
            meta.getPersistentDataContainer().set(STORED_ITEM, PersistentDataType.STRING, id);
            meta.getPersistentDataContainer().set(STORED, PersistentDataType.INTEGER, amount);
        }
        return meta;
    }
    
    @Nonnull
    private static Pair<String, Integer> getData(@Nullable ItemMeta meta) {
        if (meta != null) {
            String item = meta.getPersistentDataContainer().get(STORED_ITEM, PersistentDataType.STRING);
            Integer amount = meta.getPersistentDataContainer().get(STORED, PersistentDataType.INTEGER);
            if (amount != null && item != null) {
                return new Pair<>(item, amount);
            }
        }
        return new Pair<>(null, 0);
    }
    
    static ItemMeta transferData(@Nullable ItemMeta input, @Nullable ItemMeta output) {
        Pair<String, Integer> data = getData(input);
        if (data.getA() != null) {
            saveData(output, data.getA(), StackUtils.getItemByID(data.getA()), data.getB());
        }
        return output;
    }
    
}
