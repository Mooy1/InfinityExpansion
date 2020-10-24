package me.mooy1.infinityexpansion.implementation.transport;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.utils.ItemStackUtils;
import me.mooy1.infinityexpansion.utils.MessageUtils;
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
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OutputDuct extends SlimefunItem {

    private static final int[] WHITELIST = {
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

        new BlockMenuPreset(getId(), Objects.requireNonNull(Items.OUTPUT_DUCT.getDisplayName())) {
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
                inv.dropItems(b.getLocation(), WHITELIST);
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

        BlockMenu machine = getOutputMachine(b);
        int[] inputSlots = getSlots(machine, ItemTransportFlow.INSERT);

        if (machine != null && inputSlots != null && inputSlots.length > 0) {
            Location location = machine.getLocation();
            int loc = getRelativeLocation(location, l);
            List<Location> checked = new ArrayList<>();
            checked.add(l);
            checked.add(location);

            Pair<List<BlockMenu>, Pair<List<Location>, Integer>> flow = inputFlow(0, l, new ArrayList<>(), loc, checked);
            List<BlockMenu> list = flow.getFirstValue();
            int count = flow.getSecondValue().getSecondValue();

            MessageUtils.broadcast("COUNT" + count);

            if (list.isEmpty()) return;

            MessageUtils.broadcast("CONNECTED" + machine);
            MessageUtils.broadcast("LIST" + list.toString());

            for (int whiteList : WHITELIST) {
                String whiteListID = ItemStackUtils.getIDFromItem(machine.getItemInSlot(whiteList));

                if (whiteListID != null) {
                    for (BlockMenu menu : list) {
                        int[] slots = getSlots(menu, ItemTransportFlow.WITHDRAW);

                        if (slots != null && slots.length > 0) {
                            for (int slot : slots) {
                                ItemStack output = menu.getItemInSlot(slot);
                                String id = ItemStackUtils.getIDFromItem(output);

                                if (id != null && id.equals(whiteListID) && machine.fits(output, inputSlots)) {
                                    int amount = output.getAmount();
                                    ItemStack oneOutput = output.clone();
                                    oneOutput.setAmount(1);

                                    for (int i = 0 ; i < amount ; i ++) {
                                        machine.pushItem(oneOutput, inputSlots);
                                    }

                                    menu.consumeItem(slot, amount);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Nullable
    private BlockMenu getOutputMachine(@Nonnull Block b) {
        Location l = b.getLocation();
        Location[] locations = getAdjacentLocations(l);

        for (Location location : locations) {
            String id = locationID(location);

            if (id != null) {
                BlockMenuPreset preset = BlockMenuPreset.getPreset(id);

                if (preset != null) {

                    return BlockStorage.getInventory(location);


                }
            }
        }
        return null;
    }

    @Nullable
    private int[] getSlots(@Nullable BlockMenu menu, ItemTransportFlow itemTransportFlow) {
        if (menu != null) {

            BlockMenuPreset preset = menu.getPreset();

            int[] slots = preset.getSlotsAccessedByItemTransport(menu, itemTransportFlow, new ItemStack(Material.COBBLESTONE));

            if (slots != null && slots.length > 0) {
                return slots;
            } else {
                slots = preset.getSlotsAccessedByItemTransport(itemTransportFlow);

                if (slots != null && slots.length > 0) {
                    return slots;
                }
            }
        }

        return null;
    }

    @Nonnull
    private Pair<List<BlockMenu>, Pair<List<Location>, Integer>> inputFlow(int count, @Nonnull Location l, @Nonnull List<BlockMenu> list, int prev, @Nonnull List<Location> checked) {
        checked.add(l);

        String here = BlockStorage.getLocationInfo(l, "id");
        if (here == null) {
            return new Pair<>(list, new Pair<>(checked, count));
        }

        Location[] locations = new Location[0];

        if (here.equals("OUTPUT_DUCT") || here.equals("CONNECTOR_DUCT")) {

            locations = getAdjacentLocations(l);
            count++;

        } else if (here.equals("ITEM_DUCT")) {

            locations = getOneLocation(l, prev);
            count++;

            MessageUtils.broadcast("DUCT_LOCATIONS" + Arrays.toString(locations));

        } else if (BlockStorage.hasInventory(l.getBlock())) {

            list.add(BlockStorage.getInventory(l));

        }

        for (Location location : locations) {
            if (!checked.contains(location) && count < LENGTH) {

                int loc = getRelativeLocation(location, l);

                Pair<List<BlockMenu>, Pair<List<Location>, Integer>> flowA = inputFlow(count, location, list, loc, checked);
                Pair<List<Location>, Integer> flowB = flowA.getSecondValue();
                list = flowA.getFirstValue();
                checked = flowB.getFirstValue();
                count = flowB.getSecondValue();
            }
        }

        return new Pair<>(list, new Pair<>(checked, count));

    }

    @Nonnull
    private static Location[] getAdjacentLocations(@Nonnull Location l) {
        Location[] locations = new Location[6];
        locations[0] = l.clone().add(1, 0, 0);
        locations[1] = l.clone().add(-1, 0, 0);
        locations[2] = l.clone().add(0, 1, 0);
        locations[3] = l.clone().add(0, -1, 0);
        locations[4] = l.clone().add(0, 0, 1);
        locations[5] = l.clone().add(0, 0, -1);

        return locations;
    }

    @Nonnull
    private static Location[] getOneLocation(@Nonnull Location l, int direction) {
        Location[] location = {l};

        if (direction == 0) {
            location[0] = l.add(1, 0, 0);
        } else if (direction == 1) {
            location[0] = l.add(-1, 0, 0);
        } else if (direction == 2) {
            location[0] = l.add(0, 1, 0);
        } else if (direction == 3) {
            location[0] = l.add(0, -1, 0);
        } else if (direction == 4) {
            location[0] = l.add(0, 0, 1);
        } else if (direction == 5) {
            location[0] = l.add(0, 0, -1);
        }

        return location;
    }

    private static int getRelativeLocation(@Nonnull Location next, @Nonnull Location current) {
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

    @Nullable
    private static String locationID(@Nullable Location l) {
        if (l != null) {
            return BlockStorage.getLocationInfo(l, "id");
        }
        return null;
    }
}
