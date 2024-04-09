package com.example.javaexercise.dtos;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public record EmployeeDto(Long id,
                          String name,
                          String surname,
                          LocalDate birthday,
                          String organizationName,
                          String superiorName,
                          List<SubordinateDto> subordinates) {
}
