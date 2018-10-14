package cdcl.bot.command;

import cdcl.bot.command.commands.general.*;
import cdcl.bot.command.commands.moderation.Ban;
import cdcl.bot.command.commands.moderation.Mute;
import cdcl.bot.command.commands.moderation.TempMute;
import cdcl.bot.command.commands.moderation.UnMute;
import cdcl.bot.command.commands.roster.*;
import cdcl.bot.command.commands.special.AsycSpecial;
import cdcl.bot.command.commands.special.Facts;
import cdcl.bot.command.commands.special.Modify;
import cdcl.bot.command.commands.special.SpamManagement;
import cdcl.bot.core.framework.ICommand;

import java.util.ArrayList;

public class CommandManager {

    private ArrayList<ICommand> commands = new ArrayList<>();

    public void addMods() {
        commands.add(new Roster());
        commands.add(new Player());
        commands.add(new CreateTeam());
        commands.add(new RemoveTeam());
        commands.add(new RenameTeam());
        commands.add(new AddPlayer());
        commands.add(new RemovePlayer());
        commands.add(new AddLeader());
        commands.add(new RemoveLeader());
        commands.add(new UpdateRank());
        commands.add(new UpdateTier());
        commands.add(new UpdateScore());

        commands.add(new AddStaff());
        commands.add(new RemoveStaff());

        commands.add(new Links());
        commands.add(new Help());

        commands.add(new Ban());
        commands.add(new Mute());
        commands.add(new TempMute());
        commands.add(new UnMute());
        commands.add(new Blacklist());

        //commands.add(new GetRoleID());

        commands.add(new AsycSpecial());
        commands.add(new SpamManagement());

        //commands.add(new Backup());

        commands.add(new SafeShutdown());

        commands.add(new Say());

        commands.add(new Purge());

        //commands.add(new SetLoggingChannel());

        commands.add(new Modify());

        commands.add(new CoinFlip());

        commands.add(new Facts());
    }

    public ArrayList<ICommand> getCommands() {
        return commands;
    }

    public ICommand getCommandFromName(String commandName) {
        for (ICommand command : commands) {
            if (command.getCommandUsage().equalsIgnoreCase(commandName)) {
                return command;
            }
            if (command.aliases() != null) {
                for (String s : command.aliases()) {
                    if (s.equalsIgnoreCase(commandName)) {
                        return command;
                    }
                }
            }
        }
        throw new NullPointerException();
    }
}
