package io.github.mooy1.infinityexpansion;

import io.github.mooy1.infinityexpansion.lists.InfinityRecipes;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.mooy1.infinityexpansion.setup.ItemSetup;
import io.github.mooy1.infinityexpansion.setup.command.InfinityCommand;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.Updater;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class InfinityExpansion extends JavaPlugin implements SlimefunAddon {

    private static InfinityExpansion instance;

    @Override
    public void onEnable() {
        instance = this;

        //config
        updateConfig();

        //stats
        @SuppressWarnings("unused")
        final Metrics metrics = new Metrics(this, 8991);

        PaperLib.suggestPaper(this);

        //auto update
        if (getDescription().getVersion().startsWith("DEV - ")) {
            getLogger().log(Level.INFO, "Starting auto update");
            Updater updater = new GitHubBuildsUpdater(this, this.getFile(), "Mooy1/InfinityExpansion/master");
            updater.start();
        } else {
            getLogger().log(Level.WARNING, "You must be on a DEV build to auto update!");
        }

        //items
        ItemSetup.setup(this);

        //commands
        new InfinityCommand(this).register();

        //set enabled infinity recipes
        setupInfinityRecipes();

        //spam console
        for (String line : getChangeLog()) {
            getLogger().log(Level.INFO, line);
        }
    }

    /**
     * This method gets the enabled infinity items and
     * adds them and their recipe to the machine
     */
    private static void setupInfinityRecipes() {
        List<ItemStack> enabledOutputs = new ArrayList<>();
        List<ItemStack[]> enabledRecipes = new ArrayList<>();

        int i = 0;
        for (ItemStack output : InfinityRecipes.OUTPUTS) {
            SlimefunItem slimefunItem = SlimefunItem.getByItem(output);

            if (!Objects.requireNonNull(slimefunItem).isDisabled()) {
                enabledOutputs.add(output);
                enabledRecipes.add(InfinityRecipes.RECIPES[i]);
            } else {
                instance.getLogger().log(Level.INFO, ChatColor.RED + "Infinity Item " + slimefunItem.getId() + " disabled!");
            }
            i++;
        }

        InfinityRecipes.OUTPUTS = enabledOutputs.toArray(new ItemStack[0]);
        InfinityRecipes.RECIPES = enabledRecipes.toArray(new ItemStack[0][]);
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
    public static InfinityExpansion getInstance() {
        return instance;
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
                ChatColor.GRAY + " - Added output duct config options",
                ChatColor.GRAY + " - Infinity Matrix added",
                ChatColor.GRAY + " - Added ItemDucts and OutputDucts,",
                ChatColor.GRAY + " These may be buggy, make sure to report issues!",
                ChatColor.GRAY + " You can use /ie reloadconfig to get a new config with new options!",
                ChatColor.GREEN + "",
                ChatColor.GREEN + "########################################",
                ChatColor.GREEN + ""
        };
    }

    private void updateConfig() {
        getConfig().options().copyDefaults(true);
        getConfig().options().copyHeader(true);
        saveConfig();
    }
}