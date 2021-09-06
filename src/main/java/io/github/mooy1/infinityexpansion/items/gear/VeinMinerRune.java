package io.github.mooy1.infinityexpansion.items.gear;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinitylib.common.CoolDowns;
import io.github.mooy1.infinitylib.common.Events;
import io.github.mooy1.infinitylib.common.Scheduler;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.implementation.items.magical.runes.SoulboundRune;
import me.mrCookieSlime.Slimefun.api.BlockStorage;

/**
 * A VeinMiner rune, most code from {@link SoulboundRune}
 *
 * @author Mooy1
 */
public final class VeinMinerRune extends SlimefunItem implements Listener, NotPlaceable {

    private static final String[] ALLOWED = {
            "_ORE", "_LOG", "_WOOD", "GILDED", "SOUL", "GRAVEL",
            "MAGMA", "OBSIDIAN", "DIORITE", "ANDESITE", "GRANITE", "_LEAVES",
            "GLASS", "DIRT", "GRASS", "DEBRIS", "GLOWSTONE"
    };
    private static final double RANGE = 1.5;
    private static final int MAX = 64;
    private static final String LORE = ChatColor.AQUA + "Veinminer - Crouch to use";
    private static final NamespacedKey key = InfinityExpansion.createKey("vein_miner");

    private final CoolDowns cooldowns = new CoolDowns(1000);
    private Block processing;

    public VeinMinerRune(ItemGroup category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe) {
        super(category, item, type, recipe);
        Events.registerListener(this);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (isItem(e.getItemDrop().getItemStack()) && e.getItemDrop().getItemStack().getAmount() == 1) {
            Scheduler.run(20, () -> activate(e.getPlayer(), e.getItemDrop()));
        }
    }

    private void activate(Player p, Item rune) {
        // Being sure the entity is still valid and not picked up or whatsoever.
        if (!rune.isValid()) {
            return;
        }

        Location l = rune.getLocation();
        Collection<Entity> entities = Objects.requireNonNull(l.getWorld()).getNearbyEntities(l, RANGE, RANGE, RANGE, this::findCompatibleItem);
        Optional<Entity> optional = entities.stream().findFirst();

        if (optional.isPresent()) {

            Item item = (Item) optional.get();
            ItemStack itemStack = item.getItemStack();

            if (itemStack.getAmount() == 1) {
                // This lightning is just an effect, it deals no damage.
                l.getWorld().strikeLightningEffect(l);

                Scheduler.run(10, () -> {
                    // Being sure entities are still valid and not picked up or whatsoever.
                    if (rune.isValid() && item.isValid() && rune.getItemStack().getAmount() == 1) {

                        l.getWorld().createExplosion(l, 0);
                        l.getWorld().playSound(l, Sound.ENTITY_GENERIC_EXPLODE, 0.3F, 1);

                        item.remove();
                        rune.remove();

                        setVeinMiner(itemStack, true);
                        l.getWorld().dropItemNaturally(l, itemStack);

                        p.sendMessage(ChatColor.GREEN + "Added Vein Miner to tool!");
                    }
                    else {
                        p.sendMessage(ChatColor.RED + "Failed to add vein miner!");
                    }
                });

            }
            else {
                p.sendMessage(ChatColor.RED + "Failed to add vein miner!");
            }
        }
    }

    private boolean findCompatibleItem(Entity entity) {
        if (entity instanceof Item) {
            Item item = (Item) entity;
            ItemStack stack = item.getItemStack();
            return stack.getAmount() == 1
                    && stack.getItemMeta() instanceof Damageable
                    && !isVeinMiner(stack) && !isItem(stack);
        }

        return false;
    }

    public static boolean isVeinMiner(@Nullable ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }
        return item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.BYTE);
    }

    public static void setVeinMiner(@Nullable ItemStack item, boolean makeVeinMiner) {
        if (item == null) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        boolean isVeinMiner = isVeinMiner(item);

        PersistentDataContainer container = meta.getPersistentDataContainer();

        if (makeVeinMiner && !isVeinMiner) {
            container.set(key, PersistentDataType.BYTE, (byte) 1);
            List<String> lore;
            if (meta.hasLore()) {
                lore = meta.getLore();
            }
            else {
                lore = new ArrayList<>();
            }
            lore.add(LORE);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        if (!makeVeinMiner && isVeinMiner) {
            container.remove(key);
            if (meta.hasLore()) {
                List<String> lore = meta.getLore();
                lore.remove(LORE);
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block b = e.getBlock();

        if (this.processing == b) {
            return;
        }

        Player p = e.getPlayer();

        if (!p.isSneaking()) {
            return;
        }

        ItemStack item = p.getInventory().getItemInMainHand();

        if (!isVeinMiner(item)) {
            return;
        }

        if (p.getFoodLevel() == 0) {
            p.sendMessage(ChatColor.GOLD + "You are too tired to vein-mine!");
            return;
        }

        String type = b.getType().toString();

        if (!isAllowed(type)) {
            return;
        }

        Location l = b.getLocation();

        if (BlockStorage.hasBlockInfo(l)) {
            return;
        }

        if (!this.cooldowns.checkAndReset(p.getUniqueId())) {
            p.sendMessage(ChatColor.GOLD + "You must wait 1 second before using again!");
            return;
        }

        Set<Block> found = new HashSet<>();
        Set<Location> checked = new HashSet<>();
        checked.add(l);
        getVein(checked, found, l, b);

        World w = b.getWorld();

        for (Block mine : found) {
            this.processing = mine;
            BlockBreakEvent event = new BlockBreakEvent(mine, p);
            Bukkit.getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                if (event.isDropItems()) {
                    for (ItemStack drop : mine.getDrops(item)) {
                        w.dropItemNaturally(l, drop);
                    }
                }
                mine.setType(Material.AIR);
            }
        }

        if (type.endsWith("ORE")) {
            w.spawn(b.getLocation(), ExperienceOrb.class).setExperience(found.size() * 2);
        }

        if (ThreadLocalRandom.current().nextBoolean()) {
            FoodLevelChangeEvent event = new FoodLevelChangeEvent(p, p.getFoodLevel() - 1);
            Bukkit.getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                p.setFoodLevel(event.getFoodLevel());
            }
        }
    }

    private static boolean isAllowed(String mat) {
        for (String test : ALLOWED) {
            if (mat.contains(test)) {
                return true;
            }
        }
        return false;
    }

    private static void getVein(Set<Location> checked, Set<Block> found, Location l, Block b) {
        if (found.size() >= MAX) {
            return;
        }

        for (Location check : getAdjacentLocations(l)) {
            if (checked.add(check) && check.getBlock().getType() == b.getType() && !BlockStorage.hasBlockInfo(b)) {
                found.add(b);
                getVein(checked, found, check, check.getBlock());
            }
        }
    }

    private static List<Location> getAdjacentLocations(Location l) {
        List<Location> list = new ArrayList<>();
        list.add(l.clone().add(1, 0, 0));
        list.add(l.clone().add(-1, 0, 0));
        list.add(l.clone().add(0, 1, 0));
        list.add(l.clone().add(0, -1, 0));
        list.add(l.clone().add(0, 0, 1));
        list.add(l.clone().add(0, 0, -1));
        Collections.shuffle(list);
        return list;
    }

}
