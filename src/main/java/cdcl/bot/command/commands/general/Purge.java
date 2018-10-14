package cdcl.bot.command.commands.general;

import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class Purge implements ICommand {

    @Override
    public void run(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().substring(1).split(" ");

        if(args.length != 2){
            event.getChannel().sendMessage(this.getCorrectCommandUsage()).queue();
            return;
        }

        int amount;

        try{
            amount = Integer.parseInt(args[1]);
        }catch (NumberFormatException e){
            event.getChannel().sendMessage("That is not a valid number!").queue();
            return;
        }

        List<Message> messages = event.getChannel().getHistory().retrievePast(amount).complete();

        for(Message m: messages){
            event.getChannel().deleteMessageById(m.getId()).queue();
        }
    }

    @Override
    public String getCorrectCommandUsage() {
        return "!purge [amount]";
    }

    @Override
    public String getCommandUsage() {
        return "purge";
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
