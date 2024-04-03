package com.example.javaexercise.repositories;

import com.example.javaexercise.models.Employee;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepo extends CrudRepository<Employee, Long> {
}
