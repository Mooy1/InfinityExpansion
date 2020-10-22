package me.mooy1.infinityexpansion.implementation.transport;

import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.lists.Items;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class InputDuct extends SlimefunItem{
    public InputDuct() {
        super(Categories.STORAGE_TRANSPORT, Items.INPUT_DUCT, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {

        });

    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) {
                InputDuct.this.tick(b);
            }

            public boolean isSynchronized() {
                return true;
            }
        });
    }

    public void tick(Block b) {
        Location l = b.getLocation();
    }
}
