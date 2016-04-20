package net.morpheus.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import net.morpheus.domain.Employee;
import net.morpheus.domain.Role;
import net.morpheus.domain.Skill;

import java.io.IOException;
import java.util.ArrayList;

public class EmployeeDeserializer extends JsonDeserializer<Employee> {
    @Override
    public Employee deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ArrayList<Skill> skills = new ArrayList<>();

        JsonNode skillsNode = node.get("skills");
        for (JsonNode jsonNode : skillsNode) {
            skills.add(new Skill(jsonNode.get("description").textValue(), jsonNode.get("value").intValue()));
        }

        return new Employee(
                node.get("username").textValue(),
                Role.valueOf(node.get("role").textValue()),
                skills
        );
    }
}
