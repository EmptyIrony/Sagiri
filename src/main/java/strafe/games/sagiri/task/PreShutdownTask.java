package strafe.games.sagiri.task;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static strafe.games.sagiri.utils.StringUtil.replaceMessage;

public class PreShutdownTask extends BukkitRunnable {
    int timer;

    public PreShutdownTask() {
        timer = 60;
    }

    @Override
    public void run() {
        if (timer == 10 || timer <= 5&& timer>=0) {
            Bukkit.broadcastMessage(replaceMessage("&eServer will be shutdown in &b" + timer + "&e seconds"));
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.CLICK, 2, 2);
                if (timer <= 0) {
                    Bukkit.shutdown();
                }
            }
        }
        timer--;
    }
}
