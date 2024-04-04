package com.example.javaexercise.dtos;

import com.example.javaexercise.models.Employee;
import com.example.javaexercise.models.Organization;

import java.util.Date;
import java.util.List;

public record createEmployeeDTO(String name,
                                String username,
                                Date birthday,
                                Organization organization) {
}
