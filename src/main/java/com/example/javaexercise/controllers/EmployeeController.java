package com.example.javaexercise.controllers;

import com.example.javaexercise.dtos.CreateEmployeeDto;
import com.example.javaexercise.models.Employee;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.services.EmployeeService;
import com.example.javaexercise.mappers.DtoMapper;
import com.example.javaexercise.services.OrganizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final OrganizationService organizationService;
    private final DtoMapper dtoMapper;

    public EmployeeController(EmployeeService employeeService, OrganizationService organizationService, DtoMapper dtoMapper) {
        this.employeeService = employeeService;
        this.organizationService = organizationService;
        this.dtoMapper = dtoMapper;
    }
    @GetMapping
    public ResponseEntity<?> getEmployee( @RequestParam(required = false) Optional<Long> employeeId,
                                          @RequestParam(required = false) Optional<String> name,
                                          @RequestParam(required = false) Optional<String> surname){
        List<Employee> employees = employeeService.findEmployees(name,surname,employeeId);
        return ResponseEntity.ok(dtoMapper.mapListEmployeeToDto(employees));
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody CreateEmployeeDto employeeDTO){
        employeeService.createEmployee(employeeDTO);
        return ResponseEntity.ok("Employee " + employeeDTO.name() + " was created.");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEmployee(@RequestParam Long employeeId){
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Employee with " + employeeId + " id was deleted.");
    }

    @PutMapping("/superior")
    public ResponseEntity<?> setSuperiorToSubordinate(@RequestParam Long superiorId, @RequestParam Long subordinateId){
        Employee subordinate = employeeService.findById(subordinateId);
        Employee superior = employeeService.findById(superiorId);
        employeeService.setSuperior(superiorId, subordinateId);
        return ResponseEntity.ok("Employee " + superior.getName() + " is set as Superior to " + subordinate.getName() + " Employee.");
    }

    @PutMapping("/organization")
    public ResponseEntity<?> setOrganization(@RequestParam Long employeeId, @RequestParam Long organizationId){
        Employee employee = employeeService.findById(employeeId);
        Organization organization = organizationService.findById(organizationId);

        employeeService.assignEmployeeToOrganization(employeeId,organizationId);
        return ResponseEntity.ok("Employee " + employee.getName() + " was assigned to " + organization.getName() + " Organization.");
    }
}


