package me.mooy1.infinityexpansion.utils;

import java.util.concurrent.ThreadLocalRandom;

public class MathUtils {

    private MathUtils() {}

    public static int randomFromZeroTo(int max) {
        return (int) Math.floor(ThreadLocalRandom.current().nextFloat() * (max + 1));
    }

    public static int randomFromOneTo(int max) {
        return (int) Math.floor(ThreadLocalRandom.current().nextFloat() * (max));
    }

    public static boolean chanceIn(int chance) {
        return randomFromOneTo(chance) == chance;
    }
}