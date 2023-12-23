package com.fzerey.user.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fzerey.user.domain.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long>{
    public Optional<Group> findByName(String name);
}
