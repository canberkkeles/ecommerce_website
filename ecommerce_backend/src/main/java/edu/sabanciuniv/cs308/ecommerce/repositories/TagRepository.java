package edu.sabanciuniv.cs308.ecommerce.repositories;


import edu.sabanciuniv.cs308.ecommerce.entities.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository  extends CrudRepository<Tag,Long> {

    Tag findByName(String tagName);
    List<Tag> findAll();
}
