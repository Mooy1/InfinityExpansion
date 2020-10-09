package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class Quarry extends SlimefunItem implements InventoryBlock, EnergyNetComponent {
    
    public static int BASIC_SPEED = 1;
    public static int ADVANCED_SPEED = 2;
    public static int VOID_SPEED = 4;
    public static int INFINITY_SPEED = 8;

    public static int BASIC_ENERGY = 2400;
    public static int ADVANCED_ENERGY = 7200;
    public static int VOID_ENERGY = 45000;
    public static int INFINITY_ENERGY = 240000;

    private final Type type;

    private final int[] OUTPUT_SLOTS = {
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44
    };

    private final int STATUS_SLOT = 4;

    public Quarry(Type type) {
        super(Categories.INFINITY_MACHINES, type.getItem(), type.getRecipeType(), type.getRecipe());
        this.type = type;

        new BlockMenuPreset(getID(), Objects.requireNonNull(type.getItem().getDisplayName())) {
            @Override
            public void init() {
                setupInv(this);
            }

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
                if (getNext(b) == null) {
                    setNext(b, 0);
                }
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

            if (inv != null) {
                inv.dropItems(b.getLocation(), getOutputSlots());
            }

            return true;
        });
    }

    private void setupInv(BlockMenuPreset blockMenuPreset) {
        for (int i = 0; i < 4; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i = 5; i < 9; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        for (int i = 45; i < 54; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemRed,
            ChestMenuUtils.getEmptyClickHandler());
    }


    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) { Quarry.this.tick(b); }

            public boolean isSynchronized() { return false; }
        });
    }

    public void tick(Block b) {

        @Nullable final BlockMenu inv = BlockStorage.getInventory(b);
        if (inv == null) return;

        boolean playerWatching = inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty();

        Location l = b.getLocation();
        int energyConsumption = getEnergyConsumption(type);

        if (getCharge(l) < energyConsumption) {

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughEnergy);
            }

        } else {
            int outputID = Integer.parseInt(getNext(b));
            ItemStack outputItem = type.getOutput()[outputID].clone();

            if (!inv.fits(outputItem, getOutputSlots())) {

                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughRoom);
                }

            } else {

                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                            "&aMining..."));
                }
                removeCharge(l, energyConsumption);
                inv.pushItem(outputItem, getOutputSlots());

                if (outputID + 1 == type.getOutput().length) {
                    setNext(b, 0);
                } else {
                    setNext(b, outputID + 1);
                }
            }
        }
    }

    private void setNext(Block b, int next) {
        setBlockData(b, "next", String.valueOf(next));
    }

    private String getNext(Block b) {
        return getBlockData(b.getLocation(), "next");
    }

    private void setBlockData(Block b, String key, String data) {
        BlockStorage.addBlockInfo(b, key, data);
    }

    private String getBlockData(Location l, String key) {
        return BlockStorage.getLocationInfo(l, key);
    }

    @Override
    public int getCapacity() {
        return getEnergyConsumption(type);
    }

    private int getEnergyConsumption(Type type) {
        if (type == Type.BASIC) {
            return BASIC_ENERGY;
        } else if (type == Type.ADVANCED) {
            return ADVANCED_ENERGY;
        } else if (type == Type.VOID) {
            return VOID_ENERGY;
        } else if (type == Type.INFINITY) {
            return INFINITY_ENERGY;
        } else {
            return 0;
        }
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int[] getInputSlots() {
        return new int[0];
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        BASIC(Items.BASIC_QUARRY, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.MACHINE_PLATE, SlimefunItems.LARGE_CAPACITOR, Items.MACHINE_PLATE,
            new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.GEO_MINER, new ItemStack(Material.DIAMOND_PICKAXE),
            Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }, new ItemStack[] {
                new ItemStack(Material.COBBLESTONE, 1),
                new ItemStack(Material.COAL, 4),
                new ItemStack(Material.COBBLESTONE, 1),
                new ItemStack(Material.COBBLESTONE, 1),
                new ItemStack(Material.IRON_ORE, 1),
                new ItemStack(Material.COBBLESTONE, 1),
                new ItemStack(Material.COBBLESTONE, 1),
                new ItemStack(Material.COBBLESTONE, 1),
                new ItemStack(Material.GOLD_ORE, 1),
                new ItemStack(Material.COBBLESTONE, 1),
                new ItemStack(Material.COBBLESTONE, 1),
                new ItemStack(Material.LAPIS_LAZULI, 4),
                new ItemStack(Material.COBBLESTONE, 1),
                new ItemStack(Material.EMERALD, 1),
                new ItemStack(Material.COBBLESTONE, 1),
                new ItemStack(Material.DIAMOND, 1),
                new ItemStack(Material.REDSTONE, 4),
                new ItemStack(Material.COBBLESTONE, 1),
                new ItemStack(Material.COBBLESTONE, 1),
        }),

        ADVANCED(Items.ADVANCED_QUARRY, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.MACHINE_PLATE, SlimefunItems.CARBONADO_EDGED_CAPACITOR, Items.MACHINE_PLATE,
            new ItemStack(Material.NETHERITE_PICKAXE), Items.BASIC_QUARRY, new ItemStack(Material.NETHERITE_PICKAXE),
            Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }, new ItemStack[] {
                new ItemStack(Material.COBBLESTONE, 2),
                new ItemStack(Material.COAL, 8),
                new ItemStack(Material.COBBLESTONE, 2),
                new ItemStack(Material.COBBLESTONE, 2),
                new ItemStack(Material.IRON_INGOT, 2),
                new ItemStack(Material.COBBLESTONE, 2),
                new ItemStack(Material.NETHERRACK, 2),
                new ItemStack(Material.NETHERRACK, 2),
                new ItemStack(Material.QUARTZ, 8),
                new ItemStack(Material.COBBLESTONE, 2),
                new ItemStack(Material.COBBLESTONE, 2),
                new ItemStack(Material.GOLD_INGOT, 2),
                new ItemStack(Material.COBBLESTONE, 2),
                new ItemStack(Material.COBBLESTONE, 2),
                new ItemStack(Material.LAPIS_LAZULI, 8),
                new ItemStack(Material.COBBLESTONE, 2),
                new ItemStack(Material.EMERALD, 2),
                new ItemStack(Material.COBBLESTONE, 2),
                new ItemStack(Material.DIAMOND, 2),
                new ItemStack(Material.REDSTONE, 8),
                new ItemStack(Material.COBBLESTONE, 2),
                new ItemStack(Material.COBBLESTONE, 2),
                new ItemStack(Material.NETHERITE_SCRAP, 2),
        }),

        VOID(Items.VOID_QUARRY, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.VOID_INGOT, SlimefunItems.ENERGIZED_CAPACITOR, Items.VOID_INGOT,
            Items.MAGNONIUM_PICKAXE, Items.ADVANCED_QUARRY, Items.MAGNONIUM_PICKAXE,
            Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }, new ItemStack[] {
                new ItemStack(Material.COBBLESTONE, 4),
                new ItemStack(Material.COAL, 16),
                new ItemStack(Material.COBBLESTONE, 4),
                new SlimefunItemStack(SlimefunItems.SIFTED_ORE, 6),
                new ItemStack(Material.COBBLESTONE, 4),
                new ItemStack(Material.IRON_INGOT, 4),
                new ItemStack(Material.COBBLESTONE, 4),
                new ItemStack(Material.NETHERRACK, 4),
                new ItemStack(Material.NETHERRACK, 4),
                new ItemStack(Material.QUARTZ, 16),
                new ItemStack(Material.COBBLESTONE, 4),
                new ItemStack(Material.COBBLESTONE, 4),
                new ItemStack(Material.GOLD_INGOT, 4),
                new ItemStack(Material.COBBLESTONE, 4),
                new SlimefunItemStack(SlimefunItems.SIFTED_ORE, 6),
                new ItemStack(Material.COBBLESTONE, 4),
                new ItemStack(Material.LAPIS_LAZULI, 46),
                new ItemStack(Material.COBBLESTONE, 4),
                new ItemStack(Material.EMERALD, 4),
                new ItemStack(Material.COBBLESTONE, 4),
                new ItemStack(Material.DIAMOND, 4),
                new ItemStack(Material.REDSTONE, 16),
                new ItemStack(Material.COBBLESTONE, 4),
                new SlimefunItemStack(SlimefunItems.GOLD_24K, 4),
                new ItemStack(Material.COBBLESTONE, 4),
                new ItemStack(Material.NETHERITE_SCRAP, 4),
        }),

        INFINITY(Items.INFINITY_QUARRY, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.INFINITE_INGOT, Items.INFINITY_CAPACITOR, Items.INFINITE_INGOT,
            Items.INFINITY_PICKAXE, Items.VOID_QUARRY, Items.INFINITY_PICKAXE,
            Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT
        }, new ItemStack[] {
                new ItemStack(Material.COBBLESTONE, 8),
                new ItemStack(Material.COAL, 32),
                new ItemStack(Material.COBBLESTONE, 8),
                new ItemStack(Material.COBBLESTONE, 8),
                new ItemStack(Material.IRON_INGOT, 8),
                new ItemStack(Material.COBBLESTONE, 8),
                new ItemStack(Material.NETHERRACK, 8),
                new ItemStack(Material.NETHERRACK, 8),
                new ItemStack(Material.QUARTZ, 32),
                new ItemStack(Material.COBBLESTONE, 8),
                new ItemStack(Material.COBBLESTONE, 8),
                new ItemStack(Material.GOLD_INGOT, 8),
                new ItemStack(Material.COBBLESTONE, 8),
                new SlimefunItemStack(SlimefunItems.MAGNESIUM_INGOT, 8),
                new SlimefunItemStack(SlimefunItems.COPPER_INGOT, 8),
                new SlimefunItemStack(SlimefunItems.ZINC_INGOT, 8),
                new SlimefunItemStack(SlimefunItems.TIN_INGOT, 8),
                new SlimefunItemStack(SlimefunItems.ALUMINUM_INGOT, 8),
                new SlimefunItemStack(SlimefunItems.SILVER_INGOT, 8),
                new SlimefunItemStack(SlimefunItems.LEAD_INGOT, 8),
                new ItemStack(Material.COBBLESTONE, 8),
                new ItemStack(Material.LAPIS_LAZULI, 32),
                new ItemStack(Material.COBBLESTONE, 8),
                new ItemStack(Material.EMERALD, 8),
                new ItemStack(Material.COBBLESTONE, 8),
                new ItemStack(Material.DIAMOND, 8),
                new ItemStack(Material.REDSTONE, 32),
                new ItemStack(Material.COBBLESTONE, 8),
                new SlimefunItemStack(SlimefunItems.GOLD_24K, 8),
                new ItemStack(Material.COBBLESTONE, 8),
                new ItemStack(Material.NETHERITE_SCRAP, 8),
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
        private final ItemStack[] output;
    }
}
