package net.morpheus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Skill {

    @JsonProperty
    private String description;
    @JsonProperty
    private Integer value;
    @JsonProperty
    private String comment;
    @JsonProperty
    private String devComment;

    public Skill(String description, Integer value, String comment, String devComment) {
        this.description = description;
        this.value = value;
        this.comment = comment;
        this.devComment = devComment;
    }

    public String description() {
        return description;
    }

    public Integer value() { return value; }

    public String comment() { return comment; }

    public String devComment() { return  devComment; }

}
