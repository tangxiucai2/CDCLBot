package cdcl.bot.roster.staff;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.util.FileUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;

public class StaffManager {

    private HashMap<String, StaffType> staff = new HashMap<>();

    public void loadStaffData() {
        try {
            JSONObject obj = (JSONObject) DiscordBot.theBot.jsonParser.parse(FileUtil.readFileLinesWithoutArray(DiscordBot.theBot.staffList));
            for (Object s : obj.keySet()) {
                String entry = (String) s;
                switch ((String) obj.get(entry)) {
                    case "management":
                        staff.put(entry, StaffType.MANAGEMENT);
                        break;
                    case "host":
                        staff.put(entry, StaffType.HOST);
                        break;
                    case "ref":
                        staff.put(entry, StaffType.REFEREE);
                        break;
                    case "media":
                        staff.put(entry, StaffType.MEDIA);
                        break;
                    case "advisor":
                        staff.put(entry, StaffType.ADVISOR);
                        break;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, StaffType> getStaff() {
        return staff;
    }
}
