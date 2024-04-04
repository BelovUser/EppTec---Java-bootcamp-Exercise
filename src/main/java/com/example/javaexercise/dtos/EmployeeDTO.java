package com.example.javaexercise.dtos;

import com.example.javaexercise.models.Organization;

import java.util.Date;
import java.util.List;

public record EmployeeDTO(Long id,
                          String name,
                          String username,
                          Date birthday,
                          String organizationName,
                          String supEmployeeName,
                          List<String> subEmployee) {
}
