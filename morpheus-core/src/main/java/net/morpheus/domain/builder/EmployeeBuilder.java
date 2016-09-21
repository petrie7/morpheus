package net.morpheus.domain.builder;

import net.morpheus.domain.EmployeeDetails;
import net.morpheus.domain.Level;
import net.morpheus.domain.Role;
import net.morpheus.domain.Team;

public class EmployeeBuilder {

    private String username;
    private Role role = Role.Developer;
    private Level level = Level.JuniorDeveloper;
    private Team team = null;
    private boolean isArchived = false;

    public static EmployeeBuilder anEmployee() {
        return new EmployeeBuilder();
    }

    public EmployeeBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public EmployeeBuilder withRole(Role role) {
        this.role = role;
        return this;
    }

    public EmployeeBuilder withLevel(Level level) {
        this.level = level;
        return this;
    }

    public EmployeeBuilder withTeam(Team team) {
        this.team = team;
        return this;
    }

    public EmployeeBuilder withIsArchived(boolean isArchived) {
        this.isArchived = isArchived;
        return this;
    }

    public EmployeeDetails build() {
        return new EmployeeDetails(username, level, role, team, isArchived);
    }
}
