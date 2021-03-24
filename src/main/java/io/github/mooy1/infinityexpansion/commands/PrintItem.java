package io.github.mooy1.infinityexpansion.commands;

import io.github.mooy1.infinitylib.commands.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public final class PrintItem extends AbstractCommand {

    public PrintItem() {
        super("printitem", "Prints the internal data of an item for debugging purposes", true);
    }

    @Override
    public void onExecute(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return;
        }

        Player p = (Player) commandSender;

        ItemStack item = p.getInventory().getItemInMainHand();
        
        if (item.getType() == Material.AIR) {
            p.sendMessage(ChatColor.RED + "You must be holding an item!");
            return;
        }
        
        p.sendMessage(item.toString());
    }

    @Override
    public void onTab(@Nonnull CommandSender commandSender, @Nonnull String[] strings, @Nonnull List<String> list) {
        
    }

}
