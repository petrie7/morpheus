package net.morpheus.domain;

public enum Team {

    NoTeam("No Team"),
    Sonique("Sonique"),
    Lando("Lando"),
    FiveOh("Five Oh"),
    Mongoose("Mongoose"),
    Merkats("Merkats"),
    Hiro("Hiro"),
    Nim("Nim");

    private final String description;

    Team(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
