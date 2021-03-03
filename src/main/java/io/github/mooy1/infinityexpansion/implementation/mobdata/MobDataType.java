package io.github.mooy1.infinityexpansion.implementation.mobdata;

import com.google.common.collect.ImmutableMap;
import io.github.mooy1.infinityexpansion.implementation.materials.Items;
import io.github.mooy1.infinityexpansion.implementation.materials.EnderEssence;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Locale;
import java.util.Map;

enum MobDataType {

    ZOMBIE("Zombie", 60, 1, 2, new ItemStack[] {
            new ItemStack(Material.IRON_SWORD, 1), new ItemStack(Material.ROTTEN_FLESH, 16), new ItemStack(Material.IRON_SHOVEL, 1),
            new ItemStack(Material.IRON_INGOT, 64), EmptyDataCard.ITEM, new ItemStack(Material.IRON_INGOT, 1),
            new ItemStack(Material.CARROT, 64), new ItemStack(Material.ROTTEN_FLESH, 16), new ItemStack(Material.POTATO, 64)
    }, ImmutableMap.of(1, new ItemStack(Material.ROTTEN_FLESH, 1), 8, new ItemStack(Material.IRON_INGOT))),

    SLIME("Slime", 90, 1, 2, new ItemStack[] {
            new ItemStack(Material.SLIME_BLOCK, 16), new ItemStack(Material.LIME_DYE, 16), new ItemStack(Material.SLIME_BLOCK, 16),
            new ItemStack(Material.LIME_DYE, 16), EmptyDataCard.ITEM, new ItemStack(Material.LIME_DYE, 16),
            new ItemStack(Material.SLIME_BLOCK, 16), new ItemStack(Material.LIME_DYE, 16), new ItemStack(Material.SLIME_BLOCK, 16)
    }, ImmutableMap.of(1, new ItemStack(Material.SLIME_BALL), 3, new ItemStack(Material.SLIME_BALL))),
    
    COW("Cow", 30, 1, 1, new ItemStack[] {
            new ItemStack(Material.LEATHER, 64), new ItemStack(Material.BEEF, 64), new ItemStack(Material.LEATHER, 64),
            new ItemStack(Material.COOKED_BEEF, 64), EmptyDataCard.ITEM, new ItemStack(Material.COOKED_BEEF, 64),
            new ItemStack(Material.LEATHER, 64), new ItemStack(Material.BEEF, 64), new ItemStack(Material.LEATHER, 64)
    }, ImmutableMap.of(1, new ItemStack(Material.BEEF, 1), 2, new ItemStack(Material.LEATHER, 2))),

    SHEEP("Sheep", 30, 1, 1, new ItemStack[] {
            new ItemStack(Material.WHITE_WOOL, 64), new ItemStack(Material.MUTTON, 64), new ItemStack(Material.WHITE_WOOL, 64),
            new ItemStack(Material.COOKED_MUTTON, 64), EmptyDataCard.ITEM, new ItemStack(Material.COOKED_MUTTON, 64),
            new ItemStack(Material.WHITE_WOOL, 64), new ItemStack(Material.MUTTON, 64), new ItemStack(Material.WHITE_WOOL, 64)
    }, ImmutableMap.of(1, new ItemStack(Material.MUTTON, 1), 2, new ItemStack(Material.WHITE_WOOL, 2))),

    SPIDER("Spider", 90, 1, 2, new ItemStack[] {
            new ItemStack(Material.COBWEB, 8), new ItemStack(Material.STRING, 64), new ItemStack(Material.COBWEB, 8),
            new ItemStack(Material.SPIDER_EYE, 32), EmptyDataCard.ITEM, new ItemStack(Material.SPIDER_EYE, 32),
            new ItemStack(Material.COBWEB, 8), new ItemStack(Material.STRING, 64), new ItemStack(Material.COBWEB, 8)
    }, ImmutableMap.of(1, new ItemStack(Material.STRING, 2), 2, new ItemStack(Material.SPIDER_EYE))),

