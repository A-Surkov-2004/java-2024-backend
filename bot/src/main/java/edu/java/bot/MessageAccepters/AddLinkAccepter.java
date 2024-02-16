package edu.java.bot.MessageAccepters;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import static edu.java.bot.UserDataMapClass.userData;

public class AddLinkAccepter extends BasicAccepter {

    public AddLinkAccepter(String requiredState) {
        super(requiredState);
    }

    @Override
    public SendMessage accept(Update update) {
        long id = update.message().chat().id();
        String message = update.message().text();
        if (!checkLink(message)) {
            reply = (new SendMessage(id, "Ссылка недействительна. Попробуйте ещё раз"));
        } else {
            userData.get(id).stateReset();
            userData.get(id).linkAdd(message);
            reply = (new SendMessage(id, "Ссылка была успешно добавлена!"));
        }
        return reply;
    }

    private boolean checkLink(String link) {
        return true;
    }
}
