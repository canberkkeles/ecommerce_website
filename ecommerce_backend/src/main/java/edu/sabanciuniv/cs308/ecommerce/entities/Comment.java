package edu.sabanciuniv.cs308.ecommerce.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "Comment_Table")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_product_id", referencedColumnName = "product_id")
    private Product product;

    @Length(min = 1, max = 250)
    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "isValidated")
    private boolean isValidated;


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

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
