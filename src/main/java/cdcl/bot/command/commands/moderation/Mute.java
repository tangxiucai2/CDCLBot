package cdcl.bot.command.commands.moderation;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.util.CommonUtil;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Mute implements ICommand {

    @Override
    public void run(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().substring(1).split(" ");

        if (args.length != 2) {
            event.getChannel().sendMessage(this.getCorrectCommandUsage()).queue();
            return;
        }

        if (event.getMessage().getMentionedMembers().size() != 1) {
            event.getChannel().sendMessage(this.getCorrectCommandUsage()).queue();
            return;
        }

        Member toMute = event.getMessage().getMentionedMembers().get(0);

        try {
            event.getGuild().getController().addSingleRoleToMember(toMute, event.getGuild().getRoleById(DiscordBot.theBot.moderationManager.getMutedRole())).queue();
            event.getChannel().sendMessage(CommonUtil.getSuccessMessage()).queue();
        } catch (Exception e) {
            event.getChannel().sendMessage("Could not mute this user!").queue();
            e.printStackTrace();
        }
    }

    @Override
    public String getCorrectCommandUsage() {
        return "!mute [@user]";
    }

    @Override
    public String getCommandUsage() {
        return "mute";
    }

    @Override
    public String[] aliases() {
        return null;
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
