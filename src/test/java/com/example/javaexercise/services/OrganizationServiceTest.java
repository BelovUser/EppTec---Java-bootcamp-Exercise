package com.example.javaexercise.services;

import com.example.javaexercise.dtos.CreateOrganizationDto;
import com.example.javaexercise.mappers.DtoMapper;
import com.example.javaexercise.models.Employee;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.repositories.EmployeeRepository;
import com.example.javaexercise.repositories.OrganizationRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrganizationServiceTest {

    @InjectMocks
    private OrganizationService organizationService;
    @Mock
    private OrganizationRepository organizationRepository;
    @Mock
    private DtoMapper dtoMapper;
    @Test
    void createOrganization_givenCreateOrganizationDto_saveOrganization() {
        //arrange
        CreateOrganizationDto createOrganizationDTO = new CreateOrganizationDto("name", "address");
        Organization organization = new Organization();
        organization.setName("name");
        organization.setAddress("address");

        when(dtoMapper.mapDtoToOrganization(createOrganizationDTO)).thenReturn(organization);
        //act
        organizationService.createOrganization(createOrganizationDTO);
        //assert
        verify(organizationRepository, times(1)).save(organization);
    }

    @Test
    void findByName_givenEmployeeId_whenEmployeeExist_returnEmployee() {
        //arrange
        Organization expectedOrganization = new Organization();
        expectedOrganization.setName("organizationName");
        expectedOrganization.setAddress("address");

        when(organizationRepository.findByName("organizationName")).thenReturn(Optional.of(expectedOrganization));
        //act
        Optional<Organization> actualOrganization = organizationService.findByName("organizationName");
        //assert
        assertEquals(actualOrganization.get(),expectedOrganization);
    }

    @Test
    void save_givenOrganization_saveOrganization() {
        //arrange
        Organization organization = new Organization();
        organization.setName("organizationName");
        organization.setAddress("address");
        //act
        organizationService.save(organization);
        //assert
        verify(organizationRepository, times(1)).save(organization);
    }
}