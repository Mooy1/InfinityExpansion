package me.mooy1.infinityexpansion;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mooy1.infinityexpansion.materials.VoidDustResource;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

public class InfinityExpansion extends JavaPlugin implements SlimefunAddon {

    private static InfinityExpansion instance;

    @Override
    public void onEnable() {

        instance = this;

        //bstats

        //Metrics metrics = new Metrics(this, 8991);

        //config

        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update") && getDescription().getVersion().startsWith("DEV - ")) {
            new GitHubBuildsUpdater(this, getFile(), "Mooy1/InfinitExpansion/master").start();
        }

        //Register items

        ItemSetup.setup(this);

        //Geo miner resources

        new VoidDustResource().register();
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