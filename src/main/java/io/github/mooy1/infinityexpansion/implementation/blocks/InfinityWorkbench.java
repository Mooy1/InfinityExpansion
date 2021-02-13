package io.github.mooy1.infinityexpansion.implementation.blocks;

import io.github.mooy1.infinityexpansion.implementation.abstracts.AbstractEnergyCrafter;
import io.github.mooy1.infinityexpansion.implementation.materials.CompressedItem;
import io.github.mooy1.infinityexpansion.implementation.materials.MachineItem;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.mooy1.infinityexpansion.setup.categories.InfinityCategory;
import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.player.MessageUtils;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.mooy1.infinitylib.recipes.large.LargeOutput;
import io.github.mooy1.infinitylib.recipes.large.LargeRecipeMap;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * A 6x6 crafting table O.o
 *
 * @author Mooy1
 */
public final class InfinityWorkbench extends AbstractEnergyCrafter {

    private static final int ENERGY = 10_000_000;
    public static final SlimefunItemStack ITEM = new SlimefunItemStack(
            "INFINITY_FORGE",
            Material.RESPAWN_ANCHOR,
            "&6Infinity Workbench",
            "&7Used to craft infinity items",
            "",
            LorePreset.energy(ENERGY) + "per item"
    );
    
    public static final int[] INPUT_SLOTS = {
        0, 1, 2, 3, 4, 5,
        9, 10, 11, 12, 13, 14,
        18, 19, 20, 21, 22, 23,
        27, 28, 29, 30, 31, 32,
        36, 37, 38, 39, 40, 41,
        45, 46, 47, 48, 49, 50
    };
    
    private static final int[] OUTPUT_SLOTS = {MenuPreset.slot3 + 27};
    private static final int STATUS_SLOT = MenuPreset.slot3;
    private static final int[] STATUS_BORDER = {6, 8, 15, 17, 24, 25, 26};
    private static final int RECIPE_SLOT = 7;
    
    public static final LargeRecipeMap RECIPES = new LargeRecipeMap(36);
    public static final LinkedHashMap<String, Pair<SlimefunItemStack, ItemStack[]>> ITEMS = new LinkedHashMap<>();
    public static final List<String> IDS = new ArrayList<>();
    
    public static final RecipeType TYPE = new RecipeType(PluginUtils.getKey("infinity_forge"), ITEM, (stacks, stack) -> {
        if (stacks.length == 36 && stack instanceof SlimefunItemStack) {
            SlimefunItemStack item = (SlimefunItemStack) stack;
            RECIPES.put(stacks, item);
            ITEMS.put(item.getItemId(), new Pair<>(item, stacks));
            IDS.add(item.getItemId());
        }
    }, "", "&cUse the infinity recipes category to see the correct recipe!");
    
    public InfinityWorkbench() {
        super(Categories.MAIN_MATERIALS, ITEM, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            CompressedItem.VOID_INGOT, MachineItem.MACHINE_PLATE, CompressedItem.VOID_INGOT,
                SlimefunItems.ENERGIZED_CAPACITOR, new ItemStack(Material.CRAFTING_TABLE), SlimefunItems.ENERGIZED_CAPACITOR,
                CompressedItem.VOID_INGOT, MachineItem.MACHINE_PLATE, CompressedItem.VOID_INGOT
        }, ENERGY, STATUS_SLOT);

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                Location l = b.getLocation();
                inv.dropItems(l, OUTPUT_SLOTS);
                inv.dropItems(l, INPUT_SLOTS);
            }

            return true;
        });
    }
    
    @Override
    public void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : MenuPreset.slotChunk3) {
            blockMenuPreset.addItem(i + 27, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : STATUS_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(RECIPE_SLOT, new CustomItem(Material.BOOK, "&6Recipes"), ChestMenuUtils.getEmptyClickHandler());
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.invalidInput, ChestMenuUtils.getEmptyClickHandler());
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

    public void craft(@Nonnull Block b, @Nonnull BlockMenu inv, @Nonnull  Player p) {
        int charge = getCharge(b.getLocation());
         
        if (charge < ENERGY) { //not enough energy
            MessageUtils.messageWithCD(p, 1000,
                    ChatColor.RED + "Not enough energy!",
                    ChatColor.GREEN + "Charge: " + ChatColor.RED + charge + ChatColor.GREEN + "/" + ENERGY + " J"
            );
            return;
        }
        
        LargeOutput output = RECIPES.get(inv, INPUT_SLOTS);
        
        if (output == null) { //invalid
            MessageUtils.messageWithCD(p, 1000, ChatColor.RED + "Invalid Recipe!");
            return;
        }
            
        if (!inv.fits(output.getOutput(), OUTPUT_SLOTS)) { //not enough room
            MessageUtils.messageWithCD(p, 1000, ChatColor.GOLD + "Not enough room!");
            return;
        }

        for (int slot : INPUT_SLOTS) {
            if (inv.getItemInSlot(slot) != null) {
                inv.consumeItem(slot);
            }
        }
        
        MessageUtils.message(p, ChatColor.GREEN + "Successfully crafted: " + ChatColor.WHITE + output.getOutput().getItemMeta().getDisplayName());

        inv.pushItem(output.getOutput().clone(), OUTPUT_SLOTS);
        setCharge(b.getLocation(), 0);
            
    }
    
    @Override
    public void update(@Nonnull BlockMenu inv) {

        LargeOutput output = RECIPES.get(inv, INPUT_SLOTS);

        if (output == null) { //invalid

            inv.replaceExistingItem(STATUS_SLOT, MenuPreset.invalidRecipe);

        } else { //correct recipe

            inv.replaceExistingItem(STATUS_SLOT, Util.getDisplayItem(output.getOutput().clone()));
            
        }
        
    }

}
