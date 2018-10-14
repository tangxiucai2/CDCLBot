package cdcl.bot.util;

import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.core.framework.Team;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class CommonUtil {

    public static String getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static int parseIntTimeMinutes(String parseText) {
        if (parseText.substring(parseText.length() - 2).equalsIgnoreCase("mo")) {
            return Integer.parseInt(parseText.substring(0, parseText.length() - 2)) * 30 * 24 * 60;
        }
        if (getLastCharacter(parseText).equalsIgnoreCase("d")) {
            return Integer.parseInt(parseText.substring(0, parseText.length() - 1)) * 24 * 60;
        } else if (getLastCharacter(parseText).equalsIgnoreCase("h")) {
            return Integer.parseInt(parseText.substring(0, parseText.length() - 1)) * 60;
        } else if (getLastCharacter(parseText).equalsIgnoreCase("m")) {
            return Integer.parseInt(parseText.substring(0, parseText.length() - 1));
        } else {
            throw new NullPointerException();
        }
    }

    public static int parseIntTimeSeconds(String parseText) {
        if (parseText.substring(parseText.length() - 2).equalsIgnoreCase("mo")) {
            return Integer.parseInt(parseText.substring(0, parseText.length() - 2)) * 30 * 24 * 60 * 60;
        }
        if (getLastCharacter(parseText).equalsIgnoreCase("d")) {
            return Integer.parseInt(parseText.substring(0, parseText.length() - 1)) * 24 * 60 * 60;
        } else if (getLastCharacter(parseText).equalsIgnoreCase("h")) {
            return Integer.parseInt(parseText.substring(0, parseText.length() - 1)) * 60 * 60;
        } else if (getLastCharacter(parseText).equalsIgnoreCase("m")) {
            return Integer.parseInt(parseText.substring(0, parseText.length() - 1)) * 60;
        }else if(getLastCharacter(parseText).equalsIgnoreCase("s")){
            return Integer.parseInt(parseText.substring(0, parseText.length() - 1));
        } else {
            throw new NullPointerException();
        }
    }

    private static String getLastCharacter(String string) {
        return string.substring(string.length() - 1);
    }

    public static String processString(String s) {
        StringBuilder builder = new StringBuilder();
        for (char character : s.toCharArray()) {
            if (character == '_' || character == '*' || character == '~') {
                builder.append("\\");
                builder.append(character);
            } else {
                builder.append(character);
            }
        }
        return builder.toString();
    }


    public static boolean hasRequiredPermissionLevel(Member member, PermissionLevel permissionLevel) {
        if (permissionLevel.equals(PermissionLevel.STANDARD)) {
            return true;
        }

        if (member.getUser().getId().equalsIgnoreCase("282253323006902282")) {
            return true;
        }

        if (permissionLevel.equals(PermissionLevel.MANAGEMENT)) {
            return hasRole(member, "462796314649034752");
        }
        if (permissionLevel.equals(PermissionLevel.REFEREE)) {
            return hasRole(member, "462829570920415232") || hasRole(member, "462796314649034752");
        }

        return false;
    }

    public static boolean hasRole(Member m, String roleID) {
        for (Role r : m.getRoles()) {
            if (r.getId().equalsIgnoreCase(roleID))
                return true;
        }
        return false;
    }

    public static void sendIOError(Exception e, MessageChannel channel) {
        channel.sendMessage("IOException : " + e.getMessage()).queue();
    }

    public static String getSuccessMessage() {
        return " :white_check_mark:";
    }

    public static int getColorFromTier(Team team) {
        switch (team.getTeamTier()) {
            case 1:
                return 0xe9340e;
            case 2:
                return 0xecc71d;
            case 3:
                return 0xe8ec1d;
            case 4:
                return 0x29fc2b;
            case 5:
                return 0x1d24c9;
            case 6:
                return 0x8201bb;
            case 7:
                return 0xe756ef;
        }
        return 0xffffff;
    }

    @SuppressWarnings("unchecked")
    public static JSONObject getJSONFromTeam(Team team) {
        JSONObject obj = new JSONObject();
        obj.put("Wins", team.getTeamWins());
        obj.put("Losses", team.getTeamLosses());
        obj.put("Rank", team.getTeamRank());
        obj.put("Tier", team.getTeamTier());
        JSONArray roster = new JSONArray();
        for (Map.Entry<String, Boolean> set : team.getRoster().entrySet()) {
            if (set.getValue()) {
                roster.add("TL:" + set.getKey());
            } else {
                roster.add("R:" + set.getKey());
            }
        }
        obj.put("Roster", roster);
        return obj;
    }


}
