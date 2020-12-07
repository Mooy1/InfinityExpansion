package io.github.mooy1.infinityexpansion.utils;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public final class WirelessUtils {

    public static void setConnected(@Nonnull Location l, @Nullable Location set) {
        BlockStorage.addBlockInfo(l, "connected", toString(set));
    }

    public static String toString(@Nullable Location l) {
        if (l == null) {
            return null;
        }
        return l.getX() + " " + l.getY() + " " + l.getZ() + " " + Objects.requireNonNull(l.getWorld()).getName();
    }

    @Nullable
    public static Location getConnected(@Nonnull Location l) {
        return toLocation(BlockStorage.getLocationInfo(l, "connected"));
    }

    @Nullable
    public static BlockMenu getTargetMenu(@Nonnull Location l) {
        Location target = getTarget(l);
        if (target == null) return null;
        return TransferUtils.getMenu(target);
    }

    @Nullable
    public static Inventory getTargetInv(@Nonnull Location l) {
        Location target = getTarget(l);
        if (target == null) return null;
        return TransferUtils.getInventory(target.getBlock());
    }

    public static Location toLocation(@Nullable String s) {
        if (s == null) {
            return null;
        }
        String[] split = s.split(" ");
        return new Location(Bukkit.getServer().getWorld(split[3]), Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]));
    }

    @Nullable
    public static Location getTarget(@Nonnull Location l) {
        return LocationUtils.getRelativeLocation(l, Integer.parseInt(BlockStorage.getLocationInfo(l, "target")));
    }

    public static boolean isVanilla(@Nonnull Location l) {
        return Boolean.parseBoolean(BlockStorage.getLocationInfo(l, "vanilla"));
    }

    public static final BlockPlaceHandler NODE_HANDLER = new BlockPlaceHandler(false) {
        @Override
        public void onPlayerPlace(@Nonnull BlockPlaceEvent e) {
            Block b = e.getBlockPlaced();
            Block targetBlock = e.getBlockAgainst();
            Location targetLocation = targetBlock.getLocation();

            BlockMenu machine = TransferUtils.getMenu(targetLocation);
            Inventory inventory = TransferUtils.getInventory(targetBlock);

            if (inventory == null && machine == null) {
                MessageUtils.messageWithCD(e.getPlayer(), 1000, ChatColor.RED + "Must be connected to a machine or inventory!");
                InfinityExpansion.runSync(() -> WirelessUtils.breakBlock(b, e.getPlayer()), 4);
            } else {
                Location l = b.getLocation();
                setTarget(l, targetLocation);
                setVanilla(l, machine == null);
            }
        }
    };

    public static void setVanilla(@Nonnull Location l, boolean vanilla) {
        BlockStorage.addBlockInfo(l, "vanilla", Boolean.toString(vanilla));
    }

    public static void setTarget(@Nonnull Location l, @Nonnull Location target) {
        BlockStorage.addBlockInfo(l, "target", String.valueOf(LocationUtils.getDirectionInt(l, target)));
    }
    
    public static boolean centerAndTest(@Nonnull Location from, @Nonnull Location to) {
        from.add(.5, .5, .5);
        to.add(.5, .7, .5);
        return Objects.equals(to.getWorld(), from.getWorld()) && to.distance(from) < 64;
    }

    public static void sendParticle(@Nullable World w, @Nonnull Location from, @Nonnull Location to) {
        if (w == null) {
            return;
        }
        w.spawnParticle(Particle.END_ROD, from, 0, to.getX() - from.getX(), to.getY() - from.getY(), to.getZ() - from.getZ(), .09);
    }

    public static void sendParticle(@Nonnull Player p, @Nonnull Location from, @Nonnull Location to) {
        p.spawnParticle(Particle.END_ROD, from, 0, to.getX() - from.getX(), to.getY() - from.getY(), to.getZ() - from.getZ(), .09);
    }

    public static void breakBlock(@Nonnull Block b, @Nullable Player p) {
        if (p != null) {
            Bukkit.getPluginManager().callEvent(new BlockBreakEvent(b, p));
            b.setType(Material.AIR);
        }
    }

    public static void breakWithNoPlayer(@Nonnull Location l, @Nonnull ItemStack item) {
        if (l.getWorld() != null) {
            l.getWorld().dropItemNaturally(l, item);
        }
        BlockStorage.clearBlockInfo(l);
        l.getBlock().setType(Material.AIR);
    }

}
