package com.example.javaexercise.dtos;

import java.util.List;

public record OrganizationDto(Long id,
                              String name,
                              String address,
                              List<EmployeeDto> employees) {
}
