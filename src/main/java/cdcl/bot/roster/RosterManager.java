package cdcl.bot.roster;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.Team;
import cdcl.bot.util.FileUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RosterManager {

    private ArrayList<Team> teams = new ArrayList<>();


    public void loadRoster() throws IOException, ParseException {
        JSONArray teams = (JSONArray) DiscordBot.theBot.jsonParser.parse(FileUtil.readFileLinesWithoutArray(DiscordBot.theBot.teamList));
        for (Object object1 : teams.toArray()) {
            JSONObject obj = (JSONObject) DiscordBot.theBot.jsonParser.parse(FileUtil.readFileLinesWithoutArray(new File(DiscordBot.theBot.teamsDir + ((String) object1).toLowerCase() + ".json")));
            HashMap<String, Boolean> roster = new HashMap<>();
            int teamTier = Integer.parseInt(obj.get("Tier").toString());
            int teamRank = Integer.parseInt(obj.get("Rank").toString());
            int teamWins = Integer.parseInt(obj.get("Wins").toString());
            int teamLosses = Integer.parseInt(obj.get("Losses").toString());

            for (Object object : ((JSONArray) obj.get("Roster")).toArray()) {
                String entry = (String) object;

                if (entry.startsWith("TL:")) {
                    roster.put(entry.substring(3), true);
                } else if (entry.startsWith("R:")) {
                    roster.put(entry.substring(2), false);
                }
            }

            getTeams().add(new Team((String) object1, roster, teamRank, teamTier, teamWins, teamLosses));
        }

    }

    public boolean isTeam(String teamName, boolean shortenedName) {
        int count = 0;
        for (Team team : teams) {
            try {
                if (team.getTeamName().equalsIgnoreCase(teamName)) {
                    return true;
                } else if (shortenedName && (team.getTeamName().substring(0, teamName.length()).equalsIgnoreCase(teamName)
                        || (team.getTeamName().toLowerCase().contains(teamName.toLowerCase()) && teamName.length() >= 3))) {
                    count++;
                }
            } catch (StringIndexOutOfBoundsException ignore) {
            }
        }

        return count == 1;
    }

    public Team getTeamFromName(String teamName, boolean shortenedName) {
        int count = 0;
        Team lastTeam = null;
        for (Team team : teams) {
            try {
                if (team.getTeamName().equalsIgnoreCase(teamName)) {
                    return team;
                } else if ((team.getTeamName().substring(0, teamName.length()).equalsIgnoreCase(teamName) || (team.getTeamName().toLowerCase().contains(teamName.toLowerCase()) && teamName.length() >= 3)) && shortenedName) {
                    count++;
                    lastTeam = team;
                }
            } catch (StringIndexOutOfBoundsException ignore) {
            }
        }

        if (count == 1) {
            return lastTeam;
        }

        throw new NullPointerException();
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }
}
