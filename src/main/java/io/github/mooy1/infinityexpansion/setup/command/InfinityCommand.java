package io.github.mooy1.infinityexpansion.setup.command;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Command stuff, Many stuffs from Slimefun's Command stuff
 *
 * @author Mooy1
 *
 */
public class InfinityCommand implements CommandExecutor, Listener {

    private boolean registered = false;
    private final InfinityExpansion plugin;
    public final List<SubCommand> commands = new LinkedList<>();

    public InfinityCommand(@Nonnull InfinityExpansion plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
        if (args.length > 0) {
            for (SubCommand command : commands) {
                if (args[0].equalsIgnoreCase(command.getName())) {
                    if (!command.isOp() || sender.hasPermission("infinityexpansion.admin")) {
                        command.onExecute(sender, args);
                    } else {
                        sendNoPerm(sender);
                    }
                    return true;
                }
            }
        }
        return false;
    }
    @Nonnull
    public InfinityExpansion getPlugin() {
        return plugin;
    }

    public void register() {
        Validate.isTrue(!registered, "Infinity Expansion's subcommands have already been registered!");

        registered = true;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        Objects.requireNonNull(plugin.getCommand("infinityexpansion")).setExecutor(this);
        Objects.requireNonNull(plugin.getCommand("infinityexpansion")).setTabCompleter(new InfinityTabCompleter(this));
        commands.addAll(SubCommands.getAllCommands(this));
    }

    public void sendHelp(@Nonnull CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(ChatColors.color("&7----------&b&l InfinityExpansion &7----------"));
        sender.sendMessage("");
        sender.sendMessage(ChatColors.color("&6Aliases: &e/ie, /ix, /infinity"));
        sender.sendMessage("");

        for (SubCommand cmd : commands) {
            if (!cmd.isOp() || sender.hasPermission("infinityexpansion.admin")) {
                sender.sendMessage(ChatColors.color("&6/ie " + cmd.getName() + " &e- " + cmd.getDescription()));
            }
        }

        sender.sendMessage("");
    }

    public void sendNoPerm(@Nonnull CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equalsIgnoreCase("/help infinityexpansion")) {
            sendHelp(e.getPlayer());
            e.setCancelled(true);
        }
    }

    public List<String> getSubCommandNames() {
        return commands.stream().map(SubCommand::getName).collect(Collectors.toList());
    }
}
