package com.example.javaexercise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.Date;
import java.util.List;

@Entity
public class Employee {
    @Id
    private Long id;
    private String name;
    private String username;
    private Date birthday;
    @OneToMany(mappedBy = "supEmployee")
    private List<Employee> subEmployee;
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

    public List<Employee> getSubEmployee() {
        return subEmployee;
    }

    public void setSubEmployee(List<Employee> subEmployee) {
        this.subEmployee = subEmployee;
    }

    public Employee getSupEmployee() {
        return supEmployee;
    }

    public void setSupEmployee(Employee supEmployee) {
        this.supEmployee = supEmployee;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
