package com.example.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class MailUtilLocal {
    public static void sendMail(String to, String from, String subject, String body, boolean bodyIsHTML)
            throws MessagingException {
        // 1 - Cấu hình thuộc tính SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // 2 - Tạo phiên với xác thực
        final String username = "ngtrucc06@gmail.com"; // Thay bằng email Gmail của bạn
        final String password = "wpbnshlecufhqdoq\n"; // Thay bằng App Password của Gmail
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        session.setDebug(true); // Bật debug để kiểm tra lỗi

        // 3 - Tạo tin nhắn
        Message message = new MimeMessage(session);
        message.setSubject(subject);
        if (bodyIsHTML) {
            message.setContent(body, "text/html; charset=UTF-8");
        } else {
            message.setText(body); // Sử dụng setText chỉ với một tham số
        }

        // 4 - Thiết lập địa chỉ gửi và nhận
        Address fromAddress = new InternetAddress(from);
        Address toAddress = new InternetAddress(to);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

        // 5 - Gửi tin nhắn
        Transport.send(message);
    }
}