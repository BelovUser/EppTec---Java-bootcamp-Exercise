package com.example.javaexercise.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Organization {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String address;
    @OneToMany(mappedBy = "organization",cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employee) {
        this.employees = employee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String surname) {
        this.address = surname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {this.id = id;}
}
