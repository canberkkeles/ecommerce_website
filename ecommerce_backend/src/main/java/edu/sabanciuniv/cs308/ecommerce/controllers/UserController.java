package edu.sabanciuniv.cs308.ecommerce.controllers;

import edu.sabanciuniv.cs308.ecommerce.data_model.*;
import edu.sabanciuniv.cs308.ecommerce.entities.User;
import edu.sabanciuniv.cs308.ecommerce.exceptions.*;
import edu.sabanciuniv.cs308.ecommerce.login_system.LoginDto;
import edu.sabanciuniv.cs308.ecommerce.login_system.TfaLoginDto;
import edu.sabanciuniv.cs308.ecommerce.login_system.Two_Factor_Authentication_Service;
import edu.sabanciuniv.cs308.ecommerce.repositories.UserRepository;
import edu.sabanciuniv.cs308.ecommerce.services.UserService;
import edu.sabanciuniv.cs308.ecommerce.services.UserVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    Two_Factor_Authentication_Service tfa_login;

    @Autowired
    UserVerificationService verificationService;

    @Autowired
    UserRepository userRepository;


    @GetMapping("/users")
    public ResponseEntity<?> getUsers(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(HttpServletRequest request, HttpServletResponse response, @RequestBody SignupDTO user) {
        try {
            User signedupUser = userService.signupUser(user);
            verificationService.setUser(signedupUser);
            verificationService.saveUser(signedupUser);
            verificationService.sendVerificationCode();
            return new ResponseEntity<>(signedupUser, HttpStatus.OK);
        } catch (SignupException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> LoginUser(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginDto user) {
        try {
            User loggedinUser = userService.loginUser(user);
            //TFA CODE STARTS HERE
            tfa_login.setUser(loggedinUser);
            tfa_login.saveUser(loggedinUser);
            tfa_login.sendAuthenticationCode();
            return new ResponseEntity<>(loggedinUser, HttpStatus.OK);
        } catch (LoginException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/updateuser")
    public ResponseEntity<?> updateUser(HttpServletRequest request, HttpServletResponse response, @RequestBody userUpdateDto user) {
        try {
            User updatedUser = userService.updateUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (LoginException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/updateuser/updatepassword")
    public ResponseEntity<?> updateUserPassword(HttpServletRequest request, HttpServletResponse response, @RequestBody PasswordChangeDto dto) {
        try {
            User updatedUser = userService.updateUserPassword(dto);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (LoginException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }



    @PostMapping("/2fa")
    public ResponseEntity<?> check2FA(HttpServletRequest request, HttpServletResponse response, @RequestBody TfaLoginDto dto) {
            try {
                User tfaUser = userRepository.findUserById(dto.getUserId());
                String tfaCode = tfa_login.checkSixDigitCode(dto.getCode(), tfaUser);
                return new ResponseEntity<>(tfaCode, HttpStatus.OK);
        }
            catch (TwoFaException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/verification/{verificationCode}")
    public ResponseEntity<?> checkVerificationCode(HttpServletRequest request, HttpServletResponse response, @PathVariable("verificationCode") String verificationCode) throws VerificationException {
        try {
            String verifyUser = verificationService.checkVerificationCode(verificationCode);
            return new ResponseEntity<>(verifyUser, HttpStatus.OK);
        } catch (VerificationException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }







}
