package com.pakerek.auth.user.repository;

import com.pakerek.auth.user.model.TokenType;
import com.pakerek.auth.user.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {

    Optional<VerificationToken> findByTokenAndType(String token, TokenType type);

    List<VerificationToken> findAllByTokenAndTypeOrderByExpiryDate(String token, TokenType type);
}
