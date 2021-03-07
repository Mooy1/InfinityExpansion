package io.github.mooy1.infinityexpansion.implementation.mobdata;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.materials.Items;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A mob data card which will be able to be used in the {@link MobSimulationChamber}
 */
public final class MobDataCard extends SlimefunItem implements RecipeDisplayItem, NotPlaceable {

    static final Map<String, MobDataCard> CARDS = new HashMap<>();

    /**
     * 1 drop
     */
    public MobDataCard(String name, int energy, int xp, Material material, ItemStack[] recipe,
                       ItemStack dropA, int chanceA) {
        this(name, energy, xp, material, recipe,
                new Pair<>(dropA, chanceA));
    }

    /**
     * 2 drops
     */
    public MobDataCard(String name, int energy, int xp, Material material, ItemStack[] recipe,
                       ItemStack dropA, int chanceA, ItemStack dropB, int chanceB) {
        this(name, energy, xp, material, recipe,
                new Pair<>(dropA, chanceA), new Pair<>(dropB, chanceB));
    }

    /**
     * 3 drops
     */
    public MobDataCard(String name, int energy, int xp, Material material, ItemStack[] recipe,
                       ItemStack dropA, int chanceA, ItemStack dropB, int chanceB, ItemStack dropC, int chanceC) {
        this(name, energy, xp, material, recipe,
                new Pair<>(dropA, chanceA), new Pair<>(dropB, chanceB), new Pair<>(dropC, chanceC));
    }

    /**
     * 4 drops
     */
    public MobDataCard(String name, int energy, int xp, Material material, ItemStack[] recipe,
                       ItemStack dropA, int chanceA, ItemStack dropB, int chanceB, ItemStack dropC, int chanceC, ItemStack dropD, int chanceD) {
        this(name, energy, xp, material, recipe,
                new Pair<>(dropA, chanceA), new Pair<>(dropB, chanceB), new Pair<>(dropC, chanceC), new Pair<>(dropD, chanceD));
    }
    
    @SafeVarargs
    private MobDataCard(String name, int energy, int xp, Material material, ItemStack[] recipe, @Nonnull Pair<ItemStack, Integer>... drops) {
        super(Categories.MOB_SIMULATION, new SlimefunItemStack(
                name.toUpperCase(Locale.ROOT).replace(" ", "_") + "_DATA_CARD",
                material,
                "&b" + name + " Data Card",
                "&7Place in a mob simulation chamber to activate",
                "",
                LorePreset.energyPerSecond(energy)
        ), MobDataInfuser.TYPE, recipe);
        this.drops = drops;
        this.xp = xp;
        this.energy = energy;
    }

    @Nonnull
    final Pair<ItemStack, Integer>[] drops;
    final int xp;
    final int energy;

    @Override
    public void postRegister() {
        CARDS.put(getId(), this);
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();
        for (Pair<ItemStack, Integer> i : this.drops) {
            items.add(null);
            items.add(i.getFirstValue());
        }
        return items;
    }

