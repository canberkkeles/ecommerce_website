package edu.sabanciuniv.cs308.ecommerce.services;

import edu.sabanciuniv.cs308.ecommerce.data_model.FilterDto;
import edu.sabanciuniv.cs308.ecommerce.data_model.SearchDTO;
import edu.sabanciuniv.cs308.ecommerce.entities.Comment;
import edu.sabanciuniv.cs308.ecommerce.entities.Product;
import edu.sabanciuniv.cs308.ecommerce.entities.Tag;
import edu.sabanciuniv.cs308.ecommerce.exceptions.ProductException;
import edu.sabanciuniv.cs308.ecommerce.interfaces.I_Product_Manager_Service;
import edu.sabanciuniv.cs308.ecommerce.login_system.ProductDto;
import edu.sabanciuniv.cs308.ecommerce.login_system.TagDto;
import edu.sabanciuniv.cs308.ecommerce.repositories.CommentRepository;
import edu.sabanciuniv.cs308.ecommerce.repositories.ProductRepository;
import edu.sabanciuniv.cs308.ecommerce.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Comparator.comparing;

@Component
public class ProductManagerService implements I_Product_Manager_Service {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    CommentRepository commentRepository;


    @Override
    public Product addProduct(ProductDto productDto) throws ProductException {
        if(productRepository.findByProductName(productDto.getProductName()) != null)
            throw new ProductException("There is a product with the same name.");
        else if(productDto.getProductName().length() < 1 || productDto.getProductName().length() > 50 || productDto.getProductDescription().length() < 1 || productDto.getProductDescription().length() > 250){
            throw new ProductException("Product name/description can't be null\n" +
                    "Length of product name has to be less than 50 characters\n" +
                    "Length of product description has to be less than 250 characters");
        }
        else{
            Product product = new Product();
            product.setProductName(productDto.getProductName());
            product.setProductDescription(productDto.getProductDescription());
            product.setProductPrice(productDto.getProductPrice());
            product.setAvgRating(0.0);
            product.setTotalRating(0);
            product.setRatingCount(0);
            product.setCampaign(false);
            product.setCampaignPercentage(0f);
            product.setProductImage(productDto.getProductImage());
            productRepository.save(product);
            return product;
        }
    }

    @Override
    public Product editProduct(ProductDto productDto, Long id) throws ProductException {
        Product product = productRepository.findByid(id);
        if(product == null)
            throw new ProductException("Product could not be found.");
        else{
            product.setProductName(productDto.getProductName());
            product.setProductDescription(productDto.getProductDescription());
            product.setProductPrice(productDto.getProductPrice());
            productRepository.save(product);
            return product;
        }
    }

    @Override
    public Product showProductDetails(Long id) throws ProductException{
        Product product = productRepository.findByid(id);
        if(product == null)
            throw new ProductException("Product could not be found.");
        else{
            return product;
        }
    }

    @Override
    public Product deleteProduct(Long id) throws ProductException{
        Product product = productRepository.findByid(id);
        if(product == null)
            throw new ProductException("Product could not be found.");
        else{
            List<Comment> commentList = commentRepository.findByProduct(product);
            for(Comment comment: commentList) {
                commentRepository.delete(comment);
            }
            productRepository.delete(product);
            return product;
        }
    }

    @Override
    public Product addTagToProduct(Long id, TagDto tagDto) throws ProductException {
        Product product = productRepository.findByid(id);
        Tag tag = tagRepository.findByName(tagDto.getTagName());
        if(product == null || tag == null)
            throw new ProductException("Product or Tag does not exist.");
        else if(product.getTagList().contains(tag))
            throw new ProductException("This product already has " + tag.getName() +" tag.");

        List<Tag> newProductTagsList = product.getTagList();
        newProductTagsList.add(tag);
        product.setTagList(newProductTagsList);
        tag.setTagUsed(true);
        productRepository.save(product); // Save the new tag to the product.
        tagRepository.save(tag); //Save the product to tag.
        return product;
    }

    @Override
    public Product deleteTagFromProduct(Long id, TagDto tagDto) throws ProductException {
        Product product = productRepository.findByid(id);
        Tag tag = tagRepository.findByName(tagDto.getTagName());
        if(product == null || tag != null)
            throw new ProductException("Product or Tag does not exist.");
        else if(!product.getTagList().contains(tag))
            throw new ProductException("This product does not have " + tag.getName() +" tag.");

        List<Tag> newProductTagsList = product.getTagList();
        newProductTagsList.remove(tag); // Remove from the list.
        product.setTagList(newProductTagsList);
        productRepository.save(product); // Delete the tag from the product.
        return product;
    }

