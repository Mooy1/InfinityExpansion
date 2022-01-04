package io.github.mooy1.infinityexpansion.items.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.experimental.UtilityClass;

import io.github.thebusybiscuit.slimefun4.libraries.dough.common.CommonPatterns;

@UtilityClass
public final class StorageSaveFix {

    public static void fixStuff(Logger logger) {
        long time = System.nanoTime();
        int fixed = 0;
        File folder = new File("data-storage/Slimefun/stored-blocks/");
        String[] ids = new String[] {
                "INFINITY_STORAGE",
                "VOID_STORAGE",
                "REINFORCED_STORAGE",
                "ADVANCED_STORAGE",
                "BASIC_STORAGE"
        };

        for (File world : folder.listFiles()) {
            String name = world.getName();
            int locationBeginIndex = name.length() + 1;
            Map<String, String> locations = new HashMap<>();

            for (String id : ids) {
                File storages = new File(world, id + ".sfb");

                if (!storages.exists()) {
                    continue;
                }

                List<String> lines;
                boolean changed = false;

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
                    String correct = locations.get(location);

                    if (correct == null) {
                        locations.put(location, id);
                    } else {
                        iterator.remove();
                        changed = true;
                        if (fixed++ < 25) {
                            String[] cords = CommonPatterns.SEMICOLON.split(location);
                            logger.log(Level.INFO, "Fixed bugged " + correct + " in "
                                    + name + " @ "
                                    + cords[0] + ", "
                                    + cords[1] + ", "
                                    + cords[2]);
                        }
                    }
                }

                if (changed) try {
                    Files.write(storages.toPath(), lines);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        time = System.nanoTime() - time;
        if (fixed > 0) {
            logger.log(Level.INFO, "Fixed " + fixed + " bugged storage(s) in " + (time / 1000000) + " ms");
        }
    }

}
