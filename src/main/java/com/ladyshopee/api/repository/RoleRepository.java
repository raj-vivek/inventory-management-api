package com.ladyshopee.api.repository;

import com.ladyshopee.api.model.ERole;
import com.ladyshopee.api.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}