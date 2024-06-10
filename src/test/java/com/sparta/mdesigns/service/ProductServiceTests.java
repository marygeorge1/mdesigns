package com.sparta.mdesigns.service;

import com.sparta.mdesigns.entities.Product;
import com.sparta.mdesigns.repositories.ProductRepository;
import com.sparta.mdesigns.services.IProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTests {

    @MockBean
    ProductRepository productRepository;

    @Autowired
    IProductService productService;

    @Test
    public void getProductsOfThisCategoryTest(){

        Product product1=new Product();
        Product product2=new Product();
        List<Product> products=new ArrayList<>();
        products.add(product1);
        products.add(product2);

        Mockito.when(productRepository.findAllByProdImageStartsWith(Mockito.any(String.class))).thenReturn(products);

        String type="N1.jpg";
        Assertions.assertEquals(products,productService.getProductsOfThisCategory(type));
    }

    @Test
    public void getProductsInThePriceRangeTest(){
        Product product1=new Product();
        Product product2=new Product();
        List<Product> products=new ArrayList<>();
        products.add(product1);
        products.add(product2);

        Mockito.when(productRepository
                .findAllByPriceBetweenAndProdImageStartingWith(Mockito.any(Integer.class),Mockito.any(Integer.class),Mockito.any(String.class)))
                .thenReturn(products);

        Integer min=30;
        Integer max=50;
        String type="N1.jpg";

        Assertions.assertEquals(products,productService.getProductsInThePriceRange(min,max,type));

    }

    @Test
    public void getProductByIdTest_ProductExists(){
        Product product=new Product();
        Mockito.when(productRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(product));
        Integer id=10;
        Assertions.assertEquals(product,productService.getProductById(id));

    }

    @Test
    public void getProductByIdTest_ProductDoesNotExist(){

        Mockito.when(productRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(null));
        Integer id=10;
        Assertions.assertEquals(null,productService.getProductById(id));

    }


}
