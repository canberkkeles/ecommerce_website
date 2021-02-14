package edu.sabanciuniv.cs308.ecommerce.controllers;

import edu.sabanciuniv.cs308.ecommerce.data_model.ResponseObject;
import edu.sabanciuniv.cs308.ecommerce.data_model.UserIdDto;
import edu.sabanciuniv.cs308.ecommerce.entities.CartP;
import edu.sabanciuniv.cs308.ecommerce.entities.CartP_Product;
import edu.sabanciuniv.cs308.ecommerce.entities.Product;
import edu.sabanciuniv.cs308.ecommerce.entities.User;
import edu.sabanciuniv.cs308.ecommerce.exceptions.FindUsernameException;
import edu.sabanciuniv.cs308.ecommerce.exceptions.ProductException;
import edu.sabanciuniv.cs308.ecommerce.login_system.CartDto;
import edu.sabanciuniv.cs308.ecommerce.login_system.LoginDto;
import edu.sabanciuniv.cs308.ecommerce.repositories.CartRepo;
import edu.sabanciuniv.cs308.ecommerce.repositories.ProductRepository;
import edu.sabanciuniv.cs308.ecommerce.repositories.UserRepository;
import edu.sabanciuniv.cs308.ecommerce.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

@Autowired
    CartService cartService;

@Autowired
    UserRepository userrepo;

@Autowired
    CartRepo cartRepo;

@Autowired
    ProductRepository productRepo;

    @PostMapping("/{productId}")
    public ResponseEntity<?> addProductToCart(HttpServletRequest request, HttpServletResponse response , @RequestBody CartDto cartDto, @PathVariable("productId") Long id) {
        try {
            User user = userrepo.findUserById(cartDto.getUserId());
            CartP cart = cartService.addProducttoCart(cartDto,id);
            return new ResponseEntity<>(user.getCartP_productsList(), HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProductFromCart(HttpServletRequest request, HttpServletResponse response , @RequestBody CartDto cartDto, @PathVariable("productId") Long id) {
        try {
            User user = userrepo.findUserById(cartDto.getUserId());
            CartP cart = cartService.deleteProductFromCart(cartDto,id);
            List<CartP_Product> usercart = user.getCartP_productsList();
            return new ResponseEntity<>(user.getCartP_productsList(), HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/getcartwithcartid/{id}")
    public ResponseEntity<?> getCartWithCartId(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) throws ProductException {
        try{
        Long cartid = id;
        CartP cart = cartService.showCartDetailsbyUserId(cartid);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }


    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductListofUserCart(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
       try {
           User user = userrepo.findUserById(id);
           List<CartP_Product> usercart = user.getCartP_productsList();
           return new ResponseEntity<>(usercart, HttpStatus.OK);
       }
       catch(Exception e){
           return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
       }

    }

    @DeleteMapping("/")
    public ResponseEntity<?> clearCart(@RequestBody UserIdDto dto, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<CartP_Product> list = cartService.clearCart(dto);
            return new ResponseEntity<>(list,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }

    }


    @GetMapping("/totalofusercart/{username}")
    public ResponseEntity<?> getTotalofUsercart(@PathVariable("username") String username, HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = userrepo.findByUsername(username);
            List<CartP_Product> list = user.getCartP_productsList();
            int total = 0;
            for (CartP_Product cp : list) {

                total += cp.getQuantity() * (productRepo.findByProductName(cp.getProduct_name())).getProductPrice();

            }
            return new ResponseEntity<>(total, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }

    }

}
