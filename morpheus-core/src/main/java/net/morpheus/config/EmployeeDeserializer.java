package net.morpheus.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import net.morpheus.domain.Employee;
import net.morpheus.domain.Level;
import net.morpheus.domain.Role;
import net.morpheus.domain.Skill;

import java.io.IOException;
import java.util.ArrayList;

public class EmployeeDeserializer extends JsonDeserializer<Employee> {
    @Override
    public Employee deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ArrayList<Skill> skills = new ArrayList<>();

        if (node.has("skills")) {
            JsonNode skillsNode = node.get("skills");
            for (JsonNode jsonNode : skillsNode) {
                skills.add(
                        new Skill(
                                jsonNode.get("description").textValue(),
                                jsonNode.get("value").asText().equals("null") ? null : jsonNode.get("value").intValue(),
                                jsonNode.get("comment").textValue())
                );
            }
        }

        switch (Role.valueOf(node.get("role").textValue())) {
            case Developer:
                return Employee.developer(
                        node.get("username").textValue(),
                        skills,
                        Level.valueOf(node.get("level").textValue())
                );
            case TeamLead:
                return Employee.teamLead(
                        node.get("username").textValue(),
                        skills
                );
            case Manager:
                return Employee.manager(
                        node.get("username").textValue()
                );
            default:
                throw new IllegalArgumentException("Unrecognised role");
        }
    }
}
