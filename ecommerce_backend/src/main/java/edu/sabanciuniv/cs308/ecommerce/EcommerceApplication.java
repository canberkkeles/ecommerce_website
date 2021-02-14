package edu.sabanciuniv.cs308.ecommerce;

import edu.sabanciuniv.cs308.ecommerce.entities.User;
import edu.sabanciuniv.cs308.ecommerce.login_system.LoginDto;
import edu.sabanciuniv.cs308.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication {

    @Autowired
    static UserRepository repo;

    public static void main(String[] args) {


        SpringApplication.run(EcommerceApplication.class, args);
    }

}
