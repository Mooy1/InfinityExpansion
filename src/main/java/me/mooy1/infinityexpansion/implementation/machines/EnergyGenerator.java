package me.mooy1.infinityexpansion.implementation.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNet;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.lists.InfinityRecipes;
import me.mooy1.infinityexpansion.lists.RecipeTypes;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mooy1.infinityexpansion.utils.LoreUtils;
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
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ConcurrentModificationException;
import java.util.Objects;

/**
 * Solar panels and some other basic generators
 *
 * @author Mooy1
 *
 * Thanks to
 * @author J3fftw1
 * for some stuff to work off of
 *
 */
public class EnergyGenerator extends SlimefunItem implements EnergyNetProvider {

    public static final int WATER_RATE = 9;
    public static final int WATER_STORAGE = 1_500;
    public static final int WATER_RATE2 = 90;
    public static final int WATER_STORAGE2 = 15_000;

    public static final int GEO_RATE = 210;
    public static final int GEO_STORAGE = 35_000;
    public static final int GEO_RATE2 = 1680;
    public static final int GEO_STORAGE2 = 280_000;

    public static final int BASIC_RATE = 6;
    public static final int BASIC_STORAGE = 1_000;
    public static final int ADVANCED_RATE = 300;
    public static final int ADVANCED_STORAGE = 30_000;
    public static final int CELE_RATE = 1800;
    public static final int CELE_STORAGE = 300_000;
    public static final int VOID_RATE = 5400;
    public static final int VOID_STORAGE = 900_000;
    public static final int INFINITY_RATE = 120000;
    public static final int INFINITY_STORAGE = 20_000_000;

    private final Type type;

