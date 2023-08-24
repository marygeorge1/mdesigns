package com.sparta.mdesigns.security;

import com.sparta.mdesigns.entities.Cart;
import com.sparta.mdesigns.entities.User;
import com.sparta.mdesigns.repositories.CartRepository;
import com.sparta.mdesigns.repositories.UsersRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        HttpSession session= request.getSession();

        User user=usersRepository.findByEmail(authentication.getName());
        session.setAttribute("currentUser",user);
        List<Cart> cartItems=cartRepository.findByUser(user);
        session.setAttribute("cartItems",cartItems);

        if(session.getAttribute("url_prior_login")!=null){
            String redirectUrl = (String) session.getAttribute("url_prior_login");
            if(redirectUrl!=null){
                session.removeAttribute("url_prior_login");
            }

            getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        }
        else{
            super.onAuthenticationSuccess(request, response, authentication);
        }





    }
}
