package strafe.games.sagiri.leaderboard;

import strafe.games.miku.profile.Profile;
import strafe.games.sagiri.manager.MongoManager;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LeaderBoard {
    private List<LeaderboardEntry> killsLeaderboard = new ArrayList<>();
    private List<LeaderboardEntry> winsLeaderboard = new ArrayList<>();
    private List<LeaderboardEntry> playedLeaderboard = new ArrayList<>();

    public List<LeaderboardEntry> getKillsLeaderboard() {
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put("kills", -1);
        FindIterable<Document> iter1 = MongoManager.getInstance().getUuid().find().sort(dbObject).limit(5);
        int num = 0;
        for (Document doc : iter1) {
            if (num == 5) {
                break;
            }
            killsLeaderboard.add(new LeaderboardEntry(Profile.getByUuid(UUID.fromString(doc.getString("uuid"))).getName(), doc.getInteger("kills")));
            num++;
        }
        return killsLeaderboard;
    }

    public List<LeaderboardEntry> getWinsLeaderboard() {
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put("wins", -1);
        FindIterable<Document> iter1 = MongoManager.getInstance().getUuid().find().sort(dbObject).limit(5);
        int num = 0;
        for (Document doc : iter1) {
            if (num == 5) {
                break;
            }
            winsLeaderboard.add(new LeaderboardEntry(Profile.getByUuid(UUID.fromString(doc.getString("uuid"))).getName(), doc.getInteger("wins")));
            num++;
        }
        return winsLeaderboard;
    }

    public List<LeaderboardEntry> getPlayedLeaderboard() {
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put("played", -1);
        FindIterable<Document> iter1 = MongoManager.getInstance().getUuid().find().sort(dbObject).limit(5);
        int num = 0;
        for (Document doc : iter1) {
            if (num == 5) {
                break;
            }
            playedLeaderboard.add(new LeaderboardEntry(Profile.getByUuid(UUID.fromString(doc.getString("uuid"))).getName(), doc.getInteger("played")));
            num++;
        }
        return playedLeaderboard;
    }
}
