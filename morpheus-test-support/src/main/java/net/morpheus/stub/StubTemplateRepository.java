package net.morpheus.stub;

import net.morpheus.domain.Template;
import net.morpheus.persistence.SkillTemplateRepository;

import java.util.ArrayList;
import java.util.List;

public class StubTemplateRepository implements SkillTemplateRepository {

    private List<Template> templates = new ArrayList<>();

    @Override
    public void saveOrUpdate(List<Template> templates) {
        this.templates = templates;
    }

    @Override
    public Template findByTemplateName(String templateName) {
        return templates.stream().filter(template -> template.templateName().equals(templateName)).findFirst().get();
    }

    @Override
    public List<Template> readAll() {
        return templates;
    }

    @Override
    public void deleteAll() {
        templates.clear();
    }
}
