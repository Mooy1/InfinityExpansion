package io.github.mooy1.infinityexpansion.implementation.storage;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.MessageUtils;
import io.github.mooy1.infinityexpansion.utils.TransferUtils;
import io.github.mooy1.infinityexpansion.utils.WirelessUtils;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A tool for configuring wireless input/output nodes
 *
 * @author Mooy1
 * 
 */
public class WirelessConfigurator extends SlimefunItem implements NotPlaceable, Listener {

    private final NamespacedKey key;
    private final Map<Player, Long> coolDowns = new HashMap<>();

    public WirelessConfigurator(InfinityExpansion plugin) {
        super(Categories.STORAGE_TRANSPORT, Items.WIRELESS_CONFIGURATOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {

        });
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.key = new NamespacedKey(plugin, "wireless");
    }
    
    @EventHandler
    public void onRightClick(@Nonnull PlayerRightClickEvent e) {
        if (e.getHand() == EquipmentSlot.OFF_HAND) return;

        if (SlimefunItem.getByItem(e.getItem()) instanceof WirelessConfigurator) {

            if (e.getClickedBlock().isPresent()) {

                if (SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(), e.getClickedBlock().get(), ProtectableAction.PLACE_BLOCK)) {

                    if (e.getPlayer().isSneaking()) {
                        clearHandler(e.getClickedBlock().get(), e.getPlayer());
                    } else {
                        connectHandler(e.getClickedBlock().get(), e.getPlayer(), e.getItem());
                    }
                    
                    e.setUseBlock(Event.Result.DENY);
                }
                
            } else if (e.getPlayer().isSneaking()) {
                resetHandler(e.getItem(), e.getPlayer());
            }

        } else if (e.getPlayer().isSneaking() && e.getClickedBlock().isPresent()
                && Objects.equals(BlockStorage.checkID(e.getClickedBlock().get()), Items.WIRELESS_INPUT_NODE.getItemId())
                && SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(), e.getClickedBlock().get(), ProtectableAction.ACCESS_INVENTORIES)
        ) {
            infoHandler(e.getClickedBlock().get(), e.getClickedBlock().get().getLocation(), e.getPlayer(), false);
            e.setUseBlock(Event.Result.DENY);
        }
    }

    private void resetHandler(@Nonnull ItemStack item, @Nonnull Player p) {
        setTemp(item, null);
        MessageUtils.messageWithCD(p, 500, "&eCleared selected input node!");
    }

    private void clearHandler(@Nonnull Block b, @Nonnull Player p) {
        if (Objects.equals(BlockStorage.checkID(b), Items.WIRELESS_INPUT_NODE.getItemId())) {

            WirelessUtils.setConnected(b.getLocation(), null);
            MessageUtils.messageWithCD(p, 500, "&eConnected Location Cleared!");

        } else {
            clickInputNode(p);
        }
    }

    private void connectHandler(@Nonnull Block b, @Nonnull Player p, @Nonnull ItemStack item) {
        String id = BlockStorage.checkID(b);
        if (id == null) {
            clickInputNode(p);
            return;
        }
        Location temp = getTemp(item);
        if (temp == null) {
            if (id.equals(Items.WIRELESS_INPUT_NODE.getItemId())) {
                setTemp(item, b.getLocation());
                MessageUtils.message(p, "&aNow click on a wireless output node to connect");
            } else {
                clickInputNode(p);
            }
        } else {
            if (id.equals(Items.WIRELESS_OUTPUT_NODE.getItemId())) {
                if (temp.getBlock().getType() != Material.AIR && Objects.equals(BlockStorage.checkID(temp), Items.WIRELESS_INPUT_NODE.getItemId())) {
                    setTemp(item, null);
                    WirelessUtils.setConnected(temp, b.getLocation());
                    MessageUtils.message(p, "&aConnected Nodes");
                    infoHandler(temp.getBlock(), temp, p, true);
                } else {
                    MessageUtils.message(p, "&cInput node was broken or is unavailable!");
                }
            } else {
                MessageUtils.messageWithCD(p, 500, "&cClick on a wireless output node!");
            }
        }
    }

    private void clickInputNode(@Nonnull Player p) {
        MessageUtils.messageWithCD(p, 500, "&cClick on a wireless input node!");
    }

    private void infoHandler(@Nonnull Block b, @Nonnull Location l, @Nonnull Player p, boolean force) {

        if (!force && this.coolDowns.containsKey(p) && System.currentTimeMillis() - this.coolDowns.get(p) < 2000) {
            return;
        }
        this.coolDowns.put(p, System.currentTimeMillis());
        
        Location source = WirelessUtils.getTarget(b.getLocation());
        
        if (source == null) {
            MessageUtils.message(p, "&eSource block was missing!");
            WirelessUtils.breakBlock(b, p);
            return;
        }
        
        Inventory sourceInventory = TransferUtils.getInventory(source.getBlock());
        BlockMenu sourceMenu = TransferUtils.getMenu(source);
        boolean sourceVanilla = WirelessUtils.isVanilla(l);

        if (displayInfo(l, sourceMenu, sourceInventory, p, "Source", sourceVanilla)) {
            return;
        }

        Location connected = WirelessUtils.getConnected(l);

        if (connected == null) {
            MessageUtils.message(p, "&aTarget Inventory: Not connected");
            return;
        }

        Location target = WirelessUtils.getTarget(connected);

        if (target == null) {
            MessageUtils.message(p, "&aTarget block was missing!");
            return;
        }

        Inventory targetInventory = TransferUtils.getInventory(target.getBlock());
        BlockMenu targetMenu = TransferUtils.getMenu(target);
        boolean targetVanilla = WirelessUtils.isVanilla(connected);

        if (displayInfo(connected, targetMenu, targetInventory, p, "Target", targetVanilla)) {
            return;
        }
        
        if (!WirelessUtils.centerAndTest(l, connected)) {
            MessageUtils.message(p, "&aCan't show visualization, Nodes are too far apart or in different worlds!");
            return;
        }
        
        for (long i = 5 ; i <= 40 ; i += 4) {
            InfinityExpansion.runSync(() -> WirelessUtils.sendParticle(p, l, connected), i);
        }
    }
    
    private boolean displayInfo(@Nonnull Location l, @Nullable BlockMenu menu, @Nullable Inventory inv, @Nonnull Player p, @Nonnull String s, boolean vanilla) {
        try {
            MessageUtils.message(p, "&a" + s + " Inventory: " + (vanilla
                    ? ChatColor.WHITE + Objects.requireNonNull(inv).getType().getDefaultTitle()
                    : Objects.requireNonNull(menu).getPreset().getTitle()
            ));
        } catch (NullPointerException e) {
            MessageUtils.message(p, "&e" + s + " inventory was missing!");
            WirelessUtils.breakBlock(l.getBlock(), p);
            return true;
        }
        return false;
    }
    
    @Nullable
    private Location getTemp(@Nonnull ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return null;

        return WirelessUtils.toLocation(meta.getPersistentDataContainer().get(this.key, PersistentDataType.STRING));
    }

    private void setTemp(@Nonnull ItemStack item, @Nullable Location l) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return;

        String set = WirelessUtils.toString(l);

        if (set == null) {
            meta.getPersistentDataContainer().remove(this.key);
        } else {
            meta.getPersistentDataContainer().set(this.key, PersistentDataType.STRING, set);
        }

        item.setItemMeta(meta);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        this.coolDowns.remove(e.getPlayer());
    }

}
