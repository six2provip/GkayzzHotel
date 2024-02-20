package com.ps20652.Hotel.services.impl;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ps20652.Hotel.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {


    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendOtpEmail(String to, String otp) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
    
            helper.setTo(to);
            helper.setSubject("[GkayzHotel] Mã xác thực");
    
            // Thiết kế HTML và CSS cho tấm thiệp với mã OTP lớn
            String htmlContent = "<html>"
                    + "<head>"
                    + "<style>"
                    + "body { font-family: Arial, sans-serif; background-color: #F8F8F8; padding: 20px; }"
                    + ".card { background-color: #007BFF; padding: 20px; text-align: center; border-radius: 10px; color: #fff; }"
                    + ".otp { font-size: 36px; font-weight: bold; margin: 20px 0; }"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<div class='card'>"
                    + "<h2>Thông báo xác thực OTP</h2>"
                    + "<p>Mã xác thực của bạn là:</p>"
                    + "<div class='otp'>" + otp + "</div>"
                    + "</div>"
                    + "</body>"
                    + "</html>";
    
            helper.setText(htmlContent, true);
    
            javaMailSender.send(message);
        } catch (MessagingException e) {
            // Xử lý lỗi khi gửi email
            System.out.println("Lỗi khi gửi email: " + e.getMessage());
        }
    }
    

    @Override
    public String generateOtp() {
      // Tạo một UUID ngẫu nhiên
        UUID uuid = UUID.randomUUID();

        // Chuyển đổi UUID thành chuỗi
        String uuidString = uuid.toString();

        // Lấy toàn bộ số từ chuỗi UUID
        String numericString = uuidString.replaceAll("[^0-9]", "");

        // Lấy 6 ký tự đầu tiên từ chuỗi số
        String otp = numericString.substring(0, Math.min(6, numericString.length()));

        // Trả về OTP
        return otp;
    }

    @Override
    @Async
    public void sendAccountLinkedEmail(String email) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
    
            helper.setTo(email);
            String subject = "[GkayzHotel] Liên kết tài khoản thành công";
            helper.setSubject(subject);
    
            // Thiết kế HTML như một thiệp chúc mừng với màu vàng, đen và trắng
            String htmlContent = "<html>"
            + "<body style='font-family: Arial, sans-serif; background-color: #F8F8F8; padding: 20px;'>"
            + "<div style='background-color: #FFD700; padding: 20px; text-align: center; border-radius: 10px;'>"
            + "<h1 style='color: #333; margin-bottom: 10px;'>Chúc mừng!</h1>"
            + "<p style='font-size: 16px; color: #333;'>Tài khoản của bạn đã được liên kết thành công.</p>"
            + "</div>"
            + "<div style='text-align: center; margin-top: 20px;'>"
            + "<img src='https://t3.ftcdn.net/jpg/05/72/31/38/360_F_572313872_o7HR9THHs6NoLT8LJoNpop2aE7jrjJrv.png' alt='Chúc mừng' style='max-width: 100%; height: auto;'>"
            + "</div>"
            + "</body>"
            + "</html>";
    
            helper.setText(htmlContent, true);
    
            javaMailSender.send(message);
        } catch (MessagingException e) {
            // Xử lý lỗi khi gửi email
            System.out.println("Lỗi khi gửi email: " + e.getMessage());
        }
    }
    
    
    
}
