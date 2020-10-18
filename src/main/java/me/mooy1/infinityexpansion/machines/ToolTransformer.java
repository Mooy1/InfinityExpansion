package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.utils.MessageUtils;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ToolTransformer extends SlimefunItem implements InventoryBlock, EnergyNetComponent, RecipeDisplayItem {

    public static final int ENERGY = 100000;

    private static final int[] OUTPUT_SLOTS = {
            PresetUtils.slot2 + 27
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
            PresetUtils.slot1, PresetUtils.slot3
    };
    private static final int INPUT_SLOT1 = INPUT_SLOTS[0];
    private static final int INPUT_SLOT2 = INPUT_SLOTS[1];
    private static final int STATUS_SLOT = PresetUtils.slot2;

    public ToolTransformer() {
        super(Categories.ADVANCED_MACHINES, Items.TOOL_TRANSFORMER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {

        });

        setupInv();

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), OUTPUT_SLOTS);
                inv.dropItems(b.getLocation(), INPUT_SLOTS);
            }

            return true;
        });
    }

    private void setupInv() {
        createPreset(this, Items.TOOL_TRANSFORMER.getDisplayName(),
                blockMenuPreset -> {
                    for (int i : BACKGROUND) {
                        blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : PresetUtils.slotChunk1) {
                        blockMenuPreset.addItem(i, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, "&9Tool Input"), ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : PresetUtils.slotChunk3) {
                        blockMenuPreset.addItem(i, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, "&9Material Input"), ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : OUTPUT_BORDER) {
                        blockMenuPreset.addItem(i, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : PresetUtils.slotChunk2) {
                        blockMenuPreset.addItem(i, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
                    }
                    for (int i : PresetUtils.slotChunk2) {
                        blockMenuPreset.addItem(i + 27, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
                    }
                    blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemBarrier,
                            ChestMenuUtils.getEmptyClickHandler());
                });
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) { ToolTransformer.this.tick(b); }

            public boolean isSynchronized() { return false; }
        });
    }

    public void tick(Block b) {
        @Nullable final BlockMenu inv = BlockStorage.getInventory(b.getLocation());
        if (inv == null) return;

        if (inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty()) {

            if (getCharge(b.getLocation()) < ENERGY) { //not enough energy

                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughEnergy);

            } else {

                ItemStack inputItem = inv.getItemInSlot(INPUT_SLOT1);

                if (inputItem == null) { //no input

                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, "&9Input a tool"));

                } else {

                    String inputToolType;

                    if (getToolType(inputItem) != null) {
                        inputToolType = getToolType(inputItem);
                    } else {
                        inputToolType = getArmorType(inputItem);
                    }

                    if (inputToolType == null) { //invalid input

                        inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cNot a tool or armor!"));

                        /*if (inv.getItemInSlot(OUTPUT_SLOTS[0]) == null) {
                            inv.pushItem(inputItem, OUTPUT_SLOTS);
                            inv.consumeItem(INPUT_SLOT1, inputItem.getAmount());
                        }*/

                    } else { //valid input

                        ItemStack inputMaterial = inv.getItemInSlot(INPUT_SLOT2);

                        if (inputMaterial == null) { //no material

                            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BLUE_STAINED_GLASS_PANE, "&9Input materials"));

                        } else {

                            Material outputMaterial = getOutput(inputMaterial, inputToolType);

                            if (outputMaterial == null) { //invalid material

                            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cInvalid Materials!"));

                            /*if (inv.getItemInSlot(OUTPUT_SLOTS[0]) == null) {
                                inv.pushItem(inputMaterial, OUTPUT_SLOTS);
                                inv.consumeItem(INPUT_SLOT2, inputMaterial.getAmount());
                            }*/

                            } else if (inv.getItemInSlot(OUTPUT_SLOTS[0]) != null){ //valid material, not enough room

                                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughRoom);

                            } else { //output

                                removeCharge(b.getLocation(), ENERGY);

                                MessageUtils.messagePlayersInInv(inv, "Transformed into: " + ItemUtils.getItemName(new ItemStack(outputMaterial)));

                                inputItem.setType(outputMaterial);
                                inv.pushItem(inputItem, OUTPUT_SLOTS);

                                inv.consumeItem(INPUT_SLOT1);
                                inv.consumeItem(INPUT_SLOT2, getAmount(inputMaterial, inputToolType));

                                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aTool Transformed!"));

                            }
                        }
                    }
                }
            }
        }
    }

    private Material getOutput(ItemStack inputMaterial, String inputToolType) {

        for (String toolType : TOOL_TYPES) {
            if (inputToolType.equals(toolType)) { //make sure its a tool

                for (int i = 0; i < TOOL_RECIPE.length ; i++) { //compare to each recipe
                    ItemStack recipe = TOOL_RECIPE[i];

                    if (inputMaterial.getType() == recipe.getType() && inputMaterial.getAmount() >= recipe.getAmount()) {
                        return Material.getMaterial(TOOL_MATERIALS[i] + toolType);
                    }
                }
            }
        }

        for (String armorType : ARMOR_TYPES) {
            if (inputToolType.equals(armorType)) { //make sure its a armor

                for (int i = 0; i < ARMOR_RECIPE.length ; i++) { //compare to each recipe
                    ItemStack recipe = ARMOR_RECIPE[i];

                    if (inputMaterial.getType() == recipe.getType() && inputMaterial.getAmount() >= recipe.getAmount()) {

                        return Material.getMaterial(ARMOR_MATERIALS[i] + armorType);
                    }
                }
            }
        }

        return null;
    }

    private int getAmount(ItemStack inputMaterial, String inputToolType) {

        for (String toolType : TOOL_TYPES) {

            if (inputToolType.equals(toolType)) {

                for (ItemStack input : TOOL_RECIPE) {

                    if (inputMaterial.getType() == input.getType() && inputMaterial.getAmount() >= input.getAmount()) {

                        return input.getAmount();
                    }
                }
            }
        }

        for (String armorType : ARMOR_TYPES) {

            if (inputToolType.equals(armorType)) {

                for (ItemStack input : ARMOR_RECIPE) {

                    if (inputMaterial.getType() == input.getType() && inputMaterial.getAmount() >= input.getAmount()) {
                        
                        return input.getAmount();
                    }
                }
            }
        }

        return 0;
    }

    private String getToolType(ItemStack item) {
        Material material = item.getType();

        for (String toolType : TOOL_TYPES) {

            for (String toolMaterial : TOOL_MATERIALS) {

                if (material == Material.getMaterial(toolMaterial + toolType)) return toolType;
            }
        }
        return null;
    }

    private String getArmorType(ItemStack item) {
        Material material = item.getType();

        for (String armorType : ARMOR_TYPES) {

            for (String armorMaterial : ARMOR_MATERIALS) {

                if (material == Material.getMaterial(armorMaterial + armorType)) return armorType;
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
        return ENERGY;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();

        items.add(new CustomItem(Material.DIAMOND_PICKAXE, "&7For Tools >>>"));
        items.add(new CustomItem(Material.DIAMOND_CHESTPLATE, "&7For Armor >>>"));

        for (int i = 0; i < TOOL_RECIPE.length ; i++) {
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
    public int[] getInputSlots() {
        return new int[0];
    }

    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }
}
