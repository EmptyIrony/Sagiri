package strafe.games.sagiri.manager;

import net.minecraft.server.v1_8_R3.EntityZombie;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import strafe.games.miku.profile.Profile;
import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.player.Relog;
import strafe.games.sagiri.task.LogoutTask;

import java.util.UUID;

import static strafe.games.sagiri.utils.StringUtil.replaceMessage;

public class LogoutManager implements Listener {
    public static void setLogout(Player player) {
        Relog relog = new Relog(player.getUniqueId(), spawnLogoutEntity(player), player.getInventory().getContents(), player.getInventory().getArmorContents());
        new LogoutTask(Sagiri.getIns()).runTaskTimer(Sagiri.getIns(),20,20);
        Sagiri.getIns().getRelogs().add(relog);
    }

    private static UUID spawnLogoutEntity(Player player) {
        Zombie zombie = (Zombie) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
        zombie.setCustomNameVisible(true);
        CraftZombie craftZombie = (CraftZombie) zombie;
        zombie.setCustomName(replaceMessage("&7&m" + player.getName()));
        zombie.setHealth(player.getHealth());
        EntityZombie entityZombie = craftZombie.getHandle();

        NBTTagCompound nbtTag = entityZombie.getNBTTag();
        if (nbtTag==null){
            nbtTag = new NBTTagCompound();
        }
        entityZombie.c(nbtTag);
        nbtTag.setInt("NoAI",1);
        entityZombie.f(nbtTag);

        craftZombie.setVillager(true);
        zombie.setFireTicks(player.getFireTicks());
        zombie.getEquipment().setItemInHand(player.getItemInHand());
        zombie.getEquipment().setArmorContents(player.getInventory().getArmorContents());
        return zombie.getUniqueId();
    }

    @EventHandler
    public void onHitLogoutZombie(EntityDeathEvent event) {
        if (event.getEntity().getType() == EntityType.ZOMBIE) {
            for (Relog relog : Sagiri.getIns().getRelogs()) {
                if (event.getEntity().getUniqueId().equals(relog.getZombieUUID())) {
                    Location zombieDeathLocation = event.getEntity().getLocation();
                    for (ItemStack item : relog.getPlaterItems()) {
                        if (item == null || item.getType() == Material.AIR) {
                            continue;
                        }
                        zombieDeathLocation.getWorld().dropItemNaturally(zombieDeathLocation, item);
                    }
                    for (ItemStack item : relog.getArmors()) {
                        if (item == null || item.getType() == Material.AIR) {
                            continue;
                        }
                        zombieDeathLocation.getWorld().dropItemNaturally(zombieDeathLocation, item);
                    }
                    Profile profile = Profile.getByUuid(relog.getPlayerUUID());
                    Sagiri.getIns().getRelogs().remove(relog);
                    event.getEntity().getLocation().getWorld().strikeLightningEffect(event.getEntity().getLocation());
                    int deatherKills;
                    int killerKills;
                    if (Sagiri.getIns().getKills().get(profile.getPlayer()) == null) {
                        deatherKills = 0;
                    } else {
                        deatherKills = Sagiri.getIns().getKills().get(profile.getPlayer());
                    }
                    if (Sagiri.getIns().getKills().get(event.getEntity().getKiller()) == null) {
                        killerKills = 1;
                        Sagiri.getIns().getKills().put(event.getEntity().getKiller(), 1);
                    } else {
                        killerKills = Sagiri.getIns().getKills().get(event.getEntity().getKiller()) + 1;
                        Sagiri.getIns().getKills().put(event.getEntity().getKiller(), killerKills);
                    }
                    Sagiri.getIns().getAlivePlayer().remove(relog.getPlayerUUID());
                    Bukkit.broadcastMessage(replaceMessage("&b" + profile.getName() + "&7[&c" + deatherKills + "&7]" + "&e was slain by &b" + event.getEntity().getKiller().getName() + "&7[&c" + killerKills + "&7]"));
                    if (Sagiri.getIns().getAlivePlayer().size() == 1) {
                        WinnerManager.setWinner();
                    }
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onFire(EntityCombustEvent event) {
        event.setCancelled(true);
    }
}
