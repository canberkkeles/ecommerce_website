package edu.sabanciuniv.cs308.ecommerce.services;

import edu.sabanciuniv.cs308.ecommerce.data_model.CampaignDTO;
import edu.sabanciuniv.cs308.ecommerce.data_model.OrderAddressChangeDto;
import edu.sabanciuniv.cs308.ecommerce.data_model.OrderStatusChangeDto;
import edu.sabanciuniv.cs308.ecommerce.entities.Invoice;
import edu.sabanciuniv.cs308.ecommerce.entities.Order;
import edu.sabanciuniv.cs308.ecommerce.entities.Product;
import edu.sabanciuniv.cs308.ecommerce.entities.User;
import edu.sabanciuniv.cs308.ecommerce.exceptions.OrderStatusException;
import edu.sabanciuniv.cs308.ecommerce.exceptions.ProductException;
import edu.sabanciuniv.cs308.ecommerce.helpers.MailingSystemHelper;
import edu.sabanciuniv.cs308.ecommerce.interfaces.I_Sales_Manager_Service;
import edu.sabanciuniv.cs308.ecommerce.repositories.OrderRepository;
import edu.sabanciuniv.cs308.ecommerce.repositories.ProductRepository;
import edu.sabanciuniv.cs308.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesManagerService implements I_Sales_Manager_Service {


    @Autowired
    OrderRepository orderRepo;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    UserRepository userRepo;

    @Override
    public Order deleteOrder(long id) throws OrderStatusException {
        Optional<Order> ord = orderRepo.findById(id);
        if(ord.isPresent()){
            Order realOrd = ord.get();
            orderRepo.delete(realOrd);
            MailingSystemHelper helper = new MailingSystemHelper();
            helper.sendMail(realOrd.getUser(), "Order #" +realOrd.getId() + " Deleted", "Dear "+ realOrd.getUser().getFirstName() +" " + realOrd.getUser().getLastName() +","+
                    "\n\n Your order is not accepted, you can request for a refund.");
            return realOrd;
        }
        else{
            throw new OrderStatusException("Order not found!");
        }
    }

    @Override
    public Order changeOrderStatus(long id, OrderStatusChangeDto newStatus) throws OrderStatusException {
        Optional<Order> ord = orderRepo.findById(id);
        if(ord.isPresent()){
            Order realOrd = ord.get();
            realOrd.setOrderStatus(newStatus.getStatus());
            orderRepo.save(realOrd);
            MailingSystemHelper helper = new MailingSystemHelper();
            helper.sendMail(realOrd.getUser(), "Order #" +realOrd.getId() + " Status Change", "Dear "+ realOrd.getUser().getFirstName() +" " + realOrd.getUser().getLastName() +","+
                    "\n\n Status of your order is changed to " + realOrd.getOrderStatus() + ".");
            return realOrd;
        }
        else{
            throw new OrderStatusException("Order not found!");
        }
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    @Override
    public Order findOrder(long id) throws OrderStatusException {
        Optional<Order> ord = orderRepo.findById(id);
        if(ord.isPresent()){
            Order realOrd = ord.get();
            return realOrd;
        }
        else{
            throw new OrderStatusException("Order not found!");
        }
    }

    @Override
    public Order changeAddress(long id, OrderAddressChangeDto newAddress) throws OrderStatusException {
        Optional<Order> ord = orderRepo.findById(id);
        if(ord.isPresent()){
            Order realOrd = ord.get();
            realOrd.setOrderAddress(newAddress.getAddress());
            realOrd.getOrderedInvoices().setAddress(newAddress.getAddress());
            orderRepo.save(realOrd);
            MailingSystemHelper helper = new MailingSystemHelper();
            helper.sendMail(realOrd.getUser(), "Order #" +realOrd.getId() + " Address Change", "Dear "+ realOrd.getUser().getFirstName() +" " + realOrd.getUser().getLastName() +","+
                    "\n\n Your products will be sent to " + realOrd.getOrderAddress() + ".");
            return realOrd;
        }
        else{
            throw new OrderStatusException("Order not found!");
        }
    }

    @Override
    public Invoice getInvoice(Long id) throws OrderStatusException {
        Optional<Order> ord = orderRepo.findById(id);
        if(ord.isPresent()){
            Order realOrd = ord.get();
            return realOrd.getOrderedInvoices();
        }
        else{
            throw new OrderStatusException("Order not found!");
        }
    }

    @Override
    public Product setCampaign(CampaignDTO campaign) throws ProductException {
        if(campaign.getPercentage() <= 0 || campaign.getPercentage() >= 100){
            throw new ProductException("Invalid campaign percentage");
        }
        Optional<Product> ord = productRepo.findById(campaign.getProductID());
        if(ord.isPresent()) {
            Product prod = ord.get();
            if(prod.isCampaign() == true){
                throw new ProductException("Product price is already discounted!");
            }
            prod.setCampaign(true);
            float perc = campaign.getPercentage();
            float price = prod.getProductPrice();
            float newPrice = ((100f - perc) * price) / 100f;
            prod.setCampaignPercentage(perc);
            prod.setProductPrice(newPrice);
            productRepo.save(prod);
            Iterable<User> users = userRepo.findAll();
            MailingSystemHelper helper = new MailingSystemHelper();
            for(User u : users){
                helper.sendMail(u, "Boss is crazy! " +"%" + perc + " discount!" ,"Dear " + u.getFirstName() + " " + u.getLastName() + ",\n\nDo not miss on this campaign!\n\nPrice of " +
                        prod.getProductName() + " is now ₺" + newPrice + " instead of ₺" + price + "!\nStay healthy and awesome!\nPep-e Commerce");
            }
            return prod;
        }
        else{
            throw new ProductException("Product not found!");
        }
    }

    @Override
    public Product deleteCampaign(Long id) throws ProductException {
        Product prod = productRepo.findByid(id);
        if(prod != null){
            if(prod.isCampaign() == false){
                throw new ProductException("Not a campaign product!");
            }

            float campaignedPrice = prod.getProductPrice();
            float campaignPercentage = prod.getCampaignPercentage();

            prod.setCampaign(false);
            float oldPrice = campaignedPrice * ( 100f / (100f - campaignPercentage));
            prod.setProductPrice(oldPrice);
            return productRepo.save(prod);
        }
        else{
            throw new ProductException("Product not found!");
        }
    }

    @Override
    public List<Product> getCampaigns() {
        return productRepo.findByCampaignTrue();
    }
}
