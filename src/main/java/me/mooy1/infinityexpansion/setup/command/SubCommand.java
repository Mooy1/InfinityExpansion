package me.mooy1.infinityexpansion.setup.command;

import me.mooy1.infinityexpansion.InfinityExpansion;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public abstract class SubCommand {

    protected final InfinityExpansion plugin;
    protected final InfinityCommand cmd;

    private final String name;
    private final String description;
    private final boolean hidden;

    @ParametersAreNonnullByDefault
    protected SubCommand(InfinityExpansion plugin, InfinityCommand cmd, String name, String description, boolean hidden) {
        this.plugin = plugin;
        this.cmd = cmd;

        this.name = name;
        this.description = description;
        this.hidden = hidden;
    }

    @Nonnull
    public final String getName() {
        return name;
    }

    public final boolean isHidden() {
        return hidden;
    }

    public abstract void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args);

    @Nonnull
    protected String getDescription() {
        return description;
    }
}
