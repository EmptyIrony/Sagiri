package strafe.games.sagiri.holographic;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import strafe.games.miku.profile.Profile;
import strafe.games.sagiri.Sagiri;

import static strafe.games.sagiri.utils.StringUtil.replaceMessage;

public class SpawnHologram extends BukkitRunnable {
    public void run() {
        Hologram winsHologram = HologramsAPI.createHologram(Sagiri.getIns(),new Location(Bukkit.getWorld("Lobby"), 2.5, 67, 25));
        winsHologram.appendTextLine( replaceMessage("&7&m-----&6Wins Top&7&m-----"));
        for (int i = 0; i < 5; i++) {
            try {
                String name = Sagiri.getIns().getLeaderBoard().getWinsLeaderboard().get(i).getName();
                Profile userProfile = Profile.getByUsername(name);
                winsHologram.appendTextLine(replaceMessage("&6#" + (i + 1) + userProfile.getColoredUsername() + " - &e" + Sagiri.getIns().getLeaderBoard().getWinsLeaderboard().get(i).getValve() + "Wins"));
            } catch (Exception e) {
                winsHologram.appendTextLine(replaceMessage("&6#" + (i + 1) + "none" + " - &e" + "0" + "Wins"));
            }
        }

        winsHologram.appendItemLine(new ItemStack(Material.DIAMOND_BLOCK));
        Hologram killsHologram = HologramsAPI.createHologram(Sagiri.getIns(),new Location(Bukkit.getWorld("Lobby"), 13, 67, 19));
        killsHologram.appendTextLine(replaceMessage("&7&m-----&6Kills Top&7&m-----"));
        for (int i = 0; i < 5; i++) {
            try {
                String name = Sagiri.getIns().getLeaderBoard().getKillsLeaderboard().get(i).getName();
                Profile userProfile = Profile.getByUsername(name);
                killsHologram.appendTextLine(replaceMessage("&6#" + (i + 1) + " &f" + userProfile.getColoredUsername() + " - &e" + Sagiri.getIns().getLeaderBoard().getKillsLeaderboard().get(i).getValve() + "Kills"));
            } catch (Exception e) {
                killsHologram.appendTextLine(replaceMessage("&6#" + (i + 1) + "none" + " - &e" + "0" + "Kills"));
            }
        }
        killsHologram.appendItemLine(new ItemStack(Material.DIAMOND_SWORD));

        Hologram playedHologram = HologramsAPI.createHologram(Sagiri.getIns(),new Location(Bukkit.getWorld("Lobby"), -9, 67, 20));
        playedHologram.appendTextLine(replaceMessage("&7&m-----&6Played Top&7&m-----"));
        for (int i = 0; i < 5; i++) {
            try {
                String name = Sagiri.getIns().getLeaderBoard().getPlayedLeaderboard().get(i).getName();
                Profile userProfile = Profile.getByUsername(name);
                playedHologram.appendTextLine(replaceMessage("&6#" + (i + 1) + " &f" + userProfile.getColoredUsername() + " - &e" + Sagiri.getIns().getLeaderBoard().getPlayedLeaderboard().get(i).getValve() + "scenes"));
            } catch (Exception e) {
                playedHologram.appendTextLine(replaceMessage("&6#" + (i + 1) + "none" + " - &e" + "0" + "scenes"));
            }
        }

        playedHologram.appendItemLine(new ItemStack(Material.DIAMOND_PICKAXE));

    }
}
