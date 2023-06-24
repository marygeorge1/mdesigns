package com.sparta.mdesigns.entities;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Column(name = "userid")
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    //@Column(name = "proid")
    @ManyToOne
    @JoinColumn(name = "proid")
    private Product product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(user, cart.user) && Objects.equals(product, cart.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, product);
    }
}
