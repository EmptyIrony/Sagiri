package strafe.games.sagiri.commands;

import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.enums.Status;
import strafe.games.sagiri.listener.PlayerJoinListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ForceStart implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (Sagiri.getIns().getStatus() == Status.WAITING) {
            if (commandSender.hasPermission("StrafeKits.admin")) {
                new PlayerJoinListener().readyToStart();
            }
        }
        return true;
    }
}
