package strafe.games.sagiri.task;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import strafe.games.miku.profile.Profile;
import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.manager.WinnerManager;
import strafe.games.sagiri.player.Relog;

import java.util.List;
import java.util.UUID;

import static strafe.games.sagiri.utils.StringUtil.replaceMessage;

@AllArgsConstructor
public class LogoutTask extends BukkitRunnable {
    private Sagiri sagiri;

    @Override
    public void run() {
        List<Relog> relogs = sagiri.getRelogs();
        for (Relog relog : relogs) {
            if (relog.getCooldown().hasExpired()){
                Profile profile = Profile.getByUuid(relog.getPlayerUUID());
                sagiri.getRelogs().remove(relog);
                sagiri.getAlivePlayer().remove(relog.getPlayerUUID());
                for (Entity entity:Bukkit.getWorld("world").getEntities()){
                    if (entity.getUniqueId().equals(relog.getZombieUUID())){
                        entity.remove();
                        break;
                    }
                }
                Bukkit.broadcastMessage(replaceMessage("&b"+profile.getName()+"&e dead"));
                if (sagiri.getAlivePlayer().size() == 1) {
                    WinnerManager.setWinner();
                }
            }
        }
    }
}
