package io.github.mooy1.infinityexpansion.items.machines;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.items.abstracts.AbstractEnergyCrafter;
import io.github.mooy1.infinitylib.common.StackUtils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;

/**
 * Machine that changes the material of gear and tools
 *
 * @author Mooy1
 */
@ParametersAreNonnullByDefault
public final class GearTransformer extends AbstractEnergyCrafter implements RecipeDisplayItem {

    private static final boolean SF = InfinityExpansion.config().getBoolean("balance-options.allow-sf-item-transform");
    private static final int[] OUTPUT_SLOTS = { 40 };
    private static final int[] INPUT_SLOTS = { 10, 16 };
    private static final int STATUS_SLOT = 13;
    private static final ItemStack[] TOOL_RECIPE = {
            new ItemStack(Material.OAK_PLANKS, 4),
            new ItemStack(Material.COBBLESTONE, 4),
            new ItemStack(Material.IRON_INGOT, 4),
            new ItemStack(Material.GOLD_INGOT, 4),
            new ItemStack(Material.DIAMOND, 4),
            new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1)
    };
    private static final ItemStack[] ARMOR_RECIPE = {
            new ItemStack(Material.LEATHER, 9),
            new ItemStack(Material.CHAIN, 9),
            new ItemStack(Material.IRON_INGOT, 9),
            new ItemStack(Material.GOLD_INGOT, 9),
            new ItemStack(Material.DIAMOND, 9),
            new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1)
    };
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

    public GearTransformer(ItemGroup category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe, int energy) {
        super(category, item, type, recipe, energy, STATUS_SLOT);
    }

    @Override
    protected void setup(@Nonnull BlockMenuPreset blockMenuPreset) {
        blockMenuPreset.drawBackground(new int[] {
                3, 4, 5,
                12, STATUS_SLOT, 14,
                21, 22 , 23,
                27, 29, 33, 35,
                36, 44,
                45, 46, 47, 51, 52, 53
        });
        blockMenuPreset.drawBackground(OUTPUT_BORDER, new int[] {
                28, 30, 31, 32, 34,
                37, 38, 39, 41, 42, 43,
                48, 49, 50
        });
        blockMenuPreset.drawBackground(new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE, "&9Tool Input"), new int[] {
                0, 1, 2,
                9, 11,
                18, 19, 20
        });
        blockMenuPreset.drawBackground(new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE, "&9Material Input"), new int[] {
                6, 7, 8,
                15, 17,
                24, 25, 26
        });
    }

    @Override
    protected int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    protected int[] getOutputSlots() {
        return OUTPUT_SLOTS;
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

                if (material == Material.getMaterial(armorMaterial + armorType)) {
                    return armorType;
                }
            }
        }

        for (String toolType : TOOL_TYPES) {

            for (String toolMaterial : TOOL_MATERIALS) {

                if (material == Material.getMaterial(toolMaterial + toolType)) {
                    return toolType;
                }
            }
        }
        return null;

    }

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

    @Override
    public void update(@Nonnull BlockMenu inv) {
        ItemStack inputItem = inv.getItemInSlot(INPUT_SLOTS[0]);

        if (inputItem == null) { //no input

            inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE, "&9Input a tool or piece of gear"));
            return;

        }

        if (!SF && StackUtils.getId(inputItem) != null) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.RED_STAINED_GLASS_PANE, "&cSlimefun items may not have their material changed!"));
            return;
        }

        String inputToolType = getType(inputItem);

        if (inputToolType == null) { //invalid input

            inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.BARRIER, "&cNot a tool or piece of gear!"));
            return;

        }

        ItemStack inputMaterial = inv.getItemInSlot(INPUT_SLOTS[1]);

        if (inputMaterial == null) { //no material

            inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.BLUE_STAINED_GLASS_PANE, "&9Input materials"));
            return;

        }

        Pair<Material, Integer> pair = getOutput(inputMaterial, inputToolType);

        if (pair == null) { //invalid material

            inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.BARRIER, "&cInvalid Materials!"));
            return;

        }

        if (inv.getItemInSlot(OUTPUT_SLOTS[0]) != null) { //valid material, not enough room

            inv.replaceExistingItem(STATUS_SLOT, NO_ROOM_ITEM);
            return;

        }

        //output
        setCharge(inv.getLocation(), 0);

        inputItem.setType(pair.getFirstValue());
        inv.pushItem(inputItem, OUTPUT_SLOTS);

        inv.replaceExistingItem(INPUT_SLOTS[0], null);
        inv.consumeItem(INPUT_SLOTS[1], pair.getSecondValue());

        inv.replaceExistingItem(STATUS_SLOT, new CustomItemStack(Material.LIME_STAINED_GLASS_PANE, "&aTool Transformed!"));
    }

}
