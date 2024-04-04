package com.example.javaexercise.repositories;

import com.example.javaexercise.models.Organization;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrganizationRepo extends CrudRepository<Organization, Long> {

    Optional<Organization> findAllByName(String name);
}
