package io.github.mooy1.infinityexpansion.implementation.storage;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.abstracts.Container;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.ItemFilter;
import io.github.mooy1.infinityexpansion.utils.TransferUtils;
import io.github.mooy1.infinityexpansion.utils.WirelessUtils;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Moves items from connected inventory to wireless output nodes
 * 
 * @author Mooy1
 * 
 */
public class WirelessInputNode extends Container {

    private static final ItemStack WHITELIST = new CustomItem(Material.WHITE_STAINED_GLASS_PANE, "&fWhitelist", "&7Click to switch");
    private static final ItemStack BLACKLIST = new CustomItem(Material.BLACK_STAINED_GLASS_PANE, "&8Blacklist", "&7Click to switch");
    private static final int SLOT = 13;
    private static final int INFO = 4;
    
    public WirelessInputNode() {
        super(Categories.STORAGE_TRANSPORT, Items.WIRELESS_INPUT_NODE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                
        }, new SlimefunItemStack(Items.WIRELESS_INPUT_NODE, 3));
        
        addItemHandler(WirelessUtils.NODE_HANDLER);
        
        registerBlockHandler(getId(), (p, b, item, reason) -> {
            BlockMenu menu = BlockStorage.getInventory(b);
            if (menu != null) {
                menu.dropItems(b.getLocation(), SLOT);
            }
            return true;
        });
        
    }
    
    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        if (BlockStorage.getLocationInfo(b.getLocation(), "whitelist") == null) {
            setWhitelist(b.getLocation(), false);
        }
        menu.replaceExistingItem(INFO, isWhitelist(b.getLocation()) ? WHITELIST : BLACKLIST, false);
        menu.addMenuClickHandler(INFO, (p, i, stack, action) -> {
            boolean blacklist = !isWhitelist(b.getLocation());
            setWhitelist(b.getLocation(), blacklist);
            menu.replaceExistingItem(INFO, blacklist ? WHITELIST : BLACKLIST, false);
            return false;
        });
    }
    
    private void setWhitelist(@Nonnull Location l, boolean whitelist) {
        BlockStorage.addBlockInfo(l, "whitelist", String.valueOf(whitelist));
    }
    
    private boolean isWhitelist(@Nonnull Location l) {
        return Boolean.parseBoolean(BlockStorage.getLocationInfo(l, "whitelist"));
    }

    @Override
    public void tick(@Nonnull Block b, @Nonnull Location l, @Nonnull BlockMenu whiteMenu) {
        
        if (!InfinityExpansion.progressEvery(4)) {
            return;
        }

        Location connected = WirelessUtils.getConnected(l);

        if (connected == null) {
            return;
        }
        
        BlockMenu targetMenu;
        Inventory targetInv;
        
        if (WirelessUtils.isVanilla(connected)) {
            targetInv = WirelessUtils.getTargetInv(connected);
            if (targetInv == null) {
                WirelessUtils.breakWithNoPlayer(connected, Items.WIRELESS_OUTPUT_NODE);
                WirelessUtils.setConnected(l, null);
                return;
            }
            targetMenu = null;
        } else {
            targetMenu = WirelessUtils.getTargetMenu(connected);
            if (targetMenu == null) {
                WirelessUtils.breakWithNoPlayer(connected, Items.WIRELESS_OUTPUT_NODE);
                WirelessUtils.setConnected(l, null);
                return;
            }
            targetInv = null;
        }
        
        boolean vanilla = WirelessUtils.isVanilla(l);
        ItemStack item = whiteMenu.getItemInSlot(SLOT);
        ItemFilter filter = item != null ? new ItemFilter(item) : null;
        boolean whitelist = isWhitelist(l);
        
        if (vanilla) {
            Inventory sourceInv = WirelessUtils.getTargetInv(l);
            
            if (sourceInv == null) {
                WirelessUtils.breakWithNoPlayer(l, getItem());
                return;
            }

            if (outputFromInv(sourceInv, targetMenu, targetInv, l, filter, whitelist)) {
                return;
            }

        } else {
            BlockMenu sourceMenu = WirelessUtils.getTargetMenu(l);

            if (sourceMenu == null) {
                WirelessUtils.breakWithNoPlayer(l, getItem());
                return;
            }
            
            if (outputFromMenu(sourceMenu, targetMenu, targetInv, l, filter, whitelist)) {
                return;
            }

        }

        if (WirelessUtils.centerAndTest(l, connected)) {
            WirelessUtils.sendParticle(l.getWorld(), l, connected);
        }
    }
    
    private boolean outputFromMenu(@Nonnull BlockMenu menu, @Nullable BlockMenu outMenu, @Nullable Inventory outInv, @Nonnull Location l, @Nullable ItemFilter filter, boolean whitelist) {
        for (int slot : TransferUtils.getSlots(menu, ItemTransportFlow.WITHDRAW, null)) {
            ItemStack item = menu.getItemInSlot(slot);
            
            if (item == null || whitelist != new ItemFilter(item).matches(filter, false)) {
                continue;
            }

            if (outMenu != null) {
                if (outMenu == menu) {
                    WirelessUtils.setConnected(l, null);
                    return true;
                }
                menu.replaceExistingItem(slot, TransferUtils.insertToBlockMenu(outMenu, item), false);
            } else if (outInv != null) {
                menu.replaceExistingItem(slot, TransferUtils.insertToVanillaInventory(item, outInv), false);
            }

            return false;
        }

        return true;
    }

    private boolean outputFromInv(@Nonnull Inventory inv, @Nullable BlockMenu outMenu, @Nullable Inventory outInv, @Nonnull Location l, @Nullable ItemFilter filter, boolean whitelist) {
        for (int slot : TransferUtils.getOutputSlots(inv)) {
            ItemStack item = inv.getContents()[slot];

            if (item == null || whitelist != new ItemFilter(item).matches(filter, false)) {
                continue;
            }

            if (outMenu != null) {
                inv.setItem(slot, TransferUtils.insertToBlockMenu(outMenu, item));
            } else if (outInv != null) {
                if (Objects.equals(outInv.getLocation(), inv.getLocation())) {
                    WirelessUtils.setConnected(l, null);
                    return true;
                }
                inv.setItem(slot, TransferUtils.insertToVanillaInventory(item, outInv));
            }

            return false;
        }
        
        return true;
    }
    
    @Override
    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i = 0 ; i < 18 ; i++) {
            if (i == INFO || i == SLOT) {
                i++;
            }
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
    }

    @Override
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        return new int[0];
    }

}
