package com.example.javaexercise.repositories;

import com.example.javaexercise.models.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Calendar;
import java.util.Date;
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
        arrangedEmployee.setBirthday(new Date(1999, Calendar.DECEMBER,12));

        employeeRepository.save(arrangedEmployee);
    }
    @AfterEach
    void tearDown(){
        employeeRepository.delete(arrangedEmployee);
    }

    @Test
    void findByNameAndSurname_givenExistingName_returnEmployee() {
        //arrange
        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        Optional<Employee> actualEmployee = employeeRepository.findByNameAndSurname("John","Doe");
        //assert
        assertEquals(actualEmployee.get(), arrangedEmployee);
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