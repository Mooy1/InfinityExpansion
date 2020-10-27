package me.mooy1.infinityexpansion.implementation.materials;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.implementation.machines.SingularityConstructor;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.lists.RecipeTypes;
import me.mooy1.infinityexpansion.utils.RecipeUtils;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Singularities and there recipe displays
 *
 * @author Mooy1
 */
public class Singularity extends SlimefunItem {

    public Singularity(Type type) {
        super(Categories.INFINITY_MATERIALS, type.getItem(), RecipeTypes.SINGULARITY_CONSTRUCTOR, RecipeUtils.MiddleItem(recipeMaker(type.getId())));
    }

    private static ItemStack[] bigRecipeMaker(int amount, ItemStack item) { //to slow for sf calc
        ItemStack[] recipe = new ItemStack[amount];

        for (int i = 0; i < amount; i++) {
            recipe[i] = item;
        }
        return recipe;
    }

    private static ItemStack displayItemMaker(int amount, ItemStack item) { //to slow for sf calc
        if (item.getItemMeta() != null) {
            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList<>();
            if (meta.getLore() != null) {
                lore = meta.getLore();
            }
            lore.add(ChatColor.DARK_GRAY + "(" + ChatColor.GRAY + "x" + amount + ChatColor.DARK_GRAY + ")");
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
        return null;
    }

    private static final ItemStack[] INPUT_ITEMS = {
            new CustomItem(Material.BRICK, "Copper Ingot"),
            new CustomItem(Material.IRON_INGOT, "Zinc Ingot"),
            new CustomItem(Material.IRON_INGOT, "Tin Ingot"),
            new CustomItem(Material.IRON_INGOT, "Aluminum Ingot"),
            new CustomItem(Material.IRON_INGOT, "Silver Ingot"),
            new CustomItem(Material.IRON_INGOT, "Magnesium Ingot"),
            new CustomItem(Material.IRON_INGOT, "Lead Ingot"),

            new CustomItem(Material.GOLD_INGOT, "Gold Ingot"),
            new CustomItem(Material.IRON_INGOT, "Iron Ingot"),
            new CustomItem(Material.DIAMOND, "Diamond"),
            new CustomItem(Material.EMERALD, "Emerald"),
            new CustomItem(Material.NETHERITE_INGOT, "Netherite Ingot"),

            new CustomItem(Material.COAL, "Coal"),
            new CustomItem(Material.REDSTONE, "Redstone Dust"),
            new CustomItem(Material.LAPIS_LAZULI, "Lapis Lazuli"),
            new CustomItem(Material.QUARTZ, "Quartz"),

            new CustomItem(Material.IRON_INGOT, "Infinity Ingot")
    };

    @Nonnull
    public static ItemStack recipeMaker(int id) {
        ItemStack item = INPUT_ITEMS[id].clone();
        ItemMeta meta = item.getItemMeta();
        assert meta != null;

        String name = meta.getDisplayName();
        meta.setDisplayName(ChatColor.YELLOW + String.valueOf(SingularityConstructor.OUTPUT_TIMES[id]) + " " + name);
        item.setItemMeta(meta);
        return item;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        COPPER(Items.COPPER_SINGULARITY, 0),
        ZINC(Items.ZINC_SINGULARITY, 1),
        TIN(Items.TIN_SINGULARITY, 2),
        ALUMINUM(Items.ALUMINUM_SINGULARITY, 3),
        SILVER(Items.SILVER_SINGULARITY, 4),
        MAGNESIUM(Items.MAGNESIUM_SINGULARITY, 5),
        LEAD(Items.LEAD_SINGULARITY, 6),

        GOLD(Items.GOLD_SINGULARITY, 7),
        IRON(Items.IRON_SINGULARITY, 8),
        DIAMOND(Items.DIAMOND_SINGULARITY,9),
        EMERALD(Items.EMERALD_SINGULARITY, 10),
        NETHERITE(Items.NETHERITE_SINGULARITY, 11),

        COAL(Items.COAL_SINGULARITY, 12),
        REDSTONE(Items.REDSTONE_SINGULARITY, 13),
        LAPIS(Items.LAPIS_SINGULARITY, 14),
        QUARTZ(Items.QUARTZ_SINGULARITY, 15),

        INFINITY(Items.INFINITY_SINGULARITY, 16);

        @Nonnull
        private final SlimefunItemStack item;
        private final int id;
    }
}
