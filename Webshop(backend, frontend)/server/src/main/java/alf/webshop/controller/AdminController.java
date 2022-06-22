package alf.webshop.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alf.webshop.domain.Product;
import alf.webshop.domain.User;
import alf.webshop.service.ProductService;
import alf.webshop.service.UserService;


@RestController
@RequestMapping(path = "/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public AdminController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    public List<User> getUsers() {
        logger.info("Visszateritett userek");
        return userService.getUsers();
        
    }

    @PostMapping("/createuser")
    public ResponseEntity<User> createUser(@RequestBody User user){
        user = userService.createUser(user);
        logger.info("Sikeres user krealas");
        return new ResponseEntity<User>(user, HttpStatus.CREATED);

    }

    @PostMapping("/createadmin")
    public ResponseEntity<User> createAdminUser(@RequestBody User adminUser){
        adminUser = userService.createAdminUser(adminUser);
        logger.info("Sikeres admin krealas");

        return new ResponseEntity<User>(adminUser, HttpStatus.CREATED);
    }

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> products = productService.getProducts();
        logger.info("Visszateritett termekek");
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
    
    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") Long productId){
        Optional<Product> product = productService.getProduct(productId);
        if(!product.isPresent()){
            logger.warn("Nincs ilyen termek");
            return new ResponseEntity<Product>((Product)null, HttpStatus.NOT_FOUND);
        }
        logger.info("Van ilyen termek");
        return new ResponseEntity<Product>(product.get(), HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        product = productService.createProduct(product);
        logger.info("Sikeresen letrehozodott a termek");

        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }
    
    @PutMapping("/product/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("productId") Long productId){
        productService.deleteProductById(productId);
        logger.info("Sikeresen torlodott a termek");

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
