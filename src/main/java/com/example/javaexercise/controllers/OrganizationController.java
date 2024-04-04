package com.example.javaexercise.controllers;

import com.example.javaexercise.dtos.createOrganizationDTO;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.services.MappingService;
import com.example.javaexercise.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organization")
public class OrganizationController {

    private final OrganizationService organizationService;
    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping
    @RequestMapping("/create")
    public ResponseEntity<?> createOrganization(@RequestBody createOrganizationDTO createOrganizationDTO){
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
