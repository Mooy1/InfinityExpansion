package me.mooy1.mooyaddon.Machines;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mooy1.mooyaddon.MooyItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class InfinityForge extends SlimefunItem {
    public InfinityForge() {
        super(MooyItems.MOOYMACHINES,
                MooyItems.INFINITY_FORGE,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                        new ItemStack(Material.ANVIL), new ItemStack(Material.ANVIL), new ItemStack(Material.ANVIL),
                        MooyItems.MACHINE_CIRCUIT, MooyItems.MACHINE_CORE, MooyItems.MACHINE_CIRCUIT,
                        SlimefunItems.REINFORCED_PLATE, new ItemStack(Material.SMITHING_TABLE), SlimefunItems.REINFORCED_PLATE
                });
    }
}
