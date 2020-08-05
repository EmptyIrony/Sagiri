package strafe.games.sagiri.player;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import strafe.games.miku.util.Cooldown;

import java.util.UUID;

@Getter
public class Relog {
    private UUID playerUUID;
    private UUID zombieUUID;
    private ItemStack[] platerItems;
    private ItemStack[] armors;
    private Cooldown cooldown;

    public Relog(UUID player, UUID zombie, ItemStack[] items, ItemStack[] armors) {
        this.playerUUID = player;
        this.zombieUUID = zombie;
        this.platerItems = items;
        this.armors = armors;
        this.cooldown = new Cooldown(1000*60*5);
    }
}
