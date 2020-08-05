package strafe.games.sagiri.commands;

import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.utils.StringUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * 2 * @Author: EmptyIrony
 * 3 * @Date: 2019/12/28 21:23
 * 4
 */
@CommandMeta(label = "saveextra",permission = "core.admin")
public class SaveExtraCommand {

    public void execute(Player player){
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
            Sagiri.getIns().getKitConfig().save(new File(Sagiri.getIns().getDataFolder(), "extraKit.yml"));
            player.sendMessage(StringUtil.replaceMessage("&aSave successfully"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
