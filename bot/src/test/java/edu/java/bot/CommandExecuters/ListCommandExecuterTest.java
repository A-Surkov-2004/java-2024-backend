package edu.java.bot.CommandExecuters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.MessageAccepters.AddLinkAccepter;
import edu.java.bot.MessageAccepters.RemoveLinkAccepter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ListCommandExecuterTest {

    private final static Logger LOGGER = LogManager.getLogger();

    @Test
    @DisplayName("До регистрации")
    void test1() {
        // given
        ListCommandExecuter list = new ListCommandExecuter("", "");
        Update mock = Mockito.mock(Update.class);
        Mockito.when(mock.message()).thenReturn(Mockito.mock(Message.class));
        Mockito.when(mock.message().chat()).thenReturn(Mockito.mock(Chat.class));
        Mockito.when(mock.message().chat().id()).thenReturn(12L);

        // when
        SendMessage reply = list.execute(mock);

        // then
        assertThat(reply.getParameters().get("text"))
            .isEqualTo("Вы не зарегестрированы в системе. Используйте команду /start, чтобы начать.");
    }
    @Test
    @DisplayName("Сразу после регистрации")
    void test2() {
        // given
        ListCommandExecuter list = new ListCommandExecuter("", "");
        StartCommandExecuter start = new StartCommandExecuter("", "");
        Update mock = Mockito.mock(Update.class);
        Mockito.when(mock.message()).thenReturn(Mockito.mock(Message.class));
        Mockito.when(mock.message().chat()).thenReturn(Mockito.mock(Chat.class));
        Mockito.when(mock.message().chat().id()).thenReturn(42L);

        // when
        start.execute(mock);
        SendMessage reply = list.execute(mock);

        // then
        assertThat(reply.getParameters().get("text"))
            .isEqualTo("У вас ещё нет привязанных ссылок. Используйте команду /add чтобы привязать ресурс");
    }

    @Test
    @DisplayName("Добавлена первая ссылка")
    void test3() {
        // given
        ListCommandExecuter list = new ListCommandExecuter("", "");
        StartCommandExecuter start = new StartCommandExecuter("", "");
        AddCommandExecuter addExe = new AddCommandExecuter("", "");

        AddLinkAccepter addAcc = new AddLinkAccepter("");


        Update mock = Mockito.mock(Update.class);
        Mockito.when(mock.message()).thenReturn(Mockito.mock(Message.class));
        Mockito.when(mock.message().chat()).thenReturn(Mockito.mock(Chat.class));
        Mockito.when(mock.message().chat().id()).thenReturn(42L);
        Mockito.when(mock.message().text()).thenReturn("https://www.youtube.com/@RickAstleyYT");

        // when
        start.execute(mock);
        addExe.execute(mock);
        addAcc.accept(mock);
        SendMessage reply = list.execute(mock);

        // then
        assertThat(reply.getParameters().get("text"))
            .isEqualTo("Ваши привязанные ресурсы:\n" +
                "https://www.youtube.com/@RickAstleyYT\n");
    }
    @Test
    @DisplayName("Ссылка была удалена")
    void test4() {
        // given
        ListCommandExecuter list = new ListCommandExecuter("", "");
        StartCommandExecuter start = new StartCommandExecuter("", "");
        AddCommandExecuter addExe = new AddCommandExecuter("", "");
        AddLinkAccepter addAcc = new AddLinkAccepter("");
        RemoveCommandExecuter removeExe = new RemoveCommandExecuter("", "");
        RemoveLinkAccepter removeAcc = new RemoveLinkAccepter("");


        Update mock = Mockito.mock(Update.class);
        Mockito.when(mock.message()).thenReturn(Mockito.mock(Message.class));
        Mockito.when(mock.message().chat()).thenReturn(Mockito.mock(Chat.class));
        Mockito.when(mock.message().chat().id()).thenReturn(42L);
        Mockito.when(mock.message().text()).thenReturn("https://www.youtube.com/@RickAstleyYT");

        // when
        start.execute(mock);
        addExe.execute(mock);
        addAcc.accept(mock);
        removeAcc.accept(mock);
        SendMessage reply = list.execute(mock);

        // then
        assertThat(reply.getParameters().get("text"))
            .isEqualTo("У вас ещё нет привязанных ссылок. Используйте команду /add чтобы привязать ресурс");
    }

}
