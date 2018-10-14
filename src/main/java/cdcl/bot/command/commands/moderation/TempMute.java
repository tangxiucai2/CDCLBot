package cdcl.bot.command.commands.moderation;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.util.CommonUtil;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Calendar;


public class TempMute implements ICommand {

    @Override
    public void run(MessageReceivedEvent event) {
            String[] args = event.getMessage().getContentRaw().split(" ");

            if (args.length != 3 || event.getMessage().getMentionedMembers().size() != 1) {
                event.getChannel().sendMessage(this.getCorrectCommandUsage()).queue();
                return;
            }

            Member toMute = event.getMessage().getMentionedMembers().get(0);
            int minutes;

            try {
                minutes = CommonUtil.parseIntTimeMinutes(args[2]);
            } catch (NullPointerException | NumberFormatException e) {
                event.getChannel().sendMessage("Please specify a valid amount of time!").queue();
                return;
            }


            Calendar c = Calendar.getInstance();
            c.add(Calendar.MINUTE, minutes);
            DiscordBot.theBot.moderationManager.getMuteList().put(toMute, c.getTime());
            event.getGuild().getController().addSingleRoleToMember(toMute, event.getGuild().getRoleById(DiscordBot.theBot.moderationManager.getMutedRole())).queue();
            event.getChannel().sendMessage(CommonUtil.getSuccessMessage()).queue();

    }

    @Override
    public String getCorrectCommandUsage() {
        return "!tempmute [@user] [length(mo, d, h, m)]";
    }

    @Override
    public String getCommandUsage() {
        return "tempmute";
    }

    @Override
    public String[] aliases() {
        return new String[]{"tmute"};
    }

    @Override
    public PermissionLevel getRequiredPermissionLevel() {
        return PermissionLevel.REFEREE;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.Moderation;
    }
}
