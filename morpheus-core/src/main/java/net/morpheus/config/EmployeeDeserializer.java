package net.morpheus.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import net.morpheus.domain.EmployeeDetails;
import net.morpheus.domain.Level;
import net.morpheus.domain.Role;
import net.morpheus.domain.Team;

import java.io.IOException;

import static net.morpheus.domain.builder.EmployeeBuilder.anEmployee;

public class EmployeeDeserializer extends JsonDeserializer<EmployeeDetails> {
    @Override
    public EmployeeDetails deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Role role = Role.valueOf(node.get("role").textValue());
        Team team = new Team((node.has("team") && !node.get("team").has("name")) ? "" : node.get("team").get("name").asText());
        Level level = Level.valueOf(node.get("level").textValue());
        String username = node.get("username").asText();

        return anEmployee()
                .withUsername(username)
                .withLevel(level)
                .withTeam(team)
                .withRole(role)
                .build();
    }
}
