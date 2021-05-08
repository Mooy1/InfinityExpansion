package io.github.mooy1.infinityexpansion.commands;

import java.util.List;
import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.mooy1.infinitylib.commands.AbstractCommand;
import me.mrCookieSlime.Slimefun.api.BlockStorage;

public final class SetData extends AbstractCommand {

    public SetData() {
        super("setdata", "Set slimefun block data of the block you are looking at", true);
    }

    @Override
    public void onExecute(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can use this!");
            return;
        }
        
        if (strings.length != 3) {
            commandSender.sendMessage(ChatColor.RED + "You must specify a key and value to set!");
            return;
        }

        Player p = (Player) commandSender;

        Block target = p.getTargetBlockExact(8, FluidCollisionMode.NEVER);

        if (target == null || target.getType() == Material.AIR) {
            p.sendMessage(ChatColor.RED + "You need to target a block to use this command!");
            return;
        }

        String id =  BlockStorage.getLocationInfo(target.getLocation(), "id");
        
        if (id == null) {
            p.sendMessage(ChatColor.RED + "You need to target a slimefun block to use this command!");
            return;
        }
        
        if (strings[1].equals("id")) {
            p.sendMessage(ChatColor.RED + "You cannot change the id of this block, it could cause internal issues!");
            return;
        }
        
        if (strings[2].equals("\\remove")) {
            p.sendMessage(ChatColor.GREEN + "Successfully removed value of key '" + strings[1] + "' in " + id);
            BlockStorage.addBlockInfo(target, strings[1], null);
        } else {
            p.sendMessage(ChatColor.GREEN + "Successfully set key '" + strings[1] + "' to value '" + strings[2] + "' in " + id);
            BlockStorage.addBlockInfo(target, strings[1], strings[2]);
        }
    }

    @Override
    public void onTab(@Nonnull CommandSender commandSender, @Nonnull String[] strings, @Nonnull List<String> list) {
        if (!(commandSender instanceof Player)) {
            return;
        }

        Player p = (Player) commandSender;

        Block target = p.getTargetBlockExact(8, FluidCollisionMode.NEVER);

        if (target == null || target.getType() == Material.AIR) {
            return;
        }

        if (strings.length == 2) {
            if (BlockStorage.hasBlockInfo(target)) {
                list.addAll(BlockStorage.getLocationInfo(target.getLocation()).getKeys());
                list.remove("id");
            }
        } else if (strings.length == 3 && !strings[1].equals("id")) {
            String current = BlockStorage.getLocationInfo(target.getLocation(), strings[1]);
            if (current != null) {
                list.add(current);
                list.add("\\remove");
            }
        }
    }

}
