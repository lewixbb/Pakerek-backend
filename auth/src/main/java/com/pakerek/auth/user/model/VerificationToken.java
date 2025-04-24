package com.pakerek.auth.user.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Verification_token")
public class VerificationToken {

    private static final int EXPIRATION = 24*60*60*1000;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType type;
    @ManyToOne(cascade = CascadeType.REFRESH)
//    @JoinColumn(name = "user_id", unique = true)
    private User user;
    private Date expiryDate;

    public VerificationToken(TokenType type, User user) {
        this.type = type;
        this.user = user;
        this.expiryDate = expirationDateCalculator();
        this.token = tokenGenerator();
    }

    private Date expirationDateCalculator(){
        return new Date(new Date().getTime() + EXPIRATION);
    }

    private String tokenGenerator(){
        return UUID.randomUUID().toString();
    }
}
