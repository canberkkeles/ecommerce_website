package edu.sabanciuniv.cs308.ecommerce.controllers;

import edu.sabanciuniv.cs308.ecommerce.data_model.*;
import edu.sabanciuniv.cs308.ecommerce.entities.Comment;
import edu.sabanciuniv.cs308.ecommerce.entities.Product;
import edu.sabanciuniv.cs308.ecommerce.entities.Tag;
import edu.sabanciuniv.cs308.ecommerce.exceptions.FindUsernameException;
import edu.sabanciuniv.cs308.ecommerce.exceptions.ProductException;
import edu.sabanciuniv.cs308.ecommerce.services.CommentAndRatingService;
import edu.sabanciuniv.cs308.ecommerce.services.ProductManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductManagerService productManagerService;

    @Autowired
    CommentAndRatingService commentAndRatingService;

    @GetMapping("/")
    public ResponseEntity<?> getAllProducts(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Product> productList = productManagerService.getAllProducts();
            return new ResponseEntity<>(productList, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> getFilteredProducts(HttpServletRequest request, HttpServletResponse response, @RequestBody FilterDto filterDto) {
        try {
            List<Product> productList = productManagerService.filterProducts(filterDto);
            return new ResponseEntity<>(productList, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long productId)
    {
        try {
            Product product = productManagerService.showProductDetails(productId);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> rateProduct(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long productId, @RequestBody RatingDto ratingDto)
    {
        try {
            Product product = commentAndRatingService.rateProduct(ratingDto,productId);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (FindUsernameException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<?> getComments(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long productId)
    {
        try {
            List<Comment> commentList = commentAndRatingService.getAllValidatedComments(productId);
            return new ResponseEntity<>(commentList, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<?> postComments(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long productId, @RequestBody CommentDto commentDto)
    {
        try {
            Comment comment = commentAndRatingService.commentProduct(commentDto,productId);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (FindUsernameException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/search_products")
    public ResponseEntity<?> searchProducts(HttpServletRequest request, HttpServletResponse response, @RequestBody SearchDTO search){
        List<Product> products = productManagerService.searchProducts(search);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PostMapping("/search_category")
    public ResponseEntity<?> searchCategory(HttpServletRequest request, HttpServletResponse response, @RequestBody SearchDTO search){
        List<Product> products = productManagerService.searchCategory(search);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PostMapping("/search_campaign")
    public ResponseEntity<?> searchCampaign(HttpServletRequest request, HttpServletResponse response, @RequestBody SearchDTO search){
        List<Product> products = productManagerService.searchCampaign(search);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @GetMapping("/get_taglist/{id}")
    public ResponseEntity<?> getTagList(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long productId)
    {
        try {
            List<Tag> tags = productManagerService.getTags(productId);
            return new ResponseEntity<>(tags, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

}
