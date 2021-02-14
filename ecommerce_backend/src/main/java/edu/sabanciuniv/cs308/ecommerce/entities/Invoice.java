package edu.sabanciuniv.cs308.ecommerce.entities;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Invoice_Table")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Long id;

    @ManyToMany(cascade=CascadeType.ALL)
    private List<CartP_Product> cartP_productsList;
/*
    @ManyToOne
    @JoinColumn(name = "invoice_user_id", referencedColumnName = "user_id")
    private User user;

 */

    @Column(name = "total_amount")
    private Long totalAmount;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "address")
    private String address;

    @Column(name = "verified")
    private Boolean paymentverification;


    public List<CartP_Product> getCartP_productsList() {
        return cartP_productsList;
    }

    public void setCartP_productsList(List<CartP_Product> cartP_productsList) {
        this.cartP_productsList = cartP_productsList;
    }
/*
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


 */
    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getPaymentverification() {
        return paymentverification;
    }

    public void setPaymentverification(Boolean paymentverification) {
        this.paymentverification = paymentverification;
    }

    @Override
    public String toString() {
        String invString = "Invoice #" + id + "\n-------------------------------------------------------------\n" + "Product List\n";
        for (CartP_Product cp:cartP_productsList)
        {
            invString = invString + cp.getProduct_name() + " ... " + cp.getQuantity() + " ... " + cp.getPrice() + "TL\n";
        }
        invString = invString +
                "Total = " + this.totalAmount + "TL" +
                "\n\n"+ "Address: " + this.address +
                "\n"+ "Name: " + this.name +
                "\n"+ "Surname: " + this.surname +
                "\n-------------------------------------------------------------";
        return invString;
    }
}
