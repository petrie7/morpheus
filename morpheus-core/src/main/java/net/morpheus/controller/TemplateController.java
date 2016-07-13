package net.morpheus.controller;

import net.morpheus.domain.Template;
import net.morpheus.persistence.SkillTemplateRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/template")
public class TemplateController {

    @Resource
    private SkillTemplateRepository skillTemplateRepository;

    @RequestMapping(method = POST)
    public void saveNewTemplates(@RequestBody List<Template> templates) {
        skillTemplateRepository.persist(templates);
    }

    @RequestMapping(method = GET)
    public List<Template> template() {
        return skillTemplateRepository.readAll();
    }
}
