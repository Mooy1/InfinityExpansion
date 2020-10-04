package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SingularityConstructor extends SlimefunItem implements InventoryBlock, EnergyNetComponent {

    private final Type type;

    private final int STATUSSLOT = 13;

    private final int[] INPUTSLOTS = {
            10
    };

    private final int INPUTSLOT = INPUTSLOTS[0];

    private final int[] OUTPUTSLOTS = {
            16
    };

    private final int OUTPUTSLOT = OUTPUTSLOTS[0];

    private final int[] INPUTBORDER = {
            0, 1, 2, 9, 11, 18, 19, 20
    };

    private final int[] STATUSBORDER = {
            3, 4, 5, 12, 14, 21, 22, 23
    };

    private final int[] OUTPUTBORDER = {
            6, 7, 8, 15, 17, 24, 25, 26
    };

    private final ItemStack[] inputItems = new ItemStack[] {
            SlimefunItems.COPPER_INGOT,
            SlimefunItems.ZINC_INGOT,
            SlimefunItems.TIN_INGOT,
            SlimefunItems.ALUMINUM_INGOT,
            SlimefunItems.SILVER_INGOT,
            SlimefunItems.MAGNESIUM_INGOT,
            SlimefunItems.LEAD_INGOT,

            SlimefunItems.GOLD_4K,
            new ItemStack(Material.GOLD_INGOT),

            new ItemStack(Material.IRON_INGOT),
            new ItemStack(Material.DIAMOND),
            new ItemStack(Material.EMERALD),
            new ItemStack(Material.NETHERITE_INGOT),

            new ItemStack(Material.COAL),
            new ItemStack(Material.REDSTONE),
            new ItemStack(Material.LAPIS_LAZULI),
            new ItemStack(Material.QUARTZ)
    };

    private final String[] outputItems = new String[] {
            "COPPER_SINGULARITY",
            "ZINC_SINGULARITY",
            "TIN_SINGULARITY",
            "ALUMINUM_SINGULARITY",
            "SILVER_SINGULARITY",
            "MAGNESIUM_SINGULARITY",
            "LEAD_SINGULARITY",

            "GOLD_SINGULARITY",
            "GOLD_SINGULARITY",

            "IRON_SINGULARITY",
            "DIAMOND_SINGULARITY",
            "EMERALD_SINGULARITY",
            "NETHERITE_SINGULARITY",

            "COAL_SINGULARITY",
            "REDSTONE_SINGULARITY",
            "LAPIS_LAZULI_SINGULARITY",
            "QUARTZ_SINGULARITY",
    };

    private final int[] outputTimes = new int[] {
            32,
            32000,
            32000,
            32000,
            32000,
            32000,
            32000,

            32000,
            32000,

            64000,
            8000,
            8000,
            800,

            32000,
            64000,
            64000,
            64000,
    };

    private final ItemStack loadingItem = new CustomItem(
            Material.RED_STAINED_GLASS_PANE,
            "&aLoading...");

    private final ItemStack inputBorderItem = new CustomItem(
            Material.BLUE_STAINED_GLASS_PANE,
            "&9Input");

    private final ItemStack outputBorderItem = new CustomItem(
            Material.ORANGE_STAINED_GLASS_PANE,
            "&6Output");

    public SingularityConstructor(Type type) {
        super(Categories.INFINITY_MACHINES, type.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, type.getRecipe());
        this.type = type;

        setupInv();

        registerBlockHandler(getID(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                String item = getBlockData(b.getLocation(), "item");
                int progress = Integer.parseInt(getBlockData(b.getLocation(), "progress"));

                inv.dropItems(b.getLocation(), getOutputSlots());
                inv.dropItems(b.getLocation(), getInputSlots());

                if (progress > 0 && item != null) {
                    int stacksize = 64;

                    int stacks = (int) Math.floor((float) progress / stacksize);
                    int remainder = progress % stacksize;

                    for (int i = 0; i < stacks; i++) {
                        b.getWorld().dropItemNaturally(b.getLocation(), new CustomItem(getInput(b), stacksize));
                    }
                    if (remainder > 0) {
                        b.getWorld().dropItemNaturally(b.getLocation(), new CustomItem(getInput(b), remainder));
                    }
                }
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
                for (int i : STATUSBORDER) {
                    blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                }
                for (int i : INPUTBORDER) {
                    blockMenuPreset.addItem(i, inputBorderItem, ChestMenuUtils.getEmptyClickHandler());
                }
                for (int i : OUTPUTBORDER) {
                    blockMenuPreset.addItem(i, outputBorderItem, ChestMenuUtils.getEmptyClickHandler());
                }

                blockMenuPreset.addItem(STATUSSLOT, loadingItem, ChestMenuUtils.getEmptyClickHandler());
            });
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) { SingularityConstructor.this.tick(b); }

            public boolean isSynchronized() { return false; }
        });
    }

    public void tick(Block b) {

        @Nullable final BlockMenu inv = BlockStorage.getInventory(b.getLocation());
        if (inv == null) return;

        String item = getBlockData(b.getLocation(), "item");

        //start and fix bugs

        if (getBlockData(b.getLocation(), "progress") == null || item == null) {
            setProgress(b, 0);
            setItem(b, null);
        }

        int progress = Integer.parseInt(getBlockData(b.getLocation(), "progress"));
        item = getBlockData(b.getLocation(), "item");
        String lore = "";
        String loree = "";

        if (progress > 0) {
            lore = "&7Contructing: " + SlimefunItem.getByID(item).getItem().getItemMeta().getDisplayName();
            loree = "&7(" + progress + "/" + getTime(b) + ")";
        }

        ItemStack inputitem = inv.getItemInSlot(INPUTSLOT);

        if (inputitem != null) {
            if (getCharge(b.getLocation()) >= type.getEnergyConsumption()) {
                if (progress == 0) { //start new singularity
                    if (getOutput(b, inputitem) != null) {
                        setItem(b, inputToOutput(inputitem));
                        setProgress(b, 1);
                        removeCharge(b.getLocation(), type.getEnergyConsumption());

                        if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
                            inv.replaceExistingItem(STATUSSLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                                    lore,
                                    loree
                            ));
                        }
                    } else {
                        //not started
                        if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
                            inv.replaceExistingItem(STATUSSLOT, new CustomItem(Material.BLUE_STAINED_GLASS_PANE,
                                    "&9Input an item to start construction!"
                            ));
                        }
                    }
                } else if (progress == getTime(b)){ //output singularity
                    if (!inv.fits(SlimefunItem.getByID(item).getItem(), getOutputSlots())) {

                        removeCharge(b.getLocation(), type.getEnergyConsumption());
                        inv.pushItem(SlimefunItem.getByID(item).getItem(), getOutputSlots());
                        setProgress(b, 0);
                        setItem(b, null);

                    } else {
                        //not enough room
                        if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
                            inv.replaceExistingItem(STATUSSLOT, new CustomItem(Material.ORANGE_STAINED_GLASS_PANE,
                                    "&6Not enough room!",
                                    lore,
                                    loree
                            ));
                        }
                    }
                } else { //increase progress
                    if (inputitem == getInput(b)) {
                        removeCharge(b.getLocation(), type.getEnergyConsumption());
                        inv.consumeItem(INPUTSLOT, 1);
                        setProgress(b, progress + 1);

                        if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
                            inv.replaceExistingItem(STATUSSLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                                    lore,
                                    loree
                            ));
                        }
                    }
                }
            }
            //when not enough power
            if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
                inv.replaceExistingItem(STATUSSLOT, new CustomItem(Material.RED_STAINED_GLASS_PANE,
                        "&cNot enough energy!",
                        lore,
                        loree
                ));
            }
        }
    }

    private int getTime(Block b) {
        return outputToTime(getBlockData(b.getLocation(), "item"));
    }

    private ItemStack getInput(Block b) {
        return outputToInput(getBlockData(b.getLocation(), "item"));
    }

    private ItemStack getOutput(Block b, ItemStack input) {
        if (SlimefunItem.getByID(inputToOutput(input)) != null) {
            return SlimefunItem.getByID(inputToOutput(input)).getItem();
        }
        return null;
    }

    private int outputToTime(String output) {
        if (output != null) {
            for (int i = 0;i < outputItems.length; i++) {
                if (outputItems[i].equals(output)) return outputTimes[i];
            }
        }
        return 0;
    }

    private ItemStack outputToInput(String output) {
        if (output != null) {
            for (int i = 0;i < outputItems.length; i++) {
                if (outputItems[i].equals(output)) return inputItems[i];
            }
        }
        return null;
    }

    private String inputToOutput(ItemStack input) {
        if (input != null) {
            for (int i = 0;i < inputItems.length; i++) {
                if (inputItems[i] == input) return outputItems[i];
            }
        }
        return null;
    }


    private void setProgress(Block b, int progress) {
        setBlockData(b, "progress", String.valueOf(progress));
    }

    private void setItem(Block b, String item) {
        setBlockData(b, "item", item);
    }

    private void setBlockData(Block b, String key, String data) {
        BlockStorage.addBlockInfo(b, key, data);
    }

    private String getBlockData(Location l, String key) {
        return BlockStorage.getLocationInfo(l, key);
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

    @Override
    public int[] getInputSlots() {
        return INPUTSLOTS;
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUTSLOTS;
    }

    public static final RecipeType SINGULARITY_CONSTRUCTOR = new RecipeType(
            new NamespacedKey(InfinityExpansion.getInstance(), "singularity_contructor"), Items.SINGULARITY_CONSTRUCTOR
    );

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)

    public enum Type {
        BASIC(Items.SINGULARITY_CONSTRUCTOR, 1, 1_000, new ItemStack[] {
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                Items.MACHINE_PLATE, SlimefunItems.CARBON_PRESS_3, Items.MACHINE_PLATE,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(Items.INFINITY_CONSTRUCTOR, 10, 100_000, new ItemStack[] {
                Items.INFINITE_INGOT, Items.MACHINE_PLATE, Items.INFINITE_INGOT,
                Items.MACHINE_PLATE, Items.SINGULARITY_CONSTRUCTOR, Items.MACHINE_PLATE,
                Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final int speed;
        private final int energyConsumption;
        private final ItemStack[] recipe;
    }
}
