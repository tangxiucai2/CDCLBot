package cdcl.bot.thread;

import cdcl.bot.core.framework.ICommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandThread extends Thread {

    private ICommand command;
    private MessageReceivedEvent event;

    public CommandThread(ICommand command, MessageReceivedEvent event) {
        this.command = command;
        this.event = event;
    }

    @Override
    public void run() {
        command.run(event);
        super.run();
    }
}
