package com.example.javaexercise.mappers;

import com.example.javaexercise.dtos.*;
import com.example.javaexercise.models.Employee;
import com.example.javaexercise.models.Organization;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DtoMapper {
    public static EmployeeDto mapToEmployeeDTO(Employee employee){
        List<SubordinateDto> subordinates = mapAllSubordinates(employee);
        String organizationName = employee.getOrganization() == null? "none":employee.getOrganization().getName();
        String superior = employee.getSuperior() == null? "none":employee.getSuperior().getName();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String birthday = simpleDateFormat.format(employee.getBirthday());

        return new EmployeeDto(employee.getId(),
                employee.getName(),
                employee.getSurname(),
                birthday,
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

    private static List<SubordinateDto> mapAllSubordinates(Employee employee){
        return employee.getSubordinates().stream()
                .map(e -> new SubordinateDto(e.getId(), e.getName() + " " + e.getSurname()))
                .toList();
    }

    public List<EmployeeDto> mapListEmployeeToDto(List<Employee> employees) {
        return employees.stream()
                .map(DtoMapper::mapToEmployeeDTO)
                .toList();
    }
}
