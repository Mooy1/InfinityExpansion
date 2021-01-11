package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.implementation.materials.Singularity;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.InfinityRecipes;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.lists.RecipeTypes;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.objects.AbstractContainer;
import io.github.mooy1.infinitylib.presets.MenuPreset;
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
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Constructs singularities form many items
 *
 * @author Mooy1
 */
public class SingularityConstructor extends AbstractContainer implements EnergyNetComponent, RecipeDisplayItem {

    public static final int BASIC_ENERGY = 120;
    public static final int BASIC_SPEED = 1;
    public static final int INFINITY_ENERGY = 1200;
    public static final int INFINITY_SPEED = 32;

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
    
    public SingularityConstructor(Type type) {
        super(type.getCategory(), type.getItem(), type.getRecipeType(), type.getRecipe());
        this.type = type;

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                int progress = Integer.parseInt(getBlockData(b.getLocation(), "progress"));
                String inputTest = getProgressID(b);
                Location l = b.getLocation();

                inv.dropItems(l, OUTPUT_SLOTS);
                inv.dropItems(l, INPUT_SLOTS);

                if (progress > 0 && inputTest != null) {

                    String input = Singularity.RECIPES.get(Integer.parseInt(inputTest)).getB();

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

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        if (getProgress(b) == null) {
            setProgress(b, 0);
        }
    }

    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : STATUS_BORDER) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : INPUT_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : OUTPUT_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.loadingItemBarrier, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        if (flow == ItemTransportFlow.INSERT) {
            return INPUT_SLOTS;
        } else if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        } else {
            return new int[0];
        }
    }

    @Override //TODO REDO THIS 
    public void tick(@Nonnull Block b, @Nonnull BlockMenu inv) {
        String name = "";
        Material statusMat = Material.BARRIER;

        int progress = Integer.parseInt(getProgress(b));
        ItemStack inputSlotItem = inv.getItemInSlot(INPUT_SLOT);

        int energy = this.type.getEnergy();

        if (getCharge(b.getLocation()) < energy) { //when not enough power

            name = "&cNot enough energy!";

        } else if (inputSlotItem == null) { //no input

            String progressTest = getProgressID(b);

            if (progress == 0 || progressTest == null) { //haven't started

                name = "&9Input a resource!";
                statusMat = Material.BLUE_STAINED_GLASS_PANE;

            } else { //started but wrong input

                name = "&cInput more &b" + Singularity.RECIPES.get(Integer.parseInt(progressTest)).getB() + "&c!";

            }

        } else { //input

            int speed = this.type.getSpeed();

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
                int outputTime = Singularity.RECIPES.get(progressID).getC();

                if (progress < outputTime) { //increase progress

                    String input = Singularity.RECIPES.get(progressID).getB();

                    if (Objects.equals(StackUtils.getItemID(inputSlotItem, true), input)) { //input matches

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
                }
                
                progress = Integer.parseInt(getProgress(b));
                
                if (progress >= outputTime) {
                    ItemStack output = Singularity.RECIPES.get(progressID).getA();

                    if (inv.fits(output, OUTPUT_SLOTS)) { //output

                        output = output.clone();

                        removeCharge(b.getLocation(), this.type.getEnergy());
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
                SlimefunItemStack output = Singularity.RECIPES.get(progressID).getA();
                int outputTime = Singularity.RECIPES.get(progressID).getC();

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

        for (int i = 0; i < Singularity.RECIPES.size() ; i++) {
            if (Objects.equals(StackUtils.getItemID(item, true), Singularity.RECIPES.get(i).getB())) {
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
        return this.type.getEnergy() * 2;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> items = new ArrayList<>();

        for (int i = 0 ; i < Singularity.RECIPES.size() ; i++) {
            items.add(StackUtils.getItemFromID(Singularity.RECIPES.get(i).getB(), 1));
            items.add(Singularity.RECIPES.get(i).getA());
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
