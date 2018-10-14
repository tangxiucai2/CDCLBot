package cdcl.bot.command.commands.special;

import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class AsycSpecial implements ICommand {

    @Override
    public void run(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        Message message = event.getChannel().sendMessage("Logging in").complete();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!event.getMember().getUser().getId().equalsIgnoreCase("282253323006902282")) {
            event.getChannel().editMessageById(message.getId(), "Login Failed!").queue();
            return;
        }
        event.getChannel().editMessageById(message.getId(), "Login Successful!").queue();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        event.getChannel().sendMessage("Welcome Developer {" + event.getMember().getEffectiveName() + '}').queue();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Developer Panel : ");
        builder.addField("Options", "Destroy Discord Server\n Ban all management, \n Mute all management, \n remove all teams, \n Give management a high five, \n givemyselfmod", true);
        event.getChannel().sendMessage(builder.build()).queue();
    }

    @Override
    public String getCorrectCommandUsage() {
        return "devpanel";
    }

    @Override
    public String getCommandUsage() {
        return "devpanel";
    }

    @Override
    public String[] aliases() {
        return new String[]{"devlogin", "pissoffmicheal"};
    }

    @Override
    public PermissionLevel getRequiredPermissionLevel() {
        return PermissionLevel.MANAGEMENT;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.NONE;
    }
}
