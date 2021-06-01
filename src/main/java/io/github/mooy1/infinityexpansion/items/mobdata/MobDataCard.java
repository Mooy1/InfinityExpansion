package io.github.mooy1.infinityexpansion.items.mobdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.RandomizedSet;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

/**
 * A mob data card which will be able to be used in the {@link MobSimulationChamber}
 */
public final class MobDataCard extends SlimefunItem implements RecipeDisplayItem, NotPlaceable {

    static final Map<String, MobDataCard> CARDS = new HashMap<>();

    /**
     * @deprecated Use {@code MobDataCard.addDrop(drop, weight)} in a chain instead
     */
    @Deprecated
    public MobDataCard(String name, MobDataTier tier, ItemStack[] recipe,
                       ItemStack dropA, int chanceA, ItemStack dropB, int chanceB) {
        this(name, tier, recipe);
        addDrop(dropA, chanceA).addDrop(dropB, chanceB);
    }
    
    public MobDataCard(String name, MobDataTier tier, ItemStack[] recipe) {
        super(Categories.MOB_SIMULATION, new SlimefunItemStack(
                name.toUpperCase(Locale.ROOT).replace(" ", "_") + "_DATA_CARD",
                tier.material,
                "&b" + name + " Data Card",
                "&7Place in a mob simulation chamber to activate",
                "",
                LorePreset.energyPerSecond(tier.energy)
        ), MobDataInfuser.TYPE, recipe);
        
        this.tier = tier;
        
        CARDS.put(getId(), this);
    }

    @Nonnull
    final RandomizedSet<ItemStack> drops = new RandomizedSet<>();
    @Nonnull
    final MobDataTier tier;
    
    public MobDataCard addDrop(ItemStack drop, float chance) {
        this.drops.add(drop, 1 / chance);
        return this;
    }

    public MobDataCard addDrop(ItemStack drop, int amount, float chance) {
        return addDrop(new CustomItem(drop, amount), chance);
    }

    public MobDataCard addDrop(Material drop, float chance) {
        return addDrop(new ItemStack(drop), chance);
    }

    public MobDataCard addDrop(Material drop, int amount, float chance) {
        return addDrop(new ItemStack(drop, amount), chance);
    }
    
    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();
        for (ItemStack item : this.drops) {
            items.add(null);
            items.add(item);
        }
        return items;
    }

}
