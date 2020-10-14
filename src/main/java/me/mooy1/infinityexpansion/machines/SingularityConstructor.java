package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.materials.Singularities;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.utils.ItemUtils;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SingularityConstructor extends SlimefunItem implements InventoryBlock, EnergyNetComponent, RecipeDisplayItem {

    public static final RecipeType RECIPE_TYPE = new RecipeType(
            new NamespacedKey(InfinityExpansion.getInstance(), "singularity_constructor"), Items.SINGULARITY_CONSTRUCTOR
    );

    public static int BASIC_ENERGY = 300;
    public static int BASIC_SPEED = 1;
    public static int INFINITY_ENERGY = 30000;
    public static int INFINITY_SPEED = 10;

    private final Type type;
    private final int STATUS_SLOT = 13;
    private final int[] INPUT_SLOTS = {
            10
    };
    private final int INPUT_SLOT = INPUT_SLOTS[0];
    private final int[] OUTPUT_SLOTS = {
            16
    };
    private final int OUTPUT_SLOT = OUTPUT_SLOTS[0];
    private final int[] INPUT_BORDER = {
            0, 1, 2, 9, 11, 18, 19, 20
    };
    private final int[] STATUS_BORDER = {
            3, 4, 5, 12, 14, 21, 22, 23
    };
    private final int[] OUTPUT_BORDER = {
            6, 7, 8, 15, 17, 24, 25, 26
    };
    public static final String[] inputItems = {
            "COPPER_INGOT",
            "ZINC_INGOT",
            "TIN_INGOT",
            "ALUMINUM_INGOT",
            "SILVER_INGOT",
            "MAGNESIUM_INGOT",
            "LEAD_INGOT",

            "GOLD_INGOT",
            "IRON_INGOT",
            "DIAMOND",
            "EMERALD",
            "NETHERITE_INGOT",

            "COAL",
            "REDSTONE",
            "LAPIS_LAZULI",
            "QUARTZ",

            "INFINITE_INGOT",
    };
    public static final String[] outputItems = {
            "COPPER_SINGULARITY",
            "ZINC_SINGULARITY",
            "TIN_SINGULARITY",
            "ALUMINUM_SINGULARITY",
            "SILVER_SINGULARITY",
            "MAGNESIUM_SINGULARITY",
            "LEAD_SINGULARITY",

            "GOLD_SINGULARITY",
            "IRON_SINGULARITY",
            "DIAMOND_SINGULARITY",
            "EMERALD_SINGULARITY",
            "NETHERITE_SINGULARITY",

            "COAL_SINGULARITY",
            "REDSTONE_SINGULARITY",
            "LAPIS_SINGULARITY",
            "QUARTZ_SINGULARITY",

            "INFINITY_SINGULARITY",
    };
    public static final int[] outputTimes = {
            8000,
            8000,
            8000,
            8000,
            8000,
            8000,
            8000,

            8000,
            16000,
            8000,
            8000,
            800,

            8000,
            16000,
            16000,
            16000,
            2880,
    };

    public SingularityConstructor(Type type) {
        super(type.getCategory(), type.getItem(), type.getRecipeType(), type.getRecipe());
        this.type = type;

        new BlockMenuPreset(getID(), Objects.requireNonNull(type.getItem().getDisplayName())) {
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
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
                return new int[0];
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

        registerBlockHandler(getID(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                int progress = Integer.parseInt(getBlockData(b.getLocation(), "progress"));
                String inputTest = getProgressID(b);
                Location l = b.getLocation();

                inv.dropItems(l, getOutputSlots());
                inv.dropItems(l, getInputSlots());

                if (progress > 0 && inputTest != null) {

                    String input = inputItems[Integer.parseInt(getProgressID(b))];

                    int stacksize = 64;

                    int stacks = (int) Math.floor((float) progress / stacksize);
                    int remainder = progress % stacksize;

                    for (int i = 0; i < stacks; i++) {
                        b.getWorld().dropItemNaturally(l, ItemUtils.getItemFromID(input, stacksize));
                    }
                    if (remainder > 0) {
                        b.getWorld().dropItemNaturally(l, ItemUtils.getItemFromID(input, remainder));
                    }
                }
            }
            setProgressID(b, null);
            setProgress(b, 0);

            if (BlockStorage.getLocationInfo(b.getLocation(), "stand") != null) {
                Objects.requireNonNull(Bukkit.getEntity(UUID.fromString(BlockStorage.getLocationInfo(b.getLocation(), "stand")))).remove();
            }

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

        String name = null;
        Material statusMat = Material.BARRIER;

        int progress = Integer.parseInt(getProgress(b));
        ItemStack inputSlotItem = inv.getItemInSlot(INPUT_SLOT);

        if (getCharge(b.getLocation()) < getEnergyConsumption(type)) { //when not enough power

            name = "&cNot enough energy!";

        } else if (inputSlotItem == null) { //no input

            if (progress == 0 || getProgressID(b) == null) { //havent started

                name = "&9Input a resource!";
                statusMat = Material.BLUE_STAINED_GLASS_PANE;

            } else { //started but wrong input

                ItemStack input = ItemUtils.getItemFromID(inputItems[Integer.parseInt(getProgressID(b))], 1);

                if (input.getItemMeta() != null) {
                    if (!input.getItemMeta().getDisplayName().equals("")) { //sf name
                        name = "&cInput more " + me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils.getItemName(input) + "s&c!";
                    } else { //vanilla name
                        name = "&cInput more &f" + me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils.getItemName(input) + "s&c!";
                    }
                }
            }

        } else { //input

            int speed = getSpeed(type);

            if (progress == 0 || getProgressID(b) == null) { //no input

                if (checkItemAndSet(b, inv, inputSlotItem, speed)) { //try to start contruction

                    name = "&aBeggining contruction!";
                    statusMat = Material.NETHER_STAR;

                } else { //failed to start contruction

                    name = "&cA singularity can't be contructed from this!";

                    if (inv.getItemInSlot(OUTPUT_SLOT) == null) {
                        inv.pushItem(inputSlotItem, OUTPUT_SLOTS);
                        inv.consumeItem(INPUT_SLOT, inputSlotItem.getAmount());
                    }
                }

            } else { //progress

                int progressID = Integer.parseInt(getProgressID(b));
                int outputTime = outputTimes[progressID];

                if (progress < outputTime) { //increase progress

                    String input = inputItems[progressID];

                    if (ItemUtils.getIDFromItem(inputSlotItem).equals(input)) { //input matches

                        int inputSlotAmount = inputSlotItem.getAmount();

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

                        name = "&aContructing...";
                        statusMat = Material.NETHER_STAR;

                    } else { //input doesnt match

                        name = "&cWrong resource input!";

                        if (inv.getItemInSlot(OUTPUT_SLOT) == null) {
                            inv.pushItem(inputSlotItem, OUTPUT_SLOTS);
                            inv.consumeItem(INPUT_SLOT, inputSlotItem.getAmount());

                        }
                    }
                } else { //if contruction done

                    ItemStack output = ItemUtils.getItemFromID(outputItems[progressID], 1);

                    if (inv.fits(output, OUTPUT_SLOTS)) { //output

                        inv.pushItem(output, OUTPUT_SLOTS);
                        setProgress(b, 0);
                        setProgressID(b, null);

                        name = "&aContruction complete!";
                        statusMat = Material.NETHER_STAR;

                    } else { //not enough room

                        name = "&6Not enough room!";
                        statusMat = Material.ORANGE_STAINED_GLASS_PANE;
                    }
                }
            }
        }

        //update status and finish

        if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {

            progress = Integer.parseInt(getProgress(b));
            String lore = "";
            String loree = "";

            if (progress > 0) {
                int progressID = Integer.parseInt(getProgressID(b));
                String output = outputItems[progressID];
                int outputTime = outputTimes[progressID];

                String displayname = "";
                ItemMeta displaymeta = ItemUtils.getItemFromID(output, 1).getItemMeta();

                if (displaymeta != null) {
                    displayname = displaymeta.getDisplayName();
                }

                lore = "&7Contructing: " + displayname;
                loree = "&7Progress: (" + progress + "/" + outputTime + ")";
            }

            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(statusMat,
                    name,
                    lore,
                    loree
            ));
        }
    }

    private boolean checkItemAndSet(Block b, BlockMenu inv, ItemStack item, int speed) {
        int itemAmount = item.getAmount();

        for (int i = 0 ; i < inputItems.length ; i++) {
            if (ItemUtils.getIDFromItem(item).equals(inputItems[i])) {
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
        return getEnergyConsumption(type);
    }

    @Override
    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    private int getEnergyConsumption(Type type) {
        if (type == Type.BASIC) {
            return BASIC_ENERGY;
        } else if (type == Type.INFINITY) {
            return INFINITY_ENERGY;
        } else {
            return 0;
        }
    }

    private int getSpeed(Type type) {
        if (type == Type.BASIC) {
            return BASIC_SPEED;
        } else if (type == Type.INFINITY) {
            return INFINITY_SPEED;
        } else {
            return 0;
        }
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> items = new ArrayList<>();

        for (Singularities.Type type : Singularities.Type.values()) {
            items.add(type.getRecipe());
            items.add(type.getItem());
        }

        return items;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)

    public enum Type {
        BASIC(Categories.ADVANCED_MACHINES, Items.SINGULARITY_CONSTRUCTOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                Items.MACHINE_PLATE, SlimefunItems.CARBON_PRESS_3, Items.MACHINE_PLATE,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(Categories.INFINITY_MACHINES, Items.INFINITY_CONSTRUCTOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.INFINITE_INGOT, Items.MACHINE_PLATE, Items.INFINITE_INGOT,
                Items.MACHINE_PLATE, Items.SINGULARITY_CONSTRUCTOR, Items.MACHINE_PLATE,
                Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT
        });

        @Nonnull
        private final Category category;
        private final SlimefunItemStack item;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}
