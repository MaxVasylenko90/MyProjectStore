package luadjotest.com.ua.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cart_small")
public class CartSmall {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer quantity = 1;
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
    @ManyToOne(fetch = FetchType.EAGER)
    private Cart cart;
    private Integer subtotal;

    public CartSmall(Product product, Cart cart) {
        this.product = product;
        this.cart = cart;
        setSubtotal();
    }

    public CartSmall(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Integer getSubtotal() {
        setSubtotal();
        return subtotal;
    }

    public void setSubtotal() {
        subtotal = quantity * product.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartSmall)) return false;
        CartSmall cartSmall = (CartSmall) o;
        return getId().equals(cartSmall.getId()) && getQuantity().equals(cartSmall.getQuantity()) && getProduct().equals(cartSmall.getProduct()) && getCart().equals(cartSmall.getCart());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getQuantity(), getProduct(), getCart());
    }
}
