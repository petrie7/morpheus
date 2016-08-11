package net.morpheus.domain.builder;

import net.morpheus.domain.EmployeeRecord;
import net.morpheus.domain.Skill;

import java.util.ArrayList;

public class EmployeeRecordBuilder {

    private boolean isWorkInProgress = false;
    private ArrayList<Skill> skills = new ArrayList<>();
    private String lastUpdateDate = null;
    private String username;

    private EmployeeRecordBuilder() {
    }

    public static EmployeeRecordBuilder anEmployeeRecord() {
        return new EmployeeRecordBuilder();
    }

    public EmployeeRecordBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public EmployeeRecordBuilder withSkills(ArrayList<Skill> skills) {
        this.skills = skills;
        return this;
    }

    public EmployeeRecordBuilder isWorkInProgress(boolean isWorkInProgress) {
        this.isWorkInProgress = isWorkInProgress;
        return this;
    }

    public EmployeeRecordBuilder withLastUpdatedDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    public EmployeeRecord build() {
        EmployeeRecord employeeRecord = new EmployeeRecord(username, skills, isWorkInProgress);
        employeeRecord.setDate(lastUpdateDate);
        return employeeRecord;
    }
}
