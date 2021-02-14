package edu.sabanciuniv.cs308.ecommerce.services;

import edu.sabanciuniv.cs308.ecommerce.data_model.CommentDto;
import edu.sabanciuniv.cs308.ecommerce.data_model.RatingDto;
import edu.sabanciuniv.cs308.ecommerce.entities.Comment;
import edu.sabanciuniv.cs308.ecommerce.entities.Product;
import edu.sabanciuniv.cs308.ecommerce.entities.User;
import edu.sabanciuniv.cs308.ecommerce.exceptions.FindUsernameException;
import edu.sabanciuniv.cs308.ecommerce.exceptions.ProductException;
import edu.sabanciuniv.cs308.ecommerce.interfaces.I_Comment_And_Rating_Service;
import edu.sabanciuniv.cs308.ecommerce.repositories.CommentRepository;
import edu.sabanciuniv.cs308.ecommerce.repositories.ProductRepository;
import edu.sabanciuniv.cs308.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentAndRatingService implements I_Comment_And_Rating_Service {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    @Override
    public Product rateProduct(RatingDto rating, Long productId) throws ProductException, FindUsernameException {
        Product product = productRepository.findByid(productId);
        if(product == null)
            throw new ProductException("Product could not be found.");
        else if(!userService.isUserVerified(rating.getUserId()))
        {
            throw new FindUsernameException("Your account is not verified! Please check your e-mail and verify your account.");
        }
        else if(product.getUsersThatRatedThisProduct().contains(rating.getUserId()))
            throw new ProductException("You already rated this product.");
        else{
            product.setTotalRating(product.getTotalRating() + rating.getRating());
            product.setRatingCount(product.getRatingCount() + 1);
            product.setAvgRating(Double.valueOf(product.getTotalRating()) / Double.valueOf(product.getRatingCount()));
            List<Long> list = product.getUsersThatRatedThisProduct();
            list.add(rating.getUserId());
            product.setUsersThatRatedThisProduct(list);
            productRepository.save(product);
            return product;
        }
    }

    @Override
    public Comment commentProduct(CommentDto comment, Long productId) throws ProductException, FindUsernameException {
        Product product = productRepository.findByid(productId);
        User user = userRepository.findUserById(comment.getUserId());
        if(product == null)
            throw new ProductException("Product could not be found.");
        else if(!userService.isUserVerified(comment.getUserId())) {
            throw new FindUsernameException("Your account is not verified! Please check your e-mail and verify your account.");
        }
        else if(product.getUsersThatCommentedThisProduct().contains(comment.getUserId()))
            throw new ProductException("You already commented this product.");
        else{
            Comment newComment = new Comment();
            newComment.setValidated(false);
            newComment.setCommentText(comment.getComment());
            newComment.setProduct(product);
            newComment.setUser(user);
            List<Long> list = product.getUsersThatCommentedThisProduct();
            list.add(comment.getUserId());
            product.setUsersThatCommentedThisProduct(list);
            commentRepository.save(newComment);
            return newComment;
        }
    }

    @Override
    public List<Comment> getAllValidatedComments(Long productId) throws ProductException{
        Product product = productRepository.findByid(productId);
        if(product == null)
            throw new ProductException("Product could not be found.");
        else
        {
            List<Comment> validatedCommentList = commentRepository.findByProductAndIsValidatedTrue(product);
            return validatedCommentList;
        }
    }
}
