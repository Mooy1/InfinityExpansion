package me.mooy1.mooyaddon;

import me.mooy1.mooyaddon.Items.*;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

public class MooyAddon extends JavaPlugin implements SlimefunAddon {

    public static MooyAddon instance;

    @Override
    public void onEnable() {

        Config cfg = new Config(this);

        if (getConfig().getBoolean("options.auto-update") && getDescription().getVersion().startsWith("DEV - ")) {
            new GitHubBuildsUpdater(this, getFile(), "Mooy1/MooyAddon/master").start();
        }

        RegisterItems();

    }

    public void RegisterItems() {

        //add category

        MooyItems.MAIN.register();

        //add geominer resources

        new VoidDust().register();

        //add compressed cobblestones

        for (CompressedCobblestone.Compression compression : CompressedCobblestone.Compression.values()) {
            new CompressedCobblestone(compression).register(this);
        }

        //add Magnonium resources

        for (MagnoniumResource.Type type : MagnoniumResource.Type.values()) {
            new MagnoniumResource(type).register(this);
        }

        //add Magnonium tools

        for (MagnoniumTools.Tool tool : MagnoniumTools.Tool.values()) {
            new MagnoniumTools(tool).register(this);
        }
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