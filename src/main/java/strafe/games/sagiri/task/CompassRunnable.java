package strafe.games.sagiri.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import strafe.games.miku.profile.Profile;
import strafe.games.sagiri.Sagiri;

import java.util.List;
import java.util.UUID;

public class CompassRunnable extends BukkitRunnable {
    private Player player;
    private Player target = null;

    public CompassRunnable(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        List<UUID> list = Sagiri.getIns().getAlivePlayer();
        list.remove(player.getUniqueId());
        for (UUID uuid : list) {
            if (Bukkit.getPlayer(uuid) == null) {
                return;
            }
            Player p = Profile.getByUuid(uuid).getPlayer();
            if (target == null) {
                target = p;
            } else {
                if (p.getLocation().distance(player.getLocation()) < target.getLocation().distance(player.getLocation())) {
                    target = p;
                }
            }
        }
        player.setCompassTarget(target.getLocation());
    }
}
