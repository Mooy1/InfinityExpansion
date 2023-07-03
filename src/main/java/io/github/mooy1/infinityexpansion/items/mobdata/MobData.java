package io.github.mooy1.infinityexpansion.items.mobdata;

import lombok.experimental.UtilityClass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.categories.Groups;
import io.github.mooy1.infinityexpansion.items.materials.Materials;
import io.github.mooy1.infinitylib.core.Environment;
import io.github.mooy1.infinitylib.machines.MachineLore;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

@UtilityClass
public final class MobData {

    private static final int CHAMBER_INTERVAL =
            InfinityExpansion.config().getInt("mob-simulation-options.ticks-per-output", 1, 1000);
    private static final int CHAMBER_BUFFER = 15000;
    private static final int CHAMBER_ENERGY = 150;
    private static final int INFUSER_ENERGY = 20000;

    public static final SlimefunItemStack EMPTY_DATA_CARD = new SlimefunItemStack(
            "EMPTY_DATA_CARD",
            Material.CHAINMAIL_CHESTPLATE,
            "&8Empty Data Card",
            "&7Infuse with a mob's items to fill"
    );
    public static final SlimefunItemStack INFUSER = new SlimefunItemStack(
            "DATA_INFUSER",
            Material.LODESTONE,
            "&8Mob Data Infuser",
            "&7Infused empty data cards with mob items",
            "",
            MachineLore.energy(INFUSER_ENERGY) + "per use"
    );
    public static final SlimefunItemStack CHAMBER = new SlimefunItemStack(
            "MOB_SIMULATION_CHAMBER",
            Material.GILDED_BLACKSTONE,
            "&8Mob Simulation Chamber",
            "&7Use mob data cards to activate",
            "",
            MachineLore.energyBuffer(CHAMBER_BUFFER),
            MachineLore.energyPerSecond(CHAMBER_ENERGY)
    );

    public static final SlimefunItemStack COW = MobDataCard.create("Cow", MobDataTier.PASSIVE);
    public static final SlimefunItemStack SHEEP = MobDataCard.create("Sheep", MobDataTier.PASSIVE);
    public static final SlimefunItemStack CHICKEN = MobDataCard.create("Chicken", MobDataTier.PASSIVE);
    public static final SlimefunItemStack VILLAGER = MobDataCard.create("Villager", MobDataTier.NEUTRAL);

    public static final SlimefunItemStack BEE = MobDataCard.create("Bee", MobDataTier.NEUTRAL);
    public static final SlimefunItemStack SLIME = MobDataCard.create("Slime", MobDataTier.NEUTRAL);
    public static final SlimefunItemStack MAGMA_CUBE = MobDataCard.create("Magma Cube", MobDataTier.NEUTRAL);

    public static final SlimefunItemStack WITCH = MobDataCard.create("Witch", MobDataTier.ADVANCED);
    public static final SlimefunItemStack ZOMBIE = MobDataCard.create("Zombie", MobDataTier.HOSTILE);
    public static final SlimefunItemStack SPIDER = MobDataCard.create("Spider", MobDataTier.HOSTILE);
    public static final SlimefunItemStack SKELETON = MobDataCard.create("Skeleton", MobDataTier.HOSTILE);
    public static final SlimefunItemStack CREEPER = MobDataCard.create("Creeper", MobDataTier.HOSTILE);

    public static final SlimefunItemStack WITHER_SKELETON = MobDataCard.create("Wither Skeleton", MobDataTier.ADVANCED);
    public static final SlimefunItemStack ENDERMEN = MobDataCard.create("Endermen", MobDataTier.ADVANCED);
    public static final SlimefunItemStack GUARDIAN = MobDataCard.create("Guardian", MobDataTier.HOSTILE);
    public static final SlimefunItemStack IRON_GOLEM = MobDataCard.create("Iron Golem", MobDataTier.ADVANCED);
    public static final SlimefunItemStack BLAZE = MobDataCard.create("Blaze", MobDataTier.ADVANCED);

