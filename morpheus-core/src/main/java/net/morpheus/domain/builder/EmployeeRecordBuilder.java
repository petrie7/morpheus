package net.morpheus.domain.builder;

import net.morpheus.domain.EmployeeRecord;
import net.morpheus.domain.Level;
import net.morpheus.domain.Role;
import net.morpheus.domain.Skill;

import java.util.ArrayList;

import static net.morpheus.domain.Level.JuniorDeveloper;
import static net.morpheus.domain.Role.Developer;

public class EmployeeRecordBuilder {

    private String username;
    private Role role = Developer;
    private Level level = JuniorDeveloper;
    private boolean isWorkInProgress = false;
    private ArrayList<Skill> skills = new ArrayList<>();

    private EmployeeRecordBuilder() {
    }

    public static EmployeeRecordBuilder anEmployeeRecord() {
        return new EmployeeRecordBuilder();
    }

    public EmployeeRecordBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public EmployeeRecordBuilder withRole(Role role) {
        this.role = role;
        return this;
    }

    public EmployeeRecordBuilder withSkills(ArrayList<Skill> skills) {
        this.skills = skills;
        return this;
    }

    public EmployeeRecordBuilder withLevel(Level level) {
        this.level = level;
        return this;
    }

    public EmployeeRecordBuilder isWorkInProgress(boolean isWorkInProgress) {
        this.isWorkInProgress = isWorkInProgress;
        return this;
    }

    public EmployeeRecord build() {
        return new EmployeeRecord(username, role, skills, level, isWorkInProgress);
    }

}
