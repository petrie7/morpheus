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

import static net.morpheus.domain.Level.SeniorDeveloper;
import static net.morpheus.domain.Role.TeamLead;
import static net.morpheus.domain.builder.EmployeeBuilder.anEmployee;

public class EmployeeDeserializer extends JsonDeserializer<EmployeeDetails> {
    @Override
    public EmployeeDetails deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        switch (Role.valueOf(node.get("role").textValue())) {
            case Developer:
                return anEmployee()
                        .withUsername(node.get("username").textValue())
                        .withLevel(Level.valueOf(node.get("level").textValue()))
                        .withTeam(new Team(node.get("team").asText()))
                        .build();
            case TeamLead:
                return anEmployee()
                        .withUsername(node.get("username").textValue())
                        .withRole(TeamLead)
                        .withLevel(SeniorDeveloper)
                        .withTeam(new Team(node.get("team").asText()))
                        .build();
            default:
                throw new IllegalArgumentException("Unrecognised role");
        }
    }
}
