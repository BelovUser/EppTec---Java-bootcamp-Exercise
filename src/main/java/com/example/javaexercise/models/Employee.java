package com.example.javaexercise.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String username;
    private Date birthday;
    @OneToMany(mappedBy = "supEmployee")
    private List<Employee> subEmployees = new ArrayList<>();
    @ManyToOne
    private Employee supEmployee;
    @ManyToOne
    private Organization organization;

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Employee> getSubEmployees() {
        return subEmployees;
    }

    public void addToSubEmployees(Employee subEmployee){
        if(subEmployee.equals(this)){
            throw new RuntimeException("Employee can´t have itself in subEmployee.");
        }
        this.subEmployees.add(subEmployee);
    }

    public Employee getSupEmployee() {
        return supEmployee;
    }

    public void setSupEmployee(Employee supEmployee) {
        if(supEmployee.equals(this)){
            throw new RuntimeException("Employee can´t be supEmployee to itself.");
        }
        this.subEmployees.add(supEmployee);
    }

    public Long getId() {
        return id;
    }
}
