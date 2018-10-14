package cdcl.bot.command.commands.roster;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.core.framework.Team;
import cdcl.bot.util.CommonUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class UpdateScore implements ICommand {

    @Override
    public void run(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().substring(1).split(" ");

        if(args.length != 3){
            event.getChannel().sendMessage(this.getCorrectCommandUsage()).queue();
            return;
        }

        if(!DiscordBot.theBot.rosterManager.isTeam(args[1], false)){
            event.getChannel().sendMessage("That is not a valid team!").queue();
            return;
        }

        int wins;
        int losses;

        try{
            String[] nums = args[2].split("-");
            wins = Integer.parseInt(nums[0]);
            losses = Integer.parseInt(nums[1]);
        }catch (NumberFormatException e){
            event.getChannel().sendMessage("Please specify a valid score (W-L)").queue();
            return;
        }

        Team toUpdate = DiscordBot.theBot.rosterManager.getTeamFromName(args[1], false);
        toUpdate.setTeamWins(wins);
        toUpdate.setTeamLosses(losses);
        event.getChannel().sendMessage(CommonUtil.getSuccessMessage()).queue();
    }

    @Override
    public String getCorrectCommandUsage() {
        return "!updatescore [team] [W-L]";
    }

    @Override
    public String getCommandUsage() {
        return "updatescore";
    }

    @Override
    public String[] aliases() {
        return new String[]{"changescore"};
    }

    @Override
    public PermissionLevel getRequiredPermissionLevel() {
        return PermissionLevel.MANAGEMENT;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.Roster;
    }
}
