package cdcl.bot.core.framework;

import java.util.HashMap;

public class Team {

    private String teamName;
    private int teamTier, teamRank, teamWins, teamLosses;
    private HashMap<String, Boolean> roster;

    public Team(String teamName, HashMap<String, Boolean> roster, int teamRank, int teamTier, int teamWins, int teamLosses) {
        this.teamName = teamName;
        this.roster = roster;
        this.teamTier = teamTier;
        this.teamRank = teamRank;
        this.teamWins = teamWins;
        this.teamLosses = teamLosses;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamTier() {
        return teamTier;
    }

    public void setTeamTier(int teamTier) {
        this.teamTier = teamTier;
    }

    public int getTeamRank() {
        return teamRank;
    }

    public void setTeamRank(int teamRank) {
        this.teamRank = teamRank;
    }

    public int getTeamWins() {
        return teamWins;
    }

    public void setTeamWins(int teamWins) {
        this.teamWins = teamWins;
    }

    public int getTeamLosses() {
        return teamLosses;
    }

    public void setTeamLosses(int teamLosses) {
        this.teamLosses = teamLosses;
    }

    public HashMap<String, Boolean> getRoster() {
        return roster;
    }
}
