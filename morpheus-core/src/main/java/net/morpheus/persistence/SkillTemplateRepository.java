package net.morpheus.persistence;

import net.morpheus.domain.Template;

import java.util.List;

public interface SkillTemplateRepository {
    void saveOrUpdate(List<Template> templates);

    Template findByTemplateName(String templateName);

    List<Template> readAll();

    void deleteAll();
}
