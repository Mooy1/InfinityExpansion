package io.github.mooy1.infinityexpansion.implementation.mobdata;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.abstracts.AbstractCrafter;
import io.github.mooy1.infinitylib.slimefun.presets.MenuPreset;
import io.github.mooy1.infinitylib.slimefun.recipes.inputs.StrictMultiInput;
import io.github.mooy1.infinitylib.slimefun.recipes.outputs.StrictMultiOutput;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

public final class MobDataInfuser extends AbstractCrafter implements EnergyNetComponent {

    private static final Map<StrictMultiInput, StrictMultiOutput> RECIPES = new HashMap<>();
    static final RecipeType TYPE = new RecipeType(InfinityExpansion.inst().getKey("mob_data_infuser"), MobData.INFUSER,
            (stacks, itemStack) -> {
                StrictMultiInput input = new StrictMultiInput(stacks);
                RECIPES.put(input, new StrictMultiOutput(itemStack, input.getAmounts()));
            }
    );
    
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
        return this.energy;
    }

}
