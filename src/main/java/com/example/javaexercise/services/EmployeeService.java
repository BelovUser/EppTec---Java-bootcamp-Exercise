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
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
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

    public List<Employee> findEmployees(Optional<String> name, Optional<String> surname, Optional<Long> id){
        String employeeName = name.orElse(null);
        String employeeSurname = surname.orElse(null);
        Long employeeId = id.orElse(null);
        //all parameters
        if (employeeId !=null && employeeName != null && employeeSurname != null) {
            List<Employee> employees = findAllByNameAndSurnameAndId(employeeName, employeeSurname, employeeId);
            return employees;
        }
        // none parameters
        if (employeeId ==null && employeeName == null && employeeSurname == null) {
            throw new NoParameterProvidedException("Provide at least one parameter.");
        }
        // id and name or surname
        if (employeeId != null && (employeeName != null || employeeSurname != null)) {
            List<Employee> employees = findAllByNameOrSurnameAndId(employeeName, employeeSurname, employeeId);
            return employees;
        }
        // only id
        if (id.isPresent()) {
            Employee employee = findById(id.get());
            return List.of(employee);
        }
        // only name or only surname
        if ((name.isPresent() && surname.isEmpty()) || (surname.isPresent() && name.isEmpty())) {
            List<Employee> employees = findAllByNameOrSurname(employeeName, employeeSurname);
            return employees;
        }
        // only name and surname
        if((name.isPresent() && surname.isPresent()) || id.isEmpty()){
            List<Employee> employees = findAllByNameAndSurname(employeeName, employeeSurname);
            return employees;
        }
        throw new InvalidParameterException("Invalid parameters provided.");
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

   public List<Employee> findAllByNameOrSurnameOrId(String name, String surname, Long id){
        List<Employee> employees = employeeRepository.findAllByNameOrSurnameOrId(name,surname,id);
        if(employees.isEmpty()){
            throw new EntityNotFoundException("Couldn't find any Employees with given parameters.");
        }
        return employees;
   }

   public List<Employee> findAllByNameAndSurnameAndId(String name, String surname, Long id){
        List<Employee> employees = employeeRepository.findAllByNameAndSurnameAndId(name,surname,id);
        if(employees.isEmpty()){
           throw new EntityNotFoundException("Couldn't find any Employees with given parameters.");
        }
        return employees;
   }

   public List<Employee> findAllByNameOrSurnameAndId(String name, String surname, Long id){
        List<Employee> employees = employeeRepository.findAllByAndOrSurnameAndId(name, id, surname, id);
        if(employees.isEmpty()){
           throw new EntityNotFoundException("Couldn't find any Employees with given parameters.");
        }
        return employees;
   }

   public List<Employee> findAllByNameOrSurname(String name, String surname){
        List<Employee> employees = employeeRepository.findAllByNameOrSurname(name,surname);
        if(employees.isEmpty()){
           throw new EntityNotFoundException("Couldn't find any Employees with given parameters.");
        }
        return employees;
   }

   public List<Employee> findAllByNameAndSurname(String name, String surname){
        List<Employee> employees = employeeRepository.findAllByNameAndSurname(name,surname);
        if(employees.isEmpty()){
           throw new EntityNotFoundException("Couldn't find any Employees with given parameters.");
        }
        return employees;
   }
}
