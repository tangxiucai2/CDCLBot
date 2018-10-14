package cdcl.bot.thread;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.util.CommonUtil;
import net.dv8tion.jda.core.entities.Member;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MuteTimeThread extends Thread {

    @Override
    public void run() {
        while (true) {

            try {
                for (Map.Entry<Member, Date> entry : new HashMap<>(DiscordBot.theBot.moderationManager.getMuteList()).entrySet()) {
                    if (entry.getKey() == null) {
                        continue;
                    }
                    if (!DiscordBot.theBot.moderationManager.getMuteList().containsKey(entry.getKey()) &&
                            !CommonUtil.hasRole(entry.getKey(), DiscordBot.theBot.moderationManager.getMutedRole())) {
                        DiscordBot.theBot.guild.getController().addSingleRoleToMember(entry.getKey(), DiscordBot.theBot.guild.getRoleById(DiscordBot.theBot.moderationManager.getMutedRole())).queue();
                    }

                    if (entry.getValue().before(Calendar.getInstance().getTime())) {
                        DiscordBot.theBot.guild.getController().removeSingleRoleFromMember(entry.getKey(), DiscordBot.theBot.guild.getRoleById(DiscordBot.theBot.moderationManager.getMutedRole())).queue();
                        DiscordBot.theBot.moderationManager.getMuteList().remove(entry.getKey());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            try {
                Thread.sleep(14000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
