package me.mooy1.infinityexpansion.setup.command;

import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mooy1.infinityexpansion.setup.command.InfinityCommand;
import me.mooy1.infinityexpansion.setup.command.SubCommand;
import me.mooy1.infinityexpansion.setup.command.subcommands.Changelog;
import me.mooy1.infinityexpansion.setup.command.subcommands.Help;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SubCommands {

    private SubCommands() {}

    public static Collection<SubCommand> getAllCommands(InfinityCommand cmd) {
        InfinityExpansion plugin = cmd.getPlugin();
        List<SubCommand> commands = new LinkedList<>();

        commands.add(new Changelog(plugin, cmd));
        commands.add(new Help(plugin, cmd));

        return commands;
    }
}
