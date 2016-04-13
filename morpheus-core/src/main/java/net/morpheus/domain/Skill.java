package net.morpheus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Skill {
    @JsonProperty
    private String description;
    @JsonProperty
    private Integer value;

    public Skill(String description, Integer value) {
        this.description = description;
        this.value = value;
    }

    public String description() {
        return description;
    }

    public Integer value() {
        return value;
    }
}
