package alf.webshop.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
public class Product {

    public Product() {}

    public Product(String name, int price, String description, ECategory category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 10)
    private String name;

    @Positive
    private int price;

    @Size(min = 5, max =80)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ECategory category;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ECategory getCategory() {
        return this.category;
    }

    public void setCategory(ECategory category) {
        this.category = category;
    }

    public Product copyValidValuesFrom(Product product) {
        if (product.id != null)
            this.id = product.id;
        if (product.name != null)
            this.name = product.name;
        if (product.price != 0)
            this.price = product.price;
        if (product.description != null)
            this.description = product.description;
        if (product.category != null)
            this.category = product.category;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", price='" + getPrice() + "'" +
                ", description='" + getDescription() + "'" +
                ", category='" + getCategory() + "'" +
                "}";
    }
}

// {
//     "id": 1,
//     "name": "Termek1",
//     "price": 15,
//     "description": "Ez egy leiras1",
//     "category": "OTHER"
// }