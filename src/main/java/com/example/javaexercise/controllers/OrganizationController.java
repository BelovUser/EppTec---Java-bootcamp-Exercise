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
    public ResponseEntity<?> createOrganization(@RequestBody CreateOrganizationDto createOrganizationDTO){
        organizationService.createOrganization(createOrganizationDTO);
        return ResponseEntity.ok("Organization " +  createOrganizationDTO.name() + " was created.");
    }

    @GetMapping
    public ResponseEntity<?> getOrganizationByName(@RequestParam String name){
        Optional<Organization> optOrganization = organizationService.findByName(name);
        if(optOrganization.isEmpty()){
            return ResponseEntity.badRequest().body("Could not find Organization with name " + name);
        }
        return ResponseEntity.ok(dtoMapper.mapToOrganizationDTO(optOrganization.get()));
    }
}
