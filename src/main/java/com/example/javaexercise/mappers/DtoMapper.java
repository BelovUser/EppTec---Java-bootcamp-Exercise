package com.example.javaexercise.mappers;

import com.example.javaexercise.dtos.*;
import com.example.javaexercise.models.Employee;
import com.example.javaexercise.models.Organization;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DtoMapper {
    public static EmployeeDto mapToEmployeeDTO(Employee employee){
        List<SubordinateDto> subordinates = employee.getSubordinates().isEmpty()? null:mapAllSubordinates(employee);
        String organizationName = employee.getOrganization() == null? null:employee.getOrganization().getName();
        SuperiorDto superior = employee.getSuperior() == null? null:mapSuperiorToDto(employee);

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
    public Organization mapDtoToOrganization(CreateOrganizationDto createOrganizationDTO){
        Organization organization = new Organization();
        organization.setName(createOrganizationDTO.name());
        organization.setAddress(createOrganizationDTO.address());
        return organization;
    }

    public Employee mapDtoToEmployee(CreateEmployeeDto employeeDTO){
        Employee employee = new Employee();
        employee.setName(employeeDTO.name());
        employee.setSurname(employeeDTO.surname());
        employee.setBirthday(employeeDTO.birthday());
        return employee;
    }

    public static List<SubordinateDto> mapAllSubordinates(Employee employee){
        return employee.getSubordinates().stream()
                .map(e -> new SubordinateDto(e.getId(), e.getName() + " " + e.getSurname()))
                .toList();
    }

    public List<EmployeeDto> mapListEmployeeToDto(List<Employee> employees) {
        return employees.stream()
                .map(DtoMapper::mapToEmployeeDTO)
                .toList();
    }

    public static SuperiorDto mapSuperiorToDto(Employee employee){
        String superiorFullName = employee.getSuperior().getName() + " " + employee.getSuperior().getSurname();
        Long superiorId = employee.getSuperior().getId();
        return new SuperiorDto(superiorId,superiorFullName);
    }
}
