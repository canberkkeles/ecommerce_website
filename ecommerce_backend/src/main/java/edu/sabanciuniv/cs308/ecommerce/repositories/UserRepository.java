package edu.sabanciuniv.cs308.ecommerce.repositories;

import edu.sabanciuniv.cs308.ecommerce.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {

    User findByEmail(String email);
    User findUserById(Long id);
    User findByUsername(String username);
    User findByVerificationCode(String verificationCode);
    User findByEmailAndPasswordAndActiveTrue(String email,String password);
    User findByUsernameAndPasswordAndActiveTrue(String username, String password);
    User findByEmailOrUsername(String email,String username);
    //User findUserByCart()

}
