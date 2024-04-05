package com.example.javaexercise.dtos;

import java.util.Date;

public record CreateEmployeeDto(String name,
                                String surname,
                                Date birthday) {
}
