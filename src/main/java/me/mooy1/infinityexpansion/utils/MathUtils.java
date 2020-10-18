package me.mooy1.infinityexpansion.utils;

public class MathUtils {

    private MathUtils() {}

    public static int randomFrom(double random, int range) {
        return (int) Math.ceil(random * range);
    }

    public static boolean chanceIn(double random, int chance) {
        return randomFrom(random, chance) == chance;
    }
}