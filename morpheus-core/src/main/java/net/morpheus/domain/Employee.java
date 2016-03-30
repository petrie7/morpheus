package net.morpheus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.util.Map;

public class Employee {

    @Id
    @JsonProperty
    private final String username;
    @JsonProperty
    private Role role;
    @JsonProperty
    private Map<String, Integer> skills;

    public Employee(String username, Role role, Map<String, Integer> skills) {
        this.username = username;
        this.role = role;
        this.skills = skills;
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

    public Map<String, Integer> skills() {
        return skills;
    }

    public void addNewSkill(String skill, Integer score) {
        skills.put(skill, score);
    }
}