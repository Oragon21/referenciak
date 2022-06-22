package alf.webshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alf.webshop.domain.Cart;
import alf.webshop.domain.Product;
import alf.webshop.repository.CartRepository;
import alf.webshop.repository.ProductRepository;

@Service
public class CartService {
    Logger logger = LoggerFactory.getLogger(CartService.class);
    
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public List<Cart> getCarts() {
        logger.info("Visszateritett carts-ok");

        return cartRepository.findAll();
    }

    public Optional<Cart> getCart(Long cartId){
        return cartRepository.findById(cartId);
    }

    public Cart createCart(){
        return cartRepository.save(new Cart());
    }

    public Cart updateCart(Cart cart, Cart newData){
        cart.copyValidValuesFrom(newData);
        return cartRepository.save(cart);
    }

    public Cart addProductToCart(Cart cart, Long productId) {
        List<Product> products = cart.getProducts();
        Optional<Product> product = productRepository.findById(productId);
        if(!product.isPresent()){
            throw new EntityNotFoundException(String.format("Product with id %d not found", productId));
        }
        products.add(product.get());
        cart.setProducts(products);
        return cartRepository.save(cart);
    }

    public Cart emptyCart(Cart cart){
        cart.setProducts(new ArrayList<Product>());
        return cartRepository.save(cart);
    }

    public Cart removeProductFromCart(Cart cart, Long productId) {
        List<Product> products = cart.getProducts();
        products.removeIf(p -> productId == p.getId());
        cart.setProducts(products);
        return cartRepository.save(cart);
    }

    public void deleteCart(Long cartId){
        cartRepository.deleteById(cartId);
    }
}
