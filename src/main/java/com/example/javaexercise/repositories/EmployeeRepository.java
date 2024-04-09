package com.example.javaexercise.repositories;

import com.example.javaexercise.models.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findAllByNameOrSurname(String name, String surname);
}
