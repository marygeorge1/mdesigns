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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.List;

@Controller
@SessionAttributes("appName")
public class HomeController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IProductService productService;


    //Reading data from application.properties file
    @Value("${webapplication.name}")
    private String applicationName;



    //Redirecting any request coming to root URL to /home
    @RequestMapping("/")
    public void redirectToShowHomePage(HttpServletResponse response) throws IOException {
        response.sendRedirect("/home");
    }


    @RequestMapping("/home")
    public String showHomePage(Model model){

        model.addAttribute("loggedInStatus",false);
        /*
        The value of applicationName which is read from application.properties file
        is added to the model attribute, so that it can be added as a session attribute
        in the first request to the application itself.
        */
        model.addAttribute("appName",applicationName);
        return "index";
    }

    @RequestMapping("/login")
    public String showLoginPage(){
        return "login";
    }

    @RequestMapping("/signUp")
    public String showSignUpPage(@ModelAttribute("user") User dummyUser){
        //model.addAttribute("user",new User());
        return "signup";
    }

    @RequestMapping(value = "/verifyUser",method = RequestMethod.POST)
    public String verifyUserDetails(User user, Model model, HttpServletRequest request){

        //boolean invalidLoginCredentialStatus=!(userService.verifyUser(user));
        User loggedInUser=userService.verifyUser(user);

        if(loggedInUser!=null){

           // model.addAttribute("loggedInStatus",true);
            //
            HttpSession session=request.getSession();
            session.setAttribute("currentUser",loggedInUser);

            List<Cart> cartItems=userService.getCartItems(loggedInUser);
            session.setAttribute("cartItems",cartItems);

            model.addAttribute("session",session);

            return "index";
        }
        else {

            model.addAttribute("invalidLoginCredentialStatus",true);
            return "login";
        }



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
    public String addProductToCart(@PathVariable Integer pid,HttpSession session,Model model){

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
}
