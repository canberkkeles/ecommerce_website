package edu.sabanciuniv.cs308.ecommerce.interfaces;


import edu.sabanciuniv.cs308.ecommerce.entities.User;
import edu.sabanciuniv.cs308.ecommerce.exceptions.VerificationException;

public interface I_User_Verification_Service {
    String checkVerificationCode(String verificationCode) throws VerificationException;
    void sendVerificationCode();
}
