package net.morpheus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.util.List;

public class Template {

    @JsonProperty
    @Id
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

    public void updateFields(List<TemplateField> fields) {
        this.fields = fields;
    }

    public List<TemplateField> fields() {
        return fields;
    }
}
