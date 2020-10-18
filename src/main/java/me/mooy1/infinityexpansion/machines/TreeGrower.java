package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.utils.MathUtils;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TreeGrower extends SlimefunItem implements EnergyNetComponent, RecipeDisplayItem {

    public static final int ENERGY1 = 36;
    public static final int ENERGY2 = 360;
    public static final int ENERGY3 = 7200;
    public static final int SPEED1 = 1;
    public static final int SPEED2 = 4;
    public static final int SPEED3 = 16;

    public static final int TIME = 640;
    private final Type type;

    private static final int[] OUTPUT_SLOTS = PresetUtils.largeOutput;
    private static final int[] INPUT_SLOTS = {
            PresetUtils.slot1 + 27
    };
    private static final int STATUS_SLOT = PresetUtils.slot1;

    public TreeGrower(Type type) {
        super(type.getCategory(), type.getItem(), type.getRecipeType(), type.getRecipe());
        this.type = type;

        new BlockMenuPreset(getId(), Objects.requireNonNull(type.getItem().getDisplayName())) {
            @Override
            public void init() {
                setupInv(this);
            }

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
                if (getProgress(b) == null) {
                    setProgress(b, 0);
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
                    return INPUT_SLOTS;
                } else if (flow == ItemTransportFlow.WITHDRAW) {
                    return OUTPUT_SLOTS;
                } else {
                    return new int[0];
                }
            }
        };

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), OUTPUT_SLOTS);
                inv.dropItems(b.getLocation(), INPUT_SLOTS);

                String progressType = getType(b);
                if (progressType != null) {
                    b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Objects.requireNonNull(Material.getMaterial(progressType + "_SAPLING"))));
                }
            }

            return true;
        });
    }

    private void setupInv(BlockMenuPreset blockMenuPreset) {
        for (int i : PresetUtils.slotChunk1) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.slotChunk1) {
            blockMenuPreset.addItem(i + 27, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.largeOutputBorder) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemRed, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) { TreeGrower.this.tick(b); }

            public boolean isSynchronized() { return false; }
        });
    }

    public void tick(Block b) {
        Location l = b.getLocation();
        @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
        if (inv == null) return;

        int energy = type.getEnergy();
        int charge = getCharge(l);
        boolean playerWatching = inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty();

        if (charge < energy) { //not enough energy

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughEnergy);
            }

        } else {

            int progress = Integer.parseInt(getProgress(b));

            if (progress == 0) { //try to start
                ItemStack input = inv.getItemInSlot(INPUT_SLOTS[0]);

                if (input == null) {

                    if (playerWatching) {
                        inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, "&9Input a sapling"));
                    }

                } else {

                    String inputType = getInputType(input);

                    if (inputType == null) {

                        if (playerWatching) {
                            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cInput a sapling!"));
                        }

                        for (int slot : OUTPUT_SLOTS) {
                            if (inv.getItemInSlot(slot) == null) {
                                inv.replaceExistingItem(slot, input);
                                inv.consumeItem(INPUT_SLOTS[0], input.getAmount());
                                break;
                            }
                        }

                    } else { //start

                        setProgress(b, type.getSpeed());
                        setType(b, inputType);
                        inv.consumeItem(INPUT_SLOTS[0], 1);
                        setCharge(l, charge - energy);

                        if (playerWatching) {
                            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                                    "&aPlanting... (" + type.getSpeed() + "/" + TIME + ")"));
                        }

                    }
                }

            } else if (progress < TIME) { //progress

                setProgress(b, progress + type.getSpeed());
                setCharge(l, charge - energy);

                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                            "&aGrowing... (" + (progress + type.getSpeed()) + "/" + TIME + ")"));
                }

            } else { //done

                String type = getType(b);

                ItemStack output1 = new ItemStack(Objects.requireNonNull(Material.getMaterial(type + "_LOG")), 4 + MathUtils.randomFrom(Math.random(), 6));
                ItemStack output2 = new ItemStack(Objects.requireNonNull(Material.getMaterial(type + "_LEAVES")), 6 + MathUtils.randomFrom(Math.random(), 10));
                ItemStack output3 = new ItemStack(Objects.requireNonNull(Material.getMaterial(type + "_SAPLING")), MathUtils.randomFrom(Math.random(), 2));

                if (!inv.fits(output1, OUTPUT_SLOTS)) {

                    if (playerWatching) {
                        inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughRoom);
                    }

                } else {
                    inv.pushItem(output1, OUTPUT_SLOTS);
                    if (inv.fits(output2, OUTPUT_SLOTS)) inv.pushItem(output2, OUTPUT_SLOTS);
                    if (inv.fits(output3, INPUT_SLOTS)) inv.pushItem(output3, INPUT_SLOTS);

                    if (type.equals("OAK")) {
                        ItemStack apple = new ItemStack(Material.APPLE);
                        if (inv.fits(apple, OUTPUT_SLOTS)) inv.pushItem(apple, OUTPUT_SLOTS);
                    }

                    if (playerWatching) {
                        inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aHarvesting..."));
                    }

                    setProgress(b, 0);
                    setType(b, null);
                    setCharge(l, charge - energy);

                }
            }
        }
    }

    private String getInputType(ItemStack input) {
        for (String recipe : INPUTS) {
            if (input.getType() == Material.getMaterial(recipe + "_SAPLING")) return recipe;
        }
        return null;
    }

    private void setType(Block b, String type) {
        setBlockData(b, "type", type);
    }

    private String getType(Block b) {
        return getBlockData(b.getLocation(), "type");
    }

    private void setProgress(Block b, int progress) {
        setBlockData(b, "progress", String.valueOf(progress));
    }

    private String getProgress(Block b) {
        return getBlockData(b.getLocation(), "progress");
    }

    private void setBlockData(Block b, String key, String data) {
        BlockStorage.addBlockInfo(b, key, data);
    }

    private String getBlockData(Location l, String key) {
        return BlockStorage.getLocationInfo(l, key);
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return type.getEnergy();
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();

        for (String input : INPUTS) {
            items.add(new ItemStack(Objects.requireNonNull(Material.getMaterial(input + "_SAPLING"))));
            items.add(new ItemStack(Objects.requireNonNull(Material.getMaterial(input + "_LOG"))));
        }
        return items;
    }
    
    private static final String[] INPUTS = {
            "OAK",
            "DARK_OAK",
            "ACACIA",
            "SPRUCE",
            "BIRCH",
            "JUNGLE"
    };


    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {
        BASIC(ENERGY1, SPEED1, Categories.BASIC_MACHINES, Items.BASIC_TREE_GROWER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                Items.MAGNONIUM_INGOT, SlimefunItems.GEO_MINER, Items.MAGNONIUM_INGOT,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        ADVANCED(ENERGY2, SPEED2, Categories.ADVANCED_MACHINES, Items.ADVANCED_TREE_GROWER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                Items.MAGNONIUM_INGOT, SlimefunItems.GEO_MINER, Items.MAGNONIUM_INGOT,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(ENERGY3, SPEED3, Categories.INFINITY_MACHINES, Items.INFINITY_TREE_GROWER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.INFINITE_INGOT, Items.VOID_INGOT, Items.INFINITE_INGOT,
                Items.VOID_INGOT, Items.VOID_HARVESTER, Items.VOID_INGOT,
                Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT
        });

        private final int energy;
        private final int speed;
        @Nonnull
        private final Category category;
        private final SlimefunItemStack item;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}
