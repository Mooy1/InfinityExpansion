package io.github.mooy1.infinityexpansion.items.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class StorageDupeFix {

    public static void fixDuplicateStorages(Logger logger) {
        long time = System.nanoTime();
        int totalDupes = 0;
        File folder = new File("data-storage/Slimefun/stored-blocks/");
        String[] ids = new String[] {
                "INFINITY_STORAGE",
                "VOID_STORAGE",
                "REINFORCED_STORAGE",
                "ADVANCED_STORAGE",
                "BASIC_STORAGE"
        };

        for (File world : folder.listFiles()) {
            int locationBeginIndex = world.getName().length() + 1;
            Set<String> locations = new HashSet<>();

            for (String id : ids) {
                File storages = new File(world, id + ".sfb");

                if (!storages.exists()) {
                    continue;
                }

                List<String> lines;
                int dupes = 0;

                try {
                    lines = Files.readAllLines(storages.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }

                Iterator<String> iterator = lines.listIterator();
                while (iterator.hasNext()) {
                    String line = iterator.next();
                    String location = line.substring(locationBeginIndex, line.indexOf(':'));
                    if (locations.contains(location)) {
                        iterator.remove();
                        dupes++;
                    } else {
                        locations.add(location);
                    }
                }

                if (dupes > 0) {
                    totalDupes += dupes;
                    try {
                        Files.write(storages.toPath(), lines);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        time = System.nanoTime() - time;
        if (totalDupes > 0) {
            logger.log(Level.INFO, "Fixed " + totalDupes + " duplicate storage(s) in " + (time / 1000000) + " ms");
        }
    }

}
