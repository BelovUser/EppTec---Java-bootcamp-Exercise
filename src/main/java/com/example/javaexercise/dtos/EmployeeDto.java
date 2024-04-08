package com.example.javaexercise.dtos;

import java.util.Date;
import java.util.List;

public record EmployeeDto(Long id,
                          String name,
                          String surname,
                          Date birthday,
                          String organizationName,
                          String superiorName,
                          List<SubordinateDto> subordinates) {
}
