package strafe.games.sagiri.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.task.PearTask;
import strafe.games.sagiri.utils.StringUtil;

public class EnderPearlListener implements Listener {
    @EventHandler
    public void onRight(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (event.getItem() != null) {
                if (event.getItem().getType() == Material.ENDER_PEARL) {
                    if (Sagiri.getIns().getIsCooldown().get(event.getPlayer()) != null && Sagiri.getIns().getIsCooldown().get(event.getPlayer())) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(StringUtil.replaceMessage("&cEnderPearl is cooldown!"));
                    } else {
                        Sagiri.getIns().getIsCooldown().put(event.getPlayer(), true);

                        new PearTask(event.getPlayer()).runTaskTimerAsynchronously(Sagiri.getIns(), 20, 2);
                    }
                }
            }
        }
    }
}
