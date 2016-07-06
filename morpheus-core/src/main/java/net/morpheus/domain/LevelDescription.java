package net.morpheus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LevelDescription {
    @JsonProperty
    private final Level level;
    @JsonProperty
    private final String description;

    public LevelDescription(Level level, String description) {
        this.level = level;
        this.description = description;
    }
}
