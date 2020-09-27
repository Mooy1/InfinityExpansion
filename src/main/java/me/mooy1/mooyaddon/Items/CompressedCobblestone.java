package me.mooy1.mooyaddon.Items;

import me.mooy1.mooyaddon.MooyItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nonnull;

public class CompressedCobblestone extends SlimefunItem {

    private final Compression compression;

    public CompressedCobblestone(Compression compression) {
        super(MooyItems.MOOYMAIN, compression.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, compression.getRecipe());
        this.compression = compression;
    }

    public static ItemStack cobble = new ItemStack(Material.COBBLESTONE);
    public static ItemStack cobble1 = MooyItems.COMPRESSED_COBBLESTONE_1;
    public static ItemStack cobble2 = MooyItems.COMPRESSED_COBBLESTONE_2;
    public static ItemStack cobble3 = MooyItems.COMPRESSED_COBBLESTONE_3;
    public static ItemStack cobble4 = MooyItems.COMPRESSED_COBBLESTONE_4;
    public static ItemStack cobble5 = MooyItems.COMPRESSED_COBBLESTONE_5;
    public static ItemStack cobble6 = MooyItems.COMPRESSED_COBBLESTONE_6;
    public static ItemStack cobble7 = MooyItems.COMPRESSED_COBBLESTONE_7;
    public static ItemStack cobble8 = MooyItems.COMPRESSED_COBBLESTONE_8;

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)

    public enum Compression {
        ONE(MooyItems.COMPRESSED_COBBLESTONE_1, new ItemStack[]{
                cobble, cobble, cobble,
                cobble, cobble, cobble,
                cobble, cobble, cobble
        }),
        TWO(MooyItems.COMPRESSED_COBBLESTONE_2, new ItemStack[]{
                cobble1, cobble1, cobble1,
                cobble1, cobble1, cobble1,
                cobble1, cobble1, cobble1
        }),
        THREE(MooyItems.COMPRESSED_COBBLESTONE_3, new ItemStack[]{
                cobble2, cobble2, cobble2,
                cobble2, cobble2, cobble2,
                cobble2, cobble2, cobble2
        }),
        FOUR(MooyItems.COMPRESSED_COBBLESTONE_4, new ItemStack[]{
                cobble3, cobble3, cobble3,
                cobble3, cobble3, cobble3,
                cobble3, cobble3, cobble3
        }),
        FIVE(MooyItems.COMPRESSED_COBBLESTONE_5, new ItemStack[]{
                cobble4, cobble4, cobble4,
                cobble4, cobble4, cobble4,
                cobble4, cobble4, cobble4
        }),
        SIX(MooyItems.COMPRESSED_COBBLESTONE_6, new ItemStack[]{
                cobble5, cobble5, cobble5,
                cobble5, cobble5, cobble5,
                cobble5, cobble5, cobble5
        }),
        SEVEN(MooyItems.COMPRESSED_COBBLESTONE_7, new ItemStack[]{
                cobble6, cobble6, cobble6,
                cobble6, cobble6, cobble6,
                cobble6, cobble6, cobble6
        }),
        EIGHT(MooyItems.COMPRESSED_COBBLESTONE_8, new ItemStack[]{
                cobble7, cobble7, cobble7,
                cobble7, cobble7, cobble7,
                cobble7, cobble7, cobble7
        }),
        NINE(MooyItems.COMPRESSED_COBBLESTONE_9, new ItemStack[]{
                cobble8, cobble8, cobble8,
                cobble8, cobble8, cobble8,
                cobble8, cobble8, cobble8
        });

        @Nonnull
        private final SlimefunItemStack item;
        private final ItemStack[] recipe;

        }
    }