package com.example.javaexercise.services;

import com.example.javaexercise.dtos.CreateEmployeeDto;
import com.example.javaexercise.models.Employee;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


@SpringBootTest
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private OrganizationService mockOrganizationService;
    @Mock
    private EmployeeRepository mockEmployeeRepository;

    @Test
    void findById_givenEmployeeId_whenEmployeeExists_returnEmployee() {
        //arrange
        Employee expectedEmployee = new Employee();
        expectedEmployee.setName("Name");
        expectedEmployee.setSurname("Surname");
        expectedEmployee.setBirthday(new Date(1999, Calendar.DECEMBER,12));

        when(mockEmployeeRepository.findById(1L)).thenReturn(Optional.of(expectedEmployee));
        //act
        Optional<Employee> actualEmployee = employeeService.findById(1L);
        //assert
        assertEquals(actualEmployee.get(),expectedEmployee);
    }

    @Test
    void createEmployee_givenCreateEmployeeDto_createEmployee() {
        //arrange
        CreateEmployeeDto employeeDto = new CreateEmployeeDto("Name", "Surname",
                new Date(1999, Calendar.DECEMBER,12));
        //act
        employeeService.createEmployee(employeeDto);
        //assert
        verify(mockEmployeeRepository,times(1)).save(any(Employee.class));
    }

    @Test
    void deleteEmployee_givenEmployeeId_whenEmployeeExists_deleteEmployee() {
        //arrange
        CreateEmployeeDto employeeDto = new CreateEmployeeDto("Name", "Surname",
                new Date(1999, Calendar.DECEMBER,12));

        employeeService.createEmployee(employeeDto);
        //act
        employeeService.deleteEmployee(1L);
        //assert
        verify(mockEmployeeRepository,times(1)).save(any(Employee.class));
    }

    @Test
    void setSuperior_givenSuperiorIdAndSubordinateId_whenSuperiorAndSubordinateEmployeeExists_setSuperiorToSubordinate() {
        //arrange
        Employee superior = mock(Employee.class);
        when(superior.getName()).thenReturn("SuperiorName");
        when(superior.getSurname()).thenReturn("SuperiorSurname");
        when(superior.getBirthday()).thenReturn(new Date(1999, Calendar.DECEMBER, 12));

        Employee subordinate = mock(Employee.class);
        when(subordinate.getName()).thenReturn("SubordinateName");
        when(subordinate.getSurname()).thenReturn("SubordinateSurname");
        when(subordinate.getBirthday()).thenReturn(new Date(1999, Calendar.DECEMBER, 12));

        when(mockEmployeeRepository.findById(1L)).thenReturn(Optional.of(superior));
        when(mockEmployeeRepository.findById(2L)).thenReturn(Optional.of(subordinate));
        //act
        employeeService.setSuperior(1L,2L);
        //assert
        verify(subordinate,times(1)).setSuperior(superior);
        verify(superior,times(1)).addToSubordinates(subordinate);
    }

    @Test
    void assignEmployeeToOrganization_givenEmployeeIdAndOrganizationName_WhenEmployeeAndOrganizationExists_setEmployeeToOrganization() {
        //arrange
        Long employeeId = 1L;
        String organizationName = "TestOrganization";

        Employee employee = new Employee();
        Organization organization = new Organization();
        organization.setName(organizationName);

        when(employeeService.findById(employeeId)).thenReturn(Optional.of(employee));
        when(mockOrganizationService.findByName(organizationName)).thenReturn(Optional.of(organization));
        //act
        employeeService.assignEmployeeToOrganization(employeeId, organizationName);
        //assert
        verify(mockEmployeeRepository, times(1)).save(employee);
        verify(mockOrganizationService, times(1)).save(organization);
        assertEquals(organization, employee.getOrganization());
        assertTrue(organization.getEmployees().contains(employee));
    }

    @Test
    void findByNameAndSurname_givenNameAndSurname_whenEmployeeExist_returnEmployee() {
        //arrange
        String name = "Name";
        String surname = "Surname";

        Employee expectedEmployee = new Employee();
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(new Date(1999, Calendar.DECEMBER,12));

        when(mockEmployeeRepository.findByNameAndSurname(name, surname))
                .thenReturn(Optional.of(expectedEmployee));
        //act
        Optional<Employee> actualEmployee = employeeService.findByNameAndSurname(name,surname);
        //assert
        assertEquals(actualEmployee.get(),expectedEmployee);
    }
}