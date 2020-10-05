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
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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

    private final String[] inputItems = new String[] {
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
            "QUARTZ"
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
            16000,
            16000,
            16000,
            16000,
            16000,
            16000,
            16000,

            16000,
            32000,
            8000,
            8000,
            800,

            16000,
            32000,
            32000,
            32000,
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

        setupInv();

        registerBlockHandler(getID(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                String input = getBlockData(b.getLocation(), "input");
                int progress = Integer.parseInt(getBlockData(b.getLocation(), "progress"));

                inv.dropItems(b.getLocation(), getOutputSlots());
                inv.dropItems(b.getLocation(), getInputSlots());

                if (progress > 0 && item != null) {
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

    private void setupInv() {
        createPreset(this, type.getItem().getImmutableMeta().getDisplayName().orElse("&cTHIS IS A BUG"),
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

        String progresstest = getBlockData(b.getLocation(), "progress");
        String name = null;
        Material statusmat = null;

        //start and fix bugs

        if (progresstest == null) {
            setProgress(b, 0);
            setTime(b, 0);
        }

        int progress = Integer.parseInt(getBlockData(b.getLocation(), "progress"));
        int time = Integer.parseInt(getBlockData(b.getLocation(), "time"));
        String input = getBlockData(b.getLocation(), "input");
        String output = getBlockData(b.getLocation(), "output");

        ItemStack inputSlotItem = inv.getItemInSlot(INPUTSLOT);

        //start

        if (getCharge(b.getLocation()) <type.getEnergyConsumption()) { //when not enough power

            name = "&cNot enough energy!";
            statusmat = Material.BARRIER;

        } else if (progress == 0) { //when not started

            if (inputSlotItem == null) { //no input

                name = "&9Input a resource to start construction!";
                statusmat = Material.BLUE_STAINED_GLASS_PANE;

            } else { //input

                if (checkItemAndSet(b, inv, inputSlotItem)) { //try to start contruction

                    name = "&aBeggining contruction!";
                    statusmat = Material.NETHER_STAR;

                    if (inputSlotItem.getAmount() >= type.getSpeed()) { //make sure there is enough

                    } else { //not enough

                        name = "&cNot enough resource input!";
                        statusmat = Material.BARRIER;

                    }

                } else { //failed to start contruction

                    name = "&cA singularity can't be contructed from this!";
                    statusmat = Material.BARRIER;

                    if (inv.fits(inputSlotItem, OUTPUTSLOTS)) { //try to move to output slot to decrease timings

                        inv.pushItem(inputSlotItem, OUTPUTSLOTS);
                        inv.consumeItem(INPUTSLOT, inputSlotItem.getAmount());

                    }

                }
            }

        } else { //increase progress

            if (inputSlotItem == null) { //no input

                if (getItemFromID(input, 1).getItemMeta().getDisplayName() != "") { //sf name
                    name = "&cInput more " + ItemUtils.getItemName(getItemFromID(input, 1)) + "s&c!";
                } else { //vanilla name
                    name = "&cInput more &f" + ItemUtils.getItemName(getItemFromID(input, 1)) + "s&c!";
                }

                statusmat = Material.BARRIER;

            } else if (progress < time) { //input

                if (getID(inputSlotItem).equals(input)) { //input matches

                    if (inputSlotItem.getAmount() >= type.getSpeed()) { //enough

                        inv.consumeItem(INPUTSLOT, type.getSpeed());
                        setProgress(b, progress + type.getSpeed());
                        name = "&aContructing...";
                        statusmat = Material.NETHER_STAR;

                    } else { //not enough

                        name = "&cNot enough resource input!";
                        statusmat = Material.BARRIER;

                    }

                } else { //input doesnt match

                    name = "&cIncorrect resource Input!";
                    statusmat = Material.BARRIER;

                    if (inv.fits(inputSlotItem, OUTPUTSLOTS)) { //try to move to output slot to decrease timings

                        inv.pushItem(inputSlotItem, OUTPUTSLOTS);
                        inv.consumeItem(INPUTSLOT, inputSlotItem.getAmount());

                    }
                }
            }

            if (progress >= time) { //if contruction done

                if (inv.fits(getItemFromID(output, 1), OUTPUTSLOTS)) { //output

                    inv.pushItem(getItemFromID(output, 1), OUTPUTSLOTS);
                    setTime(b, 0);
                    setProgress(b, 0);
                    setInput(b, null);
                    setOutput(b, null);

                    name = "&aContruction complete!";
                    statusmat = Material.NETHER_STAR;

                } else { //not enough room

                    name = "&6Not enough room!";
                    statusmat = Material.ORANGE_STAINED_GLASS_PANE;

                }
            }
        }

        //update status

        progress = Integer.parseInt(getBlockData(b.getLocation(), "progress"));
        time = Integer.parseInt(getBlockData(b.getLocation(), "time"));
        output = getBlockData(b.getLocation(), "output");

        String lore = "";
        String loree = "";

        if (progress > 0) {
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

    private boolean checkItemAndSet(Block b, BlockMenu inv, ItemStack item) {
        for (int i = 0 ; i < inputItems.length ; i++) {
            if (getID(item).equals(inputItems[i])) {
                setInput(b, inputItems[i]);
                setOutput(b, outputItems[i]);
                setTime(b, outputTimes[i]);
                setProgress(b, type.getSpeed());
                inv.consumeItem(INPUTSLOT, type.getSpeed());
                return true;
            }
        }
        return false;
    }

    private String getID(ItemStack item) {
        if (SlimefunItem.getByItem(item) != null) {
            return SlimefunItem.getByItem(item).getID();
        } else {
            return item.getType().toString();
        }
    }

    private ItemStack getItemFromID(String id, int amount) {
        if (SlimefunItem.getByID(id) != null) {
            return new CustomItem(SlimefunItem.getByID(id).getItem(), amount);
        } else{
            return new ItemStack(Material.getMaterial(id), amount);
        }
    }

    private void setProgress(Block b, int progress) {
        setBlockData(b, "progress", String.valueOf(progress));
    }

    private void setTime(Block b, int time) {
        setBlockData(b, "time", String.valueOf(time));
    }

    private void setOutput(Block b, String id) {
        setBlockData(b, "output", id);
    }

    private void setInput(Block b, String id) {
        setBlockData(b, "input", id);
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
