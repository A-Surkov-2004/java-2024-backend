package edu.java.clients;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

public class StackClient {

    private String baseUrl;

    private Service service;

    public JsonObject getInfo(String id) {

        return JsonParser.parseString(service.getInfo(id))
            .getAsJsonObject();
    }

    public void setBaseURL(String baseUrl) {
        this.baseUrl = baseUrl;
        WebClient webClient = WebClient.builder().baseUrl(this.baseUrl).build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        service = factory.createClient(Service.class);
    }

    interface Service {
        @GetExchange("/questions/{id}?order=desc&sort=activity&site=stackoverflow")
        String getInfo(@PathVariable String id);
    }
}
