package com.sparta.mdesigns.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "pid")
    private Integer pId;

    @Column(name = "pname")
    private String prodName;

    @Column(name = "pdescription")
    private String prodDescription;

    private Integer price;

    @Column(name = "pimage")
    private String prodImage;


    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdDescription() {
        return prodDescription;
    }

    public void setProdDescription(String prodDescription) {
        this.prodDescription = prodDescription;
    }



    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getProdImage() {
        return prodImage;
    }

    public void setProdImage(String prodImage) {
        this.prodImage = prodImage;
    }


    @Override
    public String toString() {
        return "Product{" +
                "pId=" + pId +
                ", prodName='" + prodName + '\'' +
                ", prodDescription='" + prodDescription + '\'' +
                ", price=" + price +
                ", prodImage='" + prodImage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(pId, product.pId) && Objects.equals(prodName, product.prodName) && Objects.equals(prodDescription, product.prodDescription) && Objects.equals(price, product.price) && Objects.equals(prodImage, product.prodImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pId, prodName, prodDescription, price, prodImage);
    }
}
