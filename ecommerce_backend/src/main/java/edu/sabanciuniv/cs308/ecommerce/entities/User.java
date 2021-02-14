package edu.sabanciuniv.cs308.ecommerce.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Entity
@Table(name = "user_table")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Length(min = 3, max = 10)
    @Column(name = "user_name")
    private String username;
    
    @Length(min = 8,max = 20)
    @Column(name = "password")
    private String password;

    @Email
    @Column(name = "e_mail")
    private String email;

    @Column(name = "user_first_name")
    private String firstName;

    @Column(name = "user_last_name")
    private String lastName;

    @Column(name = "user_role")
    private String userRole;

    @Column(name = "user_active")
    private boolean active;

    @Length(min = 6, max = 6)
    @Column(name = "login_code")
    private String code;

    @Length(min = 64, max = 64)
    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "user_verified")
    private boolean verified;


    @OneToMany(mappedBy = "user")
    private List<Address> addressList;


    @ManyToMany(cascade=CascadeType.ALL)
    private List<Invoice> invoicesList;

    public List<Invoice> getInvoicesList() {
        return invoicesList;
    }

    public void setInvoicesList(List<Invoice> invoicesList) {
        this.invoicesList = invoicesList;
    }

    @ManyToMany(cascade=CascadeType.ALL)
    private List<CartP_Product> cartP_productsList;

    public List<CartP_Product> getCartP_productsList() {
        return cartP_productsList;
    }

    public void setCartP_productsList(List<CartP_Product> cartP_productsList) {
        this.cartP_productsList = cartP_productsList;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public void setEmail(String email){this.email = email;}

    public @Email String getEmail() {
        return email;
    }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getVerificationCode() { return verificationCode; }

    public void setVerificationCode(String verificationCode) { this.verificationCode = verificationCode; }

    public boolean isVerified() { return verified; }

    public void setVerified(boolean verified) { this.verified = verified; }

    public List<Address> getAddressList() { return addressList; }

    public void setAddressList(List<Address> addressList) { this.addressList = addressList; }

}
