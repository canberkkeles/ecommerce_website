package edu.sabanciuniv.cs308.ecommerce.services;

import edu.sabanciuniv.cs308.ecommerce.entities.User;
import edu.sabanciuniv.cs308.ecommerce.exceptions.LoginException;
import edu.sabanciuniv.cs308.ecommerce.exceptions.SignupException;
import edu.sabanciuniv.cs308.ecommerce.exceptions.VerificationException;
import edu.sabanciuniv.cs308.ecommerce.helpers.Login_System_Helpers;
import edu.sabanciuniv.cs308.ecommerce.interfaces.I_User_Verification_Service;
import edu.sabanciuniv.cs308.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class UserVerificationService implements I_User_Verification_Service {

    private final String verificationCode;
    private User user;
    private final Login_System_Helpers helper;

    public UserVerificationService() {
        helper = new Login_System_Helpers();
        verificationCode = helper.createRandomVerificationCodeString();
    }


    @Autowired
    UserRepository repo;

    public User getUser() {
        return user;
    }

    public void setUser(User newUser) {
        this.user = newUser;
    }

    public void saveUser(User newUser) throws SignupException
    {
        User databaseUser = newUser;
        databaseUser.setVerificationCode(this.getVerificationCode());
        if(databaseUser != null)
            newUser.setId(databaseUser.getId());
        repo.save(databaseUser);
    }

    @Override
    public String checkVerificationCode(String verificationCode) throws VerificationException {
        User verifyUser = repo.findByVerificationCode(verificationCode);
        if(verificationCode.equals(verifyUser.getVerificationCode()) && !verifyUser.isVerified()){
            user.setVerified(true);
            repo.save(user);
            return verificationCode;
        }
        else if(verifyUser.isVerified()) {
            throw new VerificationException("User is already verified.");
        }
        else {
            throw new VerificationException("False Code!");
        }
    }

    @Override
    public void sendVerificationCode() {
        if (this.user != null && this.user.getEmail() != null) {
            final String eMail = this.user.getEmail();

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
                message.setSubject("Verification Code");
                message.setText("Dear " + user.getUsername() + "," +
                        "\n\n Your Verification Link: http://localhost:3000/user/verification/" + this.verificationCode +
                        "\n\n Please go to this link to verify your account.");

                Transport.send(message);

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public String getVerificationCode() {
        return verificationCode;
    }
}
