package edu.sabanciuniv.cs308.ecommerce.data_model;

import edu.sabanciuniv.cs308.ecommerce.entities.Address;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

public class UserDAO {

        private Long id;

        @Length(min = 3, max = 10)
        private String username;

        @Length(min = 8,max = 20)
        private String password;

        @Email
        private String email;

        private String firstName;

        private String lastName;

        private String userRole;

        private List<Address> addressList;


        public Long getId() { return id; }

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

        public List<Address> getAddressList() { return addressList; }

        public void setAddressList(List<Address> addressList) { this.addressList = addressList; }

        public String getEmail() { return email; }

        public void setEmail(String email) { this.email = email; }
}
