package me.mooy1.infinityexpansion.Materials;

import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nonnull;

import static me.mooy1.infinityexpansion.Materials.Cores.Compress;

public class CompressedCobblestone extends SlimefunItem {

    private final Compression compression;

    public CompressedCobblestone(Compression compression) {
        super(Items.MOOYMATERIALS, compression.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, compression.getRecipe());
        this.compression = compression;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)

    public enum Compression {
        ONE(Items.COMPRESSED_COBBLESTONE_1, Compress(new ItemStack(Material.COBBLESTONE))),
        TWO(Items.COMPRESSED_COBBLESTONE_2, Compress(Items.COMPRESSED_COBBLESTONE_1)),
        THREE(Items.COMPRESSED_COBBLESTONE_3, Compress(Items.COMPRESSED_COBBLESTONE_2)),
        FOUR(Items.COMPRESSED_COBBLESTONE_4, Compress(Items.COMPRESSED_COBBLESTONE_3)),
        FIVE(Items.COMPRESSED_COBBLESTONE_5, Compress(Items.COMPRESSED_COBBLESTONE_4)),
        SIX(Items.COMPRESSED_COBBLESTONE_6, Compress(Items.COMPRESSED_COBBLESTONE_5)),
        SEVEN(Items.COMPRESSED_COBBLESTONE_7, Compress(Items.COMPRESSED_COBBLESTONE_6)),
        EIGHT(Items.COMPRESSED_COBBLESTONE_8, Compress(Items.COMPRESSED_COBBLESTONE_7)),
        NINE(Items.COMPRESSED_COBBLESTONE_9, Compress(Items.COMPRESSED_COBBLESTONE_8));

        @Nonnull
        private final SlimefunItemStack item;
        private final ItemStack[] recipe;
    }
}