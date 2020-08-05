package strafe.games.sagiri.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.material.EnderChest;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 2 * @Author: EmptyIrony
 * 3 * @Date: 2019/12/28 21:25
 * 4
 */
public class ExtraChestTask extends BukkitRunnable {
    public static int time = 300;

    @Override
    public void run() {
        time--;

        if (time==0){
            World world = Bukkit.getWorld("world");
            Location l1 = new Location(world,0,64,15);
            Location l2 = new Location(world,0 ,64,-15);
            Location l3 = new Location(world,15,64,0);
            Location l4 = new Location(world,-15,64,0);

            l1.getBlock().setType(Material.ENDER_CHEST);
            l2.getBlock().setType(Material.ENDER_CHEST);
            l3.getBlock().setType(Material.ENDER_CHEST);
            l4.getBlock().setType(Material.ENDER_CHEST);


        }
    }
}
