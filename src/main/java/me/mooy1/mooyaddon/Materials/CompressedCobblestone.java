package me.mooy1.mooyaddon.Materials;

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

import static me.mooy1.mooyaddon.Materials.Cores.Compress;

public class CompressedCobblestone extends SlimefunItem {

    private final Compression compression;

    public CompressedCobblestone(Compression compression) {
        super(MooyItems.MOOYMATERIALS, compression.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, compression.getRecipe());
        this.compression = compression;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)

    public enum Compression {
        ONE(MooyItems.COMPRESSED_COBBLESTONE_1, Compress(new ItemStack(Material.COBBLESTONE))),
        TWO(MooyItems.COMPRESSED_COBBLESTONE_2, Compress(MooyItems.COMPRESSED_COBBLESTONE_1)),
        THREE(MooyItems.COMPRESSED_COBBLESTONE_3, Compress(MooyItems.COMPRESSED_COBBLESTONE_2)),
        FOUR(MooyItems.COMPRESSED_COBBLESTONE_4, Compress(MooyItems.COMPRESSED_COBBLESTONE_3)),
        FIVE(MooyItems.COMPRESSED_COBBLESTONE_5, Compress(MooyItems.COMPRESSED_COBBLESTONE_4)),
        SIX(MooyItems.COMPRESSED_COBBLESTONE_6, Compress(MooyItems.COMPRESSED_COBBLESTONE_5)),
        SEVEN(MooyItems.COMPRESSED_COBBLESTONE_7, Compress(MooyItems.COMPRESSED_COBBLESTONE_6)),
        EIGHT(MooyItems.COMPRESSED_COBBLESTONE_8, Compress(MooyItems.COMPRESSED_COBBLESTONE_7)),
        NINE(MooyItems.COMPRESSED_COBBLESTONE_9, Compress(MooyItems.COMPRESSED_COBBLESTONE_8));

        @Nonnull
        private final SlimefunItemStack item;
        private final ItemStack[] recipe;
    }
}