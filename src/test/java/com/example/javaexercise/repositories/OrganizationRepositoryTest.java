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
    void findByName_givenName_WhenOrganizationExist_returnOrganization() {
        //arrange
        Organization expectedOrganization = this.organization;
        //act
        Optional<Organization> actualOrganization = organizationRepository.findByNameIgnoreCase("organization");
        //assert
        assertEquals(actualOrganization.get(),expectedOrganization);
    }
}