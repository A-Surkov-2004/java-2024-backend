package edu.java.bot;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.CommandExecuters.AddCommandExecuter;
import edu.java.bot.CommandExecuters.BasicCommandExecuter;
import edu.java.bot.CommandExecuters.HelpCommandExecuter;
import edu.java.bot.CommandExecuters.ListCommandExecuter;
import edu.java.bot.CommandExecuters.RemoveCommandExecuter;
import edu.java.bot.CommandExecuters.StartCommandExecuter;
import edu.java.bot.MessageAccepters.AddLinkAccepter;
import edu.java.bot.MessageAccepters.BasicAccepter;
import edu.java.bot.MessageAccepters.RemoveLinkAccepter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static edu.java.bot.BotApplication.bot;
import static edu.java.bot.UserDataMapClass.userData;

public class CommandReader {
    public static Set<BasicCommandExecuter> allExe = new HashSet<>();
    public static Set<BasicAccepter> allAcc = new HashSet<>();
    private final static Logger LOGGER = LogManager.getLogger();

    public CommandReader() {
        allExe.add(new StartCommandExecuter("/start", "Start conversation with bot"));
        allExe.add(new HelpCommandExecuter("/help", "Get list of commands"));
        allExe.add(new ListCommandExecuter("/list", "View tracking links"));
        allExe.add(new AddCommandExecuter("/track", "Add link to tracking system"));
        allExe.add(new RemoveCommandExecuter("/untrack", "Remove link from tracking system"));

        allAcc.add(new AddLinkAccepter(AddCommandExecuter.GIVEN_STATE));
        allAcc.add(new RemoveLinkAccepter(RemoveCommandExecuter.GIVEN_STATE));

        publishCommands(allExe);
    }

    public void read(Update update) {

        String message = update.message().text();
        long id = update.message().chat().id();
        if (!userData.containsKey(id) || Objects.equals(userData.get(id).stateGet(), UserClass.DEFAULT_STATE)) {
            boolean commandFound = false;
            for (BasicCommandExecuter command : allExe) {
                if (Objects.equals(message, command.getName())) {
                    commandFound = true;
                    SendMessage response = command.execute(update);
                    bot.execute(response);
                }
            }
            if (!commandFound) {
                bot.execute(new SendMessage(

                    id,
                    "Команда не распознана. Используйте команду /help для получения списка допустимых команд"
                ));
            }
        } else {
            for (BasicAccepter accepter : allAcc) {
                if (Objects.equals(userData.get(id).stateGet(), accepter.getRequiredState())) {
                    SendMessage response = accepter.accept(update);
                    bot.execute(response);
                }
            }
        }
    }

    public Set<BasicCommandExecuter> getExecuters() {
        return allExe;
    }

    private void publishCommands(Set<BasicCommandExecuter> allExe) {

        BotCommand[] botCommands = new BotCommand[allExe.size()];
        int i = 0;
        for (BasicCommandExecuter executer : allExe) {
            botCommands[i] = new BotCommand(executer.getName(), executer.getDescription());
            i++;

        }
        SetMyCommands commandsUpdate = new SetMyCommands(botCommands);
        bot.execute(commandsUpdate);
    }

}
