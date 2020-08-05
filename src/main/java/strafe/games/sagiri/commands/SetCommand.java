package strafe.games.sagiri.commands;

import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.utils.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("StrafeKits.admin")) {
                if (strings.length == 0) {
                    player.sendMessage(StringUtil.replaceMessage("&8&m---------------"));
                    player.sendMessage(StringUtil.replaceMessage("&b/set spawn"));
                    player.sendMessage(StringUtil.replaceMessage("&b/set gamespawn"));
                    player.sendMessage(StringUtil.replaceMessage("&b/set chest"));
                    player.sendMessage(StringUtil.replaceMessage("&8&m---------------"));
                } else if (strings.length == 1) {
                    if (strings[0].equalsIgnoreCase("spawn")) {
                        Sagiri.getIns().getConfig().set("SpawnPoint.x", player.getLocation().getBlockX());
                        Sagiri.getIns().getConfig().set("SpawnPoint.y", player.getLocation().getBlockY());
                        Sagiri.getIns().getConfig().set("SpawnPoint.z", player.getLocation().getBlockZ());
                        Sagiri.getIns().getConfig().set("SpawnPoint.yaw", player.getLocation().getYaw());
                        Sagiri.getIns().getConfig().set("SpawnPoint.pitch", player.getLocation().getPitch());
                        Sagiri.getIns().getConfig().set("SpawnPoint.world", player.getLocation().getWorld().getName());
                        player.sendMessage(StringUtil.replaceMessage("&aSuccessfully"));
                    } else if (strings[0].equalsIgnoreCase("gamespawn")) {
                        Sagiri.getIns().getConfig().set("ReadyPoint.x", player.getLocation().getBlockX());
                        Sagiri.getIns().getConfig().set("ReadyPoint.y", player.getLocation().getBlockY());
                        Sagiri.getIns().getConfig().set("ReadyPoint.z", player.getLocation().getBlockZ());
                        Sagiri.getIns().getConfig().set("ReadyPoint.yaw", player.getLocation().getYaw());
                        Sagiri.getIns().getConfig().set("ReadyPoint.pitch", player.getLocation().getPitch());
                        Sagiri.getIns().getConfig().set("ReadyPoint.world", player.getLocation().getWorld().getName());
                        player.sendMessage(StringUtil.replaceMessage("&aSuccessfully"));
                    }
                }
            }
        }
        return true;
    }
}
