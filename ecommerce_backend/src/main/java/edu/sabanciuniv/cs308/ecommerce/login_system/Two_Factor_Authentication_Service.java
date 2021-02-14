package edu.sabanciuniv.cs308.ecommerce.login_system;

import edu.sabanciuniv.cs308.ecommerce.entities.User;
import edu.sabanciuniv.cs308.ecommerce.exceptions.LoginException;
import edu.sabanciuniv.cs308.ecommerce.exceptions.TwoFaException;
import edu.sabanciuniv.cs308.ecommerce.helpers.Login_System_Helpers;
import edu.sabanciuniv.cs308.ecommerce.interfaces.I_Two_Factor_Authentication_Service;
import edu.sabanciuniv.cs308.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class Two_Factor_Authentication_Service implements I_Two_Factor_Authentication_Service {
    private String code;
    private User user;
    private final Login_System_Helpers helper;

    @Autowired
    UserRepository repo;

    public Two_Factor_Authentication_Service() {
        helper = new Login_System_Helpers();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User newUser) {
        this.user = newUser;
    }

    public void saveUser(User newUser) throws LoginException
    {
        User databaseUser = newUser;
        this.code = helper.createSixDigitAuthenticationCodeString();
        databaseUser.setCode(this.getCode());
        if(databaseUser != null)
            newUser.setId(databaseUser.getId());
        repo.save(databaseUser);
    }

    public String getCode(){
        return code;
    }

    @Override
    public String checkSixDigitCode(String sixDigitCode, User tfaUser) throws TwoFaException {
        if(sixDigitCode.equals(tfaUser.getCode()))
            return tfaUser.getCode();
        else
            throw new TwoFaException("False Code!");
    }

    @Override
    public void sendAuthenticationCode() { //Sends authentication code mail to the user. USE THIS BEFORE THAN checkSixDigitCode().


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
                message.setSubject("Authentication Code");
                message.setText("Dear " + user.getUsername() + "," +
                        "\n\n Your Authentication Code: " + this.code +
                        "\n\n Please enter this code to login your account. ");

                Transport.send(message);

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
