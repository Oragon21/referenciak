package alf.webshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import alf.webshop.domain.ECategory;
import alf.webshop.domain.Product;
import alf.webshop.repository.ProductRepository;

@SpringBootTest
public class ProdcutServiceTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    private Product product1 = new Product(
        "Babaagy",
        200,
        "valami leiras",
        ECategory.BABY
    );
    private Product product2 = new Product(
        "Kepkeret",
        500,
        "valami leiras ami jo",
        ECategory.ART
    );

    @BeforeEach
    public void before(){
        productRepository.deleteAll();
        productRepository.flush();
    }

    @Test
    public void userServiceContextLoads(){
        assertNotNull(productService);
    }

    @Test
    public void databaseEmptyTest(){
        boolean products = productService.getProducts().isEmpty();
        assertTrue(products);
    }

    @Test
    public void createProductTest(){
        Product result = productService.createProduct(product1);
        assertNotNull(result);
        assertNotNull(result.getId());
    }

    @Test
    public void getProductTest(){
        productService.createProduct(product1);
        productService.createProduct(product2);
        List<Product> products = productService.getProducts();
        assertEquals(2, products.size());
    }

    public void getProductTestByCategory(){
        productService.createProduct(product2);
        List<Product> products = productService.getProducts();
        assertEquals(ECategory.ART, products.get(0).getCategory());
    }

    @Test
    public void updateProductTest(){
        Product created = productService.createProduct(product1);
        created = productService.updateProduct(product2);
        assertNotNull(created);
        assertEquals(created.getId(), product2.getId());
        assertEquals(created.getName(), product2.getName());
    }

    @Test
    public void deleteUserTest(){
        Product product = productService.createProduct(product1);
        productService.deleteProductById(product.getId());
        List<Product> products = productService.getProducts();
        assertEquals(0, products.size());
    }
}