package com.example.javaexercise.controllers;

import com.example.javaexercise.dtos.createOrganizationDto;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.services.MappingService;
import com.example.javaexercise.services.OrganizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/Organization")
public class OrganizationController {

    private final OrganizationService organizationService;
    private final MappingService mappingService;
    public OrganizationController(OrganizationService organizationService, MappingService mappingService) {
        this.organizationService = organizationService;
        this.mappingService = mappingService;
    }

    @PostMapping
    @RequestMapping("/create")
    public ResponseEntity<?> createOrganization(@RequestBody createOrganizationDto createOrganizationDTO){
        organizationService.createOrganization(createOrganizationDTO);
        return ResponseEntity.ok("Organization " +  createOrganizationDTO.name() + " was created.");
    }

    @GetMapping
    public ResponseEntity<?> getOrganizationByName(@RequestParam String name){
        Optional<Organization> optOrganization = organizationService.findByName(name);
        if(optOrganization.isEmpty()){
            return ResponseEntity.badRequest().body("Could not find Organization with name " + name);
        }
        return ResponseEntity.ok(mappingService.mapToOrganizationDTO(optOrganization.get()));
    }
}
