package edu.java;


import com.google.gson.JsonObject;
import edu.java.clients.GitHubClient;
import edu.java.clients.StackClient;
import edu.java.configuration.ClientConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;

class MyServiceTest {
    @Test
    void test1(){
        //given
        ApplicationContext context = new AnnotationConfigApplicationContext(ClientConfiguration.class);
        GitHubClient ghc = context.getBean(GitHubClient.class);

        //when
        JsonObject js = ghc.getInfo("A-Surkov-2004", "C-coding");

        //then
        assertThat(js.get("owner").getAsJsonObject().get("login").getAsString()).isEqualTo("A-Surkov-2004");

    }

    @Test
    void test2(){
        //given
        ApplicationContext context = new AnnotationConfigApplicationContext(ClientConfiguration.class);
        StackClient sc = context.getBean(StackClient.class);

        //when
        JsonObject js = sc.getInfo("275944");

        //then
        assertThat(js.get("items").getAsJsonArray().get(0).getAsJsonObject().get("answer_count").getAsInt()).isEqualTo(48);

    }
}
