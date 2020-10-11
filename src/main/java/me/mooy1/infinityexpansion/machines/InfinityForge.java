package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.utils.ItemUtils;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InfinityForge extends SlimefunItem implements InventoryBlock, EnergyNetComponent {

    public static final RecipeType RECIPE_TYPE = new RecipeType(
            new NamespacedKey(InfinityExpansion.getInstance(), "infinity_forge"), Items.INFINITY_FORGE
    );

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
        43
    };
    private final int[] OUTPUT_BORDER = {
            33, 34, 35,
            42,     44,
            51, 52, 53
    };

    private final int STATUS_SLOT = 25;
    private final int[] STATUS_BORDER = {
            6,     8,
            15, 16,17,
            24,    26,
    };
    private final int RECIPE_SLOT = 7;

    public InfinityForge() {
        super(Categories.ADVANCED_MACHINES, Items.INFINITY_FORGE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.VOID_INGOT, Items.MACHINE_PLATE, Items.VOID_INGOT,
                SlimefunItems.ENERGIZED_CAPACITOR, new ItemStack(Material.CRAFTING_TABLE), SlimefunItems.ENERGIZED_CAPACITOR,
                Items.VOID_INGOT, Items.MACHINE_PLATE, Items.VOID_INGOT
        });

        setupInv();

        registerBlockHandler(getID(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);
            Location l = b.getLocation();

            if (inv != null) {
                inv.dropItems(l, getOutputSlots());
                inv.dropItems(l, getInputSlots());
            }

            return true;
        });
    }

    private void setupInv() {
        createPreset(this, Items.INFINITY_FORGE.getDisplayName(),
                blockMenuPreset -> {
                    for (int i : OUTPUT_BORDER) {
                        blockMenuPreset.addItem(i, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : STATUS_BORDER) {
                        blockMenuPreset.addItem(i, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
                    }
                    blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemBarrier,
                            ChestMenuUtils.getEmptyClickHandler());
                    blockMenuPreset.addItem(RECIPE_SLOT, PresetUtils.recipesItem,
                            ChestMenuUtils.getEmptyClickHandler());
                });
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
                        "&aCharge: " + charge + "/" + ENERGY + " J"
                ));

            } else { //enough energy

                ItemStack output = getOutput(inv);

                if (output == null) { //invalid

                    inv.replaceExistingItem(STATUS_SLOT, PresetUtils.invalidRecipe);

                } else { //correct recipe

                    if (!inv.fits(output.clone(), OUTPUT_SLOTS)) { //not enough room

                        inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughRoom);

                    } else { //enough room

                        for (int slot : INPUT_SLOTS) {
                            inv.replaceExistingItem(slot, null);
                        }
                        inv.pushItem(output.clone(), OUTPUT_SLOTS);
                        setCharge(b.getLocation(), 0);
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

        int i = 0;
        for (String[] recipe : RECIPES) {
            if (inputs == recipe) {
                return OUTPUTS[i];
            }
            i++;
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
        return INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    public static final ItemStack[] OUTPUTS = {
            new ItemStack(Material.COBBLESTONE),
            Items.VOID_INGOT,
            null
    };

    public static final String[][] RECIPES = {
            {
                    "", "", "", "", "", "",
                    "", "", "", "", "", "",
                    "", "", "DIRT", "DIRT", "", "",
                    "", "", "DIRT", "DIRT", "", "",
                    "", "", "", "", "", "",
                    "", "", "", "", "", ""
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
                    "DIRT", "", "", "", "", "",
                    "", "", "", "", "", "",
                    "", "", "", "", "", "",
                    "", "", "", "", "", "",
                    "", "", "", "", "", "",
                    "", "", "", "", "", ""
            },
    };
}
