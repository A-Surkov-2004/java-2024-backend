package edu.java.bot.CommandExecuters;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.UserClass;
import static edu.java.bot.UserDataMapClass.userData;

public class StartCommandExecuter extends BasicCommandExecuter {

    public StartCommandExecuter(String name, String description) {
        super(name, description);
    }

    @Override
    public SendMessage execute(Update update) {
        long id = update.message().chat().id();
        if (userData.containsKey(id)) {
            reply = new SendMessage(id, "Вы уже зарегестрированы в системе.");
        } else {
            userData.put(id, new UserClass());
            reply = (new SendMessage(id, "Привет!\n"
                + "Этот телеграм-бот предназначен для оповещения пользователя о появлении новой информации"
                + " на его любимых ресурсах. Используй команду /help, чтобы узнать о доступных командах."));
        }
        return reply;
    }
}
