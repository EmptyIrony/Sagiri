package strafe.games.sagiri.listener;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.enums.Status;
import strafe.games.sagiri.manager.LogoutManager;
import strafe.games.sagiri.manager.PlayerManager;
import strafe.games.sagiri.manager.SpectatorsManager;
import strafe.games.sagiri.player.Relog;
import strafe.games.sagiri.task.PreGameTask;
import strafe.games.sagiri.utils.border.BorderUtil;

import static strafe.games.sagiri.utils.StringUtil.replaceMessage;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        PlayerManager.createPlayerData(event.getUniqueId());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {


        if (Sagiri.getIns().getStatus() == Status.WAITING) {
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
            event.getPlayer().getInventory().clear();
            event.getPlayer().setHealth(20.0D);
            event.getPlayer().setFoodLevel(20);
            event.getPlayer().setLevel(0);
            event.getPlayer().setExp(0);
            event.getPlayer().getInventory().setArmorContents(null);
            event.setJoinMessage(replaceMessage("&b" + event.getPlayer().getName() + "&e joined the game"));
            event.getPlayer().teleport(new Location(Bukkit.getWorld(Sagiri.getIns().getConfig().getString("SpawnPoint.world")), Sagiri.getIns().getConfig().getInt("SpawnPoint.x"), Sagiri.getIns().getConfig().getInt("SpawnPoint.y"), Sagiri.getIns().getConfig().getInt("SpawnPoint.z"), Sagiri.getIns().getConfig().getInt("SpawnPoint.yaw"), Sagiri.getIns().getConfig().getInt("SpawnPoint.pitch")));

            if (Bukkit.getOnlinePlayers().size() >= Sagiri.getIns().getConfig().getInt("MinPlayers") && Sagiri.getIns().getStatus() == Status.WAITING) {
                readyToStart();
            }
        } else {
            if (Sagiri.getIns().getStatus() == Status.READY) {
                event.setJoinMessage(replaceMessage("&b" + event.getPlayer().getName() + "&e joined the game"));
                event.getPlayer().teleport(new Location(Bukkit.getWorld(Sagiri.getIns().getConfig().getString("ReadyPoint.world")), Sagiri.getIns().getConfig().getInt("ReadyPoint.x"), Sagiri.getIns().getConfig().getInt("ReadyPoint.y"), Sagiri.getIns().getConfig().getInt("ReadyPoint.z"), Sagiri.getIns().getConfig().getInt("ReadyPoint.yaw"), Sagiri.getIns().getConfig().getInt("ReadyPoint.pitch")));
            } else {
                event.setJoinMessage(null);
                event.getPlayer().getInventory().clear();
                for (Relog relog : Sagiri.getIns().getRelogs()) {
                    if (relog.getPlayerUUID().equals(event.getPlayer().getUniqueId())) {
                        World world = Bukkit.getWorld("world");
                        Entity entity1 = null;
                        for (Entity entity : world.getEntities()) {
                            if (entity.getUniqueId().equals(relog.getZombieUUID())) {
                                entity1 = entity;
                                break;
                            }
                        }
                        Zombie zombie = (Zombie) entity1;
                        Sagiri.getIns().getRelogs().remove(relog);
                        assert zombie != null;
                        zombie.remove();
                        for (ItemStack item : relog.getPlaterItems()) {
                            if (item == null) {
                                continue;
                            }
                            event.getPlayer().getInventory().addItem(item);
                        }
                        event.getPlayer().getInventory().setArmorContents(relog.getArmors());
                        event.getPlayer().setHealth(zombie.getHealth());
                        event.getPlayer().teleport(zombie.getLocation());
                        return;
                    }
                }
                Sagiri.getIns().getSpectators().add(event.getPlayer().getUniqueId());
                SpectatorsManager.setSpec(event.getPlayer());
                event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0, 80, 0));

            }
        }
    }

    public void readyToStart() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(new Location(Bukkit.getWorld(Sagiri.getIns().getConfig().getString("ReadyPoint.world")), Sagiri.getIns().getConfig().getInt("ReadyPoint.x"), Sagiri.getIns().getConfig().getInt("ReadyPoint.y"), Sagiri.getIns().getConfig().getInt("ReadyPoint.z"), Sagiri.getIns().getConfig().getInt("ReadyPoint.yaw"), Sagiri.getIns().getConfig().getInt("ReadyPoint.pitch")));
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
        }
        Sagiri.getIns().setStatus(Status.READY);
        new PreGameTask().runTaskTimer(Sagiri.getIns(), 20L, 20L);
        BorderUtil.buildWalls(500, Material.BEDROCK, 5, Bukkit.getWorld("world"));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");
        if (Sagiri.getIns().getStatus() == Status.PVE || Sagiri.getIns().getStatus() == Status.PVP || Sagiri.getIns().getStatus() == Status.DEATHMATCH) {
            if (Sagiri.getIns().getAlivePlayer().contains(event.getPlayer().getUniqueId())) {
                LogoutManager.setLogout(event.getPlayer());
                Bukkit.broadcastMessage(replaceMessage("&b" + event.getPlayer().getName() + "&e Log out"));
            }
        } else if (Sagiri.getIns().getStatus() != Status.END) {
            event.setQuitMessage(replaceMessage("&b" + event.getPlayer().getName() + "&e left the game"));
        }
    }
}
