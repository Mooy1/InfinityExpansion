package me.mooy1.infinityexpansion.utils;

import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Collection of utils for creating BlockMenuPresets
 *
 * @author Mooy1
 */
public final class PresetUtils {

    private PresetUtils() {}

    public static final int[] slotChunk1 = {0, 1, 2, 9, 11, 18, 19, 20};
    public static final int slot1 = 10;

    public static final int[] slotChunk2 = {3, 4, 5, 12, 14, 21, 22, 23};
    public static final int slot2 = 13;

    public static final int[] slotChunk3 = {6, 7, 8, 15, 17, 24, 25, 26};
    public static final int slot3 = 16;

    public static final int[] largeOutput = {
            13, 14, 15, 16,
            22, 23, 24, 25,
            31, 32, 33, 34,
            40, 41, 42, 43
    };

    public static final int[] largeOutputBorder = {
            3, 4, 5, 6, 7, 8,
            12, 17,
            21, 26,
            30, 35,
            39, 44,
            48, 49, 50, 51, 52, 53
    };

    public static final ItemStack loadingItemRed = new CustomItem(
            Material.RED_STAINED_GLASS_PANE,
            "&cLoading...");
    public static final ItemStack invalidInput = new CustomItem(
            Material.BARRIER,
            "&cInvalid Input!");
    public static final ItemStack invisibleBackground = new CustomItem(
            Material.LIGHT_GRAY_STAINED_GLASS_PANE,
            " ");
    public static final ItemStack craftedIn =  new CustomItem(
            Material.CYAN_STAINED_GLASS_PANE,
            "&3Crafted in");
    public static final ItemStack loadingItemBarrier = new CustomItem(
            Material.BARRIER,
            "&cLoading...");
    public static final ItemStack inputAnItem = new CustomItem(
            Material.BLUE_STAINED_GLASS_PANE,
            "&9Input an item");
    public static final ItemStack invalidRecipe = new CustomItem(
            Material.BARRIER,
            "&cInvalid Recipe!");
    public static final ItemStack notEnoughEnergy = new CustomItem(
            Material.RED_STAINED_GLASS_PANE,
            "&cNot enough energy!");
    public static final ItemStack notEnoughRoom = new CustomItem(
            Material.ORANGE_STAINED_GLASS_PANE,
            "&6Not enough room!");
    public static final ItemStack borderItemInput = new CustomItem(
            Material.BLUE_STAINED_GLASS_PANE,
            "&9Input");
    public static final ItemStack borderItemOutput = new CustomItem(
            Material.ORANGE_STAINED_GLASS_PANE,
            "&6Output");
    public static final ItemStack borderItemStatus = new CustomItem(
            Material.CYAN_STAINED_GLASS_PANE,
            "&3Status");
    public static final ItemStack connectToEnergyNet = new CustomItem(
            Material.RED_STAINED_GLASS_PANE,
            "&cConnect to an energy network!");
}
