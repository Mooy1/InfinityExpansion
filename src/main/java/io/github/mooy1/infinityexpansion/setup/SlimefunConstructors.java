package io.github.mooy1.infinityexpansion.setup;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.InfinityRecipes;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.lists.RecipeTypes;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.Capacitor;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.AutoDisenchanter;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.AutoEnchanter;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.ChargingBench;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.reactors.Reactor;
import io.github.thebusybiscuit.slimefun4.utils.holograms.ReactorHologram;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineFuel;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;

import javax.annotation.Nonnull;

public class SlimefunConstructors {

    public static int INFINITY_CAPACITOR = 1600000000;
    public static int VOID_CAPACITOR = 16000000;

    public static int ADVANCED_EN_SPEED = 5;
    public static int ADVANCED_EN_ENERGY = 180;

    public static int ADVANCED_DIS_SPEED = 5;
    public static int ADVANCED_DIS_ENERGY = 180;

    public static int INFINITY_EN_SPEED = 75;
    public static int INFINITY_EN_ENERGY = 12000;

    public static int INFINITY_DIS_SPEED = 90;
    public static int INFINITY_DIS_ENERGY = 12000;

    public static int ADVANCED_CHARGER_SPEED = 30;
    public static int ADVANCED_CHARGER_ENERGY = 180;

    public static int INFINITY_CHARGER_SPEED = 6000;
    public static int INFINITY_CHARGER_ENERGY = 60000;
    
    public static int STAR_ENERGY = 1800;
    public static int STAR_BUFFER = 90000;
    
    public static void setup(InfinityExpansion plugin) {
        
        new Capacitor(Categories.INFINITY_CHEAT, INFINITY_CAPACITOR, Items.INFINITY_CAPACITOR,
                RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(Items.INFINITY_CAPACITOR)).register(plugin);

        new Capacitor(Categories.ADVANCED_MACHINES, VOID_CAPACITOR, Items.VOID_CAPACITOR,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.VOID_INGOT, Items.REDSTONE_SINGULARITY, Items.VOID_INGOT,
                Items.VOID_INGOT, SlimefunItems.ENERGIZED_CAPACITOR, Items.VOID_INGOT,
                Items.VOID_INGOT, Items.REDSTONE_SINGULARITY, Items.VOID_INGOT
        }).register(plugin);

