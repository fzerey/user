package com.fzerey.user.domain.model;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users_attributes")
public class UserAttribute {

    
    public UserAttribute(Long attributeId, Long userId, String value) {
        this.id = new UserAttributeId(userId, attributeId);
        this.value = value;
    }

    @EmbeddedId
    private UserAttributeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("attributeId")
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    @Column(name = "value")
    private String value;

}