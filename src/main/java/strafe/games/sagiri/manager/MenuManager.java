package strafe.games.sagiri.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import strafe.games.miku.profile.Profile;
import strafe.games.miku.util.ItemBuilder;
import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.player.Relog;

import java.util.UUID;

import static strafe.games.sagiri.utils.StringUtil.replaceMessage;

public class MenuManager {
    public static Inventory getPlayerSelectorMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, replaceMessage("&cPlayer Selector"));
        for (UUID uuid : Sagiri.getIns().getAlivePlayer()) {
            Player p = Profile.getByUuid(uuid).getPlayer();
            if (p == null) {
                for (Relog relog : Sagiri.getIns().getRelogs()) {
                    if (relog.getPlayerUUID().equals(uuid)) {
                        inv.addItem(new ItemBuilder(Material.SKULL_ITEM)
                                .name(Profile.getByUuid(uuid).getName())
                                .build());
                    }
                }

            } else {
                inv.addItem(new ItemBuilder(Material.SKULL_ITEM)
                        .name(p.getName())
                        .build());
            }
        }
        return inv;
    }
}
