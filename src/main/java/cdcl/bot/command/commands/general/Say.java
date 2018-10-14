package cdcl.bot.command.commands.general;

import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Say implements ICommand {

    @Override
    public void run(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args.length < 3){
            event.getChannel().sendMessage(this.getCorrectCommandUsage()).queue();
            return;
        }

        if(event.getMessage().getMentionedChannels().size() != 1){
            event.getChannel().sendMessage("Please specify a valid channel!").queue();
            return;
        }

        TextChannel channel = event.getMessage().getMentionedChannels().get(0);
        String message;
        try {
            message = event.getMessage().getContentRaw();
        }catch (Exception e){
            event.getChannel().sendMessage("That is not a valid message!").queue();
            return;
        }

        String toSend = message.substring(message.indexOf('(') + 1, message.indexOf(')'));
        channel.sendMessage(toSend).queue();
    }

    @Override
    public String getCorrectCommandUsage() {
        return "say [channel] ([message])";
    }

    @Override
    public String getCommandUsage() {
        return "say";
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
