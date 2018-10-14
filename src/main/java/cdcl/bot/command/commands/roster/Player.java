package cdcl.bot.command.commands.roster;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.core.framework.Team;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Map;

public class Player implements ICommand {

    @Override
    public void run(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().substring(1).split(" ");

        if (args.length != 2) {
            event.getChannel().sendMessage(this.getCorrectCommandUsage()).queue();
            return;
        }

        for (Team team : DiscordBot.theBot.rosterManager.getTeams()) {
            for (Map.Entry<String, Boolean> roster : team.getRoster().entrySet()) {
                if (roster.getKey().equalsIgnoreCase(args[1])) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle(createString(roster.getKey(), roster.getValue()));
                    builder.addField("Team :", team.getTeamName(), true);
                    event.getChannel().sendMessage(builder.build()).queue();
                    return;
                }
            }
        }
    }

    private String createString(String s, boolean tl) {
        StringBuilder sb = new StringBuilder();

        sb.append(s);
        if (tl) {
            sb.append(" :crown:");
        }

        if (DiscordBot.theBot.staffManager.getStaff().containsKey(s.toLowerCase())) {
            sb.append(" ");
            sb.append(DiscordBot.theBot.staffManager.getStaff().get(s.toLowerCase()).getTag());
        }

        return sb.toString();
    }

    @Override
    public String[] aliases() {
        return new String[]{"p"};
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.Roster;
    }

    @Override
    public String getCorrectCommandUsage() {
        return "!player [name]";
    }

    @Override
    public String getCommandUsage() {
        return "player";
    }

    @Override
    public PermissionLevel getRequiredPermissionLevel() {
        return PermissionLevel.STANDARD;
    }
}
