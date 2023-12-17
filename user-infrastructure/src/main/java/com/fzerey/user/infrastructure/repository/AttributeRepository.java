package com.fzerey.user.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fzerey.user.domain.model.Attribute;

public interface AttributeRepository extends JpaRepository<Attribute, Long>{
    public Optional<Attribute> findByKey(String key);
}
