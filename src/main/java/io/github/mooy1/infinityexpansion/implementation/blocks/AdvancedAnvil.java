package io.github.mooy1.infinityexpansion.implementation.blocks;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.materials.Items;
import io.github.mooy1.infinityexpansion.implementation.abstracts.AbstractEnergyCrafter;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.player.MessageUtils;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Combines slimefun items, exceeds vanilla anvil limits
 *
 * @author Mooy1
 */
public final class AdvancedAnvil extends AbstractEnergyCrafter {

    public static final int ENERGY = 100_000;
    
    public static final SlimefunItemStack ITEM = new SlimefunItemStack(
            "ADVANCED_ANVIL",
            Material.SMITHING_TABLE,
            "&cAdvanced Anvil",
            "&7Combines tools and gear enchants and sometimes upgrades them",
            "&bWorks with Slimefun items",
            "",
            LorePreset.energy(ENERGY) + "per use"

    );
    
    private static final Map<Enchantment, Integer> MAX_LEVELS = Util.getEnchants(Objects.requireNonNull(InfinityExpansion.getInstance().getConfig().getConfigurationSection("advanced-anvil-max-levels")));

    private static final int[] INPUT_SLOTS = {
            MenuPreset.slot1, MenuPreset.slot2
    };
    private static final int INPUT_SLOT1 = INPUT_SLOTS[0];
    private static final int INPUT_SLOT2 = INPUT_SLOTS[1];
    private static final int[] OUTPUT_SLOTS = {
            MenuPreset.slot3
    };
    private static final int STATUS_SLOT = MenuPreset.slot2 + 27;
    private static final int[] OTHER_STATUS = {47, 51};
    private static final int[] BACKGROUND = {
            27, 28, 29, 33, 34, 35,
            36, 37, 38, 42, 43, 44,
            45, 46, 52, 53
    };

    public AdvancedAnvil() {
        super(Categories.MAIN_MATERIALS, ITEM, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.MACHINE_PLATE, Items.MACHINE_PLATE, Items.MACHINE_PLATE,
                Items.MACHINE_PLATE, new ItemStack(Material.ANVIL), Items.MACHINE_PLATE,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }, ENERGY, STATUS_SLOT);

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

    @Override
    public void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : MenuPreset.slotChunk3) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : BACKGROUND) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk1) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk2) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk2) {
            blockMenuPreset.addItem(i + 27, MenuPreset.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : OTHER_STATUS) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.invalidInput, ChestMenuUtils.getEmptyClickHandler());
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
        if (getCharge(l) < ENERGY) { //not enough energy
            MessageUtils.messageWithCD(p, 1000,
                    ChatColor.RED + "Not enough energy!",
                    ChatColor.GREEN + "Charge: " + ChatColor.RED + getCharge(l) + ChatColor.GREEN + "/" + ENERGY + " J"
            );
            return;
        }

        ItemStack item1 = inv.getItemInSlot(INPUT_SLOT1);
        ItemStack item2 = inv.getItemInSlot(INPUT_SLOT2);

        if (item1 == null || item2 == null || (item2.getType() != Material.ENCHANTED_BOOK && item1.getType() != item2.getType())) {
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
            return;
        }

        p.playSound(l, Sound.BLOCK_ANVIL_USE, 1, 1);
        inv.consumeItem(INPUT_SLOT1, 1);
        inv.consumeItem(INPUT_SLOT2, 1);
        inv.pushItem(output, OUTPUT_SLOTS);
        removeCharge(l, ENERGY);
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
    
    private static ItemStack combineEnchants(@Nonnull MapDifference<Enchantment, Integer> dif, @Nonnull ItemStack item1, @Nonnull ItemStack item2) {
        ItemStack item = item1.clone();
        item.setAmount(1);
        ItemMeta meta = item.getItemMeta();

        Map<Enchantment, Integer> enchants = new HashMap<>();
        Map<Enchantment, Integer> common = dif.entriesInCommon();
        Map<Enchantment, MapDifference.ValueDifference<Integer>> differing = dif.entriesDiffering();
        Map<Enchantment, Integer> unique = dif.entriesOnlyOnRight();

        boolean changed = false;

        //upgrades (same enchant and level)
        for (Map.Entry<Enchantment, Integer> e : common.entrySet()) {
            if (MAX_LEVELS.containsKey(e.getKey()) && e.getValue() < MAX_LEVELS.get(e.getKey())) {
                enchants.put(e.getKey(), e.getValue() + 1);
                changed = true;
            }
        }

        //override (same enchant different level)
        for (Map.Entry<Enchantment, MapDifference.ValueDifference<Integer>> e : differing.entrySet()) {
            if (e.getValue().rightValue() > e.getValue().leftValue()) {
                enchants.put(e.getKey(), e.getValue().rightValue());
                changed = true;
            }
        }

        boolean bookOntoTool = item2.getType() == Material.ENCHANTED_BOOK && item1.getType() != Material.ENCHANTED_BOOK;

        //unique (different enchants from 2nd item)
        for (Map.Entry<Enchantment, Integer> e : unique.entrySet()) {
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
