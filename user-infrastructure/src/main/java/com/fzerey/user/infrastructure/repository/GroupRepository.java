package com.fzerey.user.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fzerey.user.domain.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    public Optional<Group> findByName(String name);

    public Page<Group> findAll(Pageable pageable);

    @Query("SELECT g FROM Group g WHERE " +
            "LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    public Page<Group> findByNameLike(String name, Pageable pageable);
}
