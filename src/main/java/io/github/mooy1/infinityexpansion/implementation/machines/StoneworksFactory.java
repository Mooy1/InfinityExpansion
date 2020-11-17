package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.PresetUtils;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Turns cobble into stuff
 *
 * @author Mooy1
 */
public class StoneworksFactory extends SlimefunItem implements EnergyNetComponent, RecipeDisplayItem {

    public static final int ENERGY = 180;

    private static final int[] PROCESS_BORDER = {0, 1, 2, 3, 4, 5, 18, 19, 20, 21, 22, 23};
    private static final int[] OUT_BORDER = {6, 7, 8, 17, 24, 25, 26};
    private static final int[] OUTPUT_SLOTS = {17};
    private static final int STATUS_SLOT = 9;
    private static final int[] CHOICE_SLOTS = {11, 13, 15};
    private static final int[] USE_SLOTS = {10, 12, 14, 16};
    private static final ItemStack COBBLE_GEN = new CustomItem(Material.GRAY_CONCRETE, "Cobblegen");

    public StoneworksFactory() {
        super(Categories.ADVANCED_MACHINES, Items.STONEWORKS_FACTORY, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL_PLATE, Items.BASIC_COBBLE_GEN, Items.MAGSTEEL_PLATE,
                SlimefunItems.ELECTRIC_FURNACE, Items.MACHINE_CIRCUIT, SlimefunItems.ELECTRIC_ORE_GRINDER,
                Items.MAGSTEEL_PLATE, SlimefunItems.ELECTRIC_PRESS, Items.MAGSTEEL_PLATE
        });

        new BlockMenuPreset(getId(), Objects.requireNonNull(Items.STONEWORKS_FACTORY.getDisplayName())) {
            @Override
            public void init() {
                setupInv(this);
            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return (player.hasPermission("slimefun.inventory.bypass")
                        || SlimefunPlugin.getProtectionManager().hasPermission(
                        player, block.getLocation(), ProtectableAction.ACCESS_INVENTORIES));
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                if (flow == ItemTransportFlow.WITHDRAW) {
                    return OUTPUT_SLOTS;
                }

                return new int[0];
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
                if (flow == ItemTransportFlow.WITHDRAW) {
                    return OUTPUT_SLOTS;
                }

                return new int[0];
            }
        };

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), OUTPUT_SLOTS);
            }

            return true;
        });
    }

    private void setupInv(BlockMenuPreset blockMenuPreset) {
        for (int i : PROCESS_BORDER) {
            blockMenuPreset.addItem(i, new CustomItem(Material.GRAY_STAINED_GLASS_PANE, "Processing"), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : OUT_BORDER) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : CHOICE_SLOTS) {
            blockMenuPreset.addItem(i, Option.NONE.getItem(), ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, COBBLE_GEN, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) { StoneworksFactory.this.tick(b); }

            public boolean isSynchronized() { return false; }
        });
    }

    public void tick(Block b) {
        Location l = b.getLocation();
        @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
        if (inv == null) return;
        
        int charge = getCharge(l);
        boolean playerWatching = inv.hasViewer();

        if (charge < ENERGY) { //not enough energy

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughEnergy);
            }
            return;
            
        }
        
        
    }
    
    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return ENERGY;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();
        for (Output output : Output.values()) {
            items.add(null);
            items.add(output.getItem());
        }
        return items;
    }
    
    @Getter
    @AllArgsConstructor
    private enum Option {
        NONE(new CustomItem(Material.BARRIER, "None"), new Output[0], new Output[0]),
        FURNACE(new CustomItem(Material.FURNACE, "Smelting"), new Output[] {Output.COBBLE, Output.SAND}, new Output[] {Output.STONE, Output.GLASS}),
        CRUSH(new CustomItem(Material.DIAMOND_PICKAXE, "Crushing"), new Output[] {Output.COBBLE, Output.GRAVEL}, new Output[] {Output.GRAVEL, Output.SAND}),
        COMPACT(new CustomItem(Material.PISTON, "Compacting"), new Output[] {Output.STONE, Output.GRANITE, Output.DIO, Output.AND}, new Output[] {Output.BRICK, Output.GRANITE_P, Output.DIO_P, Output.AND_P}),
        TRANSFORM_AND(new CustomItem(Material.GRANITE, "Transform - Granite"), new Output[] {Output.COBBLE, Output.DIO, Output.AND}, new Output[] {Output.GRANITE, Output.GRANITE, Output.GRANITE}),
        TRANSFORM_DIO(new CustomItem(Material.FURNACE, "Transform - Diorite"), new Output[] {Output.COBBLE, Output.GRANITE, Output.AND}, new Output[] {Output.DIO, Output.DIO, Output.DIO}),
        TRANSFORM_GRAN(new CustomItem(Material.FURNACE, "Transform - Andesite"), new Output[] {Output.COBBLE, Output.DIO, Output.GRANITE}, new Output[] {Output.AND, Output.AND, Output.AND});
        
        @Nonnull
        private final ItemStack item;
        @Nonnull
        private final Output[] inputs;
        @Nonnull
        private final Output[] outputs;
    }
    
    @Getter
    @AllArgsConstructor
    private enum Output {
        COBBLE(new ItemStack(Material.COBBLESTONE), Option.values()),
        STONE(new ItemStack(Material.STONE), new Option[] {Option.COMPACT}),
        BRICK(new ItemStack(Material.STONE_BRICKS), new Option[0]),
        GRAVEL(new ItemStack(Material.GRAVEL), new Option[] {Option.CRUSH}),
        SAND(new ItemStack(Material.SAND), new Option[] {Option.FURNACE}),
        GLASS(new ItemStack(Material.GLASS), new Option[0]),
        GRANITE(new ItemStack(Material.GRANITE), new Option[] {Option.TRANSFORM_AND, Option.TRANSFORM_DIO, Option.COMPACT}),
        DIO(new ItemStack(Material.DIORITE), new Option[] {Option.TRANSFORM_AND, Option.TRANSFORM_GRAN, Option.COMPACT}),
        AND(new ItemStack(Material.ANDESITE), new Option[] {Option.TRANSFORM_GRAN, Option.TRANSFORM_DIO, Option.COMPACT}),
        GRANITE_P(new ItemStack(Material.POLISHED_GRANITE), new Option[0]),
        DIO_P(new ItemStack(Material.POLISHED_DIORITE), new Option[0]),
        AND_P(new ItemStack(Material.POLISHED_ANDESITE), new Option[0]);
        
        @Nonnull
        private final ItemStack item;
        @Nonnull
        private final Option[] options;
    }
}
