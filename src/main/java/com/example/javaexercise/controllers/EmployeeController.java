package com.example.javaexercise.controllers;

import com.example.javaexercise.dtos.EmployeeDTO;
import com.example.javaexercise.models.Employee;
import com.example.javaexercise.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/Employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping
    public ResponseEntity<?> getEmployeeById(@RequestParam Long id){
        Optional<Employee> employee = employeeService.findById(id);
        if(employee.isEmpty()){
            return ResponseEntity.badRequest().body("Could not find Employee by " + id + " id.");
        }
        return ResponseEntity.ok(employee.get());
    }

    @PostMapping
    @RequestMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTO employeeDTO){
        employeeService.createEmployee(employeeDTO);
        return ResponseEntity.ok("Employee" + employeeDTO.name() + " was created.");
    }

    @DeleteMapping
    @RequestMapping("/delete")
    public ResponseEntity<?> deleteEmployee(@RequestParam Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee with " + id + " id was deleted.");
    }

    @PutMapping
    @RequestMapping("/setSuperior")
    public ResponseEntity<?> setSupEmployee(@RequestParam Long supEmployeeId,@RequestParam Long subEmployeeId){
        Optional<Employee> optSubEmployee = employeeService.findById(subEmployeeId);
        Optional<Employee> optSupEmployee = employeeService.findById(supEmployeeId);

        if(optSupEmployee.isEmpty()){
            return ResponseEntity.badRequest().body("Could not find supEmployee with " + supEmployeeId + "id.");
        } else if(optSubEmployee.isEmpty()){
            return ResponseEntity.badRequest().body("Could not find subEmployee with " + subEmployeeId + "id.");
        }

        Employee subEmployee = employeeService.findById(subEmployeeId).get();
        Employee supEmployee = employeeService.findById(supEmployeeId).get();

        employeeService.setSupEmployee(supEmployeeId,subEmployeeId);
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


