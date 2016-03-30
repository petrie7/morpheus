package net.morpheus.domain;

import org.springframework.data.annotation.Id;

import java.util.Map;

public class Employee {

    @Id
    private final String username;

    private Role role;
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