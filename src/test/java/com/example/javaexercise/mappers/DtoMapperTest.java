package com.example.javaexercise.mappers;

import com.example.javaexercise.dtos.CreateEmployeeDto;
import com.example.javaexercise.dtos.CreateOrganizationDto;
import com.example.javaexercise.dtos.EmployeeDto;
import com.example.javaexercise.dtos.OrganizationDto;
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
}