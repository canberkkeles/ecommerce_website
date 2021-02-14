package edu.sabanciuniv.cs308.ecommerce.entities;



import javax.persistence.*;


@Entity
@Table(name = "Ordered_Product_Table")
public class Ordered_Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordered_product_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ordering_product_order_id", referencedColumnName = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "ordering_product_product_id", referencedColumnName = "product_id")
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
