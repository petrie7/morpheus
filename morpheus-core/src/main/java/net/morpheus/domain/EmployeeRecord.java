package net.morpheus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("unused")
public class EmployeeRecord {

    @Id
    private final String id = UUID.randomUUID().toString();
    @JsonProperty
    private String username;
    @JsonProperty
    private ArrayList<Skill> skills;
    @JsonProperty
    private boolean isWorkInProgress;
    @JsonProperty
    private String lastUpdateDate;

    public EmployeeRecord(String username, ArrayList<Skill> skills, boolean isWorkInProgress) {
        this.username = username;
        this.skills = skills;
        this.isWorkInProgress = isWorkInProgress;
    }

    public String username() {
        return username;
    }

    public List<Skill> skills() {
        return skills;
    }

    public boolean isWorkInProgress() {
        return isWorkInProgress;
    }

    public void setDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public LocalDateTime date() {
        return LocalDateTime.parse(lastUpdateDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeRecord that = (EmployeeRecord) o;
        return isWorkInProgress == that.isWorkInProgress &&
                Objects.equals(username, that.username) &&
                Objects.equals(skills, that.skills) &&
                Objects.equals(lastUpdateDate, that.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, skills, isWorkInProgress, lastUpdateDate);
    }

    @Override
    public String toString() {
        return "EmployeeRecord{" +
                "username='" + username + '\'' +
                ", skills=" + skills +
                ", isWorkInProgress=" + isWorkInProgress +
                ", lastUpdateDate='" + lastUpdateDate + '\'' +
                '}';
    }
}