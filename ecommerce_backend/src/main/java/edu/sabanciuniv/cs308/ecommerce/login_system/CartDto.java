package edu.sabanciuniv.cs308.ecommerce.login_system;

import edu.sabanciuniv.cs308.ecommerce.entities.User;

public class CartDto {
    private Long quantity;
    private Long userId;
    private String image;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
