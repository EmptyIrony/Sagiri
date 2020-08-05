package strafe.games.sagiri.utils;

public class StringUtil {
    public static String replaceMessage(String message) {
        return message.replaceAll("&", "§");
    }

    public static String secondsToString(int pTime) {
        return String.format("%02d:%02d", pTime / 60, pTime % 60);
    }
}
