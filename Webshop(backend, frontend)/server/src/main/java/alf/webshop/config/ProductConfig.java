package alf.webshop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import alf.webshop.domain.ECategory;
import alf.webshop.domain.Product;
import alf.webshop.service.ProductService;

@Configuration
public class ProductConfig {
    
    Logger logger = LoggerFactory.getLogger(ProductConfig.class);
    @Bean
    CommandLineRunner productConfigCommandLineRunner(ProductService productService) {
        
        return args -> {

            Product product1 = new Product("Kép", 5, "Ez egy szép festmeny.", ECategory.ART);
            Product product2 = new Product("Jároka", 7, "Ha szüksége lenne egy biztos támaszra a gyereke számára.", ECategory.BABY);
            Product product3 = new Product("Ecset", 12, "Festőecset, érezze magát Picassonak.", ECategory.ART);
            Product product4 = new Product("Autó", 3, "Gyorsulás 0-ról 100-ra 5 másodperc alatt. Jó választás.", ECategory.CAR);
            Product product5 = new Product("Ház", 150, "Nincs hol laknia? Csak tegye be kosarába és minden megoldódik.", ECategory.OTHER);
            Product product6 = new Product("Motor", 5, "Gyorsulási motor csak saját felelősségre.", ECategory.CAR);
            Product product7 = new Product("Kosárlabda", 5, "Pattogtasson kedvére sajár otthonában vagy a pályán.", ECategory.SPORTS);
            productService.createProduct(product1);
            productService.createProduct(product2);
            productService.createProduct(product3);
            productService.createProduct(product4);
            productService.createProduct(product5);
            productService.createProduct(product6);
            productService.createProduct(product7);
            logger.info("Sikeresen feltoltodott");
        };
    }
}
