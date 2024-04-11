package com.example.javaexercise.services;

import com.example.javaexercise.dtos.CreateEmployeeDto;
import com.example.javaexercise.mappers.DtoMapper;
import com.example.javaexercise.models.Employee;
import com.example.javaexercise.models.Organization;
import com.example.javaexercise.repositories.EmployeeRepository;
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

   public Optional<Employee> findById(Long employeeId){
        return employeeRepository.findById(employeeId);
   }

   public void createEmployee(CreateEmployeeDto employeeDTO){
        Employee newEmployee = dtoMapper.mapDtoToEmployee(employeeDTO);
        employeeRepository.save(newEmployee);
   }

   public void deleteEmployee(Long employeeId){
        employeeRepository.deleteById(employeeId);
   }

   public void setSuperior(Long superiorId, Long subordinateId){
        Employee superior = findById(superiorId).get();
        Employee subordinate = findById(subordinateId).get();
        if(superior.equals(subordinate)){
           throw new RuntimeException("subordinate and superior cannot be the same Entity.");
        }
        superior.addToSubordinates(subordinate);
        subordinate.setSuperior(superior);
        employeeRepository.save(superior);
        employeeRepository.save(subordinate);
   }

   public void assignEmployeeToOrganization(Long employeeId, Long organizationId){
       Optional<Employee> optEmployee = findById(employeeId);
       Optional<Organization> optOrganization = organizationService.findById(organizationId);
       Employee employee = optEmployee.get();
       Organization organization = optOrganization.get();

       organization.getEmployees().add(employee);
       organizationService.save(organization);

       employee.setOrganization(organization);
       employeeRepository.save(employee);
   }

   public List<Employee> findAllByNameOrSurnameOrId(String name, String surname, Long id){
        return employeeRepository.findAllByNameIgnoreCaseOrSurnameIgnoreCaseOrId(name,surname,id);
   }

   public List<Employee> findAllByNameAndSurnameAndId(String name, String surname, Long id){
        return employeeRepository.findAllByNameIgnoreCaseAndSurnameIgnoreCaseAndId(name,surname,id);
   }

   public List<Employee> findAllByNameOrSurnameAndId(String name, String surname, Long id){
        return employeeRepository.findAllByNameIgnoreCaseAndIdOrSurnameIgnoreCaseAndId(name, id, surname, id);
   }

   public List<Employee> findAllByNameOrSurname(String name, String surname){
        return employeeRepository.findAllByNameIgnoreCaseOrSurnameIgnoreCase(name,surname);
   }

   public List<Employee> findAllByNameAndSurname(String name, String surname){
        return employeeRepository.findAllByNameIgnoreCaseAndSurnameIgnoreCase(name,surname);
   }
}
