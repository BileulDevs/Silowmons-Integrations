package dev.darcosse.silowmons_integrations.fabric.util;

public class ScoreboardUtils {
    public static String getPodiumColor(int value) {
        return switch (value) {
            case 1 -> "§e#" + value;
            case 2 -> "§7#" + value;
            case 3 -> "§n#" + value;
            default -> "§f#" + value;
        };
    }
}
