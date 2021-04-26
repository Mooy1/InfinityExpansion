package io.github.mooy1.infinityexpansion.implementation.mobdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nonnull;

import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinitylib.slimefun.presets.LorePreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;

/**
 * A mob data card which will be able to be used in the {@link MobSimulationChamber}
 */
public final class MobDataCard extends SlimefunItem implements RecipeDisplayItem, NotPlaceable {

    static final Map<String, MobDataCard> CARDS = new HashMap<>();

    /**
     * 1 drop
     */
    public MobDataCard(String name, MobDataTier tier, ItemStack[] recipe,
                       ItemStack dropA, int chanceA) {
        this(name, tier, recipe,
                new Pair<>(dropA, chanceA));
    }

    /**
     * 2 drops
     */
    public MobDataCard(String name, MobDataTier tier, ItemStack[] recipe,
                       ItemStack dropA, int chanceA, ItemStack dropB, int chanceB) {
        this(name, tier, recipe,
                new Pair<>(dropA, chanceA), new Pair<>(dropB, chanceB));
    }

    /**
     * 3 drops
     */
    public MobDataCard(String name, MobDataTier tier, ItemStack[] recipe,
                       ItemStack dropA, int chanceA, ItemStack dropB, int chanceB, ItemStack dropC, int chanceC) {
        this(name, tier, recipe,
                new Pair<>(dropA, chanceA), new Pair<>(dropB, chanceB), new Pair<>(dropC, chanceC));
    }

    /**
     * 4 drops
     */
    public MobDataCard(String name, MobDataTier tier, ItemStack[] recipe,
                       ItemStack dropA, int chanceA, ItemStack dropB, int chanceB, ItemStack dropC, int chanceC, ItemStack dropD, int chanceD) {
        this(name, tier, recipe,
                new Pair<>(dropA, chanceA), new Pair<>(dropB, chanceB), new Pair<>(dropC, chanceC), new Pair<>(dropD, chanceD));
    }

    @SafeVarargs
    private MobDataCard(String name, MobDataTier tier, ItemStack[] recipe, @Nonnull Pair<ItemStack, Integer>... drops) {
        super(Categories.MOB_SIMULATION, new SlimefunItemStack(
                name.toUpperCase(Locale.ROOT).replace(" ", "_") + "_DATA_CARD",
                tier.material,
                "&b" + name + " Data Card",
                "&7Place in a mob simulation chamber to activate",
                "",
                LorePreset.energyPerSecond(tier.energy)
        ), MobDataInfuser.TYPE, recipe);
        this.drops = drops;
        this.tier = tier;
    }

    @Nonnull
    final Pair<ItemStack, Integer>[] drops;
    final MobDataTier tier;

    @Override
    public void postRegister() {
        CARDS.put(getId(), this);
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();
        for (Pair<ItemStack, Integer> i : this.drops) {
            items.add(null);
            items.add(i.getFirstValue());
        }
        return items;
    }

}
