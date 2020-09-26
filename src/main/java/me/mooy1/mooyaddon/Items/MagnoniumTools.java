package me.mooy1.mooyaddon.Items;

import me.mooy1.mooyaddon.MooyItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class MagnoniumTools extends SlimefunItem{

    private final Tool tool;
    private static final ItemStack alloy = MooyItems.MAGNONIUM_ALLOY;

    public MagnoniumTools(Tool tool) {
        super(MooyItems.MAIN, tool.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, tool.getRecipe());
        this.tool = tool;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Tool {

        CROWN(MooyItems.MAGNONIUM_CROWN, new ItemStack[] {
                alloy, alloy, alloy,
                alloy, null, alloy,
                null, null, null
        }),

        CHESTPLATE(MooyItems.MAGNONIUM_CHESTPLATE, new ItemStack[] {
                alloy, null, alloy,
                alloy, alloy, alloy,
                alloy, alloy, alloy
        }),

        LEGGINGS(MooyItems.MAGNONIUM_LEGGINGS, new ItemStack[] {
                alloy, alloy, alloy,
                alloy, null, alloy,
                alloy, null, alloy
        }),

        BOOTS(MooyItems.MAGNONIUM_BOOTS, new ItemStack[] {
                null, null, null,
                alloy, null, alloy,
                alloy, null, alloy
        }),

        BLADE(MooyItems.MAGNONIUM_BLADE , new ItemStack[] {
            null, alloy, null,
            null, alloy, null,
            null, alloy, null
        }),

        PICKAXE(MooyItems.MAGNONIUM_PICKAXE, new ItemStack[] {
            alloy, alloy, alloy,
            null, alloy, null,
            null, alloy, null
        });

        private final SlimefunItemStack item;
        private final ItemStack[] recipe;
    }
}