package edu.sabanciuniv.cs308.ecommerce.helpers;

import net.bytebuddy.utility.RandomString;
import java.util.Random;

public class Login_System_Helpers {
    public static String createSixDigitAuthenticationCodeString() {
        Random sixDigitNumber = new Random();
        int number = sixDigitNumber.nextInt(899999);
        number = number + 100000;
        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    public String createRandomVerificationCodeString() {
        int length = 64;
        String verificationCode = RandomString.make(64);
        return verificationCode;
    }
}
