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
    @OneToMany(mappedBy = "superior",cascade = CascadeType.ALL)
    private List<Employee> subordinates = new ArrayList<>();
    @ManyToOne(cascade = CascadeType.ALL)
    private Employee superior;
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

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void addToSubEmployees(Employee subordinate){
        this.subordinates.add(subordinate);
    }

    public Employee getSuperior() {
        return superior;
    }

    public void setSuperior(Employee superior) {
        this.subordinates.add(superior);
    }

    public Long getId() {
        return id;
    }
}
