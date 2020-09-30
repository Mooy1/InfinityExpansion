package me.mooy1.infinityexpansion.Machines;

import com.google.common.collect.ImmutableList;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.RandomizedSet;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Quarry extends SlimefunItem implements InventoryBlock, EnergyNetComponent {

    RandomizedSet<ItemStack> randomizer = new RandomizedSet<>();

    randomizer.add()

    private final Quarry.Tier tier;
    private final int[] OUTPUTSLOTS = new int[] {28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

    public Quarry(Tier tier) {
        super(Categories.INFINITY_MACHINES, tier.getItem(), tier.getRecipeType(), tier.getRecipe());
        this.tier = tier;
        setupInv();
    }

    private void setupInv() {
        createPreset(this, tier.getItem().getImmutableMeta().getDisplayName().orElse("&7THIS IS A BUG"),
                blockMenuPreset -> {
                    for (int i = 0; i < 13; i++) {
                        blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i = 14; i < 28; i++) {
                        blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                    }
                    blockMenuPreset.addItem(35, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                    blockMenuPreset.addItem(36, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                    for (int i = 44; i < 54; i++) {
                        blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                    }
                    blockMenuPreset.addItem(13,
                            new CustomItem(Material.RED_STAINED_GLASS_PANE,
                                    "&cNot Mining..."),
                            ChestMenuUtils.getEmptyClickHandler());
                });
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) { Quarry.this.tick(b); }
            public boolean isSynchronized() { return false; }
        });
    }


    @Override
    public int getCapacity() {
        return tier.getEnergyConsumption()*2;
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
        return OUTPUTSLOTS;
    }

    public void tick(Block b) {

        int speed = tier.getSpeed();

        Location l = b.getLocation();

        @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
        if (inv == null) return;

        ItemStack output = new ItemStack(Material.COBBLESTONE, speed);

        if (getCharge(b.getLocation()) >= tier.getEnergyConsumption()) {
            BlockMenu menu = BlockStorage.getInventory(b);

            if (!menu.fits(output, getOutputSlots())) {
                if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
                    inv.replaceExistingItem(13, new CustomItem(Material.ORANGE_STAINED_GLASS_PANE, "&6Not enough room!"));
                }
                return;
            } else {
                if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
                    inv.replaceExistingItem(13, new CustomItem(Material.GREEN_STAINED_GLASS_PANE, "&aMining..."));
                }
            }

            removeCharge(b.getLocation(), tier.getEnergyConsumption());
            menu.pushItem(output, getOutputSlots());
        } else {
            if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
                inv.replaceExistingItem(13, new CustomItem(Material.RED_STAINED_GLASS_PANE, "&cNot enough energy!"));
            }
        }
    }


    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Tier {

        BASIC(Items.QUARRY, 1, 12, 2_400,RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MACHINE_PLATE, SlimefunItems.LARGE_CAPACITOR, Items.MACHINE_PLATE,
                new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.GEO_MINER, new ItemStack(Material.DIAMOND_PICKAXE),
                Items.MACHINE_CIRCUIT , Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        ADVANCED(Items.ADVANCED_QUARRY, 2, 8, 7_200,RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MACHINE_PLATE, SlimefunItems.CARBONADO_EDGED_CAPACITOR, Items.MACHINE_PLATE,
                new ItemStack(Material.NETHERITE_PICKAXE), Items.QUARRY, new ItemStack(Material.NETHERITE_PICKAXE),
                Items.MACHINE_CIRCUIT , Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        VOID(Items.VOID_QUARRY, 5, 4, 27_000,RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MACHINE_PLATE, SlimefunItems.ENERGIZED_CAPACITOR, Items.MACHINE_PLATE,
            Items.MAGNONIUM_PICKAXE, Items.ADVANCED_QUARRY, Items.MAGNONIUM_PICKAXE,
                Items.MACHINE_CIRCUIT , Items.INFINITE_MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(Items.INFINITY_QUARRY, 25, 1, 240_000, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.INFINITY_INGOT, Items.INFINITE_CAPACITOR, Items.INFINITY_INGOT,
                Items.INFINITY_PICKAXE, Items.VOID_QUARRY, Items.INFINITY_PICKAXE,
                Items.INFINITE_MACHINE_CIRCUIT , Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT
        });


        @Nonnull
        private final SlimefunItemStack item;
        private final int speed;
        private final int ratio;
        private final int energyConsumption;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}
