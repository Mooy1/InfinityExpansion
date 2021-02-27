package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.implementation.materials.Items;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.abstracts.AbstractMachine;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AllArgsConstructor;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Turns cobble into stuff
 */
public final class StoneworksFactory extends AbstractMachine implements RecipeDisplayItem {
    
    public static final SlimefunItemStack ITEM = new SlimefunItemStack(
            "STONEWORKS_FACTORY",
            Material.BLAST_FURNACE,
            "&8Stoneworks Factory",
            "&7Generates cobblestone and processes it into various materials",
            "",
            LorePreset.energyPerSecond(StoneworksFactory.ENERGY)
    );
    
    public static final int ENERGY = 240;

    private static final int[] PROCESS_BORDER = {0, 1, 2, 3, 4, 5, 18, 19, 20, 21, 22, 23};
    private static final int[] OUT_BORDER = {6, 7, 8, 17, 24, 25, 26};
    private static final int[] OUTPUT_SLOTS = {16};
    private static final int STATUS_SLOT = 9;
    private static final int[] CHOICE_SLOTS = {11, 13, 15};
    private static final int[] PROCESS_SLOTS = {10, 12, 14};
    private static final ItemStack COBBLE_GEN = new CustomItem(Material.GRAY_CONCRETE, "&8Cobblegen");
    private static final ItemStack PROCESSING = new CustomItem(Material.GRAY_STAINED_GLASS_PANE, "&7Processing");

