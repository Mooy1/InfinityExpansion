package me.mooy1.infinityexpansion.setup.command.subcommands;

import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mooy1.infinityexpansion.setup.command.InfinityCommand;
import me.mooy1.infinityexpansion.setup.command.SubCommand;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

public class Help extends SubCommand {
    protected Help(InfinityExpansion plugin, InfinityCommand cmd) {
        super(plugin, cmd, "help", "displays this", false);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        cmd.sendHelp(sender);
    }
}
