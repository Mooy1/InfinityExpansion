package io.github.mooy1.infinityexpansion.setup.commands;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.command.LibCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ResetConfig extends LibCommand {
    public ResetConfig() {
        super("resetconfig", "resets the config, all current options will be set to default, this might not work", true);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {

        if (args.length < 2 || !args[1].equals("yes")) {
            sender.sendMessage(ChatColor.RED + "Confirm resetting config");
            return;
        }

        FileConfiguration config = InfinityExpansion.getInstance().getConfig();
        InputStream resource = InfinityExpansion.getInstance().getResource("config.yml");
        if (resource == null) {
            PluginUtils.log(Level.WARNING, "&cError reading default config, report this bug!");
            return;
        }
        FileConfiguration defaults = YamlConfiguration.loadConfiguration(new InputStreamReader(resource));
        for (String path : config.getKeys(false)) {
            config.set(path, defaults.get(path));
        }
        InfinityExpansion.getInstance().saveConfig();
        sender.sendMessage(ChatColor.GREEN + "Config reset, restart server to apply defaults!");
    }

    @Nonnull
    @Override
    public List<String> onTab(@Nonnull CommandSender sender, @Nonnull String[] args) {
        List<String> tabs = new ArrayList<>();

        if (args.length == 2) {
            tabs.add("Are-you-sure?");
            tabs.add("yes");
            tabs.add("no");
        }

        return tabs;
    }
}