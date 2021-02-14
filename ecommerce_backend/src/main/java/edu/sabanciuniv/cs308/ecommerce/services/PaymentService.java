package edu.sabanciuniv.cs308.ecommerce.services;

import edu.sabanciuniv.cs308.ecommerce.entities.CartP;
import edu.sabanciuniv.cs308.ecommerce.entities.CartP_Product;
import edu.sabanciuniv.cs308.ecommerce.entities.Payment;
import edu.sabanciuniv.cs308.ecommerce.entities.User;
import edu.sabanciuniv.cs308.ecommerce.exceptions.ProductException;
import edu.sabanciuniv.cs308.ecommerce.helpers.MailingSystemHelper;
import edu.sabanciuniv.cs308.ecommerce.login_system.CartDto;
import edu.sabanciuniv.cs308.ecommerce.login_system.PaymentDto;
import edu.sabanciuniv.cs308.ecommerce.repositories.PaymentRepo;
import edu.sabanciuniv.cs308.ecommerce.repositories.ProductRepository;
import edu.sabanciuniv.cs308.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PaymentService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    ProductRepository productRepo;



    public Payment getPaymentfromUser(PaymentDto paymentDto) throws ProductException {
        if (userRepo.findUserById(paymentDto.getUserId()) == null) {
            throw new ProductException("User does not exist.");
        } else {
            User user = userRepo.findUserById(paymentDto.getUserId());
            Payment payment = new Payment();
            payment.setUser(user);
            payment.setNameandSurname(paymentDto.getName_surname());
            payment.setCreditCardNumber(paymentDto.getCreditCartNum());
            payment.setCVV2(paymentDto.getCvv2());
            payment.setExpiration_date_month((paymentDto.getExpiration_date_month()));
            payment.setExpiration_date_year(paymentDto.getExpiration_date_year());
            List<CartP_Product> list = user.getCartP_productsList();
            long total = 0;
            for (CartP_Product cp : list) {

                total += cp.getQuantity() * (productRepo.findByProductName(cp.getProduct_name())).getProductPrice();

            }
            payment.setTotalAmount(total);
            //list.clear();
            //user.setCartP_productsList(list);
            //userRepo.save(user);
            paymentRepo.save(payment);
            MailingSystemHelper helper = new MailingSystemHelper();
            helper.sendMail(user, "Payment #" +payment.getId() + " Completed", "Dear "+ user.getFirstName() +" " + user.getLastName() +","+ "\n\n Your payment is completed. " + payment.getTotalAmount() + "TL will be withdrawn from your account.");
            return payment;


        }
    }


}
