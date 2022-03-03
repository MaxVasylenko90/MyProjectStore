package luadjotest.com.ua.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart_products")
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    private Integer total = 0;

    @OneToMany(mappedBy = "cart",
            cascade = CascadeType.ALL)
    private List<CartSmall> cartSmall;

    public Cart(User user) {
        this.user = user;
        cartSmall = new ArrayList<>();
    }

    public Cart(){}

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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setTotal() {
        total = 0;
        for(CartSmall cartSmall : getCartSmall()) {
            total += cartSmall.getSubtotal();
        }
    }

    public List<CartSmall> getCartSmall() {
        return cartSmall;
    }

    public void setCartSmall(List<CartSmall> cartSmall) {
        this.cartSmall = cartSmall;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
