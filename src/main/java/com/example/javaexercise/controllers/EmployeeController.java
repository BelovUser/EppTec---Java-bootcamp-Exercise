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
@RequestMapping("/api/v1/Employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final OrganizationService organizationService;
    private final DtoMapper dtoMapper;

    public EmployeeController(EmployeeService employeeService, OrganizationService organizationService, DtoMapper dtoMapper) {
        this.employeeService = employeeService;
        this.organizationService = organizationService;
        this.dtoMapper = dtoMapper;
    }
    @GetMapping("/byId")
    public ResponseEntity<?> getEmployeeById(@RequestParam Long employeeId){
        Optional<Employee> optEmployee = employeeService.findById(employeeId);
        if(optEmployee.isEmpty()){
            return ResponseEntity.badRequest().body("Could not find Employee by " + employeeId + " id.");
        }
        return ResponseEntity.ok(dtoMapper.mapToEmployeeDTO(optEmployee.get()));
    }
    @GetMapping("/allByNameOrSurname")
    public ResponseEntity<?> getAllEmployeesByNameOrSurname(@RequestParam String name, @RequestParam String surname){
        List<Employee> employees = employeeService.findAllByNameAndSurname(name, surname);
        return ResponseEntity.ok(dtoMapper.mapListEmployeeToDto(employees));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody CreateEmployeeDto employeeDTO){
        employeeService.createEmployee(employeeDTO);
        return ResponseEntity.ok("Employee " + employeeDTO.name() + " was created.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEmployee(@RequestParam Long employeeId){
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Employee with " + employeeId + " id was deleted.");
    }

    @PutMapping("/setSuperior")
    public ResponseEntity<?> setSuperior(@RequestParam Long superiorId, @RequestParam Long subordinateId){
        if(superiorId == subordinateId){
                return ResponseEntity.badRequest().body("Employee cannot be subordinate and superior to itself.");
        }

        Optional<Employee> optSubordinate = employeeService.findById(subordinateId);
        Optional<Employee> optSuperior = employeeService.findById(superiorId);

        if(optSuperior.isEmpty() && optSubordinate.isEmpty()){
            return ResponseEntity.badRequest().body("Could not find both employees.");
        } else if (optSubordinate.isEmpty()) {
            return ResponseEntity.badRequest().body("Could not find subordinate with " + subordinateId + " id.");
        } else if (optSuperior.isEmpty()) {
            return ResponseEntity.badRequest().body("Could not find superior with " + superiorId + " id.");
        }

        Employee subordinate = employeeService.findById(subordinateId).get();
        Employee superior = employeeService.findById(superiorId).get();

        employeeService.setSuperior(superiorId, subordinateId);
        return ResponseEntity.ok("Employee " + superior.getName() + " is set as Superior to " + subordinate.getName() + " Employee.");
    }

    @PutMapping("/setOrganization")
    public ResponseEntity<?> setOrganization(@RequestParam Long employeeId, @RequestParam String organizationName){
        Optional<Employee> optEmployee = employeeService.findById(employeeId);
        Optional<Organization> optOrganization = organizationService.findByName(organizationName);

        if(optEmployee.isEmpty() && optOrganization.isEmpty()){
            return ResponseEntity.badRequest().body("Could not find both employee and organization.");
        } else if (optOrganization.isEmpty()) {
            return ResponseEntity.badRequest().body("Could not find organization named " + organizationName + ".");
        } else if (optEmployee.isEmpty()) {
            return ResponseEntity.badRequest().body("Could not find employee with " + employeeId + " id.");
        }

        employeeService.assignEmployeeToOrganization(employeeId,organizationName);
        return ResponseEntity.ok("Employee " + optEmployee.get().getName() + " was assigned to " + organizationName + " Organization.");
    }
}


