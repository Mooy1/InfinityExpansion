package io.github.mooy1.infinityexpansion.setup.commands;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinitylib.command.LibCommand;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Changelog extends LibCommand {
    public Changelog() {
        super("changelog", "displays the update changelog", false);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        sender.sendMessage(InfinityExpansion.getChangeLog());
    }

    @Nonnull
    @Override
    public List<String> onTab(@Nonnull CommandSender sender, @Nonnull String[] args) {
        return new ArrayList<>();
    }
}