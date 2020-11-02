package io.github.mooy1.infinityexpansion.setup.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class InfinityTabCompleter implements TabCompleter {

    private static final int MAX_SUGGESTIONS = 80;

    private final InfinityCommand command;

    public InfinityTabCompleter(@Nonnull InfinityCommand command) {
        this.command = command;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, String[] args) {
        if (args.length == 1) {
            return createReturnList(command.getSubCommandNames());
        } else if (args.length > 0) {
            for (SubCommand command : command.commands) {
                if (args[0].equalsIgnoreCase(command.getName())) {
                    return createReturnList(command.onTab(sender, args));
                }
            }
        }

        return null;
    }

    @Nonnull
    private List<String> createReturnList(@Nonnull List<String> list) {
        List<String> returnList = new LinkedList<>();

        for (String item : list) {
            returnList.add(item.toLowerCase());

            if (returnList.size() >= MAX_SUGGESTIONS) {
                break;
            }
        }

        return returnList;
    }
}
