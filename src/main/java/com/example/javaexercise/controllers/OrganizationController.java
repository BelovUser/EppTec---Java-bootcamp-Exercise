package com.example.javaexercise.controllers;

import com.example.javaexercise.dtos.OrganizationDTO;
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
    public ResponseEntity<?> createOrganization(@RequestBody OrganizationDTO organizationDTO){
        organizationService.createOrganization(organizationDTO);
        return ResponseEntity.ok("Organization " +  organizationDTO.name() + " was created.");
    }
}
