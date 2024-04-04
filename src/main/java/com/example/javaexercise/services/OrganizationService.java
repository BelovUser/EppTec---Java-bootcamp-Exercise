package com.example.javaexercise.services;

import com.example.javaexercise.dtos.EmployeeDTO;
import com.example.javaexercise.dtos.OrganizationDTO;
import com.example.javaexercise.dtos.createOrganizationDTO;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.repositories.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {

    private final OrganizationRepo organizationRepo;
    @Autowired
    public OrganizationService(OrganizationRepo organizationRepo) {
        this.organizationRepo = organizationRepo;
    }


    public void createOrganization(createOrganizationDTO createOrganizationDTO){
        Organization organization = new Organization();
        organization.setName(createOrganizationDTO.name());
        organization.setAddress(createOrganizationDTO.address());

        organizationRepo.save(organization);
    }

    public Optional<Organization> findByName(String name){
        return organizationRepo.findAllByName(name);
    }

    public void save(Organization organization){
        organizationRepo.save(organization);
    }

}
