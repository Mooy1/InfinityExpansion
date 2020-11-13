package io.github.mooy1.infinityexpansion.utils;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Collection of utils for math and random stuffs
 *
 * @author Mooy1
 */
public class MathUtils {

    private MathUtils() {}

    /**
     * This method returns a random int from the given range, inclusive
     *
     * @param min minimum int, inclusive
     * @param max maximum int, inclusive
     * @return random int in range
     */
    public static int randomFromRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * This method gets a random ItemStack from the array
     *
     * @param array array of outputs
     * @return random output
     */
    @Nonnull
    public static ItemStack randomOutput(@Nonnull ItemStack[] array) {
        return array[randomFromRange(0, array.length - 1)].clone();
    }

    /**
     * This method will return true 1 / chance times.
     *
     * @param chance average tries to return true
     * @return true if chance
     */
    public static boolean chanceIn(int chance) {
        return randomFromRange(1, chance) == chance;
    }
}