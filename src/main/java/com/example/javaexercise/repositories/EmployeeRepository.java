package com.example.javaexercise.repositories;

import com.example.javaexercise.models.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE UPPER(e.name) LIKE UPPER(CONCAT('%', :name, '%')) OR UPPER(e.surname) LIKE UPPER(CONCAT('%', :surname, '%')) OR e.id = :id")
    List<Employee> findAllByNameOrSurnameOrId(@Param("name") String name, @Param("surname") String surname, @Param("id") Long id);

    @Query("SELECT e FROM Employee e WHERE UPPER(e.name) LIKE UPPER(CONCAT('%', :name, '%')) AND UPPER(e.surname) LIKE UPPER(CONCAT('%', :surname, '%')) AND e.id = :id")
    List<Employee> findAllByNameAndSurnameAndId(@Param("name") String name, @Param("surname") String surname, @Param("id") Long id);

    @Query("SELECT e FROM Employee e WHERE UPPER(e.name) LIKE UPPER(CONCAT('%', :name, '%')) AND e.id = :id OR UPPER(e.surname) LIKE UPPER(CONCAT('%', :surname, '%')) AND e.id = :id2")
    List<Employee> findAllByAndOrSurnameAndId(@Param("name") String name, @Param("id") Long id, @Param("surname") String surname, @Param("id2") Long id2);

    @Query("SELECT e FROM Employee e WHERE UPPER(e.name) LIKE UPPER(CONCAT('%', :name, '%')) OR UPPER(e.surname) LIKE UPPER(CONCAT('%', :surname, '%'))")
    List<Employee> findAllByNameOrSurname(@Param("name") String name, @Param("surname") String surname);

    @Query("SELECT e FROM Employee e WHERE UPPER(e.name) LIKE UPPER(CONCAT('%', :name, '%')) AND UPPER(e.surname) LIKE UPPER(CONCAT('%', :surname, '%'))")
    List<Employee> findAllByNameAndSurname(@Param("name") String name, @Param("surname") String surname);
}
