package me.mooy1.infinityexpansion.utils;

import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class PresetItemUtils {

    private PresetItemUtils() {}

    public static final ItemStack loadingItemLime = new CustomItem(
            Material.LIME_STAINED_GLASS_PANE,
            "&aLoading...");
    public static final ItemStack loadingItemRed = new CustomItem(
            Material.RED_STAINED_GLASS_PANE,
            "&cLoading...");
    public static final ItemStack loadingItemBarrier = new CustomItem(
            Material.BARRIER,
            "&cLoading...");

    public static final ItemStack recipesItem = new CustomItem(
            Material.BOOK,
            "&eRecipes", "&cComing Soon");

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
    public static final ItemStack borderItemEnergy = new CustomItem(
            Material.GREEN_STAINED_GLASS_PANE,
            "&2Energy");
    public static final ItemStack borderItemStatus = new CustomItem(
            Material.CYAN_STAINED_GLASS_PANE,
            "&3Status");
}
