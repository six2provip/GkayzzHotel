package com.ps20652.Hotel.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;

public interface EmailService {
    
    public void sendOtpEmail(String to, String otp);

    public String generateOtp();
    
    public void sendAccountLinkedEmail(String email);
   


}
