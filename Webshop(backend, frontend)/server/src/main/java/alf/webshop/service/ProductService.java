package alf.webshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alf.webshop.domain.Cart;
import alf.webshop.domain.ECategory;
import alf.webshop.domain.Product;
import alf.webshop.repository.CartRepository;
import alf.webshop.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class ProductService {
    Logger logger = LoggerFactory.getLogger(ProductService.class);
    
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public List<Product> getProducts() {
        logger.info("Visszateritett termekek");

        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(ECategory category) {
        return productRepository.findAllByCategory(category);
    }

    public Optional<Product> getProduct(Long productId){
        return productRepository.findById(productId);
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public Product updateProduct(Product product){
        return productRepository.save(product);
    }

    public void deleteProductById(Long id){
        Product product = productRepository.getById(id);
        List<Cart> carts = cartRepository.findAllByProductsContains(product);
        for (Cart cart : carts) {
            cart.setProducts(new ArrayList<Product>());
            cartRepository.save(cart);
        }
        cartRepository.flush();
        productRepository.deleteById(id);
    }
}
