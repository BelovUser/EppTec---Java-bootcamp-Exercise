package com.example.javaexercise.services;

import com.example.javaexercise.dtos.CreateOrganizationDto;
import com.example.javaexercise.mappers.DtoMapper;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.repositories.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

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
        CreateOrganizationDto createOrganizationDTO = new CreateOrganizationDto("organizationName", "address");
        Organization organization = new Organization();
        organization.setName("organizationName");
        organization.setAddress("address");

        when(dtoMapper.mapDtoToOrganization(createOrganizationDTO)).thenReturn(organization);
        //act
        organizationService.createOrganization(createOrganizationDTO);
        //assert
        verify(organizationRepository, times(1)).save(organization);
    }

    @Test
    void findByName_givenEmployeeName_whenEmployeeExist_returnEmployee() {
        //arrange
        Organization expectedOrganization = new Organization();
        expectedOrganization.setName("organizationName");
        expectedOrganization.setAddress("address");

        when(organizationRepository.findByNameIgnoreCase("organizationName")).thenReturn(Optional.of(expectedOrganization));
        //act
        Organization actualOrganization = organizationService.findByName("organizationName");
        //assert
        assertEquals(actualOrganization,expectedOrganization);
    }

    @Test
    void findByName_givenUpperCaseEmployeeName_whenEmployeeExist_returnEmployee() {
        //arrange
        String searchName = "ORGANIZATIONAME";

        Organization expectedOrganization = new Organization();
        expectedOrganization.setName("organizationName");
        expectedOrganization.setAddress("address");

        when(organizationRepository.findByNameIgnoreCase(searchName)).thenReturn(Optional.of(expectedOrganization));
        //act
        Organization actualOrganization = organizationService.findByName(searchName);
        //assert
        assertEquals(actualOrganization,expectedOrganization);
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