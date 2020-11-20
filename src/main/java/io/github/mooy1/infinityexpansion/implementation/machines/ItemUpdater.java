package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.implementation.items.VeinMinerRune;
import io.github.mooy1.infinityexpansion.implementation.template.LoreStorage;
import io.github.mooy1.infinityexpansion.implementation.template.Machine;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.MessageUtils;
import io.github.mooy1.infinityexpansion.utils.PresetUtils;
import io.github.mooy1.infinityexpansion.utils.StackUtils;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

/**
 * A machine that makes a new slimefun item from the inputted item's id
 * will transfer same enchants and transfer storage units stored items
 *
 * @author Mooy1
 */
public class ItemUpdater extends Machine implements EnergyNetComponent {

    public static final int ENERGY = 200;

    private static final int[] OUTPUT_SLOTS = {
            PresetUtils.slot3
    };
    private static final int[] INPUT_SLOTS = {
            PresetUtils.slot1
    };
    private static final int INPUT_SLOT = INPUT_SLOTS[0];
    private static final int STATUS_SLOT = PresetUtils.slot2;

    public ItemUpdater() {
        super(Categories.BASIC_MACHINES, Items.ITEM_UPDATER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                Items.MAGSTEEL, Items.MACHINE_CIRCUIT, Items.MAGSTEEL,
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
        });

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

    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : PresetUtils.slotChunk3) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.slotChunk2) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.slotChunk1) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemRed,
                ChestMenuUtils.getEmptyClickHandler());
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
    
    @Override
    public void tick(@Nonnull Block b, @Nonnull Location l, @Nonnull BlockMenu inv) {
        boolean playerWatching = inv.hasViewer();

        ItemStack input = inv.getItemInSlot(INPUT_SLOT);

        if (getCharge(b.getLocation()) < ENERGY) { //not enough energy

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughEnergy);
            }
            return;
        }

        if (input == null) { //check input

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.inputAnItem);
            }
            return;
        }

        ItemStack output = null;
        int inputAmount = input.getAmount();
        if (SlimefunItem.getByItem(input) != null) {
            SlimefunItem slimefunItem = SlimefunItem.getByItem(input);
            if (slimefunItem != null) {
                output = slimefunItem.getItem().clone();
                output.setAmount(inputAmount);

                if (slimefunItem instanceof LoreStorage) {
                    ((LoreStorage) slimefunItem).transfer(output, input);
                }
            }
        }

        if (output == null) { //not sf item
            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(
                        Material.RED_STAINED_GLASS_PANE,
                        "&9Input a &aSlimefun &9item!")
                );
            }
            return;

        }

        if (!inv.fits(output, OUTPUT_SLOTS)) { //update

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughRoom);
            }
            return;
        }

        //transfer durability
        if (input.getItemMeta() instanceof Damageable) {
            ItemMeta outputMeta = output.getItemMeta();
            if (outputMeta == null) return;
            Damageable outputDurability = (Damageable) outputMeta;

            if (input.getItemMeta() instanceof Damageable) {
                ItemMeta inputMeta = input.getItemMeta();
                if (inputMeta == null) return;
                Damageable inputDurability = (Damageable) inputMeta;

                outputDurability.setDamage(inputDurability.getDamage());
                output.setItemMeta(outputMeta);
            }
        }
        
        if (VeinMinerRune.isVeinMiner(input)) {
            VeinMinerRune.setVeinMiner(output, true);
        }
        
        if (SlimefunUtils.isSoulbound(input)) {
            SlimefunUtils.setSoulbound(output, true);
        }
        
        StackUtils.removeEnchants(output);

        output.addUnsafeEnchantments(input.getEnchantments());

        if (!output.getEnchantments().isEmpty()) {
            MessageUtils.messagePlayersInInv(inv, ChatColor.GREEN + "Enchantments transferred!");
        }

        removeCharge(b.getLocation(), ENERGY);
        inv.consumeItem(INPUT_SLOT, inputAmount);
        inv.pushItem(output, OUTPUT_SLOTS);

        if (playerWatching) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(
                    Material.LIME_STAINED_GLASS_PANE,
                    "&aItem Updated!")
            );
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
}
