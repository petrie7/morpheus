package net.morpheus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class Employee {

    @Id
    private final String id = UUID.randomUUID().toString();
    @JsonProperty
    private String username;
    @JsonProperty
    private Role role;
    @JsonProperty
    private ArrayList<Skill> skills;
    @JsonProperty
    private Level level;
    @JsonProperty
    private String lastUpdateDate;

    public static Employee manager(String username) {
        return new Employee(username, Role.Manager, new ArrayList<>(), Level.Manager);
    }

    public static Employee developer(String username, ArrayList<Skill> skills, Level level) {
        return new Employee(username, Role.Developer, skills, level);
    }

    public static Employee teamLead(String username, ArrayList<Skill> skills) {
        return new Employee(username, Role.TeamLead, skills, Level.SeniorDeveloper);
    }

    protected Employee(String username, Role role, ArrayList<Skill> skills, Level level) {
        this.username = username;
        this.role = role;
        this.skills = skills;
        this.level = level;
    }

    public String username() {
        return username;
    }

    public Role role() {
        return role;
    }

    public List<Skill> skills() {
        return skills;
    }

    public Level level() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public LocalDateTime date() {
        return LocalDateTime.parse(lastUpdateDate);
    }

    @Override
    public boolean equals(Object obj) {
        Employee employee = (Employee) obj;
        return employee.username().equals(this.username);
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.username);
        return builder.hashCode();
    }
}