package alf.webshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import alf.webshop.domain.Cart;
import alf.webshop.domain.Product;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
	List<Cart> findAllByProductsContains(Product product);
}