package me.mooy1.infinityexpansion.implementation.transport;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OutputDuct extends SlimefunItem {

    private static final int[] INPUT_SLOTS = {
            PresetUtils.slot2
    };
    private static final int[] BACKGROUND = {
            0, 1, 2, 6, 7, 8,
            9, 10 ,11, 15, 16, 17,
            18, 19, 20, 24, 25, 26
    };
    private static final int LENGTH = 16;

    public OutputDuct() {
        super(Categories.STORAGE_TRANSPORT, Items.OUTPUT_DUCT, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{

        });

        new BlockMenuPreset(getId(), Objects.requireNonNull(Items.RESOURCE_SYNTHESIZER.getDisplayName())) {
            @Override
            public void init() {
                setupInv(this);
            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return (player.hasPermission("slimefun.inventory.bypass")
                        || SlimefunPlugin.getProtectionManager().hasPermission(
                        player, block.getLocation(), ProtectableAction.ACCESS_INVENTORIES));
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
                return new int[0];
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
                return new int[0];
            }
        };

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), INPUT_SLOTS);
            }

            return true;
        });
    }

    private void setupInv(BlockMenuPreset blockMenuPreset) {
        for (int i : BACKGROUND) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.slotChunk2) {
            blockMenuPreset.addItem(i, new CustomItem(Material.WHITE_STAINED_GLASS_PANE, "&fWhitelist"),
                    ChestMenuUtils.getEmptyClickHandler());
        }
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) {
                OutputDuct.this.tick(b);
            }

            public boolean isSynchronized() {
                return true;
            }
        });
    }

    public void tick(Block b) {
        Location l = b.getLocation();

        Location machine = getOutputMachine(b);
        int[] outputSlots = getOutputSlots(machine);

        if (machine != null && outputSlots != null && outputSlots.length > 0) {
            int loc = getRelativeLocation(l, machine);
            Map<BlockMenu, Integer> inputs = inputFlow(0, l, new HashMap<>(), loc);

            if (inputs.isEmpty()) return;


        }
    }

    @Nullable
    private Location getOutputMachine(Block b) {
        Location l = b.getLocation();
        Location[] locations = getAllLocations(l);

        for (Location location : locations) {
            String id = locationID(location);

            if (id != null) {
                BlockMenuPreset preset = BlockMenuPreset.getPreset(id);

                if (preset != null) {

                    int[] slots = preset.getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
                    BlockMenu menu = BlockStorage.getInventory(location);

                    for (int slot : slots) {
                        if (menu.getItemInSlot(slot) != null) {
                            return location;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Nullable
    private int[] getOutputSlots(Location l) {
        String id = locationID(l);

        if (id != null) {
            BlockMenuPreset preset = BlockMenuPreset.getPreset(id);

            if (preset != null) {
                return preset.getSlotsAccessedByItemTransport(ItemTransportFlow.INSERT);
            }
        }
        return null;
    }

    @Nonnull
    private Map<BlockMenu, Integer> inputFlow(int count, Location l, Map<BlockMenu, Integer> map, int prev) {
        count++;
        if (count >= LENGTH) return map;

        String here = BlockStorage.getLocationInfo(l, "id");
        if (here == null) return map;

        if (here.equals("OUTPUT_DUCT") || here.equals("CONNECTOR_DUCT")) {

            for (Location location : getAllButOneLocation(l, prev)) {
                int loc = getRelativeLocation(l, location);
                map = inputFlow(count, location, map, loc);
            }

        } else if (here.equals("ITEM_DUCT")) {

            Location location = getOneLocation(l, prev);
            int loc = getRelativeLocation(l, location);
            map = inputFlow(count, location, map, loc);

        } else {

            BlockMenu
        }

        return map;
    }

    @Nonnull
    private static Location[] getAllLocations(Location l) {
        Location[] locations = new Location[6];
        locations[0] = l.add(1, 0, 0);
        locations[1] = l.add(-1, 0, 0);
        locations[2] = l.add(0, 1, 0);
        locations[3] = l.add(0, -1, 0);
        locations[4] = l.add(0, 0, 1);
        locations[5] = l.add(0, 0, -1);

        return locations;
    }

    @Nonnull
    private static Location[] getAllButOneLocation(Location l, int direction) {
        Location[] original = getAllLocations(l);
        Location[] locations = new Location[5];

        for (int i = 0 ; i < 6 ; i++) {
            if (i == direction) i++;
            locations[i] = original[i];
        }

        return locations;
    }

    @Nonnull
    private static Location getOneLocation(Location l, int direction) {
        Location location = l;

        if (direction == 0) {
            location = l.add(1, 0, 0);
        } else if (direction == 1) {
            location = l.add(-1, 0, 0);
        } else if (direction == 2) {
            location = l.add(0, 1, 0);
        } else if (direction == 3) {
            location = l.add(0, -1, 0);
        } else if (direction == 4) {
            location = l.add(0, 0, 1);
        } else if (direction == 5) {
            location = l.add(0, 0, -1);
        }

        return location;
    }

    private static int getRelativeLocation(Location current, Location previous) {
        int a = current.getBlockX();
        int b = current.getBlockY();
        int c = current.getBlockZ();
        int x = previous.getBlockX();
        int y = previous.getBlockY();
        int z = previous.getBlockZ();

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
            return 6;
        }
    }

    @Nullable
    private static String locationID(Location l) {
        return BlockStorage.getLocationInfo(l, "id");
    }
}
