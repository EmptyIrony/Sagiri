package strafe.games.sagiri.player;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import strafe.games.miku.profile.Profile;
import strafe.games.miku.profile.option.menu.ProfileOptionsMenu;

import java.util.UUID;

@RequiredArgsConstructor
@Data
public class PlayerData {
    private int wins = 0, kills = 0, played = 0;

    private final UUID uuid;

    private final String name;
}
