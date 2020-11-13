package io.github.mooy1.infinityexpansion.implementation.materials;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import io.github.mooy1.infinityexpansion.implementation.machines.SingularityConstructor;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.lists.RecipeTypes;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
        super(Categories.INFINITY_MATERIALS, type.getItem(), RecipeTypes.SINGULARITY_CONSTRUCTOR, makeRecipe(type.getRecipe(), type.getId()));
    }

    @Nonnull
    private static ItemStack[] makeRecipe(@Nonnull ItemStack item, int id) {
        List<ItemStack> recipe = new ArrayList<>();
        int amount = SingularityConstructor.OUTPUT_TIMES[id];
        int stacks = (int) Math.floor(amount / 64D);
        int extra = amount % 64;

        for (int i = 0 ; i < stacks ; i++) {
            recipe.add(new CustomItem(item, 64));
        }
        
        recipe.add(new CustomItem(item, extra));
        
        while (recipe.size() < 9) {
            recipe.add(null);
        }
        
        return recipe.toArray(new ItemStack[0]);
    }
    
    /*private static final ItemStack[] INPUT_ITEMS = {
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
    }*/

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        COPPER(Items.COPPER_SINGULARITY, SlimefunItems.COPPER_INGOT, 0),
        ZINC(Items.ZINC_SINGULARITY, SlimefunItems.ZINC_INGOT, 1),
        TIN(Items.TIN_SINGULARITY, SlimefunItems.TIN_INGOT, 2),
        ALUMINUM(Items.ALUMINUM_SINGULARITY, SlimefunItems.ALUMINUM_INGOT, 3),
        SILVER(Items.SILVER_SINGULARITY, SlimefunItems.SILVER_INGOT, 4),
        MAGNESIUM(Items.MAGNESIUM_SINGULARITY, SlimefunItems.MAGNESIUM_INGOT, 5),
        LEAD(Items.LEAD_SINGULARITY, SlimefunItems.LEAD_INGOT, 6),

        GOLD(Items.GOLD_SINGULARITY, new ItemStack(Material.GOLD_INGOT), 7),
        IRON(Items.IRON_SINGULARITY, new ItemStack(Material.IRON_INGOT), 8),
        DIAMOND(Items.DIAMOND_SINGULARITY, new ItemStack(Material.DIAMOND), 9),
        EMERALD(Items.EMERALD_SINGULARITY, new ItemStack(Material.EMERALD), 10),
        NETHERITE(Items.NETHERITE_SINGULARITY, new ItemStack(Material.NETHERITE_INGOT), 11),

        COAL(Items.COAL_SINGULARITY, new ItemStack(Material.COAL), 12),
        REDSTONE(Items.REDSTONE_SINGULARITY, new ItemStack(Material.REDSTONE), 13),
        LAPIS(Items.LAPIS_SINGULARITY, new ItemStack(Material.LAPIS_LAZULI), 14),
        QUARTZ(Items.QUARTZ_SINGULARITY, new ItemStack(Material.QUARTZ), 15),

        INFINITY(Items.INFINITY_SINGULARITY, Items.INFINITE_INGOT, 16);

        @Nonnull
        private final SlimefunItemStack item;
        private final ItemStack recipe;
        private final int id;
    }
}