        new AutoEnchanter(Categories.ADVANCED_MACHINES, Items.ADVANCED_ENCHANTER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                Items.MAGSTEEL_PLATE, SlimefunItems.AUTO_ENCHANTER, Items.MAGSTEEL_PLATE,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }) {
            @Override
            public ItemStack getProgressBar() {
                return new ItemStack(Material.NETHERITE_CHESTPLATE);
            }

            @Override
            public int getEnergyConsumption() {
                return ADVANCED_EN_ENERGY;
            }

            @Override
            public int getCapacity() {
                return ADVANCED_EN_ENERGY;
            }

            @Override
            public int getSpeed() {
                return ADVANCED_EN_SPEED;
            }

        }.register(plugin);

        new AutoDisenchanter(Categories.ADVANCED_MACHINES, Items.ADVANCED_DISENCHANTER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                Items.MAGSTEEL_PLATE, SlimefunItems.AUTO_DISENCHANTER, Items.MAGSTEEL_PLATE,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }) {
            @Override
            public ItemStack getProgressBar() {
                return new ItemStack(Material.ENCHANTED_BOOK);
            }

            @Override
            public int getEnergyConsumption() {
                return ADVANCED_DIS_ENERGY;
            }

            @Override
            public int getCapacity() {
                return ADVANCED_DIS_ENERGY;
            }

            @Override
            public int getSpeed() {
                return ADVANCED_DIS_SPEED;
            }


        }.register(plugin);

        new AutoEnchanter(Categories.INFINITY_CHEAT, Items.INFINITY_ENCHANTER, RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(Items.INFINITY_ENCHANTER)) {
            @Override
            public ItemStack getProgressBar() {
                return new ItemStack(Material.NETHERITE_CHESTPLATE);
            }

            @Override
            public int getEnergyConsumption() {
                return INFINITY_EN_ENERGY;
            }

            @Override
            public int getCapacity() {
                return INFINITY_EN_ENERGY;
            }

            @Override
            public int getSpeed() {
                return INFINITY_EN_SPEED;
            }

        }.register(plugin);

        new AutoDisenchanter(Categories.INFINITY_CHEAT, Items.INFINITY_DISENCHANTER, RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(Items.INFINITY_DISENCHANTER)) {
            @Override
            public ItemStack getProgressBar() {
                return new ItemStack(Material.ENCHANTED_BOOK);
            }

            @Override
            public int getEnergyConsumption() {
                return INFINITY_DIS_ENERGY;
            }

            @Override
            public int getCapacity() {
                return INFINITY_DIS_ENERGY;
            }

            @Override
            public int getSpeed() {
                return INFINITY_DIS_SPEED;
            }

        }.register(plugin);

        new ChargingBench(Categories.ADVANCED_MACHINES, Items.ADVANCED_CHARGER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.MAGSTEEL_PLATE, Items.MACHINE_CIRCUIT, Items.MAGSTEEL_PLATE,
                Items.MACHINE_CIRCUIT, SlimefunItems.CHARGING_BENCH, Items.MACHINE_CIRCUIT,
                Items.MAGSTEEL_PLATE, Items.MACHINE_CORE, Items.MAGSTEEL_PLATE,
        }) {
            @Override
            public int getEnergyConsumption() {
                return ADVANCED_CHARGER_ENERGY;
            }

            @Override
            public int getCapacity() {
                return ADVANCED_CHARGER_ENERGY;
            }

            @Override
            public int getSpeed() {
                return ADVANCED_CHARGER_SPEED;
            }

        }.register(plugin);

        new ChargingBench(Categories.INFINITY_CHEAT, Items.INFINITY_CHARGER, RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(Items.INFINITY_CHARGER)) {
            @Override
            public int getEnergyConsumption() {
                return INFINITY_CHARGER_ENERGY;
            }

            @Override
            public int getCapacity() {
                return INFINITY_CHARGER_ENERGY;
            }

            @Override
            public int getSpeed() {
                return INFINITY_CHARGER_SPEED;
            }

        }.register(plugin);
        
        new Reactor(Categories.ADVANCED_MACHINES, Items.ADVANCED_NETHER_STAR_REACTOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.WITHER_PROOF_GLASS, SlimefunItems.WITHER_PROOF_GLASS, SlimefunItems.WITHER_PROOF_GLASS,
                Items.MACHINE_CIRCUIT, SlimefunItems.NETHER_STAR_REACTOR, Items.MACHINE_CIRCUIT,
                SlimefunItems.WITHER_PROOF_OBSIDIAN, SlimefunItems.WITHER_PROOF_OBSIDIAN, SlimefunItems.WITHER_PROOF_OBSIDIAN,
        }){
            
            @Override
            public int getCapacity() {
                return STAR_BUFFER;
            }

            @Override
            public int getEnergyProduction() {
                return STAR_ENERGY;
            }

            @Override
            protected void registerDefaultFuelTypes() {
                registerFuel(new MachineFuel(600, new ItemStack(Material.NETHER_STAR)));
            }

            @Override
            public void extraTick(@Nonnull Location l) {
                if (InfinityExpansion.progressEvery(4)) return;
                
                InfinityExpansion.runSync(() -> {
                    ArmorStand hologram = ReactorHologram.getArmorStand(l, true);
                    if (hologram == null) return;
                    
                    for (Entity entity : hologram.getNearbyEntities(8, 8, 8)) {
                        if (entity instanceof LivingEntity && entity.isValid()) {
                            
                            Location h = hologram.getLocation().clone().add(0, 1, 0);
                            
                            if (h.getBlock().getType() == Material.AIR) {
                                
                                RayTraceResult result = hologram.getWorld().rayTraceBlocks(h, entity.getLocation().toVector().subtract(h.toVector()), 16);

                                if (result != null) {
                                    Block hit = result.getHitBlock();

                                    if (hit != null) {
                                        String id = BlockStorage.getLocationInfo(hit.getLocation(), "id");

                                        if (id != null && id.contains("WITHER_PROOF")) {
                                            continue;
                                        }
                                    }
                                }
                            }
                            
                            ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1));
                        }
                    }
                });
            }

            @Override
            public ItemStack getCoolant() {
                return SlimefunItems.NETHER_ICE_COOLANT_CELL;
            }

            @Nonnull
            @Override
            public ItemStack getFuelIcon() {
                return new ItemStack(Material.NETHER_STAR);
            }

            @Nonnull
            @Override
            public ItemStack getProgressBar() {
                return new ItemStack(Material.NETHER_STAR);
            }

        }.register(plugin);
    }
}
