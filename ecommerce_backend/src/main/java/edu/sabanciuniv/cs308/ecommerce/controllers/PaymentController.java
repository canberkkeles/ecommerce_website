package edu.sabanciuniv.cs308.ecommerce.controllers;


import edu.sabanciuniv.cs308.ecommerce.data_model.ResponseObject;
import edu.sabanciuniv.cs308.ecommerce.entities.CartP;
import edu.sabanciuniv.cs308.ecommerce.entities.Payment;
import edu.sabanciuniv.cs308.ecommerce.exceptions.ProductException;
import edu.sabanciuniv.cs308.ecommerce.login_system.CartDto;
import edu.sabanciuniv.cs308.ecommerce.login_system.PaymentDto;
import edu.sabanciuniv.cs308.ecommerce.repositories.UserRepository;
import edu.sabanciuniv.cs308.ecommerce.services.CartService;
import edu.sabanciuniv.cs308.ecommerce.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    UserRepository userrepo;

    @Autowired
    PaymentService paymentService;


    @PostMapping("/getpaymentfromuser")
    public ResponseEntity<?> getPaymentFromUser(HttpServletRequest request, HttpServletResponse response , @RequestBody PaymentDto paymentdto) {
        try {
            Payment payment = paymentService.getPaymentfromUser(paymentdto);
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }


}
