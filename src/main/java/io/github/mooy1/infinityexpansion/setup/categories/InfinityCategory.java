package io.github.mooy1.infinityexpansion.setup.categories;

import io.github.mooy1.infinityexpansion.implementation.blocks.InfinityWorkbench;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.player.LeaveListener;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.categories.FlexCategory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AllArgsConstructor;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.UUID;

/**
 * A custom category for displaying 6x6 recipes and their sub-recipes
 *
 * @author Mooy1
 */
public class InfinityCategory extends FlexCategory implements Listener {
    
    public static final InfinityCategory CATEGORY = new InfinityCategory(PluginUtils.getKey("infinity_recipes"),
            new CustomItem(Material.RESPAWN_ANCHOR, "&bInfinity &7Recipes"), 3
    );

    private static final int[] INFINITY_RECIPE_SLOTS = {
            1, 2, 3, 4, 5, 6,
            10, 11, 12, 13, 14, 15,
            19, 20, 21, 22, 23, 24,
            28, 29, 30, 31, 32, 33,
            37, 38, 39, 40, 41, 42,
            46, 47, 48, 49, 50, 51
    };
    private static final int[] NORMAL_RECIPE_SLOTS = {
            12, 13, 14,
            21, 22, 23,
            30, 31, 32
    };
    private static final int NORMAL_RECIPE_TYPE = 19;
    private static final int NORMAL_RECIPE_OUTPUT = 25;
    private static final int[] NORMAL_RECIPE_BACKGROUND = {
            1, 2, 3, 4, 5, 6, 7, 8,
            36, 37, 38, 39, 40, 41, 42, 43, 44
    };
    private static final int[] INFINITY_OUTPUT_BORDER = {
            25, 26, 34, 43, 44
    };
    private static final int[] INFINITY_BACKGROUND = {
            9, 18, 27, 36, 53
    };
    private static final int INFINITY_OUTPUT = 35;
    private static final int BACK = 0;
    private static final int NEXT = 52;
    private static final int PREV = 45;
    private static final int INFINITY_BENCH = 8;
    private static final int[] WORKBENCH_BORDER = {
            7, 16, 17
    };
    private static final ItemStack BENCH = new CustomItem(Material.NETHER_STAR,
            "&bCreate the recipe from items in your inventory: ",
            "&aLeft-Click to move 1 set",
            "&aRight-Click to move as many sets as possible"
    );
    private static final ItemStack INFO = new CustomItem(Material.CYAN_STAINED_GLASS_PANE, "&3Info");
    
    private static final HashMap<UUID, String> history = new HashMap<>();

    private InfinityCategory(NamespacedKey key, ItemStack item, int tier) {
        super(key, item, tier);
        LeaveListener.add(history);
    }

    @Override
    public boolean isVisible(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        return false;
    }

    @Override
    public void open(Player player, PlayerProfile playerProfile, SlimefunGuideMode slimefunGuideMode) {
        open(player, new BackEntry(null, playerProfile, slimefunGuideMode), true);
        playerProfile.getGuideHistory().add(this, 1);
    }
    
