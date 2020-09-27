package me.mooy1.mooyaddon;

import lombok.SneakyThrows;
import me.mooy1.mooyaddon.Items.VoidDustResource;
import me.mooy1.mooyaddon.MooyItemSetup;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

public class MooyAddon extends JavaPlugin implements SlimefunAddon {

    private static MooyAddon instance;

    @Override
    public void onEnable() {

        instance = this;

        Config cfg = new Config(this);

        if (getConfig().getBoolean("options.auto-update") && getDescription().getVersion().startsWith("DEV - ")) {
            new GitHubBuildsUpdater(this, getFile(), "Mooy1/MooyAddon/master").start();
        }

        //Register items

        MooyItemSetup.setup(this);

        //Geo miner resources

        new VoidDustResource().register();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/Mooy1/MooyAddon/issues";
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    public static MooyAddon getInstance() {
        return instance;
    }
}