package com.example.javaexercise.services;

import com.example.javaexercise.dtos.EmployeeDTO;
import com.example.javaexercise.models.Employee;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.repositories.EmployeeRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final OrganizationService organizationService;

    public EmployeeService(EmployeeRepo employeeRepo, OrganizationService organizationService) {
        this.employeeRepo = employeeRepo;
        this.organizationService = organizationService;
    }

   public Optional<Employee> findById(Long employeeId){
        return employeeRepo.findById(employeeId);
   }

   public void createEmployee(EmployeeDTO employeeDTO){
        Employee newEmployee = new Employee();
        newEmployee.setName(employeeDTO.name());
        newEmployee.setUsername(employeeDTO.username());
        newEmployee.setBirthday(employeeDTO.birthday());

        employeeRepo.save(newEmployee);
   }

   public void deleteEmployee(Long employeeId){
        employeeRepo.deleteById(employeeId);
   }

   public void setSupEmployee(Long supEmployeeId, Long suBEmployeeId){
        Employee supEmployee = findById(supEmployeeId).get();
        Employee subEmployee = findById(suBEmployeeId).get();

        supEmployee.addToSubEmployees(supEmployee);
        subEmployee.setSupEmployee(supEmployee);
   }

   public boolean assignEmployeeToOrganization(Long employeeId, String organizationName){
       Optional<Employee> optEmployee = findById(employeeId);
       Optional<Organization> optOrganization = organizationService.findByName(organizationName);
       if(optEmployee.isEmpty() || optOrganization.isEmpty()){
           return false;
       }
       Employee employee = optEmployee.get();
       Organization organization = optOrganization.get();

       organization.getEmployees().add(employee);
       organizationService.save(organization);

       employee.setOrganization(organization);
       employeeRepo.save(employee);

       return true;
   }
}
