package cdcl.bot.command.commands.general;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.util.CommonUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Links implements ICommand {

    private MessageEmbed embed;

    public Links() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Links:");
        builder.addField("Rulebook :", "https://bit.ly/2oPfzNR", false);
        //builder.addField("Standings/Blacklists :", "https://bit.ly/2wR2lUz", false);
        builder.addField("Team Registration :", "http://bit.ly/2NWRXSr", false);
        builder.addField("Host Application :", "http://bit.ly/2Nwfz38", false);
        builder.addField("Referee Application :", "http://bit.ly/2M8krqG", false);
        builder.addField("Media Application :", "http://bit.ly/2oTlTnI", false);
        builder.addField("Blacklist Appeal :", "http://bit.ly/2NY6A7Z", false);

        builder.setColor(DiscordBot.theBot.defaultColor);
        embed = builder.build();
    }

    @Override
    public void run(MessageReceivedEvent event) {
        try {
            event.getMember().getUser().openPrivateChannel().complete().sendMessage(embed).queue();
            event.getChannel().sendMessage(CommonUtil.getSuccessMessage()).queue();
        } catch (Exception e) {
            event.getChannel().sendMessage(embed).queue();
        }
    }

    @Override
    public String[] aliases() {
        return null;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.Misc;
    }

    @Override
    public String getCorrectCommandUsage() {
        return "!links";
    }

    @Override
    public String getCommandUsage() {
        return "links";
    }

    @Override
    public PermissionLevel getRequiredPermissionLevel() {
        return PermissionLevel.STANDARD;
    }
}
