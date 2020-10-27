package me.mooy1.infinityexpansion.implementation.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.lists.Items;
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

/**
 * Machines that generate materials at the cost of energy
 *
 * @author Mooy1
 */
public class MaterialGenerator extends SlimefunItem implements EnergyNetComponent, RecipeDisplayItem {

    public static final int COBBLE_ENERGY = 30;
    public static final int COBBLE2_ENERGY = 120;
    public static final int OBSIDIAN_ENERGY = 360;

    public static final int COBBLE_SPEED = 1;
    public static final int COBBLE2_SPEED = 4;
    public static final int OBSIDIAN_SPEED = 1;

    private static final int[] OUTPUT_SLOTS = {
            13
    };
    private static final int STATUS_SLOT = 4;

    private final Type type;

    public MaterialGenerator(Type type) {
        super(type.getCategory(), type.getItem(), type.getRecipeType(), type.getRecipe());
        this.type = type;

        new BlockMenuPreset(getId(), Objects.requireNonNull(type.getItem().getDisplayName())) {
            @Override
            public void init() {
                setupInv(this);
            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return (player.hasPermission("slimefun.inventory.bypass")
                        || SlimefunPlugin.getProtectionManager().hasPermission(
                        player, block.getLocation(), ProtectableAction.ACCESS_INVENTORIES));
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                if (flow == ItemTransportFlow.WITHDRAW) {
                    return OUTPUT_SLOTS;
                } else {
                    return new int[0];
                }
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
                if (flow == ItemTransportFlow.WITHDRAW) {
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
            }

            return true;
        });
    }

    private void setupInv(BlockMenuPreset  blockMenuPreset) {
        for (int i = 0; i < 13; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i = 14; i < 18; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemRed,
                ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) { MaterialGenerator.this.tick(b); }

            public boolean isSynchronized() { return true; }
        });
    }

    public void tick(Block b) {
        Location l = b.getLocation();
        @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
        if (inv == null) return;

        boolean playerWatching = inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty();

        int energy = type.getEnergy();

        if (getCharge(l) < energy) { //not enough energy

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughEnergy);
            }

        } else {

            ItemStack output = new ItemStack(type.getOutput(), type.getSpeed());

            if (!inv.fits(output, OUTPUT_SLOTS)) {

                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughRoom);
                }

            } else {

                inv.pushItem(output, OUTPUT_SLOTS);

                removeCharge(l, energy);

                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aGenerating..."));
                }
            }
        }
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
        items.add(new ItemStack(type.getOutput(), type.getSpeed()));
        return items;
    }

    @Nonnull
    @Override
    public String getRecipeSectionLabel(@Nonnull Player p) {
        return "&7Generates";
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {
        COBBLE_(Categories.BASIC_MACHINES, COBBLE_ENERGY, Material.COBBLESTONE, COBBLE_SPEED,
                Items.BASIC_COBBLE_GEN, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        Items.MAGSTEEL, new ItemStack(Material.DIAMOND_PICKAXE), Items.MAGSTEEL,
                        new ItemStack(Material.WATER_BUCKET), Items.COMPRESSED_COBBLESTONE_2, new ItemStack(Material.LAVA_BUCKET),
                        Items.MAGSTEEL, Items.MACHINE_CIRCUIT, Items.MAGSTEEL
                }),
        COBBLE_2(Categories.ADVANCED_MACHINES, COBBLE2_ENERGY, Material.COBBLESTONE, COBBLE2_SPEED,
                Items.ADVANCED_COBBLE_GEN, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        Items.MAGSTEEL_PLATE, SlimefunItems.PROGRAMMABLE_ANDROID_MINER, Items.MAGSTEEL_PLATE,
                        new ItemStack(Material.WATER_BUCKET), Items.COMPRESSED_COBBLESTONE_4, new ItemStack(Material.LAVA_BUCKET),
                        Items.MACHINE_CIRCUIT, Items.BASIC_COBBLE_GEN, Items.MACHINE_CIRCUIT
                }),
        OBSIDIAN_(Categories.ADVANCED_MACHINES, OBSIDIAN_ENERGY, Material.OBSIDIAN, OBSIDIAN_SPEED,
                Items.BASIC_OBSIDIAN_GEN, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        SlimefunItems.FLUID_PUMP, SlimefunItems.PROGRAMMABLE_ANDROID_MINER, SlimefunItems.FLUID_PUMP,
                        new ItemStack(Material.DISPENSER), Items.VOID_INGOT, new ItemStack(Material.DISPENSER),
                        Items.MACHINE_CIRCUIT, Items.ADVANCED_COBBLE_GEN, Items.MACHINE_CIRCUIT
                });

        @Nonnull
        private final Category category;
        private final int energy;
        private final Material output;
        private final int speed;
        private final SlimefunItemStack item;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}
