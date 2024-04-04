package com.example.javaexercise.controllers;

import com.example.javaexercise.dtos.createEmployeeDTO;
import com.example.javaexercise.models.Employee;
import com.example.javaexercise.services.EmployeeService;
import com.example.javaexercise.services.MappingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/Employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final MappingService mappingService;

    public EmployeeController(EmployeeService employeeService, MappingService mappingService) {

        this.employeeService = employeeService;
        this.mappingService = mappingService;
    }
    @GetMapping
    public ResponseEntity<?> getEmployeeById(@RequestParam Long id){
        Optional<Employee> employee = employeeService.findById(id);
        if(employee.isEmpty()){
            return ResponseEntity.badRequest().body("Could not find Employee by " + id + " id.");
        }
        return ResponseEntity.ok(mappingService.mapToEmployeeDTO(employee.get()));
    }

    @PostMapping
    @RequestMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody createEmployeeDTO employeeDTO){
        employeeService.createEmployee(employeeDTO);
        return ResponseEntity.ok("Employee " + employeeDTO.name() + " was created.");
    }

    @DeleteMapping
    @RequestMapping("/delete")
    public ResponseEntity<?> deleteEmployee(@RequestParam Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee with " + id + " id was deleted.");
    }

    @PutMapping
    @RequestMapping("/setSuperior")
    public ResponseEntity<?> setSuperior(@RequestParam Long superiorId, @RequestParam Long subordinateId){
        Optional<Employee> optSubEmployee = employeeService.findById(subordinateId);
        Optional<Employee> optSupEmployee = employeeService.findById(superiorId);

        if(optSupEmployee.isEmpty()){
            return ResponseEntity.badRequest().body("Could not find superior with " + superiorId + "id.");
        } else if(optSubEmployee.isEmpty()){
            return ResponseEntity.badRequest().body("Could not find subordinate with " + subordinateId + "id.");
        }

        Employee subEmployee = employeeService.findById(subordinateId).get();
        Employee supEmployee = employeeService.findById(superiorId).get();

        employeeService.setSuperior(superiorId, subordinateId);
        return ResponseEntity.ok("Employee " + supEmployee.getName() + " is set as Superior to " + subEmployee.getName() + " Employee.");
    }

    @PutMapping
    @RequestMapping("/setOrganization")
    public ResponseEntity<?> setOrganization(@RequestParam Long employeeId, @RequestParam String orgName){
        try{
            employeeService.assignEmployeeToOrganization(employeeId, orgName);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Could not found Employee or/and Organization.");
        }
        Optional<Employee> optEmployee = employeeService.findById(employeeId);
        return ResponseEntity.ok("Employee " + optEmployee.get().getName() + " was assigned to " + orgName + " Organization.");
    }
}


