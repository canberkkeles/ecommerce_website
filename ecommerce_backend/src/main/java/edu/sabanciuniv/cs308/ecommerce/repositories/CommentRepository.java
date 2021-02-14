package edu.sabanciuniv.cs308.ecommerce.repositories;

import edu.sabanciuniv.cs308.ecommerce.entities.Comment;
import edu.sabanciuniv.cs308.ecommerce.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment,Long> {

    List<Comment> findByIsValidatedFalse();
    List<Comment> findByProductAndIsValidatedTrue(Product product);
    List<Comment> findByProduct(Product product);
    Comment findByid(Long id);
    List<Comment> findAll();
}

