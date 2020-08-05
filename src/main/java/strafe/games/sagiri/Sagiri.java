package strafe.games.sagiri;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import strafe.games.miku.Miku;
import strafe.games.miku.util.board.assemble.Assemble;
import strafe.games.miku.util.tab.Ziggurat;
import strafe.games.sagiri.commands.*;
import strafe.games.sagiri.enums.Status;
import strafe.games.sagiri.holographic.SpawnHologram;
import strafe.games.sagiri.leaderboard.LeaderBoard;
import strafe.games.sagiri.listener.*;
import strafe.games.sagiri.manager.LogoutManager;
import strafe.games.sagiri.manager.MongoManager;
import strafe.games.sagiri.manager.PlayerManager;
import strafe.games.sagiri.player.Relog;
import strafe.games.sagiri.scoreboard.CustomScoreboardImpl;
import strafe.games.sagiri.scoreboard.CustomTabImpl;
import strafe.games.sagiri.utils.StringUtil;

import java.io.File;
import java.sql.Connection;
import java.util.*;

@Getter
public class Sagiri extends JavaPlugin {
    @Getter
    private static Sagiri ins;

    @Setter
    private Status status;

    private Map<UUID, Boolean> isSelect = new HashMap<>();
    private Configuration extra;

    @Setter
    private int borderSize;
    private List<UUID> alivePlayer = new ArrayList<>();
    private List<UUID> spectators = new ArrayList<>();
    private Map<Player, Integer> kills = new HashMap<>();
    private FileConfiguration kitConfig;
    private Map<Integer, List<ItemStack>> kits = new HashMap<>();
    private Map<Integer,List<ItemStack>> extraKits = new HashMap<>();
    private Map<Player, Location> openInvLocation = new HashMap<>();
    @Setter
    private boolean deathMatch;
    private Map<Player, Boolean> isCooldown = new HashMap<>();
    private Map<Player, Double> cooldownTimer = new HashMap<>();
    @Setter
    private Connection connection;
    private List<Player> playedPlayer = new ArrayList<>();
    private MongoManager mongo;
    private LeaderBoard leaderBoard;
    private List<Relog> relogs = new ArrayList<>();

    @Override
    public void onEnable() {
        ins = this;
        this.registerConfig();
        this.status = Status.WAITING;
        this.registerWorld();
        this.registerManagers();
        this.registerListeners();
        this.registerCommands();
        new SpawnHologram().runTaskLater(Sagiri.getIns(), 20L);
        this.registerKits();

    }

    @Override
    public void onDisable() {
        saveConfig();
        for (Player player : playedPlayer) {
            PlayerManager.saveData(PlayerManager.playerData.get(player.getUniqueId()));
        }
    }

    private void registerWorld() {
        if (!Bukkit.getWorlds().contains(Bukkit.getWorld("Lobby"))) {
            Bukkit.createWorld(new WorldCreator("Lobby"));
        }
        if (!Bukkit.getWorlds().contains(Bukkit.getWorld("world"))) {
            Bukkit.createWorld(new WorldCreator("world"));
        }
    }

    private void registerConfig() {
        saveDefaultConfig();
    }

    private void registerManagers() {
        Assemble assemble = new Assemble(this, new CustomScoreboardImpl());
        assemble.setTicks(2);
        Ziggurat ziggurat = new Ziggurat(this, new CustomTabImpl());
        ziggurat.setTicks(20);
        this.mongo = new MongoManager();
        this.leaderBoard = new LeaderBoard();
    }

    private void registerListeners() {
        List<Listener> listeners = Lists.newArrayList();
        listeners.add(new PreGameListener());
        listeners.add(new EntityDamageListener());
        listeners.add(new PlayerJoinListener());
        listeners.add(new PlayerDeathListener());
        listeners.add(new InventoryListener());
        listeners.add(new BorderListener());
        listeners.add(new SpecListener());
        listeners.add(new DeathMatchListener());
        listeners.add(new EnderPearlListener());
        listeners.add(new LogoutManager());
        listeners.add(new GameListener());
        listeners.forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }

    private void registerCommands() {
        getServer().getPluginCommand("set").setExecutor(new SetCommand());
        getServer().getPluginCommand("forcestart").setExecutor(new ForceStart());
        getServer().getPluginCommand("save").setExecutor(new SaveChestCommand());
        getServer().getPluginCommand("shabishujukusantianzhineishaleni").setExecutor(new ResetDatabase());

        Miku.get().getHoncho().registerCommand(new SaveExtraCommand());
    }

    private void registerKits() {
        File file = new File(getDataFolder(), "kitConfig.yml");
        kitConfig = YamlConfiguration.loadConfiguration(file);
        for (int i = 1; i < 99; i++) {
            if (kitConfig.get("kit." + i) == null) {
                getLogger().info(StringUtil.replaceMessage("成功加载" + (i - 1) + "个kit"));
                break;
            }
            List<ItemStack> list = (ArrayList<ItemStack>) getKitConfig().get("kit." + i);
            kits.put(i, list);
        }

        File file1 = new File(getDataFolder(), "extraKit.yml");
        extra = YamlConfiguration.loadConfiguration(file1);

        for (int i = 1; i < 99; i++) {
            if (extra.get("kit." + i) == null) {
                getLogger().info(StringUtil.replaceMessage("成功加载" + (i - 1) + "个高级kit"));
                break;
            }
            List<ItemStack> list = (ArrayList<ItemStack>) extra.get("kit." + i);
            extraKits.put(i, list);
        }
    }
}
