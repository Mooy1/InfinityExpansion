package io.github.mooy1.infinityexpansion;

import io.github.mooy1.infinityexpansion.setup.Setup;
import io.github.mooy1.infinityexpansion.setup.commands.GiveRecipe;
import io.github.mooy1.infinityexpansion.setup.commands.ResetConfig;
import io.github.mooy1.infinitylib.ConfigUtils;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.command.CommandManager;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

public class InfinityExpansion extends JavaPlugin implements SlimefunAddon {

    @Getter
    private static InfinityExpansion instance;
    @Getter
    private static double difficulty = 1;

    @Override
    public void onEnable() {
        instance = this;

        PluginUtils.setup(ChatColor.AQUA + "Infinity" + ChatColor.GRAY + "Expansion", this, "Mooy1/InfinityExpansion/master", getFile());
        
        CommandManager.setup("infinityexpansion", "infinityexpansion.admin", "/ie, /ix, /infinity",
                new GiveRecipe(), new ResetConfig()
        );
        
        @SuppressWarnings("unused") final Metrics metrics = new Metrics(this, 8991);

        difficulty = ConfigUtils.getDouble(getConfig(), "balance-options.difficulty", .1, 10, 1);

        Setup.setup(this);
        
        PluginUtils.runSync(() -> PluginUtils.log("Join the Slimefun Addon Community Discord: discord.gg/V2cJR9ADFU"));
        
        PluginUtils.startTicker(() -> {});

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

}