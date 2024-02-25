package edu.java.configuration;

import edu.java.clients.GitHubClient;
import edu.java.clients.StackClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    private final static String GITHUB_DEFAULT_BASE_URL = "https://api.github.com/";
    private final static String STACK_DEFAULT_BASE_URL = "https://api.stackexchange.com/2.3";

    @Bean
    public GitHubClient gitHubClient() {
        GitHubClient ghc = new GitHubClient();
        ghc.setBaseURL(GITHUB_DEFAULT_BASE_URL);
        return ghc;
    }

    @Bean
    public StackClient stackClient() {
        StackClient sc = new StackClient();
        sc.setBaseURL(STACK_DEFAULT_BASE_URL);
        return sc;
    }
}
