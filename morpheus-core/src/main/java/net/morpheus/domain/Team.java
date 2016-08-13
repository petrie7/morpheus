package net.morpheus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Team {

    @JsonProperty
    private final String name;

    public Team(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }
}
