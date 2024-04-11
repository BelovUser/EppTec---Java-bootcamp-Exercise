package com.example.javaexercise.mappers;

import com.example.javaexercise.dtos.*;
import com.example.javaexercise.models.Employee;
import com.example.javaexercise.models.Organization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DtoMapperTest {

    @Autowired
    private DtoMapper dtoMapper;

    @Test
    void mapToEmployeeDTO_givenEmployee_shouldReturnEmployeeDto() {
        //arrange
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setSurname("Doe");
        employee.setBirthday(LocalDate.of(1999, 12,12));

        EmployeeDto expectedEmployeeDto = new EmployeeDto(1L,"John","Doe",
                LocalDate.of(1999, 12,12), null,null, null);
        //act
        EmployeeDto actualEmployeeDto = dtoMapper.mapToEmployeeDTO(employee);
        //assert
        assertEquals(expectedEmployeeDto,actualEmployeeDto);
    }

    @Test
    void mapToOrganizationDTO_givenOrganization_shouldReturnOrganizationDto() {
        //arrange
        Organization organization = new Organization();
        organization.setId(1L);
        organization.setName("Org");
        organization.setAddress("st.Peter 123");

        OrganizationDto expectedOrganizationDto = new OrganizationDto(1L,"Org","st.Peter 123", List.of());
        //act
        OrganizationDto actualOrganizationDto = dtoMapper.mapToOrganizationDTO(organization);
        //assert
        assertEquals(expectedOrganizationDto,actualOrganizationDto);
    }

    @Test
    void mapDtoToEmployee_givenCreateEmployeeDto_shouldReturnEmployee() {
        //arrange
        CreateEmployeeDto createEmployeeDto = new CreateEmployeeDto("John", "Doe", LocalDate.of(1999, 12,12));

        Employee expectedEmployee = new Employee();
        expectedEmployee.setName("John");
        expectedEmployee.setSurname("Doe");
        expectedEmployee.setBirthday(LocalDate.of(1999, 12,12));
        //act
        Employee actualEmployee = dtoMapper.mapDtoToEmployee(createEmployeeDto);
        //assert
        assertEquals(expectedEmployee.getName(),actualEmployee.getName());
        assertEquals(expectedEmployee.getSurname(),actualEmployee.getSurname());
        assertEquals(expectedEmployee.getBirthday(),actualEmployee.getBirthday());
    }

    @Test
    void mapDtoToOrganization_givenOrganization_shouldReturnOrganization() {
        //arrange
        CreateOrganizationDto createOrganizationDto = new CreateOrganizationDto("Org", "st.Peter 123");

        Organization expectedOrganization = new Organization();
        expectedOrganization.setName("Org");
        expectedOrganization.setAddress("st.Peter 123");
        //act
        Organization actualOrganization = dtoMapper.mapDtoToOrganization(createOrganizationDto);
        //assert
        assertEquals(expectedOrganization.getName(),actualOrganization.getName());
        assertEquals(expectedOrganization.getAddress(),actualOrganization.getAddress());
    }

    @Test
    void mapSuperiorToDto_givenEmployee_shouldReturnSuperiorToDto(){
        //arrange
        Employee superior = new Employee();
        superior.setId(1L);
        superior.setName("Mark");
        superior.setSurname("Poe");
        superior.setBirthday(LocalDate.of(1999, 12,12));

        Employee employee = new Employee();
        employee.setId(2L);
        employee.setName("John");
        employee.setSurname("Doe");
        employee.setBirthday(LocalDate.of(1999, 12,12));
        employee.setSuperior(superior);

        SuperiorDto expectedSuperiorDto = new SuperiorDto(1L, superior.getName() + " " + superior.getSurname());
        //act
        SuperiorDto actualSuperiorDto = dtoMapper.mapSuperiorToDto(employee);
        //assert
        assertEquals(expectedSuperiorDto,actualSuperiorDto);
    }

    @Test
    void mapAllSubordinates_givenEmployee_shouldReturnSubordinateDtoList(){
        //arrange
        Employee subordinate = new Employee();
        subordinate.setId(1L);
        subordinate.setName("Mark");
        subordinate.setSurname("Poe");
        subordinate.setBirthday(LocalDate.of(1999, 12,12));

        Employee employee = new Employee();
        employee.setId(2L);
        employee.setName("John");
        employee.setSurname("Doe");
        employee.setBirthday(LocalDate.of(1999, 12,12));
        employee.setSubordinates(List.of(subordinate));

        SubordinateDto expectedSubordinateDto = new SubordinateDto(1L, subordinate.getName() + " " + subordinate.getSurname());
        //act
        List<SubordinateDto> actualSubordinateDto = dtoMapper.mapAllSubordinates(employee);
        //assert
        assertEquals(List.of(expectedSubordinateDto),actualSubordinateDto);
    }

    @Test
    void mapListEmployeeToDto_givenEmployeeList_shouldReturnEmployeeDtoList(){
        //arrange
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setSurname("Doe");
        employee.setBirthday(LocalDate.of(1999, 12,12));

        EmployeeDto employeeDto = new EmployeeDto(1L,"John","Doe",
                LocalDate.of(1999, 12,12), null,null, null);

        List<EmployeeDto> expectedEmployeeDtoList = List.of(employeeDto);
        //act
        List<EmployeeDto> actualEmployeeDtoList = dtoMapper.mapListEmployeeToDto(List.of(employee));
        //assert
        assertEquals(expectedEmployeeDtoList,actualEmployeeDtoList);
    }
}