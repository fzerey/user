package com.fzerey.user.domain.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fzerey.user.shared.exceptions.token.InvalidTokenException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Transactional
@Table(name = "users")
public class User extends AuditableEntity{

    public User(String username, String email, String phoneNumber, Group group) {
        super();
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.group = group;
        this.subId = UUID.randomUUID().toString();
        this.isActive = true;
        this.isVerified = false;
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

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Column(name = "verification_code")
    private String verificationCode;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserAttribute> userAttributes = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Token> tokens = new HashSet<>();

    public void addAttribute(UserAttribute userAttribute) {
        if (userAttributes == null)
            userAttributes = new HashSet<>();
        userAttributes.add(userAttribute);
    }

    public void addToken(Token token) {
        if (tokens == null)
            tokens = new HashSet<>();
        tokens.add(token);
    }

    public void refreshToken(Token existingToken, Token newToken) throws InvalidTokenException {
        if(!existingToken.isValid())
            throw new InvalidTokenException();
            
        tokens.stream().filter(t -> t.getId().equals(existingToken.getId())).findFirst()
                .orElseThrow().setValid(false);
        tokens.add(newToken);
    }

    public void logout(){
        for(var token : tokens){
            token.setValid(false);
        }
    }
}
