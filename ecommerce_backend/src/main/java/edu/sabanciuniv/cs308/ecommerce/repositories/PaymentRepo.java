package edu.sabanciuniv.cs308.ecommerce.repositories;

import edu.sabanciuniv.cs308.ecommerce.entities.CartP;
import edu.sabanciuniv.cs308.ecommerce.entities.Payment;
import edu.sabanciuniv.cs308.ecommerce.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentRepo extends CrudRepository<Payment,Long> {

    List<Payment> findAll();




}
