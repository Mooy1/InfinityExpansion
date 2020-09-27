package me.mooy1.mooyaddon.Machines;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.AutoEnchanter;
import me.mooy1.mooyaddon.MooyItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FastAutoEnchanter extends AutoEnchanter {

    public FastAutoEnchanter() {
        super(MooyItems.MOOYMAIN,
                MooyItems.FAST_AUTO_ENCHANTER,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        new ItemStack(Material.NETHER_STAR), SlimefunItems.SYNTHETIC_DIAMOND, new ItemStack(Material.NETHER_STAR),
                        SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.AUTO_ENCHANTER, SlimefunItems.SYNTHETIC_DIAMOND,
                        new ItemStack(Material.NETHER_STAR), SlimefunItems.SYNTHETIC_DIAMOND, new ItemStack(Material.NETHER_STAR)
        });
    }

    @Override
    public int getEnergyConsumption() {
        return 999;
    }


    @Override
    public int getSpeed() {
        return 10;
    }

    @Override
    public String getMachineIdentifier() {
        return "FAST_AUTO_ENCHANTER";
    }

}