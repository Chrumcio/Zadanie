package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/*
Klasa modelowa aplikacji, każde pole klasy będzie kolumną w tabeli product w bazie danych.
ID jest generowane automatycznie
 */

@Entity
@Table(name = "product")
@Setter
@Getter
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "price")
    @NotNull
    @Min(0)
    private Integer price;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "priceWithDiscount")
    private Integer priceWithDiscount;

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", priceWithDiscount=" + priceWithDiscount +
                '}';
    }
}
