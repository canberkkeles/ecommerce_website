package edu.sabanciuniv.cs308.ecommerce.login_system;

public class LoginDto {

    private String password;
    private String emailOrUsername;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailOrUsername() {
        return emailOrUsername;
    }

    public void setEmailOrUsername(String emailOrUsername) {
        this.emailOrUsername = emailOrUsername;
    }
}
