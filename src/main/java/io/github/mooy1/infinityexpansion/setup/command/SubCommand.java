package io.github.mooy1.infinityexpansion.setup.command;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

public abstract class SubCommand {

    protected final InfinityExpansion plugin;
    protected final InfinityCommand cmd;
    @Getter
    private final boolean op;
    @Getter
    private final String name;
    @Getter
    private final String description;

    @ParametersAreNonnullByDefault
    protected SubCommand(InfinityExpansion plugin, InfinityCommand cmd, String name, String description, boolean op) {
        this.plugin = plugin;
        this.cmd = cmd;
        this.name = name;
        this.description = description;
        this.op = op;
    }

    public abstract void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args);

    @NonNull
    public abstract List<String> onTab(@Nonnull CommandSender sender, @Nonnull String[] args);
}
