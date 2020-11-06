package io.github.mooy1.infinityexpansion.setup.command.subcommands;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.setup.command.InfinityCommand;
import io.github.mooy1.infinityexpansion.setup.command.SubCommand;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ReloadConfig extends SubCommand {
    public ReloadConfig(InfinityExpansion plugin, InfinityCommand cmd) {
        super(plugin, cmd, "reloadconfig", "refreshes the config, all current options will be set to default, this might not work", true);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {

        if (args.length < 2 || !args[1].equals("yes")) {
            sender.sendMessage(ChatColor.RED + "Confirm resetting config");
            return;
        }

        InfinityExpansion.getInstance().saveDefaultConfig();
        sender.sendMessage(ChatColor.GREEN + "Config reset to default!");
    }

    @Override
    public @NonNull List<String> onTab(@Nonnull CommandSender sender, @Nonnull String[] args) {
        List<String> tabs = new ArrayList<>();

        if (args.length == 2) {
            tabs.add("Are-you-sure?");
            tabs.add("yes");
            tabs.add("no");
        }

        return tabs;
    }
}