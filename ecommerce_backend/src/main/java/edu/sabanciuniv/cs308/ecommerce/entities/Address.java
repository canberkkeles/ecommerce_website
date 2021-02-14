package edu.sabanciuniv.cs308.ecommerce.entities;

import javax.persistence.*;

@Entity
@Table(name = "Address_Table")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "address_of_user_id", referencedColumnName = "user_id")
    private User user;
    @Column(name = "city")
    private String city;
    @Column(name = "full_address")
    private String fullAddress;

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }


}
