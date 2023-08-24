package com.sparta.mdesigns.security;

import com.sparta.mdesigns.entities.Cart;
import com.sparta.mdesigns.entities.User;
import com.sparta.mdesigns.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SessionListener implements ApplicationListener<HttpSessionDestroyedEvent> {

    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(HttpSessionDestroyedEvent event) {

        System.out.println("------------Inside Application Event-----------------");

        HttpSession session = event.getSession();

        User user=(User)session.getAttribute("currentUser");
        userService.deleteCartObjectsByUser(user);

        List<Cart> cartList=(List<Cart>) session.getAttribute("cartItems");

        cartList.stream().forEach(cartObject->userService.saveNewCartItem(cartObject));
    }
}
