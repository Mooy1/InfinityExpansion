package io.github.mooy1.infinityexpansion.implementation.generators;

import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.InfinityRecipes;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.lists.RecipeTypes;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.objects.AbstractContainer;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNet;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.cscorelib2.collections.Pair;
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
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
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
public class EnergyGenerator extends AbstractContainer implements EnergyNetProvider {

    public static final int WATER_RATE = 9;
    public static final int WATER_STORAGE = 900;
    
    public static final int WATER_RATE2 = 45;
    public static final int WATER_STORAGE2 = 4500;

    public static final int GEO_RATE = 90;
    public static final int GEO_STORAGE = 9000;
    
    public static final int GEO_RATE2 = 450;
    public static final int GEO_STORAGE2 = 45000;

    public static final int BASIC_RATE = 15;
    public static final int BASIC_STORAGE = 1500;
    
    public static final int ADVANCED_RATE = 180;
    public static final int ADVANCED_STORAGE = 18000;
    
    public static final int CELE_RATE = 750;
    public static final int CELE_STORAGE = 75000;
    
    public static final int VOID_RATE = 3600;
    public static final int VOID_STORAGE = 360000;
    
    public static final int INFINITY_RATE = 90000;
    public static final int INFINITY_STORAGE = 9000000;

    private final Type type;

    public EnergyGenerator(Type type) {
        super(type.getCategory(), type.getItem(), type.getRecipeType(), type.getRecipe());
        this.type = type;
    }
    
    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
            for (int i = 0; i < 9; i++)
                blockMenuPreset.addItem(i, MenuPreset.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());

            blockMenuPreset.addItem(4,
                    MenuPreset.loadingItemRed,
                    ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        return new int[0];
    }

    @Override
    public void tick(@Nonnull Block b, @Nonnull BlockMenu inv) {
        try {
            if (!SlimefunPlugin.getNetworkManager().getNetworkFromLocation(b.getLocation(), EnergyNet.class).isPresent()) {
                if (inv.hasViewer()) {
                    inv.replaceExistingItem(4, MenuPreset.connectToEnergyNet);
                }
            }
        } catch (ConcurrentModificationException ignored) { }
    }

    public int getGeneratedOutput(@Nonnull Location l, @Nonnull Config data) {
        @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
        if (inv == null) return 0;
        
        Pair<Integer, String> pair = getGeneratingAmount(l.getBlock(), Objects.requireNonNull(l.getWorld()));
        final int rate = pair.getFirstValue();
                
        if (inv.hasViewer()) {

            final int stored = getCharge(l);

            if (!SlimefunPlugin.getNetworkManager().getNetworkFromLocation(l, EnergyNet.class).isPresent()) { //not connected

                inv.replaceExistingItem(4, MenuPreset.connectToEnergyNet);

            } else if (rate == 0) {

                inv.replaceExistingItem(4, new CustomItem(
                        Material.GREEN_STAINED_GLASS_PANE,
                        "&cNot generating",
                        "&7Stored: &6" + LorePreset.format(stored) + " J"
                ));

            } else {
                
                inv.replaceExistingItem(4, new CustomItem(
                        Material.GREEN_STAINED_GLASS_PANE,
                        "&aGeneration",
                        "&7Type: &6" + pair.getSecondValue(),
                        "&7Generating: &6" + LorePreset.roundHundreds(rate * PluginUtils.TICK_RATIO) + " J/s ",
                        "&7Stored: &6" + LorePreset.format(stored) + " J"
                ));
            }
        }

        return rate;
        
    }
    
    private Pair<Integer, String> getGeneratingAmount(@Nonnull Block block, @Nonnull World world) {
        int generation = this.type.getGeneration();

        if (this.type.isWater()) {
            BlockData blockData = block.getBlockData();

            if (blockData instanceof Waterlogged) {
                Waterlogged waterLogged = (Waterlogged) blockData;
                if (waterLogged.isWaterlogged()) {
                    return new Pair<>(generation, "Hydroelectric");
                }
            }
            
        } else if (this.type == Type.INFINITY) {
            
            return new Pair<>(generation, "Infinity");
            
        } else if (world.getEnvironment() == World.Environment.NETHER) {
            
            if (this.type.isOverworld()) {
                return new Pair<>(generation * 2, "Geothermal - Nether");
            }

            if (this.type.isNight() || this.type.isNether()) {
                return new Pair<>(generation, "Nether");
            }
            
        } else if (world.getEnvironment() == World.Environment.THE_END) {
            
            if (this.type.isEnd() || this.type.isNight()) {
                return new Pair<>(generation, "End");
            }
            
        } else if (world.getEnvironment() == World.Environment.NORMAL) {

            if (this.type.isOverworld()) {
                return new Pair<>(generation, "Geothermal");
            }

            if (world.getTime() >= 13000 || block.getLocation().add(0, 1, 0).getBlock().getLightFromSky() != 15) {

                if (this.type.isNight()) {
                    return new Pair<>(generation, "Night");
                }

            } else if (this.type.isDay()) {

                return new Pair<>(generation, "Day");
            }
        }

        return new Pair<>(0, null);
    }

