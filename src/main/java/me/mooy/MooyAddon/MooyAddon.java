package me.mooy.mooyaddon;

import org.bukkit.plugin.java.JavaPlugin;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
import me.mooy.mooyaddon.Items.*;

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

    @Override
    public void onDisable() {
        instance = null;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/Mooy1/MooyAddon/issues";
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    public void RegisterItems() {

        MooyItems.MAIN.register();
        new VoidDust().register();
        new MagnoniumResource(MagnoniumResource.Type.BLOCK).register(this);
        new MagnoniumResource(MagnoniumResource.Type.CORE).register(this);
        new MagnoniumResource(MagnoniumResource.Type.ALLOY).register(this);
        new MagnoniumTools(MagnoniumTools.Tool.CROWN).register(this);
        new MagnoniumTools(MagnoniumTools.Tool.CHESTPLATE).register(this);
        new MagnoniumTools(MagnoniumTools.Tool.LEGGINGS).register(this);
        new MagnoniumTools(MagnoniumTools.Tool.BOOTS).register(this);
        new MagnoniumTools(MagnoniumTools.Tool.BLADE).register(this);
        new MagnoniumTools(MagnoniumTools.Tool.PICKAXE).register(this);
        new CompressedCobblestone(CompressedCobblestone.Compression.ONE).register(this);
        new CompressedCobblestone(CompressedCobblestone.Compression.TWO).register(this);
        new CompressedCobblestone(CompressedCobblestone.Compression.THREE).register(this);
        new CompressedCobblestone(CompressedCobblestone.Compression.FOUR).register(this);
        new CompressedCobblestone(CompressedCobblestone.Compression.FIVE).register(this);
        new CompressedCobblestone(CompressedCobblestone.Compression.SIX).register(this);
        new CompressedCobblestone(CompressedCobblestone.Compression.SEVEN).register(this);
        new CompressedCobblestone(CompressedCobblestone.Compression.EIGHT).register(this);
        new CompressedCobblestone(CompressedCobblestone.Compression.NINE).register(this);
    }

    public static MooyAddon getInstance() {
        return instance;
    }
}
