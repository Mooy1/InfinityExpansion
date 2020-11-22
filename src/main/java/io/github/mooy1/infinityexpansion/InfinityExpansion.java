package io.github.mooy1.infinityexpansion;

import io.github.mooy1.infinityexpansion.implementation.storage.StorageUnit;
import io.github.mooy1.infinityexpansion.implementation.transport.OutputDuct;
import io.github.mooy1.infinityexpansion.lists.InfinityRecipes;
import io.github.mooy1.infinityexpansion.setup.ItemSetup;
import io.github.mooy1.infinityexpansion.setup.command.InfinityCommand;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
import org.apache.commons.lang.Validate;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
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
        setupConfigOptions(getConfig());

        //stats
        @SuppressWarnings("unused")
        final Metrics metrics = new Metrics(this, 8991);

        PaperLib.suggestPaper(this);

        //auto update
        if (getDescription().getVersion().startsWith("DEV - ")) {
            log(Level.INFO, "Starting auto update");
            new GitHubBuildsUpdater(this, this.getFile(), "Mooy1/InfinityExpansion/master").start();
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
            if (progressTick < 60) {
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
                ChatColor.AQUA + " Infinity Expansion v" + getInstance().getPluginVersion() + " Changelog",
                ChatColor.GREEN + "     -------------------------    ",
                ChatColor.GREEN + "",
                ChatColor.GRAY + " - signs on storage units display info",
                ChatColor.GRAY + " - config for enchants",
                ChatColor.GRAY + " - optimizations",
                ChatColor.GRAY + " - energy balancing",
                ChatColor.GRAY + " - fixed exploits",
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

    private void setupConfigOptions(FileConfiguration config) {
        int configMax = config.getInt("output-duct-options.max-duct-length");
        if (configMax > 3 && configMax < 33) {
            OutputDuct.DUCT_LENGTH = configMax;
        } else {
            config.set("output-duct-options.max-duct-length", 12);
        }

        configMax = config.getInt("output-duct-options.max-input-inventories");
        if (configMax > 0 && configMax < 21) {
            OutputDuct.MAX_INVS = configMax;
        } else {
            config.set("output-duct-options.max-input-inventories", 8);
        }

        configMax = config.getInt("output-duct-options.max-slots-to-check");
        if (configMax > 0 && configMax < 54) {
            OutputDuct.MAX_SLOTS = configMax;
        } else {
            config.set("output-duct-options.max-slots-to-check", 9);
        }

        configMax = config.getInt("storage-unit-options.sign-refresh-ticks");
        if (configMax > 0 && configMax <= 20) {
            StorageUnit.SIGN_REFRESH = configMax;
        } else {
            config.set("storage-unit-options.sign-refresh-ticks", 5);
        }
        StorageUnit.DISPLAY_SIGNS = config.getBoolean("storage-unit-options.display-signs");
        
        saveConfig();
    }

    /**
     * @param rate ticks per progress
     * @return whether the block should progress
     */
    public static boolean progressEvery(int rate) {
        return progressOn(rate, 0);
    }
    
    /**
     * @param rate ticks per progress
     * @param pos offset from from other progress at same rate
     * @return whether the block should progress
     */
    public static boolean progressOn(int rate, int pos) {
        return progressTick % rate == pos;
    }
    
    public static int currentTick() {
        return progressTick;
    }
    
    public static void runSync(@Nonnull Runnable runnable, long delay) {
        Validate.notNull(runnable, "Cannot run null");
        Validate.isTrue(delay >= 0, "The delay cannot be negative");
        
        if (instance == null || !instance.isEnabled()) {
            return;
        }

        instance.getServer().getScheduler().runTaskLater(instance, runnable, delay);
    }

    public static void runSync(@Nonnull Runnable runnable) {
        Validate.notNull(runnable, "Cannot run null");

        if (instance == null || !instance.isEnabled()) {
            return;
        }

        instance.getServer().getScheduler().runTask(instance, runnable);
    }
}