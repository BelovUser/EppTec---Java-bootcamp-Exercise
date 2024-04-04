package com.example.javaexercise.services;

import com.example.javaexercise.dtos.EmployeeDTO;
import com.example.javaexercise.dtos.OrganizationDTO;
import com.example.javaexercise.models.Employee;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.repositories.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MappingService {
    private final OrganizationRepo organizationRepo;
    private final EmployeeService employeeService;
    @Autowired
    public MappingService(OrganizationRepo organizationRepo, EmployeeService employeeService) {
        this.organizationRepo = organizationRepo;
        this.employeeService = employeeService;
    }

    public static EmployeeDTO mapToEmployeeDTO(Employee employee){
        List<String> subEmployees =  getAllSubEmployeesNames(employee);
        String organizationName = employee.getOrganization().getName() == null? null:employee.getName();
        String supEmployee = employee.getSupEmployee() == null? null:employee.getName();
        return new EmployeeDTO(employee.getId(),
                employee.getName(),
                employee.getUsername(),
                employee.getBirthday(),
                supEmployee,
                organizationName,
                subEmployees
                );
    }

    public OrganizationDTO mapToOrganizationDTO(Organization organization){
        List<EmployeeDTO> employees = organization.getEmployees().stream()
                .map(MappingService::mapToEmployeeDTO)
                .toList();

        return new OrganizationDTO(organization.getId(),
                organization.getName(),
                organization.getAddress(),
                employees);
    }

    private static List<String> getAllSubEmployeesNames(Employee employee){
        return employee.getSubEmployees().stream()
                .map(Employee::getName)
                .toList();
    }
}
