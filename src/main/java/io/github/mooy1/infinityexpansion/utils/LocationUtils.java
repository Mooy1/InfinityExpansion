package io.github.mooy1.infinityexpansion.utils;

import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Collection of locations utils
 *
 * @author Mooy1
 */
public class LocationUtils {

    private LocationUtils() {}

    /**
     * This method returns an array of all adjacent locations from a location
     *
     * @param l location
     * @return adjacent locations
     */
    @Nonnull
    public static Location[] getAdjacentLocations(@Nonnull Location l) {
        Location[] locations = new Location[6];
        locations[0] = l.clone().add(1, 0, 0);
        locations[1] = l.clone().add(-1, 0, 0);
        locations[2] = l.clone().add(0, 1, 0);
        locations[3] = l.clone().add(0, -1, 0);
        locations[4] = l.clone().add(0, 0, 1);
        locations[5] = l.clone().add(0, 0, -1);

        return locations;
    }

    /**
     * This method gets an adjacent location from a location and the direction
     *
     * @param l location
     * @param direction direction which can be found between 2 locations with getDirectionInt
     * @return the location in the direction
     */
    @Nullable
    public static Location getRelativeLocation(@Nonnull Location l, int direction) {
        Location location = null;
        Location clone = l.clone();

        if (direction == 0) {
            location = clone.add(1, 0, 0);
        } else if (direction == 1) {
            location = clone.add(-1, 0, 0);
        } else if (direction == 2) {
            location = clone.add(0, 1, 0);
        } else if (direction == 3) {
            location = clone.add(0, -1, 0);
        } else if (direction == 4) {
            location = clone.add(0, 0, 1);
        } else if (direction == 5) {
            location = clone.add(0, 0, -1);
        }

        return location;
    }

    /**
     * This method gets the direction integer from 2 locations
     *
     * @param next the next location
     * @param current the current location
     * @return the direction int to go from the current to the next
     */
    public static int getDirectionInt(@Nonnull Location next, @Nonnull Location current) {
        int a = next.getBlockX();
        int b = next.getBlockY();
        int c = next.getBlockZ();
        int x = current.getBlockX();
        int y = current.getBlockY();
        int z = current.getBlockZ();

        if (a - x == 1) {
            return 0;
        } else if (a - x == -1) {
            return 1;
        } else if (b - y == 1) {
            return 2;
        } else if (b - y == -1) {
            return 3;
        } else if (c - z == 1) {
            return 4;
        } else if (c - z == -1) {
            return 5;
        } else {
            return 0;
        }
    }
}
