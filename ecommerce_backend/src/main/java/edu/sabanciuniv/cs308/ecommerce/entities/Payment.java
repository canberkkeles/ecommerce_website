package edu.sabanciuniv.cs308.ecommerce.entities;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "Payment_Table")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payment_user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "total_amount")
    private Long totalAmount;

    @Column(name = "name_and_surname")
    private String NameandSurname;

    @Column(name = "credit_cart_number")
    private Long creditCardNumber;

    @Column(name = "expiration_date_month")
    private Long expiration_date_month;

    @Column(name = "expiration_date_year")
    private Long expiration_date_year;

    @Column(name = "cvv2")
    private Long CVV2;

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

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getNameandSurname() {
        return NameandSurname;
    }

    public void setNameandSurname(String nameandSurname) {
        NameandSurname = nameandSurname;
    }

    public Long getCVV2() {
        return CVV2;
    }

    public void setCVV2(Long CVV2) {
        this.CVV2 = CVV2;
    }

    public Long getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(Long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Long getExpiration_date_month() {
        return expiration_date_month;
    }

    public void setExpiration_date_month(Long expiration_date_month) {
        this.expiration_date_month = expiration_date_month;
    }

    public Long getExpiration_date_year() {
        return expiration_date_year;
    }

    public void setExpiration_date_year(Long expiration_date_year) {
        this.expiration_date_year = expiration_date_year;
    }

}
