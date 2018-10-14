package cdcl.bot.command.commands.roster;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.core.framework.Team;
import cdcl.bot.util.CommonUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RemoveLeader implements ICommand {

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

        if (!toChange.getRoster().containsKey(args[2])) {
            event.getChannel().sendMessage("That player is not on " + toChange.getTeamName() + "'s roster!").queue();
            return;
        }

        if (!toChange.getRoster().get(args[2])) {
            event.getChannel().sendMessage("That player is not a team leader!").queue();
            return;
        }

        toChange.getRoster().put(args[2], false);
        event.getChannel().sendMessage(CommonUtil.getSuccessMessage()).queue();

    }

    @Override
    public String getCorrectCommandUsage() {
        return "!removeleader [team] [player]";
    }

    @Override
    public String getCommandUsage() {
        return "removeleader";
    }

    @Override
    public PermissionLevel getRequiredPermissionLevel() {
        return PermissionLevel.MANAGEMENT;
    }

    @Override
    public String[] aliases() {
        return null;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.Roster;
    }
}
