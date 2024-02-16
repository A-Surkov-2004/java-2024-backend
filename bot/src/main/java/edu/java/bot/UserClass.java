package edu.java.bot;

import java.util.HashSet;
import java.util.Set;

public class UserClass {
    public final static String DEFAULT_STATE = "default";
    private String state;
    private Set<String> links = new HashSet<>();

    public UserClass() {
        state = DEFAULT_STATE;
    }

    public void stateReset() {
        state = DEFAULT_STATE;
    }

    public void stateSet(String newState) {
        state = newState;
    }

    public String stateGet() {
        return state;
    }

    public void linkAdd(String link) {
        links.add(link);
    }

    public void linkRemove(String link) {
        links.remove(link);
    }

    public Set<String> linksGet() {
        return links;
    }
}
