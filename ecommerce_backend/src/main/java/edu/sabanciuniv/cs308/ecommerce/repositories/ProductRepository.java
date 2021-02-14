package edu.sabanciuniv.cs308.ecommerce.repositories;

import edu.sabanciuniv.cs308.ecommerce.entities.Product;
import edu.sabanciuniv.cs308.ecommerce.entities.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {

    Product findByProductName(String productName);
    Product findByid(Long id);
    Product findAllById(Long id);
    List<Product> findAll();
    List<Product> findByCampaignTrue();
    List<Product> findByProductNameContainingOrProductDescriptionContaining(String name,String description);
    List<Product> findByTagList_nameContaining(String name);
    List<Product> findByProductNameContainingAndCampaignTrueOrProductDescriptionContainingAndCampaignTrue(String name,String description);
}
