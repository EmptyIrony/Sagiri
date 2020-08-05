package strafe.games.sagiri.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import strafe.games.sagiri.manager.MongoManager;

import static strafe.games.sagiri.utils.StringUtil.replaceMessage;

public class ResetDatabase implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("strafekits.admin")) {
            MongoManager.getInstance().getUuid().drop();
            sender.sendMessage(replaceMessage("&a删库成功，你可以跑路了"));
        } else {
            sender.sendMessage(replaceMessage("&c你没有权限杀死数据库"));
        }
        return true;
    }
}