    SKELLY("Skeleton", 90, 1, 2, new ItemStack[] {
            new ItemStack(Material.LEATHER_HELMET, 1), new ItemStack(Material.BONE, 64), new ItemStack(Material.LEATHER_HELMET, 1),
            new ItemStack(Material.ARROW, 64), EmptyDataCard.ITEM, new ItemStack(Material.ARROW, 64),
            new ItemStack(Material.BOW, 1), new ItemStack(Material.BONE, 64), new ItemStack(Material.BOW, 1)
    }, ImmutableMap.of(1, new ItemStack(Material.BONE, 2), 2, new ItemStack(Material.ARROW, 2), 16, new ItemStack(Material.BOW))),

    WITHER_SKELLY("Wither Skeleton", 150, 3, 2, new ItemStack[] {
            new ItemStack(Material.WITHER_SKELETON_SKULL, 8), new ItemStack(Material.BONE, 64), new ItemStack(Material.WITHER_SKELETON_SKULL, 8),
            new ItemStack(Material.COAL_BLOCK, 64), EmptyDataCard.ITEM, new ItemStack(Material.COAL_BLOCK, 64),
            new ItemStack(Material.STONE_SWORD, 1), new ItemStack(Material.BONE, 64), new ItemStack(Material.STONE_SWORD, 1)
    }, ImmutableMap.of(1, new ItemStack(Material.COAL, 1), 2, new ItemStack(Material.BONE, 2),
            3, new ItemStack(Material.COAL, 2), 12, new ItemStack(Material.WITHER_SKELETON_SKULL))),

    ENDERMEN("Endermen", 240, 5, 2, new ItemStack[] {
            new ItemStack(Material.ENDER_EYE, 16), new ItemStack(Material.OBSIDIAN, 64), new ItemStack(Material.ENDER_EYE, 16),
            new ItemStack(Material.ENDER_PEARL, 16), EmptyDataCard.ITEM, new ItemStack(Material.ENDER_PEARL, 16),
            new ItemStack(Material.ENDER_EYE, 16), new ItemStack(Material.OBSIDIAN, 64), new ItemStack(Material.ENDER_EYE, 16)
    }, ImmutableMap.of(1, new ItemStack(Material.ENDER_PEARL, 1), 2, new ItemStack(Material.ENDER_PEARL, 1), 6, EnderEssence.ITEM)),

    CREEPER("Creeper", 120, 2, 2, new ItemStack[] {
            new ItemStack(Material.TNT, 16), new ItemStack(Material.GREEN_DYE, 64), new ItemStack(Material.TNT, 16),
            new ItemStack(Material.GUNPOWDER, 16), EmptyDataCard.ITEM, new ItemStack(Material.GUNPOWDER, 16),
            new ItemStack(Material.TNT, 16), new ItemStack(Material.GREEN_DYE, 64), new ItemStack(Material.TNT, 16)
    }, ImmutableMap.of(1, new ItemStack(Material.GUNPOWDER, 1), 2, new ItemStack(Material.GUNPOWDER, 1))),

    GUARDIAN("Guardian", 240, 2, 2, new ItemStack[] {
            new ItemStack(Material.COD, 16), new ItemStack(Material.PRISMARINE_SHARD, 64), new ItemStack(Material.PRISMARINE_CRYSTALS, 64),
            new ItemStack(Material.SPONGE, 4), EmptyDataCard.ITEM, new ItemStack(Material.PUFFERFISH, 4),
            new ItemStack(Material.PRISMARINE_CRYSTALS, 64), new ItemStack(Material.PRISMARINE_SHARD, 64), new ItemStack(Material.COOKED_COD, 16)
    }, ImmutableMap.of(1, new ItemStack(Material.PRISMARINE_SHARD), 2, new ItemStack(Material.PRISMARINE_CRYSTALS),
            3, new ItemStack(Material.COD), 8, new ItemStack(Material.PUFFERFISH), 32, new ItemStack(Material.SPONGE))),

    CHICKEN("Chicken", 60, 1, 1, new ItemStack[] {
            new ItemStack(Material.CHICKEN, 64), new ItemStack(Material.FEATHER, 64), new ItemStack(Material.COOKED_CHICKEN, 64),
            new ItemStack(Material.EGG, 16), EmptyDataCard.ITEM, new ItemStack(Material.EGG, 16),
            new ItemStack(Material.COOKED_CHICKEN, 64), new ItemStack(Material.FEATHER, 64), new ItemStack(Material.CHICKEN, 64)
    }, ImmutableMap.of(1, new ItemStack(Material.CHICKEN, 1), 2, new ItemStack(Material.FEATHER, 2),
            3, new ItemStack(Material.CHICKEN, 1), 12, new ItemStack(Material.EGG))),

