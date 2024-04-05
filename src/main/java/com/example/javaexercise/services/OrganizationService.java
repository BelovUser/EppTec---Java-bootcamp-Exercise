package com.example.javaexercise.services;

import com.example.javaexercise.dtos.createOrganizationDto;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }


    public void createOrganization(createOrganizationDto createOrganizationDTO){
        Organization organization = new Organization();
        organization.setName(createOrganizationDTO.name());
        organization.setAddress(createOrganizationDTO.address());

        organizationRepository.save(organization);
    }

    public Optional<Organization> findByName(String name){
        return organizationRepository.findAllByName(name);
    }

    public void save(Organization organization){
        organizationRepository.save(organization);
    }

}
