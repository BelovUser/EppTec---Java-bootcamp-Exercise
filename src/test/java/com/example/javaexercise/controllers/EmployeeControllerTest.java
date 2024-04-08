package com.example.javaexercise.controllers;

import com.example.javaexercise.dtos.CreateEmployeeDto;
import com.example.javaexercise.models.Employee;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.services.EmployeeService;
import com.example.javaexercise.services.OrganizationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    private String apiPath = "/api/v1/Employee";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private OrganizationService organizationService;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void getEmployeeById_givenEmployeeId_whenEmployeeExist_returnEmployee() throws Exception {
        //arrange
        Employee employee = new Employee();
        employee.setName("John");
        employee.setSurname("Doe");

        when(employeeService.findById(1L)).thenReturn(Optional.of(employee));
        //act and assert
        mockMvc.perform(get(apiPath+"/byId").param("employeeId", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"));
    }

    @Test
    void getEmployeeByNameAndSurname_givenNameAndUsername_whenEmployeeExist_returnEmployee() throws Exception {
        //arrange
        Employee employee = new Employee();
        employee.setName("John");
        employee.setSurname("Doe");

        when(employeeService.findByNameAndSurname("John","Doe")).thenReturn(Optional.of(employee));
        //act and assert
        mockMvc.perform(get(apiPath+"/byFullName")
                        .param("name", "John")
                        .param("surname","Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"));
    }

    @Test
    void createEmployee_givenCreateEmployeeDto_thenCreateEmployee() throws Exception {
        //arrange
        CreateEmployeeDto createEmployeeDto = new CreateEmployeeDto("John","Doe",null);
        //act and assert
        mockMvc.perform(post(apiPath+"/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createEmployeeDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee John was created."));
    }

    @Test
    void deleteEmployee_givenEmployeeId_whenEmployeeExist_deleteEmployee() throws Exception {
        //act and assert
        mockMvc.perform(delete(apiPath+"/delete")
                        .param("employeeId", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee with 1 id was deleted."));
    }

    @Test
    void setSuperior_givenSuperiorIdAndSubordinateId_whenBothExistAndAreNotTheSameEmployee_theSetSuperiorAndSubordinate() throws Exception {
        //arrange
        Employee superior = new Employee();
        superior.setId(1L);
        superior.setName("John");
        superior.setSurname("Doe");

        Employee subordinate = new Employee();
        subordinate.setId(2L);
        subordinate.setName("Mark");
        subordinate.setSurname("White");

        when(employeeService.findById(1L)).thenReturn(Optional.of(superior));
        when(employeeService.findById(2L)).thenReturn(Optional.of(subordinate));
        //act and assert
        mockMvc.perform(put(apiPath+"/setSuperior")
                .param("superiorId", String.valueOf(1L))
                .param("subordinateId", String.valueOf(2L)))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee " + superior.getName() + " is set as Superior to " + subordinate.getName() + " Employee."));
    }

    @Test
    void setOrganization_givenEmployeeIdAndOrganizationName_WhenBothExist_setOrganizationToEmployee() throws Exception {
        //arrange
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setSurname("Doe");

        Organization organization = new Organization();
        organization.setName("Org");
        organization.setAddress("St.Peter 123");

        when(employeeService.findById(1L)).thenReturn(Optional.of(employee));
        when(organizationService.findByName("Org")).thenReturn(Optional.of(organization));
        //act and assert
        mockMvc.perform(put(apiPath+"/setOrganization")
                        .param("employeeId", String.valueOf(1L))
                        .param("organizationName", "Org"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee " + employee.getName() + " was assigned to " + organization.getName() + " Organization."));
    }
}