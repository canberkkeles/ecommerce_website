package edu.sabanciuniv.cs308.ecommerce.repositories;

import edu.sabanciuniv.cs308.ecommerce.entities.Order;
import edu.sabanciuniv.cs308.ecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

    List<Order> findByUser(User user);
    Order findOrderById(long id);
}
