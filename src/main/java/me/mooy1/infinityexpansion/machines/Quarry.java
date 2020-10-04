package me.mooy1.infinityexpansion.machines;

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

    private final Type type;

    private final int[] OUTPUTSLOTS = new int[] {
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44};
    private final int STATUSSLOT = 4;
    private final RandomizedSet<ItemStack> outputList = new RandomizedSet<>();

    public Quarry(Type type) {
        super(Categories.INFINITY_MACHINES, type.getItem(), type.getRecipeType(), type.getRecipe());
        this.type = type;

        setupInv();

        registerBlockHandler(getID(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), getOutputSlots());
            }

            return true;
        });
    }

    private void setupInv() {
        createPreset(this, type.getItem().getImmutableMeta().getDisplayName().orElse("&7THIS IS A BUG"),
            blockMenuPreset -> {
                for (int i = 0; i < 4; i++) {
                    blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                }
                for (int i = 5; i < 9; i++) {
                    blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                }

                for (int i = 45; i < 54; i++) {
                    blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                }

                blockMenuPreset.addItem(STATUSSLOT,
                    new CustomItem(Material.RED_STAINED_GLASS_PANE,
                        "&aLoading..."),
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
    public void postRegister() {
        super.postRegister();

        int speed = type.getSpeed();
        int cobbleWeight = type.getWeight();
        int weight = (100-type.getWeight())/type.getOutputs();

        //setup outputs

        outputList.add(new ItemStack(Material.COBBLESTONE, speed), cobbleWeight);

        outputList.add(new ItemStack(Material.DIAMOND, speed), weight);
        outputList.add(new ItemStack(Material.EMERALD, speed), weight);
        outputList.add(new ItemStack(Material.REDSTONE, speed*4), weight);
        outputList.add(new ItemStack(Material.LAPIS_LAZULI, speed*4), weight);
        outputList.add(new ItemStack(Material.COAL, speed*2), weight+2);

        if (this.type == Type.BASIC) {
            outputList.add(new ItemStack(Material.IRON_ORE, speed), weight);
            outputList.add(new ItemStack(Material.GOLD_ORE, speed), weight);
        } else  {
            outputList.add(new ItemStack(Material.IRON_INGOT, speed), weight);
            outputList.add(new ItemStack(Material.GOLD_INGOT, speed), weight);
            outputList.add(new ItemStack(Material.QUARTZ, speed*2), weight);
            outputList.add(new ItemStack(Material.NETHERITE_INGOT, speed/2), weight-2);
        }

        if (this.type == Type.VOID) {
            outputList.add(new SlimefunItemStack(SlimefunItems.SIFTED_ORE, speed*4), weight*2);
            outputList.add(new SlimefunItemStack(SlimefunItems.GOLD_24K, speed/4), weight);
        } else if (this.type == Type.INFINITY) {
            outputList.add(new SlimefunItemStack(SlimefunItems.COPPER_INGOT, speed), weight);
            outputList.add(new SlimefunItemStack(SlimefunItems.ZINC_INGOT, speed), weight);
            outputList.add(new SlimefunItemStack(SlimefunItems.TIN_INGOT, speed), weight);
            outputList.add(new SlimefunItemStack(SlimefunItems.SILVER_INGOT, speed), weight);
            outputList.add(new SlimefunItemStack(SlimefunItems.LEAD_INGOT, speed), weight);
            outputList.add(new SlimefunItemStack(SlimefunItems.ALUMINUM_INGOT, speed), weight);
            outputList.add(new SlimefunItemStack(SlimefunItems.MAGNESIUM_INGOT, speed), weight);
            outputList.add(new SlimefunItemStack(SlimefunItems.MAGNESIUM_INGOT, speed), weight);
            outputList.add(new SlimefunItemStack(SlimefunItems.GOLD_24K, speed/4), weight);
        }
    }

    @Override
    public int getCapacity() {
        return type.getEnergyConsumption() * 2;
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

        Location l = b.getLocation();

        @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
        if (inv == null) return;

        ItemStack output = outputList.getRandom();

        if (getCharge(b.getLocation()) >= type.getEnergyConsumption()) {
            BlockMenu menu = BlockStorage.getInventory(b);

            if (!menu.fits(output, getOutputSlots())) {
                if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
                    inv.replaceExistingItem(STATUSSLOT, new CustomItem(Material.ORANGE_STAINED_GLASS_PANE,
                        "&6Not enough room!"));
                }
                return;
            } else {
                if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
                    inv.replaceExistingItem(STATUSSLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                        "&aMining..."));
                }
            }

            removeCharge(b.getLocation(), type.getEnergyConsumption());
            menu.pushItem(output, getOutputSlots());
        } else {
            //when not enough power
            if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
                inv.replaceExistingItem(STATUSSLOT, new CustomItem(Material.RED_STAINED_GLASS_PANE,
                    "&cNot enough energy!"));
            }
        }
    }


    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        BASIC(Items.BASIC_QUARRY, 1, 79, 7, 2_400, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.MACHINE_PLATE, SlimefunItems.LARGE_CAPACITOR, Items.MACHINE_PLATE,
            new ItemStack(Material.DIAMOND_PICKAXE), SlimefunItems.GEO_MINER, new ItemStack(Material.DIAMOND_PICKAXE),
            Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        ADVANCED(Items.ADVANCED_QUARRY, 2,64, 9, 7_200, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.MACHINE_PLATE, SlimefunItems.CARBONADO_EDGED_CAPACITOR, Items.MACHINE_PLATE,
            new ItemStack(Material.NETHERITE_PICKAXE), Items.BASIC_QUARRY, new ItemStack(Material.NETHERITE_PICKAXE),
            Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        VOID(Items.VOID_QUARRY, 4, 40, 12, 45_000, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.VOID_INGOT, SlimefunItems.ENERGIZED_CAPACITOR, Items.VOID_INGOT,
            Items.MAGNONIUM_PICKAXE, Items.ADVANCED_QUARRY, Items.MAGNONIUM_PICKAXE,
            Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(Items.INFINITY_QUARRY, 8, 15, 17, 240_000, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.INFINITE_INGOT, Items.INFINITY_CAPACITOR, Items.INFINITE_INGOT,
            Items.INFINITY_PICKAXE, Items.VOID_QUARRY, Items.INFINITY_PICKAXE,
            Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT
        });


        @Nonnull
        private final SlimefunItemStack item;
        private final int speed;
        private final int weight;
        private final int outputs;
        private final int energyConsumption;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}
