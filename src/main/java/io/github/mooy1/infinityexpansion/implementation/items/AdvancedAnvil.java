package io.github.mooy1.infinityexpansion.implementation.items;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import io.github.mooy1.infinityexpansion.implementation.template.Machine;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.MessageUtils;
import io.github.mooy1.infinityexpansion.utils.PresetUtils;
import io.github.mooy1.infinityexpansion.utils.RecipeUtils;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Combines slimefun items, exceeds vanilla anvil limits, ded
 *
 * @author Mooy1
 */
public class AdvancedAnvil extends Machine implements EnergyNetComponent {

    public static final int ENERGY = 100_000;
    
    private static final Map<Enchantment, Integer> UPGRADEABLE = new HashMap<>();
    
    static {
        UPGRADEABLE.put(Enchantment.DAMAGE_ALL, 9);
        UPGRADEABLE.put(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
        UPGRADEABLE.put(Enchantment.DURABILITY, 13);
        UPGRADEABLE.put(Enchantment.LOOT_BONUS_BLOCKS, 6);
    }

    private static final int[] INPUT_SLOTS = {
            PresetUtils.slot1, PresetUtils.slot2
    };
    private static final int INPUT_SLOT1 = INPUT_SLOTS[0];
    private static final int INPUT_SLOT2 = INPUT_SLOTS[1];
    private static final int[] OUTPUT_SLOTS = {
            PresetUtils.slot3
    };
    private static final int STATUS_SLOT = PresetUtils.slot2 + 27;
    private static final int[] OTHER_STATUS = {47, 51};
    private static final int[] BACKGROUND = {
            27, 28, 29, 33, 34, 35,
            36, 37, 38, 42, 43, 44,
            45, 46, 52, 53
    };

    public AdvancedAnvil() {
        super(Categories.MAIN, Items.ADVANCED_ANVIL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.ADAMANTITE, Items.ADAMANTITE, Items.ADAMANTITE,
                Items.MAGNONIUM, new ItemStack(Material.ANVIL), Items.MAGNONIUM,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        });

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                Location l = b.getLocation();
                inv.dropItems(l, INPUT_SLOTS);
                inv.dropItems(l, OUTPUT_SLOTS);
            }

            return true;
        });
    }

    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : PresetUtils.slotChunk3) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : BACKGROUND) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.slotChunk1) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.slotChunk2) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.slotChunk2) {
            blockMenuPreset.addItem(i + 27, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : OTHER_STATUS) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemBarrier,
                ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        return new int[0];
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        menu.addMenuClickHandler(STATUS_SLOT, (player, i, itemStack, clickAction) -> {
            craft(menu, b.getLocation(), player);
            return false;
        });
    }

    @Override
    public void tick(@Nonnull Block b, @Nonnull Location l, @Nonnull BlockMenu inv) {
        if (getCharge(l) < ENERGY) { //not enough energy
            inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughEnergy);
            return;
        }

        ItemStack item1 = inv.getItemInSlot(INPUT_SLOT1);
        ItemStack item2 = inv.getItemInSlot(INPUT_SLOT2);

        if (item1 == null || item2 == null || item1.getType() != item2.getType()) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cInvalid items!"));
            return;
        }

        ItemStack output = getOutput(item1, item2);

        if (output == null) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cNo upgrades!"));
            return;
        }

        inv.replaceExistingItem(STATUS_SLOT, RecipeUtils.getDisplayItem(output));
    }

    private void craft(BlockMenu inv, Location l, Player p) {
        if (getCharge(l) < ENERGY) { //not enough energy
            MessageUtils.messageWithCD(p, 1000, ChatColor.RED + "Not enough energy!", ChatColor.GREEN + "Charge: " + ChatColor.RED + getCharge(l) + ChatColor.GREEN + "/" + ENERGY + " J");
            return;
        }

        ItemStack item1 = inv.getItemInSlot(INPUT_SLOT1);
        ItemStack item2 = inv.getItemInSlot(INPUT_SLOT2);

        if (item1 == null || item2 == null || item1.getType() != item2.getType()) {
            MessageUtils.messageWithCD(p, 1000, ChatColor.RED + "Invalid items!");
            return;
        }

        ItemStack output = getOutput(item1, item2);

        if (output == null) {
            MessageUtils.messageWithCD(p, 1000, ChatColor.RED + "No upgrades!");
            return;
        }

        if (!inv.fits(output, OUTPUT_SLOTS)) {
            MessageUtils.messageWithCD(p, 1000, ChatColor.GOLD + "Not enough room!");
        }

        p.playSound(l, Sound.BLOCK_ANVIL_USE, 1, 1);
        inv.consumeItem(INPUT_SLOT1, 1);
        inv.consumeItem(INPUT_SLOT2, 1);
        inv.pushItem(output, OUTPUT_SLOTS);
        tick(l.getBlock(), l , inv); //update stuff
    }

    @Nullable
    private static ItemStack getOutput(@Nonnull ItemStack item1, @Nonnull ItemStack item2) {
        ItemMeta meta1 = item1.getItemMeta();
        ItemMeta meta2 = item2.getItemMeta();
        if (meta1 == null || meta2 == null || (!meta1.hasEnchants() && !meta2.hasEnchants())) return null;

        ItemStack item = item1.clone();
        item.setAmount(1);

        Map<Enchantment, Integer> map1 = meta1.getEnchants();
        Map<Enchantment, Integer> map2 = meta2.getEnchants();
        
        MapDifference<Enchantment, Integer> dif = Maps.difference(map1, map2);
        
        Map<Enchantment, Integer> common = dif.entriesInCommon();
        Map<Enchantment, MapDifference.ValueDifference<Integer>> differing = dif.entriesDiffering();
        Map<Enchantment, Integer> unique = dif.entriesOnlyOnRight();
        
        boolean changed = false;
        
        for (Enchantment e : common.keySet()) {
            if (UPGRADEABLE.containsKey(e) && common.get(e) < UPGRADEABLE.get(e)) {
                item.addUnsafeEnchantment(e, common.get(e) + 1);
                changed = true;
            }
        }
        
        for (Enchantment e : differing.keySet()) {
            MapDifference.ValueDifference<Integer> pair = differing.get(e);
            if (pair.rightValue() > pair.leftValue()) {
                item.addUnsafeEnchantment(e, pair.rightValue());
                changed = true;
            }
        }
        
        for (Enchantment e : unique.keySet()) {
            item.addUnsafeEnchantment(e, unique.get(e));
            changed = true;
        }

        if (changed) {
            return item;
        } else {
            return null;
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
