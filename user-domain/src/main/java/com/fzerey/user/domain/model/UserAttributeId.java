package com.fzerey.user.domain.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserAttributeId implements Serializable {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "attribute_id")
    private Long attributeId;
}
