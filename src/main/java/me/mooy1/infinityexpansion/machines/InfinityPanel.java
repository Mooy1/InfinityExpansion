package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
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
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InfinityPanel extends SlimefunItem implements EnergyNetProvider, InventoryBlock {

    private final InfinityPanel.Panel panel;

    public InfinityPanel(Panel panel) {
        super(Categories.INFINITY_MACHINES, panel.getItem(), panel.getRecipeType(), panel.getRecipe());
        this.panel = panel;

        setupInv();
    }

    public void setupInv() {
        createPreset(this, panel.getItem().getImmutableMeta().getDisplayName().orElse("&7THIS IS A BUG"),
            blockMenuPreset -> {
                for (int i = 0; i < 9; i++)
                    blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());

                blockMenuPreset.addItem(4,
                    new CustomItem(Material.RED_STAINED_GLASS_PANE,
                        "&cNot Generating..."),
                    ChestMenuUtils.getEmptyClickHandler());
            });
    }

    public int getGeneratedOutput(@Nonnull Location l, @Nonnull Config data) {
        @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
        if (inv == null) return 0;

        final int stored = getCharge(l);
        final boolean canGenerate = stored < getCapacity();
        final int rate = canGenerate ? getGeneratingAmount(inv.getBlock(), l.getWorld()) : 0;

        String generationType = "&cNot Generating";

        if (l.getWorld().getEnvironment() == World.Environment.NETHER) {
            generationType = "&cNether &8(Night)";
        } else if (l.getWorld().getEnvironment() == World.Environment.THE_END) {
            generationType = "&5End &8(Night)";
        } else if (rate == this.panel.getDayGenerationRate()) {
            generationType = "&aOverworld &e(Day)";
        } else if (rate == this.panel.getNightGenerationRate()) {
            generationType = "&aOverworld &8(Night)";
        }

        if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
            inv.replaceExistingItem(4,
                canGenerate ? new CustomItem(Material.GREEN_STAINED_GLASS_PANE, "&aGeneration",
                    "", "&bRate: " + generationType,
                    "&7Generating at &6" + rate + " J/s ",
                    "",
                    "&7Stored: &6" + (stored + rate) + " J"
                )
                    : new CustomItem(Material.ORANGE_STAINED_GLASS_PANE, "&cNot Generating",
                    "", "&7Generator has reached maximum capacity.",
                    "", "&7Stored: &6" + stored + " J")
            );
        }

        return rate;
    }

    public int getGeneratingAmount(@Nonnull Block block, @Nonnull World world) {

        if (world.getEnvironment() == World.Environment.NETHER) return this.panel.getDayGenerationRate();
        if (world.getEnvironment() == World.Environment.THE_END) return this.panel.getNightGenerationRate();

        if (world.getTime() >= 13000 || block.getLocation().add(0, 1, 0).getBlock().getLightFromSky() != 15) {
            return this.panel.getNightGenerationRate();
        } else {
            return this.panel.getDayGenerationRate();
        }
    }

    @Override
    public int getCapacity() {
        return this.panel.getCapacity();
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.GENERATOR;
    }

    @Override
    public int[] getInputSlots() {
        return new int[0];
    }

    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Panel {

        CELESTIAL(Items.CELESTIAL_PANEL, 1_800, 0, 300_000, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
            Items.MACHINE_PLATE, SlimefunItems.SOLAR_GENERATOR_4, Items.MACHINE_PLATE,
            Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        VOID(Items.VOID_PANEL, 0, 5_400, 900_000, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.MAGNONIUM_INGOT, Items.MAGNONIUM_INGOT, Items.MAGNONIUM_INGOT,
            Items.MACHINE_PLATE, Items.CELESTIAL_PANEL, Items.MACHINE_PLATE,
            Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(Items.INFINITY_PANEL, 120_000, 120_000, 20_000_000, RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[] {
            Items.CELESTIAL_PANEL, Items.VOID_PANEL, Items.CELESTIAL_PANEL,
            Items.INFINITY_INGOT, Items.INFINITE_CAPACITOR, Items.INFINITY_INGOT,
            Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT,
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final int dayGenerationRate;
        private final int nightGenerationRate;
        private final int capacity;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}