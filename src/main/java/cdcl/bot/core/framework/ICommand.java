package cdcl.bot.core.framework;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;


public interface ICommand {

    void run(MessageReceivedEvent event);

    String getCorrectCommandUsage();

    String getCommandUsage();

    String[] aliases();

    PermissionLevel getRequiredPermissionLevel();

    CommandCategory getCategory();

}
