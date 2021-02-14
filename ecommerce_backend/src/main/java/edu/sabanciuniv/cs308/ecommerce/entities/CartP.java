package edu.sabanciuniv.cs308.ecommerce.entities;



import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CartP_Table")
public class CartP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    /*
    @Column(name = "total")
    private Float total;

     */

    @OneToOne
    @JoinColumn(name = "address_of_user_id", referencedColumnName = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

/*
    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

 */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



}
