package net.morpheus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
    private Role role;
    @JsonProperty
    private ArrayList<Skill> skills;
    @JsonProperty
    private Level level;
    @JsonProperty
    private boolean isWorkInProgress;
    @JsonProperty
    private String lastUpdateDate;

    public EmployeeRecord(String username, Role role, ArrayList<Skill> skills, Level level, boolean isWorkInProgress) {
        this.username = username;
        this.role = role;
        this.skills = skills;
        this.level = level;
        this.isWorkInProgress = isWorkInProgress;
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
                role == that.role &&
                Objects.equals(skills, that.skills) &&
                level == that.level &&
                Objects.equals(lastUpdateDate, that.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.username);
        return builder.hashCode();
    }

    @Override
    public String toString() {
        return "EmployeeRecord{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", skills=" + skills +
                ", level=" + level +
                ", isWorkInProgress=" + isWorkInProgress +
                ", lastUpdateDate='" + lastUpdateDate + '\'' +
                '}';
    }
}