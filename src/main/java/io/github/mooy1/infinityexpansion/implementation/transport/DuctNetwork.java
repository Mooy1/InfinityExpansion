package io.github.mooy1.infinityexpansion.implementation.transport;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class DuctNetwork {
    
    public final List<Inventory> invs = new ArrayList<>();
    public final List<BlockMenu> menus = new ArrayList<>();
    public final List<Location> locations = new ArrayList<>();
    public final List<Location> outputs = new ArrayList<>();
    public final List<Location> adjacent = new ArrayList<>();
        
    public DuctNetwork(@Nonnull Location l) {
        locations.add(l);
        outputs.add(l);
    }
}
