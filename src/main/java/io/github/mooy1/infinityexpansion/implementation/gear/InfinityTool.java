package io.github.mooy1.infinityexpansion.implementation.gear;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.materials.Items;
import io.github.mooy1.infinityexpansion.implementation.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.categories.Categories;
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
                Items.INFINITY, Items.INFINITY, null, null, Items.INFINITY, Items.INFINITY,
                Items.INFINITY, Items.VOID_INGOT, Items.INFINITY, Items.INFINITY, Items.VOID_INGOT, Items.INFINITY,
                Items.INFINITY, Items.VOID_INGOT, Items.INFINITY, Items.INFINITY, Items.VOID_INGOT, Items.INFINITY,
                Items.INFINITY, Items.VOID_INGOT, Items.INFINITY, Items.INFINITY, Items.VOID_INGOT, Items.INFINITY,
                null, Items.INFINITY, Items.VOID_INGOT, Items.VOID_INGOT, Items.INFINITY, null,
                null, Items.INFINITY, Items.VOID_INGOT, Items.VOID_INGOT, Items.INFINITY, null
        }).register(plugin);
        new InfinityTool(BOW, new ItemStack[] {
                null, Items.INFINITY, Items.INFINITY, Items.VOID_INGOT, null, null,
                Items.INFINITY, null, Items.INFINITY, Items.INFINITY, Items.VOID_INGOT, null,
                Items.VOID_INGOT, null, null, EnderFlame.ITEM, Items.INFINITY, Items.VOID_INGOT,
                null, Items.VOID_INGOT, null, null, Items.INFINITY, Items.INFINITY,
                null, null, Items.VOID_INGOT, null, null, Items.INFINITY,
                null, null, null, Items.VOID_INGOT, Items.INFINITY, null
        }).register(plugin);
        new InfinityTool(AXE, new ItemStack[] {
                null, Items.VOID_INGOT, Items.INFINITY, Items.INFINITY, null, null,
                Items.VOID_INGOT, Items.INFINITY, Items.INFINITY, Items.INFINITY, Items.VOID_INGOT, null,
                null, Items.INFINITY, Items.INFINITY, Items.VOID_INGOT, Items.INFINITY, Items.INFINITY,
                null, null, Items.VOID_INGOT, Items.INFINITY, Items.INFINITY, Items.INFINITY,
                null, Items.VOID_INGOT, null, Items.INFINITY, Items.INFINITY, Items.VOID_INGOT,
                Items.VOID_INGOT, null, null, null, Items.VOID_INGOT, null
        }).register(plugin);
        new InfinityTool(BLADE, new ItemStack[] {
                null, null, null, null, Items.INFINITY, Items.INFINITY,
                null, null, null, Items.INFINITY, Items.VOID_INGOT, Items.INFINITY,
                null, null, Items.INFINITY, Items.VOID_INGOT, Items.INFINITY, null,
                Items.INFINITY, Items.INFINITY, Items.VOID_INGOT, Items.INFINITY, null, null,
                null, Items.VOID_INGOT, Items.INFINITY, null, null, null,
                Items.VOID_INGOT, null, Items.INFINITY, null, null, null
        }).register(plugin);
        new InfinityTool(SHOVEL, new ItemStack[] {
                null, null, null, Items.INFINITY, Items.INFINITY, Items.INFINITY,
                null, null, Items.INFINITY, Items.INFINITY, Items.INFINITY, Items.INFINITY,
                null, null, Items.INFINITY, Items.VOID_INGOT, Items.INFINITY, Items.INFINITY,
                null, null, Items.VOID_INGOT, Items.INFINITY, Items.INFINITY, null,
                null, Items.VOID_INGOT, null, null, null, null,
                Items.VOID_INGOT, null, null, null, null, null
        }).register(plugin);
        new InfinityTool(PICKAXE, new ItemStack[] {
                null, Items.VOID_INGOT, Items.INFINITY, Items.INFINITY, Items.INFINITY, null,
                null, null, null, Items.INFINITY, Items.VOID_INGOT, Items.INFINITY,
                null, null, null, Items.VOID_INGOT, Items.INFINITY, Items.INFINITY,
                null, null, Items.VOID_INGOT, null, null, Items.INFINITY,
                null, Items.VOID_INGOT, null, null, null, Items.VOID_INGOT,
                Items.VOID_INGOT, null, null, null, null, null
        }).register(plugin);
    }
    
    public static final SlimefunItemStack SHIELD = new SlimefunItemStack(
            "INFINITY_SHIELD",
            Material.SHIELD,
            "&bCosmic Aegis"
    );
    public static final SlimefunItemStack BLADE = new SlimefunItemStack(
            "INFINITY_BLADE",
            Material.NETHERITE_SWORD,
            "&bBlade of the Cosmos"
    );
    public static final SlimefunItemStack PICKAXE = new SlimefunItemStack(
            "INFINITY_PICKAXE",
            Material.NETHERITE_PICKAXE,
            "&9World Breaker"
    );
    public static final SlimefunItemStack AXE = new SlimefunItemStack(
            "INFINITY_AXE",
            Material.NETHERITE_AXE,
            "&4Nature's Ruin"
    );
    public static final SlimefunItemStack SHOVEL = new SlimefunItemStack(
            "INFINITY_SHOVEL",
            Material.NETHERITE_SHOVEL,
            "&aMountain Eater"
    );
    public static final SlimefunItemStack BOW = new SlimefunItemStack(
            "INFINITY_BOW",
            Material.BOW,
            "&6Sky Piercer"
    );
    
    private InfinityTool(@Nonnull SlimefunItemStack stack, ItemStack[] recipe) {
        super(Categories.INFINITY_CHEAT, stack, InfinityWorkbench.TYPE, recipe);
    }
    
}