    public static void setup(InfinityExpansion plugin) {
        new MobDataCard("Zombie", 60, 1, Material.DIAMOND_CHESTPLATE, new ItemStack[] {
                new ItemStack(Material.IRON_SWORD, 1), new ItemStack(Material.ROTTEN_FLESH, 16), new ItemStack(Material.IRON_SHOVEL, 1),
                new ItemStack(Material.IRON_INGOT, 64), EmptyDataCard.ITEM, new ItemStack(Material.IRON_INGOT, 1),
                new ItemStack(Material.CARROT, 64), new ItemStack(Material.ROTTEN_FLESH, 16), new ItemStack(Material.POTATO, 64)
        }, new ItemStack(Material.ROTTEN_FLESH), 1, new ItemStack(Material.IRON_INGOT), 16).register(plugin);
        new MobDataCard("Slime", 90, 1, Material.DIAMOND_CHESTPLATE, new ItemStack[] {
                new ItemStack(Material.SLIME_BLOCK, 16), new ItemStack(Material.LIME_DYE, 16), new ItemStack(Material.SLIME_BLOCK, 16),
                new ItemStack(Material.LIME_DYE, 16), EmptyDataCard.ITEM, new ItemStack(Material.LIME_DYE, 16),
                new ItemStack(Material.SLIME_BLOCK, 16), new ItemStack(Material.LIME_DYE, 16), new ItemStack(Material.SLIME_BLOCK, 16)
        }, new ItemStack(Material.SLIME_BALL), 1, new ItemStack(Material.SLIME_BALL), 2).register(plugin);
        new MobDataCard("Magma Cube", 120, 2, Material.DIAMOND_CHESTPLATE, new ItemStack[] {
                new ItemStack(Material.MAGMA_BLOCK, 64), new ItemStack(Material.MAGMA_CREAM, 16), new ItemStack(Material.MAGMA_BLOCK, 64),
                new ItemStack(Material.SLIME_BLOCK, 16), EmptyDataCard.ITEM, new ItemStack(Material.SLIME_BLOCK, 16),
                new ItemStack(Material.MAGMA_BLOCK, 64), new ItemStack(Material.MAGMA_CREAM, 16), new ItemStack(Material.MAGMA_BLOCK, 64)
        }, new ItemStack(Material.MAGMA_CREAM), 1, new ItemStack(Material.MAGMA_CREAM), 2).register(plugin);
        new MobDataCard("Cow", 30, 1, Material.IRON_CHESTPLATE, new ItemStack[] {
                new ItemStack(Material.LEATHER, 64), new ItemStack(Material.BEEF, 64), new ItemStack(Material.LEATHER, 64),
                new ItemStack(Material.COOKED_BEEF, 64), EmptyDataCard.ITEM, new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.LEATHER, 64), new ItemStack(Material.BEEF, 64), new ItemStack(Material.LEATHER, 64)
        }, new ItemStack(Material.BEEF), 1, new ItemStack(Material.LEATHER), 2).register(plugin);
        new MobDataCard("Sheep", 30, 1, Material.IRON_CHESTPLATE, new ItemStack[] {
                new ItemStack(Material.WHITE_WOOL, 64), new ItemStack(Material.MUTTON, 64), new ItemStack(Material.WHITE_WOOL, 64),
                new ItemStack(Material.COOKED_MUTTON, 64), EmptyDataCard.ITEM, new ItemStack(Material.COOKED_MUTTON, 64),
                new ItemStack(Material.WHITE_WOOL, 64), new ItemStack(Material.MUTTON, 64), new ItemStack(Material.WHITE_WOOL, 64)
        }, new ItemStack(Material.MUTTON), 1, new ItemStack(Material.WHITE_WOOL), 2).register(plugin);
        new MobDataCard("Spider", 90, 1, Material.DIAMOND_CHESTPLATE, new ItemStack[] {
                new ItemStack(Material.COBWEB, 8), new ItemStack(Material.STRING, 64), new ItemStack(Material.COBWEB, 8),
                new ItemStack(Material.SPIDER_EYE, 32), EmptyDataCard.ITEM, new ItemStack(Material.SPIDER_EYE, 32),
                new ItemStack(Material.COBWEB, 8), new ItemStack(Material.STRING, 64), new ItemStack(Material.COBWEB, 8)
        }, new ItemStack(Material.STRING), 1, new ItemStack(Material.SPIDER_EYE), 2).register(plugin);
        new MobDataCard("Skeleton", 90, 1, Material.DIAMOND_CHESTPLATE, new ItemStack[] {
                new ItemStack(Material.LEATHER_HELMET, 1), new ItemStack(Material.BONE, 64), new ItemStack(Material.LEATHER_HELMET, 1),
                new ItemStack(Material.ARROW, 64), EmptyDataCard.ITEM, new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.BOW, 1), new ItemStack(Material.BONE, 64), new ItemStack(Material.BOW, 1)
        }, new ItemStack(Material.BONE), 1, new ItemStack(Material.ARROW), 2).register(plugin);
        new MobDataCard("Wither Skeleton", 150, 3, Material.DIAMOND_CHESTPLATE, new ItemStack[] {
                new ItemStack(Material.WITHER_SKELETON_SKULL, 8), new ItemStack(Material.BONE, 64), new ItemStack(Material.WITHER_SKELETON_SKULL, 8),
                new ItemStack(Material.COAL_BLOCK, 64), EmptyDataCard.ITEM, new ItemStack(Material.COAL_BLOCK, 64),
                new ItemStack(Material.STONE_SWORD, 1), new ItemStack(Material.BONE, 64), new ItemStack(Material.STONE_SWORD, 1)
        }, new ItemStack(Material.COAL), 1, new ItemStack(Material.BONE), 2, new ItemStack(Material.COAL), 3).register(plugin);
        new MobDataCard("Endermen", 240, 5, Material.DIAMOND_CHESTPLATE, new ItemStack[] {
                new ItemStack(Material.ENDER_EYE, 16), new ItemStack(Material.OBSIDIAN, 64), new ItemStack(Material.ENDER_EYE, 16),
                new ItemStack(Material.ENDER_PEARL, 16), EmptyDataCard.ITEM, new ItemStack(Material.ENDER_PEARL, 16),
                new ItemStack(Material.ENDER_EYE, 16), new ItemStack(Material.OBSIDIAN, 64), new ItemStack(Material.ENDER_EYE, 16)
        }, new ItemStack(Material.ENDER_PEARL), 1, new ItemStack(Material.ENDER_PEARL), 2).register(plugin);
        new MobDataCard("Creeper", 120, 2, Material.DIAMOND_CHESTPLATE, new ItemStack[] {
                new ItemStack(Material.TNT, 16), new ItemStack(Material.GREEN_DYE, 64), new ItemStack(Material.TNT, 16),
                new ItemStack(Material.GUNPOWDER, 16), EmptyDataCard.ITEM, new ItemStack(Material.GUNPOWDER, 16),
                new ItemStack(Material.TNT, 16), new ItemStack(Material.GREEN_DYE, 64), new ItemStack(Material.TNT, 16)
        }, new ItemStack(Material.GUNPOWDER), 1).register(plugin);
        new MobDataCard("Guardian", 240, 2, Material.DIAMOND_CHESTPLATE, new ItemStack[] {
                new ItemStack(Material.COD, 16), new ItemStack(Material.PRISMARINE_SHARD, 64), new ItemStack(Material.PRISMARINE_CRYSTALS, 64),
                new ItemStack(Material.SPONGE, 4), EmptyDataCard.ITEM, new ItemStack(Material.PUFFERFISH, 4),
                new ItemStack(Material.PRISMARINE_CRYSTALS, 64), new ItemStack(Material.PRISMARINE_SHARD, 64), new ItemStack(Material.COOKED_COD, 16)
        }, new ItemStack(Material.PRISMARINE_SHARD), 1, new ItemStack(Material.PRISMARINE_CRYSTALS), 2,
                new ItemStack(Material.COD), 3, new ItemStack(Material.SPONGE), 40).register(plugin);
        new MobDataCard("Chicken", 60, 1, Material.IRON_CHESTPLATE, new ItemStack[] {
                new ItemStack(Material.CHICKEN, 64), new ItemStack(Material.FEATHER, 64), new ItemStack(Material.COOKED_CHICKEN, 64),
                new ItemStack(Material.EGG, 16), EmptyDataCard.ITEM, new ItemStack(Material.EGG, 16),
                new ItemStack(Material.COOKED_CHICKEN, 64), new ItemStack(Material.FEATHER, 64), new ItemStack(Material.CHICKEN, 64)
        }, new ItemStack(Material.CHICKEN), 1, new ItemStack(Material.FEATHER), 2).register(plugin);
        new MobDataCard("Iron Golem", 180, 1, Material.DIAMOND_CHESTPLATE, new ItemStack[] {
                new ItemStack(Material.IRON_BLOCK, 64), new ItemStack(Material.PUMPKIN, 16), new ItemStack(Material.IRON_BLOCK, 64),
                new ItemStack(Material.POPPY, 16), EmptyDataCard.ITEM, new ItemStack(Material.POPPY, 16),
                new ItemStack(Material.IRON_BLOCK, 64), new ItemStack(Material.PUMPKIN, 16), new ItemStack(Material.IRON_BLOCK, 64)
        }, new ItemStack(Material.IRON_INGOT), 1, new ItemStack(Material.POPPY), 2, new ItemStack(Material.IRON_INGOT), 3).register(plugin);
        new MobDataCard("Blaze", 150, 5, Material.DIAMOND_CHESTPLATE, new ItemStack[] {
                new ItemStack(Material.MAGMA_BLOCK, 64), new ItemStack(Material.BLAZE_ROD, 64), new ItemStack(Material.MAGMA_BLOCK, 64),
                new ItemStack(Material.BLAZE_ROD, 64), EmptyDataCard.ITEM, new ItemStack(Material.BLAZE_ROD, 64),
                new ItemStack(Material.MAGMA_BLOCK, 64), new ItemStack(Material.BLAZE_ROD, 64), new ItemStack(Material.MAGMA_BLOCK, 64)
        }, new ItemStack(Material.BLAZE_ROD), 1).register(plugin);
        new MobDataCard("Wither", 6000, 60, Material.NETHERITE_CHESTPLATE, new ItemStack[] {
                new ItemStack(Material.WITHER_SKELETON_SKULL, 64), new ItemStack(Material.WITHER_SKELETON_SKULL, 64), new ItemStack(Material.WITHER_SKELETON_SKULL, 64),
                new SlimefunItemStack(SlimefunItems.WITHER_PROOF_OBSIDIAN, 64), EmptyDataCard.ITEM, new SlimefunItemStack(SlimefunItems.WITHER_PROOF_OBSIDIAN, 64),
                new SlimefunItemStack(Items.VOID_INGOT, 4), new SlimefunItemStack(SlimefunItems.WITHER_ASSEMBLER, 4), new SlimefunItemStack(Items.VOID_INGOT, 4)
        }, new ItemStack(Material.NETHER_STAR), 1, SlimefunItems.COMPRESSED_CARBON, 1, SlimefunItems.COMPRESSED_CARBON, 2).register(plugin);
        new MobDataCard("Ender Dragon", 9000, 150, Material.NETHERITE_CHESTPLATE, new ItemStack[] {
                new ItemStack(Material.END_CRYSTAL, 64), new SlimefunItemStack(Items.VOID_INGOT, 32), new ItemStack(Material.CHORUS_FLOWER, 64),
                SlimefunItems.INFUSED_ELYTRA, EmptyDataCard.ITEM, new ItemStack(Material.DRAGON_HEAD, 1),
                new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 64), new SlimefunItemStack(Items.VOID_INGOT, 32), new ItemStack(Material.DRAGON_BREATH, 64)
        }, Items.VOID_DUST, 1, new ItemStack(Material.DRAGON_EGG), 4_000_000).register(plugin);
    }

}
