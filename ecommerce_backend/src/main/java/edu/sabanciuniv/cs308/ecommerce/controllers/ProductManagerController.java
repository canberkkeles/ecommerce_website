package edu.sabanciuniv.cs308.ecommerce.controllers;

import edu.sabanciuniv.cs308.ecommerce.data_model.ResponseObject;
import edu.sabanciuniv.cs308.ecommerce.entities.Comment;
import edu.sabanciuniv.cs308.ecommerce.entities.Product;
import edu.sabanciuniv.cs308.ecommerce.entities.Tag;
import edu.sabanciuniv.cs308.ecommerce.entities.User;
import edu.sabanciuniv.cs308.ecommerce.exceptions.ProductException;
import edu.sabanciuniv.cs308.ecommerce.exceptions.SignupException;
import edu.sabanciuniv.cs308.ecommerce.login_system.ProductDto;
import edu.sabanciuniv.cs308.ecommerce.login_system.TagDto;
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
@RequestMapping("/product_manager")

public class ProductManagerController {

    @Autowired
    ProductManagerService productManagerService;

    @GetMapping("/product")
    public ResponseEntity<?> getAllProducts(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Product> productList = productManagerService.getAllProducts();
            return new ResponseEntity<>(productList, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductDto productDto) {
        try {
            Product addedProduct = productManagerService.addProduct(productDto);
            return new ResponseEntity<>(addedProduct, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> editProduct(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductDto productDto, @PathVariable("id") Long id) {
        try {
            Product editedProduct = productManagerService.editProduct(productDto,id);
            return new ResponseEntity<>(editedProduct, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> showProduct(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long id) {
        try {
            Product product = productManagerService.showProductDetails(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/product/{id}")
    public ResponseEntity<?> addTagsToProduct(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long id, @RequestBody TagDto tagDto) {
        try {
            Product product = productManagerService.addTagToProduct(id,tagDto);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long id) {
        try {
            Product product = productManagerService.deleteProduct(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/tags")
    public ResponseEntity<?> addTag(HttpServletRequest request, HttpServletResponse response, @RequestBody TagDto tagDto) {
        try {
            Tag addedTag = productManagerService.addNewTagToDatabase(tagDto);
            return new ResponseEntity<>(addedTag, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/tags")
    public ResponseEntity<?> listTags(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Tag> listTags = productManagerService.getAllTags();
            return new ResponseEntity<>(listTags, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/tags")
    public ResponseEntity<?> deleteTag(HttpServletRequest request, HttpServletResponse response, @RequestBody TagDto tagDto) {
        try {
            Tag deletedTag = productManagerService.deleteTagFromDatabase(tagDto);
            return new ResponseEntity<>(deletedTag, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/comments")
    public ResponseEntity<?> listComments(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Comment> listInvalidComments = productManagerService.showAllInvalidComments();
            return new ResponseEntity<>(listInvalidComments, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<?> showComment(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long id) {
        try {
            Comment comment = productManagerService.showComment(id);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/comments/{id}")
    public ResponseEntity<?> validateComment(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long id) {
        try {
            Comment comment = productManagerService.validateComment(id);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

}
