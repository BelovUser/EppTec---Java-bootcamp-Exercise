package com.example.javaexercise.dtos;

import java.util.Date;
import java.util.List;

public record EmployeeDTO(Long id,
                          String name,
                          String username,
                          Date birthday,
                          String organizationName,
                          String superiorName,
                          List<String> subordinatesNames) {
}
