package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
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
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
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
import java.util.UUID;

import static me.mooy1.infinityexpansion.Utils.getIDofItem;
import static me.mooy1.infinityexpansion.Utils.getItemFromID;

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
    private final String[] inputItems = {
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

            "INFINITE_INGOT"
    };
    private final String[] outputItems = {
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
            "LAPIS_LAZULI_SINGULARITY",
            "QUARTZ_SINGULARITY",

            "INFINITY_SINGULARITY"
    };
    private final int[] outputTimes = {
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

            1000
    };
    private final ItemStack loadingItem = new CustomItem(
            Material.LIME_STAINED_GLASS_PANE,
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

        new BlockMenuPreset(getID(), type.getItem().getDisplayName()) {
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
                    return INPUTSLOTS;
                } else if (flow == ItemTransportFlow.WITHDRAW) {
                    return OUTPUTSLOTS;
                } else {
                    return new int[0];
                }
            }
        };

        registerBlockHandler(getID(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                int progress = Integer.parseInt(getBlockData(b.getLocation(), "progress"));
                String inputtest = getProgressID(b);

                inv.dropItems(b.getLocation(), getOutputSlots());
                inv.dropItems(b.getLocation(), getInputSlots());

                if (progress > 0 && inputtest != null) {

                    String input = inputItems[Integer.parseInt(getProgressID(b))];

                    int stacksize = 64;

                    int stacks = (int) Math.floor((float) progress / stacksize);
                    int remainder = progress % stacksize;

                    for (int i = 0; i < stacks; i++) {
                        b.getWorld().dropItemNaturally(b.getLocation(),getItemFromID(input, stacksize));
                    }
                    if (remainder > 0) {
                        b.getWorld().dropItemNaturally(b.getLocation(),getItemFromID(input, remainder));
                    }
                }
            }

            if (BlockStorage.getLocationInfo(b.getLocation(), "stand") != null) {
                Bukkit.getEntity(UUID.fromString(BlockStorage.getLocationInfo(b.getLocation(), "stand"))).remove();
            }

            return true;
        });
    }

    private void setupInv(BlockMenuPreset blockMenuPreset) {
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
        Material statusmat = null;

        int progress = Integer.parseInt(getProgress(b));
        ItemStack inputSlotItem = inv.getItemInSlot(INPUTSLOT);

        if (getCharge(b.getLocation()) < type.getEnergyConsumption()) { //when not enough power

            name = "&cNot enough energy!";
            statusmat = Material.BARRIER;

        } else if (inputSlotItem == null) { //no input

            if (progress == 0 || getProgressID(b) == null) { //havent started

                name = "&9Input a resource!";
                statusmat = Material.BLUE_STAINED_GLASS_PANE;

            } else { //started

                ItemStack input = getItemFromID(inputItems[Integer.parseInt(getProgressID(b))], 1);

                if (!input.getItemMeta().getDisplayName().equals("")) { //sf name
                    name = "&cInput more " + ItemUtils.getItemName(input) + "s&c!";
                } else { //vanilla name
                    name = "&cInput more &f" + ItemUtils.getItemName(input) + "s&c!";
                }

                statusmat = Material.BARRIER;
            }

        } else { //started

            int speed = type.getSpeed();

            if (progress == 0 || getProgressID(b) == null) { //no input

                if (checkItemAndSet(b, inv, inputSlotItem, speed)) { //try to start contruction

                    name = "&aBeggining contruction!";
                    statusmat = Material.NETHER_STAR;

                    if (inputSlotItem.getAmount() >= speed) { //make sure there is enough

                    } else { //not enough

                        name = "&cNot enough resource input!";
                        statusmat = Material.BARRIER;

                    }

                } else { //failed to start contruction

                    name = "&cA singularity can't be contructed from this!";
                    statusmat = Material.BARRIER;

                    if (inv.getItemInSlot(OUTPUTSLOT) == null) {
                        inv.pushItem(inputSlotItem, OUTPUTSLOTS);
                        inv.consumeItem(INPUTSLOT, inputSlotItem.getAmount());
                    }
                }

            } else {

                int progressID = Integer.parseInt(getProgressID(b));
                int outputTime = outputTimes[progressID];

                if (progress < outputTime) { //increase progress

                String input = inputItems[progressID];

                if (getIDofItem(inputSlotItem).equals(input)) { //input matches

                    int inputSlotAmount = inputSlotItem.getAmount();

                    if (inputSlotAmount >= speed) { //speed

                        setProgress(b, progress + speed);
                        inv.consumeItem(INPUTSLOT, speed);

                    } else { //less than speed

                        setProgress(b, progress + inputSlotAmount);
                        inv.consumeItem(INPUTSLOT, inputSlotAmount);

                    }

                    progress = Integer.parseInt(getProgress(b));

                    if (progress > outputTime) {
                        setProgress(b, outputTime);
                    }

                    name = "&aContructing...";
                    statusmat = Material.NETHER_STAR;

                    if (progress >= outputTime) { //if contruction done

                        String output = outputItems[progressID];

                        if (inv.fits(getItemFromID(output, 1), OUTPUTSLOTS)) { //output

                            inv.pushItem(getItemFromID(output, 1), OUTPUTSLOTS);
                            setProgress(b, 0);
                            setProgressID(b, null);

                            name = "&aContruction complete!";
                            statusmat = Material.NETHER_STAR;

                        } else { //not enough room

                            name = "&6Not enough room!";
                            statusmat = Material.ORANGE_STAINED_GLASS_PANE;

                        }
                    }

                    } else { //input doesnt match

                        name = "&cWrong resource input!";
                        statusmat = Material.BARRIER;

                        if (inv.getItemInSlot(OUTPUTSLOT) == null) {
                            inv.pushItem(inputSlotItem, OUTPUTSLOTS);
                            inv.consumeItem(INPUTSLOT, inputSlotItem.getAmount());

                        }
                    }
                }
            }
        }

        //update status

        progress = Integer.parseInt(getBlockData(b.getLocation(), "progress"));


        String lore = "";
        String loree = "";

        if (progress > 0) {
            String output = outputItems[Integer.parseInt(getProgressID(b))];
            int time = outputTimes[Integer.parseInt(getProgressID(b))];

            String displayname = "";
            ItemMeta displaymeta = getItemFromID(output, 1).getItemMeta();

            if (displaymeta != null) {
                displayname = displaymeta.getDisplayName();
            }

            lore = "&7Contructing: " + displayname;
            loree = "&7Progress: (" + progress + "/" + time + ")";

        }

        if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {
            inv.replaceExistingItem(STATUSSLOT, new CustomItem(statusmat,
                    name,
                    lore,
                    loree
            ));
        }
    }

    private boolean checkItemAndSet(Block b, BlockMenu inv, ItemStack item, int speed) {
        int itemAmount = item.getAmount();

        for (int i = 0 ; i < inputItems.length ; i++) {
            if (getIDofItem(item).equals(inputItems[i])) {
                if (itemAmount >= speed) {
                    setProgress(b, speed);
                    inv.consumeItem(INPUTSLOT, speed);
                } else {
                    setProgress(b, itemAmount);
                    inv.consumeItem(INPUTSLOT, itemAmount);
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
            new NamespacedKey(InfinityExpansion.getInstance(), "singularity_constructor"), Items.SINGULARITY_CONSTRUCTOR
    );

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)

    public enum Type {
        BASIC(Items.SINGULARITY_CONSTRUCTOR, 1, 600, new ItemStack[] {
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                Items.MACHINE_PLATE, SlimefunItems.CARBON_PRESS_3, Items.MACHINE_PLATE,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(Items.INFINITY_CONSTRUCTOR, 10, 60_000, new ItemStack[] {
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
