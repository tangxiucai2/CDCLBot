package cdcl.bot.command.commands.general;

import cdcl.bot.core.DiscordBot;
import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import cdcl.bot.util.CommonUtil;
import cdcl.bot.util.ZipUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Backup implements ICommand {


    @Override
    public void run(MessageReceivedEvent event) {
        try {
            File f = new File(DiscordBot.theBot.rootDir + "temp" + File.separator + "backup.zip");
            ZipUtil.createZipFile(new File(DiscordBot.theBot.rootDir).toPath(), f.toPath());
            Thread.sleep(100);
            event.getMember().getUser().openPrivateChannel().complete().sendFile(f).queue();
            Thread.sleep(100);
            Files.delete(f.toPath());
            event.getChannel().sendMessage(CommonUtil.getSuccessMessage()).queue();
        } catch (IOException | InterruptedException e) {
            CommonUtil.sendIOError(e, event.getChannel());
        }
    }

    @Override
    public String getCorrectCommandUsage() {
        return "!backup";
    }

    @Override
    public String getCommandUsage() {
        return "backup";
    }

    @Override
    public String[] aliases() {
        return null;
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
