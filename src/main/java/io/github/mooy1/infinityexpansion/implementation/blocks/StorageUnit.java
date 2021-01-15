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
import io.github.thebusybiscuit.slimefun4.implementation.items.backpacks.SlimefunBackpack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
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
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
            if (inv == null) return false;
            String storedItem = getStored(b);
            int stored = getAmount(b);
            e.setDropItems(false);
            ItemStack drop = getItem().clone();
            if (stored > 0 && storedItem != null) {
                stored = tryToStoreOrDrop(inv, stored, storedItem, INPUT_SLOT, OUTPUT_SLOT);
                drop.setItemMeta(saveData(drop.getItemMeta(), storedItem, stored));
                MessageUtils.message(e.getPlayer(), ChatColor.GREEN + "Stored items transferred to dropped item");
            }
            inv.dropItems(b.getLocation(), INPUT_SLOT, OUTPUT_SLOT);
            b.getWorld().dropItemNaturally(b.getLocation(), drop);
            return true;
        });
    }
    
    private int tryToStoreOrDrop(@Nonnull BlockMenu inv, int stored, String storedItem, int... slots) {
        for (int slot : slots) {
            ItemStack item = inv.getItemInSlot(slot);

            if (item == null) {
                continue;
            }
            
            if (canBeAdded(item)) {
                String inputID = StackUtils.getIDorElse(item, item.getType().toString());

                if (Objects.equals(inputID, storedItem)) {

                    int inputAmount = item.getAmount();
                    if (this.max >= stored + inputAmount) {

                        stored = stored + inputAmount;
                        inv.replaceExistingItem(slots[0], null);

                    } else {

                        int amount = this.max - stored;

                        stored = this.max;
                        item.setAmount(inputAmount - amount);
                        inv.replaceExistingItem(slots[0], item);
                    }
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
        String stored = getStored(b);
        int amount = getAmount(b);

        // input
        ItemStack input = menu.getItemInSlot(INPUT_SLOT);
        if (input != null) {
            String id = StackUtils.getIDorElse(input, input.getType().toString());
            if (stored == null || amount <= 0) {
                if (canBeStored(input)) {
                    stored = id;
                    amount = input.getAmount();
                    input.setAmount(0);
                }  // else cant be stored
            } else if (id.equals(stored)) {
                if (canBeAdded(input)) {
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
            }  // else doesn't match
        }
        
        ItemStack output = StackUtils.getItemFromID(stored, 1);
        
        if (output != null) {
            StackUtils.removeEnchants(output);
            
            // output
            if (amount > 0) {
                int remove = Math.min(64, amount - 1);
                // last item
                if (remove == 0) {
                    if (menu.getItemInSlot(OUTPUT_SLOT) == null) {
                        menu.replaceExistingItem(OUTPUT_SLOT, output, false);
                        amount = 0;
                        stored = null;
                        output = null;
                    }
                } else {
                    output.setAmount(remove);
                    ItemStack remaining = menu.pushItem(output, OUTPUT_SLOT);
                    if (remaining != null) {
                        remove -= remaining.getAmount();
                    }
                    amount -= remove;
                }
            }
        }
        
        // update status
        if (menu.hasViewer()) {
            if (output == null) {
                menu.replaceExistingItem(STATUS_SLOT, new CustomItem(
                        new ItemStack(Material.BARRIER),
                        "&cEmpty"
                ));
            } else {
                ItemStack status = new CustomItem(
                        output,
                        ChatColor.WHITE + ItemUtils.getItemName(output),
                        "&6Stored: &e" + LorePreset.format(amount) + (this.max == INFINITY_STORAGE ? "" : "/" + LorePreset.format(this.max) + " &7(" + Math.round((float) 100 * amount / this.max)  + "%)"),
                        "&7Stacks: " + LorePreset.format(Math.round((float) amount / output.getMaxStackSize()))
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
                        lines.setLine(1, ChatColor.WHITE + (output != null ? ItemUtils.getItemName(output) : "EMPTY"));
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
        setStored(b, stored);
    }

    
    private static boolean canBeStored(@Nonnull ItemStack stack) {
        if (stack.getEnchantments().size() != 0 || SlimefunTag.SHULKER_BOXES.isTagged(stack.getType())) {
            return false;
        }
        SlimefunItem item = SlimefunItem.getByItem(stack);
        return !(item instanceof SlimefunBackpack || item instanceof StorageUnit);
    }

    private static boolean canBeAdded(ItemStack stack) {
        return stack.getEnchantments().size() == 0;
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

    public String getStored(@Nonnull Block b) {
        return BlockStorage.getLocationInfo(b.getLocation(), "storeditem");
    }

    private static final NamespacedKey STORED_ITEM = PluginUtils.getKey("stored_item");
    private static final NamespacedKey STORED = PluginUtils.getKey("stored");
    
    @Nullable
    @Contract("!null, _, _ -> !null")
    private static  ItemMeta saveData(@Nullable ItemMeta meta, @Nonnull String item, int amount) {
        if (meta != null) {
            meta.getPersistentDataContainer().set(STORED_ITEM, PersistentDataType.STRING, item);
            meta.getPersistentDataContainer().set(STORED, PersistentDataType.INTEGER, amount);
        }
        return null;
    }
    
    @Nonnull
    private static  Pair<String, Integer> getData(@Nullable ItemMeta meta) {
        if (meta != null) {
            String item = meta.getPersistentDataContainer().get(STORED_ITEM, PersistentDataType.STRING);
            Integer amount = meta.getPersistentDataContainer().get(STORED, PersistentDataType.INTEGER);
            if (amount != null && item != null) {
                return new Pair<>(item, amount);
            }
        }
        return new Pair<>(null, 0);
    }
    
    @Nullable
    @Contract("_, !null -> !null")
    static ItemMeta transferMeta(@Nullable ItemStack input, @Nullable ItemMeta output) {
        if (input != null) {
            Pair<String, Integer> data = getData(input.getItemMeta());
            if (data.getA() != null) {
                saveData(output, data.getA(), data.getB());
            }
        }
        return output;
    }
    
}
