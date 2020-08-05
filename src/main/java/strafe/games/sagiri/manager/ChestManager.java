package strafe.games.sagiri.manager;

import strafe.games.sagiri.Sagiri;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class ChestManager {
    public static List<ItemStack> getRandomKits() {
        Random random = new Random();
        int kitNum = random.nextInt(Sagiri.getIns().getKits().size()) + 1;
        return Sagiri.getIns().getKits().get(kitNum);
    }
}
