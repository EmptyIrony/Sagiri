package strafe.games.sagiri.listener;

import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.enums.Status;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PreGameListener implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (Sagiri.getIns().getStatus() == Status.PVP || Sagiri.getIns().getStatus() == Status.PVE) {
            return;
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (Sagiri.getIns().getStatus() == Status.WAITING || Sagiri.getIns().getStatus() == Status.READY) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (Sagiri.getIns().getStatus() == Status.WAITING || Sagiri.getIns().getStatus() == Status.READY) {
            event.setCancelled(true);
        }
    }
}
