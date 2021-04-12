package io.github.mooy1.infinityexpansion.implementation.mobdata;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinityexpansion.implementation.abstracts.AbstractCrafter;
import io.github.mooy1.infinityexpansion.implementation.materials.Items;
import io.github.mooy1.infinitylib.slimefun.presets.LorePreset;
import io.github.mooy1.infinitylib.slimefun.presets.MenuPreset;
import io.github.mooy1.infinitylib.slimefun.recipes.AdvancedRecipeMap;
import io.github.mooy1.infinitylib.slimefun.recipes.RecipeMap;
import io.github.mooy1.infinitylib.slimefun.recipes.inputs.StrictMultiInput;
import io.github.mooy1.infinitylib.slimefun.recipes.outputs.StrictMultiOutput;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

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
    private static final RecipeMap<StrictMultiInput, StrictMultiOutput> RECIPES = new AdvancedRecipeMap<>();
    public static final RecipeType TYPE = new RecipeType(InfinityExpansion.inst().getKey("mob_data_infuser"), ITEM,
            ((stacks, itemStack) -> RECIPES.put(new StrictMultiInput(stacks), new StrictMultiOutput(itemStack))));

    public MobDataInfuser() {
        super(Categories.MOB_SIMULATION, ITEM, RECIPES, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MACHINE_CIRCUIT, SlimefunItems.REINFORCED_ALLOY_INGOT, Items.MACHINE_CIRCUIT,
                SlimefunItems.REINFORCED_ALLOY_INGOT, Items.MACHINE_CORE, SlimefunItems.REINFORCED_ALLOY_INGOT,
                Items.MACHINE_CIRCUIT, SlimefunItems.REINFORCED_ALLOY_INGOT, Items.MACHINE_CIRCUIT
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

    @Nonnull
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
