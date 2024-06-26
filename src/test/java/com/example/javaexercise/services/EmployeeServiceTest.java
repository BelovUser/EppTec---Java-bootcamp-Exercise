package com.example.javaexercise.services;

import com.example.javaexercise.dtos.CreateEmployeeDto;
import com.example.javaexercise.exceptions.EntityNotFoundException;
import com.example.javaexercise.exceptions.NoParameterProvidedException;
import com.example.javaexercise.mappers.DtoMapper;
import com.example.javaexercise.models.Employee;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@SpringBootTest
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private OrganizationService mockOrganizationService;
    @Mock
    private EmployeeRepository mockEmployeeRepository;
//    @Mock
//    private JpaSpecification mockJpaSpecification;
    @Mock
    private DtoMapper dtoMapper;

    @Test
    void findById_givenEmployeeId_whenEmployeeExists_returnEmployee() {
        //arrange
        Employee expectedEmployee = new Employee();
        expectedEmployee.setName("Name");
        expectedEmployee.setSurname("Surname");
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findById(1L)).thenReturn(Optional.of(expectedEmployee));
        //act
        Employee actualEmployee = employeeService.findById(1L);
        //assert
        assertEquals(actualEmployee,expectedEmployee);
    }

    @Test
    void createEmployee_givenCreateEmployeeDto_createEmployee() {
        //arrange
        CreateEmployeeDto employeeDto = new CreateEmployeeDto("Name", "Surname",
                LocalDate.of(1999, 12,12));
        Employee employee = new Employee();
        employee.setName("Name");
        employee.setSurname("Surname");
        employee.setBirthday(LocalDate.of(1999, 12,12));

        when(dtoMapper.mapDtoToEmployee(employeeDto)).thenReturn(employee);
        //act
        employeeService.createEmployee(employeeDto);
        //assert
        verify(mockEmployeeRepository,times(1)).save(employee);
    }

    @Test
    void deleteEmployee_givenEmployeeId_whenEmployeeExists_deleteEmployee() {
        //arrange
        CreateEmployeeDto employeeDto = new CreateEmployeeDto("Name", "Surname",
                LocalDate.of(1999, 12,12));
        Employee employee = new Employee();
        employee.setName("Name");
        employee.setSurname("Surname");
        employee.setBirthday(LocalDate.of(1999, 12,12));

        when(dtoMapper.mapDtoToEmployee(employeeDto)).thenReturn(employee);
        //act
        employeeService.createEmployee(employeeDto);
        employeeService.deleteEmployee(1L);
        //assert
        verify(mockEmployeeRepository,times(1)).save(employee);
    }

    @Test
    void setSuperior_givenSuperiorIdAndSubordinateId_whenSuperiorAndSubordinateEmployeeExists_setSuperiorToSubordinate() {
        //arrange
        Employee superior = mock(Employee.class);
        when(superior.getName()).thenReturn("SuperiorName");
        when(superior.getSurname()).thenReturn("SuperiorSurname");
        when(superior.getBirthday()).thenReturn(LocalDate.of(1999, 12,12));

        Employee subordinate = mock(Employee.class);
        when(subordinate.getName()).thenReturn("SubordinateName");
        when(subordinate.getSurname()).thenReturn("SubordinateSurname");
        when(subordinate.getBirthday()).thenReturn(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findById(1L)).thenReturn(Optional.of(superior));
        when(mockEmployeeRepository.findById(2L)).thenReturn(Optional.of(subordinate));
        //act
        employeeService.setSuperior(1L,2L);
        //assert
        verify(subordinate,times(1)).setSuperior(superior);
        verify(superior,times(1)).addToSubordinates(subordinate);
    }

    @Test
    void setSuperior_givenSameId_whenEmployeeExists_throwException() {
        //arrange
        Employee expectedEmployee = new Employee();
        expectedEmployee.setName("Name");
        expectedEmployee.setSurname("Surname");
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findById(1L)).thenReturn(Optional.of(expectedEmployee));
        //act and assert
        assertThrows(RuntimeException.class, () -> {
            employeeService.setSuperior(1L, 1L);
        });
    }

    @Test
    void setSuperior_givenSuperiorIdAndSubordinateId_whenSubordinateHasSuperiorInSubordinates_throwException(){
        //arrange
        Employee subordinate = mock(Employee.class);
        when(subordinate.getName()).thenReturn("SubordinateName");
        when(subordinate.getSurname()).thenReturn("SubordinateSurname");
        when(subordinate.getBirthday()).thenReturn(LocalDate.of(1999, 12,12));

        Employee superior = mock(Employee.class);
        when(superior.getName()).thenReturn("SuperiorName");
        when(superior.getSurname()).thenReturn("SuperiorSurname");
        when(superior.getBirthday()).thenReturn(LocalDate.of(1999, 12,12));
        when(superior.getSuperior()).thenReturn(subordinate);

        when(mockEmployeeRepository.findById(1L)).thenReturn(Optional.of(superior));
        when(mockEmployeeRepository.findById(2L)).thenReturn(Optional.of(subordinate));
        //act and assert
        assertThrows(RuntimeException.class, () -> {
            employeeService.setSuperior(1L, 2L);
        });
    }

    @Test
    void assignEmployeeToOrganization_givenEmployeeIdAndOrganizationName_WhenEmployeeAndOrganizationExists_setEmployeeToOrganization() {
        //arrange
        Long employeeId = 1L;
        String organizationName = "TestOrganization";

        Employee employee = new Employee();
        Organization organization = new Organization();
        organization.setName(organizationName);

        when(mockEmployeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(mockOrganizationService.findById(1L)).thenReturn(organization);
        //act
        employeeService.assignEmployeeToOrganization(employeeId, 1L);
        //assert
        verify(mockEmployeeRepository, times(1)).save(employee);
        verify(mockOrganizationService, times(1)).save(organization);
        assertEquals(organization, employee.getOrganization());
        assertTrue(organization.getEmployees().contains(employee));
    }

    @Test
    void findEmployees_givenNameAndSurnameAndId_whenEmployeeExist_thenReturnEmployee(){
        //arrange
        String employeeName = "John";
        String employeeSurname = "Doe";
        Long employeeId = null;

        Employee employee = new Employee();
        employee.setName(employeeName);
        employee.setSurname(employeeSurname);

        when(mockEmployeeRepository.findAll(any())).thenReturn(List.of(employee));
        //act
        List<Employee> actualEmployees = employeeService.findEmployees(employeeName,employeeSurname,employeeId);
        //assert
        assertEquals(actualEmployees, List.of(employee));
    }

    @Test
    void findEmployees_givenNameAndSurnameAndId_whenEmployeeDontExist_thenThrowException(){
        //arrange
        String employeeName = "John";
        String employeeSurname = "Doe";
        Long employeeId = null;

        Employee employee = new Employee();
        employee.setName(employeeName);
        employee.setSurname(employeeSurname);
        //act assert
        assertThrows(EntityNotFoundException.class, () -> employeeService.findEmployees(employeeName,employeeSurname,employeeId));
    }

    @Test
    void findEmployees_givenNoParameters_thenThrowException(){
        //arrange
        String employeeName = null;
        String employeeSurname = null;
        Long employeeId = null;
        //act assert
        assertThrows(NoParameterProvidedException.class, () -> employeeService.findEmployees(employeeName,employeeSurname,employeeId));
    }
}