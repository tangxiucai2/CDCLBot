package cdcl.bot.command.commands.roster;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.core.framework.Team;
import cdcl.bot.util.CommonUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Comparator;
import java.util.Map;

public class Roster implements ICommand {

    private MessageEmbed rosterHelp;

    public Roster() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Roster Help :");
        builder.addField("!roster [team]", "Shows a team on the roster", false);
        builder.addField("!roster list", "Lists all teams on the league", false);
        builder.setColor(DiscordBot.theBot.defaultColor);
        rosterHelp = builder.build();
    }

    @Override
    public void run(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().substring(1).split(" ");
        if (args.length == 1 || args[1].equalsIgnoreCase("help")) {
            event.getChannel().sendMessage(rosterHelp).queue();
            return;
        }

        if (args[1].equalsIgnoreCase("list")) {
            DiscordBot.theBot.rosterManager.getTeams().sort(Comparator.comparing(Team::getTeamRank));
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(DiscordBot.theBot.defaultColor);
            int loops = 1;
            for (Team team : DiscordBot.theBot.rosterManager.getTeams()) {
                String teamTier = Integer.toString(team.getTeamTier());
                boolean inline = DiscordBot.theBot.rosterManager.getTeams().size() > loops;
                embedBuilder.addField(team.getTeamName(), "Tier: " + teamTier + " Rank: " + Integer.toString(team.getTeamRank()), inline);
                loops++;
            }
            embedBuilder.setTitle("Teams: ");
            event.getChannel().sendMessage(embedBuilder.build()).queue();
            return;
        }

        String messageOnwards = event.getMessage().getContentRaw().substring(8);
        if (DiscordBot.theBot.rosterManager.isTeam(messageOnwards, true)) {
            Team team = DiscordBot.theBot.rosterManager.getTeamFromName(messageOnwards, true);
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle(createTitleBuilder(team));
            builder.addField("Roster:", createRosterBuilder(team), true);
            builder.setColor(CommonUtil.getColorFromTier(team));
            builder.setFooter("As of " + CommonUtil.getCurrentTime(), null);
            event.getChannel().sendMessage(builder.build()).queue();
        } else {
            event.getChannel().sendMessage("That is not a valid team!").queue();
        }

    }

    private String createTitleBuilder(Team team) {
        return "Team: " +
                team.getTeamName() +
                "\n" +
                "Tier: " +
                team.getTeamTier() +
                " | " +
                "Rank: " +
                team.getTeamRank() +
                " | " +
                "W-L's: " +
                team.getTeamWins() +
                '-' +
                team.getTeamLosses();
    }

    private String createRosterBuilder(Team team) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Boolean> roster : team.getRoster().entrySet()) {
            if (roster.getValue()) {
                builder.append(CommonUtil.processString(roster.getKey()));
                builder.append(" :crown:");
                if (DiscordBot.theBot.staffManager.getStaff().containsKey(roster.getKey().toLowerCase())) {
                    builder.append(" ");
                    builder.append(DiscordBot.theBot.staffManager.getStaff().get(roster.getKey().toLowerCase()).getTag());
                }
                builder.append("\n");
            }


        }
        for (Map.Entry<String, Boolean> roster : team.getRoster().entrySet()) {
            if (!roster.getValue()) {
                builder.append(CommonUtil.processString(roster.getKey()));
                if (DiscordBot.theBot.staffManager.getStaff().containsKey(roster.getKey().toLowerCase())) {
                    builder.append(" ");
                    builder.append(DiscordBot.theBot.staffManager.getStaff().get(roster.getKey().toLowerCase()).getTag());
                }
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    @Override
    public String[] aliases() {
        return new String[]{"r"};
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.Roster;
    }

    @Override
    public String getCorrectCommandUsage() {
        return "!roster [command], roster [help]";
    }

    @Override
    public String getCommandUsage() {
        return "roster";
    }

    @Override
    public PermissionLevel getRequiredPermissionLevel() {
        return PermissionLevel.STANDARD;
    }
}
