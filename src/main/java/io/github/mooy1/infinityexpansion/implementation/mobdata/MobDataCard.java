package io.github.mooy1.infinityexpansion.implementation.mobdata;

import com.google.common.collect.ImmutableMap;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.lists.RecipeTypes;
import io.github.mooy1.infinityexpansion.utils.LoreUtils;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MobDataCard extends SlimefunItem implements RecipeDisplayItem, NotPlaceable {

    @Getter
    private final Type type;
    public static final List<MobDataCard> CARDS = new ArrayList<>();

    public MobDataCard(Type type) {
        super(Categories.MOB_SIMULATION, makeCard(type), RecipeTypes.DATA_INFUSER, type.getRecipe());
        this.type = type;
        CARDS.add(this);
    }

    private static SlimefunItemStack makeCard(Type type) {
        return new SlimefunItemStack(
                type.getName().toUpperCase().replace(" ", "_") + "_DATA_CARD",
                type.isUpgraded() ? Material.NETHERITE_CHESTPLATE : Material.DIAMOND_CHESTPLATE,
                "&b" + type.getName() + " Data Card",
                "&7Place in a mob simulation chamber to activate",
                "",
                LoreUtils.energyPerSecond(type.getEnergy())
        );
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();

        for (int i : type.getDrops().keySet()) {
            items.add(null);
            items.add(type.getDrops().get(i));
        }

        return items;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {

        ZOMBIE("Zombie", 60, 1, false, new ItemStack[]{
                new ItemStack(Material.IRON_SWORD, 1), new ItemStack(Material.ROTTEN_FLESH, 16), new ItemStack(Material.IRON_SHOVEL, 1),
                new ItemStack(Material.IRON_INGOT, 1), Items.EMPTY_DATA_CARD, new ItemStack(Material.IRON_INGOT, 1),
                new ItemStack(Material.CARROT, 1), new ItemStack(Material.ROTTEN_FLESH, 16), new ItemStack(Material.POTATO, 1)
        }, ImmutableMap.of(1, new ItemStack(Material.ROTTEN_FLESH, 2), 8, new ItemStack(Material.IRON_INGOT))),

        SPIDER("Spider", 90, 1, false, new ItemStack[]{
                new ItemStack(Material.COBWEB, 1), new ItemStack(Material.STRING, 16), new ItemStack(Material.COBWEB, 1),
                new ItemStack(Material.SPIDER_EYE, 8), Items.EMPTY_DATA_CARD, new ItemStack(Material.SPIDER_EYE, 8),
                new ItemStack(Material.COBWEB, 1), new ItemStack(Material.STRING, 16), new ItemStack(Material.COBWEB, 1)
        }, ImmutableMap.of(1, new ItemStack(Material.STRING, 2), 2, new ItemStack(Material.SPIDER_EYE))),

        SKELLY("Skeleton", 90, 1, false, new ItemStack[]{
                new ItemStack(Material.LEATHER_HELMET, 1), new ItemStack(Material.BONE, 16), new ItemStack(Material.LEATHER_HELMET, 1),
                new ItemStack(Material.ARROW, 16), Items.EMPTY_DATA_CARD, new ItemStack(Material.ARROW, 16),
                new ItemStack(Material.BOW, 1), new ItemStack(Material.BONE, 16), new ItemStack(Material.BOW, 1)
        }, ImmutableMap.of(1, new ItemStack(Material.BONE, 2), 2, new ItemStack(Material.ARROW, 2), 16, new ItemStack(Material.BOW))),

        WITHER_SKELLY("Wither Skeleton", 150, 3, false, new ItemStack[]{
                new ItemStack(Material.WITHER_SKELETON_SKULL, 1), new ItemStack(Material.BONE, 16), new ItemStack(Material.WITHER_SKELETON_SKULL, 1),
                new ItemStack(Material.COAL, 64), Items.EMPTY_DATA_CARD, new ItemStack(Material.COAL, 64),
                new ItemStack(Material.STONE_SWORD, 1), new ItemStack(Material.BONE, 16), new ItemStack(Material.STONE_SWORD, 1)
        }, ImmutableMap.of(1, new ItemStack(Material.COAL, 1), 2, new ItemStack(Material.BONE, 2),
                3, new ItemStack(Material.COAL, 2), 12, new ItemStack(Material.WITHER_SKELETON_SKULL))),

        ENDERMEN("Endermen", 240, 5, false, new ItemStack[]{
                new ItemStack(Material.ENDER_EYE, 1), new ItemStack(Material.OBSIDIAN, 16), new ItemStack(Material.ENDER_EYE, 1),
                new ItemStack(Material.ENDER_PEARL, 16), Items.EMPTY_DATA_CARD, new ItemStack(Material.ENDER_PEARL, 16),
                new ItemStack(Material.ENDER_EYE, 1), new ItemStack(Material.OBSIDIAN, 16), new ItemStack(Material.ENDER_EYE, 1)
        }, ImmutableMap.of(1, new ItemStack(Material.ENDER_PEARL, 1), 2, new ItemStack(Material.ENDER_PEARL, 1), 3, Items.ENDER_ESSENCE)),

        CREEPER("Creeper", 120, 2, false, new ItemStack[]{
                new ItemStack(Material.TNT, 1), new ItemStack(Material.GREEN_DYE, 16), new ItemStack(Material.TNT, 1),
                new ItemStack(Material.GUNPOWDER, 16), Items.EMPTY_DATA_CARD, new ItemStack(Material.GUNPOWDER, 16),
                new ItemStack(Material.TNT, 1), new ItemStack(Material.GREEN_DYE, 16), new ItemStack(Material.TNT, 1)
        }, ImmutableMap.of(1, new ItemStack(Material.GUNPOWDER, 1), 2, new ItemStack(Material.GUNPOWDER, 1))),

        GUARDIAN("Guardian", 240, 2, false, new ItemStack[]{
                new ItemStack(Material.COD, 16), new ItemStack(Material.PRISMARINE_SHARD, 32), new ItemStack(Material.PRISMARINE_CRYSTALS, 16),
                new ItemStack(Material.SPONGE, 4), Items.EMPTY_DATA_CARD, new ItemStack(Material.PUFFERFISH, 4),
                new ItemStack(Material.PRISMARINE_CRYSTALS, 16), new ItemStack(Material.PRISMARINE_SHARD, 32), new ItemStack(Material.COOKED_COD, 16)
        }, ImmutableMap.of(1, new ItemStack(Material.PRISMARINE_SHARD), 2, new ItemStack(Material.PRISMARINE_CRYSTALS),
                3, new ItemStack(Material.COD), 8, new ItemStack(Material.PUFFERFISH), 16, new ItemStack(Material.SPONGE))),

        CHICKEN("Chicken", 60, 1, false, new ItemStack[]{
                new ItemStack(Material.CHICKEN, 16), new ItemStack(Material.FEATHER, 16), new ItemStack(Material.TNT, 1),
                new ItemStack(Material.EGG, 8), Items.EMPTY_DATA_CARD, new ItemStack(Material.EGG, 8),
                new ItemStack(Material.COOKED_CHICKEN, 16), new ItemStack(Material.FEATHER, 16), new ItemStack(Material.CHICKEN, 16)
        }, ImmutableMap.of(1, new ItemStack(Material.CHICKEN, 1), 2, new ItemStack(Material.FEATHER, 2),
                3, new ItemStack(Material.CHICKEN, 1), 12, new ItemStack(Material.EGG))),

        IRON("Iron Golem", 180, 1, false, new ItemStack[]{
                new ItemStack(Material.IRON_INGOT, 64), new ItemStack(Material.PUMPKIN, 16), new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.POPPY, 16), Items.EMPTY_DATA_CARD, new ItemStack(Material.POPPY, 16),
                new ItemStack(Material.IRON_INGOT, 64), new ItemStack(Material.PUMPKIN, 16), new ItemStack(Material.IRON_INGOT, 64)
        }, ImmutableMap.of(1, new ItemStack(Material.IRON_INGOT, 2), 3, SlimefunItems.BASIC_CIRCUIT_BOARD, 4, new ItemStack(Material.POPPY, 1))),

        BLAZE("Blaze", 150, 5, false, new ItemStack[]{
                new ItemStack(Material.MAGMA_BLOCK, 64), new ItemStack(Material.BLAZE_ROD, 8), new ItemStack(Material.MAGMA_BLOCK, 64),
                new ItemStack(Material.BLAZE_ROD, 8), Items.EMPTY_DATA_CARD, new ItemStack(Material.BLAZE_ROD, 8),
                new ItemStack(Material.MAGMA_BLOCK, 64), new ItemStack(Material.BLAZE_ROD, 8), new ItemStack(Material.MAGMA_BLOCK, 64)
        }, ImmutableMap.of(1, new ItemStack(Material.BLAZE_ROD, 1), 2, new ItemStack(Material.BLAZE_ROD, 1))),

        DRAGON("Ender Dragon", 9000, 150, true, new ItemStack[]{
                new ItemStack(Material.END_CRYSTAL, 64), new ItemStack(Material.DRAGON_EGG), new ItemStack(Material.CHORUS_FLOWER, 64),
                SlimefunItems.INFUSED_ELYTRA, Items.EMPTY_DATA_CARD, new ItemStack(Material.DRAGON_HEAD, 1),
                new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 64), Items.VOID_INGOT, new ItemStack(Material.DRAGON_BREATH, 64)
        }, ImmutableMap.of(1, new ItemStack(Material.ENDER_PEARL, 3), 2, new ItemStack(Material.DRAGON_BREATH, 2), 8, Items.VOID_BIT));
        
        @Nonnull
        private final String name;
        private final int energy;
        private final int xp;
        private final boolean upgraded;
        @Nonnull
        private final ItemStack[] recipe;
        @Nonnull
        private final Map<Integer, ItemStack> drops;

    }
}
