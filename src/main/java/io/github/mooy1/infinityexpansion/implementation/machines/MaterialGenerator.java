package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.implementation.template.Machine;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.PresetUtils;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Machines that generate materials at the cost of energy
 *
 * @author Mooy1
 */
public class MaterialGenerator extends Machine implements EnergyNetComponent, RecipeDisplayItem {

    public static final int COBBLE_ENERGY = 24;
    public static final int COBBLE2_ENERGY = 120;
    public static final int OBSIDIAN_ENERGY = 240;

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

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), OUTPUT_SLOTS);
            }

            return true;
        });
    }

    public void setupInv(@Nonnull BlockMenuPreset  blockMenuPreset) {
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
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        }

        return new int[0];
    }
    
    @Override
    public void tick(@Nonnull Block b, @Nonnull Location l, @Nonnull BlockMenu inv) {
        boolean playerWatching = inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty();

        int energy = type.getEnergy();

        if (getCharge(l) < energy) { //not enough energy

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughEnergy);
            }
            return;

        }

        ItemStack output = new ItemStack(type.getOutput(), type.getSpeed());

        if (!inv.fits(output, OUTPUT_SLOTS)) {

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughRoom);
            }
            return;

        }

        inv.pushItem(output, OUTPUT_SLOTS);

        removeCharge(l, energy);

        if (playerWatching) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aGenerating..."));
        }
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return type.getEnergy() * 2;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();
        items.add(null);
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
