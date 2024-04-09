package com.example.javaexercise.repositories;

import com.example.javaexercise.models.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee arrangedEmployee;

    @BeforeEach
    void setUp(){
        this.arrangedEmployee = new Employee();
        arrangedEmployee.setName("John");
        arrangedEmployee.setSurname("Doe");
        arrangedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        employeeRepository.save(arrangedEmployee);
    }
    @AfterEach
    void tearDown(){
        employeeRepository.delete(arrangedEmployee);
    }

    @Test
    void findByNameOrSurname_givenExistingName_returnEmployee() {
        //arrange
        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        List<Employee> actualEmployee = employeeRepository.findAllByNameOrSurname("John","Doe");
        //assert
        assertEquals(actualEmployee, List.of(arrangedEmployee));
    }
    @Test
    void findById_givenExistingId_returnEmployee() {
        //arrange
        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        Optional<Employee> actualEmployee = employeeRepository.findById(1L);
        //assert
        assertEquals(actualEmployee.get(), arrangedEmployee);
    }
}