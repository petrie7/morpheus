package net.morpheus.domain.builder;

import net.morpheus.domain.LevelDescription;
import net.morpheus.domain.TemplateField;

import static java.util.Arrays.asList;
import static net.morpheus.domain.Level.*;

public class TemplateFieldBuilder {
    private String fieldName;
    private String juniorDescription;
    private String midDescription;
    private String seniorDescription;

    public static TemplateFieldBuilder templateField() {
        return new TemplateFieldBuilder();
    }

    public TemplateFieldBuilder fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public TemplateFieldBuilder descriptionForJunior(String juniorDescription) {
        this.juniorDescription = juniorDescription;
        return this;
    }

    public TemplateFieldBuilder descriptionForMid(String midDescription) {
        this.midDescription = midDescription;
        return this;
    }

    public TemplateFieldBuilder descriptionForSenior(String seniorDescription) {
        this.seniorDescription = seniorDescription;
        return this;
    }

    public TemplateField build() {

        return new TemplateField(
                fieldName,
                asList(
                        new LevelDescription(JuniorDeveloper, juniorDescription),
                        new LevelDescription(MidDeveloper, midDescription),
                        new LevelDescription(SeniorDeveloper, seniorDescription)
                )
        );
    }
}
