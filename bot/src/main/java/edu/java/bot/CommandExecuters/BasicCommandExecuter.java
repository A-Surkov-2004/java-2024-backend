package edu.java.bot.CommandExecuters;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public abstract class BasicCommandExecuter {
    private String name;
    private String description;
    protected SendMessage reply;

    public BasicCommandExecuter(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public SendMessage execute(Update update) {
        return reply;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
