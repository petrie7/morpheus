package net.morpheus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.util.Objects;

public class EmployeeDetails {

    @Id
    @JsonProperty
    private String username;
    @JsonProperty
    private Level level;
    @JsonProperty
    private Role role;
    @JsonProperty
    private Team team;

    public EmployeeDetails(String username, Level level, Role role, Team team) {
        this.username = username;
        this.level = level;
        this.role = role;
        this.team = team;
    }

    public String username() {
        return username;
    }

    public Level level() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Role role() {
        return role;
    }

    public Team team() {
        return team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDetails employee = (EmployeeDetails) o;
        return Objects.equals(username, employee.username) &&
                level == employee.level &&
                role == employee.role &&
                team == employee.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, level, role, team);
    }
}