    @Override
    public List<Product> getAllProducts() throws ProductException {
        List<Product> productList = productRepository.findAll();
        Collections.sort(productList, comparing(Product::getProductName));
        return productList;

    }

    @Override
    public Tag addNewTagToDatabase(TagDto tagDto) throws ProductException {
        if(tagRepository.findByName(tagDto.getTagName()) != null)
            throw new ProductException("There is a tag with the same name.");
        else{
            Tag tag = new Tag();
            tag.setName(tagDto.getTagName());
            tagRepository.save(tag);
            return tag;
        }
    }

    @Override
    public List<Tag> getAllTags() throws ProductException{
        List<Tag> tagList = tagRepository.findAll();
        if(tagList == null)
            throw new ProductException("There are no tags in the database. Please Add One.");
        else{
            return tagList;
        }
    }

    @Override
    public Tag deleteTagFromDatabase(TagDto tagDto) throws ProductException {
        Tag tag = tagRepository.findByName(tagDto.getTagName());
        if(tag == null)
            throw new ProductException("The tag you want to delete does not exist or already deleted.");
        else if(tag.isTagUsed())
            throw new ProductException("This tag is used by some products.");
        else{
            tagRepository.delete(tag);
            return tag;
        }
    }

    @Override
    public Comment validateComment(Long id) throws ProductException {
        Comment comment = commentRepository.findByid(id);
        if(comment == null)
            throw new ProductException("The comment could not be found.");
        else{
            comment.setValidated(true);
            commentRepository.save(comment);
            return comment;
        }
    }

    @Override
    public Comment showComment(Long id) throws ProductException {
        Comment comment = commentRepository.findByid(id);
        if(comment == null)
            throw new ProductException("The comment could not be found.");
        else{
            return comment;
        }
    }

    @Override
    public List<Comment> showAllInvalidComments() throws ProductException {
        List<Comment> commentList = commentRepository.findByIsValidatedFalse();
        if(commentList == null)
            throw new ProductException("There are no comments");
        else{
            return commentList;
        }
    }

    @Override
    public List<Product> filterProducts(FilterDto filterDto) throws ProductException{
        List<Product> productList = productRepository.findAll();
            List<Product> filteredProductList = new ArrayList<Product>();
            for(Product product: productList)
            {
                double rating = product.getAvgRating();
                double price = product.getProductPrice();
                if(rating >= filterDto.getMinRating() && rating <= filterDto.getMaxRating() && price >= filterDto.getMinPrice() && price <= filterDto.getMaxPrice())
                {
                    filteredProductList.add(product);
                }
            }
            if(filterDto.getSortingAttribute().equals("Name") && filterDto.getSortingType().equals("Ascending"))
            {
                Collections.sort(filteredProductList, comparing(Product::getProductName));
            }
            else if(filterDto.getSortingAttribute().equals("Name") && filterDto.getSortingType().equals("Descending"))
            {
                Collections.sort(filteredProductList, comparing(Product::getProductName).reversed());
            }
            else if(filterDto.getSortingAttribute().equals("Price") && filterDto.getSortingType().equals("Ascending"))
            {
                Collections.sort(filteredProductList, comparing(Product::getProductPrice));
            }
            else if(filterDto.getSortingAttribute().equals("Price") && filterDto.getSortingType().equals("Descending"))
            {
                Collections.sort(filteredProductList, comparing(Product::getProductPrice).reversed());
            }
            else if(filterDto.getSortingAttribute().equals("Rating") && filterDto.getSortingType().equals("Ascending"))
            {
                Collections.sort(filteredProductList, comparing(Product::getAvgRating));
            }
            else if(filterDto.getSortingAttribute().equals("Rating") && filterDto.getSortingType().equals("Descending"))
            {
                Collections.sort(filteredProductList, comparing(Product::getAvgRating).reversed());
            }
            return filteredProductList;
        }

    @Override
    public List<Product> searchProducts(SearchDTO search) {
        return productRepository.findByProductNameContainingOrProductDescriptionContaining(search.getQuery(),search.getQuery());
    }

    @Override
    public List<Product> searchCategory(SearchDTO search) {
        return productRepository.findByTagList_nameContaining(search.getQuery());
    }


    @Override
    public List<Product> searchCampaign(SearchDTO search) {
        return productRepository.findByProductNameContainingAndCampaignTrueOrProductDescriptionContainingAndCampaignTrue(search.getQuery(), search.getQuery());
    }

    public List<Tag> getTags(Long id) throws ProductException {
        Product p = productRepository.findByid(id);
        if(p == null){
            throw new ProductException("Product not found");
        }
        else{
            return p.getTagList();
        }
    }
}
