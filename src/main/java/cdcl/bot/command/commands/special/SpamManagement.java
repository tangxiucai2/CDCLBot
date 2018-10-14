package cdcl.bot.command.commands.special;

import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.util.CommonUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


public class SpamManagement extends ListenerAdapter implements ICommand{


    @Override
    public void run(MessageReceivedEvent event) {
        if(!event.getMember().getUser().getId().equalsIgnoreCase("282253323006902282") && !event.getMember().getUser().getId().equalsIgnoreCase("491083055394717718")){
            event.getChannel().sendMessage("Permission denied...").queue();
            return;
        }

        String[] args = event.getMessage().getContentRaw().substring(1).split(" ");

        if(args.length != 3) {
            event.getChannel().sendMessage(this.getCorrectCommandUsage()).queue();
            return;
        }

        int seconds;

        try {
            seconds = CommonUtil.parseIntTimeSeconds(args[2]);
        }catch (NumberFormatException | NullPointerException e){
            event.getChannel().sendMessage("Please specify a valid length of time!").queue();
            return;
        }

        Calendar temp = Calendar.getInstance();
        temp.add(Calendar.SECOND, seconds);
        Date untilStop = temp.getTime();

        Calendar until = Calendar.getInstance();
        until.add(Calendar.SECOND, 2);
        Date untilNext = until.getTime();


        while (!untilStop.before(Calendar.getInstance().getTime())){
            if(untilNext.before(Calendar.getInstance().getTime())) {
                event.getChannel().sendMessage("<@&462796314649034752> " + args[1]).queue();
                until.add(Calendar.SECOND, 2);
                untilNext = until.getTime();
            }
        }
    }

    @Override
    public String getCorrectCommandUsage() {
        return "spammanagement [message] [timeUntilStop]";
    }

    @Override
    public String getCommandUsage() {
        return "spammanagement";
    }

    @Override
    public String[] aliases() {
        return new String[]{"spammanager"};
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
