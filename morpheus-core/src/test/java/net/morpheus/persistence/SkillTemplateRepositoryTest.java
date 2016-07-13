package net.morpheus.persistence;

import net.morpheus.domain.Level;
import net.morpheus.domain.LevelDescription;
import net.morpheus.domain.Template;
import net.morpheus.domain.TemplateField;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SkillTemplateRepositoryTest extends AbstractRepositoryTestCase {

    @Test
    public void canPersistTemplate() {
        Template expectedTemplate = makeATemplateWithName("Some Template Title");

        skillTemplateRepository.persist(singletonList(expectedTemplate));

        Template actualTemplate = skillTemplateRepository.findByTemplateName(expectedTemplate.templateName());

        assertThat(actualTemplate.templateName(), is(expectedTemplate.templateName()));
    }

    @Test
    public void canDeleteTemplates() {
        Template template1 = makeATemplateWithName("Some Template Title");
        Template template2 = makeATemplateWithName("Some Other Template Title");

        skillTemplateRepository.persist(asList(template1, template2));
        skillTemplateRepository.deleteAll();

        List<Template> result = skillTemplateRepository.readAll();

        assertThat(result.size(), is(0));
    }

    private Template makeATemplateWithName(String templateName) {
        ArrayList<LevelDescription> levelDescriptions = new ArrayList<>();
        levelDescriptions.add(new LevelDescription(Level.JuniorDeveloper, "Desc"));
        return new Template(templateName, singletonList(
                new TemplateField("Some Field Name", levelDescriptions)));
    }
}
