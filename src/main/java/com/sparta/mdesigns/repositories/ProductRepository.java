package com.sparta.mdesigns.repositories;

import com.sparta.mdesigns.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findAllByProdImageStartsWith(String ptype);
    List<Product> findAllByPriceBetweenAndProdImageStartingWith(Integer min,Integer max,String ptype);
    List<Product> findAllByPriceGreaterThanAndProdImageStartingWith(Integer price,String ptype);
}
