package com.example.javaexercise.mappers;

import com.example.javaexercise.dtos.EmployeeDto;
import com.example.javaexercise.dtos.OrganizationDto;
import com.example.javaexercise.models.Employee;
import com.example.javaexercise.models.Organization;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DtoMapper {
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
                .map(DtoMapper::mapToEmployeeDTO)
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
