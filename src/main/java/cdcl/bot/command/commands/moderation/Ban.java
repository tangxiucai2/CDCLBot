package cdcl.bot.command.commands.moderation;

import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.util.CommonUtil;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;


public class Ban implements ICommand {

    @Override
    public void run(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().substring(1).split(" ");

        if (args.length < 2 || args.length > 3) {
            event.getChannel().sendMessage(this.getCorrectCommandUsage()).queue();
            return;
        }

        if (event.getMessage().getMentionedMembers().size() != 1) {
            event.getChannel().sendMessage(this.getCorrectCommandUsage()).queue();
            return;
        }

        String reason = args[2] == null || args[2].isEmpty() ? "Not Defined" : args[2];

        Member toBan = event.getMessage().getMentionedMembers().get(0);

        event.getGuild().getController().ban(toBan, 0, reason).queue();
        event.getChannel().sendMessage(CommonUtil.getSuccessMessage()).queue();

    }

    @Override
    public String[] aliases() {
        return null;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.Moderation;
    }

    @Override
    public String getCorrectCommandUsage() {
        return "!ban [@user] reason, !ban [@user] [reason]";
    }

    @Override
    public String getCommandUsage() {
        return "ban";
    }

    @Override
    public PermissionLevel getRequiredPermissionLevel() {
        return PermissionLevel.REFEREE;
    }
}
