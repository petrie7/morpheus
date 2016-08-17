package net.morpheus.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Employee {

    @JsonProperty
    private EmployeeDetails employeeDetails;
    @JsonProperty
    private List<EmployeeRecord> employeeRecords;

    public Employee(EmployeeDetails employeeDetails, List<EmployeeRecord> employeeRecords) {
        this.employeeDetails = employeeDetails;
        this.employeeRecords = employeeRecords;
    }
}
