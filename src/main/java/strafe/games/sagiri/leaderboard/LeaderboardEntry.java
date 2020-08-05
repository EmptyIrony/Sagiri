package strafe.games.sagiri.leaderboard;

import lombok.Getter;
import lombok.Setter;

public class LeaderboardEntry {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int valve;

    public LeaderboardEntry(String name, int valve) {
        this.name = name;
        this.valve = valve;
    }
}
