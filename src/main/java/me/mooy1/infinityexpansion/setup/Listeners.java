package me.mooy1.infinityexpansion.setup;

import me.mooy1.infinityexpansion.InfinityExpansion;

public class Listeners implements org.bukkit.event.Listener {
    public Listeners(InfinityExpansion plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);


    }
}
