package luadjotest.com.ua.model;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer price;
    private Integer quantity;

    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true) //автоматическое удаление картинок при удалении продукта
    private List<Image> images;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @OneToMany(mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CartSmall> cartSmall;

    public Product(String name, String description, Integer quantity, Integer price) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.images = new ArrayList<>();
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

//    public Set<Cart> getCarts() {
//        return carts;
//    }
//
//    public void setCarts(Set<Cart> carts) {
//        this.carts = carts;
//    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Transactional
    public void changeQuantity(Integer number) {
        this.quantity = this.getQuantity() - number;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void addImage(Image image) {
        images.add(image);
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void removeImage(Image image) {
        image.setProduct(null);
        images.remove(image);

    }

    public String getMainImage() {
        for (Image image : this.getImages()) {
            if (image.isMainImage())
                return image.getFileName();
        }
        return null;
    }

    public List<Image> getNonMainImages() {
        List<Image> list = new ArrayList<>();
        for (Image image : this.getImages()) {
            if (!image.isMainImage())
                list.add(image);
        }
        return list;
    }

    public List<Integer> countImages() {
        List<Integer> list = new ArrayList<>();
        for(int i = 1; i < this.getImages().size(); i++)
            list.add(i);
        return list;
    }

    public List<CartSmall> getCartSmall() {
        return cartSmall;
    }

    public void setCartSmall(List<CartSmall> cartSmall) {
        this.cartSmall = cartSmall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getId().equals(product.getId()) && getName().equals(product.getName()) && getDescription().equals(product.getDescription()) && getPrice().equals(product.getPrice()) && getQuantity().equals(product.getQuantity()) && getImages().equals(product.getImages()) && getCategory().equals(product.getCategory()) && getCartSmall().equals(product.getCartSmall());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getPrice(), getQuantity(), getImages(), getCategory(), getCartSmall());
    }

}
