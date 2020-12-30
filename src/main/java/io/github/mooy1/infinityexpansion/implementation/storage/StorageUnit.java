package io.github.mooy1.infinityexpansion.implementation.storage;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.abstracts.LoreStorage;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.lists.RecipeTypes;
import io.github.mooy1.infinitylib.items.LoreUtils;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.objects.AbstractContainer;
import io.github.mooy1.infinitylib.player.MessageUtils;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.backpacks.SlimefunBackpack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
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
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
public class StorageUnit extends AbstractContainer implements LoreStorage {
    
    public static boolean DISPLAY_SIGNS = false;
    public static int SIGN_REFRESH = 8;

    private static final Map<Block, Set<Block>> SIGNS = new HashMap<>();

    public static final int BASIC = 6400;
    public static final int ADVANCED = 25600;
    public static final int REINFORCED = 102400;
    public static final int VOID = 409600;
    public static final int INFINITY = 1_600_000_000;
    
    private final Type type;
    
    private static final int STATUS_SLOT = MenuPreset.slot2;
    private static final int[] INPUT_SLOTS = {MenuPreset.slot1};
    private static final int INPUT_SLOT = INPUT_SLOTS[0];
    private static final int[] OUTPUT_SLOTS = {MenuPreset.slot3};
    private static final int OUTPUT_SLOT = OUTPUT_SLOTS[0];
    private static final int[] INPUT_BORDER = MenuPreset.slotChunk1;
    private static final int[] STATUS_BORDER = MenuPreset.slotChunk2;
    private static final int[] OUTPUT_BORDER = MenuPreset.slotChunk3;

