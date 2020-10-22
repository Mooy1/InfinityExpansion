package me.mooy1.infinityexpansion.utils;

import java.util.concurrent.ThreadLocalRandom;

public class MathUtils {

    private MathUtils() {}

    public static int randomFrom(int range) {
        return (int) Math.ceil(ThreadLocalRandom.current().nextDouble() * range);
    }

    public static boolean chanceIn(int chance) {
        return randomFrom(chance) == chance;
    }
}