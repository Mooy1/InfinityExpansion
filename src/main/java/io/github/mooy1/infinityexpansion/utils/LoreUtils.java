package io.github.mooy1.infinityexpansion.utils;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;

import java.text.DecimalFormat;

/**
 * Collection of utils for building item lore
 *
 * @author Mooy1
 */
public final class LoreUtils {

    public static final int SERVER_TICK = 20;

    public static final int CUSTOM_TICKER_DELAY = SlimefunPlugin.getCfg().getInt("URID.custom-ticker-delay");

    public static final float SERVER_TICK_RATIO = (float) SERVER_TICK / CUSTOM_TICKER_DELAY;

    private LoreUtils() {}

    public static String energyPerSecond(int energy) {
        return "&8\u21E8 &e\u26A1 &7" + format(Math.round(energy * SERVER_TICK_RATIO)) + " J/s";
    }
    public static String energyBuffer(int energy) {
        return "&8\u21E8 &e\u26A1 &7" + format(energy) + " Buffer";
    }

    public static String energyPer(int energy) {
        return "&8\u21E8 &e\u26A1 &7" + format(energy) + " J ";
    }

    public static String speed(int speed) {
        return "&8\u21E8 &b\u26A1 &7Speed: &b" + speed + 'x';
    }

    public static String storesItem(int amount) {
        return "&6Capacity: &e" + format(amount) + " &eitems";
    }

    public static String storesInfinity() {
        return "&6Capacity:&e infinite items";
    }

    public static String roundHundreds(float number) {
        return format(Math.round(number * 100) / 100);
    }

    public static final DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");

    public static String format(int number) {
        return decimalFormat.format(number);
    }
}
