package strafe.games.sagiri.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * 2 * @Author: EmptyIrony
 * 3 * @Date: 2019/12/28 21:13
 * 4
 */
public class GameListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if (event.getBlock().getType()== Material.GRASS||event.getBlock().getType()==Material.LONG_GRASS){
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
        }
    }
}
