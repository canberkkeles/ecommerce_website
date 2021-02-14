package edu.sabanciuniv.cs308.ecommerce.controllers;

import edu.sabanciuniv.cs308.ecommerce.data_model.CampaignDTO;
import edu.sabanciuniv.cs308.ecommerce.data_model.OrderAddressChangeDto;
import edu.sabanciuniv.cs308.ecommerce.data_model.OrderStatusChangeDto;
import edu.sabanciuniv.cs308.ecommerce.data_model.ResponseObject;
import edu.sabanciuniv.cs308.ecommerce.entities.Invoice;
import edu.sabanciuniv.cs308.ecommerce.entities.Order;
import edu.sabanciuniv.cs308.ecommerce.entities.Product;
import edu.sabanciuniv.cs308.ecommerce.exceptions.OrderStatusException;
import edu.sabanciuniv.cs308.ecommerce.exceptions.ProductException;
import edu.sabanciuniv.cs308.ecommerce.services.SalesManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/sales_manager")
public class SalesManagerController {

    @Autowired
    SalesManagerService service;

    @PutMapping("/order/{id}")
    public ResponseEntity<?> updateOrderStatus(HttpServletRequest request, HttpServletResponse response,
                                               @RequestBody OrderStatusChangeDto ord, @PathVariable("id") long id){

        try {
            Order updatedOrder = service.changeOrderStatus(id,ord);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (OrderStatusException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()),HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/order")
    public ResponseEntity<?> getAllOrders(HttpServletRequest request, HttpServletResponse response){
        return new ResponseEntity<>(service.getAllOrders(),HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOrder(HttpServletRequest request, HttpServletResponse response,@PathVariable("id") long id){
        try {
            Order foundOrder = service.findOrder(id);
            return new ResponseEntity<>(foundOrder, HttpStatus.OK);
        } catch (OrderStatusException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()),HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<?> deleteProduct(HttpServletRequest request, HttpServletResponse response,@PathVariable("id") long id){
        try {
            Order foundOrder = service.findOrder(id);
            return new ResponseEntity<>(foundOrder, HttpStatus.OK);
        } catch (OrderStatusException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()),HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/order/{id}")
    public ResponseEntity<?> changeAddress(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long id, @RequestBody OrderAddressChangeDto newAddress){
        try {
            Order foundOrder = service.changeAddress(id,newAddress);
            return new ResponseEntity<>(foundOrder, HttpStatus.OK);
        } catch (OrderStatusException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()),HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/order/{id}/invoice")
    public ResponseEntity<?> getAllInvoices(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long id){
        try {
            Invoice invoice = service.getInvoice(id);
            return new ResponseEntity<>(invoice, HttpStatus.OK);
        } catch (OrderStatusException e) {
            return new ResponseEntity<>(new ResponseObject(e.getMessage()),HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/campaign")
    public ResponseEntity<?> setCampaign(HttpServletRequest request, HttpServletResponse response, @RequestBody CampaignDTO campaign) {
        try{
            Product product = service.setCampaign(campaign);
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        catch(ProductException e){
            return new ResponseEntity<>(new ResponseObject(e.getMessage()),HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/campaign/{id}")
    public ResponseEntity<?> deleteCampaign(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long id){
        try{
            Product product = service.deleteCampaign(id);
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        catch(ProductException e){
            return new ResponseEntity<>(new ResponseObject(e.getMessage()),HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/campaign")
    public ResponseEntity<?> getAllCampaigns(HttpServletRequest request, HttpServletResponse response) {

        return new ResponseEntity<>(service.getCampaigns(), HttpStatus.OK);
    }

}
