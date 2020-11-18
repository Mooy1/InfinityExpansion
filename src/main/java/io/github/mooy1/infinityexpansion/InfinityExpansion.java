package io.github.mooy1.infinityexpansion;

import io.github.mooy1.infinityexpansion.implementation.transport.OutputDuct;
import io.github.mooy1.infinityexpansion.lists.InfinityRecipes;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.mooy1.infinityexpansion.setup.ItemSetup;
import io.github.mooy1.infinityexpansion.setup.command.InfinityCommand;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.Updater;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.logging.Level;

public class InfinityExpansion extends JavaPlugin implements SlimefunAddon {

    @Getter
    private static InfinityExpansion instance;
    @Getter
    private int tickRate;
    private static int progressTick = 1;
    
    @Override
    public void onEnable() {
        //instance
        instance = this;
        tickRate = SlimefunPlugin.getCfg().getInt("URID.custom-ticker-delay");
        
        //config
        updateConfig();
        setupConfigOptions();

        //stats
        @SuppressWarnings("unused")
        final Metrics metrics = new Metrics(this, 8991);

        PaperLib.suggestPaper(this);

        //auto update
        if (getDescription().getVersion().startsWith("DEV - ")) {
            log(Level.INFO, "Starting auto update");
            Updater updater = new GitHubBuildsUpdater(this, this.getFile(), "Mooy1/InfinityExpansion/master");
            updater.start();
        } else {
            log(Level.WARNING, "You must be on a DEV build to auto update!");
        }

        //items
        ItemSetup.setup(this);

        //commands
        new InfinityCommand(this).register();

        //set enabled infinity recipes
        InfinityRecipes.setup(this);

        //spam console
        for (String line : getChangeLog()) {
            getLogger().log(Level.INFO, line);
        }
        
        //progress ticker
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (progressTick < 12) {
                progressTick ++;
            } else {
                progressTick = 1;
            }
        }, 100L, tickRate);
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

    @Nonnull
    public static String[] getChangeLog() {
        return new  String[] {
                ChatColor.GREEN + "",
                ChatColor.GREEN + "########################################",
                ChatColor.GREEN + "",
                ChatColor.AQUA + "     Infinity Expansion v" + getInstance().getPluginVersion(),
                ChatColor.GREEN + "     -------------------------    ",
                ChatColor.AQUA + "              Changelog            ",
                ChatColor.GRAY + " - Advanced anvil",
                ChatColor.GRAY + " - optimizations",
                ChatColor.GRAY + " - energy balancing",
                ChatColor.GRAY + " - Recipe balancing",
                ChatColor.GREEN + "",
                ChatColor.GREEN + "########################################",
                ChatColor.GREEN + ""
        };
    }

    public static void log(@Nonnull Level level , @Nonnull String... logs) {
        for (String log : logs) {
            instance.getLogger().log(level, log);
        }
    }

    private void updateConfig() {
        getConfig().options().copyDefaults(true);
        getConfig().options().copyHeader(true);
        saveConfig();
    }

    private void setupConfigOptions() {
        int configMax = InfinityExpansion.getInstance().getConfig().getInt("output-duct-options.max-duct-length");
        if (configMax > 3 && configMax < 33) {
            OutputDuct.DUCT_LENGTH = configMax;
        }

        configMax = InfinityExpansion.getInstance().getConfig().getInt("output-duct-options.max-input-inventories");
        if (configMax > 0 && configMax < 21) {
            OutputDuct.MAX_INVS = configMax;
        }

        configMax = InfinityExpansion.getInstance().getConfig().getInt("output-duct-options.max-slots-to-check");
        if (configMax > 0 && configMax < 54) {
            OutputDuct.MAX_SLOTS = configMax;
        }
    }
    
    public static boolean progressEvery(int i) {
        return progressOn(i, 0);
    }
    
    public static boolean progressOn(int i, int pos) {
        return progressTick % i == pos;
    }
    
    public static int currentTick() {
        return progressTick;
    }
}