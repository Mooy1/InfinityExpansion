package io.github.mooy1.infinityexpansion.implementation.mobdata;

import io.github.mooy1.infinityexpansion.implementation.abstracts.AbstractCrafter;
import io.github.mooy1.infinityexpansion.implementation.materials.MachineItem;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.mooy1.infinitylib.misc.DelayedRecipeType;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class MobDataInfuser extends AbstractCrafter implements EnergyNetComponent {
    
    public static final SlimefunItemStack ITEM = new SlimefunItemStack(
            "DATA_INFUSER",
            Material.LODESTONE,
            "&8Mob Data Infuser",
            "&7Infused empty data cards with mob items",
            "",
            LorePreset.energy(MobDataInfuser.ENERGY) + "per use"
    );
    private static final int ENERGY = 20000;
    public static final DelayedRecipeType TYPE = new DelayedRecipeType(ITEM);

    public MobDataInfuser() {
        super(Categories.MOB_SIMULATION, ITEM, TYPE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                MachineItem.MACHINE_CIRCUIT, SlimefunItems.REINFORCED_ALLOY_INGOT, MachineItem.MACHINE_CIRCUIT,
                SlimefunItems.REINFORCED_ALLOY_INGOT, MachineItem.MACHINE_CORE, SlimefunItems.REINFORCED_ALLOY_INGOT,
                MachineItem.MACHINE_CIRCUIT, SlimefunItems.REINFORCED_ALLOY_INGOT, MachineItem.MACHINE_CIRCUIT
        });
    }

    @Override
    public boolean preCraftFail(@Nonnull Location l, @Nonnull BlockMenu inv) {
        return getCharge(l) < ENERGY;
    }

    @Nonnull
    @Override
    public String preCraftMessage(@Nonnull Location l, @Nonnull BlockMenu inv) {
        return ChatColor.RED + "Not enough energy!";
    }

    @Nullable
    @Override
    public ItemStack preCraftItem(@Nonnull Location l, @Nonnull BlockMenu inv) {
        return MenuPreset.notEnoughEnergy;
    }

    @Override
    public void postCraft(@Nonnull Location l, @Nonnull BlockMenu inv, @Nonnull Player p) {
        setCharge(l, 0);
    }
    
    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return ENERGY;
    }
    
}
