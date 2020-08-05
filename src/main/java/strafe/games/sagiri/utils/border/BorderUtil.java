package strafe.games.sagiri.utils.border;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;


public class BorderUtil {
    public static void buildWalls(int size, Material mat, int h, World w) {
        Location loc = new Location(w, 0.0, 59.0, 0.0);
        for (int i = h; i < h + h; ++i) {
            for (int x = loc.getBlockX() - size; x <= loc.getBlockX() + size; ++x) {
                for (int y = 58; y <= 58; ++y) {
                    for (int z = loc.getBlockZ() - size; z <= loc.getBlockZ() + size; ++z) {
                        if (x == loc.getBlockX() - size || x == loc.getBlockX() + size || z == loc.getBlockZ() - size
                                || z == loc.getBlockZ() + size) {
                            Location loc2 = new Location(w, x, y, z);
                            loc2.setY(w.getHighestBlockYAt(loc2));
                            loc2.getBlock().setType(mat);
                        }
                    }
                }
            }
        }
    }


}

