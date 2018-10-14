package cdcl.bot.core;

import cdcl.bot.command.CommandManager;
import cdcl.bot.command.commands.special.SpamManagement;
import cdcl.bot.core.listener.Listener;
import cdcl.bot.logger.Logger;
import cdcl.bot.moderation.ModerationManager;
import cdcl.bot.roster.RosterManager;
import cdcl.bot.roster.staff.StaffManager;
import cdcl.bot.thread.FileThread;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class DiscordBot {

    //Object Declarations
    public static final DiscordBot theBot = new DiscordBot();
    private final String testingToken = "StopTryingToFigureOutMyTokens ;)"
    private final String officialToken = "StopTryingToFigureOutMyTokens ;)";
    //Text Declarations
    public char prefix = '!';
    public String rootDir = File.separator + "root" + File.separator + "data" + File.separator;
    public String teamsDir = rootDir + File.separator + "teamlist" + File.separator;
    public RosterManager rosterManager = new RosterManager();
    public CommandManager cmdManager = new CommandManager();
    public StaffManager staffManager = new StaffManager();
    public File staffList = new File(rootDir + "staff.json");
    public Guild guild;
    public File teamList = new File(rootDir + "teams.json");
    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd*HH:mm:ss");
    public File muteList = new File(rootDir + "moderation" + File.separator + "muted.json");
    public ModerationManager moderationManager = new ModerationManager();
    public JSONParser jsonParser = new JSONParser();
    public FileThread thread = new FileThread();
    public File blacklistedFile = new File(rootDir + "blacklisted.json");
    public Logger logger = new Logger();
    public File facts = new File(rootDir + "facts.png");
    //Int
    public int defaultColor = 0x3ee8db;

    public static void main(String[] args) throws LoginException, IOException, ParseException {
        theBot.staffManager.loadStaffData();
        theBot.rosterManager.loadRoster();
        theBot.cmdManager.addMods();
        theBot.thread.start();
        JDABuilder bot = new JDABuilder();
        bot.addEventListener(new Listener());
        bot.addEventListener(theBot.logger);
        bot.setGame(Game.playing("Cake Wars Duos"));
        bot.setToken(theBot.testingToken);
        bot.build();
    }

}

