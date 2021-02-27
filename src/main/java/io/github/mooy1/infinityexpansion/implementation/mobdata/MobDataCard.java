package io.github.mooy1.infinityexpansion.implementation.mobdata;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MobDataCard extends SlimefunItem implements RecipeDisplayItem, NotPlaceable {
    
    public static void setup(InfinityExpansion plugin) {
        for (MobDataType type : MobDataType.values()) {
            new MobDataCard(type).register(plugin);
        }
    }
    
    static final Map<String, MobDataType> CARDS = new HashMap<>();
    private final MobDataType type;
    
    private MobDataCard(MobDataType type) {
        super(Categories.MOB_SIMULATION, type.item, MobDataInfuser.TYPE, type.recipe);
        this.type = type;
        CARDS.put(type.item.getItemId(), type);
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();

        for (ItemStack i : this.type.drops.values()) {
            items.add(null);
            items.add(i);
        }

        return items;
    }

}
