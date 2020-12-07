package io.github.mooy1.infinityexpansion.implementation.storage;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.MessageUtils;
import io.github.mooy1.infinityexpansion.utils.TransferUtils;
import io.github.mooy1.infinityexpansion.utils.WirelessUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class WirelessInputNode extends SlimefunItem {
    
    public WirelessInputNode() {
        super(Categories.STORAGE_TRANSPORT, Items.WIRELESS_INPUT_NODE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                
        }, new SlimefunItemStack(Items.WIRELESS_INPUT_NODE, 3));
        
        addItemHandler(new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return true;
            }

            @Override
            public void tick(Block block, SlimefunItem slimefunItem, Config config) {
                WirelessInputNode.this.tick(block.getLocation());
            }
        });

        addItemHandler(WirelessUtils.NODE_HANDLER);
        
    }

    private void tick(@Nonnull Location l) {
        
        if (!InfinityExpansion.progressEvery(4)) {
            return;
        }

        Location connected = WirelessUtils.getConnected(l);

        if (connected == null) {
            return;
        }
        
        BlockMenu menu;
        Inventory inv;
        
        if (WirelessUtils.isVanilla(connected)) {
            inv = WirelessUtils.getTargetInv(connected);
            if (inv == null) {
                WirelessUtils.breakWithNoPlayer(connected, Items.WIRELESS_OUTPUT_NODE);
                WirelessUtils.setConnected(l, null);
                return;
            }
            menu = null;
        } else {
            menu = WirelessUtils.getTargetMenu(connected);
            if (menu == null) {
                WirelessUtils.breakWithNoPlayer(connected, Items.WIRELESS_OUTPUT_NODE);
                WirelessUtils.setConnected(l, null);
                return;
            }
            inv = null;
        }
        
        boolean vanilla = WirelessUtils.isVanilla(l);
        
        if (vanilla) {
            Inventory targetInv = WirelessUtils.getTargetInv(l);
            
            if (targetInv == null) {
                WirelessUtils.breakWithNoPlayer(l, getItem());
                return;
            }

            outputFromInv(targetInv, menu, inv, l);

        } else {
            BlockMenu targetMenu = WirelessUtils.getTargetMenu(l);

            if (targetMenu == null) {
                WirelessUtils.breakWithNoPlayer(l, getItem());
                return;
            }
            
            outputFromMenu(targetMenu, menu, inv, l);

        }

        if (WirelessUtils.centerAndTest(l, connected)) {
            WirelessUtils.sendParticle(l.getWorld(), l, connected);
        }
    }
    
    private void outputFromMenu(@Nonnull BlockMenu menu, @Nullable BlockMenu outMenu, @Nullable Inventory outInv, @Nonnull Location l) {
        for (int slot : TransferUtils.getSlots(menu, ItemTransportFlow.WITHDRAW, null)) {
            ItemStack item = menu.getItemInSlot(slot);
            
            if (item == null) {
                continue;
            }

            if (outMenu != null) {
                if (outMenu == menu) {
                    MessageUtils.broadcast("SAME MENU");
                    WirelessUtils.setConnected(l, null);
                    return;
                }
                menu.replaceExistingItem(slot, TransferUtils.insertToBlockMenu(outMenu, item), false);
            } else if (outInv != null) {
                menu.replaceExistingItem(slot, TransferUtils.insertToVanillaInventory(item, outInv), false);
            }
            
            break;
        }
    }

    private void outputFromInv(@Nonnull Inventory inv, @Nullable BlockMenu outMenu, @Nullable Inventory outInv, @Nonnull Location l) {
        for (int slot : TransferUtils.getOutputSlots(inv)) {
            ItemStack item = inv.getContents()[slot];

            if (item == null) {
                continue;
            }

            if (outMenu != null) {
                inv.setItem(slot, TransferUtils.insertToBlockMenu(outMenu, item));
            } else if (outInv != null) {
                if (Objects.equals(outInv.getLocation(), inv.getLocation())) {
                    MessageUtils.broadcast("SAME INV");
                    WirelessUtils.setConnected(l, null);
                    return;
                }
                inv.setItem(slot, TransferUtils.insertToVanillaInventory(item, outInv));
            }

            break;
        }
    }
    
}
