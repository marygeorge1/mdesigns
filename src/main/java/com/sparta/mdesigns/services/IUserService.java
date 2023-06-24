package com.sparta.mdesigns.services;

import com.sparta.mdesigns.entities.Cart;
import com.sparta.mdesigns.entities.User;

import java.util.List;

public interface IUserService {
    User verifyUser(User user);
    boolean addNewUser(User user);

    List<Cart> getCartItems(User user);

    void saveNewCartItem(Cart cart);

    void deleteCartObjectsByUser(User user);

}
