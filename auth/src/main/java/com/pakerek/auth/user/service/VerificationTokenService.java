package com.pakerek.auth.user.service;

import com.pakerek.auth.user.model.TokenType;
import com.pakerek.auth.user.model.User;
import com.pakerek.auth.user.model.VerificationToken;

public interface VerificationTokenService {

    VerificationToken generateToken(TokenType type, User user);

    VerificationToken getActivationToken(String uuid);

    VerificationToken getPasswordChangeToken(String uuid);

    void setTokenAsExpired(VerificationToken token);

    void tokenValidation(VerificationToken token);
}
