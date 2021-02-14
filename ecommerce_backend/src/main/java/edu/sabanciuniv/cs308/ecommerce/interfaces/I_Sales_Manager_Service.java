package edu.sabanciuniv.cs308.ecommerce.interfaces;

import edu.sabanciuniv.cs308.ecommerce.data_model.CampaignDTO;
import edu.sabanciuniv.cs308.ecommerce.data_model.OrderAddressChangeDto;
import edu.sabanciuniv.cs308.ecommerce.data_model.OrderStatusChangeDto;
import edu.sabanciuniv.cs308.ecommerce.entities.Invoice;
import edu.sabanciuniv.cs308.ecommerce.entities.Order;
import edu.sabanciuniv.cs308.ecommerce.entities.Product;
import edu.sabanciuniv.cs308.ecommerce.exceptions.OrderStatusException;
import edu.sabanciuniv.cs308.ecommerce.exceptions.ProductException;

import java.util.List;

public interface I_Sales_Manager_Service {

    List<Order> getAllOrders();
    Order findOrder(long id) throws OrderStatusException;
    Order changeAddress(long id, OrderAddressChangeDto newAddress) throws OrderStatusException;
    Order changeOrderStatus(long id, OrderStatusChangeDto newStatus) throws OrderStatusException;
    Order deleteOrder(long id) throws OrderStatusException;
    Invoice getInvoice(Long id) throws OrderStatusException;
    Product setCampaign(CampaignDTO campaign) throws ProductException;
    Product deleteCampaign(Long id) throws ProductException;
    List<Product> getCampaigns();
}
