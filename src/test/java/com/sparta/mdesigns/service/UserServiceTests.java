package com.sparta.mdesigns.service;

import com.sparta.mdesigns.entities.Cart;
import com.sparta.mdesigns.entities.Product;
import com.sparta.mdesigns.entities.User;
import com.sparta.mdesigns.repositories.CartRepository;
import com.sparta.mdesigns.repositories.UsersRepository;
import com.sparta.mdesigns.services.IUserService;
import com.sparta.mdesigns.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserServiceTests {

    @MockBean
    UsersRepository userRepo;

    @MockBean
    CartRepository cartRepo;

    @Autowired
    IUserService userService;


    @Test
    public void addNewUserMethodTest_UserSavedSuccessfully(){
        User dummyuser=new User();
        Mockito.when(userRepo.save(Mockito.any(User.class))).thenReturn(dummyuser);
        Assertions.assertEquals(true,userService.addNewUser(dummyuser));
    }

    @Test
    public void addNewUserMethodTest_Unsuccessful(){
        User dummyuser=new User();
        Mockito.when(userRepo.save(Mockito.any(User.class))).thenReturn(null);
        Assertions.assertEquals(false,userService.addNewUser(dummyuser));
    }

    @Test
    public void getCartItemsTest(){

        User dummyUser=new User();
        Product dummyProduct1=new Product();
        Product dummyProduct2=new Product();

        Cart item1=new Cart();
        item1.setUser(dummyUser);
        item1.setProduct(dummyProduct1);

        Cart item2=new Cart();
        item2.setUser(dummyUser);
        item2.setProduct(dummyProduct2);

        List<Cart> cartList=new ArrayList<>();
        cartList.add(item1);
        cartList.add(item2);

        Mockito.when(cartRepo.findByUser(Mockito.any(User.class))).thenReturn(cartList);

        Assertions.assertEquals(cartList,userService.getCartItems(dummyUser));

    }




}
