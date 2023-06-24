package com.sparta.mdesigns.services;

import com.sparta.mdesigns.dtos.PriceRangeDTO;
import com.sparta.mdesigns.entities.Product;

import java.util.List;

public interface IProductService {
    List<Product> getProductsOfThisCategory(String type);

    List<Product> getProductsInThePriceRange(Integer min,Integer max,String ptype);

    List<Product> getProductsWithPriceGreaterThanTheValue(Integer
                                                           price,String ptype);
    Product getProductById(Integer id);
}
