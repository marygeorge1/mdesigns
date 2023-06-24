package com.sparta.mdesigns.services;

import com.sparta.mdesigns.dtos.PriceRangeDTO;
import com.sparta.mdesigns.entities.Product;
import com.sparta.mdesigns.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{

    private ProductRepository prodRepo;

    @Autowired
    public ProductService(ProductRepository prodRepo){
       this.prodRepo=prodRepo;
    }
    @Override
    public List<Product> getProductsOfThisCategory(String type) {
        return prodRepo.findAllByProdImageStartsWith(type);
    }

    @Override
    public List<Product> getProductsInThePriceRange(Integer min, Integer max,String ptype) {
        return prodRepo.findAllByPriceBetweenAndProdImageStartingWith(min,max,ptype);
    }

    @Override
    public List<Product> getProductsWithPriceGreaterThanTheValue(Integer price, String ptype) {
        return prodRepo.findAllByPriceGreaterThanAndProdImageStartingWith(price,ptype);
    }

    @Override
    public Product getProductById(Integer id) {

        Product product=null;
        Optional<Product> result=prodRepo.findById(id);
        if(result.isPresent()){
            product=result.get();
        }
        return product;
    }
}
