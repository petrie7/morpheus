package net.morpheus.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import net.morpheus.domain.EmployeeRecord;
import net.morpheus.domain.Level;
import net.morpheus.domain.Role;
import net.morpheus.domain.Skill;

import java.io.IOException;
import java.util.ArrayList;

import static net.morpheus.domain.Level.SeniorDeveloper;
import static net.morpheus.domain.Role.TeamLead;
import static net.morpheus.domain.builder.EmployeeRecordBuilder.anEmployeeRecord;

public class EmployeeDeserializer extends JsonDeserializer<EmployeeRecord> {
    @Override
    public EmployeeRecord deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
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
                return anEmployeeRecord()
                        .withUsername(node.get("username").textValue())
                        .withLevel(Level.valueOf(node.get("level").textValue()))
                        .withSkills(skills)
                        .isWorkInProgress(node.get("isWorkInProgress") == null ? false : node.get("isWorkInProgress").asBoolean())
                        .build();
            case TeamLead:
                return anEmployeeRecord()
                        .withUsername(node.get("username").textValue())
                        .withRole(TeamLead)
                        .withLevel(SeniorDeveloper)
                        .withSkills(skills)
                        .isWorkInProgress(node.get("isWorkInProgress") == null ? false : node.get("isWorkingInProgress").asBoolean())
                        .build();
            case Manager:
                return anEmployeeRecord()
                        .withUsername(node.get("username").textValue())
                        .withLevel(Level.Manager)
                        .withRole(Role.Manager)
                        .build();
            default:
                throw new IllegalArgumentException("Unrecognised role");
        }
    }
}
