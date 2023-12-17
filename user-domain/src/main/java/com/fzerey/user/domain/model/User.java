package com.fzerey.user.domain.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    public User(String username, String password, Group group) {
        this.username = username;
        this.password = password;
        this.group = group;
        this.userAttributes = new HashSet<UserAttribute>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserAttribute> userAttributes;

    public void addAttribute(UserAttribute userAttribute) {
        if (userAttributes == null)
            userAttributes = new HashSet<UserAttribute>();
        userAttributes.add(userAttribute);
    }
}
