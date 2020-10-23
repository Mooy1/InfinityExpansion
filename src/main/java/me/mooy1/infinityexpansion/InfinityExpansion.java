package me.mooy1.infinityexpansion;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mooy1.infinityexpansion.lists.InfinityRecipes;
import me.mooy1.infinityexpansion.setup.ItemSetup;
import me.mooy1.infinityexpansion.setup.Listeners;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.Updater;
import org.bstats.bukkit.Metrics;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
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

        //get enabled infinity recipes

        setupInfinityRecipes();

        //listeners

        new Listeners(this);

        //spam console

        getLogger().log(Level.INFO, "######################################");
        getLogger().log(Level.INFO, "     Infinity Expansion v" + getDescription().getVersion() + "      ");
        getLogger().log(Level.INFO, "     -----------------------------    ");
        getLogger().log(Level.INFO, "              Changelog               ");
        getLogger().log(Level.INFO, " - Added some item generators");
        getLogger().log(Level.INFO, " - Optimized strainers");
        getLogger().log(Level.INFO, " - added item converters");
        getLogger().log(Level.INFO, " - Stored Items in storage units will");
        getLogger().log(Level.INFO, " No longer be dropped, instead stored in drop");
        getLogger().log(Level.INFO, "######################################");

    }

    private static void setupInfinityRecipes() {
        List<ItemStack> enabledOutputs = new ArrayList<>();
        List<ItemStack[]> enabledRecipes = new ArrayList<>();

        int i = 0;
        for (ItemStack output : InfinityRecipes.OUTPUTS) {
            SlimefunItem slimefunItem = SlimefunItem.getByItem(output);
            assert slimefunItem != null;

            if (!slimefunItem.isDisabled()) {
                enabledOutputs.add(output);
                enabledRecipes.add(InfinityRecipes.RECIPES[i]);
            } else {
               instance.getLogger().log(Level.INFO, "Infinity Item " + slimefunItem.getId() + " disabled!");
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

    public static InfinityExpansion getInstance() {
        return instance;
    }
}
