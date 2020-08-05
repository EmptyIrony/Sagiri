package strafe.games.sagiri.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import strafe.games.miku.util.ItemBuilder;
import strafe.games.miku.util.LocationUtil;
import strafe.games.sagiri.Sagiri;

import static strafe.games.sagiri.utils.StringUtil.replaceMessage;

public class SpectatorsManager {
    private static Player target;

    public static void setSpec(Player player) {
        target = player;
        Sagiri.getIns().getAlivePlayer().remove(player.getUniqueId());
        Sagiri.getIns().getSpectators().add(player.getUniqueId());
        player.setHealth(20.0D);
        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());
        player.setExp(0);
        player.setLevel(0);
        player.setFoodLevel(20);
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.hidePlayer(player);
        }
        player.setAllowFlight(true);
        player.spigot().setCollidesWithEntities(false);
        new BukkitRunnable() {
            @Override
            public void run() {
                target.getInventory().setItem(0, new ItemBuilder(Material.ITEM_FRAME)
                        .name(replaceMessage("&cPlayer Selector"))
                        .build());
                target.getInventory().setItem(4, new ItemBuilder(Material.DIAMOND_SWORD)
                        .name(replaceMessage("&bTeleport to 00"))
                        .build());
            }
        }.runTaskLater(Sagiri.getIns(), 60L);


    }
}
