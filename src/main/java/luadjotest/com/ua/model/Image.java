package luadjotest.com.ua.model;

import javax.persistence.*;

@Entity
@Table(name="tbl_image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fileName;
    private Boolean isMainImage;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public Image(String fileName, Product product, Boolean isMainImage) {
        this.fileName = fileName;
        this.product = product;
        this.isMainImage = isMainImage;
    }
    public Image(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean isMainImage() {
        return isMainImage;
    }

    public void setMainImage(Boolean mainImage) {
        isMainImage = mainImage;
    }
}

