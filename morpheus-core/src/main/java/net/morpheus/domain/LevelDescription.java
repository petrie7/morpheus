package net.morpheus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LevelDescription {
    @JsonProperty
    private Level level;
    @JsonProperty
    private String description;

    public LevelDescription(Level level, String description) {
        this.level = level;
        this.description = description;
    }

    public LevelDescription() {
        //for serialization
    }
}
