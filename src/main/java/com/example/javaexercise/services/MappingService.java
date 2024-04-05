package com.example.javaexercise.services;

import com.example.javaexercise.dtos.EmployeeDto;
import com.example.javaexercise.dtos.OrganizationDto;
import com.example.javaexercise.models.Employee;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MappingService {
    private final OrganizationRepository organizationRepository;
    private final EmployeeService employeeService;
    @Autowired
    public MappingService(OrganizationRepository organizationRepository, EmployeeService employeeService) {
        this.organizationRepository = organizationRepository;
        this.employeeService = employeeService;
    }

    public static EmployeeDto mapToEmployeeDTO(Employee employee){
        List<String> subordinates =  getAllSubordinatesNames(employee);
        String organizationName = employee.getOrganization() == null? "none":employee.getOrganization().getName();
        String superior = employee.getSuperior() == null? "none":employee.getSuperior().getName();

        return new EmployeeDto(employee.getId(),
                employee.getName(),
                employee.getSurname(),
                employee.getBirthday(),
                organizationName,
                superior,
                subordinates
                );
    }

    public OrganizationDto mapToOrganizationDTO(Organization organization){
        List<EmployeeDto> employees = organization.getEmployees().stream()
                .map(MappingService::mapToEmployeeDTO)
                .toList();

        return new OrganizationDto(organization.getId(),
                organization.getName(),
                organization.getAddress(),
                employees);
    }

    private static List<String> getAllSubordinatesNames(Employee employee){
        return employee.getSubordinates().stream()
                .map(Employee::getName)
                .toList();
    }
}
