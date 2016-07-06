package net.morpheus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MorpheusError {
    @JsonProperty
    private String message;

    public MorpheusError(String message) {
        this.message = message;
    }
}
