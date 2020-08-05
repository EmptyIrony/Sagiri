package strafe.games.sagiri.task;

import strafe.games.sagiri.Sagiri;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import strafe.games.sagiri.utils.StringUtil;

public class PreDeathMatchTask extends BukkitRunnable {
    int preDeathMatchTimer;

    public PreDeathMatchTask() {
        preDeathMatchTimer = 10;
        Sagiri.getIns().setDeathMatch(false);
    }

    @Override
    public void run() {
        if (preDeathMatchTimer == 10) {
            Bukkit.broadcastMessage(StringUtil.replaceMessage("&eDeathMatch enable in &b" + preDeathMatchTimer + "&e seconds"));
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.CLICK, 2, 1);
            }
        }
        if (preDeathMatchTimer == 5 || preDeathMatchTimer == 4 || preDeathMatchTimer == 3 || preDeathMatchTimer == 2) {
            Bukkit.broadcastMessage(StringUtil.replaceMessage("&eDeathMatch enable in &b" + preDeathMatchTimer + "&e seconds"));
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.CLICK, 2, 1);
            }
        }
        if (preDeathMatchTimer == 1) {
            Sagiri.getIns().setDeathMatch(true);
            Bukkit.broadcastMessage(StringUtil.replaceMessage("&eDeathMatch was enable!"));
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 2, 1);
            }
        }
        preDeathMatchTimer--;
    }
}
