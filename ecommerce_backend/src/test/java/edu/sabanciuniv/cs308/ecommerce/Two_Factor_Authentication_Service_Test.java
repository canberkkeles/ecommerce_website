package edu.sabanciuniv.cs308.ecommerce;

import edu.sabanciuniv.cs308.ecommerce.exceptions.TwoFaException;
import edu.sabanciuniv.cs308.ecommerce.login_system.Two_Factor_Authentication_Service;
import org.junit.*;
public class Two_Factor_Authentication_Service_Test {
    Two_Factor_Authentication_Service two_factor_authentication_service;
    String producedCode;
    String userCode = "1000000";
    @Before
    public void setUp() throws Exception {
        two_factor_authentication_service = new Two_Factor_Authentication_Service();
        producedCode = two_factor_authentication_service.getCode();
    }
}
