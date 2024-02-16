package edu.java.bot.CommandExecuters;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Set;
import static edu.java.bot.CommandReader.allExe;
import static edu.java.bot.UserDataMapClass.userData;




public class HelpCommandExecuter extends BasicCommandExecuter {
    public HelpCommandExecuter(String name, String description) {
        super(name, description);
    }

    @Override
    public SendMessage execute(Update update) {
        long id = update.message().chat().id();
        if (userData.containsKey(id)) {
            Set<BasicCommandExecuter> executers = allExe;
            StringBuilder commands = new StringBuilder();
            commands.append("Список доступных команд:\n");
            for (BasicCommandExecuter executer : executers) {
                commands.append(executer.getName());
                commands.append(" - ");
                commands.append(executer.getDescription());
                commands.append("\n");
            }
            reply = (new SendMessage(id, commands.toString()));
        } else {
            reply =
                (new SendMessage(id, "Вы не зарегестрированы в системе. Используйте команду /start, чтобы начать."));
        }
        return reply;
    }
}
