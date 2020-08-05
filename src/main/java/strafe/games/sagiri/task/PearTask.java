package strafe.games.sagiri.task;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import strafe.games.sagiri.Sagiri;

public class PearTask extends BukkitRunnable {
    @Getter
    private double timer;
    private Player player;

    public PearTask(Player player) {
        this.timer = 16.0D;
        this.player = player;
        Sagiri.getIns().getCooldownTimer().put(player, 16.0D);
    }

    @Override
    public void run() {
        if (timer <= 0.0) {
            Sagiri.getIns().getIsCooldown().put(player, false);
            cancel();
        }
        this.timer = timer - 0.1D;
        Sagiri.getIns().getCooldownTimer().put(player, Double.valueOf(String.format("%.2f", timer)));
    }
}
