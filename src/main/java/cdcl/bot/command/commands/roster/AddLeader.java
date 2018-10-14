package cdcl.bot.command.commands.roster;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.core.framework.Team;
import cdcl.bot.util.CommonUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Map;

public class AddLeader implements ICommand {

    @Override
    public void run(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().substring(1).split(" ");

        if (args.length != 3) {
            event.getChannel().sendMessage(this.getCorrectCommandUsage()).queue();
            return;
        }

        if (!DiscordBot.theBot.rosterManager.isTeam(args[1], false)) {
            event.getChannel().sendMessage("That is not a valid team!").queue();
            return;
        }

        Team toChange = DiscordBot.theBot.rosterManager.getTeamFromName(args[1], false);


        int count = 0;
        for (Map.Entry<String, Boolean> entry : toChange.getRoster().entrySet()) {
            if (entry.getValue()) {
                count++;
            }
        }

        if (count >= 2) {
            event.getChannel().sendMessage("This team has reached the maximum amount of leaders!").queue();
            return;
        }

        toChange.getRoster().put(args[2], true);
        event.getChannel().sendMessage(CommonUtil.getSuccessMessage()).queue();
    }

    @Override
    public String getCorrectCommandUsage() {
        return "!addleader [team]";
    }

    @Override
    public String getCommandUsage() {
        return "addleader";
    }

    @Override
    public PermissionLevel getRequiredPermissionLevel() {
        return PermissionLevel.MANAGEMENT;
    }

    @Override
    public String[] aliases() {
        return new String[]{"addtl"};
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.Roster;
    }
}
