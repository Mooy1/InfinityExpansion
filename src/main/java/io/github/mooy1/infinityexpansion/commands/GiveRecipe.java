package io.github.mooy1.infinityexpansion.commands;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinitylib.commands.AbstractCommand;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;

public final class GiveRecipe extends AbstractCommand {
    
    public GiveRecipe() {
        super("giverecipe", "gives all the items in a Slimefun item recipe", true);
    }

    @Override
    public void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this!");
            return;
        }
        
        if (args.length != 2) {
            sender.sendMessage("Usage: /ie giverecipe <ID>");
            return;
        }

        SlimefunItem sfItem = SlimefunItem.getByID(args[1].toUpperCase());
        
        if (sfItem == null || sfItem instanceof MultiBlockMachine || sfItem.getRecipeType() == RecipeType.GEO_MINER) {
            sender.sendMessage(ChatColor.RED + "Invalid Slimefun item!");
            return;
        }
        
        sender.sendMessage(ChatColor.GREEN + "Gave recipe for " + sfItem.getItemName());
        
        Player p = (Player) sender;

        List<ItemStack> recipe = new ArrayList<>();
        
        for (ItemStack e : sfItem.getRecipe()) {
            if (e != null) recipe.add(e);
        }
        
        p.getInventory().addItem(recipe.toArray(new ItemStack[0]));
    }

    @Override
    public void onTab(@Nonnull CommandSender sender, @Nonnull String[] args, @Nonnull List<String> tabs) {
        if (args.length == 2) {
            for (SlimefunItem item : SlimefunPlugin.getRegistry().getEnabledSlimefunItems()) {
                tabs.add(item.getId());
            } 
        }
    }
}