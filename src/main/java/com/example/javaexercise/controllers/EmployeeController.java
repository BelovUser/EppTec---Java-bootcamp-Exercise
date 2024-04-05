package com.example.javaexercise.controllers;

import com.example.javaexercise.dtos.CreateEmployeeDto;
import com.example.javaexercise.models.Employee;
import com.example.javaexercise.services.EmployeeService;
import com.example.javaexercise.mappers.DtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/Employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final DtoMapper dtoMapper;

    public EmployeeController(EmployeeService employeeService, DtoMapper dtoMapper) {

        this.employeeService = employeeService;
        this.dtoMapper = dtoMapper;
    }
    @GetMapping
    public ResponseEntity<?> getEmployeeById(@RequestParam Long employeeId){
        Optional<Employee> employee = employeeService.findById(employeeId);
        if(employee.isEmpty()){
            return ResponseEntity.badRequest().body("Could not find Employee by " + employeeId + " id.");
        }
        return ResponseEntity.ok(dtoMapper.mapToEmployeeDTO(employee.get()));
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

        if(optSuperior.isEmpty()){
            return ResponseEntity.badRequest().body("Could not find superior with " + superiorId + "id.");
        } else if(optSubordinate.isEmpty()){
            return ResponseEntity.badRequest().body("Could not find subordinate with " + subordinateId + "id.");
        }

        Employee subordinate = employeeService.findById(subordinateId).get();
        Employee superior = employeeService.findById(superiorId).get();

        employeeService.setSuperior(superiorId, subordinateId);
        return ResponseEntity.ok("Employee " + superior.getName() + " is set as Superior to " + subordinate.getName() + " Employee.");
    }

    @PutMapping("/setOrganization")
    public ResponseEntity<?> setOrganization(@RequestParam Long employeeId, @RequestParam String organizationName){
        try{
            employeeService.assignEmployeeToOrganization(employeeId, organizationName);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Could not found Employee or/and Organization.");
        }
        Optional<Employee> optEmployee = employeeService.findById(employeeId);
        return ResponseEntity.ok("Employee " + optEmployee.get().getName() + " was assigned to " + organizationName + " Organization.");
    }
}


