package edu.sabanciuniv.cs308.ecommerce.helpers;

import edu.sabanciuniv.cs308.ecommerce.entities.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailingSystemHelper {

    public void sendMail(User user, String subject, String mail){
        if (user != null && user.getEmail() != null) {
            final String eMail = user.getEmail();

            final String username = "authcodesender998@gmail.com";
            final String password = "SCsdazcan122pdf!";

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");


            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            ;
            try {

                Message message = new MimeMessage(session);
                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(eMail)
                );
                message.setSubject(subject);
                message.setText(mail);

                Transport.send(message);

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
