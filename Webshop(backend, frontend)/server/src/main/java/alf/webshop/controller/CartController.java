package alf.webshop.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alf.webshop.domain.Cart;
import alf.webshop.domain.User;
import alf.webshop.service.CartService;
import alf.webshop.service.EmailService;
import alf.webshop.service.UserService;


@RestController
@RequestMapping(path = "/cart")
@PreAuthorize("isAuthenticated()")
public class CartController {
    
    Logger logger = LoggerFactory.getLogger(CartController.class);

    private final UserService userService;
    private final CartService cartService;
    private final EmailService emailService;

    @Autowired
    public CartController(UserService userService, CartService cartService, EmailService emailService) {
        this.userService = userService;
        this.cartService = cartService;
        this.emailService = emailService;
    }

    @GetMapping
    public ResponseEntity<Cart> getCart() {
        Cart cart = getCurrentUserCart();
        logger.info("Visszateritett cart");
        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PostMapping("/buy")
    public ResponseEntity buyCartContent(){
        Cart cart = getCurrentUserCart();
        userService.buyCartContent(cart);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)auth.getPrincipal();
        User user = userService.getUserByUsername(username);
        emailService.SendSimpleEmail(user.getEmail(), "Webshop visszaigazolás", "Termékeket sikeresen megvette!");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Cart> addProductToCart(@PathVariable Long productId){
        Cart cart = getCurrentUserCart();
        cart = cartService.addProductToCart(cart, productId);
        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Cart> emptyCart(){
        Cart cart = getCurrentUserCart();
        cart = cartService.emptyCart(cart);
        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Cart> removeProductFromCart(@PathVariable Long productId){
        Cart cart = getCurrentUserCart();
        cart = cartService.removeProductFromCart(cart, productId);
        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    private Cart getCurrentUserCart(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)auth.getPrincipal();
        User user = userService.getUserByUsername(username);
        return user.getCart();
    }
}