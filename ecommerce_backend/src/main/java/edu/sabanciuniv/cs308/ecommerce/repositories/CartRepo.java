package edu.sabanciuniv.cs308.ecommerce.repositories;

import edu.sabanciuniv.cs308.ecommerce.entities.CartP;
import edu.sabanciuniv.cs308.ecommerce.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartRepo extends CrudRepository<CartP,Long> {

    List<CartP> findAll();
    CartP findCartById(Long id);
    CartP findCartPByUser_id(Long id);
    CartP findCartPByUser(User user);




}
