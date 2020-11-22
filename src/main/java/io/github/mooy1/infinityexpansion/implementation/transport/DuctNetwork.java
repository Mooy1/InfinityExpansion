package io.github.mooy1.infinityexpansion.implementation.transport;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class DuctNetwork {
    
    private final List<Inventory> invs = new ArrayList<>();
    private final List<BlockMenu> menus = new ArrayList<>();
    private final List<Location> locations = new ArrayList<>();
    private final List<Location> outputs = new ArrayList<>();
    
    public DuctNetwork() {
        
    }
}
