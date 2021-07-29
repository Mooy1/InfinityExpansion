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
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.players.CoolDownMap;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.implementation.items.magical.runes.SoulboundRune;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

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
    private static final NamespacedKey key = InfinityExpansion.inst().getKey("vein_miner");

    private final CoolDownMap cooldowns = new CoolDownMap(1000);
    private final Set<Block> processing = new HashSet<>();

    public VeinMinerRune(Category category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe) {
        super(category, item, type, recipe);
        InfinityExpansion.inst().registerListener(this);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (isItem(e.getItemDrop().getItemStack()) && e.getItemDrop().getItemStack().getAmount() == 1) {
            InfinityExpansion.inst().runSync(() -> activate(e.getPlayer(), e.getItemDrop()), 20L);
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

                InfinityExpansion.inst().runSync(() -> {
                    // Being sure entities are still valid and not picked up or whatsoever.
                    if (rune.isValid() && item.isValid() && rune.getItemStack().getAmount() == 1) {

                        l.getWorld().createExplosion(l, 0);
                        l.getWorld().playSound(l, Sound.ENTITY_GENERIC_EXPLODE, 0.3F, 1);

                        item.remove();
                        rune.remove();

                        setVeinMiner(itemStack, true);
                        l.getWorld().dropItemNaturally(l, itemStack);

                        p.sendMessage(ChatColor.GREEN + "Added Vein Miner to tool!");
                    } else {
                        p.sendMessage(ChatColor.RED + "Failed to add vein miner!");
                    }
                }, 10L);

            } else {
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
        if (item == null) return;

        ItemMeta meta = item.getItemMeta();

        boolean isVeinMiner = isVeinMiner(item);

        PersistentDataContainer container = meta.getPersistentDataContainer();

        if (makeVeinMiner && !isVeinMiner) {
            container.set(key, PersistentDataType.BYTE, (byte) 1);
            item.setItemMeta(meta);
            StackUtils.addLore(item, LORE);
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

        if (this.processing.contains(b)) return;

        Player p = e.getPlayer();

        if (!p.isSneaking()) return;

        ItemStack item = p.getInventory().getItemInMainHand();

        if (!isVeinMiner(item)) {
            return;
        }

        if (p.getFoodLevel() == 0) {
            p.sendMessage(ChatColor.GOLD + "You are too tired to vein-mine!");
            return;
        }

        String type = b.getType().toString();

        if (!isAllowed(type)) return;

        Location l = b.getLocation();

        if (BlockStorage.hasBlockInfo(l)) return;

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
            this.processing.add(mine);
            BlockBreakEvent event = new BlockBreakEvent(mine, p);
            Bukkit.getPluginManager().callEvent(event);
            this.processing.remove(mine);
            if (!event.isCancelled()) {
                mine.setType(Material.AIR);
                if (event.isDropItems() && !"SMELTERS_PICKAXE".equals(StackUtils.getID(item))) {
                    for (ItemStack drop : mine.getDrops(item)) {
                        w.dropItemNaturally(l, drop);
                    }
                }
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
        if (found.size() >= MAX) return;

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
