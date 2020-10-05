package me.mooy1.infinityexpansion;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.Updater;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.logging.Level;

public class InfinityExpansion extends JavaPlugin implements SlimefunAddon {

    private static InfinityExpansion instance;

    @Override
    public void onEnable() {

        instance = this;

        //bstats

        final Metrics metrics = new Metrics(this, 8991);

        //config

        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update") && getDescription().getVersion().startsWith("DEV - ")) {
            Updater updater = new GitHubBuildsUpdater(this, this.getFile(), "Mooy1/InfinityExpansion/master");
            updater.start();
        } else {
            if (!cfg.getBoolean("options.auto-update")) {
                getLogger().log(Level.WARNING, "Enable auto updates in the config to get the latest builds!");
            }
            if (!getDescription().getVersion().startsWith("DEV - ")){
                getLogger().log(Level.INFO, "You must be on a DEV build to auto update!");
                getLogger().log(Level.INFO, "Current version: " + getDescription().getVersion());
            }
        }

        //Register items

        ItemSetup.setup(this);

    }

    @Override
    public void onDisable() {
        instance = null;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/Mooy1/InfinityExpansion/issues";
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    public static InfinityExpansion getInstance() {
        return instance;
    }
}
