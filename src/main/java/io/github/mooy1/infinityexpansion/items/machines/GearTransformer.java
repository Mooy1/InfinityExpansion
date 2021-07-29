package io.github.mooy1.infinityexpansion.items.machines;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.items.abstracts.AbstractEnergyCrafter;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

/**
 * Machine that changes the material of gear and tools
 *
 * @author Mooy1
 */
public final class GearTransformer extends AbstractEnergyCrafter implements RecipeDisplayItem {

    private static final boolean SF = InfinityExpansion.inst().getConfig().getBoolean("balance-options.allow-sf-item-transform");
    private static final int[] OUTPUT_SLOTS = {
            MenuPreset.STATUS + 27
    };
    private static final int[] BACKGROUND = {
            27, 29, 33, 35,
            36, 44,
            45, 46, 47, 51, 52, 53
    };
    private static final int[] OUTPUT_BORDER = {
            28, 34, 37, 38, 42, 43
    };
    private static final int[] INPUT_SLOTS = {
            MenuPreset.INPUT, MenuPreset.OUTPUT
    };
    private static final int INPUT_SLOT1 = INPUT_SLOTS[0];
    private static final int INPUT_SLOT2 = INPUT_SLOTS[1];
    private static final int STATUS_SLOT = MenuPreset.STATUS;

    public GearTransformer(Category category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe, int energy) {
        super(category, item, type, recipe, energy, STATUS_SLOT);
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu inv, @Nonnull Location l) {
        inv.dropItems(l, OUTPUT_SLOTS);
        inv.dropItems(l, INPUT_SLOTS);
    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : BACKGROUND) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.INPUT_BORDER) {
            blockMenuPreset.addItem(i, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, "&9Tool Input"), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.OUTPUT_BORDER) {
            blockMenuPreset.addItem(i, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, "&9Material Input"), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : OUTPUT_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.OUTPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.STATUS_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.STATUS_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.STATUS_BORDER) {
            blockMenuPreset.addItem(i + 27, MenuPreset.OUTPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.INVALID_INPUT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {

    }

    @Nullable
    private static Pair<Material, Integer> getOutput(ItemStack inputMaterial, String inputToolType) {

        for (String toolType : TOOL_TYPES) {
            if (inputToolType.equals(toolType)) { //make sure its a tool

                for (int i = 0 ; i < TOOL_RECIPE.length ; i++) { //compare to each recipe
                    ItemStack recipe = TOOL_RECIPE[i];

                    if (inputMaterial.getType() == recipe.getType() && inputMaterial.getAmount() >= recipe.getAmount()) {

                        return new Pair<>(Material.getMaterial(TOOL_MATERIALS[i] + toolType), recipe.getAmount());
                    }
                }

                break;
            }
        }

        for (String armorType : ARMOR_TYPES) {
            if (inputToolType.equals(armorType)) { //make sure its a armor

                for (int i = 0 ; i < ARMOR_RECIPE.length ; i++) { //compare to each recipe
                    ItemStack recipe = ARMOR_RECIPE[i];

                    if (inputMaterial.getType() == recipe.getType() && inputMaterial.getAmount() >= recipe.getAmount()) {

                        return new Pair<>(Material.getMaterial(ARMOR_MATERIALS[i] + armorType), recipe.getAmount());
                    }
                }

                break;
            }
        }

        return null;
    }

    @Nullable
    private static String getType(ItemStack item) {
        Material material = item.getType();

        for (String armorType : ARMOR_TYPES) {

            for (String armorMaterial : ARMOR_MATERIALS) {

                if (material == Material.getMaterial(armorMaterial + armorType)) return armorType;
            }
        }

        for (String toolType : TOOL_TYPES) {

            for (String toolMaterial : TOOL_MATERIALS) {

                if (material == Material.getMaterial(toolMaterial + toolType)) return toolType;
            }
        }
        return null;

    }

    private static final String[] ARMOR_TYPES = {
            "_HELMET",
            "_CHESTPLATE",
            "_LEGGINGS",
            "_BOOTS"
    };
    private static final String[] TOOL_TYPES = {
            "_SWORD",
            "_PICKAXE",
            "_AXE",
            "_SHOVEL",
            "_HOE"
    };
    private static final String[] TOOL_MATERIALS = {
            "WOODEN",
            "STONE",
            "IRON",
            "GOLDEN",
            "DIAMOND",
            "NETHERITE"
    };
    private static final String[] ARMOR_MATERIALS = {
            "LEATHER",
            "CHAINMAIL",
            "IRON",
            "GOLDEN",
            "DIAMOND",
            "NETHERITE"
    };

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0 ; i < TOOL_RECIPE.length ; i++) {
            items.add(TOOL_RECIPE[i]);
            items.add(ARMOR_RECIPE[i]);
        }

        return items;
    }

    private static final ItemStack[] TOOL_RECIPE = {
            new ItemStack(Material.OAK_PLANKS, 4),
            new ItemStack(Material.COBBLESTONE, 4),
            new ItemStack(Material.IRON_INGOT, 4),
            new ItemStack(Material.GOLD_INGOT, 4),
            new ItemStack(Material.DIAMOND, 4),
            new ItemStack(Material.NETHERITE_INGOT, 2)
    };

    private static final ItemStack[] ARMOR_RECIPE = {
            new ItemStack(Material.LEATHER, 9),
            new ItemStack(Material.CHAIN, 9),
            new ItemStack(Material.IRON_INGOT, 9),
            new ItemStack(Material.GOLD_INGOT, 9),
            new ItemStack(Material.DIAMOND, 9),
            new ItemStack(Material.NETHERITE_INGOT, 2)
    };

    @Override
    public void update(@Nonnull BlockMenu inv) {
        ItemStack inputItem = inv.getItemInSlot(INPUT_SLOT1);

        if (inputItem == null) { //no input

            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, "&9Input a tool or piece of gear"));
            return;

        }

        if (!SF && StackUtils.getID(inputItem) != null) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.RED_STAINED_GLASS_PANE, "&cSlimefun items may not have their material changed!"));
            return;
        }

        String inputToolType = getType(inputItem);

        if (inputToolType == null) { //invalid input

            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cNot a tool or piece of gear!"));
            return;

        }

        ItemStack inputMaterial = inv.getItemInSlot(INPUT_SLOT2);

        if (inputMaterial == null) { //no material

            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, "&9Input materials"));
            return;

        }

        Pair<Material, Integer> pair = getOutput(inputMaterial, inputToolType);

        if (pair == null) { //invalid material

            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cInvalid Materials!"));
            return;

        }

        if (inv.getItemInSlot(OUTPUT_SLOTS[0]) != null) { //valid material, not enough room

            inv.replaceExistingItem(STATUS_SLOT, MenuPreset.NO_ROOM);
            return;

        }

        //output
        setCharge(inv.getLocation(), 0);

        inputItem.setType(pair.getFirstValue());
        inv.pushItem(inputItem, OUTPUT_SLOTS);

        inv.replaceExistingItem(INPUT_SLOT1, null);
        inv.consumeItem(INPUT_SLOT2, pair.getSecondValue());

        inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aTool Transformed!"));
    }

}
