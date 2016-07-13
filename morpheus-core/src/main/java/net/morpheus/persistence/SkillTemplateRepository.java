package net.morpheus.persistence;

import net.morpheus.domain.Template;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class SkillTemplateRepository {

    private static final String TEMPLATE_COLLECTION = "template";
    private MongoTemplate mongoTemplate;

    public SkillTemplateRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void persist(List<Template> templates) {
        mongoTemplate.insert(templates, TEMPLATE_COLLECTION);
    }

    public Template findByTemplateName(String templateName) {
        return mongoTemplate.find(new Query(where("templateName").is(templateName)), Template.class, TEMPLATE_COLLECTION).get(0);
    }

    public List<Template> readAll() {
        return mongoTemplate.findAll(Template.class, TEMPLATE_COLLECTION);
    }

    public void deleteAll() {
        List<Template> templates = readAll();
        for (Template template : templates) {
            mongoTemplate.remove(new Query(where("templateName").is(template.templateName())), Template.class, TEMPLATE_COLLECTION);
        }
    }
}
