package strafe.games.sagiri.commands;

import strafe.games.sagiri.Sagiri;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import strafe.games.sagiri.utils.StringUtil;

import java.io.File;
import java.util.ArrayList;

public class SaveChestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("StrafeKits.admin")) {
                ArrayList<ItemStack> items = new ArrayList();
                for (int i = 0; i < 36; i++) {
                    items.add(player.getInventory().getItem(i));
                }
                if (Sagiri.getIns().getKitConfig().getConfigurationSection("kit.") == null) {
                    Bukkit.broadcastMessage(StringUtil.replaceMessage("&aFirst save kit,initialization complete!"));
                    Sagiri.getIns().getKitConfig().set("kit.1", items);
                } else {
                    Sagiri.getIns().getKitConfig().set("kit." + (Sagiri.getIns().getKitConfig().getConfigurationSection("kit.").getKeys(false).size() + 1), items);
                }
                try {
                    Sagiri.getIns().getKitConfig().save(new File(Sagiri.getIns().getDataFolder(), "kitConfig.yml"));
                    player.sendMessage(StringUtil.replaceMessage("&aSave successfully"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                player.sendMessage(StringUtil.replaceMessage("&cYou dont have permission to do this"));
            }


        }
        return true;
    }
}
