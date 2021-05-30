package io.github.mooy1.infinityexpansion;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import javax.annotation.Nonnull;

import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinityexpansion.commands.GiveRecipe;
import io.github.mooy1.infinityexpansion.commands.PrintItem;
import io.github.mooy1.infinityexpansion.commands.SetData;
import io.github.mooy1.infinityexpansion.implementation.SlimefunExtension;
import io.github.mooy1.infinityexpansion.implementation.Blocks;
import io.github.mooy1.infinityexpansion.implementation.Gear;
import io.github.mooy1.infinityexpansion.implementation.Generators;
import io.github.mooy1.infinityexpansion.implementation.Machines;
import io.github.mooy1.infinityexpansion.implementation.Materials;
import io.github.mooy1.infinityexpansion.implementation.MobData;
import io.github.mooy1.infinityexpansion.implementation.Storage;
import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;
import io.github.mooy1.infinitylib.commands.AbstractCommand;

public final class InfinityExpansion extends AbstractAddon {

    private static InfinityExpansion instance;

    public static InfinityExpansion inst() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        super.onEnable();

        if (getServer().getPluginManager().getPlugin("LiteXpansion") != null) {
            runSync(() -> log(Level.WARNING,
                    "########################################################",
                    "LiteXpansion nerfs energy generation in this addon.",
                    "You can disable these nerfs in the LiteXpansion config.",
                    "Under 'options:' add 'nerf-other-addons: false'",
                    "########################################################"
            ));
        }

        try {
            Categories.setup(this);
            MobData.setup(this);
            Materials.setup(this);
            Machines.setup(this);
            Gear.setup(this);
            Blocks.setup(this);
            Storage.setup(this);
            Generators.setup(this);
            SlimefunExtension.setup(this);
        } catch (Throwable e) {
            runSync(() -> {
                log(Level.SEVERE,
                        "The following error has occurred during InfinityExpansion startup!",
                        "Not all items will be available because of this!",
                        "Report this on Github or Discord and make sure to update Slimefun!"
                );
                e.printStackTrace();
            });
        }
    }

    @Override
    protected Metrics setupMetrics() {
        return new Metrics(this, 8991);
    }

    @Nonnull
    @Override
    protected String getGithubPath() {
        return "Mooy1/InfinityExpansion/master";
    }

    @Nonnull
    @Override
    protected List<AbstractCommand> setupSubCommands() {
        return Arrays.asList(new GiveRecipe(), new SetData(), new PrintItem());
    }

    @Override
    public void onDisable() {
        instance = null;
    }

}
