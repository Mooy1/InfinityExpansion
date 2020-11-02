package io.github.mooy1.infinityexpansion.setup.command.subcommands;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.setup.command.InfinityCommand;
import io.github.mooy1.infinityexpansion.setup.command.SubCommand;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

public class Changelog extends SubCommand {
    public Changelog(InfinityExpansion plugin, InfinityCommand cmd) {
        super(plugin, cmd, "changelog", "displays the update changelog", false);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        sender.sendMessage(InfinityExpansion.getChangeLog());
    }
}