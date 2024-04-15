package com.example.javaexercise.repositories;

import com.example.javaexercise.models.Organization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {
    Optional<Organization> findByNameIgnoreCase(String name);
    @Query("SELECT o FROM Organization o WHERE UPPER(o.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    List<Organization> findAllByName(@Param("name") String name);
}
