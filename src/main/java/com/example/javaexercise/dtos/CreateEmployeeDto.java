package com.example.javaexercise.dtos;

import java.time.LocalDate;
import java.util.Date;

public record CreateEmployeeDto(String name,
                                String surname,
                                LocalDate birthday) {
}
