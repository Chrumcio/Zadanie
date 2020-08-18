package com.example.demo.controller;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {
    private static final int MIN_DISCOUNT = 0;
    private static final int MAX_LIST_SIZE = 5;

    private ProductService productService;
    private ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    /*
    Główny endpoint aplikacji. Po jego odpytaniu zostaje zwrócona nazwa produktu wraz z jego zniżką.
    Sprawdzam na wejsciu czy kwota rabatu nie jest liczbą ujemną oraz czy nie przekazano więcej niż 5 produktów,
    których z założenia przy jednej transakcji można dodać tylko 5. W momencie podania zbyt dużej listy produktów, bądź
    niepoprawnego rabatu zostanie zwrócony użytkownikowi błąd http 409 oraz lista produktów zawierająca nazwę produktu
    oraz rabat w wysokości 0. Produkty nie zostają dodane do bazy danych.
     */
    @PostMapping("/add")
    public ResponseEntity<List<ProductResponse>> addProduct(@Valid @RequestBody List<ProductRequest> productRequests, @RequestParam int discount){
        if(discount < MIN_DISCOUNT || MAX_LIST_SIZE < productRequests.size()){
            return ResponseEntity.status(HttpStatus.CONFLICT).
                    body(productRequests.stream()
                            .map(request -> modelMapper.map(request,ProductResponse.class))
                            .collect(Collectors.toList()));
        }
        var newProducts = productService.addProduct(productRequests,discount);
        return ResponseEntity.ok().body(newProducts);
    }

    /*
    Dodany endpoint do aplikacji. Zwraca wszystkie produkty, które zawierają nazwę i kwotę zniżki.
     */
    @GetMapping("/all")
    public ResponseEntity<List<ProductResponse>> getProducts(){
        return ResponseEntity.ok().body(productService.getProducts());
    }
}