    @Override
    public int getCapacity() {
        return this.type.getStorage();
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
        }, false, false, false, false, true, false),
        WATER2(WATER_RATE2, WATER_STORAGE2, Categories.ADVANCED_MACHINES, Items.REINFORCED_HYDRO_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.HYDRO_GENERATOR, Items.MACHINE_CIRCUIT, Items.HYDRO_GENERATOR,
                Items.MAGSTEEL_PLATE, Items.MACHINE_CORE, Items.MAGSTEEL_PLATE,
                Items.HYDRO_GENERATOR, Items.MACHINE_CIRCUIT, Items.HYDRO_GENERATOR
        }, false, false, false, false, true, false),
        
        GEOTHERMAL(GEO_RATE, GEO_STORAGE, Categories.ADVANCED_MACHINES, Items.GEOTHERMAL_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE,
                SlimefunItems.LAVA_GENERATOR_2, SlimefunItems.LAVA_GENERATOR_2, SlimefunItems.LAVA_GENERATOR_2,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }, false, false, true, false, false, true),
        GEOTHERMAL2(GEO_RATE2, GEO_STORAGE2, Categories.ADVANCED_MACHINES, Items.REINFORCED_GEOTHERMAL_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.GEOTHERMAL_GENERATOR, Items.MACHINE_CIRCUIT, Items.GEOTHERMAL_GENERATOR,
                Items.MACHINE_PLATE, Items.MACHINE_CORE, Items.MACHINE_PLATE,
                Items.GEOTHERMAL_GENERATOR, Items.MACHINE_CIRCUIT, Items.GEOTHERMAL_GENERATOR
        }, false, false, true, false, false, true),
        
        BASIC(BASIC_RATE, BASIC_STORAGE, Categories.BASIC_MACHINES, Items.BASIC_PANEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.MAGSTEEL, Items.MAGSTEEL_PLATE, Items.MAGSTEEL,
                SlimefunItems.SOLAR_PANEL, SlimefunItems.SOLAR_PANEL, SlimefunItems.SOLAR_PANEL,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT
        }, true, false, false, false, false, false),
        ADVANCED(ADVANCED_RATE, ADVANCED_STORAGE, Categories.ADVANCED_MACHINES, Items.ADVANCED_PANEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.TITANIUM, Items.MACHINE_PLATE, Items.TITANIUM,
                Items.BASIC_PANEL, SlimefunItems.SOLAR_GENERATOR_4, Items.BASIC_PANEL,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT, Items.MACHINE_CIRCUIT
        }, true, false, false, false, false, false),
        
        CELESTIAL(CELE_RATE, CELE_STORAGE, Categories.ADVANCED_MACHINES, Items.CELESTIAL_PANEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.MACHINE_PLATE, Items.MACHINE_PLATE, Items.MACHINE_PLATE,
                Items.ADVANCED_PANEL, Items.ADVANCED_PANEL, Items.ADVANCED_PANEL,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }, true, false, false, false, false, false),
        VOID(VOID_RATE, VOID_STORAGE, Categories.ADVANCED_MACHINES, Items.VOID_PANEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.VOID_INGOT, Items.VOID_INGOT, Items.VOID_INGOT,
                Items.CELESTIAL_PANEL, Items.CELESTIAL_PANEL, Items.CELESTIAL_PANEL,
                Items.MAGNONIUM, Items.MAGNONIUM, Items.MAGNONIUM
        }, false, true, true, true, false, false),
        
        INFINITY(INFINITY_RATE, INFINITY_STORAGE, Categories.INFINITY_CHEAT, Items.INFINITE_PANEL, RecipeTypes.INFINITY_WORKBENCH,
                InfinityRecipes.getRecipe(Items.INFINITE_PANEL), true, true, true, true, false, false);

        private final int generation;
        private final int storage;
        @Nonnull
        private final Category category;
        @Nonnull
        private final SlimefunItemStack item;
        @Nonnull
        private final RecipeType recipeType;
        @Nonnull
        private final ItemStack[] recipe;
        boolean day;
        boolean night;
        boolean nether;
        boolean end;
        boolean water;
        boolean overworld;
    }
}