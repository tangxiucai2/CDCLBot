package cdcl.bot.command.commands.general;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.roster.staff.StaffType;
import cdcl.bot.util.CommonUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class AddStaff implements ICommand {

    @Override
    public void run(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().substring(1).split(" ");

        if (args.length != 3) {
            event.getChannel().sendMessage(this.getCorrectCommandUsage()).queue();
            return;
        }

        if (DiscordBot.theBot.staffManager.getStaff().containsKey(args[2].toLowerCase())) {
            event.getChannel().sendMessage("That player is already has a visible staff status!").queue();
            return;
        }

        StaffType type;
        if (args[1].equalsIgnoreCase("MANAGEMENT")) {
            type = StaffType.MANAGEMENT;
        } else if (args[1].equalsIgnoreCase("REFEREE") || args[1].equalsIgnoreCase("REF")) {
            type = StaffType.REFEREE;
        } else if (args[1].equalsIgnoreCase("ADVISOR")) {
            type = StaffType.ADVISOR;
        } else if (args[1].equalsIgnoreCase("HOST")) {
            type = StaffType.HOST;
        } else if (args[1].equalsIgnoreCase("MEDIA")) {
            type = StaffType.MEDIA;
        } else {
            event.getChannel().sendMessage("That is not a valid staff type!").queue();
            return;
        }
        DiscordBot.theBot.staffManager.getStaff().put(args[2].toLowerCase(), type);
        event.getChannel().sendMessage(CommonUtil.getSuccessMessage()).queue();
    }

    @Override
    public String getCorrectCommandUsage() {
        return "!addstaff [stafftype] [player]";
    }

    @Override
    public String getCommandUsage() {
        return "addstaff";
    }

    @Override
    public String[] aliases() {
        return null;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.Roster;
    }

    @Override
    public PermissionLevel getRequiredPermissionLevel() {
        return PermissionLevel.MANAGEMENT;
    }
}
