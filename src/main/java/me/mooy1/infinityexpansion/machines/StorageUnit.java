package me.mooy1.infinityexpansion.machines;

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
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Basically just barrels...
 *
 * @author Mooy1
 *
 * Thanks to
 * @author NCBPFluffyBear
 * for idea, a few bits of code,
 * and code to learn from
 *
 */

public class StorageUnit extends SlimefunItem implements InventoryBlock {

    private final Tier tier;

    private final int STATUSSLOT = 13;

    private final int[] INPUTSLOTS = {
            10
    };

    final int INPUTSLOT = INPUTSLOTS[0];

    private final int[] OUTPUTSLOTS = {
            16
    };

    final int OUTPUTSLOT = OUTPUTSLOTS[0];

    private final int[] INPUTBORDER = {
            0, 1, 2, 9, 11, 18, 19, 20
    };

    private final int[] STATUSBORDER = {
            3, 4, 5, 12, 14, 21, 22, 23
    };

    private final int[] OUTPUTBORDER = {
            6, 7, 8, 15, 17, 24, 25, 26
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

    public StorageUnit(Tier tier) {
        super(Categories.INFINITY_MACHINES, tier.getItem(), tier.getRecipeType(), tier.getRecipe());
        this.tier = tier;

        setupInv();

        registerBlockHandler(getID(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                String storeditem = getBlockData(b.getLocation(), "storeditem");
                String storeditemtype = getBlockData(b.getLocation(), "storeditemtype");
                int stored = Integer.parseInt(getBlockData(b.getLocation(), "stored"));

                inv.dropItems(b.getLocation(), getOutputSlots());
                inv.dropItems(b.getLocation(), getInputSlots());

                //drop stored items

                if (stored > 0 && storeditem != null && storeditemtype != null) {
                    int stacksize = convert(storeditemtype, storeditem, 1).getMaxStackSize();

                    int stacks = (int) Math.floor((float) stored / stacksize);
                    int remainder = stored % stacksize;

                    for (int i = 0; i < stacks; i++) {
                        b.getWorld().dropItemNaturally(b.getLocation(), convert(storeditemtype, storeditem, stacksize));
                    }
                    if (remainder > 0) {
                        b.getWorld().dropItemNaturally(b.getLocation(), convert(storeditemtype, storeditem, remainder));
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
        createPreset(this, tier.getItem().getImmutableMeta().getDisplayName().orElse("&7THIS IS A BUG"),
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
            public void tick(Block b, SlimefunItem sf, Config data) { StorageUnit.this.tick(b); }

            public boolean isSynchronized() { return false; }
        });
    }

    protected void tick(Block b) {

        @Nullable final BlockMenu inv = BlockStorage.getInventory(b.getLocation());
        if (inv == null) return;

        int maxstorage = tier.getStorage();
        String storedtest = getBlockData(b.getLocation(), "stored");
        String storeditem = getBlockData(b.getLocation(), "storeditem");
        String storeditemtype = getBlockData(b.getLocation(), "storeditemtype");

        //start and fix bugs

        if (storedtest == null || storeditem == null || storeditem.equals("") || storeditemtype == null) {
            setAmount(b, 0);
            setItem(b, null);
            setType(b, null);
        }

        //input

        ItemStack slotItem = inv.getItemInSlot(INPUTSLOT);
        //Check if empty slot
        if (slotItem != null) {
            //Check if non stackable item
            if (slotItem.getMaxStackSize() != 1) {

                int slotAmount = slotItem.getAmount();
                int stored = Integer.parseInt(getBlockData(b.getLocation(), "stored"));

                String id = slotItem.getType().toString();

                //Check if empty
                if (storeditem == null && storeditemtype == null && stored == 0) { //store new item

                    if (SlimefunItem.getByItem(slotItem) != null) { //store slimefun item
                        setItem(b, SlimefunItem.getByItem(slotItem).getID());
                        setType(b, "slimefun");
                    } else { //store vanilla item
                        setItem(b, id);
                        setType(b, "material");
                    }

                    setAmount(b, slotAmount);
                    inv.consumeItem(INPUTSLOT, slotAmount);

                } else {
                    int maxinput = maxstorage-stored;
                    storeditem = getBlockData(b.getLocation(), "storeditem");
                    storeditemtype = getBlockData(b.getLocation(), "storeditemtype");

                    if (SlimefunItem.getByItem(slotItem) != null) {
                        id = SlimefunItem.getByItem(slotItem).getID();
                        if (storeditemtype.equals("slimefun") && storeditem.equals(id)) { //deposit slimefun item

                            if (slotAmount <= maxinput) {
                                setAmount(b, stored + slotAmount);
                                inv.consumeItem(INPUTSLOT, slotAmount);
                            } else {
                                setAmount(b, stored + maxinput);
                                inv.consumeItem(INPUTSLOT, maxinput);
                            }
                        }
                    } else if (storeditemtype.equals("material") && storeditem.equals(id)) { //deposit vanilla item

                        if (slotAmount <= maxinput) {
                            setAmount(b, stored + slotAmount);
                            inv.consumeItem(INPUTSLOT, slotAmount);
                        } else {
                            setAmount(b, stored + maxinput);
                            inv.consumeItem(INPUTSLOT, maxinput);
                        }
                    }
                }
            }
        }

        //output

        if (getBlockData(b.getLocation(), "storeditemtype") != null && getBlockData(b.getLocation(), "storeditem") != null) {

            storeditem = getBlockData(b.getLocation(), "storeditem");
            storeditemtype = getBlockData(b.getLocation(), "storeditemtype");
            int stored = Integer.parseInt(getBlockData(b.getLocation(), "stored"));

            int outputRemaining;

            if (inv.getItemInSlot(OUTPUTSLOT) != null) {
                outputRemaining = convert(storeditemtype, storeditem, 1).getMaxStackSize()-inv.getItemInSlot(OUTPUTSLOT).getAmount();
            } else {
                outputRemaining = convert(storeditemtype, storeditem, 1).getMaxStackSize();
            }

            if (stored > 1) {

                int amount = 0;

                for (int i = 0; i < outputRemaining; i++) {
                    stored = Integer.parseInt(getBlockData(b.getLocation(), "stored"));

                    if (stored > 1+i) {
                        if (inv.fits(convert(storeditemtype, storeditem, 1+i), OUTPUTSLOTS)) {
                            amount++;
                        }
                    }
                }
                inv.pushItem(convert(storeditemtype, storeditem, amount), OUTPUTSLOTS);
                setAmount(b, stored-amount);

            } else if (stored == 1 && inv.getItemInSlot(OUTPUTSLOT) == null) {
                inv.pushItem(convert(storeditemtype, storeditem, 1), OUTPUTSLOTS);
                setItem(b, null);
                setType(b, null);
                setAmount(b, 0);
            }
        }
    }

    private void setItem(Block b, String storeditem) {
        setBlockData(b, "storeditem", storeditem);
    }

    private void setType(Block b, String type) {
        setBlockData(b, "storeditemtype", type);
    }

    private void setAmount(Block b, int stored) {
        setBlockData(b, "stored", String.valueOf(stored));
        updateStatus(b);
    }

    private void updateStatus(Block b) {
        BlockMenu inv = BlockStorage.getInventory(b);

        if (inv.toInventory() == null || inv.toInventory().getViewers().isEmpty()) return;

        String storeditem = getBlockData(b.getLocation(), "storeditem");
        String storeditemtype = getBlockData(b.getLocation(), "storeditemtype");
        int stored = Integer.parseInt(getBlockData(b.getLocation(), "stored"));

        if (storeditem == null || stored == 0 || storeditemtype == null) {
            inv.replaceExistingItem(STATUSSLOT, new CustomItem(
                    new ItemStack(Material.BARRIER),
                    "&cInput an Item!"
            ));
        } else {
            int maxstorage = tier.getStorage();
            ItemStack converteditem = convert(storeditemtype, storeditem, 1);

            String converteditemname = "";
            if (converteditem.getItemMeta() != null) {
                converteditemname = converteditem.getItemMeta().getDisplayName();
            }

            String stacks = "&7Stacks: " + Math.round((float)stored / convert(storeditemtype, storeditem, 1).getMaxStackSize());

            if (this.tier == Tier.INFINITY) {
                inv.replaceExistingItem(STATUSSLOT, new CustomItem(
                        converteditem,
                        converteditemname,
                        "&6Stored: &e" + stored,
                        stacks
                ));
            } else {
                inv.replaceExistingItem(STATUSSLOT, new CustomItem(
                        converteditem,
                        converteditemname,
                        "&6Stored: &e" + stored + "/" + maxstorage + " &7(" + Math.round((float) 100 * stored / maxstorage )  + "%)",
                        stacks
                ));
            }
        }
    }

    private ItemStack convert(String type, String id, int amount) {
        ItemStack out = null;

        if (type.equals("slimefun")) {
            out = new CustomItem(SlimefunItem.getByID(id).getItem(), amount);
        } else if (type.equals("material")) {
            out = new ItemStack(Material.getMaterial(id), amount);
        }
        return out;
    }

    private void setBlockData(Block b, String key, String data) {
        BlockStorage.addBlockInfo(b, key, data);
    }

    private String getBlockData(Location l, String key) {
        return BlockStorage.getLocationInfo(l, key);
    }

    @Override
    public int[] getInputSlots() {
        return INPUTSLOTS;
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUTSLOTS;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)

    public enum Tier {
        BASIC(Items.BASIC_STORAGE, 2560, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                new ItemStack(Material.OAK_LOG), Items.MAGSTEEL, new ItemStack(Material.OAK_LOG),
                new ItemStack(Material.OAK_LOG), new ItemStack(Material.BARREL), new ItemStack(Material.OAK_LOG),
                new ItemStack(Material.OAK_LOG), Items.MAGSTEEL, new ItemStack(Material.OAK_LOG)
        }),
        ADVANCED(Items.ADVANCED_STORAGE, 10240, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL, Items.MACHINE_CIRCUIT, Items.MAGSTEEL,
                Items.MAGSTEEL, Items.BASIC_STORAGE, Items.MAGSTEEL,
                Items.MAGSTEEL, Items.MACHINE_CIRCUIT, Items.MAGSTEEL
        }),
        REINFORCED(Items.REINFORCED_STORAGE, 40960, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL_PLATE, Items.MACHINE_PLATE, Items.MAGSTEEL_PLATE,
                Items.MAGSTEEL_PLATE, Items.ADVANCED_STORAGE, Items.MAGSTEEL_PLATE,
                Items.MAGSTEEL_PLATE, Items.MACHINE_CORE, Items.MAGSTEEL_PLATE
        }),
        VOID(Items.VOID_STORAGE, 163840, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.VOID_INGOT, Items.MACHINE_PLATE, Items.VOID_INGOT,
                Items.MAGNONIUM_INGOT, Items.REINFORCED_STORAGE, Items.MAGNONIUM_INGOT,
                Items.MAGNONIUM_INGOT, Items.MACHINE_CORE, Items.MAGNONIUM_INGOT
        }),
        INFINITY(Items.INFINITY_STORAGE, 10000000, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.INFINITE_INGOT, Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_INGOT,
                Items.MACHINE_PLATE, Items.VOID_STORAGE, Items.MACHINE_PLATE,
                Items.INFINITE_INGOT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_INGOT
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final int storage;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}
