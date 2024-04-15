package com.example.javaexercise.controllers;

import com.example.javaexercise.dtos.CreateOrganizationDto;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.mappers.DtoMapper;
import com.example.javaexercise.services.OrganizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/organization")
public class OrganizationController {

    private final OrganizationService organizationService;
    private final DtoMapper dtoMapper;
    public OrganizationController(OrganizationService organizationService, DtoMapper dtoMapper) {
        this.organizationService = organizationService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    public ResponseEntity<?> createOrganization(@RequestBody CreateOrganizationDto createOrganizationDto){
        organizationService.createOrganization(createOrganizationDto);
        return ResponseEntity.ok("Organization " +  createOrganizationDto.name() + " was created.");
    }

    @GetMapping
    public ResponseEntity<?> getOrganizationByName(@RequestParam String organizationName){
        List<Organization> organizations = organizationService.findAllByName(organizationName);
        return ResponseEntity.ok(dtoMapper.mapListOrganizationToDto(organizations));
    }
}
