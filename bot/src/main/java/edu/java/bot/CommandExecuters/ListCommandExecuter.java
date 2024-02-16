package edu.java.bot.CommandExecuters;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import static edu.java.bot.UserDataMapClass.userData;

public class ListCommandExecuter extends BasicCommandExecuter {
    public ListCommandExecuter(String name, String description) {
        super(name, description);
    }

    @Override
    public SendMessage execute(Update update) {
        long id = update.message().chat().id();
        if (!userData.containsKey(id)) {
            reply =
                (new SendMessage(id, "Вы не зарегестрированы в системе. Используйте команду /start, чтобы начать."));
        } else if (userData.get(id).linksGet().isEmpty()) {
            reply = (new SendMessage(
                id,
                "У вас ещё нет привязанных ссылок. Используйте команду /add чтобы привязать ресурс"
            ));
        } else {
            StringBuilder links = new StringBuilder();
            links.append("Ваши привязанные ресурсы:\n");
            for (String link : userData.get(id).linksGet()) {
                links.append(link);
                links.append("\n");
            }
            reply = (new SendMessage(id, links.toString()));
        }
        return reply;
    }
}
