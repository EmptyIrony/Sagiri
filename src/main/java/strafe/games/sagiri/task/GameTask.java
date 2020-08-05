package strafe.games.sagiri.task;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.enums.Status;
import strafe.games.sagiri.utils.StringUtil;

import java.util.Random;

public class GameTask extends BukkitRunnable {
    public static int timer = 0;
    Random random = new Random();

    @Override
    public void run() {
        if (timer == 120 || timer == 150 || timer == 170 || timer >= 175 && timer < 180) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.CLICK, 2, 1);
            }
            Bukkit.broadcastMessage(StringUtil.replaceMessage("&ePVP will enable in &b" + (180 - timer) + "&e seconds!"));
        }
        if (timer == 180) {
            Bukkit.broadcastMessage(StringUtil.replaceMessage("&ePVP has been enabled,GOOD LUCK!!"));
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 2, 1);
            }
            Sagiri.getIns().setStatus(Status.PVP);
        }
        if (Sagiri.getIns().getStatus() == Status.END) {
            cancel();
        }
        timer++;
    }

}
