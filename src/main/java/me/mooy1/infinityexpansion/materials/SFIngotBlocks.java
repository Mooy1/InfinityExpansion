package me.mooy1.infinityexpansion.materials;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

import static me.mooy1.infinityexpansion.materials.Cores.Compress;

public class SFIngotBlocks extends SlimefunItem {

    private final Block block;

    public SFIngotBlocks(SFIngotBlocks.Block block) {
        super(Categories.INFINITY_MATERIALS, block.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, block.getRecipe());
        this.block = block;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Block {

        //Blocks

        bMAGNESIUM(Items.MAGNESIUM_BLOCK, Compress(SlimefunItems.MAGNESIUM_INGOT)),
        bCOPPER(Items.COPPER_BLOCK, Compress(SlimefunItems.COPPER_INGOT)),
        bSILVER(Items.SILVER_BLOCK, Compress(SlimefunItems.SILVER_INGOT)),
        bALUMINUM(Items.ALUMINUM_BLOCK, Compress(SlimefunItems.ALUMINUM_INGOT)),
        bLEAD(Items.LEAD_BLOCK, Compress(SlimefunItems.LEAD_INGOT)),
        bZINC(Items.ZINC_BLOCK, Compress(SlimefunItems.ZINC_INGOT)),
        bTIN(Items.TIN_BLOCK, Compress(SlimefunItems.TIN_INGOT));

        @Nonnull
        private final SlimefunItemStack item;
        private final ItemStack[] recipe;

    }
}
