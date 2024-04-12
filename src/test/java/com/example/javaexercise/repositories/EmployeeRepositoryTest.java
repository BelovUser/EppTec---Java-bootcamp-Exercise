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
    void findAllByNameOrSurnameOrId_givenName_whenEmployeeExist_returnEmployee() {
        //arrange
        String searchName = "John";
        String searchSurname = null;
        Long searchId = null;

        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        List<Employee> actualEmployee = employeeRepository.findAllByNameContainsIgnoreCaseOrSurnameContainsIgnoreCaseOrId(searchName,searchSurname, searchId);
        //assert
        assertEquals(actualEmployee, List.of(arrangedEmployee));
    }

    @Test
    void findAllByNameOrSurnameOrId_givenSurname_whenEmployeeExist_returnEmployee() {
        //arrange
        String searchName = null;
        String searchSurname = "Doe";
        Long searchId = null;

        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        List<Employee> actualEmployee = employeeRepository.findAllByNameContainsIgnoreCaseOrSurnameContainsIgnoreCaseOrId(searchName,searchSurname, searchId);
        //assert
        assertEquals(actualEmployee, List.of(arrangedEmployee));
    }

    @Test
    void findAllByNameOrSurnameOrId_givenId_whenEmployeeExist_returnEmployee() {
        //arrange
        String searchName = null;
        String searchSurname = null;
        Long searchId = arrangedEmployee.getId();

        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        List<Employee> actualEmployee = employeeRepository.findAllByNameContainsIgnoreCaseOrSurnameContainsIgnoreCaseOrId(searchName,searchSurname, searchId);
        //assert
        assertEquals(actualEmployee, List.of(arrangedEmployee));
    }

    @Test
    void findAllByNameOrSurnameOrId_givenNameAndSurnameAndId_whenEmployeeExist_returnEmployee() {
        //arrange
        String searchName = "John";
        String searchSurname = "Doe";
        Long searchId = arrangedEmployee.getId();

        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        List<Employee> actualEmployee = employeeRepository.findAllByNameContainsIgnoreCaseOrSurnameContainsIgnoreCaseOrId(searchName,searchSurname, searchId);
        //assert
        assertEquals(actualEmployee, List.of(arrangedEmployee));
    }

    @Test
    void findAllByNameOrSurnameOrId_givenLowerCaseNameAndLowerCaseSurnameAndId_whenEmployeeExist_returnEmployee() {
        //arrange
        String searchName = "john";
        String searchSurname = "doe";
        Long searchId = arrangedEmployee.getId();

        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        List<Employee> actualEmployee = employeeRepository.findAllByNameContainsIgnoreCaseOrSurnameContainsIgnoreCaseOrId(searchName,searchSurname, searchId);
        //assert
        assertEquals(actualEmployee, List.of(arrangedEmployee));
    }

    @Test
    void findById_givenExistingId_returnEmployee() {
        //arrange
        Long searchId = arrangedEmployee.getId();

        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        Optional<Employee> actualEmployee = employeeRepository.findById(searchId);
        //assert
        assertEquals(actualEmployee.get(), arrangedEmployee);
    }

    @Test
    void findAllByNameIgnoreCaseAndSurnameIgnoreCaseAndId_givenNameAndSurnameAndId_whenEmployeeExist_returnEmployee() {
        //arrange
        String searchName = "John";
        String searchSurname = "Doe";
        Long searchId = arrangedEmployee.getId();

        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        List<Employee> actualEmployee = employeeRepository.findAllByNameContainsIgnoreCaseAndSurnameContainsIgnoreCaseAndId(searchName,searchSurname, searchId);
        //assert
        assertEquals(actualEmployee, List.of(arrangedEmployee));
    }

    @Test
    void findAllByNameIgnoreCaseAndSurnameIgnoreCaseAndId_givenLowerCaseNameAndLowerCaseSurnameAndId_whenEmployeeExist_returnEmployee() {
        //arrange
        String searchName = "john";
        String searchSurname = "doe";
        Long searchId = arrangedEmployee.getId();

        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        List<Employee> actualEmployee = employeeRepository.findAllByNameContainsIgnoreCaseAndSurnameContainsIgnoreCaseAndId(searchName,searchSurname, searchId);
        //assert
        assertEquals(actualEmployee, List.of(arrangedEmployee));
    }

    @Test
    void findAllByNameIgnoreCaseAndIdOrSurnameIgnoreCaseAndId_givenNameAndId_whenEmployeeExist_returnEmployee() {
        //arrange
        String searchName = "John";
        String searchSurname = null;
        Long searchId = arrangedEmployee.getId();

        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        List<Employee> actualEmployee = employeeRepository.findAllByNameContainsIgnoreCaseAndIdOrSurnameContainsIgnoreCaseAndId(searchName,searchId,searchSurname, searchId);
        //assert
        assertEquals(actualEmployee, List.of(arrangedEmployee));
    }

    @Test
    void findAllByNameIgnoreCaseAndIdOrSurnameIgnoreCaseAndId_givenSurnameAndId_whenEmployeeExist_returnEmployee() {
        //arrange
        String searchName = null;
        String searchSurname = "Doe";
        Long searchId = arrangedEmployee.getId();

        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        List<Employee> actualEmployee = employeeRepository.findAllByNameContainsIgnoreCaseAndIdOrSurnameContainsIgnoreCaseAndId(searchName,searchId,searchSurname, searchId);
        //assert
        assertEquals(actualEmployee, List.of(arrangedEmployee));
    }
    @Test
    void findAllByNameIgnoreCaseAndIdOrSurnameIgnoreCaseAndId_givenNameAndSurnameAndId_whenEmployeeExist_returnEmployee() {
        //arrange
        String searchName = "John";
        String searchSurname = "Doe";
        Long searchId = arrangedEmployee.getId();

        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        List<Employee> actualEmployee = employeeRepository.findAllByNameContainsIgnoreCaseAndIdOrSurnameContainsIgnoreCaseAndId(searchName,searchId,searchSurname, searchId);
        //assert
        assertEquals(actualEmployee, List.of(arrangedEmployee));
    }

    @Test
    void findAllByNameIgnoreCaseAndIdOrSurnameIgnoreCaseAndId_givenLowerCaseNameAndLowerCaseSurnameAndId_whenEmployeeExist_returnEmployee() {
        //arrange
        String searchName = "john";
        String searchSurname = "doe";
        Long searchId = arrangedEmployee.getId();

        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        List<Employee> actualEmployee = employeeRepository.findAllByNameContainsIgnoreCaseAndIdOrSurnameContainsIgnoreCaseAndId(searchName,searchId,searchSurname, searchId);
        //assert
        assertEquals(actualEmployee, List.of(arrangedEmployee));
    }

    @Test
    void findAllByNameIgnoreCaseOrSurnameIgnoreCase_givenName_whenEmployeeExist_returnEmployee() {
        //arrange
        String searchName = "John";
        String searchSurname = null;

        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        List<Employee> actualEmployee = employeeRepository.findAllByNameContainsIgnoreCaseOrSurnameContainsIgnoreCase(searchName,searchSurname);
        //assert
        assertEquals(actualEmployee, List.of(arrangedEmployee));
    }

    @Test
    void findAllByNameIgnoreCaseOrSurnameIgnoreCase_givenSurname_whenEmployeeExist_returnEmployee() {
        //arrange
        String searchName = null;
        String searchSurname = "Doe";

        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        List<Employee> actualEmployee = employeeRepository.findAllByNameContainsIgnoreCaseOrSurnameContainsIgnoreCase(searchName,searchSurname);
        //assert
        assertEquals(actualEmployee, List.of(arrangedEmployee));
    }

    @Test
    void findAllByNameIgnoreCaseOrSurnameIgnoreCase_givenNameAndSurname_whenEmployeeExist_returnEmployee() {
        //arrange
        String searchName = "John";
        String searchSurname = "Doe";

        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        List<Employee> actualEmployee = employeeRepository.findAllByNameContainsIgnoreCaseOrSurnameContainsIgnoreCase(searchName,searchSurname);
        //assert
        assertEquals(actualEmployee, List.of(arrangedEmployee));
    }

    @Test
    void findAllByNameIgnoreCaseOrSurnameIgnoreCase_givenLowerCaseNameAndLowerCaseSurname_whenEmployeeExist_returnEmployee() {
        //arrange
        String searchName = "john";
        String searchSurname = "doe";

        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        List<Employee> actualEmployee = employeeRepository.findAllByNameContainsIgnoreCaseOrSurnameContainsIgnoreCase(searchName,searchSurname);
        //assert
        assertEquals(actualEmployee, List.of(arrangedEmployee));
    }
    @Test
    void findAllByNameIgnoreCaseAndSurnameIgnoreCase_givenNameAndSurname_whenEmployeeExist_returnEmployee() {
        //arrange
        String searchName = "John";
        String searchSurname = "Doe";

        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        List<Employee> actualEmployee = employeeRepository.findAllByNameContainsIgnoreCaseAndSurnameContainsIgnoreCase(searchName,searchSurname);
        //assert
        assertEquals(actualEmployee, List.of(arrangedEmployee));
    }

    @Test
    void findAllByNameIgnoreCaseAndSurnameIgnoreCase_givenLowerCaseNameAndLowerCaseSurname_whenEmployeeExist_returnEmployee() {
        //arrange
        String searchName = "john";
        String searchSurname = "doe";

        Employee arrangedEmployee = this.arrangedEmployee;
        //act
        List<Employee> actualEmployee = employeeRepository.findAllByNameContainsIgnoreCaseAndSurnameContainsIgnoreCase(searchName,searchSurname);
        //assert
        assertEquals(actualEmployee, List.of(arrangedEmployee));
    }
}