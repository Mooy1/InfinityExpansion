package io.github.mooy1.infinityexpansion.implementation.materials;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MachineItem extends MaterialItem {

    public static final SlimefunItemStack MAGSTEEL_PLATE = new SlimefunItemStack(
            "MAGSTEEL_PLATE",
            Material.NETHERITE_SCRAP,
            "&4MagSteel Plate",
            "&7Machine Component"
    );
    public static final SlimefunItemStack MACHINE_PLATE = new SlimefunItemStack(
            "MACHINE_PLATE",
            Material.PAPER,
            "&fMachine Plate",
            "&7Machine Component"
    );
    public static final SlimefunItemStack MACHINE_CIRCUIT = new SlimefunItemStack(
            "MACHINE_CIRCUIT",
            Material.GOLD_INGOT,
            "&6Machine Circuit",
            "&7Machine Component"
    );

    public static final SlimefunItemStack MACHINE_CORE = new SlimefunItemStack(
            "MACHINE_CORE",
            Material.IRON_BLOCK,
            "&fMachine Core",
            "&7Machine Component"
    );

    public static void setup(InfinityExpansion plugin) {
        new MachineItem(MAGSTEEL_PLATE, new ItemStack[] {
                SmelteryItem.MAGSTEEL, SmelteryItem.MAGSTEEL, SmelteryItem.MAGSTEEL,
                SmelteryItem.MAGSTEEL, SlimefunItems.HARDENED_METAL_INGOT, SmelteryItem.MAGSTEEL,
                SmelteryItem.MAGSTEEL, SmelteryItem.MAGSTEEL, SmelteryItem.MAGSTEEL,
        }).register(plugin);
        new MachineItem(MACHINE_CIRCUIT, new ItemStack[] {
                SlimefunItems.COPPER_INGOT, SlimefunItems.ELECTRO_MAGNET, SlimefunItems.COPPER_INGOT,
                SlimefunItems.COPPER_INGOT, SlimefunItems.SILICON, SlimefunItems.COPPER_INGOT,
                SlimefunItems.COPPER_INGOT, SlimefunItems.ELECTRO_MAGNET, SlimefunItems.COPPER_INGOT
        }).register(plugin);
        new MachineItem(MACHINE_CORE, new ItemStack[] {
                SmelteryItem.TITANIUM, MACHINE_CIRCUIT, SmelteryItem.TITANIUM,
                MACHINE_CIRCUIT, MACHINE_PLATE, MACHINE_CIRCUIT,
                SmelteryItem.TITANIUM, MACHINE_CIRCUIT, SmelteryItem.TITANIUM,
        }).register(plugin);
        new MachineItem(MACHINE_PLATE, new ItemStack[] {
                SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_ALLOY_INGOT,
                MAGSTEEL_PLATE, SmelteryItem.TITANIUM, MAGSTEEL_PLATE,
                SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_ALLOY_INGOT
        }).register(plugin);
    }

    MachineItem(SlimefunItemStack item, ItemStack[] recipe) {
        super(item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
    }

}
