package alf.webshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import alf.webshop.domain.Cart;
import alf.webshop.repository.CartRepository;
import alf.webshop.repository.ProductRepository;
import alf.webshop.repository.UserRepository;
import alf.webshop.domain.ECategory;
import alf.webshop.domain.Product;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class CartServiceTest {
    
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartService cartService;
   
    /*private Product product2 = new Product(
        "Gep",
        50000,
        "valami leiras ami jo",
        ECategory.ELECTRONICS
    );*/

    ArrayList<Product> products = new ArrayList<Product>() {{
        add(new Product("Babaagy",
        200,
        "valami leiras",
        ECategory.BABY
        )        
    );
        add(new Product(
        "Kepkeret",
        500,
        "valami leiras ami jo",
        ECategory.ART));}
    };

    Cart megaCart = new Cart(products);

    @BeforeAll
    public void beforeAll() {
        userRepository.deleteAll();
        userRepository.flush();
    }
    
    @BeforeEach
    public void before() {
        cartRepository.deleteAll();
        cartRepository.flush();
    }

    @Test
    public void cartServiceContextLoad(){
        assertNotNull(cartService);
    }

    @Test
    public void databaseEmpty(){
        boolean cartExist = cartService.getCarts().isEmpty();
        assertTrue(cartExist);
    }


    @Test
    public void createCart() {
        Cart result = cartService.createCart();
        assertNotNull(result.getId());
    }

    @Test
    public void getAddress(){
        cartService.createCart();
        cartService.createCart();
        List<Cart> carts = cartService.getCarts();
        assertEquals(2, carts.size());
    }

    @Test
    public void updateCart(){
        // create products for test
        for (Product product : products) {
            productRepository.save(product);
        }
        productRepository.flush();
        Cart created = cartService.createCart();
        Cart result = cartService.updateCart(created, megaCart);
        assertNotNull(result);
        assertEquals(created.getId(), result.getId());
        assertEquals(created.getProducts().size(), result.getProducts().size());
    }

    @Test
    public void deleteCart(){
        Cart cart = cartService.createCart();
        cartService.deleteCart(cart.getId());;
        ArrayList<Cart> carts = (ArrayList)cartService.getCarts();
        assertEquals(0, carts.size());
    }
}