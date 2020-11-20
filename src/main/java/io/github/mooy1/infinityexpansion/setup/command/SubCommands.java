package io.github.mooy1.infinityexpansion.setup.command;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.setup.command.subcommands.Changelog;
import io.github.mooy1.infinityexpansion.setup.command.subcommands.GiveRecipe;
import io.github.mooy1.infinityexpansion.setup.command.subcommands.Help;
import io.github.mooy1.infinityexpansion.setup.command.subcommands.ResetConfig;

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
        commands.add(new ResetConfig(plugin, cmd));
        commands.add(new GiveRecipe(plugin, cmd));

        return commands;
    }
}