    public static final SlimefunItemStack WITHER = MobDataCard.create("Wither", MobDataTier.MINI_BOSS);
    public static final SlimefunItemStack ENDER_DRAGON = MobDataCard.create("Ender Dragon", MobDataTier.BOSS);

    public static void setup(InfinityExpansion plugin) {

        new MobSimulationChamber(Groups.MOB_SIMULATION, CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.MAGSTEEL_PLATE, Materials.MACHINE_PLATE, Materials.MAGSTEEL_PLATE,
                Materials.MACHINE_CIRCUIT, SlimefunItems.PROGRAMMABLE_ANDROID_BUTCHER, Materials.MACHINE_CIRCUIT,
                Materials.MAGSTEEL_PLATE, Materials.MACHINE_PLATE, Materials.MAGSTEEL_PLATE,
        }, CHAMBER_ENERGY, CHAMBER_INTERVAL).register(plugin);

        new MobDataInfuser(Groups.MOB_SIMULATION, INFUSER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Materials.MACHINE_CIRCUIT, SlimefunItems.REINFORCED_ALLOY_INGOT, Materials.MACHINE_CIRCUIT,
                SlimefunItems.REINFORCED_ALLOY_INGOT, Materials.MACHINE_CORE, SlimefunItems.REINFORCED_ALLOY_INGOT,
                Materials.MACHINE_CIRCUIT, SlimefunItems.REINFORCED_ALLOY_INGOT, Materials.MACHINE_CIRCUIT
        }, INFUSER_ENERGY).register(plugin);

        new SlimefunItem(Groups.MOB_SIMULATION, EMPTY_DATA_CARD, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.MAGNESIUM_INGOT, Materials.MACHINE_CIRCUIT, SlimefunItems.MAGNESIUM_INGOT,
                SlimefunItems.SYNTHETIC_SAPPHIRE, SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.SYNTHETIC_EMERALD,
                SlimefunItems.MAGNESIUM_INGOT, Materials.MACHINE_CIRCUIT, SlimefunItems.MAGNESIUM_INGOT
        }).register(plugin);

        if (InfinityExpansion.environment() == Environment.TESTING) {
            // There is some issues with player skull items in randomized sets when testing
            return;
        }

        new MobDataCard(ZOMBIE, MobDataTier.HOSTILE, new ItemStack[] {
                new ItemStack(Material.IRON_SWORD, 1), new ItemStack(Material.ROTTEN_FLESH, 16), new ItemStack(Material.IRON_SHOVEL, 1),
                new ItemStack(Material.IRON_INGOT, 64), EMPTY_DATA_CARD, new ItemStack(Material.IRON_INGOT, 64),
                new ItemStack(Material.CARROT, 64), new ItemStack(Material.ROTTEN_FLESH, 16), new ItemStack(Material.POTATO, 64)
        }).addDrop(Material.ROTTEN_FLESH, 1).register(plugin);
        new MobDataCard(SLIME, MobDataTier.NEUTRAL, new ItemStack[] {
                new ItemStack(Material.SLIME_BLOCK, 16), new ItemStack(Material.LIME_DYE, 16), new ItemStack(Material.SLIME_BLOCK, 16),
                new ItemStack(Material.LIME_DYE, 16), EMPTY_DATA_CARD, new ItemStack(Material.LIME_DYE, 16),
                new ItemStack(Material.SLIME_BLOCK, 16), new ItemStack(Material.LIME_DYE, 16), new ItemStack(Material.SLIME_BLOCK, 16)
        }).addDrop(Material.SLIME_BALL, 1).register(plugin);
        new MobDataCard(MAGMA_CUBE, MobDataTier.NEUTRAL, new ItemStack[] {
                new ItemStack(Material.MAGMA_BLOCK, 64), new ItemStack(Material.MAGMA_CREAM, 16), new ItemStack(Material.MAGMA_BLOCK, 64),
                new ItemStack(Material.SLIME_BLOCK, 16), EMPTY_DATA_CARD, new ItemStack(Material.SLIME_BLOCK, 16),
                new ItemStack(Material.MAGMA_BLOCK, 64), new ItemStack(Material.MAGMA_CREAM, 16), new ItemStack(Material.MAGMA_BLOCK, 64)
        }).addDrop(Material.MAGMA_CREAM, 1).register(plugin);
        new MobDataCard(COW, MobDataTier.PASSIVE, new ItemStack[] {
                new ItemStack(Material.LEATHER, 64), new ItemStack(Material.BEEF, 64), new ItemStack(Material.LEATHER, 64),
                new ItemStack(Material.COOKED_BEEF, 64), EMPTY_DATA_CARD, new ItemStack(Material.COOKED_BEEF, 64),
                new ItemStack(Material.LEATHER, 64), new ItemStack(Material.BEEF, 64), new ItemStack(Material.LEATHER, 64)
        }).addDrop(Material.LEATHER, 1).addDrop(Material.BEEF, 1).register(plugin);
        new MobDataCard(SHEEP, MobDataTier.PASSIVE, new ItemStack[] {
                new ItemStack(Material.WHITE_WOOL, 64), new ItemStack(Material.MUTTON, 64), new ItemStack(Material.WHITE_WOOL, 64),
                new ItemStack(Material.COOKED_MUTTON, 64), EMPTY_DATA_CARD, new ItemStack(Material.COOKED_MUTTON, 64),
                new ItemStack(Material.WHITE_WOOL, 64), new ItemStack(Material.MUTTON, 64), new ItemStack(Material.WHITE_WOOL, 64)
        }).addDrop(Material.WHITE_WOOL, 1).addDrop(Material.MUTTON, 1).addDrop(Material.PINK_WOOL, 10000).register(plugin);
        new MobDataCard(SPIDER, MobDataTier.HOSTILE, new ItemStack[] {
                new ItemStack(Material.COBWEB, 8), new ItemStack(Material.STRING, 64), new ItemStack(Material.COBWEB, 8),
                new ItemStack(Material.SPIDER_EYE, 32), EMPTY_DATA_CARD, new ItemStack(Material.SPIDER_EYE, 32),
                new ItemStack(Material.COBWEB, 8), new ItemStack(Material.STRING, 64), new ItemStack(Material.COBWEB, 8)
        }).addDrop(Material.STRING, 1).addDrop(Material.SPIDER_EYE, 2).register(plugin);
        new MobDataCard(SKELETON, MobDataTier.HOSTILE, new ItemStack[] {
                new ItemStack(Material.LEATHER_HELMET, 1), new ItemStack(Material.BONE, 64), new ItemStack(Material.LEATHER_HELMET, 1),
                new ItemStack(Material.ARROW, 64), EMPTY_DATA_CARD, new ItemStack(Material.ARROW, 64),
                new ItemStack(Material.BOW, 1), new ItemStack(Material.BONE, 64), new ItemStack(Material.BOW, 1)
        }).addDrop(Material.BONE, 1).addDrop(Material.ARROW, 3).register(plugin);
        new MobDataCard(WITHER_SKELETON, MobDataTier.ADVANCED, new ItemStack[] {
                new ItemStack(Material.WITHER_SKELETON_SKULL, 8), new ItemStack(Material.BONE, 64), new ItemStack(Material.WITHER_SKELETON_SKULL, 8),
                new ItemStack(Material.COAL_BLOCK, 64), EMPTY_DATA_CARD, new ItemStack(Material.COAL_BLOCK, 64),
                new ItemStack(Material.STONE_SWORD, 1), new ItemStack(Material.BONE, 64), new ItemStack(Material.STONE_SWORD, 1)
        }).addDrop(Material.COAL, 2, 1).addDrop(Material.BONE, 3).addDrop(Material.WITHER_SKELETON_SKULL, 15).register(plugin);
        new MobDataCard(ENDERMEN, MobDataTier.ADVANCED, new ItemStack[] {
                new ItemStack(Material.ENDER_EYE, 16), new ItemStack(Material.OBSIDIAN, 64), new ItemStack(Material.ENDER_EYE, 16),
                new ItemStack(Material.ENDER_PEARL, 16), EMPTY_DATA_CARD, new ItemStack(Material.ENDER_PEARL, 16),
                new ItemStack(Material.ENDER_EYE, 16), new ItemStack(Material.OBSIDIAN, 64), new ItemStack(Material.ENDER_EYE, 16)
        }).addDrop(Material.ENDER_PEARL, 1).register(plugin);
        new MobDataCard(CREEPER, MobDataTier.HOSTILE, new ItemStack[] {
                new ItemStack(Material.TNT, 16), new ItemStack(Material.GREEN_DYE, 64), new ItemStack(Material.TNT, 16),
                new ItemStack(Material.GUNPOWDER, 16), EMPTY_DATA_CARD, new ItemStack(Material.GUNPOWDER, 16),
                new ItemStack(Material.TNT, 16), new ItemStack(Material.GREEN_DYE, 64), new ItemStack(Material.TNT, 16)
        }).addDrop(Material.GUNPOWDER, 1).register(plugin);
        new MobDataCard(GUARDIAN, MobDataTier.HOSTILE, new ItemStack[] {
                new ItemStack(Material.COD, 16), new ItemStack(Material.PRISMARINE_SHARD, 64), new ItemStack(Material.PRISMARINE_CRYSTALS, 64),
                new ItemStack(Material.SPONGE, 4), EMPTY_DATA_CARD, new ItemStack(Material.PUFFERFISH, 4),
                new ItemStack(Material.PRISMARINE_CRYSTALS, 64), new ItemStack(Material.PRISMARINE_SHARD, 64), new ItemStack(Material.COOKED_COD, 16)
        }).addDrop(Material.PRISMARINE_SHARD, 1).addDrop(Material.PRISMARINE_CRYSTALS, 2)
                .addDrop(Material.COD, 3).addDrop(Material.SPONGE, 40).register(plugin);
        new MobDataCard(CHICKEN, MobDataTier.PASSIVE, new ItemStack[] {
                new ItemStack(Material.CHICKEN, 64), new ItemStack(Material.FEATHER, 64), new ItemStack(Material.COOKED_CHICKEN, 64),
                new ItemStack(Material.EGG, 16), EMPTY_DATA_CARD, new ItemStack(Material.EGG, 16),
                new ItemStack(Material.COOKED_CHICKEN, 64), new ItemStack(Material.FEATHER, 64), new ItemStack(Material.CHICKEN, 64)
        }).addDrop(Material.CHICKEN, 1).addDrop(Material.FEATHER, 2).register(plugin);
        new MobDataCard(IRON_GOLEM, MobDataTier.ADVANCED, new ItemStack[] {
                new ItemStack(Material.IRON_BLOCK, 64), new ItemStack(Material.PUMPKIN, 16), new ItemStack(Material.IRON_BLOCK, 64),
                new ItemStack(Material.POPPY, 16), EMPTY_DATA_CARD, new ItemStack(Material.POPPY, 16),
                new ItemStack(Material.IRON_BLOCK, 64), new ItemStack(Material.PUMPKIN, 16), new ItemStack(Material.IRON_BLOCK, 64)
        }).addDrop(Material.IRON_INGOT, 2, 1).addDrop(Material.POPPY, 3).addDrop(SlimefunItems.BASIC_CIRCUIT_BOARD, 3).register(plugin);
        new MobDataCard(BLAZE, MobDataTier.ADVANCED, new ItemStack[] {
                new ItemStack(Material.MAGMA_BLOCK, 64), new ItemStack(Material.BLAZE_ROD, 64), new ItemStack(Material.MAGMA_BLOCK, 64),
                new ItemStack(Material.BLAZE_ROD, 64), EMPTY_DATA_CARD, new ItemStack(Material.BLAZE_ROD, 64),
                new ItemStack(Material.MAGMA_BLOCK, 64), new ItemStack(Material.BLAZE_ROD, 64), new ItemStack(Material.MAGMA_BLOCK, 64)
        }).addDrop(Material.BLAZE_ROD, 1).register(plugin);
        new MobDataCard(WITHER, MobDataTier.MINI_BOSS, new ItemStack[] {
                new ItemStack(Material.WITHER_SKELETON_SKULL, 64), new ItemStack(Material.WITHER_SKELETON_SKULL, 64), new ItemStack(Material.WITHER_SKELETON_SKULL, 64),
                new SlimefunItemStack(SlimefunItems.WITHER_PROOF_OBSIDIAN, 64), EMPTY_DATA_CARD, new SlimefunItemStack(SlimefunItems.WITHER_PROOF_OBSIDIAN, 64),
                new SlimefunItemStack(Materials.VOID_INGOT, 4), new SlimefunItemStack(SlimefunItems.WITHER_ASSEMBLER, 4), new SlimefunItemStack(Materials.VOID_INGOT, 4)
        }).addDrop(Material.NETHER_STAR, 1).addDrop(SlimefunItems.COMPRESSED_CARBON, 8, 2).register(plugin);
        new MobDataCard(ENDER_DRAGON, MobDataTier.BOSS, new ItemStack[] {
                new ItemStack(Material.END_CRYSTAL, 64), new SlimefunItemStack(Materials.VOID_INGOT, 32), new ItemStack(Material.CHORUS_FLOWER, 64),
                SlimefunItems.INFUSED_ELYTRA, EMPTY_DATA_CARD, new ItemStack(Material.DRAGON_HEAD, 1),
                new SlimefunItemStack(SlimefunItems.ENDER_LUMP_3, 64), new SlimefunItemStack(Materials.VOID_INGOT, 32), new ItemStack(Material.DRAGON_BREATH, 64)
        }).addDrop(Materials.VOID_DUST, 1).addDrop(Materials.ENDER_ESSENCE, 4).addDrop(Material.DRAGON_EGG, 1_000_000).register(plugin);
        new MobDataCard(BEE, MobDataTier.NEUTRAL, new ItemStack[] {
                new ItemStack(Material.HONEYCOMB_BLOCK, 16), new ItemStack(Material.HONEY_BLOCK, 16), new ItemStack(Material.HONEYCOMB_BLOCK, 16),
                new ItemStack(Material.HONEY_BLOCK, 16), EMPTY_DATA_CARD, new ItemStack(Material.HONEY_BLOCK, 16),
                new ItemStack(Material.HONEYCOMB_BLOCK, 16), new ItemStack(Material.HONEY_BLOCK, 16), new ItemStack(Material.HONEYCOMB_BLOCK, 16)
        }).addDrop(Material.HONEYCOMB, 1).register(plugin);
        new MobDataCard(VILLAGER, MobDataTier.NEUTRAL, new ItemStack[] {
                new ItemStack(Material.EMERALD, 64), new ItemStack(Material.POTATO, 64), new ItemStack(Material.EMERALD, 64),
                new ItemStack(Material.CARROT, 64), EMPTY_DATA_CARD, new ItemStack(Material.WHEAT, 64),
                new ItemStack(Material.EMERALD, 64), new ItemStack(Material.PUMPKIN, 64), new ItemStack(Material.EMERALD, 64)
        }).addDrop(Material.EMERALD, 1).register(plugin);
        new MobDataCard(WITCH, MobDataTier.ADVANCED, new ItemStack[] {
                new ItemStack(Material.REDSTONE_BLOCK, 64), new ItemStack(Material.GLASS, 64), new ItemStack(Material.SUGAR, 64),
                new ItemStack(Material.GLOWSTONE, 64), EMPTY_DATA_CARD, new ItemStack(Material.GLOWSTONE, 64),
                new ItemStack(Material.SUGAR, 64), new ItemStack(Material.GLASS, 64), new ItemStack(Material.REDSTONE_BLOCK, 64)
        }).addDrop(Material.SUGAR, 1).addDrop(Material.REDSTONE, 1)
                .addDrop(Material.GLASS_BOTTLE, 1).addDrop(Material.GLOWSTONE_DUST, 1).register(plugin);
    }

}
