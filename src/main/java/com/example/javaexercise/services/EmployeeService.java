package com.example.javaexercise.services;

import com.example.javaexercise.dtos.createEmployeeDto;
import com.example.javaexercise.models.Employee;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final OrganizationService organizationService;

    public EmployeeService(EmployeeRepository employeeRepository, OrganizationService organizationService) {
        this.employeeRepository = employeeRepository;
        this.organizationService = organizationService;
    }

   public Optional<Employee> findById(Long employeeId){
        return employeeRepository.findById(employeeId);
   }

   public void createEmployee(createEmployeeDto employeeDTO){
        Employee newEmployee = new Employee();
        newEmployee.setName(employeeDTO.name());
        newEmployee.setSurname(employeeDTO.surname());
        newEmployee.setBirthday(employeeDTO.birthday());

        employeeRepository.save(newEmployee);
   }

   public void deleteEmployee(Long employeeId){
        employeeRepository.deleteById(employeeId);
   }

   public void setSuperior(Long superiorId, Long subordinateId){
        Employee superior = findById(superiorId).get();
        Employee subordinate = findById(subordinateId).get();
        if(superior.equals(subordinate)){
           throw new RuntimeException("subordinate and superior cannot have same Entity.");
        }
        superior.addToSubordinates(subordinate);
        subordinate.setSuperior(superior);
        employeeRepository.save(superior);
        employeeRepository.save(subordinate);
   }

   public void assignEmployeeToOrganization(Long employeeId, String organizationName){
       Optional<Employee> optEmployee = findById(employeeId);
       Optional<Organization> optOrganization = organizationService.findByName(organizationName);
       Employee employee = optEmployee.get();
       Organization organization = optOrganization.get();

       organization.getEmployees().add(employee);
       organizationService.save(organization);

       employee.setOrganization(organization);
       employeeRepository.save(employee);

   }
}
