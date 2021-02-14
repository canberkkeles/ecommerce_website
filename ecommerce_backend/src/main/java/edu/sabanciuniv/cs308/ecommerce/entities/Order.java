package edu.sabanciuniv.cs308.ecommerce.entities;


import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Order_Table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ordering_user_id", referencedColumnName = "user_id")
    private User user;
    @Column(name = "order_date")
    private String orderDate;
    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "order_address")
    private String orderAddress;

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    @OneToMany(mappedBy = "order")
    private List<Ordered_Product> orderedProductList;

    @OneToOne
    @JoinColumn(name = "order_invoice_id", referencedColumnName = "invoice_id")
    private Invoice orderedInvoices;

    public Invoice getOrderedInvoices() {
        return orderedInvoices;
    }

    public void setOrderedInvoices(Invoice orderedInvoices) {
        this.orderedInvoices = orderedInvoices;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String order_date) {
        this.orderDate =  order_date;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String order_status) {
        this.orderStatus = order_status;
    }

    public List<Ordered_Product> getOrderedProductList() { return orderedProductList; }

    public void setOrderedProductList(List<Ordered_Product> orderedProductList) { this.orderedProductList = orderedProductList; }
}
