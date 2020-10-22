package me.mooy1.infinityexpansion.implementation.items;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.implementation.guide.ChestSlimefunGuide;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.lists.InfinityRecipes;
import me.mooy1.infinityexpansion.setup.InfinityCategory;
import me.mooy1.infinityexpansion.utils.ItemStackUtils;
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
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InfinityWorkbench extends SlimefunItem implements EnergyNetComponent {

    public static final int ENERGY = 10_000_000;

    public static final int[] INPUT_SLOTS = {
        0, 1, 2, 3, 4, 5,
        9, 10, 11, 12, 13, 14,
        18, 19, 20, 21, 22, 23,
        27, 28, 29, 30, 31, 32,
        36, 37, 38, 39, 40, 41,
        45, 46, 47, 48, 49, 50
    };
    private static final int[] OUTPUT_SLOTS = {
        PresetUtils.slot3 + 27
    };
    private static final int STATUS_SLOT = PresetUtils.slot3;
    private static final int[] STATUS_BORDER = {
            6, 8,
            15, 17,
            24, 25, 26
    };
    private static final int RECIPE_SLOT = 7;

    public InfinityWorkbench() {
        super(Categories.INFINITY_MAIN, Items.INFINITY_WORKBENCH, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.VOID_INGOT, Items.MACHINE_PLATE, Items.VOID_INGOT,
                SlimefunItems.ENERGIZED_CAPACITOR, new ItemStack(Material.CRAFTING_TABLE), SlimefunItems.ENERGIZED_CAPACITOR,
                Items.VOID_INGOT, Items.MACHINE_PLATE, Items.VOID_INGOT
        });

        new BlockMenuPreset(getId(), Objects.requireNonNull(Items.INFINITY_WORKBENCH.getDisplayName())) {

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
                menu.addMenuClickHandler(RECIPE_SLOT, (p, slot, item, action) -> {
                    InfinityCategory.openFromWorkBench(p, menu);
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
                    return new int[0];
                } else {
                    return new int[0];
                }
            }
        };

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
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
        for (int i : STATUS_BORDER) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(RECIPE_SLOT, new CustomItem(Material.BOOK, "&6Recipes"),
                ChestMenuUtils.getEmptyClickHandler());
        blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemBarrier,
                ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) { InfinityWorkbench.this.tick(b); }

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

                } else { //correct recipe

                    inv.replaceExistingItem(STATUS_SLOT, RecipeUtils.getDisplayItem(output));

                }
            }
        }
    }

    public void craft(@Nonnull Block b, @Nonnull  Player p) {
        @Nullable final BlockMenu inv = BlockStorage.getInventory(b.getLocation());
        if (inv == null) return;

        int charge = getCharge(b.getLocation());

        if (charge < ENERGY) { //not enough energy

            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(
                    Material.RED_STAINED_GLASS_PANE,
                    "&cNot enough energy!",
                    "",
                    "&aCharge: &c" + charge + "&a/" + ENERGY + " J",
                    ""
            ));
            MessageUtils.message(p, ChatColor.RED + "Not enough energy!");
            MessageUtils.message(p, ChatColor.GREEN + "Charge: " + ChatColor.RED + charge + ChatColor.GREEN + "/" + ENERGY + " J");

        } else { //enough energy

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
                MessageUtils.message(p, ChatColor.GREEN + "Successfully crafted: " + ChatColor.WHITE + ItemUtils.getItemName(output));

                inv.pushItem(output, OUTPUT_SLOTS);
                setCharge(b.getLocation(), 0);

            }
        }
    }

    @Nullable
    public ItemStack getOutput(@Nonnull BlockMenu inv) {

        String[] input = new String[36];

        for (int i = 0 ; i < 36 ; i++) {
            ItemStack inputItem = inv.getItemInSlot(INPUT_SLOTS[i]);

            input[i] = ItemStackUtils.getIDFromItem(inputItem);
        }

        for (int ii = 0; ii < InfinityRecipes.RECIPES.length ; ii++) {
            int amount = 0;
            for (int i = 0 ; i < input.length ; i++) {
                String recipe = ItemStackUtils.getIDFromItem(InfinityRecipes.RECIPES[ii][i]);
                if (Objects.equals(input[i], recipe)) {
                    amount++;
                } else {
                    break;
                }
            }
            if (amount == 36) {
                return InfinityRecipes.OUTPUTS[ii].clone();
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
}
