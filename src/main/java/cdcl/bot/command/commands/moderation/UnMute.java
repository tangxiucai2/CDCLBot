package cdcl.bot.command.commands.moderation;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.util.CommonUtil;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class UnMute implements ICommand {

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

        Member toUnMute = event.getMessage().getMentionedMembers().get(0);

        if (!toUnMute.getRoles().contains(event.getGuild().getRoleById(DiscordBot.theBot.moderationManager.getMutedRole()))) {
            event.getChannel().sendMessage("That user is not muted!").queue();
            return;
        }

        try {
            event.getGuild().getController().removeSingleRoleFromMember(toUnMute, event.getGuild().getRoleById(DiscordBot.theBot.moderationManager.getMutedRole())).queue();
        } catch (Exception e) {
            event.getChannel().sendMessage("Could not un-mute that player!").queue();
        }
        event.getChannel().sendMessage(CommonUtil.getSuccessMessage()).queue();
    }

    @Override
    public String getCorrectCommandUsage() {
        return "!unmute [@user]";
    }

    @Override
    public String getCommandUsage() {
        return "unmute";
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
