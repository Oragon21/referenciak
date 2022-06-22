package alf.webshop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import alf.webshop.domain.ECategory;
import alf.webshop.domain.Product;
import alf.webshop.service.ProductService;

@RestController
@RequestMapping(path = "/product")
@PreAuthorize("isAuthenticated()")
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);
    
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        logger.info("Visszateritett termekek");

        return new ResponseEntity<List<Product>>(productService.getProducts(), HttpStatus.OK);
    }
    
    @GetMapping("/{productId}")
    public ResponseEntity getProductById(@PathVariable("productId") Long productId) {
        Optional<Product> product = productService.getProduct(productId);
        if(!product.isPresent()){
            logger.warn("Nincs ilyen termek");
            return new ResponseEntity<String>(String.format("Product with id '%d' does not exist", productId), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Product>(product.get(), HttpStatus.OK);
    }
    
    @DeleteMapping("/{productId}")
    public ResponseEntity deleteProductById(@PathVariable("productId") Long productId) {
        productService.deleteProductById(productId);
        logger.warn("Kitorlodott sikeresen a termek");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable("category") ECategory category) {
        List<Product> products = productService.getProductsByCategory(category);
        logger.info("Sikeresen visszaterultek a termekek kategoria szerin");
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
}
