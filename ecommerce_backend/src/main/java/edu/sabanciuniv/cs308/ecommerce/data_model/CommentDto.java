package edu.sabanciuniv.cs308.ecommerce.data_model;

public class CommentDto {
    private String comment;
    private long userId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
