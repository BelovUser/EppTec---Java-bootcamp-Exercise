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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    private final String urlPath = "/api/v1/Employee";

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
        employee.setBirthday(LocalDate.of(1999, 12,12));

        when(employeeService.findById(1L)).thenReturn(Optional.of(employee));
        //act and assert
        mockMvc.perform(get(urlPath).param("employeeId", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"));
    }

    @Test
    void getEmployeeById_givenEmployeeId_whenEmployeeNotExist_expectStatusBadRequest() throws Exception {
        //arrange
        Long employeeId = 1L;
        //act and assert
        mockMvc.perform(get(urlPath).param("employeeId", String.valueOf(employeeId)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Could not find Employee by " + employeeId + " id."));
    }

    @Test
    void getAllEmployeesByNameOrSurname_givenNameAndUsername_whenEmployeeExist_returnEmployees() throws Exception {
        //arrange
        Employee employee = new Employee();
        employee.setName("John");
        employee.setSurname("Doe");
        employee.setBirthday(LocalDate.of(1999, 12,12));

        when(employeeService.findAllByNameAndSurname("John","Doe")).thenReturn(List.of(employee));
        //act and assert
        mockMvc.perform(get(urlPath)
                        .param("name", "John")
                        .param("surname","Doe"))
                .andExpect(status().isOk());
    }

    @Test
    void createEmployee_givenCreateEmployeeDto_thenCreateEmployee() throws Exception {
        //arrange
        CreateEmployeeDto createEmployeeDto = new CreateEmployeeDto("John","Doe",null);
        //act and assert
        mockMvc.perform(post(urlPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createEmployeeDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee John was created."));
    }

    @Test
    void deleteEmployee_givenEmployeeId_whenEmployeeExist_deleteEmployee() throws Exception {
        //act and assert
        mockMvc.perform(delete(urlPath)
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
        mockMvc.perform(put(urlPath +"/setSuperior")
                .param("superiorId", String.valueOf(1L))
                .param("subordinateId", String.valueOf(2L)))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee " + superior.getName() + " is set as Superior to " + subordinate.getName() + " Employee."));
    }

    @Test
    void setSuperior_givenSuperiorIdAndSubordinateId_whenSuperiorNotExist_expectStatusBadRequest() throws Exception {
        //arrange
        Long superiorId = 1L;

        Employee subordinate = new Employee();
        subordinate.setId(2L);
        subordinate.setName("Mark");
        subordinate.setSurname("White");

        when(employeeService.findById(2L)).thenReturn(Optional.of(subordinate));
        //act and assert
        mockMvc.perform(put(urlPath +"/setSuperior")
                        .param("superiorId", String.valueOf(superiorId))
                        .param("subordinateId", String.valueOf(2L)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Could not find superior with " + superiorId + " id."));
    }

    @Test
    void setSuperior_givenSuperiorIdAndSubordinateId_whenSubordinateNotExist_expectStatusBadRequest() throws Exception {
        //arrange
        Long subordinateId = 2L;

        Employee superior = new Employee();
        superior.setId(1L);
        superior.setName("John");
        superior.setSurname("Doe");

        when(employeeService.findById(1L)).thenReturn(Optional.of(superior));
        //act and assert
        mockMvc.perform(put(urlPath +"/setSuperior")
                        .param("superiorId", String.valueOf(1L))
                        .param("subordinateId", String.valueOf(subordinateId)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Could not find subordinate with " + subordinateId + " id."));
    }

    @Test
    void setSuperior_givenSuperiorIdAndSubordinateId_whenSubordinateAndSuperiorAreSameEmployee_expectStatusBadRequest() throws Exception {
        //arrange
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setSurname("Doe");

        when(employeeService.findById(1L)).thenReturn(Optional.of(employee));
        //act and assert
        mockMvc.perform(put(urlPath +"/setSuperior")
                        .param("superiorId", String.valueOf(employee.getId()))
                        .param("subordinateId", String.valueOf(employee.getId())))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Employee cannot be subordinate and superior to itself."));
    }

    @Test
    void setSuperior_givenSuperiorIdAndSubordinateId_whenBothExist_expectStatusBadRequest() throws Exception {
        //arrange
        Long superiorId = 1L;
        Long subordinateId = 2L;
        //act and assert
        mockMvc.perform(put(urlPath +"/setSuperior")
                        .param("superiorId", String.valueOf(superiorId))
                        .param("subordinateId", String.valueOf(subordinateId)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Could not find both employees."));
    }

    @Test
    void setOrganization_givenEmployeeIdAndOrganizationName_whenBothExist_setOrganizationToEmployee() throws Exception {
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
        mockMvc.perform(put(urlPath +"/setOrganization")
                        .param("employeeId", String.valueOf(1L))
                        .param("organizationName", "Org"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee " + employee.getName() + " was assigned to " + organization.getName() + " Organization."));
    }

    @Test
    void setOrganization_givenEmployeeIdAndOrganizationName_whenEmployeeNotExist_expectStatusBadRequest() throws Exception {
        //arrange
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setSurname("Doe");

        String organizationName = "Org";

        when(employeeService.findById(1L)).thenReturn(Optional.of(employee));
        //act and assert
        mockMvc.perform(put(urlPath +"/setOrganization")
                        .param("employeeId", String.valueOf(1L))
                        .param("organizationName", organizationName))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Could not find organization named " + organizationName + "."));
    }

    @Test
    void setOrganization_givenEmployeeIdAndOrganizationName_whenOrganizationNotExist_expectStatusBadRequest() throws Exception {
        //arrange
        Long employeeId = 1L;

        Organization organization = new Organization();
        organization.setName("Org");
        organization.setAddress("St.Peter 123");

        when(organizationService.findByName("Org")).thenReturn(Optional.of(organization));

        //act and assert
        mockMvc.perform(put(urlPath +"/setOrganization")
                        .param("employeeId", String.valueOf(employeeId))
                        .param("organizationName", "Org"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Could not find employee with " + employeeId + " id."));
    }

    @Test
    void setOrganization_givenEmployeeIdAndOrganizationName_whenBothNotExist_expectStatusBadRequest() throws Exception {
        //arrange
        Long employeeId = 1L;
        String organizationName = "Org";
        //act and assert
        mockMvc.perform(put(urlPath +"/setOrganization")
                        .param("employeeId", String.valueOf(employeeId))
                        .param("organizationName", organizationName))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Could not find both employee and organization."));
    }
}