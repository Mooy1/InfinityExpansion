package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VoidHarvester extends SlimefunItem implements InventoryBlock, EnergyNetComponent {

    private static final int[] OUTPUTSLOTS = {
        13
    };

    private static final int STATUSSLOT = 4;
    private static final int TIME = 10000;

    private final Type type;

    public VoidHarvester(Type type) {
        super(Categories.INFINITY_MACHINES, type.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, type.getRecipe());
        this.type = type;

        setupInv();

        registerBlockHandler(getID(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), getOutputSlots());
            }

            if (BlockStorage.getLocationInfo(b.getLocation(), "stand") != null) {
                Bukkit.getEntity(UUID.fromString(BlockStorage.getLocationInfo(b.getLocation(), "stand"))).remove();
            }

            return true;
        });
    }

    private void setupInv() {
        createPreset(this, type.getItem().getImmutableMeta().getDisplayName().orElse("&7THIS IS A BUG"),
            blockMenuPreset -> {
                for (int i = 0; i < 13; i++) {
                    blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                }
                for (int i = 14; i < 18; i++) {
                    blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                }

                blockMenuPreset.addItem(STATUSSLOT,
                        new CustomItem(Material.RED_STAINED_GLASS_PANE,
                                "&aLoading..."
                        ),
                        ChestMenuUtils.getEmptyClickHandler());
            });
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) { VoidHarvester.this.tick(b); }

            public boolean isSynchronized() { return false; }
        });
    }

    public void tick(Block b) {

        @Nullable final BlockMenu inv = BlockStorage.getInventory(b.getLocation());
        if (inv == null) return;

        ItemStack output = Items.VOID_BIT;

        if (getBlockData(b.getLocation(), "progress") == null) {
            setProgress(b, 1);
        }

        int progress = Integer.parseInt(getBlockData(b.getLocation(), "progress"));

        String lore = "&7(" + progress + "/" + TIME/type.getSpeed() + ")";

        if (getCharge(b.getLocation()) >= type.getEnergyConsumption()) {

            if (!inv.fits(output, getOutputSlots())) {
                //output slots full
                if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
                    inv.replaceExistingItem(STATUSSLOT, new CustomItem(Material.ORANGE_STAINED_GLASS_PANE,
                            "&cNot enough room!",
                            lore
                    ));
                }
                return;
            }

            if (progress == TIME/type.getSpeed()) {
                //reached full progress

                removeCharge(b.getLocation(), type.getEnergyConsumption());

                inv.pushItem(output, getOutputSlots());

                setProgress(b, 1);

            } else {
                //increase progress

                removeCharge(b.getLocation(), type.getEnergyConsumption());

                setProgress(b, progress+1);

            }
            //harvesting
            if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
                inv.replaceExistingItem(STATUSSLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                        "&aHarvesting...",
                        lore
                ));
            }

        } else {
            //not enough energy
            if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
                inv.replaceExistingItem(STATUSSLOT, new CustomItem(Material.RED_STAINED_GLASS_PANE,
                        "&cNot enough energy!",
                        lore
                ));
            }
        }
    }

    private void setProgress(Block b, int progress) {
        setBlockData(b, "progress", String.valueOf(progress));
    }

    private void setBlockData(Block b, String key, String data) {
        BlockStorage.addBlockInfo(b, key, data);
    }

    private String getBlockData(Location l, String key) {
        return BlockStorage.getLocationInfo(l, key);
    }

    @Override
    public int[] getInputSlots() {
        return new int[0];
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUTSLOTS;
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return type.getEnergyConsumption();
    }

    public static final RecipeType RECIPE_TYPE = new RecipeType(
            new NamespacedKey(InfinityExpansion.getInstance(), "void_harvester"), Items.VOID_HARVESTER
    );

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)

    public enum Type {
        BASIC(Items.VOID_HARVESTER, 1, 1_200, new ItemStack[] {
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                Items.MAGNONIUM_INGOT, SlimefunItems.GEO_MINER, Items.MAGNONIUM_INGOT,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(Items.INFINITE_VOID_HARVESTER, 10, 120_000, new ItemStack[] {
                Items.INFINITE_INGOT, Items.VOID_INGOT, Items.INFINITE_INGOT,
                Items.VOID_INGOT, Items.VOID_HARVESTER, Items.VOID_INGOT,
                Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final int speed;
        private final int energyConsumption;
        private final ItemStack[] recipe;
    }
}

