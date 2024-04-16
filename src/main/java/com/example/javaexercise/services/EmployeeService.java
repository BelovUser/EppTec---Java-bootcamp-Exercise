package com.example.javaexercise.services;

import com.example.javaexercise.dtos.CreateEmployeeDto;
import com.example.javaexercise.exceptions.CantAssignSameEmployeeException;
import com.example.javaexercise.exceptions.EmployeeLoopRelationshipException;
import com.example.javaexercise.exceptions.EntityNotFoundException;
import com.example.javaexercise.exceptions.NoParameterProvidedException;
import com.example.javaexercise.mappers.DtoMapper;
import com.example.javaexercise.models.Employee;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.repositories.EmployeeRepository;
import com.example.javaexercise.specifications.JpaSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final OrganizationService organizationService;
    private final DtoMapper dtoMapper;

    public EmployeeService(EmployeeRepository employeeRepository, OrganizationService organizationService, DtoMapper dtoMapper) {
        this.employeeRepository = employeeRepository;
        this.organizationService = organizationService;
        this.dtoMapper = dtoMapper;
    }

    public List<Employee> findEmployees(String name, String surname, Long id){
        if(name == null && surname == null && id == null){
            throw new NoParameterProvidedException("Provide at least one parameter.");
        }
        Specification<Employee> employeeSpecification = JpaSpecification.isContainingNameOrSurnameOrId(name,surname,id);
        List<Employee> employees = employeeRepository.findAll(employeeSpecification);
        if(employees.isEmpty()){
            throw new EntityNotFoundException("Couldn't find any Employee with given parameters");
        }
        return employees;
    }

   public Employee findById(Long employeeId){
        return employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("Couldn't found Employee with " + employeeId + " id."));
   }

   public void createEmployee(CreateEmployeeDto employeeDTO){
        Employee newEmployee = dtoMapper.mapDtoToEmployee(employeeDTO);
        employeeRepository.save(newEmployee);
   }

   public void deleteEmployee(Long employeeId){
        employeeRepository.deleteById(employeeId);
   }

   public void setSuperior(Long superiorId, Long subordinateId){
        Employee superior = findById(superiorId);
        Employee subordinate = findById(subordinateId);
        if(superior.getSuperior() != null && superior.getSuperior().equals(subordinate)){
            throw new EmployeeLoopRelationshipException("Subordinate and Superior cant have loop relationship with each other.");
        }
        if(superior.equals(subordinate)){
           throw new CantAssignSameEmployeeException("Subordinate and Superior cannot be the same Employee.");
        }
        superior.addToSubordinates(subordinate);
        subordinate.setSuperior(superior);
        employeeRepository.save(superior);
        employeeRepository.save(subordinate);
   }

   public void assignEmployeeToOrganization(Long employeeId, Long organizationId){
       Employee employee = findById(employeeId);
       Organization organization = organizationService.findById(organizationId);

       organization.getEmployees().add(employee);
       organizationService.save(organization);

       employee.setOrganization(organization);
       employeeRepository.save(employee);
   }
}
