package edu.sabanciuniv.cs308.ecommerce.data_model;

public class FilterDto {
    private double minPrice;
    private double maxPrice;
    private double minRating;
    private double maxRating;
    private String sortingAttribute;
    private String sortingType;

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinRating() {
        return minRating;
    }

    public void setMinRating(double minRating) {
        this.minRating = minRating;
    }

    public double getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(double maxRating) {
        this.maxRating = maxRating;
    }

    public String getSortingAttribute() {
        return sortingAttribute;
    }

    public void setSortingAttribute(String sortingAttribute) {
        this.sortingAttribute = sortingAttribute;
    }

    public String getSortingType() {
        return sortingType;
    }

    public void setSortingType(String sortingType) {
        this.sortingType = sortingType;
    }


}