    public StoneworksFactory() {
        super(Categories.ADVANCED_MACHINES, ITEM, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.MAGSTEEL_PLATE, MaterialGenerator.BASIC_COBBLE, Items.MAGSTEEL_PLATE,
                SlimefunItems.ELECTRIC_FURNACE_3, Items.MACHINE_CIRCUIT, SlimefunItems.ELECTRIC_ORE_GRINDER,
                Items.MAGSTEEL_PLATE, SlimefunItems.ELECTRIC_PRESS, Items.MAGSTEEL_PLATE
        });

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), OUTPUT_SLOTS);
                inv.dropItems(b.getLocation(), PROCESS_SLOTS);
            }

            return true;
        });
    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        super.setupMenu(blockMenuPreset);
        for (int i : PROCESS_BORDER) {
            blockMenuPreset.addItem(i, PROCESSING, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : OUT_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : CHOICE_SLOTS) {
            blockMenuPreset.addItem(i, Choice.NONE.item, ChestMenuUtils.getEmptyClickHandler());
        }
    }

    @Override
    protected int getStatusSlot() {
        return STATUS_SLOT;
    }

    @Override
    protected int getEnergyConsumption() {
        return ENERGY;
    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        }
        return new int[0];
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        Location l = b.getLocation();
        
        if (BlockStorage.getLocationInfo(l, "choice0") == null) {
            setChoice(l, 0, Choice.NONE);
            setChoice(l, 1, Choice.NONE);
            setChoice(l, 2, Choice.NONE);
        }

        for (int i = 0 ; i < CHOICE_SLOTS.length ; i++) {
            menu.replaceExistingItem(CHOICE_SLOTS[i], getChoice(l, i).item);
        }
        
        for (int i = 0 ; i < 3 ; i++) {
            int finalI = i;
            menu.addMenuClickHandler(CHOICE_SLOTS[i], (p, slot, item, action) -> {
                int current = ArrayUtils.indexOf(Choice.values(), getChoice(b.getLocation(), finalI));
                Choice next;
                if (action.isRightClicked()) {
                    if (current > 0) {
                        next = Choice.values()[current - 1];
                    } else {
                        next = Choice.values()[Choice.values().length - 1];
                    }
                } else {
                    if (current < Choice.values().length - 1) {
                        next = Choice.values()[current + 1];
                    } else {
                        next = Choice.values()[0];
                    }
                }
                setChoice(l, finalI, next);
                menu.replaceExistingItem(CHOICE_SLOTS[finalI], next.item);
                return false;
            });
        }
    }
    
    private static void process(int i, BlockMenu inv, Location l) {
        int slot = PROCESS_SLOTS[i];

        ItemStack item = inv.getItemInSlot(slot);
        
        if (item == null) return;
        
        Choice c = getChoice(l, i);
        int nextSlot = i < 2 ? PROCESS_SLOTS[i + 1] : OUTPUT_SLOTS[0];

        if (c == Choice.NONE) {
            item = item.clone();
            item.setAmount(1);
            
            if (inv.fits(item, nextSlot)) {
                inv.consumeItem(slot, 1);
                inv.pushItem(item, nextSlot);
            }
            return;
        }

        for (int check = 0 ; check < c.inputs.length ; check++) {

            if (item.getType() == c.inputs[check]) {

                ItemStack output = new ItemStack(c.outputs[check]);

                if (inv.fits(output, nextSlot)) {
                    inv.consumeItem(slot, 1);
                    inv.pushItem(output, nextSlot);
                }

                break;
            }
        }
    }

    @Override
    public int getCapacity() {
        return ENERGY * 2;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();
        for (Choice option : Choice.values()) {
            for (int i = 0 ; i < option.inputs.length ; i++) {
                items.add(new ItemStack(option.inputs[i]));
                items.add(new ItemStack(option.outputs[i]));
            }
        }
        
        return items;
    }
    
    @Nonnull
    private static Choice getChoice(Location l, int i) {
        try {
            return Choice.valueOf(BlockStorage.getLocationInfo(l, "choice" + i));
        } catch (Exception e) {
            setChoice(l, i, Choice.NONE);
            return Choice.NONE;
        }
    }
    
    private static void setChoice(Location l, int i, Choice o) {
        BlockStorage.addBlockInfo(l, "choice" + i, o.toString());
    }

    @Override
    protected boolean process(@Nonnull BlockMenu inv, @Nonnull Block b, @Nonnull Config data) {
        inv.replaceExistingItem(STATUS_SLOT, COBBLE_GEN);

        int tick = PluginUtils.getCurrentTick() & 3;

        if (tick == 3) {
            ItemStack cobble = new ItemStack(Material.COBBLESTONE);

            if (inv.fits(cobble, PROCESS_SLOTS[0])) {
                inv.pushItem(cobble, PROCESS_SLOTS[0]);
            }
        } else {
            process(tick, inv, b.getLocation());
        }

        return true;
    }

    @AllArgsConstructor
    private enum Choice {
        NONE(new CustomItem(Material.BARRIER, "&cNone", "", "&7 > Click to cycle"),
                new Material[0],
                new Material[0]
        ),
        FURNACE(new CustomItem(Material.FURNACE, "&8Smelting", "", "&7 > Click to cycle"),
                new Material[]{Material.COBBLESTONE, Material.SAND},
                new Material[]{Material.STONE, Material.GLASS}
        ),
        CRUSH(new CustomItem(Material.DIAMOND_PICKAXE, "&8Crushing", "", "&7 > Click to cycle"),
                new Material[]{Material.COBBLESTONE, Material.GRAVEL},
                new Material[]{Material.GRAVEL, Material.SAND}
        ),
        COMPACT(new CustomItem(Material.PISTON, "&8Compacting", "", "&7 > Click to cycle"),
                new Material[]{Material.STONE, Material.GRANITE, Material.DIORITE, Material.ANDESITE},
                new Material[]{Material.STONE_BRICKS, Material.POLISHED_GRANITE, Material.POLISHED_DIORITE, Material.POLISHED_ANDESITE}
        ),
        TRANSFORM(new CustomItem(Material.ANDESITE, "&8Transforming", "", "&7 > Click to cycle"),
                new Material[]{Material.COBBLESTONE, Material.ANDESITE, Material.DIORITE},
                new Material[]{Material.ANDESITE, Material.DIORITE, Material.GRANITE}
        );

        private final ItemStack item;
        private final Material[] inputs;
        private final Material[] outputs;
    }
}
