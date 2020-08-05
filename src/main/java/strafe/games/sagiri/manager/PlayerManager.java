package strafe.games.sagiri.manager;

import strafe.games.miku.profile.Profile;
import strafe.games.sagiri.Sagiri;
import strafe.games.sagiri.player.PlayerData;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {
    public static Map<UUID, PlayerData> playerData = new HashMap<>();

    public static void createPlayerData(UUID uuid) {
        Profile profile = Profile.getByUuid(uuid);
        PlayerData data = new PlayerData(profile.getUuid(), profile.getName());
        loadData(data);
        playerData.put(uuid, data);
    }

    private static void loadData(PlayerData data) {
        Document document = MongoManager.getInstance().getUuid().find(Filters.eq("uuid", data.getUuid().toString())).first();
        if (document == null) {
            saveData(data);
            return;
        }
        data.setKills(document.getInteger("kills"));
        data.setWins(document.getInteger("wins"));
        data.setPlayed(document.getInteger("played"));
    }

    public static void saveData(PlayerData data) {
        Document document = new Document("uuid", data.getUuid().toString())
                .append("name", data.getUuid().toString())
                .append("wins", data.getWins())
                .append("kills", data.getKills())
                .append("played", data.getPlayed());
        Sagiri.getIns().getMongo().getUuid().replaceOne(Filters.eq("uuid", data.getUuid().toString()), document, new ReplaceOptions().upsert(true));
    }
}
