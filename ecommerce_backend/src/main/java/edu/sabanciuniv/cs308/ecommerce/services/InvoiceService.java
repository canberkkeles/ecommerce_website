package edu.sabanciuniv.cs308.ecommerce.services;

import edu.sabanciuniv.cs308.ecommerce.entities.*;
import edu.sabanciuniv.cs308.ecommerce.exceptions.ProductException;
import edu.sabanciuniv.cs308.ecommerce.helpers.MailingSystemHelper;
import edu.sabanciuniv.cs308.ecommerce.login_system.InvoiceDto;
import edu.sabanciuniv.cs308.ecommerce.login_system.PaymentDto;
import edu.sabanciuniv.cs308.ecommerce.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class InvoiceService {
    @Autowired
    UserRepository userRepo;

    @Autowired
    InvoiceRepo invoiceRepo;

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    OrderRepository orderRepository;


    public Invoice createInvoiceforUser(InvoiceDto invoiceDto) throws ProductException {
        User user = userRepo.findUserById(invoiceDto.getUserId());
        if (user == null) {
            throw new ProductException("User does not exist.");
        } else {
            List<CartP_Product> list = user.getCartP_productsList();
            Invoice invoice = new Invoice();
            Order order = new Order();
            List<CartP_Product> list2 = new ArrayList<>();
            long total = 0;
            for (CartP_Product cp : list) {
                total += cp.getQuantity() * (productRepo.findByProductName(cp.getProduct_name())).getProductPrice();
                list2.add(cp);
            }

            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());

            order.setOrderAddress(invoiceDto.getAddress());
            order.setOrderStatus("Pending");
            order.setUser(user);
            order.setOrderDate(formatter.format(date));
            invoice.setCartP_productsList(list2);
            invoice.setName(user.getFirstName());
            invoice.setSurname(user.getLastName());
            invoice.setAddress(invoiceDto.getAddress());
            invoice.setTotalAmount(total);
            invoice.setPaymentverification(true);
            order.setOrderedInvoices(invoice);
            List <Invoice> invoicesList = user.getInvoicesList();
            invoicesList.add(invoice);
            user.setInvoicesList(invoicesList);
            orderRepository.save(order);
            userRepo.save(user);
            list.clear();
            user.setCartP_productsList(list);
            userRepo.save(user);
            MailingSystemHelper helper = new MailingSystemHelper();
            helper.sendMail(user, "Order #" +order.getId() + " Created", "Dear "+ user.getFirstName() +" " + user.getLastName() +","+
                    "\n\n Your products will be sent to " + order.getOrderAddress() + "."+
                    "\n\n INVOICE OF THIS ORDER"+
                    "\n\n" + invoice.toString());

            return invoice;

        }
    }

    public Order getUserOrder(User user, Long id) throws ProductException{
        List<Order> userOrders = orderRepository.findByUser(user);
        Order order = orderRepository.findOrderById(id);
        if(userOrders.contains(order))
        {
            return order;
        }
        else
        {
            throw new ProductException("This order is assigned to another user.");
        }
    }

    public Invoice getUserInvoice(User user, Long id) throws ProductException{
        List<Order> userOrders = orderRepository.findByUser(user);
        Order order = orderRepository.findOrderById(id);
        if(userOrders.contains(order))
        {
            Invoice invoice = order.getOrderedInvoices();
            return invoice;
        }
        else
        {
            throw new ProductException("This order is assigned to another user.");
        }
    }

    public String getInvoiceStr(Long orderId) throws ProductException {
        Optional<Invoice> opt = invoiceRepo.findById(orderId);
        if(opt.isPresent()){
            Invoice inv = opt.get();
            return inv.toString();
        }
        else{
            throw new ProductException("Invoice not found!");
        }
    }
}
