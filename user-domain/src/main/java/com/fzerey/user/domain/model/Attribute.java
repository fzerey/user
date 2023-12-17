package com.fzerey.user.domain.model;

import jakarta.persistence.*;


import java.util.Set;

@Entity
@Table(name = "attributes")
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;

    @OneToMany(mappedBy = "attribute")
    private Set<UserAttribute> userAttributes;

    // Constructors, getters, and setters

    public Attribute() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Set<UserAttribute> getUserAttributes() {
        return userAttributes;
    }

    public void setUserAttributes(Set<UserAttribute> userAttributes) {
        this.userAttributes = userAttributes;
    }
}
