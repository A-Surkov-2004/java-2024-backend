package edu.java.bot.CommandExecuters;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import static edu.java.bot.UserDataMapClass.userData;

public class AddCommandExecuter extends BasicCommandExecuter {

    public final static String GIVEN_STATE = "adding link";

    public AddCommandExecuter(String name, String description) {
        super(name, description);
    }

    @Override
    public SendMessage execute(Update update) {
        long id = update.message().chat().id();
        if (!userData.containsKey(id)) {
            reply = new SendMessage(
                id,
                "Вы не зарегестрированы в системе. Используйте команду /start, чтобы начать."
            );
        } else {
            userData.get(id).stateSet(GIVEN_STATE);
            reply = (new SendMessage(id, "Введите ссылку на ресурс, который хотите зерегестрировать в системе"));
        }
        return this.reply;
    }
}
