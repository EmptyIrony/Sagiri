package strafe.games.sagiri.listener;

import net.minecraft.server.v1_8_R3.TileEntityChest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftChest;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.enums.Status;
import strafe.games.sagiri.manager.ChestManager;

import java.lang.reflect.Field;
import java.util.List;

public class InventoryListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (Sagiri.getIns().getStatus() != Status.READY && Sagiri.getIns().getStatus() != Status.WAITING) {
            if (Sagiri.getIns().getAlivePlayer().contains(event.getPlayer().getUniqueId())) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (event.getClickedBlock().getType() == Material.CHEST) {
                        Chest chest = (Chest) event.getClickedBlock().getState();
                        if (chest.getBlockInventory().getTitle().equalsIgnoreCase("container.chest")) {
                            List<ItemStack> items = ChestManager.getRandomKits();
                            for (int i = 0; i < 27; i++) {
                                if (items.get(i) == null) {
                                    chest.getBlockInventory().addItem(new ItemStack(Material.AIR));
                                    continue;
                                }
                                chest.getBlockInventory().setItem(i, items.get(i));
                            }
                            Sagiri.getIns().getOpenInvLocation().put(event.getPlayer(), chest.getLocation());
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (Sagiri.getIns().getStatus() != Status.READY && Sagiri.getIns().getStatus() != Status.WAITING) {
            if (event.getPlayer() instanceof Player) {
                Player player = (Player) event.getPlayer();
                if (event.getView().getTitle().equalsIgnoreCase("container.chest")) {
                    Location chestLocation = Sagiri.getIns().getOpenInvLocation().get(player);
                    chestLocation.getBlock().setType(Material.AIR);
                    player.playSound(chestLocation, Sound.ZOMBIE_WOODBREAK, 2, 1);
                }
            }
        }
    }

    @EventHandler
    public void onPlaceChest(BlockPlaceEvent event) {
        if (Sagiri.getIns().getStatus() != Status.READY && Sagiri.getIns().getStatus() != Status.WAITING) {
            if (event.getBlock().getType() == Material.CHEST) {
                CraftChest chest = (CraftChest) event.getBlock().getState();
                try {
                    Field inventoryField = chest.getClass().getDeclaredField("chest");
                    inventoryField.setAccessible(true);
                    TileEntityChest teChest = ((TileEntityChest) inventoryField.get(chest));
                    teChest.a("Chest");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        if (Sagiri.getIns().getStatus() == Status.PVE || Sagiri.getIns().getStatus() == Status.PVP) {
            if (Sagiri.getIns().getAlivePlayer().contains(event.getPlayer().getUniqueId())) {
                if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    if (event.getClickedBlock().getType() == Material.CHEST) {
                        Chest chest = (Chest) event.getClickedBlock().getState();
                        if (chest.getBlockInventory().getTitle().equalsIgnoreCase("container.chest")) {
                            List<ItemStack> items = ChestManager.getRandomKits();
                            for (ItemStack is : items) {
                                if (is == null) {
                                    continue;
                                }
                                chest.getLocation().getWorld().dropItemNaturally(chest.getLocation(), is);
                            }
                            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ZOMBIE_WOODBREAK, 2.0F, 1.0F);
                            event.getClickedBlock().setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent event){
        ItemStack itemStack = event.getItem().getItemStack();
        Player player = event.getPlayer();

        if (itemStack.getAmount()!=1){
            for (ItemStack item : player.getInventory()) {
                if (item==null || item.getType()==Material.AIR){
                    player.getInventory().addItem(itemStack);
                    event.getItem().remove();
                    return;
                }
            }
        }
    }
}