    IRON("Iron Golem", 180, 1, 2, new ItemStack[] {
            new ItemStack(Material.IRON_BLOCK, 64), new ItemStack(Material.PUMPKIN, 16), new ItemStack(Material.IRON_BLOCK, 64),
            new ItemStack(Material.POPPY, 16), EmptyDataCard.ITEM, new ItemStack(Material.POPPY, 16),
            new ItemStack(Material.IRON_BLOCK, 64), new ItemStack(Material.PUMPKIN, 16), new ItemStack(Material.IRON_BLOCK, 64)
    }, ImmutableMap.of(1, new ItemStack(Material.IRON_INGOT, 2), 3, SlimefunItems.BASIC_CIRCUIT_BOARD, 4, new ItemStack(Material.POPPY, 1))),

    BLAZE("Blaze", 150, 5, 2, new ItemStack[] {
            new ItemStack(Material.MAGMA_BLOCK, 64), new ItemStack(Material.BLAZE_ROD, 64), new ItemStack(Material.MAGMA_BLOCK, 64),
            new ItemStack(Material.BLAZE_ROD, 64), EmptyDataCard.ITEM, new ItemStack(Material.BLAZE_ROD, 64),
            new ItemStack(Material.MAGMA_BLOCK, 64), new ItemStack(Material.BLAZE_ROD, 64), new ItemStack(Material.MAGMA_BLOCK, 64)
    }, ImmutableMap.of(1, new ItemStack(Material.BLAZE_ROD, 1), 2, new ItemStack(Material.BLAZE_ROD, 1))),

    WITHER("Wither", 6000, 60, 3, new ItemStack[] {
            new ItemStack(Material.WITHER_SKELETON_SKULL, 64), new ItemStack(Material.WITHER_SKELETON_SKULL, 64), new ItemStack(Material.WITHER_SKELETON_SKULL, 64),
            new SlimefunItemStack(SlimefunItems.WITHER_PROOF_OBSIDIAN, 64), EmptyDataCard.ITEM,new SlimefunItemStack(SlimefunItems.WITHER_PROOF_OBSIDIAN, 64),
            new SlimefunItemStack(Items.VOID_INGOT, 4), new SlimefunItemStack(SlimefunItems.WITHER_ASSEMBLER, 4), new SlimefunItemStack(Items.VOID_INGOT, 4)
    }, ImmutableMap.of(2, new ItemStack(Material.NETHER_STAR))),
    
    DRAGON("Ender Dragon", 9000, 150, 3, new ItemStack[] {
            new ItemStack(Material.END_CRYSTAL, 64), new SlimefunItemStack(Items.VOID_INGOT, 32), new ItemStack(Material.CHORUS_FLOWER, 64),
            SlimefunItems.INFUSED_ELYTRA, EmptyDataCard.ITEM, new ItemStack(Material.DRAGON_HEAD, 1),
            new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 64), new SlimefunItemStack(Items.VOID_INGOT, 32), new ItemStack(Material.DRAGON_BREATH, 64)
    }, ImmutableMap.of(1, new ItemStack(Material.ENDER_PEARL, 3), 2, new ItemStack(Material.DRAGON_BREATH, 2), 8, Items.VOID_BIT));

    @Nonnull
    final ItemStack[] recipe;
    @Nonnull
    final Map<Integer, ItemStack> drops;
    @Nonnull
    final SlimefunItemStack item;
    final int xp;
    final int energy;

    @ParametersAreNonnullByDefault
    MobDataType(String name, int energy, int xp, int tier, ItemStack[] recipe, Map<Integer, ItemStack> drops) {
        this.recipe = recipe;
        this.xp = xp;
        this.energy = energy;
        this.drops = drops;
        this.item = new SlimefunItemStack(
                name.toUpperCase(Locale.ROOT).replace(" ", "_") + "_DATA_CARD",
                tier == 1 ? Material.IRON_CHESTPLATE : tier == 2 ? Material.DIAMOND_CHESTPLATE : Material.NETHERITE_CHESTPLATE,
                "&b" + name + " Data Card",
                "&7Place in a mob simulation chamber to activate",
                "",
                LorePreset.energyPerSecond(energy)
        );
    }

}
