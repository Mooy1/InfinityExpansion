package me.mooy1.infinityexpansion.implementation.storage;

import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.NonNull;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.utils.StackUtils;
import me.mooy1.infinityexpansion.utils.MessageUtils;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mooy1.infinityexpansion.utils.RecipeUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A crafting machine for upgrading storage units and retaining the stored items
 *
 * @author Mooy1
 */
public class StorageForge extends SlimefunItem implements RecipeDisplayItem {
    public static final ItemStack[][] RECIPES = {
            {
                    Items.MAGSTEEL, Items.MACHINE_CIRCUIT, Items.MAGSTEEL,
                    Items.MAGSTEEL, Items.BASIC_STORAGE, Items.MAGSTEEL,
                    Items.MAGSTEEL, Items.MACHINE_CIRCUIT, Items.MAGSTEEL
            },
            {
                    Items.MAGSTEEL_PLATE, Items.MACHINE_CIRCUIT, Items.MAGSTEEL_PLATE,
                    Items.MAGSTEEL_PLATE, Items.ADVANCED_STORAGE, Items.MAGSTEEL_PLATE,
                    Items.MAGSTEEL_PLATE, Items.MACHINE_PLATE, Items.MAGSTEEL_PLATE
            },
            {
                    Items.VOID_INGOT, Items.MACHINE_PLATE, Items.VOID_INGOT,
                    Items.MAGNONIUM, Items.REINFORCED_STORAGE, Items.MAGNONIUM,
                    Items.VOID_INGOT, Items.MACHINE_CORE, Items.VOID_INGOT
            },
            {
                    Items.INFINITE_INGOT, Items.VOID_INGOT, Items.INFINITE_INGOT,
                    Items.INFINITE_INGOT, Items.VOID_STORAGE, Items.INFINITE_INGOT,
                    Items.INFINITE_INGOT, Items.VOID_INGOT, Items.INFINITE_INGOT
            }
    };
    public static final ItemStack[] OUTPUTS = {
            Items.ADVANCED_STORAGE, Items.REINFORCED_STORAGE, Items.VOID_STORAGE, Items.INFINITY_STORAGE
    };
    public static final int[] INPUT_SLOTS = {
            10, 11, 12,
            19, 20, 21,
            28, 29, 30
    };
    public static final int[] INPUT_BORDER = {
            0, 1, 2, 3, 4,
            9, 13,
            18, 22,
            27, 31,
            36, 37, 38, 39, 40
    };
    public static final int[] OUTPUT_SLOTS = {
            PresetUtils.slot3 + 9
    };
    public static final int[] OUTPUT_BORDER = PresetUtils.slotChunk3;
    public static final int[] BACKGROUND = {
            5, 6, 7, 8,
            41, 42, 43, 44
    };
    public static final int[] STATUS_BORDER = {
            14, 32
    };
    public static final int STATUS_SLOT = 23;

    public StorageForge() {
        super(Categories.STORAGE_TRANSPORT, Items.STORAGE_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.MAGSTEEL, new ItemStack(Material.ANVIL), Items.MAGSTEEL,
                Items.MAGSTEEL, new ItemStack(Material.CRAFTING_TABLE), Items.MAGSTEEL,
                Items.MAGSTEEL, new ItemStack(Material.BARREL), Items.MAGSTEEL,
        });

        new BlockMenuPreset(getId(), Objects.requireNonNull(Items.STORAGE_FORGE.getDisplayName())) {
            @Override
            public void init() {
                setupInv(this);
            }

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
                menu.addMenuClickHandler(STATUS_SLOT, (p, slot, item, action) -> {
                    craft(menu, p);
                    return false;
                });
            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return (player.hasPermission("slimefun.inventory.bypass")
                        || SlimefunPlugin.getProtectionManager().hasPermission(
                        player, block.getLocation(), ProtectableAction.ACCESS_INVENTORIES));
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
                return new int[0];
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
                return new int[0];
            }
        };

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                Location l = b.getLocation();
                inv.dropItems(l, OUTPUT_SLOTS);
                inv.dropItems(l, INPUT_SLOTS);
            }

            return true;
        });
    }

    private void setupInv(BlockMenuPreset blockMenuPreset) {
        for (int slot : INPUT_BORDER) {
            blockMenuPreset.addItem(slot, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : OUTPUT_BORDER) {
            blockMenuPreset.addItem(slot + 9, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : BACKGROUND) {
            blockMenuPreset.addItem(slot, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : STATUS_BORDER) {
            blockMenuPreset.addItem(slot, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemBarrier, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) {
                StorageForge.this.tick(b);
            }

            public boolean isSynchronized() {
                return true;
            }
        });
    }

    public void tick(Block b) {
        Location l = b.getLocation();
        @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
        if (inv == null) return;

        if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
            ItemStack output = getOutput(inv);

            if (output == null) {

                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.invalidRecipe);

            } else {

                inv.replaceExistingItem(STATUS_SLOT, RecipeUtils.getDisplayItem(output));

            }
        }
    }

    /**
     * This method crafts an item and updates the status of the menu
     *
     * @param inv BlockMenu
     * @param p player crafting it
     */
    public void craft(@NonNull BlockMenu inv, @Nonnull Player p) {
        ItemStack output = getOutput(inv);

        if (output == null) { //invalid

            inv.replaceExistingItem(STATUS_SLOT, PresetUtils.invalidRecipe);
            MessageUtils.message(p, ChatColor.RED + "Invalid Recipe!");

        } else if (!inv.fits(output, OUTPUT_SLOTS)) { //not enough room

            inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughRoom);
            MessageUtils.message(p, ChatColor.GOLD + "Not enough room!");

        } else { //enough room

            for (int slot : INPUT_SLOTS) {
                if (inv.getItemInSlot(slot) != null) {
                    inv.consumeItem(slot);
                }
            }
            MessageUtils.message(p, ChatColor.GREEN + "Upgraded to: " + ChatColor.WHITE + ItemUtils.getItemName(output));
            MessageUtils.message(p, ChatColor.GREEN + "Transferred items to upgraded unit");

            inv.pushItem(output, OUTPUT_SLOTS);

        }
    }

    /**
     * This method gets the output from an inventory
     *
     * @param inv inventory to check
     * @return the output if any
     */
    @Nullable
    public ItemStack getOutput(@Nonnull BlockMenu inv) {

        String[] input = new String[9];

        for (int i = 0 ; i < 9 ; i++) {
            ItemStack inputItem = inv.getItemInSlot(INPUT_SLOTS[i]);

            input[i] = StackUtils.getIDFromItem(inputItem);
        }

        for (int ii = 0; ii < StorageForge.RECIPES.length ; ii++) {
            int amount = 0;
            for (int i = 0 ; i < input.length ; i++) {
                String recipe = StackUtils.getIDFromItem(StorageForge.RECIPES[ii][i]);

                if (Objects.equals(input[i], recipe)) {
                    amount++;
                } else {
                    break;
                }
            }

            if (amount == 9) {
                ItemStack output = StorageForge.OUTPUTS[ii].clone();

                transferItems(output, inv.getItemInSlot(INPUT_SLOTS[4]));

                return output;
            }
        }
        return null;
    }

    /**
     * This method transfers the stored item lore from the input to output
     *
     * @param output output item being modified
     * @param input input item
     */
    public static void transferItems(ItemStack output, ItemStack input) {
        StackUtils.transferLore(output, input, "Stored Item:", -1, 5);
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();

        for (ItemStack output : OUTPUTS) {
            items.add(output);
            items.add(null);
        }

        return items;
    }
}
