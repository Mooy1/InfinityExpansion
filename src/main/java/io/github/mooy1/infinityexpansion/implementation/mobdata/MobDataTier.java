package io.github.mooy1.infinityexpansion.implementation.mobdata;

import lombok.AllArgsConstructor;
import org.bukkit.Material;

@AllArgsConstructor
public enum MobDataTier {
    
    // chicken
    PASSIVE(1, 75, Material.IRON_CHESTPLATE),
    
    // slime
    NEUTRAL(2, 150, Material.IRON_CHESTPLATE),
    
    // zombie
    HOSTILE(3, 300, Material.DIAMOND_CHESTPLATE),
    
    // endermen
    ADVANCED(6, 600, Material.DIAMOND_CHESTPLATE),
    
    // wither
    MINI_BOSS(60, 4500, Material.NETHERITE_CHESTPLATE),
    
    // ender dragon
    BOSS(300, 9000, Material.NETHERITE_CHESTPLATE);
    
    final int xp;
    final int energy;
    final Material material;
    
}
