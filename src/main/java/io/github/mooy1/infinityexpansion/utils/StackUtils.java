package io.github.mooy1.infinityexpansion.utils;

import io.github.thebusybiscuit.slimefun4.core.services.CustomItemDataService;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import lombok.NonNull;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Collection of utils for modifying ItemStacks and getting their ids
 *
 * @author Mooy1
 */
public final class StackUtils {
    
    @Nullable
    public static String getIDFromItem(@Nullable ItemStack item) {
        if (item == null) {

            return null;

        } else {

            SlimefunItem sfItem = SlimefunItem.getByItem(item);

            if (sfItem != null) {

                return sfItem.getId();

            }
        }

        String id = item.getType().toString();

        if (id.equals("COPPER_INGOT")) {
            id = "MC_COPPER_INGOT";
        }

        return id;
    }
    
    private static final CustomItemDataService service = SlimefunPlugin.getItemDataService();

    /**
     * Gets the slimefun item id of an item, otherwise if vanilla true returns the material id
     */
    @Nullable
    public static String getItemID(ItemStack item, boolean vanilla) {

        if (item instanceof SlimefunItemStack) {
            return ((SlimefunItemStack) item).getItemId();
        }

        Optional<String> itemID = service.getItemData(item);

        if (itemID.isPresent()) {
            return itemID.get();
        }

        if (vanilla) {
            return item.getType().toString();
        }

        return null;
    }

    @Nullable
    public static ItemStack getItemFromID(@Nonnull String id, int amount) {

        SlimefunItem sfItem = SlimefunItem.getByID(id);

        if (sfItem != null) {

            return new CustomItem(sfItem.getItem(), amount);

        } else {

            String changedID = id;

            if (id.equals("MC_COPPER_INGOT")) {
                changedID = "COPPER_INGOT";
            }

            Material material = Material.getMaterial(changedID);

            if (material != null){

                return new ItemStack(material, amount);

            }
        }

        return null;
    }

    @ParametersAreNonnullByDefault
    public static void addLore(ItemStack item, List<String> lores) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        List<String> lore = new ArrayList<>();

        if (meta.getLore() != null) {
            lore = meta.getLore();
        }

        for (String line : lores) {
            if (line != null) {
                lore.add(line);
            }
        }

        meta.setLore(lore);

        item.setItemMeta(meta);
    }
    
    @ParametersAreNonnullByDefault
    public static void addLore(ItemStack item, String... lores) {
        addLore(item, Arrays.asList(lores));
    }

    public static void removeLore(ItemStack item, int offset, String find, int amount) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return;

        List<String> lore = new ArrayList<>();

        if (meta.getLore() != null) {
            lore = meta.getLore();
        }

        int position = lore.size() - 1;
        int i = 0;
        for (String line : lore) {
            if (ChatColor.stripColor(line).contains(ChatColor.stripColor(find))) {
                position = i;
            }
            i++;
        }

        position += offset;

        if (position < 0) return;

        for (int j = 0 ; j < amount ; j++) {
            lore.remove(position);
        }

        meta.setLore(lore);

        item.setItemMeta(meta);
    }

    @ParametersAreNonnullByDefault
    public static void insertLore(ItemStack item, int offset, String find, List<String> lores) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return;

        List<String> lore = new ArrayList<>();

        if (meta.getLore() != null) {
            lore = meta.getLore();
        }

        int position = lore.size() - 1;
        int i = 0;
        for (String line : lore) {
            if (ChatColor.stripColor(line).contains(ChatColor.stripColor(find))) {
                position = i;
            }
            i++;
        }

        position += offset;

        if (position < 0) return;

        lore = lore.subList(0, position);

        lore.addAll(lores);

        meta.setLore(lore);

        item.setItemMeta(meta);
    }

    @ParametersAreNonnullByDefault
    public static void insertLore(ItemStack item, int offset, String find, String... lores) {
        insertLore(item, offset, find, Arrays.asList(lores));
    }

    /**
     * This method transfers parts of lore from 1 item to another
     *
     * @param output item to transfer to
     * @param input item to transfer from
     * @param key string of lore to look for
     * @param offset index of first line relative to key index
     * @param lines total lines to transfer
     */
    public static void transferLore(@NonNull ItemStack output, @NonNull ItemStack input, @NonNull String key, int offset, int lines) {
        ItemMeta inputMeta = input.getItemMeta();
        if (inputMeta == null) {
            return;
        }
        List<String> inputLore = inputMeta.getLore();
        if (inputLore == null) {
            return;
        }

        ItemMeta outputMeta = output.getItemMeta();
        if (outputMeta == null) {
            return;
        }

        List<String> outputLore = outputMeta.getLore();
        if (outputLore == null) {
            return;
        }

        int i = 0;
        for (String line : inputLore) {
            if (ChatColor.stripColor(line).contains(key)) {

                for (int ii = i + offset ; ii < i + lines + offset ; ii++) {
                    outputLore.add(inputLore.get(ii));
                }
                outputMeta.setLore(outputLore);
                output.setItemMeta(outputMeta);

            }
            i++;
        }
    }
    
    public static void removeEnchants(@Nonnull ItemStack item) {
        for (Enchantment e : item.getEnchantments().keySet()) {
            item.removeEnchantment(e);
        }
    }
}
