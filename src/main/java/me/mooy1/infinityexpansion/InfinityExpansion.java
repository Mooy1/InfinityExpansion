package me.mooy1.infinityexpansion;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mooy1.infinityexpansion.setup.ItemSetup;
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

        //stats

        final Metrics metrics = new Metrics(this, 8991);

        //config

        Config cfg = new Config(this);

        //SlimefunPlugin.getVersion()

        if (getDescription().getVersion().startsWith("DEV - ")) {
            getLogger().log(Level.INFO, "Starting auto update");
            Updater updater = new GitHubBuildsUpdater(this, this.getFile(), "Mooy1/InfinityExpansion/master");
            updater.start();
        } else {
            getLogger().log(Level.WARNING, "You must be on a DEV build to auto update!");
        }

        //Register items

        ItemSetup.setup(this);

        getLogger().log(Level.INFO, "######################################");
        getLogger().log(Level.INFO, "     Infinity Expansion v" + getDescription().getVersion() + "      ");
        getLogger().log(Level.INFO, "     -----------------------------    ");
        getLogger().log(Level.INFO, "              Changelog               ");
        getLogger().log(Level.INFO, " ");
        getLogger().log(Level.INFO, " - Added Changelog");
        getLogger().log(Level.INFO, " - Fixed Tool Transformer bug");
        getLogger().log(Level.INFO, " ");
        getLogger().log(Level.INFO, "######################################");

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
