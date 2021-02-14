package edu.sabanciuniv.cs308.ecommerce.interfaces;

import edu.sabanciuniv.cs308.ecommerce.data_model.FilterDto;
import edu.sabanciuniv.cs308.ecommerce.data_model.SearchDTO;
import edu.sabanciuniv.cs308.ecommerce.entities.Comment;
import edu.sabanciuniv.cs308.ecommerce.entities.Product;
import edu.sabanciuniv.cs308.ecommerce.entities.Tag;
import edu.sabanciuniv.cs308.ecommerce.exceptions.ProductException;
import edu.sabanciuniv.cs308.ecommerce.login_system.ProductDto;
import edu.sabanciuniv.cs308.ecommerce.login_system.TagDto;

import java.util.List;

public interface I_Product_Manager_Service {
    Product addProduct(ProductDto productDto) throws ProductException;
    Product editProduct(ProductDto productDto, Long id) throws ProductException;
    Product showProductDetails(Long id) throws ProductException;
    Product deleteProduct(Long id) throws ProductException;
    Product addTagToProduct(Long id, TagDto tagName) throws ProductException;
    Product deleteTagFromProduct(Long id, TagDto tagName) throws ProductException;
    List<Product> getAllProducts() throws ProductException;
    Tag addNewTagToDatabase(TagDto tagDto) throws ProductException;
    List<Tag> getAllTags() throws ProductException;
    Tag deleteTagFromDatabase(TagDto tagDto) throws ProductException;
    Comment validateComment(Long id) throws ProductException;
    Comment showComment(Long id) throws ProductException;
    List<Comment> showAllInvalidComments() throws ProductException;
    List<Product> filterProducts(FilterDto filterDto) throws ProductException;
    List<Product> searchProducts(SearchDTO search);
    List<Product> searchCategory(SearchDTO search);
    List<Product> searchCampaign(SearchDTO search);
}
