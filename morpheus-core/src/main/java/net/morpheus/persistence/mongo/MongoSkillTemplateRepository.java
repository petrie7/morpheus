package net.morpheus.persistence.mongo;

import net.morpheus.domain.Template;
import net.morpheus.persistence.SkillTemplateRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class MongoSkillTemplateRepository implements SkillTemplateRepository {

    private static final String TEMPLATE_COLLECTION = "template";
    private MongoTemplate mongoTemplate;

    public MongoSkillTemplateRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveOrUpdate(List<Template> templates) {
        templates.forEach(template -> mongoTemplate.save(template, TEMPLATE_COLLECTION));
    }

    @Override
    public Template findByTemplateName(String templateName) {
        return mongoTemplate.find(new Query(where("templateName").is(templateName)), Template.class, TEMPLATE_COLLECTION).get(0);
    }

    @Override
    public List<Template> readAll() {
        return mongoTemplate.findAll(Template.class, TEMPLATE_COLLECTION);
    }

    @Override
    public void deleteAll() {
        List<Template> templates = readAll();
        for (Template template : templates) {
            mongoTemplate.remove(new Query(where("templateName").is(template.templateName())), Template.class, TEMPLATE_COLLECTION);
        }
    }
}
