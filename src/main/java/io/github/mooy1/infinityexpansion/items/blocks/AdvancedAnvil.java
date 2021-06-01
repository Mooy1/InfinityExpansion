package io.github.mooy1.infinityexpansion.items.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.items.abstracts.AbstractEnergyCrafter;
import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

/**
 * Combines slimefun items, exceeds vanilla anvil limits
 *
 * @author Mooy1
 */
public final class AdvancedAnvil extends AbstractEnergyCrafter {

    private static final Map<Enchantment, Integer> MAX_LEVELS = Util.getEnchants(Objects.requireNonNull(
            InfinityExpansion.inst().getConfig().getConfigurationSection("advanced-anvil-max-levels")
    ));

    private static final int[] INPUT_SLOTS = {
            MenuPreset.INPUT, MenuPreset.STATUS
    };
    private static final int INPUT_SLOT1 = INPUT_SLOTS[0];
    private static final int INPUT_SLOT2 = INPUT_SLOTS[1];
    private static final int[] OUTPUT_SLOTS = {
            MenuPreset.OUTPUT
    };
    private static final int STATUS_SLOT = MenuPreset.STATUS + 27;
    private static final int[] OTHER_STATUS = {47, 51};
    private static final int[] BACKGROUND = {
            27, 28, 29, 33, 34, 35,
            36, 37, 38, 42, 43, 44,
            45, 46, 52, 53
    };

    public AdvancedAnvil(Category category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe, int energy) {
        super(category, item, type, recipe, energy, STATUS_SLOT);
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu inv, @Nonnull Location l) {
        inv.dropItems(l, INPUT_SLOTS);
        inv.dropItems(l, OUTPUT_SLOTS);
    }

    @Override
    public void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : MenuPreset.OUTPUT_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.OUTPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : BACKGROUND) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.INPUT_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.INPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.STATUS_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.INPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.STATUS_BORDER) {
            blockMenuPreset.addItem(i + 27, MenuPreset.STATUS_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : OTHER_STATUS) {
            blockMenuPreset.addItem(i, MenuPreset.STATUS_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.INVALID_INPUT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        menu.addMenuClickHandler(STATUS_SLOT, (player, i, itemStack, clickAction) -> {
            craft(menu, b, player);
            return false;
        });
    }

    private void craft(BlockMenu inv, Block b, Player p) {
        Location l = b.getLocation();
        if (getCharge(l) < this.energy) { //not enough energy
            p.sendMessage(new String[] {
                    ChatColor.RED + "Not enough energy!",
                    ChatColor.GREEN + "Charge: " + ChatColor.RED + getCharge(l) + ChatColor.GREEN + "/" + this.energy + " J"
            });
            return;
        }

        ItemStack item1 = inv.getItemInSlot(INPUT_SLOT1);
        ItemStack item2 = inv.getItemInSlot(INPUT_SLOT2);

        if (item1 == null || item2 == null || (item2.getType() != Material.ENCHANTED_BOOK && item1.getType() != item2.getType())) {
            p.sendMessage(ChatColor.RED + "Invalid items!");
            return;
        }

        ItemStack output = getOutput(item1, item2);

        if (output == null) {
            p.sendMessage(ChatColor.RED + "No upgrades!");
            return;
        }

        if (!inv.fits(output, OUTPUT_SLOTS)) {
            p.sendMessage(ChatColor.GOLD + "Not enough room!");
            return;
        }

        p.playSound(l, Sound.BLOCK_ANVIL_USE, 1, 1);
        inv.consumeItem(INPUT_SLOT1, 1);
        inv.consumeItem(INPUT_SLOT2, 1);
        inv.pushItem(output, OUTPUT_SLOTS);
        removeCharge(l, this.energy);
        update(inv);
    }

    @Nullable
    private static ItemStack getOutput(@Nonnull ItemStack item1, @Nonnull ItemStack item2) {
        Map<Enchantment, Integer> enchants1 = getEnchants(item1.getItemMeta());
        Map<Enchantment, Integer> enchants2 = getEnchants(item2.getItemMeta());
        if (enchants1.size() == 0 && enchants2.size() == 0) {
            return null;
        }
        return combineEnchants(Maps.difference(enchants1, enchants2), item1, item2);
    }

    @Nonnull
    private static Map<Enchantment, Integer> getEnchants(@Nonnull ItemMeta meta) {
        if (meta instanceof EnchantmentStorageMeta) {
            EnchantmentStorageMeta book = (EnchantmentStorageMeta) meta;
            if (book.hasStoredEnchants()) {
                return book.getStoredEnchants();
            }
        } else if (meta.hasEnchants()) {
            return meta.getEnchants();
        }

        return new HashMap<>();
    }

    private static void setEnchants(@Nonnull ItemStack item, @Nonnull ItemMeta meta, @Nonnull Map<Enchantment, Integer> enchants) {
        if (meta instanceof EnchantmentStorageMeta) {
            EnchantmentStorageMeta book = (EnchantmentStorageMeta) meta;
            for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
                book.addStoredEnchant(entry.getKey(), entry.getValue(), true);
            }
            item.setItemMeta(book);
        } else {
            for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
                item.addUnsafeEnchantment(entry.getKey(), entry.getValue());
            }
        }
    }

    @Nullable
    private static ItemStack combineEnchants(@Nonnull MapDifference<Enchantment, Integer> dif, @Nonnull ItemStack item1, @Nonnull ItemStack item2) {
        ItemStack item = item1.clone();
        item.setAmount(1);
        ItemMeta meta = item.getItemMeta();

        Map<Enchantment, Integer> enchants = new HashMap<>();

        boolean changed = false;

        //upgrades (same enchant and level)
        for (Map.Entry<Enchantment, Integer> e : dif.entriesInCommon().entrySet()) {
            if (MAX_LEVELS.containsKey(e.getKey()) && e.getValue() < MAX_LEVELS.get(e.getKey())) {
                enchants.put(e.getKey(), e.getValue() + 1);
                changed = true;
            }
        }

        //override (same enchant different level)
        for (Map.Entry<Enchantment, MapDifference.ValueDifference<Integer>> e : dif.entriesDiffering().entrySet()) {
            if (e.getValue().rightValue() > e.getValue().leftValue()) {
                enchants.put(e.getKey(), e.getValue().rightValue());
                changed = true;
            }
        }

        boolean bookOntoTool = item2.getType() == Material.ENCHANTED_BOOK && item1.getType() != Material.ENCHANTED_BOOK;

        //unique (different enchants from 2nd item)
        for (Map.Entry<Enchantment, Integer> e : dif.entriesOnlyOnRight().entrySet()) {
            if (bookOntoTool) {
                if (!e.getKey().canEnchantItem(item)) continue;
            }
            enchants.put(e.getKey(), e.getValue());
            changed = true;
        }

        if (changed) {
            setEnchants(item, meta, enchants);
            return item;
        } else {
            return null;
        }
    }

    @Override
    public void update(@Nonnull BlockMenu inv) {
        ItemStack item1 = inv.getItemInSlot(INPUT_SLOT1);
        ItemStack item2 = inv.getItemInSlot(INPUT_SLOT2);

        if (item1 == null || item2 == null || (item2.getType() != Material.ENCHANTED_BOOK && item1.getType() != item2.getType())) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cInvalid items!"));
            return;
        }

        ItemStack output = getOutput(item1, item2);

        if (output == null) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cNo upgrades!"));
            return;
        }

        inv.replaceExistingItem(STATUS_SLOT, Util.getDisplayItem(output));

    }

}
