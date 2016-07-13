package net.morpheus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Template {

    @JsonProperty
    private String templateName;
    @JsonProperty
    private String templateClass;
    @JsonProperty
    private List<TemplateField> fields;

    public Template(String templateName, List<TemplateField> templateFields) {
        this.templateName = templateName;
        this.fields = templateFields;
        this.templateClass = templateName.replaceAll(" ", "-").toLowerCase();
    }

    public Template() {
        //for serialization
    }

    public String templateName() {
        return templateName;
    }

}
