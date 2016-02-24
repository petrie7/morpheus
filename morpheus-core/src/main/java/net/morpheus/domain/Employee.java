package net.morpheus.domain;

public class Employee {

    private final String username;

    public Employee(String username) {
        this.username = username;
    }

    public String username() {
        return username;
    }
}