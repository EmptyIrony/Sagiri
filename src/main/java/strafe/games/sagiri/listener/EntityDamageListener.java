package strafe.games.sagiri.listener;

import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.enums.Status;
import strafe.games.sagiri.utils.StringUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (Sagiri.getIns().getStatus() == Status.PVP) {
            return;
        } else {
            if (event.getDamager() instanceof Player) {
                if (Sagiri.getIns().getStatus() == Status.PVE) {
                    Player player = (Player) event.getDamager();
                    player.sendMessage(StringUtil.replaceMessage("&cPVP is not enable,you can't attack other player"));
                    event.setCancelled(true);
                } else if (Sagiri.getIns().getStatus() == Status.DEATHMATCH && !Sagiri.getIns().isDeathMatch()) {
                    event.setCancelled(true);
                }

            }
        }
    }
}
