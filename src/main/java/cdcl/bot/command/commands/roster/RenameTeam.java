package cdcl.bot.command.commands.roster;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.util.CommonUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RenameTeam implements ICommand {

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

        if (DiscordBot.theBot.rosterManager.isTeam(args[2], false)) {
            event.getChannel().sendMessage("That team name is being used!").queue();
            return;
        }
        DiscordBot.theBot.rosterManager.getTeamFromName(args[1], false).setTeamName(args[2]);
        event.getChannel().sendMessage(CommonUtil.getSuccessMessage()).queue();

    }

    @Override
    public String[] aliases() {
        return new String[]{"changeteamname"};
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.Roster;
    }

    @Override
    public String getCorrectCommandUsage() {
        return "!renameteam [old team name] [new team name]";
    }

    @Override
    public String getCommandUsage() {
        return "renameteam";
    }

    @Override
    public PermissionLevel getRequiredPermissionLevel() {
        return PermissionLevel.MANAGEMENT;
    }
}
