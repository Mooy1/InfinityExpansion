package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.lists.InfinityRecipes;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.lists.RecipeTypes;
import io.github.mooy1.infinityexpansion.utils.PresetUtils;
import io.github.mooy1.infinityexpansion.utils.StackUtils;
import io.github.mooy1.infinityexpansion.utils.TriList;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import io.github.mooy1.infinityexpansion.lists.Categories;
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
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Constructs singularities form many items
 *
 * @author Mooy1
 */
public class SingularityConstructor extends SlimefunItem implements EnergyNetComponent, RecipeDisplayItem {

    public static final int BASIC_ENERGY = 120;
    public static final int BASIC_SPEED = 1;
    public static final int INFINITY_ENERGY = 1200;
    public static final int INFINITY_SPEED = 10;

    private final Type type;
    private static final int STATUS_SLOT = 13;
    private static final int[] INPUT_SLOTS = {
            10
    };
    private static final int INPUT_SLOT = INPUT_SLOTS[0];
    private static final int[] OUTPUT_SLOTS = {
            16
    };
    private static final int OUTPUT_SLOT = OUTPUT_SLOTS[0];
    private static final int[] INPUT_BORDER = {
            0, 1, 2, 9, 11, 18, 19, 20
    };
    private static final int[] STATUS_BORDER = {
            3, 4, 5, 12, 14, 21, 22, 23
    };
    private static final int[] OUTPUT_BORDER = {
            6, 7, 8, 15, 17, 24, 25, 26
    };

    public static final TriList<SlimefunItemStack, String, Integer> RECIPES = new TriList<>();

    static {
        RECIPES.add(Items.COPPER_SINGULARITY, "COPPER_INGOT", 2000);
        RECIPES.add(Items.ZINC_SINGULARITY, "ZINC_INGOT", 4000);
        RECIPES.add(Items.TIN_SINGULARITY, "TIN_INGOT",4000);
        RECIPES.add(Items.ALUMINUM_SINGULARITY, "ALUMINUM_INGOT",4000);
        RECIPES.add(Items.SILVER_SINGULARITY, "SILVER_INGOT",4000);
        RECIPES.add(Items.MAGNESIUM_SINGULARITY, "MAGNESIUM_INGOT",4000);
        RECIPES.add(Items.LEAD_SINGULARITY, "LEAD_INGOT",4000);

        RECIPES.add(Items.GOLD_SINGULARITY, "GOLD_INGOT", 2000);
        RECIPES.add(Items.IRON_SINGULARITY, "IRON_INGOT", 4000);
        RECIPES.add(Items.DIAMOND_SINGULARITY, "DIAMOND", 500);
        RECIPES.add(Items.EMERALD_SINGULARITY, "EMERALD", 500);
        RECIPES.add(Items.NETHERITE_SINGULARITY, "NETHERITE_INGOT", 200);

        RECIPES.add(Items.COAL_SINGULARITY, "COAL", 2000);
        RECIPES.add(Items.REDSTONE_SINGULARITY, "REDSTONE", 1000);
        RECIPES.add(Items.LAPIS_SINGULARITY, "LAPIS_LAZULI",1000);
        RECIPES.add(Items.QUARTZ_SINGULARITY, "QUARTZ",1000);

        RECIPES.add(Items.INFINITY_SINGULARITY, "INFINITE_INGOT",200);
    }

    public SingularityConstructor(Type type) {
        super(type.getCategory(), type.getItem(), type.getRecipeType(), type.getRecipe());
        this.type = type;

        new BlockMenuPreset(getId(), Objects.requireNonNull(type.getItem().getDisplayName())) {
            @Override
            public void init() {
                setupInv(this);
            }

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
                if (getProgress(b) == null) {
                    setProgress(b, 0);
                }
            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return (player.hasPermission("slimefun.inventory.bypass")
                        || SlimefunPlugin.getProtectionManager().hasPermission(
                        player, block.getLocation(), ProtectableAction.ACCESS_INVENTORIES));
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                if (flow == ItemTransportFlow.INSERT) {
                    return INPUT_SLOTS;
                } else if (flow == ItemTransportFlow.WITHDRAW) {
                    return OUTPUT_SLOTS;
                } else {
                    return new int[0];
                }
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
                if (flow == ItemTransportFlow.INSERT) {
                    return INPUT_SLOTS;
                } else if (flow == ItemTransportFlow.WITHDRAW) {
                    return OUTPUT_SLOTS;
                } else {
                    return new int[0];
                }
            }
        };

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                int progress = Integer.parseInt(getBlockData(b.getLocation(), "progress"));
                String inputTest = getProgressID(b);
                Location l = b.getLocation();

