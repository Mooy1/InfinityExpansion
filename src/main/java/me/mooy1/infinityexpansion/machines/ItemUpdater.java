package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ItemUpdater extends SlimefunItem implements EnergyNetComponent, InventoryBlock {

    public static final int ENERGY = 200;

    private static final int[] OUTPUT_SLOTS= {
            PresetUtils.slot3
    };
    private static final int[] INPUT_SLOTS = {
            PresetUtils.slot1
    };
    private static final int INPUT_SLOT = INPUT_SLOTS[0];
    private static final int STATUS_SLOT = PresetUtils.slot2;

    public ItemUpdater() {
        super(Categories.ADVANCED_MACHINES, Items.ITEM_UPDATER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                Items.MAGSTEEL, Items.MACHINE_CIRCUIT, Items.MAGSTEEL,
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
        });

        setupInv();

        registerBlockHandler(getID(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), getOutputSlots());
                inv.dropItems(b.getLocation(), getInputSlots());
            }

            return true;
        });
    }

    private void setupInv() {
        createPreset(this, Items.ITEM_UPDATER.getDisplayName(),
                blockMenuPreset -> {
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
                });
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) { ItemUpdater.this.tick(b); }

            public boolean isSynchronized() { return false; }
        });
    }

    public void tick(Block b) {
        @Nullable final BlockMenu inv = BlockStorage.getInventory(b.getLocation());
        if (inv == null) return;

        boolean playerWatching = inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty();

        ItemStack input = inv.getItemInSlot(INPUT_SLOT);

        if (getCharge(b.getLocation()) < ENERGY) { //not enough energy

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughEnergy);
            }

        } else if (input == null) { //check input

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.inputAnItem);
            }

        } else {

            ItemStack output = null;
            int inputAmount = input.getAmount();
            if (SlimefunItem.getByItem(input) != null) {
                output = SlimefunItem.getByItem(input).getItem().clone();
                output.setAmount(inputAmount);
            }

            if (output == null) { //not sf item
                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(
                            Material.RED_STAINED_GLASS_PANE,
                            "&9Input a &aSlimefun &9item!")
                    );
                }

            } else if (!inv.fits(output, OUTPUT_SLOTS)) { //update

                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughRoom);
                }

            } else {
                if (!output.getEnchantments().isEmpty()) {
                    Map<Enchantment, Integer> enchantments = new HashMap<>();
                    int amount = 0;
                    for (Map.Entry<Enchantment, Integer> entry : output.getEnchantments().entrySet()) {
                        enchantments.put(entry.getKey(), entry.getValue());
                        amount++;
                    }
                    if (amount > 0 ) {
                        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                            output.removeEnchantment(entry.getKey());
                        }
                    }
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

    @Override
    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }
}
