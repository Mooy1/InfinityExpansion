package me.mooy1.infinityexpansion.Machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class Quarry extends SlimefunItem implements InventoryBlock, EnergyNetComponent {

    private final Quarry.Tier tier;
    private final int[] OUTPUTSLOTS = new int[] {28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

    public Quarry(Tier tier) {
        super(Categories.INFINITY_MACHINES, tier.getItem(), tier.getRecipeType(), tier.getRecipe());
        this.tier = tier;
        createPreset(this, tier.getItem().getImmutableMeta().getDisplayName().orElse("THIS IS A BUG REPORT IT ON GITHUB"),
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
                    blockMenuPreset.addItem(13, new CustomItem(Material.RED_STAINED_GLASS_PANE, "&cNot Mining..."));
                });
    }

    @Override
    public int getCapacity() {
        return tier.getCapacity();
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

        ItemStack output = new ItemStack(Material.COBBLESTONE);

        if (getCharge(b.getLocation()) >= tier.getEnergyConsumption()) {
            BlockMenu menu = BlockStorage.getInventory(b);

            if (!menu.fits(output, getOutputSlots())) {
                return;
            }

            removeCharge(b.getLocation(), tier.getEnergyConsumption());
            menu.pushItem(output, getOutputSlots());
        }
    }


    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Tier {

        BASIC(Items.QUARRY, 1, 7_500,125_000, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.STEEL_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.STEEL_PLATE,
                SlimefunItems.REINFORCED_PLATE, Items.MAGNONIUM_INGOT, SlimefunItems.REINFORCED_PLATE,
                SlimefunItems.STEEL_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.STEEL_PLATE
        }),
        ADVANCED(Items.ADVANCED_QUARRY, 4, 27_000,450_000, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            SlimefunItems.STEEL_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.STEEL_PLATE,
                    SlimefunItems.REINFORCED_PLATE, Items.MAGNONIUM_INGOT, SlimefunItems.REINFORCED_PLATE,
                    SlimefunItems.STEEL_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.STEEL_PLATE
        }),
        VOID(Items.VOID_QUARRY, 16, 99_000,1_650_000, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            SlimefunItems.STEEL_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.STEEL_PLATE,
                    SlimefunItems.REINFORCED_PLATE, Items.MAGNONIUM_INGOT, SlimefunItems.REINFORCED_PLATE,
                    SlimefunItems.STEEL_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.STEEL_PLATE
        }),
        INFINITY(Items.INFINITY_QUARRY, 64, 360_000, 6_000_000, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            SlimefunItems.STEEL_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.STEEL_PLATE,
                    SlimefunItems.REINFORCED_PLATE, Items.MAGNONIUM_INGOT, SlimefunItems.REINFORCED_PLATE,
                    SlimefunItems.STEEL_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.STEEL_PLATE
        });


        @Nonnull
        private final SlimefunItemStack item;
        private final int speed;
        private final int energyConsumption;
        private final int capacity;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}
