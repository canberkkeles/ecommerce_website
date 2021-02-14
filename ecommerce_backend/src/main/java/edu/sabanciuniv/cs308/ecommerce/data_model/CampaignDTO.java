package edu.sabanciuniv.cs308.ecommerce.data_model;

public class CampaignDTO {

    private long productID;
    private float percentage;

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
}
