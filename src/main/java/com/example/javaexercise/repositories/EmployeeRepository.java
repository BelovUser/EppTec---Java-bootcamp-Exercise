package com.example.javaexercise.repositories;

import com.example.javaexercise.models.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findAllByNameIgnoreCaseOrSurnameIgnoreCaseOrId(String name, String surname, Long id);
    List<Employee> findAllByNameIgnoreCaseAndSurnameIgnoreCaseAndId(String name, String surname, Long id);
    List<Employee> findAllByNameIgnoreCaseAndIdOrSurnameIgnoreCaseAndId(String name, Long id, String surname, Long id2);
    List<Employee> findAllByNameIgnoreCaseOrSurnameIgnoreCase(String name, String surname);
    List<Employee> findAllByNameIgnoreCaseAndSurnameIgnoreCase(String name, String surname);
}
