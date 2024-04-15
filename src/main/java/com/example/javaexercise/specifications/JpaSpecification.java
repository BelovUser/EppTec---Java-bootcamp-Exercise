package com.example.javaexercise.specifications;

import com.example.javaexercise.models.Employee;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class JpaSpecification {

    private JpaSpecification() {}

    public static Specification<Employee> isContainingNameOrSurnameOrId(String name, String surname, Long id){
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(name != null){
                predicates.add(builder.like(builder.upper(root.get("name")), "%" + name.toUpperCase() + "%"));
            }
            if(surname != null){
                predicates.add(builder.like(builder.upper(root.get("surname")), "%" + surname.toUpperCase() + "%"));
            }
            if(id != null){
                predicates.add(builder.equal(root.get("id"), id));
            }
            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
