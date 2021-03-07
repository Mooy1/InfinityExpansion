package io.github.mooy1.infinityexpansion.implementation.mobdata;

import io.github.mooy1.infinityexpansion.implementation.materials.Items;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class EmptyDataCard extends SlimefunItem implements NotPlaceable {

    public static final SlimefunItemStack ITEM = new SlimefunItemStack(
            "EMPTY_DATA_CARD",
            Material.CHAINMAIL_CHESTPLATE,
            "&8Empty Data Card",
            "&7Infuse with a mob's items to fill"
    );
    
    public EmptyDataCard() {
        super(Categories.MOB_SIMULATION, ITEM, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.MAGNESIUM_INGOT, Items.MACHINE_CIRCUIT, SlimefunItems.MAGNESIUM_INGOT,
                SlimefunItems.SYNTHETIC_SAPPHIRE, SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.SYNTHETIC_EMERALD,
                SlimefunItems.MAGNESIUM_INGOT, Items.MACHINE_CIRCUIT, SlimefunItems.MAGNESIUM_INGOT
        });
    }
    
}
