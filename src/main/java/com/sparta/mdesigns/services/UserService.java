package com.sparta.mdesigns.services;

import com.sparta.mdesigns.entities.Cart;
import com.sparta.mdesigns.entities.User;
import com.sparta.mdesigns.repositories.CartRepository;
import com.sparta.mdesigns.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService implements IUserService{

    private UsersRepository userRepo;
    private CartRepository cartRepo;



    @Autowired
    public UserService(UsersRepository userRepo,CartRepository cartRepo){

        this.userRepo =userRepo;
        this.cartRepo=cartRepo;
    }

    /*public User verifyUser(User user){

        User loggedUser= userRepo.findByEmail(user.getEmail());

        if(loggedUser!=null &&
                loggedUser.getEmail().equals(user.getEmail()) &&
                loggedUser.getPassword().equals(user.getPassword())){

            return loggedUser;
        }
        else{
            return null;
        }
    }*/

    @Override
    public boolean addNewUser(User user) {

        User newUser=userRepo.save(user);

        if(newUser!=null){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public List<Cart> getCartItems(User user) {

        List<Cart> cartItems=cartRepo.findByUser(user);
        return cartItems;
    }

    @Override
    public void saveNewCartItem(Cart cart) {
        cartRepo.save(cart);
    }

    @Override
    public void deleteCartObjectsByUser(User user) {

        cartRepo.deleteAllByUser(user);

    }
}
