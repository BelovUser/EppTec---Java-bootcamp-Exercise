package com.example.javaexercise.repositories;

import com.example.javaexercise.models.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findAllByNameContainsIgnoreCaseOrSurnameContainsIgnoreCaseOrId(String name, String surname, Long id);
    List<Employee> findAllByNameContainsIgnoreCaseAndSurnameContainsIgnoreCaseAndId(String name, String surname, Long id);
    List<Employee> findAllByNameContainsIgnoreCaseAndIdOrSurnameContainsIgnoreCaseAndId(String name, Long id, String surname, Long id2);
    List<Employee> findAllByNameContainsIgnoreCaseOrSurnameContainsIgnoreCase(String name, String surname);
    List<Employee> findAllByNameContainsIgnoreCaseAndSurnameContainsIgnoreCase(String name, String surname);
}