    public StorageUnit(@Nonnull Type type) {
        super(type.getCategory(), type.getItem(), type.getRecipeType(), type.getRecipe());
        this.type = type;

        addItemHandler(new BlockPlaceHandler(false) { //transfer stuffs
            @Override
            public void onPlayerPlace(BlockPlaceEvent e) {
                Block b = e.getBlock();
                setStored(b, 0);
                ItemStack placedItem = e.getItemInHand();

                ItemMeta meta = placedItem.getItemMeta();
                if (meta != null) {

                    List<String> lore = meta.getLore();
                    if (lore != null) {

                        int i = 0;
                        for (String line : lore) {
                            if (ChatColor.stripColor(line).equals("Stored Item:")) {
                                Player p = e.getPlayer();

                                String storedItem = ChatColor.stripColor(lore.get(i + 1));
                                setStoredItem(b, storedItem);

                                String stored = ChatColor.stripColor(lore.get(i + 3));
                                setStored(b, Integer.parseInt(stored));

                                MessageUtils.message(p, ChatColor.GREEN + "Stored items transferred to placed item");

                                break;
                            }
                            i++;
                        }
                    }
                }
            }
        });
        
        addItemHandler((BlockBreakHandler) (e, item, fortune, drops) -> {
            Player p = e.getPlayer();
            Block b = e.getBlock();

            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv == null) return false;

            Location l = b.getLocation();
            String storedItem = getStoredItem(b);
            int stored = getStored(b);

            e.setDropItems(false);
            ItemStack drop = type.getItem().clone();

            if (stored > 0 && storedItem != null) {
                tryToStoreOrDrop(b, inv, INPUT_SLOTS);
                tryToStoreOrDrop(b, inv, OUTPUT_SLOTS);

                stored = getStored(b);

                LoreUtils.addLore(drop, "", ChatColor.AQUA + "Stored Item:", ChatColor.GREEN + storedItem, ChatColor.AQUA + "Amount:", ChatColor.GREEN + String.valueOf(stored));

                MessageUtils.message(p, ChatColor.GREEN + "Stored items transferred to dropped item");

            } else {
                inv.dropItems(l, INPUT_SLOTS);
                inv.dropItems(l, OUTPUT_SLOTS);
            }

            b.getWorld().dropItemNaturally(l, drop);

            return true;
        });
    }

    /**
     * This method will attempt to store an item before the unit is broken, otherwise drop it
     *
     * @param b block broken
     * @param inv BlockMenu of block
     * @param slots slots to perform this on
     */
    private void tryToStoreOrDrop(Block b, @Nonnull BlockMenu inv, @Nonnull int[] slots) {
        ItemStack inputSlot = inv.getItemInSlot(slots[0]);
        
        if (inputSlot == null) return;

        Location l = b.getLocation();
        
        if (inputSlot.getEnchantments().size() > 0) {
            inv.dropItems(l, slots);
            return;
        }

        int stored = getStored(b);
        String storedItem = getStoredItem(b);
        String inputID = StackUtils.getItemID(inputSlot, true);

        if (Objects.equals(inputID, storedItem)) {

            int inputAmount = inputSlot.getAmount();
            if (this.type.getMax() >= stored + inputAmount) {

                setStored(b, stored + inputAmount);
                inv.replaceExistingItem(slots[0], null);

            } else {

                int amount = this.type.getMax() - stored;

                setStored(b, this.type.getMax());
                inputSlot.setAmount(inputAmount - amount);
                inv.replaceExistingItem(slots[0], inputSlot);
                inv.dropItems(l, slots);

            }
            return;

        }

        inv.dropItems(l, slots);
    }

    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : STATUS_BORDER) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : INPUT_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : OUTPUT_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }

        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.loadingItemBarrier, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        if (flow == ItemTransportFlow.INSERT) {
            return INPUT_SLOTS;
        } else if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        } else {
            return new int[0];
        }
    }

    @Override
    public void tick(@Nonnull Block b, @Nonnull BlockMenu inv) {
        //input
        ItemStack inputSlotItem = inv.getItemInSlot(INPUT_SLOT);

        if (inputSlotItem != null) { //Check if empty slot
            
            String inputItemID = StackUtils.getItemID(inputSlotItem, true);
            String storedItem = getStoredItem(b);

            //Check if non stackable item or another storage unit
            if (inputItemID != null && inputSlotItem.getEnchantments().size() == 0 && checkItem(inputSlotItem)) {
                
                int slotAmount = inputSlotItem.getAmount();
                int stored = getStored(b);

                if (stored == 0 && storedItem == null) { //store new item

                    setStoredItem(b, inputItemID);
                    setStored(b, slotAmount);
                    inv.consumeItem(INPUT_SLOT, slotAmount);

                } else {

                    int maxInput = this.type.getMax() - stored;
                    storedItem = getStoredItem(b);

                    if (storedItem.equals(inputItemID)) { //deposit item

                        if (slotAmount <= maxInput) {
                            setStored(b, stored + slotAmount);
                            inv.consumeItem(INPUT_SLOT, slotAmount);
                        } else {
                            setStored(b, stored + maxInput);
                            inv.consumeItem(INPUT_SLOT, maxInput);
                        }
                    }
                }

            } else {

                if (inv.fits(inputSlotItem, OUTPUT_SLOTS)) { //try to move to output slot to decrease timings

                    inv.pushItem(inputSlotItem, OUTPUT_SLOTS);
                    inv.consumeItem(INPUT_SLOT, inputSlotItem.getAmount());

                }
            }
        }

        updateStatus(b, inv, false);

        //output
        String storedItem = getStoredItem(b);

        if (storedItem == null) {
            return;
        }

        ItemStack storedItemStack = StackUtils.getItemFromID(storedItem, 1);
        if (storedItemStack == null) {
            setStoredItem(b, null);
            return;
        }
        int stored = getStored(b);
        int maxOutput;

        if (inv.getItemInSlot(OUTPUT_SLOT) != null) {
            maxOutput = storedItemStack.getMaxStackSize()-inv.getItemInSlot(OUTPUT_SLOT).getAmount();
        } else {
            maxOutput = storedItemStack.getMaxStackSize();
        }

        StackUtils.removeEnchants(storedItemStack);

        if (stored > 1) {

            int amount = Math.min(maxOutput, stored - 1);
            storedItemStack.setAmount(amount);

            if (inv.fits(storedItemStack, OUTPUT_SLOT)) {
                inv.pushItem(storedItemStack, OUTPUT_SLOTS);
                setStored(b, stored - amount);
            }

        } else if (stored == 1 && inv.getItemInSlot(OUTPUT_SLOT) == null) {
            inv.pushItem(storedItemStack, OUTPUT_SLOTS);
            setStoredItem(b, null);
            setStored(b, 0);
        }

        updateStatus(b, inv, true);
        
    }
    
    private static boolean checkItem(ItemStack stack) {
        SlimefunItem item = SlimefunItem.getByItem(stack);
        return !(item instanceof SlimefunBackpack) && !(item instanceof LoreStorage) && !SlimefunTag.SHULKER_BOXES.isTagged(stack.getType());
    }

    /**
     * This method updates the status slot of the storage unit
     *
     * @param b block of storage unit
     */
    private void updateStatus(@Nonnull Block b, @Nonnull BlockMenu inv, boolean updateSign) {
        String storedItem = getStoredItem(b);
        int stored = getStored(b);
        
        String name;
        
        if (!inv.hasViewer() && (!updateSign || !InfinityExpansion.progressEvery(SIGN_REFRESH))) return;
        
        if (storedItem == null || stored == 0) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(
                        new ItemStack(Material.BARRIER),
                        "&cInput an Item!"
                ));
            }
            
            name = "None";
            
        } else {

            ItemStack storedItemStack = StackUtils.getItemFromID(storedItem, 1);
            if (storedItemStack == null) {
                setStoredItem(b, null);
                return;
            }

            ItemStack item = makeDisplayItem(this.type.getMax(), storedItemStack, stored, this.type == Type.INFINITIES);
            
            name = ItemUtils.getItemName(item);
            
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, item);
            }
            
        }

        if (DISPLAY_SIGNS && updateSign) {
            Set<Block> cached = SIGNS.get(b);

            if (cached == null) {
                cached = addToCache(b);
            }

            for (Block sign : cached) {

                if (SlimefunTag.WALL_SIGNS.isTagged(sign.getType())) {

                    WallSign wall = (WallSign) sign.getBlockData();

                    if (sign.getRelative(wall.getFacing().getOppositeFace()).equals(b)) {
                        
                        Sign lines = (Sign) sign.getState();

                        lines.setLine(0, ChatColor.AQUA + "------------");
                        lines.setLine(1, ChatColor.WHITE + name);
                        lines.setLine(2, ChatColor.GRAY + "Stored: " + stored);
                        lines.setLine(3, ChatColor.AQUA + "------------");

                        lines.update();

                        break;
                    }
                }
            }
        }
    }
    
    private Set<Block> addToCache(@Nonnull Block b) {
        Set<Block> cached = new HashSet<>();

        Location l = b.getLocation();
        cached.add(l.clone().add(1, 0, 0).getBlock());
        cached.add(l.clone().add(-1, 0, 0).getBlock());
        cached.add(l.clone().add(0, 0, 1).getBlock());
        cached.add(l.clone().add(0, 0, -1).getBlock());
        
        SIGNS.put(b, cached);
        return cached;
    }

    /**
     * This method makes a display item with lore from the amount, type, and max storage
     *
     * @param max max storage
     * @param storedItemStack ItemStack gotten from the stored item id
     * @param stored amount stored
     * @param infinity whether the storage unit is a infinity storage unit
     * @return the display item
     */
    @Nonnull
    public static ItemStack makeDisplayItem(int max, @Nonnull ItemStack storedItemStack, int stored, boolean infinity) {
        
        String stacksLine = "&7Stacks: " + LorePreset.format(Math.round((float) stored / storedItemStack.getMaxStackSize()));
        
        String storedLine = "&6Stored: &e" + LorePreset.format(stored) + (infinity ? "" : "/" + LorePreset.format(max) + " &7(" + Math.round((float) 100 * stored / max )  + "%)");
        
        ItemStack item = new CustomItem(
                storedItemStack,
                ChatColor.WHITE + ItemUtils.getItemName(storedItemStack),
                storedLine,
                stacksLine
        );
        
        StackUtils.removeEnchants(item);

        return item;
    }

    private void setStored(Block b, int stored) {
       BlockStorage.addBlockInfo(b, "stored", String.valueOf(stored));
    }

    private void setStoredItem(Block b, String storedItem) {
        BlockStorage.addBlockInfo(b, "storeditem", storedItem);
    }

    public int getStored(@Nonnull Block b) {
        return Integer.parseInt(BlockStorage.getLocationInfo(b.getLocation(), "stored"));
    }

    public String getStoredItem(@Nonnull Block b) {
        return BlockStorage.getLocationInfo(b.getLocation(), "storeditem");
    }

    @Override
    public int getOffset() {
        return -1;
    }

    @Override
    public int getLines() {
        return 5;
    }

    @Nonnull
    @Override
    public String getTarget() {
        return "Stored Item:";
    }
    
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)

    public enum Type {
        BASICS(Categories.STORAGE_TRANSPORT, Items.BASIC_STORAGE, BASIC, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                new ItemStack(Material.OAK_LOG), Items.MAGSTEEL, new ItemStack(Material.OAK_LOG),
                new ItemStack(Material.OAK_LOG), new ItemStack(Material.BARREL), new ItemStack(Material.OAK_LOG),
                new ItemStack(Material.OAK_LOG), Items.MAGSTEEL, new ItemStack(Material.OAK_LOG)
        }),
        ADVANCES(Categories.STORAGE_TRANSPORT, Items.ADVANCED_STORAGE, ADVANCED, RecipeTypes.STORAGE_FORGE, StorageForge.RECIPES[0]),
        REINFORCED_(Categories.STORAGE_TRANSPORT, Items.REINFORCED_STORAGE, REINFORCED, RecipeTypes.STORAGE_FORGE, StorageForge.RECIPES[1]),
        VOIDS(Categories.STORAGE_TRANSPORT, Items.VOID_STORAGE, VOID, RecipeTypes.STORAGE_FORGE, StorageForge.RECIPES[2]),
        INFINITIES(Categories.STORAGE_TRANSPORT, Items.INFINITY_STORAGE, INFINITY, RecipeTypes.STORAGE_FORGE, StorageForge.RECIPES[3]);

        @Nonnull
        private final Category category;
        private final SlimefunItemStack item;
        private final int max;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}
