package edu.sabanciuniv.cs308.ecommerce.interfaces;

import edu.sabanciuniv.cs308.ecommerce.data_model.CommentDto;
import edu.sabanciuniv.cs308.ecommerce.data_model.RatingDto;
import edu.sabanciuniv.cs308.ecommerce.entities.Comment;
import edu.sabanciuniv.cs308.ecommerce.entities.Product;
import edu.sabanciuniv.cs308.ecommerce.exceptions.FindUsernameException;
import edu.sabanciuniv.cs308.ecommerce.exceptions.ProductException;

import java.util.List;

public interface I_Comment_And_Rating_Service {
    Product rateProduct(RatingDto rating, Long productId) throws ProductException, FindUsernameException;
    Comment commentProduct(CommentDto comment, Long productId) throws ProductException, FindUsernameException;
    List<Comment> getAllValidatedComments(Long productId) throws ProductException;
}
