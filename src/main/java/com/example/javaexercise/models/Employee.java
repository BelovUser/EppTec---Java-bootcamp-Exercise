package com.example.javaexercise.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthday;
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

    public LocalDate getBirthday() {return birthday;}

    public void setBirthday(LocalDate birthday) {
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
    public void setId(Long id) {
        this.id = id;
    }
}
