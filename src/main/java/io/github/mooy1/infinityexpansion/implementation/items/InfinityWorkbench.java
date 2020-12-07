package io.github.mooy1.infinityexpansion.implementation.items;

import io.github.mooy1.infinityexpansion.implementation.abstracts.Container;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.InfinityRecipes;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.setup.InfinityCategory;
import io.github.mooy1.infinityexpansion.utils.MessageUtils;
import io.github.mooy1.infinityexpansion.utils.PresetUtils;
import io.github.mooy1.infinityexpansion.utils.RecipeUtils;
import io.github.mooy1.infinityexpansion.utils.StackUtils;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * A 6x6 crafting table O.o
 *
 * @author Mooy1
 */
public class InfinityWorkbench extends Container implements EnergyNetComponent {

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
        PresetUtils.slot3 + 27
    };
    private static final int STATUS_SLOT = PresetUtils.slot3;
    private static final int[] STATUS_BORDER = {
            6, 8,
            15, 17,
            24, 25, 26
    };
    private static final int RECIPE_SLOT = 7;

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
        for (int i : PresetUtils.slotChunk3) {
            blockMenuPreset.addItem(i + 27, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : STATUS_BORDER) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(RECIPE_SLOT, new CustomItem(Material.BOOK, "&6Recipes"),
                ChestMenuUtils.getEmptyClickHandler());
        blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemBarrier,
                ChestMenuUtils.getEmptyClickHandler());
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
            InfinityCategory.openFromWorkBench(p, menu);
            return false;
        });
    }
    
    @Override
    public void tick(@Nonnull Block b, @Nonnull Location l, @Nonnull BlockMenu inv) {
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

                    inv.replaceExistingItem(STATUS_SLOT, PresetUtils.invalidRecipe);

                } else { //correct recipe

                    inv.replaceExistingItem(STATUS_SLOT, RecipeUtils.getDisplayItem(output));

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
        
        ItemStack output = getOutput(inv);
        
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
        MessageUtils.message(p, ChatColor.GREEN + "Successfully crafted: " + ChatColor.WHITE + ItemUtils.getItemName(output));

        inv.pushItem(output, OUTPUT_SLOTS);
        setCharge(b.getLocation(), 0);
            
    }

    /**
     * This method returns the output item if any from a BlockMenu
     *
     * @param inv BlockMenu to check
     * @return output if any
     */
    @Nullable
    public ItemStack getOutput(@Nonnull BlockMenu inv) {

        String[] input = new String[36];

        for (int i = 0 ; i < 36 ; i++) {
            ItemStack inputItem = inv.getItemInSlot(INPUT_SLOTS[i]);

            input[i] = StackUtils.getIDFromItem(inputItem);
        }
        
        for (ItemStack[] recipe : InfinityRecipes.RECIPES.values()) {
            int amount = 0;
            for (int i = 0 ; i < input.length ; i++) {
                String item = StackUtils.getIDFromItem(recipe[i]);
                if (Objects.equals(input[i], item)) {
                    amount++;
                } else {
                    break;
                }
            }
            if (amount == 36) {
                return InfinityRecipes.RECIPES.inverse().get(recipe).clone();
            }
        }
        return null;
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
