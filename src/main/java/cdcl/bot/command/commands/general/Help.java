package cdcl.bot.command.commands.general;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.util.CommonUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Help implements ICommand {


    @Override
    public void run(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().substring(1).split(" ");

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(DiscordBot.theBot.defaultColor);
        if (args.length != 2) {
            if (CommonUtil.hasRequiredPermissionLevel(event.getMember(), PermissionLevel.REFEREE)) {
                builder.addField("Moderation :tools:", "`!help moderation`", true);
            }
            builder.addField("Roster :desktop:", "`!help roster`", true);
            builder.addField("Misc :traffic_light:", "`!help misc`", true);
            event.getChannel().sendMessage(builder.build()).queue();
            return;
        }

        if (args[1].equalsIgnoreCase("moderation")) {
            if (!CommonUtil.hasRequiredPermissionLevel(event.getMember(), PermissionLevel.REFEREE)) {
                event.getChannel().sendMessage("That is not a valid category!").queue();
                return;
            }
            builder.setTitle("Moderation Commands :");
            for (ICommand c : DiscordBot.theBot.cmdManager.getCommands()) {
                if (c.getCategory().equals(CommandCategory.Moderation)) {
                    builder.addField('`' + c.getCorrectCommandUsage() + '`', "", false);
                }
            }
        } else if (args[1].equalsIgnoreCase("roster")) {
            builder.setTitle("Roster Commands :");
            for (ICommand c : DiscordBot.theBot.cmdManager.getCommands()) {
                if (c.getCategory().equals(CommandCategory.Roster)) {
                    builder.addField('`' + c.getCorrectCommandUsage() + '`', "", false);
                }
            }
        } else if (args[1].equalsIgnoreCase("misc")) {
            builder.setTitle("Misc Commands :");
            for (ICommand c : DiscordBot.theBot.cmdManager.getCommands()) {
                if (c.getCategory().equals(CommandCategory.Misc)) {
                    builder.addField('`' + c.getCorrectCommandUsage() + '`', "", false);
                }
            }
        }

        try {
            event.getMember().getUser().openPrivateChannel().complete().sendMessage(builder.build()).queue();
            event.getChannel().sendMessage(CommonUtil.getSuccessMessage()).queue();
        } catch (Exception e) {
            event.getChannel().sendMessage(builder.build()).queue();
        }
    }


    @Override
    public String getCorrectCommandUsage() {
        return "!help";
    }

    @Override
    public String getCommandUsage() {
        return "help";
    }

    @Override
    public String[] aliases() {
        return null;
    }

    @Override
    public PermissionLevel getRequiredPermissionLevel() {
        return PermissionLevel.STANDARD;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.NONE;
    }
}
