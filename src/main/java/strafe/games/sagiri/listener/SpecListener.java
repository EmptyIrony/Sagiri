package strafe.games.sagiri.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import strafe.games.miku.profile.Profile;
import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.manager.MenuManager;
import strafe.games.sagiri.utils.StringUtil;

import static strafe.games.sagiri.utils.StringUtil.replaceMessage;

public class SpecListener implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (Sagiri.getIns().getSpectators().contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        if (Sagiri.getIns().getSpectators().contains(event.getEntity().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (Sagiri.getIns().getSpectators().contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (Sagiri.getIns().getSpectators().contains(event.getEntity().getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (Sagiri.getIns().getSpectators().contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().getItemMeta().getDisplayName() != null && event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(StringUtil.replaceMessage("&cPlayer Selector"))) {
                event.getPlayer().openInventory(MenuManager.getPlayerSelectorMenu(event.getPlayer()));
            } else if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().getItemMeta().getDisplayName() != null && event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(StringUtil.replaceMessage("&bTeleport to 00"))) {
                event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0, 80, 0));
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (Sagiri.getIns().getSpectators().contains(player.getUniqueId())) {
                event.setCancelled(true);
                if (event.getView().getTitle().equalsIgnoreCase(replaceMessage("&cPlayer Selector"))) {
                    if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
                        if (Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getDisplayName()) != null) {
                            event.getWhoClicked().teleport(Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getDisplayName()).getLocation());
                            ((Player) event.getWhoClicked()).sendMessage(replaceMessage("&eTeleport to &b" + Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getDisplayName()).getName()));
                        } else {
                            World world = Bukkit.getWorld("world");
                            for (Entity entity : world.getEntities()) {
                                if (entity.getUniqueId().equals(Profile.getByUsername(event.getCurrentItem().getItemMeta().getDisplayName()).getUuid())) {
                                    event.getWhoClicked().teleport(entity.getLocation());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (Sagiri.getIns().getSpectators().contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
