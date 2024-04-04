package com.example.javaexercise.dtos;

import java.util.List;

public record OrganizationDTO(Long id,
                              String name,
                              String address,
                              List<EmployeeDTO> employees) {
}
