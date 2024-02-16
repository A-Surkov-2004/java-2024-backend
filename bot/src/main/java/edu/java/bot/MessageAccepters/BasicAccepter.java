package edu.java.bot.MessageAccepters;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public abstract class BasicAccepter {
    private String requiredState;
    protected SendMessage reply;

    public BasicAccepter(String requiredState) {
        this.requiredState = requiredState;
    }

    public SendMessage accept(Update update) {
        return reply;
    }

    public String getRequiredState() {
        return requiredState;
    }
}
