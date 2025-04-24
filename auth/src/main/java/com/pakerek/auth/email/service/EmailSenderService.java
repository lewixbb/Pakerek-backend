package com.pakerek.auth.email.service;

import com.pakerek.auth.email.configuration.EmailCfg;
import com.pakerek.auth.user.model.User;
import com.pakerek.auth.user.model.VerificationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final EmailCfg emailCfg;

    @Value("${notification.activation-url}")
    private String activationUrl;

    @Value("${notification.pw-change-url}")
    private String pwChangeUrl;

    @Value("classpath:static/emailTemplates/acc-activator.html")
    Resource accActivatorTemplate;

    @Value("classpath:static/emailTemplates/pw-change.html")
    Resource pwChangeTemplate;

    public void sendActivation(User user, VerificationToken token){
        try{
            String template = Files.readString(accActivatorTemplate.getFile().toPath(), StandardCharsets.UTF_8);
            template = template.replace("http://", activationUrl + "/" + token.getToken());
            emailCfg.sendMail(user.getEmail(),template,"Aktywacja konta",true);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void sendPasswordChange(User user, VerificationToken token){
        try{
            String template = Files.readString(pwChangeTemplate.getFile().toPath(), StandardCharsets.UTF_8);
            template = template.replace("http://", pwChangeUrl + "/" + token.getToken());
            template = template.replace("użytkownika", user.getEmail());
            emailCfg.sendMail(user.getEmail(),template,"Zmiana hasła",true);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

//    private void sendMail()
}
