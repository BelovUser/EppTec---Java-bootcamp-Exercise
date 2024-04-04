package com.example.javaexercise.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private Date birthday;
    @OneToMany(mappedBy = "supEmployee",cascade = CascadeType.ALL)
    private List<Employee> subEmployees = new ArrayList<>();
    @ManyToOne(cascade = CascadeType.ALL)
    private Employee supEmployee;
    @ManyToOne(cascade = CascadeType.ALL)
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
        this.subEmployees.add(subEmployee);
    }

    public Employee getSupEmployee() {
        return supEmployee;
    }

    public void setSupEmployee(Employee supEmployee) {
        this.subEmployees.add(supEmployee);
    }

    public Long getId() {
        return id;
    }
}
