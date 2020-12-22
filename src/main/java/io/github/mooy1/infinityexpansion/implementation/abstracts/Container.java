package io.github.mooy1.infinityexpansion.implementation.abstracts;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * This class is a cleaner way to add a machine
 * 
 * @author Mooy1
 * 
 */
public abstract class Container extends SlimefunItem {
    
    public Container(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
        start();
    }

    public Container(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, ItemStack recipeOutput) {
        super(category, item, recipeType, recipe, recipeOutput);
        start();
    }
    
    protected void start() {
        new BlockMenuPreset(getId(), getItemName()) {
            @Override
            public void init() {
                setupInv(this);
            }

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
                onNewInstance(menu, b);
            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return hasPermission(block, player) && (player.hasPermission("slimefun.inventory.bypass")
                        || SlimefunPlugin.getProtectionManager().hasPermission(player, block.getLocation(), ProtectableAction.INTERACT_BLOCK)
                );
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return getTransportSlots(flow);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
                return getTransportSlots(menu, flow, item);
            }
        };
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) {
                BlockMenu menu = BlockStorage.getInventory(b);
                if (menu == null) return;
                Container.this.tick(b, b.getLocation(), menu);
            }

            public boolean isSynchronized() {
                return true;
            }
        });
    }

    public abstract void tick(@Nonnull Block b, @Nonnull Location l, @Nonnull BlockMenu inv);
    
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull  Block b) {}
    
    public abstract void setupInv(@Nonnull BlockMenuPreset blockMenuPreset);
    
    public abstract int[] getTransportSlots(@Nonnull ItemTransportFlow flow);
    
    public int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, @Nonnull ItemStack item) {
        return getTransportSlots(flow);
    }
    
    public boolean hasPermission(@Nonnull Block b, @Nonnull Player p) {
        return true;
    }
    
}
