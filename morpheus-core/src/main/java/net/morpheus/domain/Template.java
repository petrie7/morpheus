package net.morpheus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Template {

    @JsonProperty
    private List<TemplateField> fields;

    public Template(List<TemplateField> templateFields) {
        this.fields = templateFields;
    }

}
