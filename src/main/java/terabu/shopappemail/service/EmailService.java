package terabu.shopappemail.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import terabu.shopappemail.dto.CommentResponse;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Service
public class EmailService {
    @Value("${email}")
    private String username;
    @Value("${passwordEmail}")
    private String password;
    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public String send(CommentResponse commentResponse){

        System.out.println(commentResponse.toString());
        System.out.println(commentResponse.getComments());
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.yandex.ru.");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            //от кого
            message.setFrom(new InternetAddress(username));
            //кому
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(commentResponse.getEmail()));
            //Заголовок письма
            message.setSubject("Вы добавили комментарий");
            //Содержимое
            message.setText("Добавлен комментарий " + commentResponse.getComments());

            //Отправляем сообщение
            Transport.send(message);
            return "Email sent successfully";
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
//
}
