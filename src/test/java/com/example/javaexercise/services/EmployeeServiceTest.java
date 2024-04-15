package com.example.javaexercise.services;

import com.example.javaexercise.dtos.CreateEmployeeDto;
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
    void findAllByNameOrSurnameOrId_givenName_whenEmployeeExist_returnEmployee() {
        //arrange
        Long id = null;
        String name = "Name";
        String surname = null;

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findAllByNameOrSurnameOrId(name, surname, id))
                .thenReturn(List.of(expectedEmployee));
        //act
        List<Employee> actualEmployee = employeeService.findAllByNameOrSurnameOrId(name,surname,id);
        //assert
        assertEquals(actualEmployee,List.of(expectedEmployee));
    }

    @Test
    void findAllByNameOrSurnameOrId_givenSurname_whenEmployeeExist_returnEmployee() {
        //arrange
        Long id = null;
        String name = null;
        String surname = "Surname";

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findAllByNameOrSurnameOrId(name, surname, id))
                .thenReturn(List.of(expectedEmployee));
        //act
        List<Employee> actualEmployee = employeeService.findAllByNameOrSurnameOrId(name,surname,id);
        //assert
        assertEquals(actualEmployee,List.of(expectedEmployee));
    }
    @Test
    void findAllByNameOrSurnameOrId_givenId_whenEmployeeExist_returnEmployee() {
        //arrange
        Long id = 1L;
        String name = null;
        String surname = null;

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findAllByNameOrSurnameOrId(name, surname, id))
                .thenReturn(List.of(expectedEmployee));
        //act
        List<Employee> actualEmployee = employeeService.findAllByNameOrSurnameOrId(name,surname,id);
        //assert
        assertEquals(actualEmployee,List.of(expectedEmployee));
    }

    @Test
    void findAllByNameOrSurnameOrId_givenIdAndName_whenEmployeeExist_returnEmployee() {
        //arrange
        Long id = 1L;
        String name = "Name";
        String surname = null;

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findAllByNameOrSurnameOrId(name, surname, id))
                .thenReturn(List.of(expectedEmployee));
        //act
        List<Employee> actualEmployee = employeeService.findAllByNameOrSurnameOrId(name,surname,id);
        //assert
        assertEquals(actualEmployee,List.of(expectedEmployee));
    }

    @Test
    void findAllByNameOrSurnameOrId_givenIdAndSurname_whenEmployeeExist_returnEmployee() {
        //arrange
        Long id = 1L;
        String name = null;
        String surname = "Surname";

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findAllByNameOrSurnameOrId(name, surname, id))
                .thenReturn(List.of(expectedEmployee));
        //act
        List<Employee> actualEmployee = employeeService.findAllByNameOrSurnameOrId(name,surname,id);
        //assert
        assertEquals(actualEmployee,List.of(expectedEmployee));
    }

    @Test
    void findAllByNameOrSurnameOrId_givenNameAndSurname_whenEmployeeExist_returnEmployee() {
        //arrange
        Long id = null;
        String name = "Name";
        String surname = "Surname";

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findAllByNameOrSurnameOrId(name, surname, id))
                .thenReturn(List.of(expectedEmployee));
        //act
        List<Employee> actualEmployee = employeeService.findAllByNameOrSurnameOrId(name,surname,id);
        //assert
        assertEquals(actualEmployee,List.of(expectedEmployee));
    }

    @Test
    void findAllByNameOrSurnameOrId_givenNameAndSurnameAndId_whenEmployeeExist_returnEmployee() {
        //arrange
        Long id = 1L;
        String name = "Name";
        String surname = "Surname";

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findAllByNameOrSurnameOrId(name, surname, id))
                .thenReturn(List.of(expectedEmployee));
        //act
        List<Employee> actualEmployee = employeeService.findAllByNameOrSurnameOrId(name,surname,id);
        //assert
        assertEquals(actualEmployee,List.of(expectedEmployee));
    }

    @Test
    void findAllByNameOrSurnameOrId_givenLowerCaseNameAndLowerCaseSurnameAndId_whenEmployeeExist_returnEmployee() {
        //arrange
        Long id = 1L;
        String name = "Name";
        String surname = "Surname";
        String searchName = "name";
        String searchSurname = "surname";

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findAllByNameOrSurnameOrId(searchName, searchSurname, id))
                .thenReturn(List.of(expectedEmployee));
        //act
        List<Employee> actualEmployee = employeeService.findAllByNameOrSurnameOrId(searchName,searchSurname,id);
        //assert
        assertEquals(actualEmployee,List.of(expectedEmployee));
    }



    @Test
    void findAllByNameAndSurnameAndId_givenNameAndSurnameAndId_whenEmployeeExist_returnEmployee(){
        //arrange
        Long id = 1L;
        String name = "Name";
        String surname = "Surname";

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findAllByNameAndSurnameAndId(name, surname, id))
                .thenReturn(List.of(expectedEmployee));
        //act
        List<Employee> actualEmployee = employeeService.findAllByNameAndSurnameAndId(name,surname,id);
        //assert
        assertEquals(actualEmployee,List.of(expectedEmployee));
    }

    @Test
    void findAllByNameOrSurnameAndId_givenNameAndId_whenEmployeeExist_returnEmployee(){
        //arrange
        Long id = 1L;
        String name = "Name";
        String surname = null;

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findAllByAndOrSurnameAndId(name, id, surname, id))
                .thenReturn(List.of(expectedEmployee));
        //act
        List<Employee> actualEmployee = employeeService.findAllByNameOrSurnameAndId(name,surname,id);
        //assert
        assertEquals(actualEmployee,List.of(expectedEmployee));
    }

    @Test
    void findAllByNameOrSurnameAndId_givenSurnameAndId_whenEmployeeExist_returnEmployee(){
        //arrange
        Long id = 1L;
        String name = null;
        String surname = "Surname";

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findAllByAndOrSurnameAndId(name, id, surname, id))
                .thenReturn(List.of(expectedEmployee));
        //act
        List<Employee> actualEmployee = employeeService.findAllByNameOrSurnameAndId(name,surname,id);
        //assert
        assertEquals(actualEmployee,List.of(expectedEmployee));
    }

    @Test
    void findAllByNameOrSurnameAndId_givenNameAndSurnameAndId_whenEmployeeExist_returnEmployee(){
        //arrange
        Long id = 1L;
        String name = "Name";
        String surname = "Surname";

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findAllByAndOrSurnameAndId(name, id, surname, id))
                .thenReturn(List.of(expectedEmployee));
        //act
        List<Employee> actualEmployee = employeeService.findAllByNameOrSurnameAndId(name,surname,id);
        //assert
        assertEquals(actualEmployee,List.of(expectedEmployee));
    }

    @Test
    void findAllByNameOrSurnameAndId_givenLowerCaseNameAndLowerCaseSurnameAndId_whenEmployeeExist_returnEmployee(){
        //arrange
        Long id = 1L;
        String name = "Name";
        String surname = "Surname";
        String searchName = "name";
        String searchSurname = "surname";

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findAllByAndOrSurnameAndId(searchName, id, searchSurname, id))
                .thenReturn(List.of(expectedEmployee));
        //act
        List<Employee> actualEmployee = employeeService.findAllByNameOrSurnameAndId(searchName,searchSurname,id);
        //assert
        assertEquals(actualEmployee,List.of(expectedEmployee));
    }

    @Test
    void findAllByNameOrSurname_givenName_whenEmployeeExist_thenReturnEmployee(){
        //arrange
        String name = "Name";
        String surname = null;

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findAllByNameOrSurname(name, surname))
                .thenReturn(List.of(expectedEmployee));
        //act
        List<Employee> actualEmployee = employeeService.findAllByNameOrSurname(name,surname);
        //assert
        assertEquals(actualEmployee,List.of(expectedEmployee));
    }

    @Test
    void findAllByNameOrSurname_givenSurname_whenEmployeeExist_thenReturnEmployee(){
        //arrange
        String name = null;
        String surname = "Surname";

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findAllByNameOrSurname(name, surname))
                .thenReturn(List.of(expectedEmployee));
        //act
        List<Employee> actualEmployee = employeeService.findAllByNameOrSurname(name,surname);
        //assert
        assertEquals(actualEmployee,List.of(expectedEmployee));
    }

    @Test
    void findAllByNameOrSurname_givenNameAndSurname_whenEmployeeExist_thenReturnEmployee(){
        //arrange
        String name = "Name";
        String surname = "Surname";

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findAllByNameOrSurname(name, surname))
                .thenReturn(List.of(expectedEmployee));
        //act
        List<Employee> actualEmployee = employeeService.findAllByNameOrSurname(name,surname);
        //assert
        assertEquals(actualEmployee,List.of(expectedEmployee));
    }

    @Test
    void findAllByNameOrSurname_givenLowerCaseNameAndLowerCaseSurname_whenEmployeeExist_thenReturnEmployee(){
        //arrange
        String name = "Name";
        String surname = "Surname";
        String searchName = "name";
        String searchSurname = "surname";

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findAllByNameOrSurname(searchName, searchSurname))
                .thenReturn(List.of(expectedEmployee));
        //act
        List<Employee> actualEmployee = employeeService.findAllByNameOrSurname(searchName,searchSurname);
        //assert
        assertEquals(actualEmployee,List.of(expectedEmployee));
    }

    @Test
    void findAllByNameAndSurname_givenNameAndSurname_whenEmployeeExist_thenReturnEmployee(){
        //arrange
        String name = "Name";
        String surname = "Surname";

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findAllByNameAndSurname(name, surname))
                .thenReturn(List.of(expectedEmployee));
        //act
        List<Employee> actualEmployee = employeeService.findAllByNameAndSurname(name,surname);
        //assert
        assertEquals(actualEmployee,List.of(expectedEmployee));
    }

    @Test
    void findAllByNameAndSurname_givenLowerCaseNameAndLowerCaseSurname_whenEmployeeExist_thenReturnEmployee(){
        //arrange
        String name = "Name";
        String surname = "Surname";
        String searchName = "name";
        String searchSurname = "surname";

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));

        when(mockEmployeeRepository.findAllByNameAndSurname(name, surname))
                .thenReturn(List.of(expectedEmployee));
        //act
        List<Employee> actualEmployee = employeeService.findAllByNameAndSurname(name,surname);
        //assert
        assertEquals(actualEmployee,List.of(expectedEmployee));
    }
}