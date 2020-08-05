package strafe.games.sagiri.manager;

import org.bukkit.Bukkit;
import strafe.games.miku.profile.Profile;
import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.enums.Status;
import strafe.games.sagiri.player.PlayerData;
import strafe.games.sagiri.task.PreShutdownTask;

import static strafe.games.sagiri.utils.StringUtil.replaceMessage;

public class WinnerManager {
    public static void setWinner() {
        Sagiri.getIns().setStatus(Status.END);
        PlayerData playerData = PlayerManager.playerData.get(Sagiri.getIns().getAlivePlayer().get(0));
        playerData.setWins(playerData.getWins() + 1);
        PlayerManager.playerData.put(Sagiri.getIns().getAlivePlayer().get(0), playerData);
        Bukkit.broadcastMessage(replaceMessage("&7&m----------------"));
        Bukkit.broadcastMessage(replaceMessage("&eWinner is &b" + Profile.getByUuid(Sagiri.getIns().getAlivePlayer().get(0)).getPlayer().getName()));
        Bukkit.broadcastMessage(replaceMessage("&eThanks for playing the game!"));
        Bukkit.broadcastMessage(replaceMessage("&7&m----------------"));
        new PreShutdownTask().runTaskTimer(Sagiri.getIns(), 20L, 20L);
    }
}
