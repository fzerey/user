package com.fzerey.user.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fzerey.user.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