    public static void open(@Nonnull Player player, @Nonnull BackEntry entry, boolean useHistory) {
        
        if (useHistory) {
            String id = history.get(player.getUniqueId());

            if (id != null) {
                openInfinityRecipe(player, id, entry);
                return;
            }
        }
        
        ChestMenu menu = new ChestMenu("&bInfinity Recipes");

        if (entry.mode != null && entry.profile != null) {
            menu.addMenuClickHandler(1, (player1, i, itemStack, clickAction) -> {
                MultiCategory.CATEGORY.open(player1, entry.profile, entry.mode);
                return false;
            });
        } else if (entry.workBench != null) {
            menu.addMenuClickHandler(1, (player1, i, itemStack, clickAction) -> {
                entry.workBench.open(player1);
                return false;
            });
        } else {
            return;
        }

        menu.addItem(0, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        menu.setEmptySlotsClickable(false);
        for (int i = 2 ; i < 9 ; i++) {
            menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        menu.addItem(45, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        menu.addItem(46, ChestMenuUtils.getPreviousButton(player, 1, 1), ChestMenuUtils.getEmptyClickHandler());
        for (int i = 47 ; i < 52 ; i++) {
            menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        menu.addItem(52, ChestMenuUtils.getNextButton(player, 1, 1), ChestMenuUtils.getEmptyClickHandler());
        menu.addItem(53, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());

        menu.addItem(1, new CustomItem(ChestMenuUtils.getBackButton(player, "", ChatColor.GRAY + SlimefunPlugin.getLocalization().getMessage(player, "guide.back.guide"))));

        int i = 9;
        for (Pair<SlimefunItemStack, ItemStack[]> item : InfinityWorkbench.ITEMS.values()) {
            if (i == 45) break;

            menu.addItem(i, item.getFirstValue(), (p, slot, item1, action) -> {
                openInfinityRecipe(p, item.getFirstValue().getItemId(), entry);
                return false;
            });

            i++;
        }

        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);

        history.put(player.getUniqueId(), null);
        
        menu.open(player);
    }

    @ParametersAreNonnullByDefault
    private static void openInfinityRecipe(Player player, String id, BackEntry entry) {
        Pair<SlimefunItemStack, ItemStack[]> pair = InfinityWorkbench.ITEMS.get(id);

        if (pair == null) {
            return;
        }

        ChestMenu menu = new ChestMenu(Objects.requireNonNull(pair.getFirstValue().getDisplayName()));
        menu.setEmptySlotsClickable(false);
        
        menu.addItem(BACK, ChestMenuUtils.getBackButton(player, ""), (player12, i, itemStack, clickAction) -> {
            open(player12, entry, false);
            return false;
        });
        
        for (int i = 0 ; i < INFINITY_RECIPE_SLOTS.length ; i++) {
            ItemStack recipeItem = pair.getSecondValue()[i];
            if (recipeItem != null) {
                menu.addItem(INFINITY_RECIPE_SLOTS[i], recipeItem, (p, slot, item, action) -> {
                    SlimefunItem slimefunItem = SlimefunItem.getByItem(recipeItem);
                    if (slimefunItem != null && !slimefunItem.isDisabled()) {
                        if (slimefunItem.getRecipeType() == InfinityWorkbench.TYPE) {
                            openInfinityRecipe(p, slimefunItem.getId(), entry);
                        } else {
                            LinkedList<SlimefunItem> list = new LinkedList<>();
                            list.add(slimefunItem);
                            openSlimefunRecipe(p, entry, id, list);
                        }
                    }
                    return false;
                });
            }
        }

        if (entry.workBench == null) {
            menu.addItem(INFINITY_BENCH, InfinityWorkbench.ITEM, (p, slot, item, action) -> {
                SlimefunItem slimefunItem = InfinityWorkbench.ITEM.getItem();
                if (slimefunItem != null) {
                    LinkedList<SlimefunItem> list = new LinkedList<>();
                    list.add(slimefunItem);
                    openSlimefunRecipe(p, entry, id, list);
                }
                return false;
            });
        } else {
            menu.addItem(INFINITY_BENCH, BENCH, (p, slot, item, action) -> {
                moveRecipe(p, entry.workBench, pair, action.isRightClicked());
                return false;
            });
        }
        
        int page = InfinityWorkbench.IDS.indexOf(id);
        
        menu.addItem(PREV, ChestMenuUtils.getPreviousButton(player, page + 1, InfinityWorkbench.IDS.size()), (player1, i, itemStack, clickAction) -> {
            if (page > 0) {
                openInfinityRecipe(player1, InfinityWorkbench.IDS.get(page - 1), entry);
            }
            return false;
        });

        menu.addItem(NEXT, ChestMenuUtils.getNextButton(player, page + 1, InfinityWorkbench.IDS.size()), (player1, i, itemStack, clickAction) -> {
            if (page < InfinityWorkbench.IDS.size() - 1) {
                openInfinityRecipe(player1, InfinityWorkbench.IDS.get(page + 1), entry);
            }
            return false;
        });
        
        for (int slot : INFINITY_BACKGROUND) {
            menu.addItem(slot, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : INFINITY_OUTPUT_BORDER) {
            menu.addItem(slot, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        menu.addItem(INFINITY_OUTPUT, pair.getFirstValue(), ChestMenuUtils.getEmptyClickHandler());
        for (int slot : WORKBENCH_BORDER) {
            menu.addItem(slot, INFO, ChestMenuUtils.getEmptyClickHandler());
        }

        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);

        history.put(player.getUniqueId(), id);
        
        menu.open(player);

    }
    
    private static void moveRecipe(@Nonnull Player player, @Nonnull BlockMenu menu, Pair<SlimefunItemStack, ItemStack[]> pair, boolean max) {
        ItemStack[] recipe = pair.getSecondValue();
        PlayerInventory inv = player.getInventory();

        for (int i = 0 ; i < (max ? 64 : 1) ; i++) {
            for (int slot = 0 ; slot < recipe.length ; slot++) { //each item in recipe
                ItemStack recipeItem = recipe[slot];

                if (recipeItem == null) {
                    continue;
                }

                String id = StackUtils.getIDorType(recipeItem);

                for (ItemStack item : inv.getContents()) { //each slot in their inv
                    if (item != null && StackUtils.getIDorType(item).equals(id)) { //matches recipe
                        //get item
                        ItemStack output = item.clone();
                        output.setAmount(1);

                        if (menu.fits(output, InfinityWorkbench.INPUT_SLOTS[slot])) {//not null and fits
                            //remove item
                            ItemUtils.consumeItem(item, 1, false);
                            //push item
                            menu.pushItem(output, InfinityWorkbench.INPUT_SLOTS[slot]);
                            break;
                        }
                    }
                }
            }
        }

        menu.open(player);

    }

    @ParametersAreNonnullByDefault
    private static void openSlimefunRecipe(Player player, BackEntry entry, String backID, LinkedList<SlimefunItem> slimefunHistory) {
        SlimefunItem slimefunItem = slimefunHistory.peekLast();
        
        if (slimefunItem == null) {
            return;
        }
        
        ItemStack output = slimefunItem.getRecipeOutput().clone();

        ChestMenu menu = new ChestMenu(ItemUtils.getItemName(output));
        menu.setEmptySlotsClickable(false);

        int length = slimefunHistory.size();

        menu.addItem(0, ChestMenuUtils.getBackButton(player, ""), (p, slot, item, action) -> {
            if (length == 1) {
                openInfinityRecipe(player, backID, entry);
            } else {
                slimefunHistory.removeLast();
                openSlimefunRecipe(player, entry, backID, slimefunHistory);
            }
            return false;
        });
        
        for (int i = 0 ; i < NORMAL_RECIPE_SLOTS.length ; i++) {
            ItemStack recipeItem = slimefunItem.getRecipe()[i];

            if (recipeItem != null) {
                menu.addItem(NORMAL_RECIPE_SLOTS[i], recipeItem, (p, slot, item, action) -> {
                    SlimefunItem recipeSlimefunItem = SlimefunItem.getByItem(recipeItem);

                    if (recipeSlimefunItem != null) {
                        slimefunHistory.add(recipeSlimefunItem);
                        openSlimefunRecipe(player, entry, backID, slimefunHistory);
                    }
                    return false;
                });
            }
            
        }

        menu.addItem(NORMAL_RECIPE_TYPE, slimefunItem.getRecipeType().toItem(), ChestMenuUtils.getEmptyClickHandler());

        for (int slot : NORMAL_RECIPE_BACKGROUND) {
            menu.addItem(slot, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        menu.addItem(NORMAL_RECIPE_OUTPUT, output, ChestMenuUtils.getEmptyClickHandler());

        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
        menu.open(player);
    }
    
    @AllArgsConstructor
    public static class BackEntry {

        @Nullable
        private final BlockMenu workBench;
        @Nullable
        private final PlayerProfile profile;
        @Nullable
        private final SlimefunGuideMode mode;
        
    }

}