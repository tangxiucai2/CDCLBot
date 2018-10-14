package cdcl.bot.moderation;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.thread.MuteTimeThread;
import cdcl.bot.util.FileUtil;
import net.dv8tion.jda.core.entities.Member;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ModerationManager {

    private HashMap<Member, Date> muteList = new HashMap<>();
    private ArrayList<String> blacklisted = new ArrayList<>();

    private void startThreads() {
        new MuteTimeThread().start();
    }


    public HashMap<Member, Date> getMuteList() {
        return muteList;
    }

    public String getMutedRole() {
        return "476112829842194443";
        //return "488044301146783774";
    }

    private void loadBlacklisted() throws IOException {
        JSONArray array;
        try {
            array = (JSONArray) DiscordBot.theBot.jsonParser.parse(FileUtil.readFileLinesWithoutArray(DiscordBot.theBot.blacklistedFile));
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
            array = null;
            System.exit(0);
        }

        for (Object obj : array.toArray()) {
            blacklisted.add(((String) obj).toLowerCase());
        }
    }


    private void loadMutes() throws IOException, ParseException {
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) DiscordBot.theBot.jsonParser.parse(FileUtil.readFileLinesWithoutArray(DiscordBot.theBot.muteList));
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
            muteList = new HashMap<>();
            return;
        }

        for (Object obj : jsonObject.keySet()) {
            String entry = (String) obj;
            muteList.put(DiscordBot.theBot.guild.getMemberById(entry), DiscordBot.theBot.dateFormat.parse((String) jsonObject.get(entry)));
        }

    }

    public void load() throws IOException, ParseException {
        if (DiscordBot.theBot.guild != null) {
            loadMutes();
            loadBlacklisted();
            startThreads();
        }
    }

    public ArrayList<String> getBlacklisted() {
        return blacklisted;
    }

    public boolean isBlacklisted(String s) {
        for (String s2 : blacklisted) {
            if (s2.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

}
