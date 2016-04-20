package net.morpheus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Employee {

    @Id
    @JsonProperty
    private String username;
    @JsonProperty
    private Role role;
    @JsonProperty
    private ArrayList<Skill> skills;
    @JsonProperty
    private Level level;

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

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Skill> skills() {
        return skills;
    }

    public void addNewSkill(Skill... listOfSkills) {
        Collections.addAll(skills, listOfSkills);
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}