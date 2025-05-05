package com.pakerek.auth.user.service;

import com.pakerek.auth.exception.TokenExpiredException;
import com.pakerek.auth.exception.TokenNotFoundException;
import com.pakerek.auth.user.model.TokenType;
import com.pakerek.auth.user.model.User;
import com.pakerek.auth.user.model.VerificationToken;
import com.pakerek.auth.user.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public VerificationToken generateToken(TokenType type, User user){
        return verificationTokenRepository.save(new VerificationToken(type,user));
    }

    @Override
    public VerificationToken getActivationToken(String uuid) {
        return verifyToken(uuid,TokenType.REGISTRATION);
    }

    @Override
    public VerificationToken getPasswordChangeToken(String uuid){
        return verifyToken(uuid,TokenType.PASSWORD_CHANGE);
    }

    private VerificationToken verifyToken(String uuid, TokenType type){
        List<VerificationToken> tokens = verificationTokenRepository.findAllByTokenAndTypeOrderByExpiryDate(uuid, type);
        if(tokens.isEmpty()){
            throw new TokenNotFoundException();
        } else {
            VerificationToken token = tokens.get(0);
            tokenValidation(token);
            return token;
        }
    }

    @Override
    public void setTokenAsExpired(VerificationToken token){
        token.setExpiryDate(new Date(new Date().getTime()-1));
        verificationTokenRepository.save(token);
    }

    @Override
    public void tokenValidation(VerificationToken token){
        if(token.getExpiryDate().before(new Date())){
            throw new TokenExpiredException();
        }
    }
}
