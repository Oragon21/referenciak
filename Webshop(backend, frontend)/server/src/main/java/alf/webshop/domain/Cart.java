package alf.webshop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@ManyToMany( fetch = FetchType.LAZY)
    private List<Product> products;

    public Cart() {
        products = new ArrayList<Product>();
    }

    public Cart(List<Product> products) {
        this.products = products;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void copyValidValuesFrom(@NotNull Cart cart) {
        if(cart.products != null){
            this.products = cart.products;
        }
    }

}
