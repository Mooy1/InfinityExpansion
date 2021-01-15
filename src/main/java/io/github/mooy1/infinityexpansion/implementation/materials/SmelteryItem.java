package io.github.mooy1.infinityexpansion.implementation.materials;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class SmelteryItem extends MaterialItem {

    public static final SlimefunItemStack MAGSTEEL = new SlimefunItemStack(
            "MAGSTEEL",
            Material.BRICK,
            "&4MagSteel"
    );
    public static final SlimefunItemStack MAGNONIUM = new SlimefunItemStack(
            "MAGNONIUM",
            Material.NETHER_BRICK,
            "&5Magnonium"
    );
    public static final SlimefunItemStack TITANIUM = new SlimefunItemStack(
            "TITANIUM",
            Material.IRON_INGOT,
            "&7Titanium"
    );
    public static final SlimefunItemStack MYTHRIL = new SlimefunItemStack(
            "MYTHRIL",
            Material.IRON_INGOT,
            "&bMythril"
    );
    public static final SlimefunItemStack ADAMANTITE = new SlimefunItemStack(
            "ADAMANTITE",
            Material.BRICK,
            "&dAdamantite"
    );
    public static final SlimefunItemStack INFINITY = new SlimefunItemStack(
            "INFINITE_INGOT",
            Material.IRON_INGOT,
            "&bInfinity Ingot", // &dI&cn&6f&ei&an&bi&3t&9y &fIngot
            "&7&oThe fury of the cosmos",
            "&7&oin the palm of your hand"
    );
    public static final SlimefunItemStack FORTUNE = new SlimefunItemStack(
            "FORTUNE_SINGULARITY",
            Material.NETHER_STAR,
            "&6Fortune Singularity"
    );
    public static final SlimefunItemStack EARTH = new SlimefunItemStack(
            "EARTH_SINGULARITY",
            Material.NETHER_STAR,
            "&aEarth Singularity"
    );
    public static final SlimefunItemStack METAL = new SlimefunItemStack(
            "METAL_SINGULARITY",
            Material.NETHER_STAR,
            "&8Metal Singularity"
    );
    public static final SlimefunItemStack MAGIC = new SlimefunItemStack(
            "MAGIC_SINGULARITY",
            Material.NETHER_STAR,
            "&dMagic Singularity"
    );

    public static void setup(InfinityExpansion plugin) {
        new SmelteryItem(INFINITY, new ItemStack[] {
                EARTH, MYTHRIL, FORTUNE, MAGIC, CompressedItem.VOID_INGOT, METAL, null, null, null
        }).register(plugin);
        new SmelteryItem(FORTUNE, new ItemStack[] {
                Singularity.GOLD, Singularity.DIAMOND, Singularity.EMERALD, Singularity.NETHERITE, ADAMANTITE, null, null, null, null
        }).register(plugin);
        new SmelteryItem(MAGIC, new ItemStack[] {
                Singularity.REDSTONE, Singularity.LAPIS, Singularity.QUARTZ, Singularity.MAGNESIUM, MAGNONIUM, null, null, null, null
        }).register(plugin);
        new SmelteryItem(EARTH, new ItemStack[] {
                CompressedItem.COBBLE_4, Singularity.COAL, Singularity.IRON, Singularity.COPPER, Singularity.LEAD, null, null, null, null
        }).register(plugin);
        new SmelteryItem(METAL, new ItemStack[] {
                Singularity.SILVER, Singularity.ALUMINUM, Singularity.TIN, Singularity.ZINC, TITANIUM, null, null, null, null
        }).register(plugin);
        new SmelteryItem(MAGSTEEL, new ItemStack[] {
                SlimefunItems.MAGNESIUM_INGOT, SlimefunItems.STEEL_INGOT, SlimefunItems.MAGNESIUM_DUST, null, null, null, null, null, null
        }).register(plugin);
        new SmelteryItem(TITANIUM, new ItemStack[] {
                SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.DAMASCUS_STEEL_INGOT, SlimefunItems.HARDENED_METAL_INGOT, null, null, null, null, null, null
        }).register(plugin);
        new SmelteryItem(MYTHRIL, new ItemStack[] {
                SlimefunItems.REINFORCED_ALLOY_INGOT,Singularity.IRON, SlimefunItems.HARDENED_METAL_INGOT, null, null, null, null, null, null
        }).register(plugin);
        new SmelteryItem(ADAMANTITE, new ItemStack[] {
                SlimefunItems.REDSTONE_ALLOY,Singularity.DIAMOND,MAGSTEEL, null, null, null, null, null, null
        }).register(plugin);
        new SmelteryItem(MAGNONIUM, new ItemStack[] {
                MAGSTEEL,Singularity.MAGNESIUM,EnderEssence.ITEM, null, null, null, null, null, null
        }).register(plugin);
    }

    SmelteryItem(SlimefunItemStack item, ItemStack[] recipe) {
        super(item, RecipeType.SMELTERY, recipe);
    }

}