    public EnergyGenerator(Type type) {
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
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
                return new int[0];
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
                return new int[0];
            }
        };
    }

    public void setupInv(BlockMenuPreset blockMenuPreset) {
            for (int i = 0; i < 9; i++)
                blockMenuPreset.addItem(i, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());

            blockMenuPreset.addItem(4,
                    PresetUtils.loadingItemRed,
                    ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) {
                EnergyGenerator.this.tick(b);
            }

            public boolean isSynchronized() {
                return true;
            }
        });
    }

    public void tick(Block b) {
        Location l = b.getLocation();

        try {
            if (!SlimefunPlugin.getNetworkManager().getNetworkFromLocation(l, EnergyNet.class).isPresent()) {
                @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
                if (inv == null) return;

                if (!inv.toInventory().getViewers().isEmpty()) {
                    inv.replaceExistingItem(4, PresetUtils.connectToEnergyNet);
                }
            }
        } catch (ConcurrentModificationException ignored) { }
    }

    public int getGeneratedOutput(@Nonnull Location l, @Nonnull Config data) {
        @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
        if (inv == null) return 0;

        final int stored = getCharge(l);
        final boolean canGenerate = stored < getCapacity();
        final int rate = canGenerate ? getGeneratingAmount(inv.getBlock(), Objects.requireNonNull(l.getWorld())) : 0;

        if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {

            if (!SlimefunPlugin.getNetworkManager().getNetworkFromLocation(l, EnergyNet.class).isPresent()) { //not connected

                inv.replaceExistingItem(4, PresetUtils.connectToEnergyNet);

            } else if (rate == 0) {

                inv.replaceExistingItem(4, new CustomItem(
                        Material.GREEN_STAINED_GLASS_PANE,
                        "&cNot generating",
                        "&7Stored: &6" + LoreUtils.format(stored + rate) + " J"
                ));

            } else {

                inv.replaceExistingItem(4, new CustomItem(
                        Material.GREEN_STAINED_GLASS_PANE,
                        "&aGeneration",
                        "&7Type: &6" + getGenerationType(rate, l),
                        "&7Generating: &6" + LoreUtils.roundHundreds(rate * LoreUtils.SERVER_TICK_RATIO) + " J/s ",
                        "&7Stored: &6" + LoreUtils.format(stored + rate) + " J"
                ));
            }
        }

        return rate;
    }

    /**
     * This method gets a string depending on the type of generation
     *
     * @param rate generation rate
     * @param l location of generator
     * @return type of generation
     */
    @Nonnull
    public String getGenerationType(int rate, Location l) {

        if (rate == WATER_RATE) return "Hydroelectric";

        if (rate == INFINITY_RATE) return "Infinity";

        if (rate == GEO_RATE || rate == GEO_RATE2) return "Geothermal";

        if (rate == GEO_RATE * 2 || rate == GEO_RATE2 * 2) return "GeoThermal - Nether";

        if (rate == CELE_RATE || rate == BASIC_RATE || rate == ADVANCED_RATE) return "Day";

        if (rate == VOID_RATE) {
            World.Environment environment = l.getBlock().getWorld().getEnvironment();
            if (environment == World.Environment.NORMAL) return "Day";
            if (environment == World.Environment.THE_END) return "End";
            if (environment == World.Environment.NETHER) return "Nether";
        }

        return "";
    }

    /**
     * This method gets the output of the generator depending on certain circumstances
     *
     * @param block block of generator
     * @param world world of generator
     * @return amount to generate
     */
    private int getGeneratingAmount(@Nonnull Block block, @Nonnull World world) {
        int generation = type.getGeneration();

        if (type == Type.WATER || type == Type.WATER2) {
            BlockData blockData = block.getBlockData();

            if (blockData instanceof Waterlogged) {
                Waterlogged waterLogged = (Waterlogged) blockData;
                if (waterLogged.isWaterlogged()) {
                    return generation;
                }
            }

            return 0;
        }

        if (type == Type.INFINITY) {
            return generation;
        }

        if (world.getEnvironment() == World.Environment.NETHER) {
            if (type == Type.GEOTHERMAL || type == Type.GEOTHERMAL2) {
                return generation * 2;
            }
            if (type == Type.VOID) {
                return generation;
            }

            return 0;
        }

        if (world.getEnvironment() == World.Environment.THE_END) {
            if (type == Type.VOID) {
                return generation;
            }

            return 0;
        }

        if (world.getTime() >= 13000 || block.getLocation().add(0, 1, 0).getBlock().getLightFromSky() != 15) {
            if (type == Type.VOID || type == Type.GEOTHERMAL || type == Type.GEOTHERMAL2) {
                return generation;
            }
        } else {
            if (type == Type.CELESTIAL || type == Type.BASIC || type == Type.ADVANCED) {
                return generation;
            }
        }

        return 0;
    }

    @Override
    public int getCapacity() {
        return type.getStorage();
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.GENERATOR;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        WATER(WATER_RATE, WATER_STORAGE, Categories.BASIC_MACHINES, Items.HYDRO_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.MAGSTEEL, Items.MACHINE_CIRCUIT, Items.MAGSTEEL,
                new ItemStack(Material.BUCKET), SlimefunItems.ELECTRO_MAGNET, new ItemStack(Material.BUCKET),
                Items.MAGSTEEL, Items.MACHINE_CIRCUIT, Items.MAGSTEEL
        }),
        WATER2(WATER_RATE2, WATER_STORAGE2, Categories.ADVANCED_MACHINES, Items.REINFORCED_HYDRO_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.HYDRO_GENERATOR, Items.MACHINE_CIRCUIT, Items.HYDRO_GENERATOR,
                Items.MAGSTEEL_PLATE, Items.MACHINE_CORE, Items.MAGSTEEL_PLATE,
                Items.HYDRO_GENERATOR, Items.MACHINE_CIRCUIT, Items.HYDRO_GENERATOR
        }),
        GEOTHERMAL(GEO_RATE, GEO_STORAGE, Categories.ADVANCED_MACHINES, Items.GEOTHERMAL_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE,
                SlimefunItems.LAVA_GENERATOR_2, SlimefunItems.LAVA_GENERATOR_2, SlimefunItems.LAVA_GENERATOR_2,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        GEOTHERMAL2(GEO_RATE2, GEO_STORAGE2, Categories.ADVANCED_MACHINES, Items.REINFORCED_GEOTHERMAL_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.GEOTHERMAL_GENERATOR, Items.MACHINE_CIRCUIT, Items.GEOTHERMAL_GENERATOR,
                Items.MACHINE_PLATE, Items.MACHINE_CORE, Items.MACHINE_PLATE,
                Items.GEOTHERMAL_GENERATOR, Items.MACHINE_CIRCUIT, Items.GEOTHERMAL_GENERATOR
        }),
        BASIC(BASIC_RATE, BASIC_STORAGE, Categories.BASIC_MACHINES, Items.BASIC_PANEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.MAGSTEEL, Items.MAGSTEEL_PLATE, Items.MAGSTEEL,
                SlimefunItems.SOLAR_PANEL, SlimefunItems.SOLAR_PANEL, SlimefunItems.SOLAR_PANEL,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT
        }),
        ADVANCED(ADVANCED_RATE, ADVANCED_STORAGE, Categories.ADVANCED_MACHINES, Items.ADVANCED_PANEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.TITANIUM, Items.MACHINE_PLATE, Items.TITANIUM,
                Items.BASIC_PANEL, SlimefunItems.SOLAR_GENERATOR_4, Items.BASIC_PANEL,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT
        }),
        CELESTIAL(CELE_RATE, CELE_STORAGE, Categories.ADVANCED_MACHINES, Items.CELESTIAL_PANEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.MACHINE_PLATE, Items.MACHINE_PLATE, Items.MACHINE_PLATE,
                Items.ADVANCED_PANEL, Items.ADVANCED_PANEL, Items.ADVANCED_PANEL,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        VOID(VOID_RATE, VOID_STORAGE, Categories.ADVANCED_MACHINES, Items.VOID_PANEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.VOID_INGOT, Items.VOID_INGOT, Items.VOID_INGOT,
                Items.MAGNONIUM, Items.CELESTIAL_PANEL, Items.MAGNONIUM,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(INFINITY_RATE, INFINITY_STORAGE, Categories.INFINITY_CHEAT, Items.INFINITE_PANEL, RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(Items.INFINITE_PANEL));

        private final int generation;
        private final int storage;
        @Nonnull
        private final Category category;
        private final SlimefunItemStack item;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}