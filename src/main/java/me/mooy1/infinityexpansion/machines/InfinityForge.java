package me.mooy1.infinityexpansion.machines;

import com.sun.org.apache.xerces.internal.xs.StringList;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.utils.ItemUtils;
import me.mooy1.infinityexpansion.utils.MessageUtils;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InfinityForge extends SlimefunItem implements InventoryBlock, EnergyNetComponent {

    public static final RecipeType RECIPE_TYPE = new RecipeType(
            new NamespacedKey(InfinityExpansion.getInstance(), "infinity_forge"), new CustomItem(
            Material.RESPAWN_ANCHOR,
            "&bInfinity &7Forge",
            "&7Used to forge infinity items",
            ""
    ));

    public static int ENERGY = 10_000_000;

    private final int[] INPUT_SLOTS = {
        0, 1, 2, 3, 4, 5,
        9, 10, 11, 12, 13, 14,
        18, 19, 20, 21, 22, 23,
        27, 28, 29, 30, 31, 32,
        36, 37, 38, 39, 40, 41,
        45, 46, 47, 48, 49, 50
    };
    private final int[] OUTPUT_SLOTS = {
        PresetUtils.slot3 + 27
    };
    private final int STATUS_SLOT = PresetUtils.slot3;

    public InfinityForge() {
        super(Categories.ADVANCED_MACHINES, Items.INFINITY_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.VOID_INGOT, Items.MACHINE_PLATE, Items.VOID_INGOT,
                SlimefunItems.ENERGIZED_CAPACITOR, new ItemStack(Material.CRAFTING_TABLE), SlimefunItems.ENERGIZED_CAPACITOR,
                Items.VOID_INGOT, Items.MACHINE_PLATE, Items.VOID_INGOT
        });

        new BlockMenuPreset(getID(), Objects.requireNonNull(Items.INFINITY_FORGE.getDisplayName())) {

            @Override
            public void init() {
                setupInv(this);
            }

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
                menu.addMenuClickHandler(STATUS_SLOT, (p, slot, item, action) -> {
                    craft(b, p);
                    return false;
                });
            }

            @Override
            public boolean canOpen(@Nonnull Block b, @Nonnull Player p) {
                return (p.hasPermission("slimefun.inventory.bypass")
                        || SlimefunPlugin.getProtectionManager().hasPermission(
                        p, b.getLocation(), ProtectableAction.ACCESS_INVENTORIES));
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
                return new int[0];
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
                if (flow == ItemTransportFlow.INSERT) {
                    return new int[0];
                } else if (flow == ItemTransportFlow.WITHDRAW) {
                    return OUTPUT_SLOTS;
                } else {
                    return new int[0];
                }
            }
        };

        registerBlockHandler(getID(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);
            Location l = b.getLocation();

            if (inv != null) {
                inv.dropItems(l, OUTPUT_SLOTS);
                inv.dropItems(l, INPUT_SLOTS);
            }

            return true;
        });
    }

    private void setupInv(BlockMenuPreset blockMenuPreset) {
        for (int i : PresetUtils.slotChunk3) {
            blockMenuPreset.addItem(i + 27, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.slotChunk3) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemBarrier,
                ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) { InfinityForge.this.tick(b); }

            public boolean isSynchronized() { return true; }
        });
    }

    public void tick(Block b) {
        @Nullable final BlockMenu inv = BlockStorage.getInventory(b.getLocation());
        if (inv == null) return;

        if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) { //only active when player watching
            int charge = getCharge(b.getLocation());

            if (charge < ENERGY) { //not enough energy

                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(
                        Material.RED_STAINED_GLASS_PANE,
                        "&cNot enough energy!",
                        "",
                        "&aCharge: " + charge + "/" + ENERGY + " J",
                        ""
                ));

            } else { //enough energy

                ItemStack output = getOutput(inv);

                if (output == null) { //invalid

                    inv.replaceExistingItem(STATUS_SLOT, PresetUtils.invalidRecipe);

                } else if (output.getItemMeta() != null){ //correct recipe

                    ItemMeta meta = output.getItemMeta();
                    List<String> lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.GREEN + "\u21E8 Click to forge");
                    lore.add("");
                    meta.setLore(lore);
                    output.setItemMeta(meta);

                    inv.replaceExistingItem(STATUS_SLOT, output);
                }
            }
        }
    }

    public void craft(Block b, Player p) {
        @Nullable final BlockMenu inv = BlockStorage.getInventory(b.getLocation());
        if (inv == null) return;

        int charge = getCharge(b.getLocation());

        if (charge < ENERGY) { //not enough energy

            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(
                    Material.RED_STAINED_GLASS_PANE,
                    "&cNot enough energy!",
                    "",
                    "&aCharge: " + charge + "/" + ENERGY + " J",
                    ""
            ));

        } else { //enough energy

            ItemStack output = getOutput(inv);

            if (output == null) { //invalid

                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.invalidRecipe);
                MessageUtils.message(p, ChatColor.RED + "Invalid Recipe!");

            } else { //correct recipe

                if (!inv.fits(output, OUTPUT_SLOTS)) { //not enough room

                    inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughRoom);
                    MessageUtils.message(p, ChatColor.GOLD + "Not enough room!");

                } else { //enough room

                    for (int slot : INPUT_SLOTS) {
                        if (inv.getItemInSlot(slot) != null) {
                            inv.consumeItem(slot);
                        }
                    }
                    inv.pushItem(output, OUTPUT_SLOTS);
                    setCharge(b.getLocation(), 0);
                    if (output.getItemMeta() != null) {
                        MessageUtils.message(p, ChatColor.GREEN + "Successfully forged: " + output.getItemMeta().getDisplayName());
                    }
                }
            }
        }
    }


    public ItemStack getOutput(BlockMenu inv) {
        String s1 = ItemUtils.getIDFromItem(inv.getItemInSlot(0));
        String s2 = ItemUtils.getIDFromItem(inv.getItemInSlot(1));
        String s3 = ItemUtils.getIDFromItem(inv.getItemInSlot(2));
        String s4 = ItemUtils.getIDFromItem(inv.getItemInSlot(3));
        String s5 = ItemUtils.getIDFromItem(inv.getItemInSlot(4));
        String s6 = ItemUtils.getIDFromItem(inv.getItemInSlot(5));

        String s7 = ItemUtils.getIDFromItem(inv.getItemInSlot(9));
        String s8 = ItemUtils.getIDFromItem(inv.getItemInSlot(10));
        String s9 = ItemUtils.getIDFromItem(inv.getItemInSlot(11));
        String s10 = ItemUtils.getIDFromItem(inv.getItemInSlot(12));
        String s11 = ItemUtils.getIDFromItem(inv.getItemInSlot(13));
        String s12 = ItemUtils.getIDFromItem(inv.getItemInSlot(14));

        String s13 = ItemUtils.getIDFromItem(inv.getItemInSlot(18));
        String s14 = ItemUtils.getIDFromItem(inv.getItemInSlot(19));
        String s15 = ItemUtils.getIDFromItem(inv.getItemInSlot(20));
        String s16 = ItemUtils.getIDFromItem(inv.getItemInSlot(21));
        String s17 = ItemUtils.getIDFromItem(inv.getItemInSlot(22));
        String s18 = ItemUtils.getIDFromItem(inv.getItemInSlot(23));

        String s19 = ItemUtils.getIDFromItem(inv.getItemInSlot(27));
        String s20 = ItemUtils.getIDFromItem(inv.getItemInSlot(28));
        String s21 = ItemUtils.getIDFromItem(inv.getItemInSlot(29));
        String s22 = ItemUtils.getIDFromItem(inv.getItemInSlot(30));
        String s23 = ItemUtils.getIDFromItem(inv.getItemInSlot(31));
        String s24 = ItemUtils.getIDFromItem(inv.getItemInSlot(32));

        String s25 = ItemUtils.getIDFromItem(inv.getItemInSlot(36));
        String s26 = ItemUtils.getIDFromItem(inv.getItemInSlot(37));
        String s27 = ItemUtils.getIDFromItem(inv.getItemInSlot(38));
        String s28 = ItemUtils.getIDFromItem(inv.getItemInSlot(39));
        String s29 = ItemUtils.getIDFromItem(inv.getItemInSlot(40));
        String s30 = ItemUtils.getIDFromItem(inv.getItemInSlot(41));

        String s31 = ItemUtils.getIDFromItem(inv.getItemInSlot(45));
        String s32 = ItemUtils.getIDFromItem(inv.getItemInSlot(46));
        String s33 = ItemUtils.getIDFromItem(inv.getItemInSlot(47));
        String s34 = ItemUtils.getIDFromItem(inv.getItemInSlot(48));
        String s35 = ItemUtils.getIDFromItem(inv.getItemInSlot(49));
        String s36 = ItemUtils.getIDFromItem(inv.getItemInSlot(50));

        String[] inputs = {
                s1, s2, s3, s4, s5, s6,
                s7, s8, s9, s10, s11, s12,
                s13, s14, s15, s16, s17, s18,
                s19, s20, s21, s22, s23, s24,
                s25, s26, s27, s28, s29, s30,
                s31 ,s32, s33, s34, s35, s36
        };

        for (int ii = 0 ; ii < RECIPES.length ; ii++) {
            int matches = 0;
            for (int i = 0 ; i < inputs.length ; i++) {
                if (inputs[i].equals(RECIPES[ii][i])) {
                    matches++;
                }
            }
            if (matches == 36) {
                return ItemUtils.getItemFromID(OUTPUTS[ii], 1);
            }
        }

        return null;
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return ENERGY;
    }

    @Override
    public int[] getInputSlots() {
        return new int[0];
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    public static final String[] OUTPUTS = {
            "INFINITE_PANEL",
            "IRON_INGOT",
            "COBBLESTONE"
    };

    public static final String[][] RECIPES = {
        {
                "VOID_INGOT", "VOID_INGOT", "VOID_INGOT", "VOID_INGOT", "VOID_INGOT", "VOID_INGOT",
                "CELESTIAL_PANEL", "CELESTIAL_PANEL", "CELESTIAL_PANEL", "CELESTIAL_PANEL", "CELESTIAL_PANEL", "CELESTIAL_PANEL",
                "VOID_PANEL", "VOID_PANEL", "VOID_PANEL", "VOID_PANEL", "VOID_PANEL", "VOID_PANEL",
                "INFINITE_MACHINE_CIRCUIT", "INFINITE_MACHINE_CIRCUIT", "INFINITE_MACHINE_CORE", "INFINITE_MACHINE_CORE", "INFINITE_MACHINE_CIRCUIT", "INFINITE_MACHINE_CIRCUIT",
                "INFINITE_MACHINE_CIRCUIT", "INFINITE_MACHINE_CIRCUIT", "INFINITE_MACHINE_CORE", "INFINITE_MACHINE_CORE", "INFINITE_MACHINE_CIRCUIT", "INFINITE_MACHINE_CIRCUIT",
                "INFINITE_INGOT", "INFINITE_INGOT", "INFINITE_INGOT", "INFINITE_INGOT", "INFINITE_INGOT", "INFINITE_INGOT"
        },
        {
                "DIRT", "", "", "", "", "DIRT",
                "", "", "", "", "", "",
                "", "", "", "", "", "",
                "", "", "", "", "", "",
                "", "", "", "", "", "",
                "DIRT", "", "", "", "", "DIRT"
        },
        {
                "COPPER_INGOT", "", "", "", "", "",
                "", "", "", "", "", "",
                "", "", "", "", "", "",
                "", "", "", "", "", "",
                "", "", "", "", "", "",
                "", "", "", "", "", ""
        },
    };
}
