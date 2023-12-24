package com.fzerey.user.domain.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Transactional
@Table(name = "users")
public class User {

    public User(String username, String password, String email, String phoneNumber, Group group) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.group = group;
        this.subId = UUID.randomUUID().toString();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sub_id")
    private String subId;

    @Column(name = "username")
    private String username;

    @Column(name = "hashed_password")
    private String hashedPassword;

    @Column(name = "salt")
    private byte[] salt;

    @Column(name ="email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserAttribute> userAttributes = new HashSet<UserAttribute>();

    public void addAttribute(UserAttribute userAttribute) {
        if (userAttributes == null)
            userAttributes = new HashSet<UserAttribute>();
        userAttributes.add(userAttribute);
    }
}
