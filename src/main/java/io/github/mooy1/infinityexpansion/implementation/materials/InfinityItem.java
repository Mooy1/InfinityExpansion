package io.github.mooy1.infinityexpansion.implementation.materials;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class InfinityItem extends SlimefunItem implements NotPlaceable {
    
    public static final SlimefunItemStack CIRCUIT = new SlimefunItemStack(
            "INFINITE_MACHINE_CIRCUIT",
            Material.DIAMOND,
            "&bInfinite &6Machine Circuit",
            "&7Machine Component"
    );
    public static final SlimefunItemStack CORE = new SlimefunItemStack(
            "INFINITE_MACHINE_CORE",
            Material.DIAMOND_BLOCK,
            "&bInfinite Machine Core",
            "&7Machine Component"
    );
    
    public static void setup(InfinityExpansion plugin) {
        new InfinityItem(CIRCUIT, new ItemStack[] {
                MachineItem.MACHINE_CIRCUIT, SmelteryItem.INFINITY, MachineItem.MACHINE_CIRCUIT, MachineItem.MACHINE_CIRCUIT, SmelteryItem.INFINITY, MachineItem.MACHINE_CIRCUIT,
                CompressedItem.VOID_INGOT, MachineItem.MACHINE_CIRCUIT, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, MachineItem.MACHINE_CIRCUIT, CompressedItem.VOID_INGOT,
                SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, MachineItem.MACHINE_CIRCUIT, MachineItem.MACHINE_CIRCUIT, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY,
                SmelteryItem.INFINITY, CompressedItem.VOID_INGOT, MachineItem.MACHINE_CIRCUIT, MachineItem.MACHINE_CIRCUIT, CompressedItem.VOID_INGOT, SmelteryItem.INFINITY,
                CompressedItem.VOID_INGOT, MachineItem.MACHINE_CIRCUIT, CompressedItem.VOID_INGOT, CompressedItem.VOID_INGOT, MachineItem.MACHINE_CIRCUIT, CompressedItem.VOID_INGOT,
                MachineItem.MACHINE_CIRCUIT, SmelteryItem.INFINITY, MachineItem.MACHINE_CIRCUIT, MachineItem.MACHINE_CIRCUIT, SmelteryItem.INFINITY, MachineItem.MACHINE_CIRCUIT
        }).register(plugin);
        new InfinityItem(CORE, new ItemStack[] {
                MachineItem.MACHINE_PLATE, MachineItem.MACHINE_CORE, SmelteryItem.INFINITY, SmelteryItem.INFINITY, MachineItem.MACHINE_CORE, MachineItem.MACHINE_PLATE,
                MachineItem.MACHINE_CORE, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_CIRCUIT, MachineItem.MACHINE_CIRCUIT, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_CORE,
                SmelteryItem.INFINITY, MachineItem.MACHINE_CIRCUIT, SmelteryItem.INFINITY, SmelteryItem.INFINITY, MachineItem.MACHINE_CIRCUIT, SmelteryItem.INFINITY,
                SmelteryItem.INFINITY, MachineItem.MACHINE_CIRCUIT, SmelteryItem.INFINITY, SmelteryItem.INFINITY, MachineItem.MACHINE_CIRCUIT, SmelteryItem.INFINITY,
                MachineItem.MACHINE_CORE, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_CIRCUIT, MachineItem.MACHINE_CIRCUIT, MachineItem.MACHINE_PLATE, MachineItem.MACHINE_CORE,
                MachineItem.MACHINE_PLATE, MachineItem.MACHINE_CORE, SmelteryItem.INFINITY, SmelteryItem.INFINITY, MachineItem.MACHINE_CORE, MachineItem.MACHINE_PLATE
        }).register(plugin);
    }

    private InfinityItem(SlimefunItemStack item, ItemStack[] recipe) {
        super(Categories.INFINITY_CHEAT, item, InfinityWorkbench.TYPE, recipe);
    }

}
