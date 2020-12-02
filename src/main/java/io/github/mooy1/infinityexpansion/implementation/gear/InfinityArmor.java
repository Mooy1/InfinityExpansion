package io.github.mooy1.infinityexpansion.implementation.gear;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.InfinityRecipes;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.lists.RecipeTypes;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.attributes.ProtectionType;
import io.github.thebusybiscuit.slimefun4.core.attributes.ProtectiveArmor;
import io.github.thebusybiscuit.slimefun4.core.attributes.Soulbound;
import io.github.thebusybiscuit.slimefun4.implementation.items.armor.SlimefunArmorPiece;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.NamespacedKey;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * armor
 *
 * @author Mooy1
 */
public class InfinityArmor extends SlimefunArmorPiece implements ProtectiveArmor, Soulbound, NotPlaceable { //add sf bow too

    public InfinityArmor(@Nonnull Type type) {
        super(Categories.INFINITY_CHEAT , type.getItem(), RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(type.getItem()), type.getPotionEffect());
    }

    @Nonnull
    @Override
    public ProtectionType[] getProtectionTypes() {
        return new ProtectionType[] {
                ProtectionType.BEES, ProtectionType.RADIATION, ProtectionType.FLYING_INTO_WALL
        };
    }

    @Override
    public boolean isFullSetRequired() {
        return false;
    }

    @Nullable
    @Override
    public NamespacedKey getArmorSetId() {
        return new NamespacedKey(InfinityExpansion.getInstance(), "infinity_armor");
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {
        HEAD(Items.INFINITY_CROWN, new PotionEffect[] {
                new PotionEffect(PotionEffectType.SATURATION, 1200, 0),
                new PotionEffect(PotionEffectType.NIGHT_VISION, 1200, 0),
                new PotionEffect(PotionEffectType.WATER_BREATHING, 1200, 0)
        }),
        CHEST(Items.INFINITY_CHESTPLATE, new PotionEffect[] {
                new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1200, 1),
                new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1200, 1),
                new PotionEffect(PotionEffectType.ABSORPTION, 1200, 2),
        }),
        PANTS(Items.INFINITY_LEGGINGS, new PotionEffect[] {
                new PotionEffect(PotionEffectType.CONDUIT_POWER, 1200, 0),
                new PotionEffect(PotionEffectType.FAST_DIGGING, 1200, 2),
                new PotionEffect(PotionEffectType.REGENERATION, 1200, 0),
        }),
        BOOTS(Items.INFINITY_BOOTS, new PotionEffect[] {
                new PotionEffect(PotionEffectType.SPEED, 1200, 2),
                new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 1200, 0),
                new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1200, 0)
        });

        @Nonnull
        private final SlimefunItemStack item;
        @Nonnull
        private final PotionEffect[] potionEffect;
    }
}