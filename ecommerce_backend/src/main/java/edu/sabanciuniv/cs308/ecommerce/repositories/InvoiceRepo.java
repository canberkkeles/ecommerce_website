package edu.sabanciuniv.cs308.ecommerce.repositories;

import edu.sabanciuniv.cs308.ecommerce.entities.Invoice;
import edu.sabanciuniv.cs308.ecommerce.entities.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvoiceRepo extends CrudRepository<Invoice,Long> {

    List<Invoice> findAll();




}
