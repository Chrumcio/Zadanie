package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/*
Interfejs służący do komunikacji z bazą danych. JpaRepository udostępnia wystarczającą
ilość metod do komunikacji z bazą danych, które wykorzystałem w aplikacji.
 */

public interface ProductRepository extends JpaRepository <Product,Long> {
}
