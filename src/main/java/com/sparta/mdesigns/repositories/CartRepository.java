package com.sparta.mdesigns.repositories;

import com.sparta.mdesigns.entities.Cart;
import com.sparta.mdesigns.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {

    List<Cart> findByUser(User user);
    void deleteAllByUser(User user);
}
