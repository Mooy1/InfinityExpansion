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
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mooy1.infinityexpansion.utils.LoreUtils;
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

public class Generators extends SlimefunItem implements EnergyNetProvider, InventoryBlock {

    public static int GEO_RATE = 360;
    public static int GEO_STORAGE = 60000;

    public static int CELE_RATE = 1800;
    public static int CELE_STORAGE = 300_000;

    public static int VOID_RATE = 5400;
    public static int VOID_STORAGE = 900_000;

    public static int INFINITY_RATE = 120000;
    public static int INFINITY_STORAGE = 20_000_000;

    private final Type type;

    public Generators(Type type) {
        super(Categories.INFINITY_MACHINES, type.getItem(), type.getRecipeType(), type.getRecipe());
        this.type = type;

        setupInv();
    }

    public void setupInv() {

        createPreset(this, type.getItem().getImmutableMeta().getDisplayName().orElse("&7THIS IS A BUG"),
            blockMenuPreset -> {
                for (int i = 0; i < 9; i++)
                    blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());

                blockMenuPreset.addItem(4,
                    PresetUtils.loadingItemRed,
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
            if (this.type == Type.GEOTHERMAL) {
                generationType = "&cNether - &cGeothermal &7(2x)";
            } else {
                generationType = "&cNether";
            }
        } else if (l.getWorld().getEnvironment() == World.Environment.THE_END) {
            if (this.type == Type.GEOTHERMAL) {
                generationType = "&5End - &cGeothermal &7(0x)";
            } else {
                generationType = "&5End";
            }
        } else if (rate == getDayGenerationRate(type)) {
            if (this.type == Type.GEOTHERMAL) {
                generationType = "&aOverworld - &cGeothermal";
            } else {
                generationType = "&aOverworld - &eDay";
            }
        } else if (rate == getNightGenerationRate(type)) {
            if (this.type == Type.GEOTHERMAL) {
                generationType = "&aOverworld - &cGeothermal";
            } else {
                generationType = "&aOverworld - &8Night";
            }
        }

        if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
            inv.replaceExistingItem(4, new CustomItem(
                    Material.GREEN_STAINED_GLASS_PANE,
                    "&aGeneration",
                    "&bRate: " + generationType,
                    "&7Generating at &6" + LoreUtils.roundHundreds(rate * LoreUtils.SERVER_TICK_RATIO) + " J/s ",
                    "&7Stored: &6" + (stored + rate) + " J"
            ));
        }

        return rate;
    }

    public int getGeneratingAmount(@Nonnull Block block, @Nonnull World world) {

        if (world.getEnvironment() == World.Environment.NETHER) {
            if (this.type == Type.GEOTHERMAL) {
                return getDayGenerationRate(type) * 2;
            } else {
                return getNightGenerationRate(type);
            }
        }

        if (world.getEnvironment() == World.Environment.THE_END) {
            if (this.type == Type.GEOTHERMAL) {
                return 0;
            } else  {
                return getNightGenerationRate(type);
            }
        }

        if (world.getTime() >= 13000 || block.getLocation().add(0, 1, 0).getBlock().getLightFromSky() != 15) {
            return getNightGenerationRate(type);
        } else {
            return getDayGenerationRate(type);
        }
    }

    private int getDayGenerationRate(Type type) {
        if (type == Type.GEOTHERMAL) {
            return GEO_RATE;
        } else if (type == Type.CELESTIAL) {
            return CELE_RATE;
        } else if (type == Type.VOID) {
            return 0;
        } else if (type == Type.INFINITY) {
            return INFINITY_RATE;
        } else {
            return 0;
        }
    }

    private int getNightGenerationRate(Type type) {
        if (type == Type.GEOTHERMAL) {
            return GEO_RATE;
        } else if (type == Type.CELESTIAL) {
            return 0;
        } else if (type == Type.VOID) {
            return VOID_RATE;
        } else if (type == Type.INFINITY) {
            return INFINITY_RATE;
        } else {
            return 0;
        }
    }
    
    private int getStorage(Type type) {
        if (type == Type.GEOTHERMAL) {
            return GEO_STORAGE;
        } else if (type == Type.CELESTIAL) {
            return CELE_STORAGE;
        } else if (type == Type.VOID) {
            return VOID_STORAGE;
        } else if (type == Type.INFINITY) {
            return INFINITY_STORAGE;
        } else {
            return 0;
        }
    }

    @Override
    public int getCapacity() {
        return getStorage(type);
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
    public enum Type {

        GEOTHERMAL(Items.GEOTHERMAL_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE,
            SlimefunItems.LAVA_GENERATOR_2, SlimefunItems.LAVA_GENERATOR_2, SlimefunItems.LAVA_GENERATOR_2,
            Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        CELESTIAL(Items.CELESTIAL_PANEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.MACHINE_PLATE, Items.MACHINE_PLATE, Items.MACHINE_PLATE,
                SlimefunItems.SOLAR_GENERATOR_4, SlimefunItems.SOLAR_GENERATOR_4, SlimefunItems.SOLAR_GENERATOR_4,
            Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        VOID(Items.VOID_PANEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.MAGNONIUM_INGOT, Items.VOID_INGOT, Items.MAGNONIUM_INGOT,
            Items.VOID_INGOT, Items.CELESTIAL_PANEL, Items.VOID_INGOT,
            Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(Items.INFINITE_PANEL, RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack[] {
            Items.CELESTIAL_PANEL, Items.VOID_PANEL, Items.CELESTIAL_PANEL,
            Items.INFINITE_INGOT, Items.INFINITY_CAPACITOR, Items.INFINITE_INGOT,
            Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT,
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}