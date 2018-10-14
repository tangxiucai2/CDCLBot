package cdcl.bot.command.commands.roster;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.util.CommonUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RemoveTeam implements ICommand {


    @Override
    public void run(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().substring(1).split(" ");

        if (args.length != 2) {
            event.getChannel().sendMessage(this.getCorrectCommandUsage()).queue();
            return;
        }

        if (!DiscordBot.theBot.rosterManager.isTeam(args[1], false)) {
            event.getChannel().sendMessage("That is not a valid team!").queue();
            return;
        }

        DiscordBot.theBot.rosterManager.getTeams().remove(DiscordBot.theBot.rosterManager.getTeamFromName(args[1].toLowerCase(), false));
        event.getChannel().sendMessage(CommonUtil.getSuccessMessage()).queue();
    }

    @Override
    public String getCorrectCommandUsage() {
        return "!removeteam [teamname]";
    }

    @Override
    public String getCommandUsage() {
        return "removeteam";
    }

    @Override
    public PermissionLevel getRequiredPermissionLevel() {
        return PermissionLevel.MANAGEMENT;
    }

    @Override
    public String[] aliases() {
        return new String[]{"deleteteam"};
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.Roster;
    }
}
