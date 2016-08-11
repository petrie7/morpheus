package net.morpheus.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import net.morpheus.domain.EmployeeRecord;
import net.morpheus.domain.Skill;

import java.io.IOException;
import java.util.ArrayList;

import static net.morpheus.domain.builder.EmployeeRecordBuilder.anEmployeeRecord;

public class EmployeeRecordDeserializer extends JsonDeserializer<EmployeeRecord> {
    @Override
    public EmployeeRecord deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
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

        return anEmployeeRecord()
                .withUsername(node.get("username").asText())
                .withSkills(skills)
                .isWorkInProgress(node.get("isWorkInProgress") == null ? false : node.get("isWorkInProgress").asBoolean())
                .build();
    }
}