                inv.dropItems(l, OUTPUT_SLOTS);
                inv.dropItems(l, INPUT_SLOTS);

                if (progress > 0 && inputTest != null) {

                    String input = RECIPES.getB().get(Integer.parseInt(inputTest));

                    int stackSize = 64;

                    int stacks = (int) Math.floor((float) progress / stackSize);
                    int remainder = progress % stackSize;

                    ItemStack drops = StackUtils.getItemFromID(input, stackSize);

                    if (drops != null) {
                        for (int i = 0; i < stacks; i++) {
                            b.getWorld().dropItemNaturally(l, drops);
                        }
                    }

                    if (remainder > 0) {
                        ItemStack drop = StackUtils.getItemFromID(input, remainder);
                        if (drop != null) {
                            b.getWorld().dropItemNaturally(l, drop);
                        }
                    }
                }
            }

            setProgressID(b, null);
            setProgress(b, 0);

            return true;
        });
    }

    private void setupInv(BlockMenuPreset blockMenuPreset) {
        for (int i : STATUS_BORDER) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : INPUT_BORDER) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : OUTPUT_BORDER) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemBarrier, ChestMenuUtils.getEmptyClickHandler());
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

        String name;
        Material statusMat = Material.BARRIER;

        int progress = Integer.parseInt(getProgress(b));
        ItemStack inputSlotItem = inv.getItemInSlot(INPUT_SLOT);

        int energy = type.getEnergy();

        if (getCharge(b.getLocation()) < energy) { //when not enough power

            name = "&cNot enough energy!";

        } else if (inputSlotItem == null) { //no input
            
            String progressTest = getProgressID(b);

            if (progress == 0 || progressTest == null) { //haven't started

                name = "&9Input a resource!";
                statusMat = Material.BLUE_STAINED_GLASS_PANE;

            } else { //started but wrong input
                
                name = "&cInput more &b" + RECIPES.getB().get(Integer.parseInt(progressTest)) + "&c!";

            }

        } else { //input

            int speed = type.getSpeed();
            
            String progressTest = getProgressID(b);

            if (progress == 0 || progressTest == null) { //no input

                if (checkItemAndSet(b, inv, inputSlotItem, speed)) { //try to start construction

                    removeCharge(b.getLocation(), energy);
                    name = "&aBeginning construction!";
                    statusMat = Material.NETHER_STAR;

                } else { //failed to start construction

                    name = "&cA singularity can't be constructed from this!";

                    if (inv.getItemInSlot(OUTPUT_SLOT) == null) {
                        inv.pushItem(inputSlotItem, OUTPUT_SLOTS);
                        inv.consumeItem(INPUT_SLOT, inputSlotItem.getAmount());
                    }
                }

            } else { //progress

                int progressID = Integer.parseInt(progressTest);
                int outputTime = RECIPES.getC().get(progressID);

                if (progress < outputTime) { //increase progress

                    String input = RECIPES.getB().get(progressID);

                    if (Objects.equals(StackUtils.getIDFromItem(inputSlotItem), input)) { //input matches

                        int inputSlotAmount = inputSlotItem.getAmount();
                        removeCharge(b.getLocation(), energy);

                        if (inputSlotAmount + progress > outputTime) {
                            inputSlotAmount = outputTime - progress;
                        }

                        if (inputSlotAmount >= speed) { //speed

                            setProgress(b, progress + speed);
                            inv.consumeItem(INPUT_SLOT, speed);

                        } else { //less than speed

                            setProgress(b, progress + inputSlotAmount);
                            inv.consumeItem(INPUT_SLOT, inputSlotAmount);

                        }

                        name = "&aConstructing...";
                        statusMat = Material.NETHER_STAR;

                    } else { //input doesnt match

                        name = "&cWrong resource input!";

                        if (inv.getItemInSlot(OUTPUT_SLOT) == null) {
                            inv.pushItem(inputSlotItem, OUTPUT_SLOTS);
                            inv.consumeItem(INPUT_SLOT, inputSlotItem.getAmount());

                        }
                    }

                } else { //if construction done

                    ItemStack output = RECIPES.getA().get(progressID).clone();
                    
                    if (inv.fits(output, OUTPUT_SLOTS)) { //output

                        removeCharge(b.getLocation(), energy);
                        inv.pushItem(output, OUTPUT_SLOTS);
                        setProgress(b, 0);
                        setProgressID(b, null);

                        name = "&aConstruction complete!";
                        statusMat = Material.NETHER_STAR;

                    } else { //not enough room

                        name = "&6Not enough room!";
                        statusMat = Material.ORANGE_STAINED_GLASS_PANE;
                    }
                }
            }
        }

        //update status and finish

        if (inv.hasViewer()) {

            progress = Integer.parseInt(getProgress(b));
            String lore = "";
            String loree = "";

            if (progress > 0) {
                int progressID = Integer.parseInt(getProgressID(b));
                SlimefunItemStack output = RECIPES.getA().get(progressID);
                int outputTime = RECIPES.getC().get(progressID);

                lore = "&7Constructing: " + output.getDisplayName();
                loree = "&7Progress: (" + progress + "/" + outputTime + ")";
            }

            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(statusMat,
                    name,
                    lore,
                    loree
            ));
        }
    }

    /**
     * This method will check the input and add location data if it is valid
     *
     * @param b block
     * @param inv BlockMenu
     * @param item item input
     * @param speed speed of machine
     * @return whether it was successful
     */
    @ParametersAreNonnullByDefault
    private boolean checkItemAndSet(Block b, BlockMenu inv, ItemStack item, int speed) {
        int itemAmount = item.getAmount();

        for (int i = 0; i < RECIPES.size() ; i++) {
            if (Objects.equals(StackUtils.getIDFromItem(item), RECIPES.getB().get(i))) {
                if (itemAmount >= speed) {
                    setProgress(b, speed);
                    inv.consumeItem(INPUT_SLOT, speed);
                } else {
                    setProgress(b, itemAmount);
                    inv.consumeItem(INPUT_SLOT, itemAmount);
                }
                setProgressID(b, String.valueOf(i));
                return true;
            }
        }
        return false;
    }

    private void setProgress(Block b, int progress) {
        setBlockData(b, "progress", String.valueOf(progress));
    }

    private void setProgressID(Block b, String progressID) {
        setBlockData(b, "progressid", progressID);
    }

    private String getProgress(Block b) {
        return getBlockData(b.getLocation(), "progress");
    }

    private String getProgressID(Block b) {
        return getBlockData(b.getLocation(), "progressid");
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
        return type.getEnergy();
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> items = new ArrayList<>();

        for (int i = 0 ; i < RECIPES.size() ; i++) {
            items.add(StackUtils.getItemFromID(RECIPES.getB().get(i), 1));
            items.add(RECIPES.getA().get(i));
        }

        return items;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)

    public enum Type {
        BASIC(Categories.ADVANCED_MACHINES, BASIC_SPEED, BASIC_ENERGY, Items.SINGULARITY_CONSTRUCTOR,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                Items.MACHINE_PLATE, SlimefunItems.CARBON_PRESS_3, Items.MACHINE_PLATE,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(Categories.INFINITY_CHEAT, INFINITY_SPEED, INFINITY_ENERGY,Items.INFINITY_CONSTRUCTOR,
                RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(Items.INFINITY_CONSTRUCTOR));

        @Nonnull
        private final Category category;
        private final int speed;
        private final int energy;
        private final SlimefunItemStack item;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}
