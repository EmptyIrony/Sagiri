package strafe.games.sagiri.task;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.enums.Status;
import strafe.games.sagiri.manager.PlayerManager;
import strafe.games.sagiri.player.PlayerData;
import strafe.games.sagiri.utils.StringUtil;
import strafe.games.miku.util.ItemBuilder;

public class PreGameTask extends BukkitRunnable {
    public static int timer = 180;

    public PreGameTask() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setAllowFlight(true);
        }
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setExp(timer * 1000F / 180000F);
            player.setLevel(timer);
        }
        if (timer == 30
                || timer == 10
                || timer == 5
                || timer == 4
                || timer == 3
                || timer == 2) {
            Bukkit.broadcastMessage(StringUtil.replaceMessage("&eThe game will start in &b" + timer + "&e seconds"));
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.CLICK, 2, 1);
            }
        }
        if (timer == 1) {
            Bukkit.broadcastMessage(StringUtil.replaceMessage("&eThe game will start in &b" + timer + "&e second"));
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.CLICK, 2, 2);
            }
            Sagiri.getIns().setBorderSize(500);
            Sagiri.getIns().setStatus(Status.PVE);
        }
        if (timer == 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.teleport(new Location(Bukkit.getWorld(Sagiri.getIns().getConfig().getString("ReadyPoint.world")), Sagiri.getIns().getConfig().getInt("ReadyPoint.x"), Sagiri.getIns().getConfig().getInt("ReadyPoint.y"), Sagiri.getIns().getConfig().getInt("ReadyPoint.z"), Sagiri.getIns().getConfig().getInt("ReadyPoint.yaw"), Sagiri.getIns().getConfig().getInt("ReadyPoint.pitch")));
                Sagiri.getIns().getAlivePlayer().add(player.getUniqueId());
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*60, 2));
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 3));
                player.setFoodLevel(20);
                player.setSaturation(12.8F);
                player.setHealth(20.0D);
                player.setLevel(0);
                player.setExp(0);
                player.getInventory().clear();
                player.getInventory().setArmorContents(null);
                player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 9));
                player.getInventory().addItem(new ItemBuilder(Material.COMPASS)
                        .name(StringUtil.replaceMessage("&cTracer"))
                        .build());
                player.updateInventory();
                player.setGameMode(GameMode.SURVIVAL);
                player.setAllowFlight(false);
                Sagiri.getIns().getPlayedPlayer().add(player);
                PlayerData data = PlayerManager.playerData.get(player.getUniqueId());
                data.setPlayed(data.getPlayed() + 1);
                PlayerManager.playerData.put(player.getUniqueId(), data);
                //new CompassRunnable(player).runTaskTimer(Sagiri.getIns(), 20L, 20L);
            }
            cancel();
            new GameTask().runTaskTimer(Sagiri.getIns(), 20L, 20L);
            new BorderTask().runTaskTimer(Sagiri.getIns(), 20L, 20L);

        }
        timer--;
    }



}
