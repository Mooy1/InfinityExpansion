package io.github.mooy1.infinityexpansion.implementation.machines;

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
public abstract class Machine extends SlimefunItem {
    public Machine(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
        start();
    }

    public Machine(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, ItemStack recipeOutput) {
        super(category, item, recipeType, recipe, recipeOutput);
        start();
    }
    
    private void start() {
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
                return (player.hasPermission("slimefun.inventory.bypass") || (SlimefunPlugin.getProtectionManager().hasPermission(player, block.getLocation(), ProtectableAction.ACCESS_INVENTORIES) && hasPermission(block, player)));
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
                Machine.this.tick(b, b.getLocation(), menu);
            }

            public boolean isSynchronized() {
                return false;
            }
        });
    }

    public abstract void tick(Block b, Location l, BlockMenu inv);
    
    public void onNewInstance(BlockMenu menu, Block b) {}
    
    public abstract void setupInv(BlockMenuPreset preset);
    
    public abstract int[] getTransportSlots(ItemTransportFlow flow);

    public abstract int[] getTransportSlots(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item);
    
    public boolean hasPermission(Block b, Player p) {
        return true;
    }
}
