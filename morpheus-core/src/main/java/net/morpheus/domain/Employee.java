package net.morpheus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Employee {

    @Id
    @JsonProperty
    private final String username;
    @JsonProperty
    private Role role;
    @JsonProperty
    private ArrayList<Skill> skills = new ArrayList<>();

    public Employee(String username, Role role, ArrayList<Skill> skills) {
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

    public List<Skill> skills() {
        return skills;
    }

    public void addNewSkill(Skill... listOfSkills) {
        Collections.addAll(skills, listOfSkills);
    }
}