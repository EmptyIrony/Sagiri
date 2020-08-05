package strafe.games.sagiri.manager;

import strafe.games.sagiri.Sagiri;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;

public class MongoManager {
    @Getter
    private static MongoManager instance;
    @Getter
    private MongoClient client;
    @Getter
    private MongoDatabase database;
    @Getter
    private MongoCollection<Document> uuid;

    public MongoManager() {
        if (instance != null) {
            throw new RuntimeException("The mongo database has already been instantiated.");
        }
        instance = this;

        this.client = new MongoClient(new ServerAddress("127.0.0.1", 27017));


        this.database = this.client.getDatabase("potsg");
        Sagiri.getIns().getLogger().info("成功链接至MongoDB");
        this.uuid = this.database.getCollection("players");
    }
}
