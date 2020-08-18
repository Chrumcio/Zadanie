package com.example.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/*
Klasa służąca do zabezpieczenia przepływu danych pomiędzy warstwami aplikacji użytkownikowi zostają udostępnione
tylko te pola, które chcemy.
 */

@Getter
@Setter
public class ProductRequest {
    @NotNull
    private String name;
    @Min(0)
    private int price;
}
