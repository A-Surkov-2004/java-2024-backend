package edu.java.clients;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

public class GitHubClient {

    private RepositoryService service;

    public JsonObject getInfo(String owner, String repo) {

        return JsonParser.parseString(service.getRepository(owner, repo))
            .getAsJsonObject();
    }

    public void setBaseURL(String baseUrl) {
        WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        service = factory.createClient(RepositoryService.class);
    }

    private interface RepositoryService {
        @GetExchange("/repos/{owner}/{repo}")
        String getRepository(@PathVariable String owner, @PathVariable String repo);

    }
}
