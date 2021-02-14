package edu.sabanciuniv.cs308.ecommerce.login_system;

public class PaymentDto {

    private Long userId;
    private String name_surname;
    private Long creditCartNum;
    private Long expiration_date_month;
    private Long expiration_date_year;
    private Long cvv2;



    public String getName_surname() {
        return name_surname;
    }

    public void setName_surname(String name_surname) {
        this.name_surname = name_surname;
    }

    public Long getCreditCartNum() {
        return creditCartNum;
    }

    public void setCreditCartNum(Long creditCartNum) {
        this.creditCartNum = creditCartNum;
    }

    public Long getExpiration_date_month() {
        return expiration_date_month;
    }

    public void setExpiration_date_month(Long expiration_date_month) {
        this.expiration_date_month = expiration_date_month;
    }

    public Long getExpiration_date_year() {
        return expiration_date_year;
    }

    public void setExpiration_date_year(Long expiration_date_year) {
        this.expiration_date_year = expiration_date_year;
    }

    public Long getCvv2() {
        return cvv2;
    }

    public void setCvv2(Long cvv2) {
        this.cvv2 = cvv2;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
