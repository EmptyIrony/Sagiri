package strafe.games.sagiri.scoreboard;

import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import strafe.games.miku.profile.Profile;
import strafe.games.miku.util.CC;
import strafe.games.miku.util.tab.ZigguratAdapter;
import strafe.games.miku.util.tab.utils.BufferedTabObject;
import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.manager.PlayerManager;
import strafe.games.sagiri.player.PlayerData;
import strafe.games.sagiri.task.PreGameTask;

import java.util.HashSet;
import java.util.Set;

public class CustomTabImpl implements ZigguratAdapter {
    @Override
    public Set<BufferedTabObject> getSlots(Player player) {
        Set<BufferedTabObject> sets = Sets.newHashSet();
        switch (Sagiri.getIns().getStatus()) {
            case WAITING:
                return getWaitingTab(player);
            case READY:
                return getReadyTab(player);
            case PVE:
            case PVP:
            case DEATHMATCH:
                return getGamingTab(player);
            case END:
                return getEndTab(player);

        }
        return sets;

    }

    private Set<BufferedTabObject> getWaitingTab(Player player) {
        Set<BufferedTabObject> tabObjects = new HashSet<>();
        tabObjects.add(wrapObject(2, "&fOnline: " + Bukkit.getOnlinePlayers().size()));
        tabObjects.add(wrapObject(22, player.getPing(), "&fYour Connection"));
        tabObjects.add(wrapObject(42, "&fNeed Players: " + (Sagiri.getIns().getConfig().getInt("MinPlayers") - Bukkit.getOnlinePlayers().size())));
        tabObjects.add(wrapObject(24, "&6&lYour Statistics:"));
        PlayerData playerData = PlayerManager.playerData.get(player.getUniqueId());
        tabObjects.add(wrapObject(5, "&fKills: " + playerData.getKills()));
        tabObjects.add(wrapObject(25, "&fPlayed: " + playerData.getPlayed()));
        tabObjects.add(wrapObject(45, "&fWins: " + playerData.getWins()));
        addBasics(tabObjects);
        return tabObjects;
    }

    private Set<BufferedTabObject> getReadyTab(Player player) {
        Set<BufferedTabObject> tabObjects = new HashSet<>();
        tabObjects.add(wrapObject(2, "&fOnline: " + Bukkit.getOnlinePlayers().size()));
        tabObjects.add(wrapObject(22, player.getPing(), "&fYour Connection"));
        tabObjects.add(wrapObject(42, "&fStart In: " + PreGameTask.timer));
        tabObjects.add(wrapObject(24, "&6&lYour Statistics:"));
        PlayerData playerData = PlayerManager.playerData.get(player.getUniqueId());
        tabObjects.add(wrapObject(5, "&fKills: " + playerData.getKills()));
        tabObjects.add(wrapObject(25, "&fPlayed: " + playerData.getPlayed()));
        tabObjects.add(wrapObject(45, "&fWins: " + playerData.getWins()));

        addBasics(tabObjects);
        return tabObjects;
    }

    private Set<BufferedTabObject> getGamingTab(Player player) {
        Set<BufferedTabObject> tabObjects = new HashSet<>();
        tabObjects.add(wrapObject(2, "&fAlive Players: " + Sagiri.getIns().getAlivePlayer().size()));
        tabObjects.add(wrapObject(22, player.getPing(), "&fYour Connection"));
        tabObjects.add(wrapObject(42, "&fSpectators: " + Sagiri.getIns().getSpectators().size()));
        tabObjects.add(wrapObject(24, "&6&lYour Statistics:"));
        PlayerData playerData = PlayerManager.playerData.get(player.getUniqueId());
        tabObjects.add(wrapObject(5, "&fKills: " + playerData.getKills()));
        tabObjects.add(wrapObject(25, "&fPlayed: " + playerData.getPlayed()));
        tabObjects.add(wrapObject(45, "&fWins: " + playerData.getWins()));
        addBasics(tabObjects);
        return tabObjects;
    }

    public Set<BufferedTabObject> getEndTab(Player player) {
        Set<BufferedTabObject> tabObjects = new HashSet<>();
        tabObjects.add(wrapObject(2, "&fWinner: " + Profile.getByUuid(Sagiri.getIns().getAlivePlayer().get(0)).getName()));
        tabObjects.add(wrapObject(22, player.getPing(), "&fYour Connection"));
        tabObjects.add(wrapObject(2, "&fWinner: " + Profile.getByUuid(Sagiri.getIns().getAlivePlayer().get(0)).getName()));
        tabObjects.add(wrapObject(24, "&6&lYour Statistics:"));
        PlayerData playerData = PlayerManager.playerData.get(player.getUniqueId());
        tabObjects.add(wrapObject(5, "&fKills: " + playerData.getKills()));
        tabObjects.add(wrapObject(25, "&fPlayed: " + playerData.getPlayed()));
        tabObjects.add(wrapObject(45, "&fWins: " + playerData.getWins()));
        addBasics(tabObjects);
        return tabObjects;
    }

    private BufferedTabObject wrapObject(int slot, String text) {
        return wrapObject(slot, 1, text);
    }

    private BufferedTabObject wrapObject(int slot, int ping, String text) {
        return new BufferedTabObject().slot(slot).ping(ping).text(CC.translate(text));
    }

    private void addBasics(Set<BufferedTabObject> bufferedTabObjects) {
        bufferedTabObjects.add(wrapObject(1, "&6&lStrafeKits.Games"));
        bufferedTabObjects.add(wrapObject(21, "&6&lStrafeSG"));
        bufferedTabObjects.add(wrapObject(41, "&6&lStrafeKits.Games"));

        bufferedTabObjects.add(wrapObject(67, "&eWarning!"));
        bufferedTabObjects.add(wrapObject(68, "&eWe recommend"));
        bufferedTabObjects.add(wrapObject(69, "&eto use 1.7.10"));
        bufferedTabObjects.add(wrapObject(70, "&eor Lunar Client"));
        bufferedTabObjects.add(wrapObject(71, "&efor better"));
        bufferedTabObjects.add(wrapObject(72, "&egaming experience"));
        bufferedTabObjects.add(wrapObject(73, "&eand more fluid pvp"));
    }

    @Override
    public String getFooter() {
        return null;
    }

    @Override
    public String getHeader() {
        return null;
    }
}
