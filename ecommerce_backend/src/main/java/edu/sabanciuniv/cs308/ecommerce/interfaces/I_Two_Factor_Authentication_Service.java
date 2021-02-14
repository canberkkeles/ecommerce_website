package edu.sabanciuniv.cs308.ecommerce.interfaces;

import edu.sabanciuniv.cs308.ecommerce.data_model.UserDAO;
import edu.sabanciuniv.cs308.ecommerce.entities.User;
import edu.sabanciuniv.cs308.ecommerce.exceptions.TwoFaException;

public interface I_Two_Factor_Authentication_Service {

    String checkSixDigitCode(String sixDigitCode, User tfaUser) throws TwoFaException;
    void sendAuthenticationCode();
}
