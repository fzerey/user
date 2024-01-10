package com.fzerey.user.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditableEntity{

    public AuditableEntity() {
        this.createdAt = this.updatedAt = LocalDateTime.now();
        this.createdBy = "system";
    }

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
