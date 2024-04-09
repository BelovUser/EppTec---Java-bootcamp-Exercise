package com.example.javaexercise.controllers;

import com.example.javaexercise.dtos.CreateOrganizationDto;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.mappers.DtoMapper;
import com.example.javaexercise.services.OrganizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/Organization")
public class OrganizationController {

    private final OrganizationService organizationService;
    private final DtoMapper dtoMapper;
    public OrganizationController(OrganizationService organizationService, DtoMapper dtoMapper) {
        this.organizationService = organizationService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrganization(@RequestBody CreateOrganizationDto createOrganizationDto){
        Optional<Organization> existingOrganization = organizationService.findByName(createOrganizationDto.name());
        if(existingOrganization.isPresent()){
            return ResponseEntity.ok("Organization with name " +  createOrganizationDto.name() + " already exist.");
        }
        organizationService.createOrganization(createOrganizationDto);
        return ResponseEntity.ok("Organization " +  createOrganizationDto.name() + " was created.");
    }

    @GetMapping("/byName")
    public ResponseEntity<?> getOrganizationByName(@RequestParam String organizationName){
        Optional<Organization> optOrganization = organizationService.findByName(organizationName);
        if(optOrganization.isEmpty()){
            return ResponseEntity.badRequest().body("Could not find Organization with name " + organizationName + ".");
        }
        return ResponseEntity.ok(dtoMapper.mapToOrganizationDTO(optOrganization.get()));
    }
}
