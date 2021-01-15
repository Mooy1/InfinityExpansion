package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.implementation.materials.MachineItem;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.objects.AbstractContainer;
import io.github.mooy1.infinitylib.player.MessageUtils;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Machine that changes the material of gear and tools
 *
 * @author Mooy1
 */
public final class GearTransformer extends AbstractContainer implements EnergyNetComponent, RecipeDisplayItem {
    
    public static final SlimefunItemStack ITEM = new SlimefunItemStack(
            "GEAR_TRANSFORMER",
            Material.EMERALD_BLOCK,
            "&7Gear Transformer",
            "&7Changes the material of vanilla tools and gear",
            "",
            LorePreset.energy(GearTransformer.ENERGY) + "Per Use"
    );

    public static final int ENERGY = 12000;
    public static boolean sf = false;
    private static final int[] OUTPUT_SLOTS = {
            MenuPreset.slot2 + 27
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
            MenuPreset.slot1, MenuPreset.slot3
    };
    private static final int INPUT_SLOT1 = INPUT_SLOTS[0];
    private static final int INPUT_SLOT2 = INPUT_SLOTS[1];
    private static final int STATUS_SLOT = MenuPreset.slot2;

    public GearTransformer() {
        super(Categories.ADVANCED_MACHINES, ITEM, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                MachineItem.MAGSTEEL_PLATE, MachineItem.MACHINE_CIRCUIT, MachineItem.MAGSTEEL_PLATE,
                MachineItem.MACHINE_CIRCUIT, new ItemStack(Material.SMITHING_TABLE), MachineItem.MACHINE_CIRCUIT,
                MachineItem.MAGSTEEL_PLATE, MachineItem.MACHINE_CIRCUIT, MachineItem.MAGSTEEL_PLATE
        });

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), OUTPUT_SLOTS);
                inv.dropItems(b.getLocation(), INPUT_SLOTS);
            }

            return true;
        });
    }

    @Override
    public void tick(@Nonnull Block b, @Nonnull BlockMenu inv) {
        if (!inv.hasViewer()) return;

        if (getCharge(b.getLocation()) < ENERGY) { //not enough energy

            inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughEnergy);
            return;

        }

        ItemStack inputItem = inv.getItemInSlot(INPUT_SLOT1);

        if (inputItem == null) { //no input

            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, "&9Input a tool or piece of gear"));
            return;

        }
        
        if (!sf && StackUtils.getID(inputItem) != null) {
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

            inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughRoom);
            return;

        }

        //output
        removeCharge(b.getLocation(), ENERGY);

        MessageUtils.messagePlayersInInv(inv, "Transformed into: " + pair.getFirstValue().toString().toUpperCase(Locale.ROOT));

        inputItem.setType(pair.getFirstValue());
        inv.pushItem(inputItem, OUTPUT_SLOTS);

        inv.replaceExistingItem(INPUT_SLOT1, null);
        inv.consumeItem(INPUT_SLOT2, pair.getSecondValue());

        inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aTool Transformed!"));
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        
    }

    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : BACKGROUND) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk1) {
            blockMenuPreset.addItem(i, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, "&9Tool Input"), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk3) {
            blockMenuPreset.addItem(i, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, "&9Material Input"), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : OUTPUT_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk2) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk2) {
            blockMenuPreset.addItem(i + 27, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.loadingItemBarrier, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        if (flow == ItemTransportFlow.INSERT) {
            return INPUT_SLOTS;
        } else if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        } else {
            return new int[0];
        }
    }

    /**
     * This method gets the output from the input material and input tool
     *
     * @param inputMaterial material
     * @param inputToolType tools type
     *
     * @return output if any
     */
    @Nullable
    private Pair<Material, Integer> getOutput(ItemStack inputMaterial, String inputToolType) {

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
    private String getType(ItemStack item) {
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
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return ENERGY * 2;
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

}
