package io.github.mooy1.infinityexpansion;

import io.github.mooy1.infinityexpansion.implementation.mobdata.MobSimulationChamber;
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
import java.util.Objects;
import java.util.logging.Level;

public class InfinityExpansion extends JavaPlugin implements SlimefunAddon {

    @Getter
    private static InfinityExpansion instance;
    @Getter
    private static int tickRate;
    @Getter
    private static int currentTick = 1;
    @Getter
    public static double vanillaScale = 1;
    
    @Override
    public void onEnable() {
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
        
        //ticker
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (currentTick < 60) {
                currentTick++;
            } else {
                currentTick = 1;
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
        OutputDuct.DUCT_LENGTH = getOrDefault("output-duct-options.max-duct-length", 4, 32, 12, config);
        OutputDuct.MAX_INVS = getOrDefault("output-duct-options.max-input-inventories", 1, 20, 8, config);
        OutputDuct.MAX_SLOTS = getOrDefault("output-duct-options.max-slots-to-check", 1, 54, 9, config);
        StorageUnit.SIGN_REFRESH = getOrDefault("storage-unit-options.sign-refresh-ticks", 1, 10, 5, config);
        StorageUnit.DISPLAY_SIGNS = getOrDefault("storage-unit-options.display-signs", true, config);
        MobSimulationChamber.CHANCE = getOrDefault("balance-options.mob-simulation-xp-chance", 1, 10, 2, config);
        vanillaScale = getOrDefault("balance-options.vanilla-economy-scale", .1, 10, 1, config);
        
        saveConfig();
    }
    
    private int getOrDefault(String path, int min, int max, int def, FileConfiguration config) {
        if (hasPath(path, config)) {
            int value = config.getInt(path);
            if (value >= min && value <= max) {
                return value;
            } else {
                configWarnValue(path);
                config.set(path, def);
                return def;
            }
        }
        return def;
    }

    private boolean getOrDefault(String path, boolean def, FileConfiguration config) {
        if (hasPath(path, config)) {
            String value = config.getString(path);
            if (Objects.equals(value, "true")) {
                return true;
            } else if (Objects.equals(value, "false")) {
                return false;
            } else {
                configWarnValue(path);
                config.set(path, def);
                return def;
            }
        }
        return def;
    }

    private double getOrDefault(String path, double min, double max, double def, FileConfiguration config) {
        if (hasPath(path, config)) {
            double value = config.getDouble(path);
            if (value >= min && value <= max) {
                return value;
            } else {
                configWarnValue(path);
                config.set(path, def);
                return def;
            }
        }
        return def;
    }
    
     private boolean hasPath(String path, FileConfiguration config) {
         if (config.contains(path)) {
             return true;
         } else {
             configWarnPath(path);
             return false;
         }
     }
    
    private void configWarnValue(String path) {
        log(Level.WARNING, "Config value at " + path + " was out of bounds, resetting it to default");
    }

    private void configWarnPath(String path) {
        log(Level.SEVERE, "Config was missing path " + path + ", please add this path or reset your config!");
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
        return currentTick % rate == pos;
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