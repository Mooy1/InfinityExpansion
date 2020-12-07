package io.github.mooy1.infinityexpansion.implementation.storage;

import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.WirelessUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

public class WirelessOutputNode extends SlimefunItem {
    
    public WirelessOutputNode() {
        super(Categories.STORAGE_TRANSPORT, Items.WIRELESS_OUTPUT_NODE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                
        }, new SlimefunItemStack(Items.WIRELESS_OUTPUT_NODE, 3));
        
        addItemHandler(WirelessUtils.NODE_HANDLER);
        
    }
    
}
