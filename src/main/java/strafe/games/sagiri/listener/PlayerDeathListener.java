package strafe.games.sagiri.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.enums.Status;
import strafe.games.sagiri.manager.PlayerManager;
import strafe.games.sagiri.manager.SpectatorsManager;
import strafe.games.sagiri.manager.WinnerManager;
import strafe.games.sagiri.player.PlayerData;
import strafe.games.sagiri.utils.StringUtil;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (Sagiri.getIns().getStatus() == Status.PVP || Sagiri.getIns().getStatus() == Status.PVE || Sagiri.getIns().getStatus() == Status.DEATHMATCH) {
            event.getEntity().getLocation().getWorld().strikeLightningEffect(event.getEntity().getLocation());
            int deatherKills;
            if (Sagiri.getIns().getKills().get(event.getEntity().getPlayer()) == null) {
                deatherKills = 0;
            } else {
                deatherKills = Sagiri.getIns().getKills().get(event.getEntity().getPlayer());
            }
            if (event.getEntity().getKiller() != null) {
                Player killer = event.getEntity().getKiller();
                int killerKills;
                PlayerData playerData = PlayerManager.playerData.get(killer.getUniqueId());
                playerData.setKills(playerData.getKills() + 1);
                PlayerManager.playerData.put(killer.getUniqueId(), playerData);
                if (Sagiri.getIns().getKills().get(killer) == null) {
                    killerKills = 1;
                    Sagiri.getIns().getKills().put(killer, 1);
                } else {
                    Sagiri.getIns().getKills().put(killer, Sagiri.getIns().getKills().get(killer) + 1);
                    killerKills = Sagiri.getIns().getKills().get(killer);
                }
                for (ItemStack item : event.getEntity().getInventory().getContents()) {
                    if (item == null || item.getType() == Material.AIR) {
                        continue;
                    }
                    event.getEntity().getLocation().getWorld().dropItemNaturally(event.getEntity().getLocation(), item);
                }
                event.getDrops().clear();

                event.setDeathMessage(StringUtil.replaceMessage("&b" + event.getEntity().getPlayer().getName() + "&7[&c" + deatherKills + "&7]" + "&e was slain by &b" + killer.getName() + "&7[&c" + killerKills + "&7]"));
                SpectatorsManager.setSpec(event.getEntity().getPlayer());
            } else {
                for (ItemStack item : event.getEntity().getInventory().getContents()) {
                    if (item == null || item.getType() == Material.AIR) {
                        continue;
                    }
                    event.getEntity().getLocation().getWorld().dropItemNaturally(event.getEntity().getLocation(), item);
                }
                event.getDrops().clear();
                event.setDeathMessage(StringUtil.replaceMessage("&b" + event.getEntity().getPlayer().getName() + "&7[&c" + deatherKills + "&7]&e death"));
                SpectatorsManager.setSpec(event.getEntity().getPlayer());
            }
            if (Sagiri.getIns().getAlivePlayer().size() == 1) {
                WinnerManager.setWinner();
            }
        }
    }
}
