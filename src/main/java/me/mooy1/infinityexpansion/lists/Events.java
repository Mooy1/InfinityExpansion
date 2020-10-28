package me.mooy1.infinityexpansion.lists;

import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mooy1.infinityexpansion.implementation.gear.InfinityArmor;
import me.mooy1.infinityexpansion.utils.MessageUtils;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

/**
 * events for addon
 *
 * @author Mooy1
 */
public class Events implements Listener {

    public Events(InfinityExpansion plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * This event gives the player flight when equipping a full set of infinity gear
     *
     * @param e event
     */
    @EventHandler
    public void activateInfinityFly(InventoryClickEvent e) {
        if (e.getSlotType() == InventoryType.SlotType.ARMOR) {
            PlayerInventory inv = (PlayerInventory) e.getClickedInventory();
            if (inv != null) {
                ItemStack[] armor = inv.getArmorContents();

                for (ItemStack item : armor) {
                    if (item == null) return;
                }

                Player p = (Player) e.getWhoClicked();

                int count = 0;
                for (ItemStack item : armor) {
                    if (SlimefunItem.getByItem(item) instanceof InfinityArmor) {
                        count++;
                    }
                }

                if (count == 4) {
                    enableFlight(p);
                } else if (count > 0) {
                    disableFlight(p);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e) {
        tryDisableFlight(e.getPlayer(), e.getItemDrop().getItemStack());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        List<ItemStack> drops = e.getDrops();

        for (ItemStack drop : drops) {
            if (tryDisableFlight(e.getEntity(), drop)) {
                break;
            }
        }
    }

    private static boolean tryDisableFlight(Player p, ItemStack item) {
        if (SlimefunItem.getByItem(item) instanceof InfinityArmor) {
            disableFlight(p);
            return true;
        }
        return false;
    }

    private static void disableFlight(Player p) {
        MessageUtils.message(p, ChatColor.RED + "Infinity Flight Disabled!");
        p.setAllowFlight(false);
    }

    private static void enableFlight(Player p) {
        MessageUtils.message(p, ChatColor.GREEN + "Infinity Flight Enabled!");
        p.setAllowFlight(true);
    }

}


