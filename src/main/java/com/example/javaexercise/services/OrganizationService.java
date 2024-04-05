package com.example.javaexercise.services;

import com.example.javaexercise.dtos.CreateOrganizationDto;
import com.example.javaexercise.mappers.DtoMapper;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final DtoMapper dtoMapper;
    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository, DtoMapper dtoMapper) {
        this.organizationRepository = organizationRepository;
        this.dtoMapper = dtoMapper;
    }

    public void createOrganization(CreateOrganizationDto createOrganizationDTO){
        Organization organization = dtoMapper.mapDtoToOrganization(createOrganizationDTO);
        organizationRepository.save(organization);
    }

    public Optional<Organization> findByName(String name){
        return organizationRepository.findByName(name);
    }

    public void save(Organization organization){
        organizationRepository.save(organization);
    }

}
