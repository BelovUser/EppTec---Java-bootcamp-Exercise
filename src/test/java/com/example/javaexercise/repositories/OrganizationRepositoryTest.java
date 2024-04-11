package com.example.javaexercise.repositories;

import com.example.javaexercise.models.Organization;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class OrganizationRepositoryTest {
    @Autowired
    private OrganizationRepository organizationRepository;
    private Organization organization;

    @BeforeEach
    void setUp() {
        this.organization = new Organization();
        organization.setName("organization");
        organization.setAddress("St.Peter 123");

        organizationRepository.save(organization);
    }

    @AfterEach
    void tearDown() {
        organizationRepository.delete(organization);
    }

    @Test
    void findByNameIgnoreCase_givenName_WhenOrganizationExist_returnOrganization() {
        //arrange
        String searchName = "organization";
        Organization expectedOrganization = this.organization;
        //act
        Optional<Organization> actualOrganization = organizationRepository.findByNameIgnoreCase(searchName);
        //assert
        assertEquals(actualOrganization.get(),expectedOrganization);
    }

    @Test
    void findByNameIgnoreCase_givenUpperCaseName_WhenOrganizationExist_returnOrganization() {
        //arrange
        String searchName = "ORGANIZATION";
        Organization expectedOrganization = this.organization;
        //act
        Optional<Organization> actualOrganization = organizationRepository.findByNameIgnoreCase(searchName);
        //assert
        assertEquals(actualOrganization.get(),expectedOrganization);
    }
}