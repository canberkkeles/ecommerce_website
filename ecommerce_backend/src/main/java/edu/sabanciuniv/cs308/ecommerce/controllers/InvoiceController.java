package edu.sabanciuniv.cs308.ecommerce.controllers;


import edu.sabanciuniv.cs308.ecommerce.data_model.ResponseObject;
import edu.sabanciuniv.cs308.ecommerce.data_model.UserIdDto;
import edu.sabanciuniv.cs308.ecommerce.entities.*;
import edu.sabanciuniv.cs308.ecommerce.exceptions.ProductException;
import edu.sabanciuniv.cs308.ecommerce.exceptions.VerificationException;
import edu.sabanciuniv.cs308.ecommerce.login_system.InvoiceDto;
import edu.sabanciuniv.cs308.ecommerce.login_system.PaymentDto;
import edu.sabanciuniv.cs308.ecommerce.repositories.InvoiceRepo;
import edu.sabanciuniv.cs308.ecommerce.repositories.OrderRepository;
import edu.sabanciuniv.cs308.ecommerce.repositories.UserRepository;
import edu.sabanciuniv.cs308.ecommerce.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    UserRepository userrepo;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    InvoiceRepo invoiceRepo;

    @Autowired
    OrderRepository orderRepository;

    @PostMapping("/createinvoiceforuser")
    public ResponseEntity<?> createInvoiceForUser(HttpServletRequest request, HttpServletResponse response , @RequestBody InvoiceDto invoiceDto) {
        try {
            Invoice invoice = invoiceService.createInvoiceforUser(invoiceDto);
            User user = userrepo.findUserById(invoiceDto.getUserId());
            //invoiceRepo.save(invoice);
            List<CartP_Product> list = user.getCartP_productsList();
            //list.clear();
            //user.setCartP_productsList(list);
           // userrepo.save(user);
            return new ResponseEntity<>(invoice, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/userOrderHistory")
    public ResponseEntity<?> getUserOrderHistory(@RequestBody UserIdDto userIdDto, HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = userrepo.findUserById(userIdDto.getUserId());
            List<Order> orders = orderRepository.findByUser(user);
            return new ResponseEntity<>(orders , HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/userOrderHistory/{id}")
    public ResponseEntity<?> getUserOrder(@RequestBody UserIdDto userIdDto, HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long orderId) {
        try {
            User user = userrepo.findUserById(userIdDto.getUserId());
            Order order = invoiceService.getUserOrder(user, orderId);
            return new ResponseEntity<>(order , HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/userOrderHistory/{id}/invoice")
    public ResponseEntity<?> getUserInvoice(@RequestBody UserIdDto userIdDto, HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long orderId) {
        try {
            User user = userrepo.findUserById(userIdDto.getUserId());
            Invoice invoice = invoiceService.getUserInvoice(user, orderId);
            return new ResponseEntity<>(invoice , HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/get_invoice/{id}")
    public ResponseEntity<?> getUserOrder(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long orderId){
        try{
            return new ResponseEntity<>(invoiceService.getInvoiceStr(orderId),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }



}
