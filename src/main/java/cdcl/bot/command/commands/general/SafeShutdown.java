package cdcl.bot.command.commands.general;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.core.framework.Team;
import cdcl.bot.roster.staff.StaffType;
import cdcl.bot.util.CommonUtil;
import cdcl.bot.util.FileUtil;
import com.cedarsoftware.util.io.JsonWriter;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.Map;

public class SafeShutdown implements ICommand {

    @Override
    public void run(MessageReceivedEvent event) {
        Message message = event.getChannel().sendMessage("Waiting for file thread to shutdown...").complete();
        while (DiscordBot.theBot.thread.isSaving) {
            //IGNORE
        }

        event.getChannel().editMessageById(message.getId(), "File thread shutdown.").queue();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        event.getChannel().editMessageById(message.getId(), "Saving data...").queue();

        try {
            FileUtils.cleanDirectory(new File(DiscordBot.theBot.teamsDir));
            Files.delete(DiscordBot.theBot.teamList.toPath());
            DiscordBot.theBot.teamList.createNewFile();
            JSONArray teamListArray = new JSONArray();
            for (Team team : DiscordBot.theBot.rosterManager.getTeams()) {
                teamListArray.add(team.getTeamName());
                File f = new File(DiscordBot.theBot.teamsDir + team.getTeamName().toLowerCase() + ".json");
                f.createNewFile();
                FileUtil.writeStringToFile(f, JsonWriter.formatJson(CommonUtil.getJSONFromTeam(team).toJSONString()), false);
            }
            FileUtil.writeStringToFile(DiscordBot.theBot.teamList, JsonWriter.formatJson(teamListArray.toJSONString()), false);

            JSONObject staffObj = new JSONObject();

            for (Map.Entry<String, StaffType> set : DiscordBot.theBot.staffManager.getStaff().entrySet()) {
                String toSave;
                if (set.getValue().equals(StaffType.MANAGEMENT)) {
                    toSave = "management";
                } else if (set.getValue().equals(StaffType.REFEREE)) {
                    toSave = "ref";
                } else if (set.getValue().equals(StaffType.MEDIA)) {
                    toSave = "media";
                } else if (set.getValue().equals(StaffType.HOST)) {
                    toSave = "host";
                } else if (set.getValue().equals(StaffType.ADVISOR)) {
                    toSave = "advisor";
                } else {
                    continue;
                }
                staffObj.put(set.getKey(), toSave);
            }

            Files.delete(DiscordBot.theBot.staffList.toPath());
            DiscordBot.theBot.staffList.createNewFile();
            FileUtil.writeStringToFile(DiscordBot.theBot.staffList, JsonWriter.formatJson(staffObj.toJSONString()), false);

            Files.delete(DiscordBot.theBot.muteList.toPath());
            DiscordBot.theBot.muteList.createNewFile();
            JSONObject mutedList = new JSONObject();
            for (Map.Entry<Member, Date> entry : DiscordBot.theBot.moderationManager.getMuteList().entrySet()) {
                mutedList.put(entry.getKey().getUser().getId(), DiscordBot.theBot.dateFormat.format(entry.getValue()));
            }

            String write;

            if(mutedList.size() == 0){
                write = "{}";
            }else{
                write = JsonWriter.formatJson(mutedList.toJSONString());
            }

            FileUtil.writeStringToFile(DiscordBot.theBot.muteList, write, false);

            JSONArray blacklisted = new JSONArray();

            for (String s : DiscordBot.theBot.moderationManager.getBlacklisted()) {
                blacklisted.add(s);
            }

            Files.delete(DiscordBot.theBot.blacklistedFile.toPath());
            DiscordBot.theBot.blacklistedFile.createNewFile();
            FileUtil.writeStringToFile(DiscordBot.theBot.blacklistedFile, JsonWriter.formatJson(blacklisted.toJSONString()), false);
        } catch (IOException e) {
            CommonUtil.sendIOError(e, event.getChannel());
            return;
        }

        event.getChannel().editMessageById(message.getId(), CommonUtil.getSuccessMessage()).complete();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    @Override
    public String getCorrectCommandUsage() {
        return "!safeshutdown";
    }

    @Override
    public String getCommandUsage() {
        return "safeshutdown";
    }

    @Override
    public String[] aliases() {
        return new String[]{"shutdown"};
    }

    @Override
    public PermissionLevel getRequiredPermissionLevel() {
        return PermissionLevel.MANAGEMENT;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.NONE;
    }
}
