package com.khanalsharad.dailyshoppingcart.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
@AllArgsConstructor
//@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Product> products;

    public Category() {
        // Empty constructor to satisfy JPA requirement
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

//    public List<Product> getProducts() {
//        return products;
//    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public void setProducts(List<Product> products) {
//        this.products = products;
//    }

    public Category(String name) {
        this.name = name;
    }
}
