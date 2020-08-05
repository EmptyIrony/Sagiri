package strafe.games.sagiri.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import strafe.games.miku.profile.Profile;
import strafe.games.miku.util.board.assemble.AssembleAdapter;
import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.task.BorderTask;
import strafe.games.sagiri.task.GameTask;
import strafe.games.sagiri.task.PreGameTask;
import strafe.games.sagiri.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import static strafe.games.sagiri.utils.StringUtil.replaceMessage;

public class CustomScoreboardImpl implements AssembleAdapter {
    @Override
    public String getTitle(Player player) {
        return replaceMessage("&6&lStrafeKits&7&l丨&fStrafeSG");
    }

    @Override
    public List<String> getLines(Player player) {
        switch (Sagiri.getIns().getStatus()) {
            case READY:
                List<String> ready = new ArrayList<>();
                ready.add(replaceMessage("&7&m---------------------"));
                ready.add(replaceMessage("&6Start in:"));
                ready.add(replaceMessage("&f" + PreGameTask.timer));
                ready.add(replaceMessage("&7&m---------------------"));
                return ready;
            case WAITING:
                List<String> waiting = new ArrayList<>();
                waiting.add(replaceMessage("&7&m---------------------"));
                waiting.add(replaceMessage("&6Need players to start:"));
                waiting.add(replaceMessage("&f" + (Sagiri.getIns().getConfig().getInt("MinPlayers") - Bukkit.getOnlinePlayers().size())));
                waiting.add(replaceMessage("&7&m---------------------"));
                return waiting;
            case PVE:
                List<String> pve = new ArrayList<>();
                pve.add(replaceMessage("&7&m---------------------"));
                pve.add(replaceMessage("&fBorder:&6 " + Sagiri.getIns().getBorderSize()));
                pve.add(replaceMessage("&fPVP Protection:&6 " + StringUtil.secondsToString((180 - GameTask.timer))));
                pve.add(replaceMessage("&fPlayers:&6 " + Sagiri.getIns().getAlivePlayer().size()));
                int kills;
                if (Sagiri.getIns().getKills().get(player) == null) {
                    kills = 0;
                } else {
                    kills = Sagiri.getIns().getKills().get(player);
                }
                pve.add(replaceMessage("&fKills:&6 " + kills));
                if (Sagiri.getIns().getIsCooldown().get(player) != null && Sagiri.getIns().getIsCooldown().get(player)) {
                    pve.add(replaceMessage("&fEnder Pearl: &6" + Sagiri.getIns().getCooldownTimer().get(player)));
                }
                pve.add("");
                pve.add("&fYour ping:&6 " + player.getPing() + "ms");
                pve.add(replaceMessage("&7&m---------------------"));
                return pve;
            case PVP:
                List<String> pvp = new ArrayList<>();
                pvp.add(replaceMessage("&7&m---------------------"));
                if (BorderTask.shrinkTimer == -1) {
                    pvp.add(replaceMessage("&fBorder:&6 " + Sagiri.getIns().getBorderSize()));
                } else {
                    pvp.add(replaceMessage("&fBorder:&6 " + Sagiri.getIns().getBorderSize() + "&7(&6" + BorderTask.shrinkTimer + "&7)"));
                }
                pvp.add(replaceMessage("&fPlayers:&6 " + Sagiri.getIns().getAlivePlayer().size()));
                int pvpkills;
                if (Sagiri.getIns().getKills().get(player) == null) {
                    pvpkills = 0;
                } else {
                    pvpkills = Sagiri.getIns().getKills().get(player);
                }
                pvp.add(replaceMessage("&fKills:&6 " + pvpkills));
                if (Sagiri.getIns().getIsCooldown().get(player) != null && Sagiri.getIns().getIsCooldown().get(player)) {
                    pvp.add(replaceMessage("&fEnder Pearl: &6" + Sagiri.getIns().getCooldownTimer().get(player)));
                }
                pvp.add("");
                pvp.add(replaceMessage("&fYour ping:&6 " + player.getPing() + "ms"));
                pvp.add(replaceMessage("&7&m---------------------"));
                return pvp;
            case DEATHMATCH:
                List<String> dm = new ArrayList<>();
                dm.add(replaceMessage("&7&m---------------------"));
                dm.add(replaceMessage("&c&lDeathMatch"));
                dm.add(replaceMessage("&fPlayers: &6" + Sagiri.getIns().getAlivePlayer().size()));
                int dmkills;
                if (Sagiri.getIns().getKills().get(player) == null) {
                    dmkills = 0;
                } else {
                    dmkills = Sagiri.getIns().getKills().get(player);
                }
                dm.add(replaceMessage("&fKills: &6" + dmkills));
                if (Sagiri.getIns().getIsCooldown().get(player) != null && Sagiri.getIns().getIsCooldown().get(player)) {
                    dm.add(replaceMessage("&fEnder Pearl: &6" + Sagiri.getIns().getCooldownTimer().get(player)));
                }
                dm.add("&fYour ping:&6 " + player.getPing() + "ms");
                dm.add(replaceMessage("&7&m---------------------"));
                return dm;
            case END:
                List<String> end = new ArrayList<>();
                end.add(replaceMessage("&7&m---------------------"));
                end.add(replaceMessage("&6Winner:"));
                end.add(replaceMessage("&f        " + Profile.getByUuid(Sagiri.getIns().getAlivePlayer().get(0)).getName()));
                end.add(replaceMessage("&7&m---------------------"));
                return end;
        }
        return null;
    }
}
