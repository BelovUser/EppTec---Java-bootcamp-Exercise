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
        // all parameters
        if (employeeId.isPresent() && name.isPresent() && surname.isPresent()) {
            List<Employee> employees = employeeService.findAllByNameAndSurnameAndId(name.get(), surname.get(), employeeId.get());
            if (employees.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(dtoMapper.mapListEmployeeToDto(employees));
        }
        // none parameters
        if (employeeId.isEmpty() && name.isEmpty() && surname.isEmpty()) {
            return ResponseEntity.badRequest().body("Provide at least one parameter.");
        }
        // id and name or surname
        if (employeeId.isPresent() && (name.isPresent() || surname.isPresent())) {
            String employeeName = name.orElse(null);
            String employeeSurname = surname.orElse(null);
            List<Employee> employees = employeeService.findAllByNameOrSurnameAndId(employeeName, employeeSurname, employeeId.get());
            if (employees.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(dtoMapper.mapListEmployeeToDto(employees));
        }
        // only id
        if (employeeId.isPresent()) {
            Optional<Employee> employee = employeeService.findById(employeeId.get());
            if (employee.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(dtoMapper.mapToEmployeeDTO(employee.get()));
        }
        // only name or only surname
        if ((name.isPresent() && surname.isEmpty()) || (surname.isPresent() && name.isEmpty())) {
            String employeeName = name.orElse(null);
            String employeeSurname = surname.orElse(null);
            List<Employee> employees = employeeService.findAllByNameOrSurname(employeeName, employeeSurname);
            if (employees.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(dtoMapper.mapListEmployeeToDto(employees));
        }
        // only name and surname
        if((name.isPresent() && surname.isPresent()) || employeeId.isEmpty()){
            String employeeName = name.get();
            String employeeSurname = surname.get();
            List<Employee> employees = employeeService.findAllByNameAndSurname(employeeName, employeeSurname);
            if (employees.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(dtoMapper.mapListEmployeeToDto(employees));
        }
        // invalid parameters
        return ResponseEntity.badRequest().body("Invalid parameters.");
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
        if(superiorId == subordinateId){
                return ResponseEntity.badRequest().body("Employee cannot be subordinate and superior to itself.");
        }

        Optional<Employee> optSubordinate = employeeService.findById(subordinateId);
        Optional<Employee> optSuperior = employeeService.findById(superiorId);

        if(optSuperior.isEmpty() || optSubordinate.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Employee subordinate = employeeService.findById(subordinateId).get();
        Employee superior = employeeService.findById(superiorId).get();
        try {
            employeeService.setSuperior(superiorId, subordinateId);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Employees cannot be in loop relationship with each other.");
        }
        return ResponseEntity.ok("Employee " + superior.getName() + " is set as Superior to " + subordinate.getName() + " Employee.");
    }

    @PutMapping("/organization")
    public ResponseEntity<?> setOrganization(@RequestParam Long employeeId, @RequestParam Long organizationId){
        Optional<Employee> optEmployee = employeeService.findById(employeeId);
        Optional<Organization> optOrganization = organizationService.findById(organizationId);

        if(optEmployee.isEmpty() || optOrganization.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        employeeService.assignEmployeeToOrganization(employeeId,organizationId);
        return ResponseEntity.ok("Employee " + optEmployee.get().getName() + " was assigned to " + optOrganization.get().getName() + " Organization.");
    }
}


