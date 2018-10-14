package cdcl.bot.core.listener;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.thread.CommandThread;
import cdcl.bot.util.CommonUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.IOException;
import java.text.ParseException;

public class Listener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (DiscordBot.theBot.guild == null) {
            DiscordBot.theBot.guild = event.getGuild();
            try {
                DiscordBot.theBot.moderationManager.load();
            } catch (Exception e) {
                event.getChannel().sendMessage("Error : Could not load bans/mute list, a stacktrace will be printed!").queue();
                event.getChannel().sendMessage(e.getMessage()).queue();
                event.getChannel().sendMessage(e.getCause().toString()).queue();
            }
        }

        if (!event.getMessage().getContentRaw().startsWith(Character.toString(DiscordBot.theBot.prefix))) return;
        if (event.getMessage().getAuthor().isBot()) return;
        if (event.getMessage().getContentRaw().length() < 4) return;
        if (!isValid(event.getMessage().getContentRaw())) return;
        if (event.getMessage().getContentRaw().equalsIgnoreCase("!rank")) return;
        try {
            ICommand toExecute = DiscordBot.theBot.cmdManager.getCommandFromName(event.getMessage().getContentRaw().substring(1)
                    .split(" ")[0]);

            if (!CommonUtil.hasRequiredPermissionLevel(event.getMember(), toExecute.getRequiredPermissionLevel())) {
                event.getChannel().sendMessage("You do not have the appropriate permission level to execute this command!").queue();
                return;
            }

            new CommandThread(toExecute, event).start();

        } catch (NullPointerException e) {
            event.getChannel().sendMessage("That is not a valid command!").queue();
            return;
        }
        super.onMessageReceived(event);
    }

    private boolean isValid(String s) {
        int count = 0;
        int exCount = 0;
        for (char c : s.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                count++;
            }
            if (c == '!') {
                exCount++;
            }
        }
        return count != 0 && exCount < 3;
    }
}
