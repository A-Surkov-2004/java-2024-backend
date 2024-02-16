package edu.java.bot.MessageAccepters;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import static edu.java.bot.UserDataMapClass.userData;

public class RemoveLinkAccepter extends BasicAccepter {

    public RemoveLinkAccepter(String requiredState) {
        super(requiredState);
    }

    @Override
    public SendMessage accept(Update update) {
        long id = update.message().chat().id();
        String message = update.message().text();
        if (!checkLink(message, id)) {
            reply = (new SendMessage(id, "Вы не были подписаны на этот ресурс"));
        } else {
            userData.get(id).linkRemove(message);
            reply = (new SendMessage(id, "Ссылка удалена из вашего списка"));
        }
        userData.get(id).stateReset();
        return reply;
    }

    private boolean checkLink(String link, long id) {
        return userData.get(id).linksGet().contains(link);
    }
}
