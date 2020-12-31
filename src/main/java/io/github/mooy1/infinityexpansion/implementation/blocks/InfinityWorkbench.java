package io.github.mooy1.infinityexpansion.implementation.blocks;

import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.InfinityRecipes;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.setup.InfinityCategory;
import io.github.mooy1.infinityexpansion.utils.RecipeUtils;
import io.github.mooy1.infinitylib.filter.FilterType;
import io.github.mooy1.infinitylib.filter.ItemFilter;
import io.github.mooy1.infinitylib.filter.MultiFilter;
import io.github.mooy1.infinitylib.objects.AbstractContainer;
import io.github.mooy1.infinitylib.player.MessageUtils;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A 6x6 crafting table O.o
 *
 * @author Mooy1
 */
public class InfinityWorkbench extends AbstractContainer implements EnergyNetComponent {

    public static final int ENERGY = 10_000_000;

    public static final int[] INPUT_SLOTS = {
        0, 1, 2, 3, 4, 5,
        9, 10, 11, 12, 13, 14,
        18, 19, 20, 21, 22, 23,
        27, 28, 29, 30, 31, 32,
        36, 37, 38, 39, 40, 41,
        45, 46, 47, 48, 49, 50
    };
    private static final int[] OUTPUT_SLOTS = {
            MenuPreset.slot3 + 27
    };
    private static final int STATUS_SLOT = MenuPreset.slot3;
    private static final int[] STATUS_BORDER = {
            6, 8,
            15, 17,
            24, 25, 26
    };
    private static final int RECIPE_SLOT = 7;
    private static final int EMPTY = new MultiFilter(FilterType.MIN_AMOUNT, new ItemFilter[36]).hashCode();

    public InfinityWorkbench() {
        super(Categories.MAIN_MATERIALS, Items.INFINITY_WORKBENCH, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            Items.VOID_INGOT, Items.MACHINE_PLATE, Items.VOID_INGOT,
                SlimefunItems.ENERGIZED_CAPACITOR, new ItemStack(Material.CRAFTING_TABLE), SlimefunItems.ENERGIZED_CAPACITOR,
                Items.VOID_INGOT, Items.MACHINE_PLATE, Items.VOID_INGOT
        });

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);
            Location l = b.getLocation();

            if (inv != null) {
                inv.dropItems(l, OUTPUT_SLOTS);
                inv.dropItems(l, INPUT_SLOTS);
            }

            return true;
        });
    }

    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : MenuPreset.slotChunk3) {
            blockMenuPreset.addItem(i + 27, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : STATUS_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(RECIPE_SLOT, new CustomItem(Material.BOOK, "&6Recipes"), ChestMenuUtils.getEmptyClickHandler());
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.loadingItemBarrier, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        return new int[0];
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        menu.addMenuClickHandler(STATUS_SLOT, (p, slot, item, action) -> {
            craft(b, menu, p);
            return false;
        });
        menu.addMenuClickHandler(RECIPE_SLOT, (p, slot, item, action) -> {
            InfinityCategory.open(p, new InfinityCategory.BackEntry(menu, null, null), true);
            return false;
        });
    }
    
    @Override
    public void tick(@Nonnull Block b, @Nonnull BlockMenu inv) {
        if (inv.hasViewer()) { //only active when player watching
            int charge = getCharge(b.getLocation());

            if (charge < ENERGY) { //not enough energy

                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(
                        Material.RED_STAINED_GLASS_PANE,
                        "&cNot enough energy!",
                        "",
                        "&aCharge: " + charge + "/" + ENERGY + " J",
                        ""
                ));

            } else { //enough energy

                ItemStack output = getOutput(inv);

                if (output == null) { //invalid

                    inv.replaceExistingItem(STATUS_SLOT, MenuPreset.invalidRecipe);

                } else { //correct recipe

                    inv.replaceExistingItem(STATUS_SLOT, RecipeUtils.getDisplayItem(output.clone()));

                }
            }
        }
    }

    /**
     * This method outputs the output of the current BlockMenu
     *
     * @param b the workbenches block
     * @param inv the BlockMenu
     * @param p the player crafting it
     */
    public void craft(@Nonnull Block b, @Nonnull BlockMenu inv, @Nonnull  Player p) {
        int charge = getCharge(b.getLocation());

        if (charge < ENERGY) { //not enough energy
            MessageUtils.messageWithCD(p, 1000, ChatColor.RED + "Not enough energy!", ChatColor.GREEN + "Charge: " + ChatColor.RED + charge + ChatColor.GREEN + "/" + ENERGY + " J");
            return;
        }
        
        SlimefunItemStack output = getOutput(inv);
        
        if (output == null) { //invalid
            MessageUtils.messageWithCD(p, 1000, ChatColor.RED + "Invalid Recipe!");
            return;
        }
            
        if (!inv.fits(output, OUTPUT_SLOTS)) { //not enough room
            MessageUtils.messageWithCD(p, 1000, ChatColor.GOLD + "Not enough room!");
            return;
        }

        for (int slot : INPUT_SLOTS) {
            if (inv.getItemInSlot(slot) != null) {
                inv.consumeItem(slot);
            }
        }
        
        MessageUtils.message(p, ChatColor.GREEN + "Successfully crafted: " + ChatColor.WHITE + output.getDisplayName());

        inv.pushItem(output.clone(), OUTPUT_SLOTS);
        setCharge(b.getLocation(), 0);
            
    }

    /**
     * This method returns the output item if any from a BlockMenu
     *
     * @param inv BlockMenu to check
     * @return output if any
     */
    @Nullable
    public SlimefunItemStack getOutput(@Nonnull BlockMenu inv) {
        MultiFilter filter = MultiFilter.fromMenu(FilterType.MIN_AMOUNT, inv, INPUT_SLOTS);
        if (filter.hashCode() == EMPTY) {
            return null;
        }
        return InfinityRecipes.RECIPES.get(filter);
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
}
