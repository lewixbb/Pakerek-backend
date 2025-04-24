package com.pakerek.auth.email.configuration;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class EmailCfg {

    private final String username;
    private final String password;
    private Authenticator auth;
    private Session session;
    private Properties properties;

    public EmailCfg(@Value("${notification.username}") String username, @Value("${notification.password}") String password) {
        this.username = username;
        this.password = password;
        config();
    }

    private void config() {

        String smtpHost = "smtp.wp.pl";
        int smtpPort = 465;

        properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("SSLOnConnect","true");
        properties.put("mail.smtp.host",smtpHost);
        properties.put("mail.smtp.port",smtpPort);

        this.auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        };
    }

    private void refreshSession(){
        session = Session.getInstance(properties,auth);
    }

    public void  sendMail(String recipientEmail, String content, String subject, boolean onCreate){
        if(session == null){
            refreshSession();
        }
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(content,"text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        }catch (MessagingException e){
            e.getMessage();
            if(onCreate){
                refreshSession();
                sendMail(recipientEmail,content,subject,false);
            }
        }

    }
}
