package com.example.javaexercise.controllers;

import com.example.javaexercise.dtos.CreateOrganizationDto;
import com.example.javaexercise.mappers.DtoMapper;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.services.EmployeeService;
import com.example.javaexercise.services.OrganizationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class OrganizationControllerTest {
    private final String urlPath = "/api/v1/Organization";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DtoMapper dtoMapper;

    @MockBean
    private OrganizationService organizationService;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void createOrganization_givenCreateOrganizationDto_thenCreateOrganization() throws Exception {
        //arrange
        CreateOrganizationDto createOrganizationDto = new CreateOrganizationDto("Org","st.Peter 123");
        //act and assert
        mockMvc.perform(post(urlPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrganizationDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Organization " +  createOrganizationDto.name() + " was created."));
    }

    @Test
    void getOrganizationByName_givenOrganizationName_whenOrganizationExist_returnOrganization() throws Exception {
        //arrange
        Organization organization = new Organization();
        organization.setId(1L);
        organization.setName("Org");
        organization.setAddress("st.Peter 123");

        when(organizationService.findByName("Org")).thenReturn(Optional.of(organization));
        //act and assert
        mockMvc.perform(get(urlPath).param("organizationName", "Org"))
                .andExpect(status().isOk());
    }

    @Test
    void getOrganizationByName_givenOrganizationName_whenOrganizationNotExist_returnBadRequest() throws Exception {
        //arrange
        String organizationName = "Org";
        //act and assert
        mockMvc.perform(get(urlPath).param("organizationName", organizationName))
                .andExpect(status().isNotFound());
    }
}