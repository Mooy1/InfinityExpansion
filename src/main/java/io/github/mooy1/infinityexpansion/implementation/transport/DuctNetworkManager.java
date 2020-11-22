package io.github.mooy1.infinityexpansion.implementation.transport;

import org.bukkit.Location;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class DuctNetworkManager {
    
    private static final List<DuctNetwork> networks = new ArrayList<>();
    
    public static void tick() {
        
    }

    private static void addToOrCreateNetwork(@Nonnull Location l) {

    }
    
    private static void addToNetwork(@Nonnull Location l) {
        
    }

    private static void createNetwork(@Nonnull Location l) {
        networks.add(new DuctNetwork(l));
    }
    
    public static void remove(@Nonnull Location l) {
        
    }
}
