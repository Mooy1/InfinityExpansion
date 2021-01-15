package io.github.mooy1.infinityexpansion.implementation.gear;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.implementation.materials.CompressedItem;
import io.github.mooy1.infinityexpansion.implementation.materials.SmelteryItem;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.attributes.Soulbound;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * tools
 *
 * @author Mooy1
 */
public final class InfinityTool extends SlimefunItem implements Soulbound, NotPlaceable {
    
    public static void setup(InfinityExpansion plugin) {
        new InfinityTool(SHIELD, new ItemStack[] {
                SmelteryItem.INFINITY, SmelteryItem.INFINITY, null, null, SmelteryItem.INFINITY, SmelteryItem.INFINITY,
                SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY, SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY,
                SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY, SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY,
                SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY, SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY,
                null, SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY, null,
                null, SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY, null
        }).register(plugin);
        new InfinityTool(BOW, new ItemStack[] {
                null, SmelteryItem.INFINITY, SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, null, null,
                SmelteryItem.INFINITY, null, SmelteryItem.INFINITY, SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, null,
                CompressedItem.VOID_INGOT, null, null, EnderFlame.ITEM, SmelteryItem.INFINITY, CompressedItem.VOID_INGOT,
                null, CompressedItem.VOID_INGOT, null, null, SmelteryItem.INFINITY, SmelteryItem.INFINITY,
                null, null, CompressedItem.VOID_INGOT, null, null, SmelteryItem.INFINITY,
                null, null, null, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY, null
        }).register(plugin);
        new InfinityTool(AXE, new ItemStack[] {
                null, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY, SmelteryItem.INFINITY, null, null,
                CompressedItem.VOID_INGOT, SmelteryItem.INFINITY, SmelteryItem.INFINITY, SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, null,
                null, SmelteryItem.INFINITY, SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY, SmelteryItem.INFINITY,
                null, null, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY, SmelteryItem.INFINITY, SmelteryItem.INFINITY,
                null, CompressedItem.VOID_INGOT, null, SmelteryItem.INFINITY, SmelteryItem.INFINITY, CompressedItem.VOID_INGOT,
                CompressedItem.VOID_INGOT, null, null, null, CompressedItem.VOID_INGOT, null
        }).register(plugin);
        new InfinityTool(BLADE, new ItemStack[] {
                null, null, null, null, SmelteryItem.INFINITY, SmelteryItem.INFINITY,
                null, null, null, SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY,
                null, null, SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY, null,
                SmelteryItem.INFINITY, SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY, null, null,
                null, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY, null, null, null,
                CompressedItem.VOID_INGOT, null, SmelteryItem.INFINITY, null, null, null
        }).register(plugin);
        new InfinityTool(SHOVEL, new ItemStack[] {
                null, null, null, SmelteryItem.INFINITY, SmelteryItem.INFINITY, SmelteryItem.INFINITY,
                null, null, SmelteryItem.INFINITY, SmelteryItem.INFINITY, SmelteryItem.INFINITY, SmelteryItem.INFINITY,
                null, null, SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY, SmelteryItem.INFINITY,
                null, null, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY, SmelteryItem.INFINITY, null,
                null, CompressedItem.VOID_INGOT, null, null, null, null,
                CompressedItem.VOID_INGOT, null, null, null, null, null
        }).register(plugin);
        new InfinityTool(PICKAXE, new ItemStack[] {
                null, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY, SmelteryItem.INFINITY, SmelteryItem.INFINITY, null,
                null, null, null, SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY,
                null, null, null, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY, SmelteryItem.INFINITY,
                null, null, CompressedItem.VOID_INGOT, null, null, SmelteryItem.INFINITY,
                null, CompressedItem.VOID_INGOT, null, null, null, CompressedItem.VOID_INGOT,
                CompressedItem.VOID_INGOT, null, null, null, null, null
        }).register(plugin);
    }
    
    public static final SlimefunItemStack SHIELD = new SlimefunItemStack(
            "INFINITY_SHIELD",
            Material.SHIELD,
            "&bCosmic Aegis",
            "&7&o"
    );
    public static final SlimefunItemStack BLADE = new SlimefunItemStack(
            "INFINITY_BLADE",
            Material.NETHERITE_SWORD,
            "&bBlade of the Cosmos",
            "&b&oEdge of infinity"
    );
    public static final SlimefunItemStack PICKAXE = new SlimefunItemStack(
            "INFINITY_PICKAXE",
            Material.NETHERITE_PICKAXE,
            "&9World Breaker",
            "&3&oThe end of the world"
    );
    public static final SlimefunItemStack AXE = new SlimefunItemStack(
            "INFINITY_AXE",
            Material.NETHERITE_AXE,
            "&4Nature's Ruin",
            "&c&oThe embodiment of fury"
    );
    public static final SlimefunItemStack SHOVEL = new SlimefunItemStack(
            "INFINITY_SHOVEL",
            Material.NETHERITE_SHOVEL,
            "&aMountain Eater",
            "&2&oYum"
    );
    public static final SlimefunItemStack BOW = new SlimefunItemStack(
            "INFINITY_BOW",
            Material.BOW,
            "&6Sky Piercer",
            "&e&oThe longbow of the Heavens"
    );
    
    private InfinityTool(@Nonnull SlimefunItemStack stack, ItemStack[] recipe) {
        super(Categories.INFINITY_CHEAT, stack, InfinityWorkbench.TYPE, recipe);
    }
    
}