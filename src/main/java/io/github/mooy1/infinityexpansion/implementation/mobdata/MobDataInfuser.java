package io.github.mooy1.infinityexpansion.implementation.mobdata;

import io.github.mooy1.infinityexpansion.implementation.abstracts.Crafter;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MobDataInfuser extends Crafter implements EnergyNetComponent {
    
    public static final int ENERGY = 20000;

    public MobDataInfuser() {
        super(Categories.MOB_SIMULATION, Items.DATA_INFUSER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MACHINE_CIRCUIT, SlimefunItems.REINFORCED_ALLOY_INGOT, Items.MACHINE_CIRCUIT,
                SlimefunItems.REINFORCED_ALLOY_INGOT, Items.MACHINE_CORE, SlimefunItems.REINFORCED_ALLOY_INGOT,
                Items.MACHINE_CIRCUIT, SlimefunItems.REINFORCED_ALLOY_INGOT, Items.MACHINE_CIRCUIT
        });
    }

    @Override
    public SlimefunItemStack[] getOutputs() {
        List<SlimefunItemStack> OUTPUTS = new ArrayList<>();
        for (MobDataCard.Type card : MobDataCard.CARDS.values()) {
            OUTPUTS.add(card.item);
        }
        return OUTPUTS.toArray(new SlimefunItemStack[0]);
    }

    @Override
    public ItemStack[][] getRecipes() {
        List<ItemStack[]> recipes = new ArrayList<>();
        for (MobDataCard.Type card : MobDataCard.CARDS.values()) {
            recipes.add(card.recipe);
        }
        return recipes.toArray(new ItemStack[0][]);
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
