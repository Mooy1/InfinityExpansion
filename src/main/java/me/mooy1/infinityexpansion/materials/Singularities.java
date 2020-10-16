package me.mooy1.infinityexpansion.materials;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.machines.SingularityConstructor;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.setup.RecipeTypes;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Singularities extends SlimefunItem {

    private static final int[] TIME = {
        SingularityConstructor.outputTimes[0],
        SingularityConstructor.outputTimes[1],
        SingularityConstructor.outputTimes[2],
        SingularityConstructor.outputTimes[3],
        SingularityConstructor.outputTimes[4],
        SingularityConstructor.outputTimes[5],
        SingularityConstructor.outputTimes[6],
        SingularityConstructor.outputTimes[7],
        SingularityConstructor.outputTimes[8],
        SingularityConstructor.outputTimes[9],
        SingularityConstructor.outputTimes[10],
        SingularityConstructor.outputTimes[11],
        SingularityConstructor.outputTimes[12],
        SingularityConstructor.outputTimes[13],
        SingularityConstructor.outputTimes[14],
        SingularityConstructor.outputTimes[15]
    };

    private static final ItemStack[] ITEM = {
        SlimefunItems.COPPER_INGOT.clone(),
        SlimefunItems.ZINC_INGOT.clone(),
        SlimefunItems.TIN_INGOT.clone(),
        SlimefunItems.ALUMINUM_INGOT.clone(),
        SlimefunItems.SILVER_INGOT.clone(),
        SlimefunItems.MAGNESIUM_INGOT.clone(),
        SlimefunItems.LEAD_INGOT.clone(),
        new ItemStack(Material.GOLD_INGOT),
        new ItemStack(Material.IRON_INGOT),
        new ItemStack(Material.DIAMOND),
        new ItemStack(Material.EMERALD),
        new ItemStack(Material.NETHERITE_INGOT),
        new ItemStack(Material.COAL),
        new ItemStack(Material.LAPIS_LAZULI),
        new ItemStack(Material.REDSTONE),
        new ItemStack(Material.QUARTZ)
    };

    public Singularities(Type type) {
        super(Categories.INFINITY_MATERIALS, type.getItem(), RecipeTypes.SINGULARITY_CONSTRUCTOR, type.getRecipe());
    }

    private static ItemStack[] recipeMaker(int amount, ItemStack item) {
        ItemStack[] recipe = new ItemStack[amount];

        for (int i = 0; i < amount; i++) {
            recipe[i] = item;
        }
        return recipe;
    }

    private static ItemStack displayMaker(int amount, ItemStack item) {
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

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        COPPER(Items.COPPER_SINGULARITY, recipeMaker(TIME[0], ITEM[0]), displayMaker(TIME[0], ITEM[0])),
        ZINC(Items.ZINC_SINGULARITY, recipeMaker(TIME[1], ITEM[1]), displayMaker(TIME[1], ITEM[1])),
        TIN(Items.TIN_SINGULARITY, recipeMaker(TIME[2], ITEM[2]), displayMaker(TIME[2], ITEM[2])),
        ALUMINUM(Items.ALUMINUM_SINGULARITY, recipeMaker(TIME[3], ITEM[3]), displayMaker(TIME[3], ITEM[3])),
        SILVER(Items.SILVER_SINGULARITY, recipeMaker(TIME[4], ITEM[4]), displayMaker(TIME[4], ITEM[4])),
        MAGNESIUM(Items.MAGNESIUM_SINGULARITY, recipeMaker(TIME[5], ITEM[5]), displayMaker(TIME[5], ITEM[5])),
        LEAD(Items.LEAD_SINGULARITY, recipeMaker(TIME[6], ITEM[6]), displayMaker(TIME[6], ITEM[6])),

        GOLD(Items.GOLD_SINGULARITY, recipeMaker(TIME[7], ITEM[7]), displayMaker(TIME[7], ITEM[7])),
        IRON(Items.IRON_SINGULARITY, recipeMaker(TIME[8], ITEM[8]), displayMaker(TIME[8], ITEM[8])),
        DIAMOND(Items.DIAMOND_SINGULARITY, recipeMaker(TIME[9], ITEM[9]), displayMaker(TIME[9], ITEM[9])),
        EMERALD(Items.EMERALD_SINGULARITY, recipeMaker(TIME[10], ITEM[10]), displayMaker(TIME[10], ITEM[10])),
        NETHERITE(Items.NETHERITE_SINGULARITY, recipeMaker(TIME[11], ITEM[11]), displayMaker(TIME[11], ITEM[11])),

        COAL(Items.COAL_SINGULARITY, recipeMaker(TIME[12], ITEM[12]), displayMaker(TIME[12], ITEM[12])),
        LAPIS(Items.LAPIS_SINGULARITY, recipeMaker(TIME[13], ITEM[13]), displayMaker(TIME[13], ITEM[13])),
        REDSTONE(Items.REDSTONE_SINGULARITY, recipeMaker(TIME[14], ITEM[14]), displayMaker(TIME[14], ITEM[14])),
        QUARTZ(Items.QUARTZ_SINGULARITY, recipeMaker(TIME[15], ITEM[15]), displayMaker(TIME[15], ITEM[15]));

        @Nonnull
        private final SlimefunItemStack item;
        private final ItemStack[] recipe;
        private final ItemStack displayRecipe;
    }
}
