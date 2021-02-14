package edu.sabanciuniv.cs308.ecommerce.data_model;

public class RatingDto {
    private int rating;
    private long userId;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
