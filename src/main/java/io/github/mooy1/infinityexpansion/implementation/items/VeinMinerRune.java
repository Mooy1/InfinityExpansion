package io.github.mooy1.infinityexpansion.implementation.items;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.LocationUtils;
import io.github.mooy1.infinityexpansion.utils.MessageUtils;
import io.github.mooy1.infinityexpansion.utils.StackUtils;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.implementation.items.magical.SoulboundRune;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectionManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * A VeinMiner rune, most code from {@link SoulboundRune}
 * 
 * @author Mooy1
 * 
 */
public class VeinMinerRune extends SlimefunItem implements Listener, NotPlaceable {

    private static final double RANGE = 1.5;
    private static final int MAX = 64;
    private static final long CD = 1000;
    private static NamespacedKey key = null;
    private static final Map<Player, Long> CDS = new HashMap<>();
    private static final String[] LORE = {"", ChatColor.AQUA + "Veinminer - Crouch to use"};
    private static final List<String> ALLOWED = new ArrayList<>(Arrays.asList(
            "_ORE", "_LOG", "_WOOD", "GILDED", "SOUL", "GRAVEL",
            "MAGMA", "OBSIDIAN", "DIORITE", "ANDESITE", "GRANITE", "_LEAVES",
            "GLASS", "DIRT", "GRASS", "DEBRIS", "GLOWSTONE"
    ));
    
    public VeinMinerRune(InfinityExpansion plugin) {
        super(Categories.MAIN, Items.VEIN_MINER_RUNE, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
                Items.MAGSTEEL, SlimefunItems.PICKAXE_OF_VEIN_MINING, Items.MAGSTEEL,
                new ItemStack(Material.REDSTONE_ORE), SlimefunItems.BLANK_RUNE, new ItemStack(Material.LAPIS_ORE),
                Items.MAGSTEEL, SlimefunItems.MAGIC_LUMP_3, Items.MAGSTEEL,
        });
        
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        key = new NamespacedKey(plugin, "vein_miner");
    }
    
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (isItem(e.getItemDrop().getItemStack()) && e.getItemDrop().getItemStack().getAmount() == 1) {
            InfinityExpansion.runSync(() -> activate(e.getPlayer(), e.getItemDrop()), 20L);
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
                
                InfinityExpansion.runSync(() -> {
                    // Being sure entities are still valid and not picked up or whatsoever.
                    if (rune.isValid() && item.isValid() && rune.getItemStack().getAmount() == 1) {

                        l.getWorld().createExplosion(l, 0);
                        l.getWorld().playSound(l, Sound.ENTITY_GENERIC_EXPLODE, 0.3F, 1);

                        item.remove();
                        rune.remove();

                        setVeinMiner(itemStack, true);
                        l.getWorld().dropItemNaturally(l, itemStack);

                        MessageUtils.message(p, ChatColor.GREEN + "Added Vein Miner to tool!");
                    } else {
                        MessageUtils.message(p, ChatColor.RED + "Failed to add vein miner!");
                    }
                }, 10L);
                
            } else {
                MessageUtils.message(p, ChatColor.RED + "Failed to add vein miner!");
            }
        }
    }

    private boolean findCompatibleItem(Entity entity) {
        if (entity instanceof Item) {
            Item item = (Item) entity;
            ItemStack stack = item.getItemStack();

            return stack.getAmount() == 1 && stack.getItemMeta() instanceof Damageable && !isVeinMiner(stack) && !isItem(stack) ;
        }

        return false;
    }

    public static boolean isVeinMiner(@Nonnull ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();

            return container.has(key, PersistentDataType.BYTE);
        }

        return false;
    }
    
    public static void setVeinMiner(@Nullable ItemStack item, boolean makeVeinMiner) {
        if (item == null) return;

        ItemMeta meta = item.getItemMeta();
        
        if (meta == null) return;

        boolean isVeinMiner = isVeinMiner(item);

        PersistentDataContainer container = meta.getPersistentDataContainer();

        if (makeVeinMiner && !isVeinMiner) {
            container.set(key, PersistentDataType.BYTE, (byte) 1);
            item.setItemMeta(meta);
            StackUtils.addLore(item, LORE);
        }

        if (!makeVeinMiner && isVeinMiner) {
            container.remove(key);
            item.setItemMeta(meta);
            StackUtils.removeLore(item, -1, LORE[1], 2);
        }
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        
        if (!p.isSneaking()) return;
        
        ItemStack item = p.getInventory().getItemInMainHand();
        
        if (isVeinMiner(item)) {
            
            if (p.getFoodLevel() == 0) {
                MessageUtils.messageWithCD(p, 500, ChatColor.GOLD + "You are too tired to vein-mine!");
                return;
            }

            Block b = e.getBlock();

            if (!isAllowed(b.getType().toString())) return;

            Location l = b.getLocation();

            if (BlockStorage.hasBlockInfo(l)) return;

            if (CDS.containsKey(p) && System.currentTimeMillis() - CDS.get(p) < CD) {
                MessageUtils.messageWithCD(p, 500, ChatColor.GOLD + "Wait " + ChatColor.YELLOW + (CD - (System.currentTimeMillis() - CDS.get(p))) + ChatColor.GOLD + " ms before using again!");
                return;
            }

            CDS.put(p, System.currentTimeMillis());
            
            List<Block> found = new ArrayList<>();
            List<Location> checked = new ArrayList<>();

            checked.add(l);
            
            checkSimilarBlocks(checked, found, l, b);

            World w = b.getWorld();

            ProtectionManager manager = SlimefunPlugin.getProtectionManager();

            for (Block mine : found) {
                if (manager.hasPermission(p, b.getLocation(), ProtectableAction.BREAK_BLOCK)) {
                    for (ItemStack drop : mine.getDrops(item)) {
                        (w).dropItemNaturally(l, drop);
                    }
                    mine.setType(Material.AIR);
                }
            }

            p.setFoodLevel(Math.max(0, p.getFoodLevel() - 1));
        }
    }
    
    private boolean isAllowed(String mat) {
        for (String test : ALLOWED) {
            if (test.startsWith("_")) {
                if (mat.endsWith(test)) return true;
            } else {
                if (mat.contains(test)) return true;
            }
        }
        return false;
    }
    
    private void checkSimilarBlocks(List<Location> checked, List<Block> found, Location l, Block b) {
        if (found.size() >= MAX) return;
        
        found.add(b);
        
        for (Location check : LocationUtils.getAdjacentLocations(l, true)) {
            if (checked.contains(check) || BlockStorage.hasBlockInfo(check)) continue;

            checked.add(check);

            if (check.getBlock().getType() == b.getType()) {
                checkSimilarBlocks(checked, found, check, check.getBlock());
            }
        }
    }
    
    @EventHandler
    private void onPlayerLeave(PlayerQuitEvent e) {
        CDS.remove(e.getPlayer());
    }
}
