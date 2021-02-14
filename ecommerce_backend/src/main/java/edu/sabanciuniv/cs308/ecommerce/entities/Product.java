package edu.sabanciuniv.cs308.ecommerce.entities;


import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@Table(name = "Product_Table")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @Length(min = 1, max = 50)
    @Column(name = "product_name")
    private String productName;
    @Length(min = 1, max = 250)
    @Column(name = "product_description")
    private String productDescription;
    @Column(name = "product_price")
    private Float productPrice;
    @ManyToMany
    private List<Tag> tagList;
    @Min(0) @Max(5)
    @Column(name = "product_avg_rating")
    private double avgRating;
    @Column(name = "product_total_rating")
    private int totalRating;
    @Column(name = "rating_count")
    private int ratingCount;
    @Column(name="usersThatRatedThisProduct")
    @ElementCollection(targetClass=Long.class)
    private List<Long> usersThatRatedThisProduct;
    @Column(name="usersThatCommentedThisProduct")
    @ElementCollection(targetClass=Long.class)
    private List<Long> usersThatCommentedThisProduct;
    @Column(name = "campaign_status")
    private boolean campaign;
    @Column(name= "product_image")
    private String productImage;

    @Column(name = "campaign_percantage")
    private float campaignPercentage;

    public float getCampaignPercentage() {
        return campaignPercentage;
    }

    public void setCampaignPercentage(float campaignPercentage) {
        this.campaignPercentage = campaignPercentage;
    }

    public boolean isCampaign() {
        return campaign;
    }

    public void setCampaign(boolean campaign) {
        this.campaign = campaign;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }

    public Float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Float productPrice) {
        this.productPrice = productPrice;
    }

    public List<Tag> getTagList() { return tagList; }

    public void setTagList(List<Tag> tagList) { this.tagList = tagList; }

    public double getAvgRating() { return avgRating; }

    public void setAvgRating(double avgRating) { this.avgRating = avgRating; }

    public int getTotalRating() { return totalRating; }

    public void setTotalRating(int totalRating) { this.totalRating = totalRating; }

    public int getRatingCount() { return ratingCount; }

    public void setRatingCount(int ratingCount) { this.ratingCount = ratingCount; }

    public List<Long> getUsersThatRatedThisProduct() { return usersThatRatedThisProduct; }

    public void setUsersThatRatedThisProduct(List<Long> usersThatRatedThisProduct) { this.usersThatRatedThisProduct = usersThatRatedThisProduct; }

    public List<Long> getUsersThatCommentedThisProduct() { return usersThatCommentedThisProduct; }

    public void setUsersThatCommentedThisProduct(List<Long> usersThatCommentedThisProduct) { this.usersThatCommentedThisProduct = usersThatCommentedThisProduct; }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
