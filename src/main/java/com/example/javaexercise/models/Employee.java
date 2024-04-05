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
    private String surname;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String username) {
        this.surname = username;
    }

    public Date getBirthday() {return birthday;}

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void addToSubordinates(Employee subordinate){
        this.subordinates.add(subordinate);
    }

    public Employee getSuperior() {
        return superior;
    }

    public void setSuperior(Employee superior) {
        this.superior = superior;
    }

    public Long getId() {
        return id;
    }
}
