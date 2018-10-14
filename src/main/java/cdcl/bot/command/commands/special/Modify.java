package cdcl.bot.command.commands.special;

import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Modify implements ICommand {

    @Override
    public void run(MessageReceivedEvent event) {
        StringBuilder builder = new StringBuilder();
        event.getChannel().sendMessage(builder.toString()).queue();
    }

    @Override
    public String getCorrectCommandUsage() {
        return "!modify";
    }

    @Override
    public String getCommandUsage() {
        return "modify";
    }

    @Override
    public String[] aliases() {
        return null;
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
