package cdcl.bot.command.commands.general;

import cdcl.bot.core.framework.CommandCategory;
import cdcl.bot.core.framework.ICommand;
import cdcl.bot.core.framework.PermissionLevel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Random;

public class CoinFlip implements ICommand {

    @Override
    public void run(MessageReceivedEvent event) {
        Random random = new Random(event.getMessageIdLong());
        int num = random.nextInt(2);

        event.getChannel().sendMessage(num == 1 ? "The coin landed on heads!" : "The coin landed on tails!").queue();
    }

    @Override
    public String getCorrectCommandUsage() {
        return "!coinflip";
    }

    @Override
    public String getCommandUsage() {
        return "coinflip";
    }

    @Override
    public String[] aliases() {
        return new String[]{"flipcoin", "tosscoin"};
    }

    @Override
    public PermissionLevel getRequiredPermissionLevel() {
        return PermissionLevel.STANDARD;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.Misc;
    }
}
