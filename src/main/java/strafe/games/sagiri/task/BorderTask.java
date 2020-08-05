package strafe.games.sagiri.task;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import strafe.games.miku.profile.Profile;
import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.enums.Status;
import strafe.games.sagiri.utils.StringUtil;
import strafe.games.sagiri.utils.border.BorderUtil;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public class BorderTask extends BukkitRunnable {
    public static int shrinkTimer = 480;
    private Random random = new SecureRandom();

    @Override
    public void run() {
        if (Sagiri.getIns().getStatus() == Status.END) {
            cancel();
        }
        if (shrinkTimer == 60 || shrinkTimer == 30 || shrinkTimer == 10 || shrinkTimer <= 5 && shrinkTimer > 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.CLICK, 2, 1);
            }
            int shrinkToBorder = (Sagiri.getIns().getBorderSize() - 50);
            Bukkit.broadcastMessage(StringUtil.replaceMessage("&eBorder will be shrink to &b" + shrinkToBorder + "x" + shrinkToBorder + "&e in &b" + shrinkTimer + "&e seconds"));
        }
        if (shrinkTimer == 0) {
            int shrinkToBorder = (Sagiri.getIns().getBorderSize() - 50);
            Sagiri.getIns().setBorderSize(shrinkToBorder);
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getLocation().getBlockX() < -1 * shrinkToBorder
                        || player.getLocation().getBlockZ() < -1 * shrinkToBorder
                        || player.getLocation().getBlockX() > shrinkToBorder
                        || player.getLocation().getBlockZ() > shrinkToBorder) {
                    int x;
                    int z;
                    if (random.nextInt(1) == 1) {
                        x = random.nextInt(shrinkToBorder);
                        z = random.nextInt(shrinkToBorder);
                    } else {
                        x = -(random.nextInt(shrinkToBorder));
                        z = -(random.nextInt(shrinkToBorder));
                    }

                    int y = Bukkit.getWorld("world").getHighestBlockYAt(x, z);
                    player.teleport(new Location(Bukkit.getWorld("world"), x, y, z, player.getLocation().getYaw(), player.getLocation().getPitch()));
                }
            }
            BorderUtil.buildWalls(shrinkToBorder, Material.BEDROCK, 15, Bukkit.getWorld("world"));
            Bukkit.broadcastMessage(StringUtil.replaceMessage("&eBorder has been shrank at &b" + Sagiri.getIns().getBorderSize() + "&ex&b" + Sagiri.getIns().getBorderSize()));
            if (Sagiri.getIns().getBorderSize() <= 100) {
                Sagiri.getIns().setStatus(Status.DEATHMATCH);
                for (UUID uuid : Sagiri.getIns().getAlivePlayer()) {
                    Player player = Profile.getByUuid(uuid).getPlayer();
                    player.teleport(getDeathMatchRandom());
                }
                BorderUtil.buildWalls(40, Material.BEDROCK, 15, Bukkit.getWorld("world"));
                Sagiri.getIns().setBorderSize(40);
                new PreDeathMatchTask().runTaskTimer(Sagiri.getIns(), 20L, 20L);
                cancel();
            } else {
                shrinkTimer = 120;
            }
        }
        shrinkTimer--;
    }

    public Location getDeathMatchRandom() {
        int x = this.random.nextInt(50) - 25;
        int z = this.random.nextInt(50) - 25;
        World world = Bukkit.getWorld("world");
        int y = world.getHighestBlockYAt(x, z);
        return new Location(world, x, y, z);
    }
}
