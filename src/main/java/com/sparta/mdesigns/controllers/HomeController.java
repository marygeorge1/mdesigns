package com.sparta.mdesigns.controllers;

import com.sparta.mdesigns.dtos.PriceRangeDTO;
import com.sparta.mdesigns.dtos.UserDTO;
import com.sparta.mdesigns.entities.Cart;
import com.sparta.mdesigns.entities.Product;
import com.sparta.mdesigns.entities.User;
import com.sparta.mdesigns.services.IProductService;
import com.sparta.mdesigns.services.IUserService;
import com.sparta.mdesigns.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.List;

@Controller
public class HomeController {

    Logger logger= LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IProductService productService;


    //Redirecting any request coming to root URL to /home
    @RequestMapping("/")
    public void redirectToShowHomePage(HttpServletResponse response) throws IOException {
        response.sendRedirect("/home");
    }


    @RequestMapping("/home")
    public String showHomePage(Model model,HttpServletRequest request){
        logger.trace("Home page accessed");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

       if(authentication.getName()!=null){
           model.addAttribute("session",request.getSession());
       }
        return "index";
    }

    @RequestMapping("/showlogin")
    public String showLoginPage(HttpServletRequest request){

        //saving the URL of the requested page
        String referrer = request.getHeader("Referer");
        System.out.println("Referer --------->"+referrer);
        //saving the URL of the requested page as a session attribute
        request.getSession().setAttribute("url_prior_login", referrer);
        return "login";
    }

    @RequestMapping("/displaysignup")
    public String showSignUpPage(@ModelAttribute("user") User dummyUser){

        //model.addAttribute("user",new User());
        return "signup";
    }


    @RequestMapping(value = "/registerUser",method = RequestMethod.POST)
    public String showRegisterSuccessPage(@Valid User newuser, BindingResult result){

       if(result.hasErrors()){
           return "signup";
       }

       boolean status=userService.addNewUser(newuser);
       return "login";
    }

    @RequestMapping(value = "/signOut")
    public String userSignOut(HttpSession session){

        User user=(User)session.getAttribute("currentUser");
        userService.deleteCartObjectsByUser(user);

        List<Cart> cartList=(List<Cart>) session.getAttribute("cartItems");

        cartList.stream().forEach(cartObject->userService.saveNewCartItem(cartObject));

        session.invalidate();
        return "index";
    }


    @RequestMapping(value = "/display")
    public String showAllProducts(@RequestParam String ptype, Model model, PriceRangeDTO pDTO){

        System.out.println(pDTO);

        List<Product> products = null;

        if(!pDTO.isPriceRange1() && !pDTO.isPriceRange2() && !pDTO.isPriceRange3()){
            products=productService.getProductsOfThisCategory(ptype); 
        }
        else{

            List<Product> products1;
            List<Product> products2;
            List<Product> products3;

            if(pDTO.isPriceRange1()){
                products1=productService.getProductsInThePriceRange(0,50,ptype);
                if(products1!=null){
                    if(products==null){
                        products=products1;
                    }
                    else{
                        products.addAll(products1);
                    }

                }

            }

            if(pDTO.isPriceRange2()){
                products2=productService.getProductsInThePriceRange(51,100,ptype);
                if(products2!=null){
                    if(products==null){
                        products=products2;
                    }
                    else{
                        products.addAll(products2);
                    }

                }

            }

            if(pDTO.isPriceRange3()){
                products3=productService.getProductsWithPriceGreaterThanTheValue(100,ptype);
                if(products3!=null){
                    if(products==null){
                        products=products3;
                    }
                    else{
                        products.addAll(products3);
                    }

                }
            }


        }
        
        model.addAttribute("products",products);
        model.addAttribute("ptype",ptype);

        return "display";
    }


    @RequestMapping(value = "/showProduct/{pid}")
    public String showProductDetails(@PathVariable Integer pid,Model model){

        Product product=productService.getProductById(pid);
        model.addAttribute("product",product);
        return "showproduct";
    }


    @RequestMapping(value = "/addToCart/{pid}")
    public String addProductToCart(@PathVariable Integer pid,HttpServletRequest request,Model model){

        //return "dummy";

        HttpSession session=request.getSession();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Logged in user"+auth.getPrincipal());

        /*if(session.getAttribute("currentUser")==null){
            return "login";
        }*/
        if(session.getAttribute("currentUser")==null){
            return "login";
        }
        else{
            Cart newCartItem=new Cart();
            newCartItem.setProduct(productService.getProductById(pid));
            newCartItem.setUser((User)session.getAttribute("currentUser"));

            //userService.saveNewCartItem(newCartItem);

            List<Cart> cartList=(List<Cart>) session.getAttribute("cartItems");
            cartList.add(newCartItem);

            //List<Cart> cartItems=userService.getCartItems((User)session.getAttribute("currentUser"));
            session.setAttribute("cartItems",cartList);

            model.addAttribute("session",session);

            return "cart";
        }

    }

    @RequestMapping(value = "/removeFromCart/{pId}")
    public String removeTheProductFromCart(@PathVariable int pId,HttpSession session,Model model){

        System.out.println("The product id to be removed"+pId);

        User user=(User)session.getAttribute("currentUser");
        Product product=productService.getProductById(pId);

        Cart cartObjectToBeRemoved=new Cart();
        cartObjectToBeRemoved.setUser(user);
        cartObjectToBeRemoved.setProduct(product);

        List<Cart> cartList=(List<Cart>) session.getAttribute("cartItems");
        System.out.println(cartList);

        for(Cart cart:cartList){
            if(cart.getProduct().equals(product) && cart.getUser().equals(user)){
                cartObjectToBeRemoved.setId(cart.getId());
            }
        }

        cartList.remove(cartObjectToBeRemoved);
        System.out.println(cartList);

        session.setAttribute("cartItems",cartList);
        model.addAttribute("session",session);
        //model.addAttribute("cartItems",cartList);
        return "cart";
    }


    @RequestMapping(value = "/navbar")
    public String showNavBar(){
        return "navbar";
    }

    @RequestMapping("/cart")
    public String showCartPage(){
        return "cart";
    }
}
