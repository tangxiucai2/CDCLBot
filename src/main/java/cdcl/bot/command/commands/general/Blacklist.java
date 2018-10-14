package cdcl.bot.command.commands.general;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.util.CommonUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Blacklist implements ICommand {

    @Override
    public void run(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().substring(1).split(" ");
        if (args.length < 2) {
            event.getChannel().sendMessage(this.getCorrectCommandUsage()).queue();
            return;
        }

        if (args[1].equalsIgnoreCase("list")) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Blacklisted");
            StringBuilder sb = new StringBuilder();
            for (String s : DiscordBot.theBot.moderationManager.getBlacklisted()) {
                sb.append("\n");
                sb.append(s);
            }
            builder.addField("Players : ", sb.toString(), true);
            builder.setFooter("As of " + CommonUtil.getCurrentTime(), null);
            builder.setColor(DiscordBot.theBot.defaultColor);
            event.getChannel().sendMessage(builder.build()).queue();
            return;
        } else if (!CommonUtil.hasRequiredPermissionLevel(event.getMember(), PermissionLevel.REFEREE)) {
            event.getChannel().sendMessage("You do not have the required permission level to complete this command!").queue();
            return;
        } else if (args.length != 3) {
            event.getChannel().sendMessage(this.getCorrectCommandUsage()).queue();
            return;
        }

        if (!(args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove"))) {
            event.getChannel().sendMessage(this.getCorrectCommandUsage()).queue();
            return;
        }

        if (args[1].equalsIgnoreCase("add")) {
            if (DiscordBot.theBot.moderationManager.isBlacklisted(args[2])) {
                event.getChannel().sendMessage("That user is already blacklisted!").queue();
                return;
            }
            DiscordBot.theBot.moderationManager.getBlacklisted().add(args[2].toLowerCase());
        }

        if (args[1].equalsIgnoreCase("remove")) {
            if (!DiscordBot.theBot.moderationManager.isBlacklisted(args[2])) {
                event.getChannel().sendMessage("That user is not blacklisted!").queue();
                return;
            }
            DiscordBot.theBot.moderationManager.getBlacklisted().remove(args[2].toLowerCase());
        }

        event.getChannel().sendMessage(CommonUtil.getSuccessMessage()).queue();
    }

    @Override
    public String getCorrectCommandUsage() {
        return "!blacklist [add/remove/list] {@user}";
    }

    @Override
    public String getCommandUsage() {
        return "blacklist";
    }

    @Override
    public String[] aliases() {
        return new String[]{"bl"};
    }

    @Override
    public PermissionLevel getRequiredPermissionLevel() {
        return PermissionLevel.STANDARD;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.Moderation;
    }
}
