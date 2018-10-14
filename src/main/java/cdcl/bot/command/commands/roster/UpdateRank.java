package cdcl.bot.command.commands.roster;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.core.framework.Team;
import cdcl.bot.util.CommonUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class UpdateRank implements ICommand {

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

        int rankToUpdate;

        try {
            rankToUpdate = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("That is not a valid number!").queue();
            return;
        }

        Team toChange = DiscordBot.theBot.rosterManager.getTeamFromName(args[1], false);

        toChange.setTeamRank(rankToUpdate);
        event.getChannel().sendMessage(CommonUtil.getSuccessMessage()).queue();
    }

    @Override
    public String getCorrectCommandUsage() {
        return "!updaterank [team] [value]";
    }

    @Override
    public String getCommandUsage() {
        return "updaterank";
    }

    @Override
    public String[] aliases() {
        return new String[]{"changerank"};
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.Roster;
    }

    @Override
    public PermissionLevel getRequiredPermissionLevel() {
        return PermissionLevel.MANAGEMENT;
    }
}
