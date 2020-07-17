package com.apple.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int productId;
    String productName;
    String productDescription;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "PRODUCT_CATEGORY_ID", referencedColumnName = "categoryId")
    private ProductCategory productCategory;
//    @OneToMany(mappedBy = "product",cascade=CascadeType.ALL, fetch =FetchType.LAZY)
 //   @JoinColumn(name="manufactureId")
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Manufacture> manufactures = new ArrayList<Manufacture>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Sales> sales = new ArrayList<Sales>();


    public List<Sales> getSales() {
        return sales;
    }

    public void setSales(List<Sales> sales) {
        sales = sales;
    }

    public List<Manufacture> getManufactures() {
        return manufactures;
    }

    public void setManufactures(List<Manufacture> manufactures) {
        manufactures = manufactures;
    }
    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
