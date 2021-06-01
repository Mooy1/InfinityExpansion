package io.github.mooy1.infinityexpansion.items.mobdata;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.items.MobData;
import io.github.mooy1.infinityexpansion.items.abstracts.AbstractCrafter;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.mooy1.infinitylib.recipes.RecipeMap;
import io.github.mooy1.infinitylib.recipes.ShapedRecipe;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

public final class MobDataInfuser extends AbstractCrafter implements EnergyNetComponent {

    private static final RecipeMap<ItemStack> RECIPES = new RecipeMap<>(ShapedRecipe::new);
    static final RecipeType TYPE = new RecipeType(InfinityExpansion.inst().getKey("mob_data_infuser"), MobData.INFUSER, RECIPES::put);
    
    private final int energy;

    public MobDataInfuser(Category category, SlimefunItemStack stack, RecipeType type, ItemStack[] recipe, int energy) {
        super(category, stack, RECIPES, type, recipe);
        this.energy = energy;
    }

    @Override
    public boolean preCraftFail(@Nonnull Location l, @Nonnull BlockMenu inv) {
        return getCharge(l) < this.energy;
    }

    @Nonnull
    @Override
    public String preCraftMessage(@Nonnull Location l, @Nonnull BlockMenu inv) {
        return ChatColor.RED + "Not enough energy!";
    }

    @Nonnull
    @Override
    public ItemStack preCraftItem(@Nonnull Location l, @Nonnull BlockMenu inv) {
        return MenuPreset.NO_ENERGY;
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
        return this.energy;
    }

}
