package net.morpheus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Template {

    @JsonProperty
    private final String templateName;
    @JsonProperty
    private final String templateClass;
    @JsonProperty
    private final List<TemplateField> fields;

    public Template(String templateName, List<TemplateField> templateFields) {
        this.templateName = templateName;
        this.fields = templateFields;
        this.templateClass = templateName.replaceAll(" ", "-").toLowerCase();
    }

}
