package com.example.javaexercise.repositories;

import com.example.javaexercise.models.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findAllByNameOrSurnameOrId(String name, String surname, Long id);
    List<Employee> findAllByNameAndSurnameAndId(String name, String surname, Long id);
    List<Employee> findAllByNameAndIdOrSurnameAndId(String name, Long id, String surname, Long id2);
    List<Employee> findAllByNameOrSurname(String name, String surname);
    List<Employee> findAllByNameAndSurname(String name, String surname);
}
