package net.morpheus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TemplateField {
    @JsonProperty
    private String fieldName;
    @JsonProperty
    private List<LevelDescription> fieldLevelDescription;

    public TemplateField(String fieldName, List<LevelDescription> levelDescriptions) {
        this.fieldName = fieldName;
        this.fieldLevelDescription = levelDescriptions;
    }

    public TemplateField() {
        //for serialization
    }
}
