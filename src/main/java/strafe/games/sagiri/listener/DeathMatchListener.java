package strafe.games.sagiri.listener;

import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.enums.Status;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class DeathMatchListener implements Listener {
    @EventHandler
    public void place(BlockPlaceEvent event) {
        if (Sagiri.getIns().getStatus() == Status.DEATHMATCH) {
            event.setCancelled(true);
        }
    }
}
