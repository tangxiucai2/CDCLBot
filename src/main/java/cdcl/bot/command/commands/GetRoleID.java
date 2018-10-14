package cdcl.bot.command.commands;

import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class GetRoleID implements ICommand {

    @Override
    public void run(MessageReceivedEvent event) {
        event.getChannel().sendMessage(event.getMessage().getMentionedRoles().get(0).getId()).queue();
    }

    @Override
    public String getCorrectCommandUsage() {
        return "yettus";
    }

    @Override
    public String getCommandUsage() {
        return "getroleid";
    }

    @Override
    public String[] aliases() {
        return null;
    }

    @Override
    public PermissionLevel getRequiredPermissionLevel() {
        return PermissionLevel.STANDARD;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.Roster;
    }
}
