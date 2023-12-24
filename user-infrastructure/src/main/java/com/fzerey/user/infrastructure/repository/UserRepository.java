package com.fzerey.user.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fzerey.user.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUsername(String username);

    public Optional<User> findBySubId(String subId);

    @Query("SELECT u FROM User u WHERE u.group.id = :groupId AND " +
            "(LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')))")
    public Page<User> findByGroupIdAndSearchByMultipleFields(Pageable pageable, @Param("groupId") Long groupId,
            @Param("query") String query);

    public Page<User> findByGroupId(Pageable pageable, Long groupId);

    public Page<User> findAll(Pageable pageable);

    @Query("SELECT u FROM User u WHERE " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%'))")
    public Page<User> findByQuery(@Param("query") String query, Pageable pageable);

}
