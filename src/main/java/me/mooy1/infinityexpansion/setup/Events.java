package me.mooy1.infinityexpansion.setup;

import me.mooy1.infinityexpansion.InfinityExpansion;
import org.bukkit.event.Listener;

public class Events implements Listener {
    public Events(InfinityExpansion plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


}
