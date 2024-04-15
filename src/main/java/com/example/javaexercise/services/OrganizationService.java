package com.example.javaexercise.services;

import com.example.javaexercise.dtos.CreateOrganizationDto;
import com.example.javaexercise.exceptions.EntityNotFoundException;
import com.example.javaexercise.exceptions.OrganizationAlreadyExistException;
import com.example.javaexercise.mappers.DtoMapper;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Optional<Organization> foundOrganization = organizationRepository.findByNameIgnoreCase(organization.getName());
        if(foundOrganization.isPresent()){
            throw new OrganizationAlreadyExistException("Organization " + organization.getName() + " already exists.");
        }
        organizationRepository.save(organization);
    }

    public Organization findByName(String name){
        return organizationRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find Organization named " + name + " ."));
    }

    public List<Organization> findAllByName(String name){
        List<Organization> organizations = organizationRepository.findAllByName(name);
        if(organizations.isEmpty()){
            throw new EntityNotFoundException("Couldn't find any Organization with name containing " + name + ".");
        }
        return organizations;
    }

    public Organization findById(Long id){
        return organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find Organization with " + id + " id."));
    }

    public void save(Organization organization){
        organizationRepository.save(organization);
    }

}
