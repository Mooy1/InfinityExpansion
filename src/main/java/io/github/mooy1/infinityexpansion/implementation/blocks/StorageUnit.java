package io.github.mooy1.infinityexpansion.implementation.blocks;

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
public class StorageUnit extends AbstractContainer implements LoreStorage {
    
    public static boolean DISPLAY_SIGNS = false;
    public static int SIGN_REFRESH = 16;

    private static final Map<Block, List<Block>> SIGNS = new HashMap<>();

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
    private static final int[] INPUT_BORDER = MenuPreset.slotChunk1;
    private static final int[] STATUS_BORDER = MenuPreset.slotChunk2;
    private static final int[] OUTPUT_BORDER = MenuPreset.slotChunk3;

    public StorageUnit(@Nonnull Type type) {
        super(Categories.STORAGE_TRANSPORT, type.getItem(), RecipeTypes.STORAGE_FORGE, StorageForge.RECIPES[type.ordinal()]);
        this.type = type;

        addItemHandler(new BlockPlaceHandler(false) { //transfer stuffs
            @Override
            public void onPlayerPlace(BlockPlaceEvent e) {
                Block b = e.getBlock();
                setAmount(b, 0);
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
                                setStored(b, storedItem);

                                String stored = ChatColor.stripColor(lore.get(i + 3));
                                setAmount(b, Integer.parseInt(stored));

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
            String storedItem = setStored(b);
            int stored = getAmount(b);

            e.setDropItems(false);
            ItemStack drop = type.getItem().clone();

            if (stored > 0 && storedItem != null) {
                tryToStoreOrDrop(b, inv, INPUT_SLOTS);
                tryToStoreOrDrop(b, inv, OUTPUT_SLOTS);

                stored = getAmount(b);

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

        int stored = getAmount(b);
        String storedItem = setStored(b);
        String inputID = StackUtils.getIDorElse(inputSlot, inputSlot.getType().toString());

        if (Objects.equals(inputID, storedItem)) {

            int inputAmount = inputSlot.getAmount();
            if (this.type.getMax() >= stored + inputAmount) {

                setAmount(b, stored + inputAmount);
                inv.replaceExistingItem(slots[0], null);

            } else {

                int amount = this.type.getMax() - stored;

                setAmount(b, this.type.getMax());
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
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {

    }
    
    @Override
    public void tick(@Nonnull Block b, @Nonnull BlockMenu menu) {
        String stored = setStored(b);
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
                    int max = this.type.max - amount;
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
                    menu.replaceExistingItem(OUTPUT_SLOTS[0], output, false);
                    amount = 0;
                    stored = null;
                    output = null;
                } else {
                    output.setAmount(remove);
                    ItemStack remaining = menu.pushItem(output, OUTPUT_SLOTS);
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
                        "&6Stored: &e" + LorePreset.format(amount) + (this.type == Type.INFINITE_ ? "" : "/" + LorePreset.format(this.type.max) + " &7(" + Math.round((float) 100 * amount / this.type.max)  + "%)"),
                        "&7Stacks: " + LorePreset.format(Math.round((float) amount / output.getMaxStackSize()))
                );
                status.setAmount(1);
                menu.replaceExistingItem(STATUS_SLOT, status);
            }
        }
        
        // update signs
        if (DISPLAY_SIGNS && InfinityExpansion.progressEvery(SIGN_REFRESH)) {
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
        return !(item instanceof SlimefunBackpack) || !(item instanceof LoreStorage);
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

    public String setStored(@Nonnull Block b) {
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
        BASIC_(Items.BASIC_STORAGE, BASIC),
        ADVANCED_(Items.ADVANCED_STORAGE, ADVANCED),
        REINFORCED_(Items.REINFORCED_STORAGE, REINFORCED),
        VOID_(Items.VOID_STORAGE, VOID),
        INFINITE_(Items.INFINITY_STORAGE, INFINITY);

        @Nonnull
        private final SlimefunItemStack item;
        private final int max;
    }
}
