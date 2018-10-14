package cdcl.bot.command.commands.general;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SetLoggingChannel implements ICommand {

    @Override
    public void run(MessageReceivedEvent event) {

        if(event.getMessage().getContentRaw().split(" ").length != 2){
            event.getChannel().sendMessage(this.getCorrectCommandUsage()).queue();
            return;
        }

        if(event.getMessage().getMentionedChannels().size() != 1){
            event.getChannel().sendMessage("Please specify a valid channel!").queue();
            return;
        }

        DiscordBot.theBot.logger.setChannel(event.getMessage().getMentionedChannels().get(0));
    }

    @Override
    public String getCorrectCommandUsage() {
        return "!setloggingchannel [#chanel]";
    }

    @Override
    public String getCommandUsage() {
        return "setloggingchannel";
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
