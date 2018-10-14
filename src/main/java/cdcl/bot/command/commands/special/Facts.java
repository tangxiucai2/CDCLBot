package cdcl.bot.command.commands.special;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Facts implements ICommand {

    @Override
    public void run(MessageReceivedEvent event) {
        event.getChannel().sendFile(DiscordBot.theBot.facts).queue();
    }

    @Override
    public String getCorrectCommandUsage() {
        return "facts";
    }

    @Override
    public String getCommandUsage() {
        return "facts";
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
