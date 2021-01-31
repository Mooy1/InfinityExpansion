package io.github.mooy1.infinityexpansion.implementation.materials;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinitylib.presets.RecipePreset;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class CompressedItem extends MachineItem {
    
    public static final SlimefunItemStack VOID_DUST = new SlimefunItemStack(
            "VOID_DUST",
            Material.GUNPOWDER,
            "&8Void Dust",
            "&7&oIts starting to take form..."
    );
    public static final SlimefunItemStack VOID_INGOT = new SlimefunItemStack(
            "VOID_INGOT",
            Material.NETHERITE_INGOT,
            "&8Void Ingot",
            "&7&oThe emptiness of the cosmos",
            "&7&oin the palm of your hand"
    );
    public static final SlimefunItemStack COBBLE_1 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_1",
            Material.ANDESITE,
            "&7Single Compressed Cobblestone",
            "&89 cobblestone combined"
    );
    public static final SlimefunItemStack COBBLE_2 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_2",
            Material.ANDESITE,
            "&7Double Compressed Cobblestone",
            "&881 cobblestone combined"
    );
    public static final SlimefunItemStack COBBLE_3 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_3",
            Material.STONE,
            "&7Triple Compressed Cobblestone",
            "&8729 cobblestone combined"
    );
    public static final SlimefunItemStack COBBLE_4 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_4",
            Material.STONE,
            "&7Quadruple Compressed Cobblestone",
            "&86,561 cobblestone combined"
    );
    public static final SlimefunItemStack COBBLE_5 = new SlimefunItemStack(
            "COMPRESSED_COBBLESTONE_5",
            Material.POLISHED_ANDESITE,
            "&7Quintuple Compressed Cobblestone",
            "&859,049 cobblestone combined"
    );
    
    public static void setup(InfinityExpansion plugin) {
        new CompressedItem(COBBLE_1, new ItemStack(Material.COBBLESTONE)).register(plugin);
        new CompressedItem(COBBLE_2, COBBLE_1).register(plugin);
        new CompressedItem(COBBLE_3, COBBLE_2).register(plugin);
        new CompressedItem(COBBLE_4, COBBLE_3).register(plugin);
        new CompressedItem(COBBLE_5, COBBLE_4).register(plugin);
        new CompressedItem(VOID_DUST, MiscItem.VOID_BIT).register(plugin);
        new CompressedItem(VOID_INGOT, VOID_DUST).register(plugin);
    }
    
    CompressedItem(SlimefunItemStack item, ItemStack recipe) {
        super(item, RecipePreset.Compress(recipe));
    }

}
